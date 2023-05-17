package com.courseManager.system.mapper;

import java.util.List;

import com.courseManager.common.core.domain.entity.SysUser;
import com.courseManager.system.domain.Teacher;
import org.apache.ibatis.annotations.Param;

/**
 * 教员Mapper接口
 * 
 * @author ruoyi
 * @date 2023-03-13
 */
public interface TeacherMapper 
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
     * 删除教员
     * 
     * @param id 教员主键
     * @return 结果
     */
    public int deleteTeacherById(Long id);

    /**
     * 批量删除教员
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteTeacherByIds(String[] ids);

    /**
     * 根据证件号集查询出不可用的证件号
     * @param idNumbers
     * @return
     */
    Teacher checkNotUniqueWithIdNumber(@Param("idNumber") String idNumbers);

    /**
     * 验证手机号的唯一性
     * @param phoneNumber
     * @param id
     * @return
     */
    Teacher checkEditPhoneUnique(@Param("phoneNumber") String phoneNumber,@Param("teacherId") Long id);
    Teacher checkPhoneUnique(String phoneNumber);

    /**
     * 根据用户登录名查询教员
     * @param userLoginName
     * @return
     */
    Teacher getTeacherByIdNumber(String userLoginName);

    Teacher checkNotUniqueByTeacherId(Long id);

    /**
     * 验证当前用户登录名是否在教员表中存在
     * @param loginName
     * @return
     */
    Teacher checkUniqueByLoginName(String loginName);

    Teacher getTeacherByIdNumberOnEdit(@Param("loginName") String loginName,@Param("userId") Long userId);
}
