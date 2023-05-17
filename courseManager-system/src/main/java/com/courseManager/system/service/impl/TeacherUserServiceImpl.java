package com.courseManager.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.courseManager.system.mapper.TeacherUserMapper;
import com.courseManager.system.domain.TeacherUser;
import com.courseManager.system.service.ITeacherUserService;
import com.courseManager.common.core.text.Convert;

/**
 * 教员与用户关联Service业务层处理
 * 
 * @author ruoyi
 * @date 2023-03-15
 */
@Service
public class TeacherUserServiceImpl implements ITeacherUserService 
{
    @Autowired
    private TeacherUserMapper teacherUserMapper;

    /**
     * 查询教员与用户关联
     * 
     * @param tuId 教员与用户关联主键
     * @return 教员与用户关联
     */
    @Override
    public TeacherUser selectTeacherUserByTuId(Long tuId)
    {
        return teacherUserMapper.selectTeacherUserByTuId(tuId);
    }

    /**
     * 查询教员与用户关联列表
     * 
     * @param teacherUser 教员与用户关联
     * @return 教员与用户关联
     */
    @Override
    public List<TeacherUser> selectTeacherUserList(TeacherUser teacherUser)
    {
        return teacherUserMapper.selectTeacherUserList(teacherUser);
    }

    /**
     * 新增教员与用户关联
     * 
     * @param teacherUser 教员与用户关联
     * @return 结果
     */
    @Override
    public int insertTeacherUser(TeacherUser teacherUser)
    {
        return teacherUserMapper.insertTeacherUser(teacherUser);
    }

    /**
     * 修改教员与用户关联
     * 
     * @param teacherUser 教员与用户关联
     * @return 结果
     */
    @Override
    public int updateTeacherUser(TeacherUser teacherUser)
    {
        return teacherUserMapper.updateTeacherUser(teacherUser);
    }

    /**
     * 批量删除教员与用户关联
     * 
     * @param tuIds 需要删除的教员与用户关联主键
     * @return 结果
     */
    @Override
    public int deleteTeacherUserByTuIds(String tuIds)
    {
        return teacherUserMapper.deleteTeacherUserByTuIds(Convert.toStrArray(tuIds));
    }

    /**
     * 删除教员与用户关联信息
     * 
     * @param tuId 教员与用户关联主键
     * @return 结果
     */
    @Override
    public int deleteTeacherUserByTuId(Long tuId)
    {
        return teacherUserMapper.deleteTeacherUserByTuId(tuId);
    }
}
