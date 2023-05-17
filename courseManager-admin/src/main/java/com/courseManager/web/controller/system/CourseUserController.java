package com.courseManager.web.controller.system;

import java.util.List;

import com.courseManager.common.core.domain.entity.SysUser;
import com.courseManager.common.core.page.PageDomain;
import com.courseManager.common.core.page.TableSupport;
import com.courseManager.common.utils.DateUtils;
import com.courseManager.common.utils.ShiroUtils;
import com.courseManager.common.utils.StringUtils;
import com.courseManager.common.utils.page.PageUtils;
import com.courseManager.system.domain.Course;
import com.courseManager.system.domain.Student;
import com.courseManager.system.domain.Teacher;
import com.courseManager.system.service.*;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import com.courseManager.common.annotation.Log;
import com.courseManager.common.enums.BusinessType;
import com.courseManager.system.domain.CourseUser;
import com.courseManager.common.core.controller.BaseController;
import com.courseManager.common.core.domain.AjaxResult;
import com.courseManager.common.utils.poi.ExcelUtil;
import com.courseManager.common.core.page.TableDataInfo;

/**
 * 学员选课
 * 
 * @author ruoyi
 * @date 2023-03-29
 */
@Controller
@RequestMapping("/system/cu")
public class CourseUserController extends BaseController
{
    private String prefix = "system/cu";

    @Autowired
    private ICourseUserService courseUserService;

    @Autowired
    private ICourseService courseService;

    @Autowired
    private IStudentService studentService;

    @Autowired
    private ITeacherService teacherService;

    @Autowired
    private IGroupService groupService;

    @Autowired
    private ISysUserService sysUserService;

    @RequiresPermissions("system:student:view")
    @GetMapping()
    public String courseList()
    {
        return prefix + "/courseList";
    }

    /**
     * 查询学员可选课题列表
     */
    @RequiresPermissions("system:student:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(Course course)
    {
//        startPage();
        List<Course> list = courseService.selectCheckCourseList(course);
        PageDomain pageDomain = TableSupport.buildPageRequest();

        return PageUtils.mySetPage(list,pageDomain);
    }


//    /**
//     * 导出课程用户关联表列表
//     */
//    @RequiresPermissions("system:student:export")
//    @Log(title = "课程用户关联表", businessType = BusinessType.EXPORT)
//    @PostMapping("/export")
//    @ResponseBody
//    public AjaxResult export(CourseUser courseUser)
//    {
//        List<CourseUser> list = courseUserService.selectCourseUserList(courseUser);
//        ExcelUtil<CourseUser> util = new ExcelUtil<CourseUser>(CourseUser.class);
//        return util.exportExcel(list, "课程用户关联表数据");
//    }

    /**
     * 选课详情
     */
    @RequiresPermissions("system:student:check")
    @GetMapping("/courseDetail/{courseId}")
    public String detail(@PathVariable Long courseId, ModelMap mmap)
    {
        Course course = courseService.getCourseDetails(courseId);
        if(StringUtils.isNotNull(course.getEndTime())&& StringUtils.isNotNull(course.getStartTime())){
            String time = DateUtils.timeDistance(course.getEndTime(), course.getStartTime());
            mmap.put("timeGap",time);
        }
        //查询当前课题的教员
        SysUser sysUser = sysUserService.selectUserById(course.getUserId());
        Teacher teacher = teacherService.getTeacherByIdNumber(sysUser.getLoginName());
        //查询当前课题的所有学员
        List<Student> studentList = studentService.getStudentList(courseId, new Student());
        mmap.put("teacher",teacher);
        mmap.put("studentList",studentList);
        mmap.put("course",course);
        return prefix + "/detail";
    }

    /**
     * 课题查看
     */
    @RequiresPermissions("system:student:check")
    @GetMapping("/courseReview/{courseId}")
    public String courseReview(@PathVariable Long courseId, ModelMap mmap)
    {
        Student student = studentService.getStudentByUserId(ShiroUtils.getUserId());
        mmap.put("courseId",courseId);

        if(!ObjectUtils.isEmpty(student)){
            mmap.put("currentStudent",student);
        }else{
            mmap.put("currentStudent",ShiroUtils.getUserId());
        }
        return prefix + "/courseReview";
    }

    /**
     * 未分组的课题学员
     */
    @PostMapping("/getCourseNotAssignment/{courseId}")
    @ResponseBody
    public TableDataInfo getCourseNotAssignment(@PathVariable Long courseId,Student student)
    {
        startPage();
        List<Student> notAssignment = studentService.getCourseNotAssignment(courseId,student);
        return getDataTable(notAssignment);
    }

    /**
     * 学员确认选课
     */
    @RequiresPermissions("system:student:check")
    @Log(title = "课题用户关联表", businessType = BusinessType.INSERT)
    @PostMapping("/cheked")
    @ResponseBody
    public AjaxResult ACKcheke(Course course)
    {
        if(!ShiroUtils.getSubject().hasRole("student")){
            return error("只有学员才能选课!");
        }
        int i = courseUserService.ACKcheke(course);
        if(i>0){
            return success("加入成功!");
        }
        return error("加入失败!");
    }

    /**
     * 新增保存课程用户关联表
     */
    @RequiresPermissions("system:student:add")
    @Log(title = "课程用户关联表", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(CourseUser courseUser)
    {
        return toAjax(courseUserService.insertCourseUser(courseUser));
    }

    /**
     * 修改课程用户关联表
     */
    @RequiresPermissions("system:student:edit")
    @GetMapping("/edit/{cuId}")
    public String edit(@PathVariable("cuId") Long cuId, ModelMap mmap)
    {
        CourseUser courseUser = courseUserService.selectCourseUserByCuId(cuId);
        mmap.put("courseUser", courseUser);
        return prefix + "/edit";
    }

    /**
     * 修改保存课程用户关联表
     */
    @RequiresPermissions("system:student:edit")
    @Log(title = "课程用户关联表", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(CourseUser courseUser)
    {
        return toAjax(courseUserService.updateCourseUser(courseUser));
    }

    /**
     * 删除课程用户关联表
     */
    @RequiresPermissions("system:student:remove")
    @Log(title = "课程用户关联表", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(courseUserService.deleteCourseUserByCuIds(ids));
    }
}
