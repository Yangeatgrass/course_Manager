package com.courseManager.system.mapper;

import java.util.List;
import com.courseManager.common.core.domain.entity.SysUser;
import com.courseManager.system.domain.Teacher;
import org.apache.ibatis.annotations.Param;

/**
 * 用户表 数据层
 * 
 * @author ruoyi
 */
public interface SysUserMapper
{
    /**
     * 根据条件分页查询用户列表
     * 
     * @param sysUser 用户信息
     * @return 用户信息集合信息
     */
    public List<SysUser> selectUserList(SysUser sysUser);

    /**
     * 根据条件分页查询已配用户角色列表
     * 
     * @param user 用户信息
     * @return 用户信息集合信息
     */
    public List<SysUser> selectAllocatedList(SysUser user);

    /**
     * 根据条件分页查询未分配用户角色列表
     * 
     * @param user 用户信息
     * @return 用户信息集合信息
     */
    public List<SysUser> selectUnallocatedList(SysUser user);

    /**
     * 通过用户名查询用户
     * 
     * @param userName 用户名
     * @return 用户对象信息
     */
    public SysUser selectUserByLoginName(String userName);

    /**
     * 通过手机号码查询用户
     * 
     * @param phoneNumber 手机号码
     * @return 用户对象信息
     */
    public SysUser selectUserByPhoneNumber(String phoneNumber);

    /**
     * 通过邮箱查询用户
     * 
     * @param email 邮箱
     * @return 用户对象信息
     */
    public SysUser selectUserByEmail(String email);

    /**
     * 通过用户ID查询用户
     * 
     * @param userId 用户ID
     * @return 用户对象信息
     */
    public SysUser selectUserById(Long userId);

    /**
     * 通过用户ID删除用户
     * 
     * @param userId 用户ID
     * @return 结果
     */
    public int deleteUserById(Long userId);

    /**
     * 批量删除用户信息
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteUserByIds(Long[] ids);

    /**
     * 修改用户信息
     * 
     * @param user 用户信息
     * @return 结果
     */
    public int updateUser(SysUser user);

    /**
     * 新增用户信息
     * 
     * @param user 用户信息
     * @return 结果
     */
    public int insertUser(SysUser user);

    /**
     * 校验用户名称是否唯一
     * 
     * @param loginName 登录名称
     * @return 结果
     */
    public SysUser checkLoginNameUnique(String loginName);

    /**
     * 校验手机号码是否唯一
     *
     * @param phonenumber 手机号码
     * @return 结果
     */
    public SysUser checkPhoneUnique(String phonenumber);
    public SysUser checkPhoneUniqueByLoginName(@Param("phonenumber") String phonenumber,@Param("loginName") String loginName);

    /**
     * 校验email是否唯一
     *
     * @param email 用户邮箱
     * @return 结果
     */
    public SysUser checkEmailUnique(String email);

    /**
     * 查询拥有教员身份的用户
     * @return
     */
    List<SysUser> getUserWithTc();


    /**
     * 根据教员id查询关联的用户
     * @return
     */
    SysUser selectUserByTeacherId(Long teacherId);

    /**
     * 根据教员的证件号判断是否已存在
     * @param idNumber
     * @return
     */
    SysUser checkNotUniqueWithIdNumber(@Param("idNumber") String idNumber);

    /**
     * 根据课题id获取选该课题的所有用户
     * @param courseId
     * @return
     */
    List<SysUser> getUserIdByCourseId(@Param("sysUser") SysUser sysUser,@Param("courseId") Long courseId);

    /**
     * 获取当前课题的所有不在小组中的学员
     * @param courseId
     * @return
     */
    List<SysUser> getNotInGroupStudents(Long courseId);

    /**
     * 根据小组id查询到在该小组的学员
     * @param groupId
     * @return
     */
    List<SysUser> selectInGroupStudent(Long groupId);

    //根据课题id和用户id 查询该用户在该课题小组的学员
    List<SysUser> getGroupUsersByCourseIdAndUserId(@Param("courseId") Long courseId,@Param("userId") Long userId);

    /**
     * 根据课题id查询没有加入该课题的学员
     * @param courseId
     * @param sysUser
     * @return
     */
    List<SysUser> studentListByInvite(@Param("courseId") Long courseId,@Param("sysUser") SysUser sysUser);

    /**
     * 根据文件id查询拥有该文件权限的用户
     * @param sysUser
     * @param fId
     * @return
     */
    List<SysUser> getusersByAssignPublic(@Param("sysUser") SysUser sysUser,@Param("fId") Long fId);

    /**
     * 获取所有学员
     * @return
     */
    List<SysUser> getUsersWithStudent(@Param("sysUser") SysUser sysUser);

}
