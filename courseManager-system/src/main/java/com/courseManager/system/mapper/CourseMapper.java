package com.courseManager.system.mapper;

import java.util.List;
import com.courseManager.system.domain.Course;
import com.courseManager.system.domain.Teacher;
import org.apache.ibatis.annotations.Param;

/**
 * 课程Mapper接口
 * 
 * @author ruoyi
 * @date 2023-03-12
 */
public interface CourseMapper 
{
    /**
     * 查询课程
     * 
     * @param courseId 课程主键
     * @return 课程
     */
    public Course selectCourseByCourseId(Long courseId);

    /**
     * 查询课程列表
     * 
     * @param course 课程
     * @return 课程集合
     */
    public List<Course> selectCourseList(Course course);

    /**
     * 新增课程
     * 
     * @param course 课程
     * @return 结果
     */
    public int insertCourse(Course course);

    /**
     * 修改课程
     * 
     * @param course 课程
     * @return 结果
     */
    public int updateCourse(Course course);

    /**
     * 删除课程
     * 
     * @param courseId 课程主键
     * @return 结果
     */
    public int deleteCourseByCourseId(Long courseId);

    /**
     * 批量删除课程
     * 
     * @param courseIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteCourseByCourseIds(String[] courseIds);

    /**
     * 根据用户id获取所创建的所有课题
     * @param course
     * @return
     */
    List<Course> selectCourseListByUserId(Course course);

    /**
     * 查询学员选课列表
     * @param course
     * @return
     */
    List<Course> selectCheckCourseList(Course course);

    /**
     * 查询当前学员已经选了的课题
     * @param course
     * @param userId
     * @return
     */
    List<Course> selectCheckedCourse(@Param("course") Course course, @Param("userId")Long userId);


    /**
     * 获取正在进行的课题列表
     * @param course
     * @return
     */
    List<Course> selectCourseListWithConduct(Course course);

    /**
     * 获取未开课状态的课题列表
     * @param course
     * @return
     */
    List<Course> selectCourseListWithNoStart(Course course);

    /**
     * 获取课题详情
     * @param courseId
     * @return
     */
    Course getCourseDetails(Long courseId);

    /**
     * 获取所有课题(包括删除的课题)
     * @param course
     * @return
     */
    List<Course> selectCourseListAnyDelete(Course course);
}
