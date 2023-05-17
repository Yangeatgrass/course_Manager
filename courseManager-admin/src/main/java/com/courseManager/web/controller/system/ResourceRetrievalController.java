package com.courseManager.web.controller.system;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.courseManager.common.annotation.Log;
import com.courseManager.common.core.controller.BaseController;
import com.courseManager.common.core.domain.AjaxResult;
import com.courseManager.common.core.domain.entity.SysUser;
import com.courseManager.common.core.page.PageDomain;
import com.courseManager.common.core.page.TableDataInfo;
import com.courseManager.common.core.page.TableSupport;
import com.courseManager.common.enums.BusinessType;
import com.courseManager.common.utils.StringUtils;
import com.courseManager.common.utils.page.PageUtils;
import com.courseManager.common.utils.sql.SqlUtil;
import com.courseManager.system.domain.Course;
import com.courseManager.system.domain.Group;
import com.courseManager.system.domain.ResourceFile;
import com.courseManager.system.domain.Student;
import com.courseManager.system.mapper.CourseMapper;
import com.courseManager.system.mapper.GroupMapper;
import com.courseManager.system.mapper.SysUserMapper;
import com.courseManager.system.service.IResourceFileService;
import com.courseManager.system.service.IResourcePublicFileService;
import com.courseManager.system.service.IStudentService;
import com.courseManager.system.service.ISysUserService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 资源检索controller
 *
 * @author ruoyi
 * @date 2023-04-07
 */
@Controller
@RequestMapping("/system/retrieval")
public class ResourceRetrievalController extends BaseController {

    private String prefix = "system/retrieval";

    private static final Logger log = LoggerFactory.getLogger(ResourceRetrievalController.class);

    @Autowired
    private IResourceFileService resourceFileService;

    @Autowired
    private IResourcePublicFileService publicFileService;

    @Autowired
    private ISysUserService sysUserService;

    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private CourseMapper courseMapper;

    @Autowired
    private GroupMapper groupMapper;

    @Autowired
    private IStudentService studentService;

    /**
     * 进入全局检索页
     *
     * @return
     */
    @RequiresPermissions("system:retrieval:view")
    @GetMapping("/global")
    public String retrieval() {
        return prefix + "/global";
    }

    /**
     * 进入资源全局统计
     *
     * @return
     */
    @RequiresPermissions("system:statistics:view")
    @GetMapping("/statistics")
    public String statistics(ModelMap mmap) {
        //获取所有课题
        Course course = new Course();
        Map<String,Object> map = course.getParams();
        map.put("NotStauts",0);
        course.setParams(map);
        List<Course> courses = courseMapper.selectCourseList(course);
        mmap.put("courses",courses);
        return prefix + "/statistics";
    }

    /**
     * 根据课题id查询所有的小组
     */
    @RequiresPermissions("system:statistics:list")
    @PostMapping("/getGroupsByCourseId/{courseId}")
    @ResponseBody
    public AjaxResult getGroupsByCourseId(@PathVariable Long courseId) {
        Group group = new Group();
        group.setCourseId(courseId);
        return success(groupMapper.selectGroupList(group));
    }

    /**
     * 根据小组id查询所有的学员
     */
    @RequiresPermissions("system:statistics:list")
    @PostMapping("/getStudentsByGroupId/{groupId}")
    @ResponseBody
    public AjaxResult getStudentsByGroupId(@PathVariable Long groupId,Student student) {
        List<Student> students = studentService.selectInGroupStudent(groupId,student);
        return success(students);
    }

    /**
     * 根据小组id查询所有的学员【用于选课中心的查看】
     */
    @PostMapping("/getStudentsByGroupIdToReview/{groupId}")
    @ResponseBody
    public TableDataInfo getStudentsByGroupIdToReview(@PathVariable Long groupId,Student student) {
        startPage();
        List<Student> students = studentService.selectInGroupStudent(groupId,student);
        return getDataTable(students);
    }

    /**
     * 资源全局统计
     *
     * @return
     */
    @RequiresPermissions("system:statistics:list")
    @PostMapping("/statisticsList")
    @ResponseBody
    public AjaxResult statisticsList(Course course, ResourceFile resourceFile,
                                     Group group,SysUser sysUser) {
        Map<String,Map> result = resourceFileService.getStatisticsList(course,resourceFile,group,sysUser);
        return success(result);
    }

    /**
     * 全局检索列表
     *
     * @return
     */
    @RequiresPermissions("system:retrieval:list")
    @PostMapping("/globalList")
    @ResponseBody
    public TableDataInfo retrievalList(Course course, ResourceFile resourceFile) {
        List<ResourceFile> retrievalList = resourceFileService.getRetrievalList(course, resourceFile);

        PageDomain pageDomain = TableSupport.buildPageRequest();
        Stream<ResourceFile> sorted = null;
        //排序
        if (StringUtils.equals(pageDomain.getOrderByColumn(),"createTime")) {
            //按时间排序
            if (StringUtils.equals(pageDomain.getIsAsc(),"desc")) {
                sorted = retrievalList.stream().sorted(Comparator.comparing(ResourceFile::getCreateTime));
            }else{
                sorted =  retrievalList.stream().sorted(Comparator.comparing(ResourceFile::getCreateTime,Comparator.reverseOrder()));
            }
        }
        if (StringUtils.equals(pageDomain.getOrderByColumn(),"courseName")) {
            //按课程名排序
            if (StringUtils.equals(pageDomain.getIsAsc(),"desc")) {
                sorted = retrievalList.stream().sorted(Comparator.comparing(ResourceFile::getCourseName));
            }else{
                sorted = retrievalList.stream().sorted(Comparator.comparing(ResourceFile::getCourseName,Comparator.reverseOrder()));
            }
        }
        List<ResourceFile> collect = sorted.collect(Collectors.toList());

        return PageUtils.mySetPage(collect,pageDomain);
    }



}
