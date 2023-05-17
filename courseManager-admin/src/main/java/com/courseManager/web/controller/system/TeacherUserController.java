package com.courseManager.web.controller.system;

import java.util.List;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.courseManager.common.annotation.Log;
import com.courseManager.common.enums.BusinessType;
import com.courseManager.system.domain.TeacherUser;
import com.courseManager.system.service.ITeacherUserService;
import com.courseManager.common.core.controller.BaseController;
import com.courseManager.common.core.domain.AjaxResult;
import com.courseManager.common.utils.poi.ExcelUtil;
import com.courseManager.common.core.page.TableDataInfo;

/**
 * 教员与用户关联Controller
 * 
 * @author ruoyi
 * @date 2023-03-15
 */
@Controller
@RequestMapping("/system/teacherWithUser")
public class TeacherUserController extends BaseController
{
    private String prefix = "system/teacherWithUser";

    @Autowired
    private ITeacherUserService teacherUserService;

    @RequiresPermissions("system:user:view")
    @GetMapping()
    public String user()
    {
        return prefix + "/user";
    }

    /**
     * 查询教员与用户关联列表
     */
    @RequiresPermissions("system:user:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(TeacherUser teacherUser)
    {
        startPage();
        List<TeacherUser> list = teacherUserService.selectTeacherUserList(teacherUser);
        return getDataTable(list);
    }

    /**
     * 导出教员与用户关联列表
     */
    @RequiresPermissions("system:user:export")
    @Log(title = "教员与用户关联", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(TeacherUser teacherUser)
    {
        List<TeacherUser> list = teacherUserService.selectTeacherUserList(teacherUser);
        ExcelUtil<TeacherUser> util = new ExcelUtil<TeacherUser>(TeacherUser.class);
        return util.exportExcel(list, "教员与用户关联数据");
    }

    /**
     * 新增教员与用户关联
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存教员与用户关联
     */
    @RequiresPermissions("system:user:add")
    @Log(title = "教员与用户关联", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(TeacherUser teacherUser)
    {
        return toAjax(teacherUserService.insertTeacherUser(teacherUser));
    }

    /**
     * 修改教员与用户关联
     */
    @RequiresPermissions("system:user:edit")
    @GetMapping("/edit/{tuId}")
    public String edit(@PathVariable("tuId") Long tuId, ModelMap mmap)
    {
        TeacherUser teacherUser = teacherUserService.selectTeacherUserByTuId(tuId);
        mmap.put("teacherUser", teacherUser);
        return prefix + "/edit";
    }

    /**
     * 修改保存教员与用户关联
     */
    @RequiresPermissions("system:user:edit")
    @Log(title = "教员与用户关联", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(TeacherUser teacherUser)
    {
        return toAjax(teacherUserService.updateTeacherUser(teacherUser));
    }

    /**
     * 删除教员与用户关联
     */
    @RequiresPermissions("system:user:remove")
    @Log(title = "教员与用户关联", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(teacherUserService.deleteTeacherUserByTuIds(ids));
    }
}
