package com.courseManager.web.controller.system;

import com.courseManager.common.annotation.Log;
import com.courseManager.common.core.controller.BaseController;
import com.courseManager.common.core.domain.AjaxResult;
import com.courseManager.common.core.domain.entity.SysUser;
import com.courseManager.common.core.page.PageDomain;
import com.courseManager.common.core.page.TableDataInfo;
import com.courseManager.common.core.page.TableSupport;
import com.courseManager.common.enums.BusinessType;
import com.courseManager.common.utils.DateUtils;
import com.courseManager.common.utils.ShiroUtils;
import com.courseManager.common.utils.StringUtils;
import com.courseManager.common.utils.page.PageUtils;
import com.courseManager.system.domain.Course;
import com.courseManager.system.domain.CourseUser;
import com.courseManager.system.domain.ResourceFile;
import com.courseManager.system.service.ICourseService;
import com.courseManager.system.service.ICourseUserService;
import com.courseManager.system.service.ISysUserService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Stream;

/**
 * 学员的课题
 * 
 * @author ruoyi
 * @date 2023-03-29
 */
@Controller
@RequestMapping("/system/studentCourse")
public class StudentCourseController extends BaseController
{
    private String prefix = "system/studentCourse";

    @Autowired
    private ICourseUserService courseUserService;

    @Autowired
    private ICourseService courseService;

    @Autowired
    private ISysUserService isysUserService;


    /**
     * 进入我的课题
     * @return
     */
    @RequiresPermissions("system:checked:view")
    @GetMapping()
    public String accessChecked()
    {
        return prefix + "/ckList";
    }

    /**
     * 进入我的小组
     * @return
     */
    @RequiresPermissions("system:group:list")
    @GetMapping("/accessGroup/{courseId}")
    public String accessGroup(@PathVariable Long courseId,ModelMap mmap)
    {
        mmap.put("courseId",courseId);
        return prefix + "/accessGroup";
    }

    /**
     * 查询学员已选课题列表
     */
    @RequiresPermissions("system:checked:checkedList")
    @PostMapping("/checkedList")
    @ResponseBody
    public TableDataInfo checkedList(Course course)
    {
//        startPage();
        List<Course> list = courseService.selectCheckedCourse(course);
        PageDomain pageDomain = TableSupport.buildPageRequest();
        Stream<ResourceFile> sorted = null;
        return PageUtils.mySetPage(list,pageDomain);
    }

    /**
     * 获取当前用户的课题的小组所有成员
     */
    @RequiresPermissions("system:group:list")
    @PostMapping("/groupUsers/{courseId}")
    @ResponseBody
    public TableDataInfo groupUsers(@PathVariable Long courseId)
    {
        startPage();
        List<SysUser> list = isysUserService.groupUsers(courseId);
        return getDataTable(list);
    }


}
