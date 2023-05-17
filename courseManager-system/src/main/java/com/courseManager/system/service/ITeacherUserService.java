package com.courseManager.system.service;

import java.util.List;
import com.courseManager.system.domain.TeacherUser;

/**
 * 教员与用户关联Service接口
 * 
 * @author ruoyi
 * @date 2023-03-15
 */
public interface ITeacherUserService 
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
     * 批量删除教员与用户关联
     * 
     * @param tuIds 需要删除的教员与用户关联主键集合
     * @return 结果
     */
    public int deleteTeacherUserByTuIds(String tuIds);

    /**
     * 删除教员与用户关联信息
     * 
     * @param tuId 教员与用户关联主键
     * @return 结果
     */
    public int deleteTeacherUserByTuId(Long tuId);
}
