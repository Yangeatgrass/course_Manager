package com.courseManager.system.mapper;

import java.util.List;

import com.courseManager.common.core.domain.entity.SysUser;
import com.courseManager.system.domain.Student;
import com.courseManager.system.domain.Teacher;
import org.apache.ibatis.annotations.Param;

/**
 * 学员Mapper接口
 * 
 * @author xiaoyang
 * @date 2023-03-17
 */
public interface StudentMapper 
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
     * 删除学员
     * 
     * @param studentId 学员主键
     * @return 结果
     */
    public int deleteStudentByStudentId(Long studentId);

    /**
     * 批量删除学员
     * 
     * @param studentIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteStudentByStudentIds(String[] studentIds);


    /**
     * 查询学员列表【教员视角】
     * @param student
     * @return
     */
    List<Student> selectStudentListTeacherView(Student student);

    /**
     * 验证学员的学号是否已存在学员表中
     * @param sNumber
     * @return
     */
    Student checkNotUniqueWithIdNumber(String sNumber);

    /**
     * 根据学员id查询该学员是否已注册
     * @param longValue
     * @return
     */
    Student checkNotUniqueByStudentId(long longValue);

    /**
     * 根据登录名获取用户
     * @param userLoginName
     * @return
     */
    Student getStudentByIdNumber(@Param("userLoginName") String userLoginName);

    /**
     * 根据课题id查询没有加入该课题的学员
     * @param courseId
     * @param student
     * @param sysUser
     * @return
     */
    List<Student> studentListByInvite(@Param("courseId") Long courseId,
                                      @Param("student")Student student,
                                      @Param("sysUser")SysUser sysUser);

    /**
     * 获取加入了该课题的学员
     * @param courseId
     * @param student
     * @return
     */
    List<Student> getStudentList(@Param("courseId") Long courseId,
                                 @Param("student") Student student);

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
    List<Student> getCourseNotAssignment(@Param("courseId") Long courseId,@Param("student") Student student);

    /**
     * 根据小组id查询到在该小组的学员
     * @param groupId
     * @return
     */
    List<Student> selectInGroupStudent(@Param("groupId") Long groupId,@Param("student") Student student);

    /**
     * 根据学员学号查询学员
     * @param username
     * @return
     */
    Student getStudentBySNumber(String username);

    Student getStudentByIdNumberOnEdit(@Param("loginName") String loginName,@Param("userId") Long userId);

    Student checkInGroup(@Param("groupId") Long groupId,@Param("studentId") Long studentId);
}
