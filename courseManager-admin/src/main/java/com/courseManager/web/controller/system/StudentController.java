package com.courseManager.web.controller.system;

import java.util.List;

import com.courseManager.common.core.domain.entity.SysUser;
import com.courseManager.common.utils.StringUtils;
import com.courseManager.system.domain.Teacher;
import com.courseManager.system.mapper.SysUserMapper;
import com.courseManager.system.service.ISysUserService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.courseManager.common.annotation.Log;
import com.courseManager.common.enums.BusinessType;
import com.courseManager.system.domain.Student;
import com.courseManager.system.service.IStudentService;
import com.courseManager.common.core.controller.BaseController;
import com.courseManager.common.core.domain.AjaxResult;
import com.courseManager.common.utils.poi.ExcelUtil;
import com.courseManager.common.core.page.TableDataInfo;
import org.springframework.web.multipart.MultipartFile;

/**
 * 学员Controller
 *
 * @author xiaoyang
 * @date 2023-03-17
 */
@Controller
@RequestMapping("/system/student")
public class StudentController extends BaseController {
    private String prefix = "system/student";

    @Autowired
    private IStudentService studentService;

    @Autowired
    private ISysUserService sysUserService;

    @Autowired
    private SysUserMapper sysUserMapper;

    @RequiresPermissions("system:student:view")
    @GetMapping()
    public String student() {
        return prefix + "/student";
    }

    /**
     * 学员注册到用户中
     */
    @Log(title = "学员管理", businessType = BusinessType.INSERT)
    @RequiresPermissions("system:user:add")
    @PostMapping("/registerStudent")
    @ResponseBody
    public AjaxResult registerStudent(Integer[] studentIds) {
        return AjaxResult.success(studentService.registerTeacher(studentIds));
    }

    /**
     * 学员状态修改
     */
    @Log(title = "学员管理", businessType = BusinessType.UPDATE)
    @RequiresPermissions("system:student:edit")
    @PostMapping("/changeStatus")
    @ResponseBody
    public AjaxResult changeStatus(Student student) {
        int i = studentService.changeStatus(student);
        if(i==-9){
            return error("该学员未注册，无法设置状态!");
        }
        return toAjax(i);
    }

    /**
     * 根据课题id查询没有加入该课题的学员
     */
    @RequiresPermissions("system:student:list")
    @PostMapping("/studentListByInvite/{courseId}")
    @ResponseBody
    public TableDataInfo studentListByInvite(@PathVariable Long courseId, SysUser sysUser,Student student) {
        startPage();
//        List<SysUser> list = sysUserService.studentListByInvite(courseId, sysUser);
        List<Student> studentList = studentService.studentListByInvite(courseId,student,sysUser);
        return getDataTable(studentList);
    }

    /**
     * 移除用户与课题关联
     */
    @RequiresPermissions("system:student:list")
    @PostMapping("/saveUserOnCourse")
    @ResponseBody
    public AjaxResult saveUserOnCourse(Long courseId, Long[] userIds) {
        return toAjax(sysUserService.saveUserOnCourse(courseId, userIds));
    }

    /**
     * 查询学员列表
     */
    @RequiresPermissions("system:student:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(Student student) {
        startPage();
        List<Student> list = studentService.selectStudentList(student);
        return getDataTable(list);
    }

    /**
     * 导出学员列表
     */
    @RequiresPermissions("system:student:export")
    @Log(title = "学员", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(Student student) {
        List<Student> list = studentService.selectStudentList(student);
        ExcelUtil<Student> util = new ExcelUtil<Student>(Student.class);
        return util.exportExcel(list, "学员数据");
    }

    /**
     * 导入学员
     */
    @Log(title = "学员管理", businessType = BusinessType.IMPORT)
    @RequiresPermissions("system:student:import")
    @PostMapping("/importData")
    @ResponseBody
    public AjaxResult importData(MultipartFile file, boolean updateSupport) throws Exception {
        ExcelUtil<Student> util = new ExcelUtil<Student>(Student.class);
        List<Student> students = util.importExcel(file.getInputStream());
        String message = studentService.importTeacher(students, updateSupport, getLoginName());
        return AjaxResult.success(message);
    }

    /**
     * 导入学员模板
     *
     * @return
     */
    @RequiresPermissions("system:student:view")
    @GetMapping("/importTemplate")
    @ResponseBody
    public AjaxResult importTemplate() {
        ExcelUtil<Student> util = new ExcelUtil<Student>(Student.class);
        return util.importTemplateExcel("学员数据");
    }


    /**
     * 新增学员
     */
    @GetMapping("/add")
    public String add() {
        return prefix + "/add";
    }

    /**
     * 新增保存学员
     */
    @RequiresPermissions("system:student:add")
    @Log(title = "学员", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(Student student) {
        SysUser user = new SysUser();
        user.setLoginName(student.getSNumber());//将学员的学号作为用户登录名校验
        user.setLoginName(student.getPhoneNumber());//将学员的手机号作为用户手机号校验
        if (!sysUserService.checkLoginNameUnique(user)) {
            return error("新增学员'" + user.getLoginName() + "'失败，学号号已存在");
        } else if (StringUtils.isNotEmpty(student.getPhoneNumber()) && !sysUserService.checkPhoneUnique(user)) {
            return error("新增学员'" + student.getStudentName() + "'失败，电话号码已存在");
        }
        return toAjax(studentService.insertStudent(student));
    }

    /**
     * 修改学员
     */
    @RequiresPermissions("system:student:edit")
    @GetMapping("/edit/{studentId}")
    public String edit(@PathVariable("studentId") Long studentId, ModelMap mmap) {
        Student student = studentService.selectStudentByStudentId(studentId);
        mmap.put("student", student);
        return prefix + "/edit";
    }

    /**
     * 修改保存学员
     */
    @RequiresPermissions("system:student:edit")
    @Log(title = "学员", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(Student student) {
//        SysUser user = sysUserMapper.checkNotUniqueWithIdNumber(student.getSNumber());
//        if(!StringUtils.isNull(user)){
//            return error("修改学员'" + student.getStudentName() + "'失败，学号已存在");
//        }else if(!StringUtils.isNull()){
//            return error("修改学员'" + student.getStudentName() + "'失败，电话号码已存在");
//        }else{
//            //校验完成 设为可用
//            student.setFlag(0);
//        }
        if (!StringUtils.isEmpty(student.getPhoneNumber()) && !ObjectUtils.isEmpty(sysUserMapper.checkPhoneUniqueByLoginName(student.getPhoneNumber(),student.getSNumber()))) {
            return error("修改学员 " + student.getStudentName() + " 失败，电话号码已存在");
        }
        return toAjax(studentService.updateStudent(student));
    }

    /**
     * 删除学员
     */
    @RequiresPermissions("system:student:remove")
    @Log(title = "学员", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(studentService.deleteStudentByStudentIds(ids));
    }

    /**
     * 选择头像
     */
    @GetMapping("/avatar")
    public String avatar(ModelMap mmap) {
        return prefix + "/profile/avatar";
    }

}
