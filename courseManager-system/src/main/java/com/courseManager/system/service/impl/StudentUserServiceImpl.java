package com.courseManager.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.courseManager.system.mapper.StudentUserMapper;
import com.courseManager.system.domain.StudentUser;
import com.courseManager.system.service.IStudentUserService;
import com.courseManager.common.core.text.Convert;

/**
 * 学员用户关联Service业务层处理
 * 
 * @author ruoyi
 * @date 2023-03-29
 */
@Service
public class StudentUserServiceImpl implements IStudentUserService 
{
    @Autowired
    private StudentUserMapper studentUserMapper;

    /**
     * 查询学员用户关联
     * 
     * @param suId 学员用户关联主键
     * @return 学员用户关联
     */
    @Override
    public StudentUser selectStudentUserBySuId(Long suId)
    {
        return studentUserMapper.selectStudentUserBySuId(suId);
    }

    /**
     * 查询学员用户关联列表
     * 
     * @param studentUser 学员用户关联
     * @return 学员用户关联
     */
    @Override
    public List<StudentUser> selectStudentUserList(StudentUser studentUser)
    {
        return studentUserMapper.selectStudentUserList(studentUser);
    }

    /**
     * 新增学员用户关联
     * 
     * @param studentUser 学员用户关联
     * @return 结果
     */
    @Override
    public int insertStudentUser(StudentUser studentUser)
    {
        return studentUserMapper.insertStudentUser(studentUser);
    }

    /**
     * 修改学员用户关联
     * 
     * @param studentUser 学员用户关联
     * @return 结果
     */
    @Override
    public int updateStudentUser(StudentUser studentUser)
    {
        return studentUserMapper.updateStudentUser(studentUser);
    }

    /**
     * 批量删除学员用户关联
     * 
     * @param suIds 需要删除的学员用户关联主键
     * @return 结果
     */
    @Override
    public int deleteStudentUserBySuIds(String suIds)
    {
        return studentUserMapper.deleteStudentUserBySuIds(Convert.toStrArray(suIds));
    }

    /**
     * 删除学员用户关联信息
     * 
     * @param suId 学员用户关联主键
     * @return 结果
     */
    @Override
    public int deleteStudentUserBySuId(Long suId)
    {
        return studentUserMapper.deleteStudentUserBySuId(suId);
    }
}
