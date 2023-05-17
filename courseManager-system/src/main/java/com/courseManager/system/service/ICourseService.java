package com.courseManager.system.service;

import java.util.List;

import com.courseManager.common.core.domain.Ztree;
import com.courseManager.common.core.domain.entity.SysUser;
import com.courseManager.system.domain.Course;
import com.courseManager.system.domain.Teacher;

/**
 * 课程Service接口
 * 
 * @author ruoyi
 * @date 2023-03-12
 */
public interface ICourseService 
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
     * 批量删除课程
     * 
     * @param courseIds 需要删除的课程主键集合
     * @return 结果
     */
    public int deleteCourseByCourseIds(String courseIds);

    /**
     * 删除课程信息
     * 
     * @param courseId 课程主键
     * @return 结果
     */
    public int deleteCourseByCourseId(Long courseId);

    /**
     * 查询所有拥有教员身份的用户
     * @return
     */
    List<SysUser> selectHavaTeachers();

    /**
     * 查询学员选课列表
     * @param course
     * @return
     */
    List<Course> selectCheckCourseList(Course course);

    /**
     * 查询当前学员已经选了的课题
     * @param course
     * @return
     */
    List<Course> selectCheckedCourse(Course course);

    /**
     * 获取未开课的课题列表
     * @param course
     * @return
     */
    List<Course> selectNotStartCourseList(Course course);

    /**
     * 获取正在进行的课题列表
     * @param course
     * @return
     */
    List<Course> selectConductCourseList(Course course);

    /**
     * 获取当前课题未分配小组的学员
     * @param courseId
     */
    List<SysUser> getCourseNotAssignment(Long courseId);

    /**
     * 获取未发布的课题列表
     * @param course
     * @return
     */
    List<Course> selectNotPubulishCourseList(Course course);

    /**
     * 根据课题的id获取所有学员
     * @param courseId
     * @return
     */
    List<SysUser> getStudentList(SysUser sysUser,Long courseId);

    /**
     * 验证课题是否可以删除
     * @param ids
     * @return
     */
    boolean validDeletion(String ids);

    /**
     * 获取课题详情
     * @param courseId
     * @return
     */
    Course getCourseDetails(Long courseId);

    int deleteCourseUserByuIds(Long courseId, Long[] uIds);
}
