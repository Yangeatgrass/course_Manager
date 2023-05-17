package com.courseManager.quartz.task;

import com.courseManager.common.utils.DateUtils;
import com.courseManager.common.utils.StringUtils;
import com.courseManager.system.domain.Course;
import com.courseManager.system.mapper.CourseMapper;
import com.courseManager.system.service.ICourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.function.Predicate;

/**
 * 课题定时任务
 */
@Component("CourseTask")
public class CourseTask {

    @Autowired
    private CourseMapper courseMapper;

    //获取当前服务器时间
    Date now = DateUtils.getNowDate();

    //自动开课与结课任务
    @Transactional //开启事务
    public void autoStartCourse() {
        //获取所有未开课状态的课题
        Course course = new Course();
        course.setStatus(1);
        List<Course> courses = courseMapper.selectCourseList(course);
        courses.stream().forEach(c -> {
            //开课校验
            if (autoConductV(c, now)) {
                //满足条件，修改课题状态为开课
                c.setStatus(2);
                courseMapper.updateCourse(c);
            }
        });

        //获取所有已开课的课题
        Course c2 = new Course();
        course.setStatus(2);
        List<Course> courses2 = courseMapper.selectCourseList(c2);
        courses2.stream().forEach(c -> {
            //结课
            if (c.getStartTime().compareTo(now) < 0 && c.getEndTime().compareTo(now) < 0) {
                c.setStatus(3);
                courseMapper.updateCourse(c);
            }
        });
//        System.err.println("课题定时任务..."+ now);
    }

    //自动开课校验
    public static boolean autoConductV(Course course, Date now) {
        //未到开课时间不予开课
        if (course.getStartTime().compareTo(now) > 0) {
            return false;
        }
        //不满开课人数不予开课
        if (course.getCheckedSum() < course.getStartNum()) {
            //TODO 此处可能需要发通知给教员之类
            System.err.println("课题：" + course.getCourseName() + " ,参加学员较少，未满足开课要求，自动开课失败！");
            return false;
        }
        //没有设置小组不予开课
        if (course.getGroupSum() == 0) {
            //TODO 此处可能需要发通知教员之类操作
            System.err.println("课题：" + course.getCourseName() + " ,没有小组，未满足开课要求，自动开课失败！");
            return false;
        }
        //TODO 更多校验
        return true;
    }

}
