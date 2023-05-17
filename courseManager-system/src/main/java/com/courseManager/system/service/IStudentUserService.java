package com.courseManager.system.service;

import java.util.List;
import com.courseManager.system.domain.StudentUser;

/**
 * 学员用户关联Service接口
 * 
 * @author ruoyi
 * @date 2023-03-29
 */
public interface IStudentUserService 
{
    /**
     * 查询学员用户关联
     * 
     * @param suId 学员用户关联主键
     * @return 学员用户关联
     */
    public StudentUser selectStudentUserBySuId(Long suId);

    /**
     * 查询学员用户关联列表
     * 
     * @param studentUser 学员用户关联
     * @return 学员用户关联集合
     */
    public List<StudentUser> selectStudentUserList(StudentUser studentUser);

    /**
     * 新增学员用户关联
     * 
     * @param studentUser 学员用户关联
     * @return 结果
     */
    public int insertStudentUser(StudentUser studentUser);

    /**
     * 修改学员用户关联
     * 
     * @param studentUser 学员用户关联
     * @return 结果
     */
    public int updateStudentUser(StudentUser studentUser);

    /**
     * 批量删除学员用户关联
     * 
     * @param suIds 需要删除的学员用户关联主键集合
     * @return 结果
     */
    public int deleteStudentUserBySuIds(String suIds);

    /**
     * 删除学员用户关联信息
     * 
     * @param suId 学员用户关联主键
     * @return 结果
     */
    public int deleteStudentUserBySuId(Long suId);
}
