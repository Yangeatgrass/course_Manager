package com.courseManager.system.service;

import java.util.List;

import com.courseManager.common.core.domain.AjaxResult;
import com.courseManager.system.domain.Course;
import com.courseManager.system.domain.CourseUser;

/**
 * 课程用户关联表Service接口
 * 
 * @author ruoyi
 * @date 2023-03-13
 */
public interface ICourseUserService 
{
    /**
     * 查询课程用户关联表
     * 
     * @param cuId 课程用户关联表主键
     * @return 课程用户关联表
     */
    public CourseUser selectCourseUserByCuId(Long cuId);

    /**
     * 查询课程用户关联表列表
     * 
     * @param courseUser 课程用户关联表
     * @return 课程用户关联表集合
     */
    public List<CourseUser> selectCourseUserList(CourseUser courseUser);

    /**
     * 新增课程用户关联表
     * 
     * @param courseUser 课程用户关联表
     * @return 结果
     */
    public int insertCourseUser(CourseUser courseUser);

    /**
     * 修改课程用户关联表
     * 
     * @param courseUser 课程用户关联表
     * @return 结果
     */
    public int updateCourseUser(CourseUser courseUser);

    /**
     * 批量删除课程用户关联表
     * 
     * @param cuIds 需要删除的课程用户关联表主键集合
     * @return 结果
     */
    public int deleteCourseUserByCuIds(String cuIds);

    /**
     * 删除课程用户关联表信息
     * 
     * @param cuId 课程用户关联表主键
     * @return 结果
     */
    public int deleteCourseUserByCuId(Long cuId);


    /**
     * 学员确认选课
     * @param course
     * @return
     */
    int ACKcheke(Course course);

}
