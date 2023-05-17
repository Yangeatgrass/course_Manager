package com.courseManager.web.controller.system;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.courseManager.common.config.RuoYiConfig;
import com.courseManager.common.core.domain.entity.SysRole;
import com.courseManager.common.core.domain.entity.SysUser;
import com.courseManager.common.utils.ShiroUtils;
import com.courseManager.common.utils.StringUtils;
import com.courseManager.common.utils.file.FileUploadUtils;
import com.courseManager.common.utils.file.MimeTypeUtils;
import com.courseManager.common.utils.reflect.ReflectUtils;
import com.courseManager.framework.shiro.service.SysPasswordService;
import com.courseManager.system.mapper.SysUserMapper;
import com.courseManager.system.service.ISysUserService;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import com.courseManager.common.annotation.Log;
import com.courseManager.common.enums.BusinessType;
import com.courseManager.system.domain.Teacher;
import com.courseManager.system.service.ITeacherService;
import com.courseManager.common.core.controller.BaseController;
import com.courseManager.common.core.domain.AjaxResult;
import com.courseManager.common.utils.poi.ExcelUtil;
import com.courseManager.common.core.page.TableDataInfo;
import org.springframework.web.multipart.MultipartFile;

/**
 * 教员Controller
 *
 * @author ruoyi
 * @date 2023-03-13
 */
@Controller
@RequestMapping("/system/teacher")
public class TeacherController extends BaseController {

    private static Logger log = LoggerFactory.getLogger(TeacherController.class);

    private String prefix = "system/teacher";

    @Autowired
    private ITeacherService teacherService;
    @Autowired
    private ISysUserService sysUserService;
    @Autowired
    private SysPasswordService passwordService;
    @Autowired
    private SysUserMapper sysUserMapper;

    @RequiresPermissions("system:teacher:view")
    @GetMapping()
    public String teacher() {
        return prefix + "/teacher";
    }

    /**
     * 教员注册到用户中
     */
    @Log(title = "教员管理", businessType = BusinessType.INSERT)
    @RequiresPermissions("system:user:add")
    @PostMapping("/registerTeacher")
    @ResponseBody
    public AjaxResult registerTeacher(Integer[] teacherIds) {
        return AjaxResult.success(teacherService.registerTeacher(teacherIds));
    }

    /**
     * 教员状态修改
     */
    @Log(title = "教员管理", businessType = BusinessType.UPDATE)
    @RequiresPermissions("system:teacher:edit")
    @PostMapping("/changeStatus")
    @ResponseBody
    public AjaxResult changeStatus(Teacher teacher) {
        int i = teacherService.changeStatus(teacher);
        if(i==-9){
            return error("该教员未注册，无法设置状态!");
        }
        return toAjax(i);
    }

    /**
     * 查询教员列表
     */
    @RequiresPermissions("system:teacher:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(Teacher teacher) {
        startPage();
        List<Teacher> list = teacherService.selectTeacherList(teacher);
        return getDataTable(list);
    }

    /**
     * 导出教员列表
     */
    @RequiresPermissions("system:teacher:export")
    @Log(title = "教员管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(Teacher teacher) {
        List<Teacher> list = teacherService.selectTeacherList(teacher);
        ExcelUtil<Teacher> util = new ExcelUtil<Teacher>(Teacher.class);
        return util.exportExcel(list, "教员数据");
    }

    /**
     * 导入教员
     */
    @Log(title = "教员管理", businessType = BusinessType.IMPORT)
    @RequiresPermissions("system:teacher:import")
    @PostMapping("/importData")
    @ResponseBody
    public AjaxResult importData(MultipartFile file, boolean updateSupport) throws Exception {
        ExcelUtil<Teacher> util = new ExcelUtil<Teacher>(Teacher.class);
        List<Teacher> teachers = util.importExcel(file.getInputStream());
        String message = teacherService.importTeacher(teachers, updateSupport, getLoginName());
        return AjaxResult.success(message);
    }

    /**
     * 导入教员模板
     *
     * @return
     */
    @RequiresPermissions("system:teacher:view")
    @GetMapping("/importTemplate")
    @ResponseBody
    public AjaxResult importTemplate() {
        ExcelUtil<Teacher> util = new ExcelUtil<Teacher>(Teacher.class);
        return util.importTemplateExcel("教员数据");
    }

    /**
     * 新增教员
     */
    @GetMapping("/add")
    public String add() {
        return prefix + "/add";
    }

    /**
     * 新增保存教员
     */
    @RequiresPermissions("system:teacher:add")
    @Log(title = "教员", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(@Validated Teacher teacher) {
        SysUser user = new SysUser();
        user.setLoginName(teacher.getIdNumber());//将教员的证件号作为用户登录名校验
//        user.setLoginName(teacher.getPhoneNumber());//将教员的手机号作为用户手机号校验
        if (!sysUserService.checkLoginNameUnique(user)) {
            return error("新增教员'" + teacher.getTeacherName() + "'失败，证件号已存在");
        } else if (StringUtils.isNotEmpty(teacher.getPhoneNumber())  && StringUtils.isNotNull(teacherService.checkPhoneUnique(teacher.getPhoneNumber()))) {
            return error("新增教员'" + teacher.getTeacherName() + "'失败，电话号码已存在");
        }
        return toAjax(teacherService.insertTeacher(teacher)); //保存教员
    }

    /**
     * 修改教员
     */
    @RequiresPermissions("system:teacher:edit")
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap) {
        Teacher teacher = teacherService.selectTeacherById(id);
        mmap.put("teacher", teacher);
        return prefix + "/edit";
    }

    /**
     * 修改保存教员
     */
    @RequiresPermissions("system:teacher:edit")
    @Log(title = "教员", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(Teacher teacher) {
//        SysUser user = sysUserMapper.checkNotUniqueWithIdNumber(teacher.getIdNumber());
//        if (!StringUtils.isNull(user)) {
//            return error("修改教员'" + teacher.getTeacherName() + "'失败，证件号已存在");
//        } else if (StringUtils.isNotEmpty(teacher.getPhoneNumber())  && StringUtils.isNotNull(teacherService.checkEditPhoneUnique(teacher.getPhoneNumber(),teacher.getId()))) {
//            return error("修改教员'" + teacher.getTeacherName() + "'失败，电话号码已存在");
//        } else {
//            teacher.setFlag(0);
//        }
        if (StringUtils.isNotEmpty(teacher.getPhoneNumber())  && StringUtils.isNotNull(sysUserMapper.checkPhoneUniqueByLoginName(teacher.getPhoneNumber(),teacher.getIdNumber()))) {
            return error("修改教员'" + teacher.getTeacherName() + "'失败，电话号码已存在");
        }

        return toAjax(teacherService.updateTeacher(teacher));
    }

    /**
     * 删除教员(修改删除标志)
     */
    @RequiresPermissions("system:teacher:remove")
    @Log(title = "教员", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
//        if(teacherService.validDeletion(ids)){
//            return toAjax(teacherService.deleteTeacherByIds(ids));
//        }
//        return error("删除失败，有教员名下还有课题!");
        return toAjax(teacherService.deleteTeacherByIds(ids));
    }

    /**
     * 教员详情
     *
     * @param id
     * @param mmap
     * @return
     */
    @RequiresPermissions("system:teacher:edit")
    @GetMapping("/detail/{id}")
    public String detail(@PathVariable("id") Long id, ModelMap mmap) {
        mmap.put("teacher", teacherService.selectTeacherById(id));
        return prefix + "/detail";
    }

    /**
     * 审核教员
     */
    @RequiresPermissions("system:teacher:edit")
    @Log(title = "教员", businessType = BusinessType.UPDATE)
    @PostMapping("/audit")
    @ResponseBody
    public AjaxResult auditSave(Teacher teacher) {
        return toAjax(teacherService.updateTeacher(teacher));
    }

    /**
     * 选择头像
     */
    @GetMapping("/avatar")
    public String avatar(ModelMap mmap)
    {
        return prefix + "/profile/avatar";
    }

    /**
     * 保存头像
     */
    @Log(title = "教员信息", businessType = BusinessType.UPDATE)
    @PostMapping("/updateAvatar")
    @ResponseBody
    public AjaxResult updateAvatar(@RequestParam("avatarfile") MultipartFile file)
    {
        try
        {
            if (!file.isEmpty())
            {
                String avatar = FileUploadUtils.upload(RuoYiConfig.getAvatarPath(), file, MimeTypeUtils.IMAGE_EXTENSION);
                return success(avatar);
            }
            return error();
        }
        catch (Exception e)
        {
            log.error("修改头像失败！", e);
            return error(e.getMessage());
        }
    }
}
