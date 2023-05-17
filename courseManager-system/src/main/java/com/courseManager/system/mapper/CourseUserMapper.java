package com.courseManager.system.mapper;

import java.util.List;

import com.courseManager.system.domain.Course;
import com.courseManager.system.domain.CourseUser;
import org.apache.ibatis.annotations.Param;

/**
 * 课程用户关联表Mapper接口
 * 
 * @author ruoyi
 * @date 2023-03-13
 */
public interface CourseUserMapper 
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
     * 删除课程用户关联表
     * 
     * @param cuId 课程用户关联表主键
     * @return 结果
     */
    public int deleteCourseUserByCuId(Long cuId);

    /**
     * 批量删除课程用户关联表
     * 
     * @param cuIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteCourseUserByCuIds(String[] cuIds);

    /**
     * 批量保存用户与课题关联
     * @param courseUsers
     * @return
     */
    int insertCourseUserBatchList(List<CourseUser> courseUsers);

    /**
     * 批量保存用户与课题关联
     * @param courseId
     * @param userIds
     * @return
     */
    int insertCourseUserBatch(@Param("courseId") Long courseId,@Param("userIds") Long[] userIds);

    /**
     * 移除学员与课题关联
     * @return
     * @param courseId
     * @param uIds
     */
    int deleteCourseUserByuIds(@Param("courseId") Long courseId,@Param("uIds") Long[] uIds);
}
