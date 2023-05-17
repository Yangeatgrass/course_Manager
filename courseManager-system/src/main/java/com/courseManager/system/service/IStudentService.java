package com.courseManager.system.service;

import java.util.List;

import com.courseManager.common.core.domain.entity.SysUser;
import com.courseManager.system.domain.Student;
import com.courseManager.system.domain.Teacher;

/**
 * 学员Service接口
 * 
 * @author xiaoyang
 * @date 2023-03-17
 */
public interface IStudentService 
{
    /**
     * 查询学员
     * 
     * @param studentId 学员主键
     * @return 学员
     */
    public Student selectStudentByStudentId(Long studentId);

    /**
     * 查询学员列表
     * 
     * @param student 学员
     * @return 学员集合
     */
    public List<Student> selectStudentList(Student student);

    /**
     * 新增学员
     * 
     * @param student 学员
     * @return 结果
     */
    public int insertStudent(Student student);

    /**
     * 修改学员
     * 
     * @param student 学员
     * @return 结果
     */
    public int updateStudent(Student student);

    /**
     * 批量删除学员
     * 
     * @param studentIds 需要删除的学员主键集合
     * @return 结果
     */
    public int deleteStudentByStudentIds(String studentIds);

    /**
     * 删除学员信息
     * 
     * @param studentId 学员主键
     * @return 结果
     */
    public int deleteStudentByStudentId(Long studentId);

    /**
     * 批量导入学员
     * @param students
     * @param updateSupport
     * @param loginName
     * @return
     */
    String importTeacher(List<Student> students, boolean updateSupport, String loginName);

    /**
     * 注册学员
     * @param studentIds
     * @return
     */
    String registerTeacher(Integer[] studentIds);

    /**
     * 学员状态修改
     * @param student
     * @return
     */
    int changeStatus(Student student);

    /**
     * 根据登录名获取用户
     * @param userLoginName
     * @return
     */
    Student getStudentByIdNumber(String userLoginName);

    /**
     * 根据课题id查询没有加入该课题的学员
     * @param courseId
     * @param student
     * @param sysUser
     * @return
     */
    List<Student> studentListByInvite(Long courseId, Student student, SysUser sysUser);

    /**
     * 获取加入了该课题的学员
     * @param courseId
     * @param student
     * @return
     */
    List<Student> getStudentList(Long courseId, Student student);

    /**
     * 根据当前登录用户查询到学员信息
     * @param userId
     * @return
     */
    Student getStudentByUserId(Long userId);

    /**
     * 获取当前课题未分配小组的学员
     * @param courseId
     * @return
     */
    List<Student> getCourseNotAssignment(Long courseId,Student student);

    /**
     * 根据小组id查询到在该小组的学员
     * @param groupId
     * @return
     */
    List<Student> selectInGroupStudent(Long groupId,Student student);

    /**
     * 根据学员学号查询学员
     * @param username
     * @return
     */
    Student getStudentBySNumber(String username);

    Student checkInGroup(Long groupId, Long studentId);
}
