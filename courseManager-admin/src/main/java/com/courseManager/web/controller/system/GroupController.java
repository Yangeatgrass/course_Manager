package com.courseManager.web.controller.system;

import java.util.Arrays;
import java.util.List;

import com.courseManager.common.core.domain.Ztree;
import com.courseManager.common.core.domain.entity.SysUser;
import com.courseManager.common.core.page.PageDomain;
import com.courseManager.common.core.page.TableSupport;
import com.courseManager.common.utils.ShiroUtils;
import com.courseManager.common.utils.StringUtils;
import com.courseManager.common.utils.page.PageUtils;
import com.courseManager.system.domain.Course;
import com.courseManager.system.domain.GroupUser;
import com.courseManager.system.domain.Student;
import com.courseManager.system.service.ICourseService;
import com.courseManager.system.service.IGroupUserService;
import com.courseManager.system.service.IStudentService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import com.courseManager.common.annotation.Log;
import com.courseManager.common.enums.BusinessType;
import com.courseManager.system.domain.Group;
import com.courseManager.system.service.IGroupService;
import com.courseManager.common.core.controller.BaseController;
import com.courseManager.common.core.domain.AjaxResult;
import com.courseManager.common.utils.poi.ExcelUtil;
import com.courseManager.common.core.page.TableDataInfo;

/**
 * 小组Controller
 * 
 * @author ruoyi
 * @date 2023-03-30
 */
@Controller
@RequestMapping("/system/group")
public class GroupController extends BaseController
{
    private String prefix = "system/group";

    @Autowired
    private IGroupService groupService;

    @Autowired
    private ICourseService courseService;

    @Autowired
    private IGroupUserService groupUserService;

    @Autowired
    private IStudentService studentService;

    /**
     * 加载小组列表树
     */
    @RequiresPermissions("system:group:list")
    @GetMapping("/groupTreeData/{courseId}")
    @ResponseBody
    public List<Ztree> groupTreeData(@PathVariable Long courseId)
    {
        List<Ztree> ztrees = groupService.selectGroupTree(new Group(),courseId);
        return ztrees;
    }

    /**
     * 加载学员查看课题的小组列表树
     */
//    @RequiresPermissions("system:group:list")
    @GetMapping("/groupTreeDataToReview/{courseId}")
    @ResponseBody
    public List<Ztree> groupTreeDataToReview(@PathVariable Long courseId)
    {
        List<Ztree> ztrees = groupService.selectGroupTreeToReview(courseId);
        return ztrees;
    }

    @RequiresPermissions("system:group:view")
    @GetMapping()
    public String group()
    {
        return prefix + "/group";
    }

    //进入学员分配页面
//    @RequiresPermissions("system:group:distribution")
    @GetMapping("/asStudent/{courseId}/{groupId}")
    public String asStudent(@PathVariable Long courseId,@PathVariable Long groupId,ModelMap mmap)
    {
        //获取当前课题未分配小组的学员
        List<Student> notIn = studentService.getCourseNotAssignment(courseId,new Student());
        //根据小组id查询到在该小组的学员
        List<Student> users = studentService.selectInGroupStudent(groupId,new Student());
        mmap.put("users",users);
        mmap.put("notInUsers",notIn);
        mmap.put("groupId",groupId);
        mmap.put("groupManager",groupService.selectGroupByGroupId(groupId).getGroupManager());
        mmap.put("ManagerFlag", ShiroUtils.getSubject().hasRole("admin") || ShiroUtils.getSubject().hasRole("teacher"));
        return "system/course/notStart/asStudent";
    }


    //进入设置组长弹窗
    @RequiresPermissions("system:group:manager")
    @GetMapping("/asManager/{groupId}")
    public String asStudent(@PathVariable Long groupId,ModelMap mmap)
    {
        //根据小组id获取该小组的学员
        List<Student> users = studentService.selectInGroupStudent(groupId,new Student());
        //根据小组id获取小组
        Group group = groupService.selectGroupByGroupId(groupId);
        mmap.put("users",users);
        mmap.put("group",group);
        mmap.put("groupId",groupId);
        return prefix+"/asManager";
    }

    //保存分配好的小组成员
//    @RequiresPermissions("system:group:distribution")
    @PostMapping("/savaGroupStudent")
    @ResponseBody
    public AjaxResult savaGroupStudent(@RequestParam("gId") Long gid, @RequestParam("sIds") long[] sIds)
    {
        groupUserService.insertGroupUserBacht(gid,sIds);
        return success();
    }

    //设置小组组长
    @RequiresPermissions("system:group:edit")
    @PostMapping("/savaGroupManager")
    @ResponseBody
    public AjaxResult savaGroupManager(@RequestParam("groupId") Long groupId, @RequestParam("sId") Long sId)
    {
        return toAjax(groupUserService.savaGroupManager(groupId,sId));
    }

    /**
     * 校验小组名称唯一性
     */
    @RequiresPermissions("system:group:add")
    @Log(title = "小组", businessType = BusinessType.INSERT)
    @PostMapping("/checkGroupNameUnique")
    @ResponseBody
    public boolean checkGroupNameUnique(String groupName,Long courseId,Long groupId)
    {
        if(groupId != null){
            return groupService.checkEditGroupNameUnique(groupName,courseId,groupId);
        }
        return groupService.checkGroupNameUnique(groupName,courseId);
    }

    //获取当前课题的所有小组
    @RequiresPermissions("system:group:list")
    @PostMapping("/groupList/{courseId}")
    @ResponseBody
    public TableDataInfo groupList(@PathVariable Long courseId, Group group)
    {
//        startPage();
        group.setCourseId(courseId);
        //获取当前课题的所有小组
        List<Group> groups = groupService.selectGroupList(group);
        PageDomain pageDomain = TableSupport.buildPageRequest();

        return PageUtils.mySetPage(groups,pageDomain);
    }

    /**
     * 根据课题id查询出该课题下的所有学员
     */
    @RequiresPermissions("system:group:add")
    @Log(title = "小组", businessType = BusinessType.INSERT)
    @PostMapping("/getUserByCourseId")
    @ResponseBody
    public AjaxResult getUserByCourseId(Long courseId)
    {
        return success(groupService.getUserIdByCourseId(courseId));
    }

    /**
     * 查询小组列表
     */
    @RequiresPermissions("system:group:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(Group group)
    {
        startPage();
        List<Group> list = groupService.selectGroupList(group);
        return getDataTable(list);
    }

    /**
     * 根据小组Id查询小组
     */
    @PostMapping("/getGroupById/{groupId}")
    @ResponseBody
    public AjaxResult getGroupById(@PathVariable Long groupId)
    {
        Group group = groupService.selectGroupByGroupId(groupId);
        return success(group);
    }

    /**
     * 导出小组列表
     */
    @RequiresPermissions("system:group:export")
    @Log(title = "小组", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(Group group)
    {
        List<Group> list = groupService.selectGroupList(group);
        ExcelUtil<Group> util = new ExcelUtil<Group>(Group.class);
        return util.exportExcel(list, "小组数据");
    }

    /**
     * 新增小组
     */
    @GetMapping("/add/{courseId}")
    public String add(@PathVariable Long courseId, ModelMap mmp)
    {
        Course course = courseService.selectCourseByCourseId(courseId);
        mmp.put("course",course);
        return prefix + "/add";
    }

    /**
     * 新增保存小组
     */
    @RequiresPermissions("system:group:add")
    @Log(title = "小组", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(Group group)
    {
        return toAjax(groupService.insertGroup(group));
    }

    /**
     * 修改小组
     */
    @RequiresPermissions("system:group:edit")
    @GetMapping("/edit/{groupId}")
    public String edit(@PathVariable("groupId") Long groupId, ModelMap mmap)
    {
        Course course = new Course();
        //获取所有课题
        List<Course> courses = courseService.selectCourseList(course);
        //获取当前小组信息
        Group group = groupService.selectGroupByGroupId(groupId);
        //获取该课题的所有学员
        List<SysUser> users = groupService.getUserIdByCourseId(group.getCourseId());
        mmap.put("courses",courses);
        mmap.put("users",users);
        mmap.put("group", group);
        return prefix + "/edit";
    }

    /**
     * 修改保存小组
     */
    @RequiresPermissions("system:group:edit")
    @Log(title = "小组", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(Group group)
    {
        return toAjax(groupService.updateGroup(group));
    }

    /**
     * 删除小组
     */
    @RequiresPermissions("system:group:remove")
    @Log(title = "小组", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(groupService.deleteGroupByGroupIds(ids));
    }
}
