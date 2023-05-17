package com.courseManager.system.mapper;

import java.util.List;
import com.courseManager.system.domain.StudentUser;

/**
 * 学员用户关联Mapper接口
 * 
 * @author ruoyi
 * @date 2023-03-29
 */
public interface StudentUserMapper 
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
     * 删除学员用户关联
     * 
     * @param suId 学员用户关联主键
     * @return 结果
     */
    public int deleteStudentUserBySuId(Long suId);

    /**
     * 批量删除学员用户关联
     * 
     * @param suIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteStudentUserBySuIds(String[] suIds);

    void deleteStudentUser(Long[] userIds);

    Long getStudentIdByUserId(Long userId);

    /**
     * 根据学员id查询该学员的关联表id
     * @param studentId
     * @return
     */
    Long getsuIdByStudentId(Long studentId);
}
