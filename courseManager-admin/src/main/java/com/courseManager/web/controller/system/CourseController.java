package com.courseManager.web.controller.system;

import java.util.List;
import java.util.stream.Stream;

import com.courseManager.common.core.domain.Ztree;
import com.courseManager.common.core.domain.entity.SysDept;
import com.courseManager.common.core.domain.entity.SysUser;
import com.courseManager.common.core.page.PageDomain;
import com.courseManager.common.core.page.TableSupport;
import com.courseManager.common.utils.ShiroUtils;
import com.courseManager.common.utils.page.PageUtils;
import com.courseManager.system.domain.*;
import com.courseManager.system.mapper.CourseUserMapper;
import com.courseManager.system.service.*;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import com.courseManager.common.annotation.Log;
import com.courseManager.common.enums.BusinessType;
import com.courseManager.common.core.controller.BaseController;
import com.courseManager.common.core.domain.AjaxResult;
import com.courseManager.common.utils.poi.ExcelUtil;
import com.courseManager.common.core.page.TableDataInfo;

import static com.courseManager.common.utils.PageUtils.startPage;

/**
 * 课程Controller
 *
 * @author ruoyi
 * @date 2023-03-12
 */
@Controller
@RequestMapping("/system/course")
public class CourseController extends BaseController
{
    private String prefix = "system/course";

    @Autowired
    private ICourseService courseService;
    @Autowired
    private CourseUserMapper courseUserMapper;
    @Autowired
    private ITeacherService teacherService;
    @Autowired
    private IGroupService groupService;
    @Autowired
    private IGroupUserService groupUserService;
    @Autowired
    private IResourceFileService resourceFileService;
    @Autowired
    private IStudentService studentService;

    @RequiresPermissions("system:course:view")
    @GetMapping("insedeCourse")
    public String insedeCourse()
    {
        return prefix + "/detail/insedeCourse";
    }

    //根据课题的id获取课题的资源列表
    @RequiresPermissions("system:resource:list")
    @PostMapping("/getResourceListByCourseId/{courseId}")
    @ResponseBody
    public TableDataInfo getResourceListByCourseId(@PathVariable Long courseId,ResourceFile resourceFile)
    {
        startPage();
        List<ResourceFile> list = null;
        if(null!=resourceFile.getParentId()){
            //打开文件夹操作
            list = resourceFileService.getResourceListByCourseId(courseId,resourceFile);
        }else{
            //搜索操作
            list = resourceFileService.getResourceListByCourseIdToSearch(courseId,resourceFile);
        }
        return getDataTable(list);
    }

    //进入资源审核页
    @RequiresPermissions("system:resource:audit")
    @GetMapping("/audit")
    public String audit(ModelMap mmap)
    {
        return prefix + "/conducted/courseFile/audit";
    }

    //进入课题资源页
    @RequiresPermissions("system:resource:list")
    @GetMapping("/courseFile/list/{courseId}")
    public String courseFile(@PathVariable Long courseId,ModelMap mmap)
    {
        mmap.put("courseId",courseId);
        return prefix + "/conducted/courseFile/list";
    }

    //上传课题资源页
    @RequiresPermissions("system:resource:list")
    @GetMapping("/updateCourseResource")
    public String updateCourseResource()
    {
        return prefix + "/conducted/courseFile/updateCourseResource";
    }


    //进入课题信息页
    @RequiresPermissions("system:course:view")
    @GetMapping()
    public String course()
    {
        return prefix + "/course";
    }

    //进入课题的学员列表页
    @RequiresPermissions("system:course:view")
    @GetMapping("/studentList/{courseId}")
    public String studentList(@PathVariable Long courseId,ModelMap mmap)
    {
        mmap.put("courseId",courseId);
        return prefix + "/notStart/studentList";
    }

    //进入未发布页面
    @RequiresPermissions("system:teacher:view")
    @GetMapping("/notPublished")
    public String notPublished()
    {
        return prefix + "/notPublished/notPublished";
    }

    //进入未开课页面
    @RequiresPermissions("system:teacher:view")
    @GetMapping("/notStart")
    public String notStart()
    {
        return prefix + "/notStart/notStart";
    }

    //进入正在进行页面
    @RequiresPermissions("system:teacher:view")
    @GetMapping("/conducted")
    public String conducted()
    {
        return prefix + "/conducted/conducted";
    }

    //进入课题邀请学员页面
    @RequiresPermissions("system:student:list")
    @GetMapping("/inviteStudent/{courseId}")
    public String inviteStudent(@PathVariable Long courseId, ModelMap mmap)
    {
        mmap.put("courseId",courseId);
        return prefix + "/notStart/inviteStudent";
    }

    //进入小组列表页面
    @RequiresPermissions("system:group:list")
    @GetMapping("/asGroup/{courseId}")
    public String asGroup(@PathVariable Long courseId,ModelMap mmap)
    {
        //获取课题信息
        Course course = courseService.selectCourseByCourseId(courseId);
        mmap.put("course",course);
        if(ObjectUtils.isNotEmpty(studentService.getStudentByUserId(ShiroUtils.getUserId()))){
            mmap.put("currentUserId",studentService.getStudentByUserId(ShiroUtils.getUserId()).getStudentId());
        }else{
            mmap.put("currentUserId",-1);
        }
        return prefix + "/notStart/asGroup";
    }

    //根据课题的id获取所有学员
//    @RequiresPermissions("system:teacher:view")
    @PostMapping("/getStudentList/{courseId}")
    @ResponseBody
    public TableDataInfo getStudentList(SysUser sysUser,@PathVariable Long courseId,Student student)
    {
        startPage();
//        List<SysUser> list = courseService.getStudentList(sysUser,courseId);
        List<Student> studentsList = studentService.getStudentList(courseId, student);
        return getDataTable(studentsList);
    }

    //获取未发布的课题列表
    @RequiresPermissions("system:notpubulish:list")
    @PostMapping("/notPublishedList")
    @ResponseBody
    public TableDataInfo notPublished(Course course)
    {
        startPage();
        List<Course> list = courseService.selectNotPubulishCourseList(course);
        return getDataTable(list);
    }

    //获取未开课的课题列表
    @RequiresPermissions("system:course:list")
    @PostMapping("/notStartList")
    @ResponseBody
    public TableDataInfo notStartList(Course course)
    {
        startPage();
        List<Course> list = courseService.selectNotStartCourseList(course);
        return getDataTable(list);
    }

    //获取正在进行的课题列表
    @RequiresPermissions("system:course:list")
    @PostMapping("/conductedList")
    @ResponseBody
    public TableDataInfo conductedList(Course course)
    {
        startPage();
        List<Course> list = courseService.selectConductCourseList(course);
        return getDataTable(list);
    }

    /**
     * 根据小组id和学员id查询该学员是否在该小组中
     */
    @PostMapping("/checkInGroup/{groupId}/{studentId}")
    @ResponseBody
    public AjaxResult checkInGroup(@PathVariable Long groupId,@PathVariable Long studentId)
    {
        Student student = studentService.checkInGroup(groupId, studentId);
        if(ObjectUtils.isNotEmpty(student)){
            return success(student);
        }
        return error(AjaxResult.Type.ERROR,"不存在");
    }

    /**
     * 移除学员与课题关联
     */
    @RequiresPermissions("system:course:edit")
    @PostMapping("/removeStudent")
    @ResponseBody
    public AjaxResult removeStudent(Long courseId,Long[] uIds)
    {
        if(courseService.deleteCourseUserByuIds(courseId,uIds)>0){
            return success("移除成功！");
        }
        return error("移除异常！");
    }

    /**
     * 退出小组
     */
    @PostMapping("/exitGroup")
    @ResponseBody
    public AjaxResult exitGroup(GroupUser groupUser)
    {
        return toAjax(groupUserService.deleteGroupUser(groupUser));
    }

    /**
     * 加入小组
     */
    @PostMapping("/JoinGroup")
    @ResponseBody
    public AjaxResult JoinGroup(GroupUser groupUser,Long courseId)
    {
        if(groupUserService.JoinGroup(groupUser,courseId) == -1){
            return error("你已加入其他小组，如想加入该小组请先退出已加入的小组!");
        }
        return success("加入小组成功!");
    }


    /**
     * 查询课程列表
     */
    @RequiresPermissions("system:course:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(Course course)
    {
        startPage();
        List<Course> list = courseService.selectCourseList(course);
        PageDomain pageDomain = TableSupport.buildPageRequest();
        return PageUtils.mySetPage(list,pageDomain);
    }

    /**
     * 根据课题id查询课题信息
     */
    @PostMapping("/getCourseById/{courseId}")
    @ResponseBody
    public AjaxResult list(@PathVariable Long courseId)
    {
        Course course = courseService.selectCourseByCourseId(courseId);
        return success(course);
    }

    /**
     * 导出课程列表
     */

    @RequiresPermissions("system:course:export")
    @Log(title = "课程", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(Course course)
    {
        List<Course> list = courseService.selectCourseList(course);
        ExcelUtil<Course> util = new ExcelUtil<Course>(Course.class);
        return util.exportExcel(list, "课程数据");
    }

    /**
     * 新增课程
     */
    @GetMapping("/add")
    public String add(ModelMap mmap)
    {
        //查询所有拥有教员身份的用户
        List<SysUser> users = courseService.selectHavaTeachers();
        //获取登录用户
        SysUser sysUser = ShiroUtils.getSysUser();
        mmap.put("isAdmin",sysUser.isAdmin()||"administartor".equals(sysUser.getRoles().get(0).getRoleKey()));
        mmap.put("currentUser",sysUser);
        mmap.put("users",users);
        return prefix + "/add";
    }

    /**
     * 新增保存课程
     */
    @RequiresPermissions("system:course:add")
    @Log(title = "课程", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(Course course)
    {
        return toAjax(courseService.insertCourse(course));
    }

    /**
     * 修改课程
     */
    @RequiresPermissions("system:course:edit")
    @GetMapping("/edit/{courseId}")
    public String edit(@PathVariable("courseId") Long courseId, ModelMap mmap)
    {
        Course course = courseService.selectCourseByCourseId(courseId);
        //查询所有拥有教员身份的用户
        List<SysUser> users = courseService.selectHavaTeachers();
        //获取登录用户
        SysUser sysUser = ShiroUtils.getSysUser();
        mmap.put("isAdmin",sysUser.isAdmin());
        mmap.put("currentUser",sysUser);
        mmap.put("users",users);
        mmap.put("course", course);
        return prefix + "/edit";
    }

    /**
     * 修改保存课程
     */
    @RequiresPermissions("system:course:edit")
    @Log(title = "课程", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(Course course)
    {
        return toAjax(courseService.updateCourse(course));
    }

    /**
     * 删除课程
     */
    @RequiresPermissions("system:course:remove")
    @Log(title = "课程", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        if(courseService.validDeletion(ids)){
            return toAjax(courseService.deleteCourseByCourseIds(ids));
        }
        return error("删除失败，课程中还有学员存在!");
    }


}
