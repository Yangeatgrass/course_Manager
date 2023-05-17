package com.courseManager.system.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import com.github.pagehelper.PageHelper;
import com.courseManager.common.core.domain.Ztree;
import com.courseManager.common.core.domain.entity.SysDept;
import com.courseManager.common.core.domain.entity.SysUser;
import com.courseManager.common.utils.DateUtils;
import com.courseManager.common.utils.ShiroUtils;
import com.courseManager.common.utils.StringUtils;
import com.courseManager.system.domain.*;
import com.courseManager.system.mapper.*;
import com.courseManager.system.service.IStudentService;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.courseManager.system.service.ICourseService;
import com.courseManager.common.core.text.Convert;
import org.springframework.transaction.annotation.Transactional;

import static com.courseManager.common.utils.PageUtils.startPage;

/**
 * 课程Service业务层处理
 *
 * @author ruoyi
 * @date 2023-03-12
 */
@Service
public class CourseServiceImpl implements ICourseService {
    @Autowired
    private CourseMapper courseMapper;

    @Autowired
    private TeacherMapper teacherMapper;

    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private CourseUserMapper courseUserMapper;

    @Autowired
    private GroupMapper groupMapper;

    @Autowired
    private IStudentService studentService;

    @Autowired
    private GroupUserMapper groupUserMapper;

    /**
     * 查询课程
     *
     * @param courseId 课程主键
     * @return 课程
     */
    @Override
    public Course selectCourseByCourseId(Long courseId) {
        return courseMapper.selectCourseByCourseId(courseId);
    }

    /**
     * 查询课程列表
     *
     * @param course 课程
     * @return 课程
     */
    @Override
    public List<Course> selectCourseList(Course course) {
        List<Course> courses = new ArrayList<>();
        //判断是否为管理员
        SysUser sysUser = ShiroUtils.getSysUser(); //获取当前登录用户
        if (sysUser.isAdmin()||ShiroUtils.getSubject().hasRole("administartor")) {
            //获取所有
            courses = courseMapper.selectCourseList(course);
        } else {
            //获取所属自己的课题
            course.setUserId(sysUser.getUserId());
            courses = courseMapper.selectCourseListByUserId(course);
        }
        return courses;
    }

    /**
     * 新增课程
     *
     * @param course 课程
     * @return 结果
     */
    @Override
    public int insertCourse(Course course) {
        course.setCreateTime(DateUtils.getNowDate());
        course.setCreateBy(ShiroUtils.getLoginName());
        return courseMapper.insertCourse(course);
    }

    /**
     * 修改课程
     *
     * @param course 课程
     * @return 结果
     */
    @Override
    public int updateCourse(Course course) {
        course.setUpdateTime(DateUtils.getNowDate());
        course.setUpdateBy(ShiroUtils.getLoginName());
        return courseMapper.updateCourse(course);
    }

    /**
     * 批量删除课程
     *
     * @param courseIds 需要删除的课程主键
     * @return 结果
     */
    @Override
    public int deleteCourseByCourseIds(String courseIds) {
        return courseMapper.deleteCourseByCourseIds(Convert.toStrArray(courseIds));
    }

    /**
     * 删除课程信息
     *
     * @param courseId 课程主键
     * @return 结果
     */
    @Override
    public int deleteCourseByCourseId(Long courseId) {
        return courseMapper.deleteCourseByCourseId(courseId);
    }



    /**
     * 查询所有拥有教员身份的用户
     *
     * @return
     */
    @Override
    public List<SysUser> selectHavaTeachers() {
        return sysUserMapper.getUserWithTc();
    }

    /**
     * 查询学员选课列表
     * @param course
     * @return
     */
    @Override
    public List<Course> selectCheckCourseList(Course course) {
        //学员已选课程list
        Student student = studentService.getStudentByUserId(ShiroUtils.getUserId());
        List<Course> cs = null;
        if(ObjectUtils.isNotEmpty(student)){
            cs = courseMapper.selectCheckedCourse(course, student.getStudentId());
        }else{
            cs = courseMapper.selectCheckedCourse(course, ShiroUtils.getUserId());
        }
        //所有课题
        List<Course> courses = courseMapper.selectCheckCourseList(course);
        cs.stream().forEach(c -> {
            for (Course l : courses) {
                if (c.getCourseId().equals(l.getCourseId())) {
                    l.setChecked(1);
                }
            }
        });
        //获取服务器当前时间
        Date nowDate = DateUtils.getNowDate();
        //过滤掉时间过期和已经开始的课题
        List<Course> newList = courses.stream().filter(c1 -> {
            if (c1.getStartTime() != null && c1.getEndTime() != null) {
                //compareTo()方法的返回值，date1小于date2返回-1，date1大于date2返回1，相等返回0
                if (c1.getStatus().equals(1) && c1.getStartTime().compareTo(nowDate)>=0) {
                    return true;
                } else {
                    return false;
                }
            }
            return false;
        }).collect(Collectors.toList());

        //给课程状态(学生可见的状态)赋值
        newList.stream().forEach(l -> {
            if (l.getStartTime() != null && l.getEndTime() != null) {
                //计算开始时间与当前时间的差
                int differ = DateUtils.differentDaysByMillisecond(nowDate, l.getStartTime());
                //赋状态
                if (differ > 0) { //正在报名
                    l.setStatusPlus(0);
                }
                if (differ == 0) { //即将开始
                    l.setStatusPlus(1);
                }
            }
        });

        return newList;
    }

    /**
     * 查询当前学员已经选了的课题
     *
     * @param course
     * @return
     */
    @Override
    public List<Course> selectCheckedCourse(Course course) {
        List<Course> courses = null;
        if(ShiroUtils.getSubject().hasRole("student")) {
            courses = courseMapper.selectCheckedCourse(course, studentService.getStudentByUserId(ShiroUtils.getUserId()).getStudentId());
        }else {
            courses = courseMapper.selectCheckedCourse(course,ShiroUtils.getUserId());
        }
        return courses;
    }

    /**
     * 获取未开课状态的课题列表
     * @param course
     * @return
     */
    @Override
    public List<Course> selectNotStartCourseList(Course course) {
        course.setStatus(1);
        List<Course> courses = new ArrayList<>();
        //判断是否为管理员
        SysUser sysUser = ShiroUtils.getSysUser(); //获取当前登录用户
        if (sysUser.isAdmin()||ShiroUtils.getSubject().hasRole("administartor")) {
            //获取所有
            courses = courseMapper.selectCourseListWithNoStart(course);
        } else {
            //获取所属自己的课题
            course.setUserId(sysUser.getUserId());
            courses = courseMapper.selectCourseListWithNoStart(course);
        }
        return courses;
    }

    /**
     * 获取正在进行状态的课题列表
     * @param course
     * @return
     */
    @Override
    public List<Course> selectConductCourseList(Course course) {
        course.setStatus(2);
        List<Course> courses = new ArrayList<>();
        //判断是否为管理员
        SysUser sysUser = ShiroUtils.getSysUser(); //获取当前登录用户
        if (sysUser.isAdmin()||ShiroUtils.getSubject().hasRole("administartor")) {
            //获取所有
            courses = courseMapper.selectCourseListWithConduct(course);
        } else {
            //获取所属自己的课题
            course.setUserId(sysUser.getUserId());
            courses = courseMapper.selectCourseListWithConduct(course);
        }
        return courses;
    }

    /**
     * 获取当前课题未分配小组的学员
     * @param courseId
     * @return
     */
    @Override
    public List<SysUser> getCourseNotAssignment(Long courseId) {
        //获取当前课题所有不在小组的学员
        List<SysUser> users = sysUserMapper.getNotInGroupStudents(courseId);
        return users;
    }

    /**
     * 获取未发布的课题列表
     *
     * @param course
     * @return
     */
    @Override
    public List<Course> selectNotPubulishCourseList(Course course) {
        course.setStatus(0);
        List<Course> courses = new ArrayList<>();
        //判断是否为管理员
        SysUser sysUser = ShiroUtils.getSysUser(); //获取当前登录用户
        if (sysUser.isAdmin()||ShiroUtils.getSubject().hasRole("administartor")) {
            //获取所有
            courses = courseMapper.selectCourseList(course);
        } else {
            //获取所属自己的课题
            course.setUserId(sysUser.getUserId());
            courses = courseMapper.selectCourseList(course);
        }
        return courses;
    }

    /**
     * 根据课题id查询该课题下的所有学员
     * @param courseId
     * @return
     */
    @Override
    public List<SysUser> getStudentList(SysUser sysUser,Long courseId) {
        sysUser.setStatus("0");
        return sysUserMapper.getUserIdByCourseId(sysUser,courseId);
    }

    /**
     * 验证课题是否可以删除
     * @param ids
     * @return
     */
    @Override
    public boolean validDeletion(String ids){
        Long[] longs = Convert.toLongArray(ids);
        for (Long l : longs) {
            if(sysUserMapper.getUserIdByCourseId(new SysUser(),l).size()>0){
                return false;
            }
        }
        return true;
    }

    /**
     * 获取课题详情
     * @param courseId
     * @return
     */
    @Override
    public Course getCourseDetails(Long courseId) {
        return courseMapper.getCourseDetails(courseId);
    }

    @Override
    @Transactional
    public int deleteCourseUserByuIds(Long courseId, Long[] uIds) {
        //移除用户在与小组关联
        //查询该课题下的所有小组
        List<Long> longs = groupMapper.selectGroupIdsListByCourseId(courseId);
        //移除每个小组中的该学员
        if(longs.size()!=0){
            for (Long aLong : longs) {
                groupUserMapper.deleteGourpUserByuIds(aLong,uIds);
            }
        }
        return courseUserMapper.deleteCourseUserByuIds(courseId,uIds);
    }

}
