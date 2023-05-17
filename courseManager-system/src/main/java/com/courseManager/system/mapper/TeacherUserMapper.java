package com.courseManager.system.mapper;

import java.util.List;
import com.courseManager.system.domain.TeacherUser;
import org.apache.ibatis.annotations.Param;

/**
 * 教员与用户关联Mapper接口
 * 
 * @author ruoyi
 * @date 2023-03-15
 */
public interface TeacherUserMapper 
{
    /**
     * 查询教员与用户关联
     * 
     * @param tuId 教员与用户关联主键
     * @return 教员与用户关联
     */
    public TeacherUser selectTeacherUserByTuId(Long tuId);

    /**
     * 查询教员与用户关联列表
     * 
     * @param teacherUser 教员与用户关联
     * @return 教员与用户关联集合
     */
    public List<TeacherUser> selectTeacherUserList(TeacherUser teacherUser);

    /**
     * 新增教员与用户关联
     * 
     * @param teacherUser 教员与用户关联
     * @return 结果
     */
    public int insertTeacherUser(TeacherUser teacherUser);

    /**
     * 修改教员与用户关联
     * 
     * @param teacherUser 教员与用户关联
     * @return 结果
     */
    public int updateTeacherUser(TeacherUser teacherUser);

    /**
     * 删除教员与用户关联
     * 
     * @param tuId 教员与用户关联主键
     * @return 结果
     */
    public int deleteTeacherUserByTuId(Long tuId);

    /**
     * 批量删除教员与用户关联
     * 
     * @param tuIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteTeacherUserByTuIds(String[] tuIds);

    /**
     * 插入选择的教员身份
     * @param userId
     * @param teacherId
     */
    void insertTeacherWithUser(@Param("userId") Long userId, @Param("teacherId") Long teacherId);

    /**
     * 根据userId删除教员与用户关联
     * @param userId
     */
    void deleteTeacherUserByUserId(Long userId);

    /**
     * 更新角色与教员关联表
     * @param userId
     * @param teacherId
     */
    void updateTeacherWithUser(@Param("userId") Long userId, @Param("teacherId") Long teacherId);

    /**
     * 批量删除用户与教员关联
     * @param userIds
     */
    void deleteTeacherUser(Long[] userIds);


    Long getTeacherIdByUserId(Long userId);

    /**
     * 根据教员id查询关联表id
     * @param id
     * @return
     */
    Long getTuIdByTeacherId(Long id);
}
