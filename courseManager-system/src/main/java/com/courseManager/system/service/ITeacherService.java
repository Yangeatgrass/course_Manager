package com.courseManager.system.service;

import java.util.List;

import com.courseManager.common.core.domain.entity.SysUser;
import com.courseManager.system.domain.Teacher;

/**
 * 教员Service接口
 * 
 * @author ruoyi
 * @date 2023-03-13
 */
public interface ITeacherService 
{
    /**
     * 查询教员
     * 
     * @param id 教员主键
     * @return 教员
     */
    public Teacher selectTeacherById(Long id);

    /**
     * 查询教员列表
     * 
     * @param teacher 教员
     * @return 教员集合
     */
    public List<Teacher> selectTeacherList(Teacher teacher);

    /**
     * 新增教员
     * 
     * @param teacher 教员
     * @return 结果
     */
    public int insertTeacher(Teacher teacher);

    /**
     * 修改教员
     * 
     * @param teacher 教员
     * @return 结果
     */
    public int updateTeacher(Teacher teacher);

    /**
     * 批量删除教员
     * 
     * @param ids 需要删除的教员主键集合
     * @return 结果
     */
    public int deleteTeacherByIds(String ids);

    /**
     * 删除教员信息
     * 
     * @param id 教员主键
     * @return 结果
     */
    public int deleteTeacherById(Long id);



    /**
     * 批量导入教员
     * @param teachers
     * @param updateSupport
     * @param loginName
     * @return
     */
    String importTeacher(List<Teacher> teachers, boolean updateSupport, String loginName);

    /**
     * 教员注册到用户中
     * @param teacherIds
     * @return
     */
    String registerTeacher(Integer[] teacherIds);

    /**
     * 编辑时验证手机号的唯一性
     * @param phoneNumber
     * @param id
     * @return
     */
    Teacher checkEditPhoneUnique(String phoneNumber, Long id);

    /**
     * 新增时验证手机号的唯一性
     * @param phoneNumber
     * @return
     */
    Teacher checkPhoneUnique(String phoneNumber);

    /**
     * 根据用户登录名查询教员
     * @param userLoginName
     * @return
     */
    Teacher getTeacherByIdNumber(String userLoginName);

    int changeStatus(Teacher teacher);

    boolean validDeletion(String ids);
}
