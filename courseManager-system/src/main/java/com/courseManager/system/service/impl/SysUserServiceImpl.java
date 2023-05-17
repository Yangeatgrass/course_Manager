package com.courseManager.system.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;
import javax.validation.Validator;

import com.courseManager.common.core.page.PageDomain;
import com.courseManager.common.core.page.TableDataInfo;
import com.courseManager.common.core.page.TableSupport;
import com.courseManager.common.utils.PageUtils;
import com.courseManager.system.domain.*;
import com.courseManager.system.mapper.*;
import com.courseManager.system.service.IStudentService;
import com.courseManager.system.service.ITeacherService;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.ibatis.annotations.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import com.courseManager.common.annotation.DataScope;
import com.courseManager.common.constant.UserConstants;
import com.courseManager.common.core.domain.entity.SysRole;
import com.courseManager.common.core.domain.entity.SysUser;
import com.courseManager.common.core.text.Convert;
import com.courseManager.common.exception.ServiceException;
import com.courseManager.common.utils.ShiroUtils;
import com.courseManager.common.utils.StringUtils;
import com.courseManager.common.utils.bean.BeanValidators;
import com.courseManager.common.utils.security.Md5Utils;
import com.courseManager.common.utils.spring.SpringUtils;
import com.courseManager.system.service.ISysConfigService;
import com.courseManager.system.service.ISysUserService;

/**
 * 用户 业务层处理
 *
 * @author ruoyi
 */
@Service
public class SysUserServiceImpl implements ISysUserService {
    private static final Logger log = LoggerFactory.getLogger(SysUserServiceImpl.class);

    @Autowired
    private SysUserMapper userMapper;

    @Autowired
    private SysRoleMapper roleMapper;

    @Autowired
    private SysPostMapper postMapper;

    @Autowired
    private SysUserPostMapper userPostMapper;

    @Autowired
    private SysUserRoleMapper userRoleMapper;

    @Autowired
    private ISysConfigService configService;

    @Autowired
    protected Validator validator;

    @Autowired
    private CourseUserMapper courseUserMapper;

    @Autowired
    private TeacherUserMapper teacherUserMapper;

    @Autowired
    private TeacherMapper teacherMapper;

    @Autowired
    private StudentMapper studentMapper;

    @Autowired
    private StudentUserMapper studentUserMapper;

    @Autowired
    private IStudentService studentService;

    @Autowired
    private ITeacherService teacherService;


    /**
     * 根据条件分页查询用户列表
     *
     * @param user 用户信息
     * @return 用户信息集合信息
     */
    @Override
    @DataScope(deptAlias = "d", userAlias = "u")
    public List<SysUser> selectUserList(SysUser user) {
        return userMapper.selectUserList(user);
    }

    /**
     * 根据条件分页查询已分配用户角色列表
     *
     * @param user 用户信息
     * @return 用户信息集合信息
     */
    @Override
    @DataScope(deptAlias = "d", userAlias = "u")
    public List<SysUser> selectAllocatedList(SysUser user) {
        return userMapper.selectAllocatedList(user);
    }

    /**
     * 根据条件分页查询未分配用户角色列表
     *
     * @param user 用户信息
     * @return 用户信息集合信息
     */
    @Override
    @DataScope(deptAlias = "d", userAlias = "u")
    public List<SysUser> selectUnallocatedList(SysUser user) {
        return userMapper.selectUnallocatedList(user);
    }

    /**
     * 通过用户名查询用户
     *
     * @param userName 用户名
     * @return 用户对象信息
     */
    @Override
    public SysUser selectUserByLoginName(String userName) {
        return userMapper.selectUserByLoginName(userName);
    }

    /**
     * 通过手机号码查询用户
     *
     * @param phoneNumber 手机号码
     * @return 用户对象信息
     */
    @Override
    public SysUser selectUserByPhoneNumber(String phoneNumber) {
        return userMapper.selectUserByPhoneNumber(phoneNumber);
    }

    /**
     * 通过邮箱查询用户
     *
     * @param email 邮箱
     * @return 用户对象信息
     */
    @Override
    public SysUser selectUserByEmail(String email) {
        return userMapper.selectUserByEmail(email);
    }

    /**
     * 通过用户ID查询用户
     *
     * @param userId 用户ID
     * @return 用户对象信息
     */
    @Override
    public SysUser selectUserById(Long userId) {
        return userMapper.selectUserById(userId);
    }

    /**
     * 通过用户ID查询用户和角色关联
     *
     * @param userId 用户ID
     * @return 用户和角色关联列表
     */
    @Override
    public List<SysUserRole> selectUserRoleByUserId(Long userId) {
        return userRoleMapper.selectUserRoleByUserId(userId);
    }

    /**
     * 可注册标志更新
     */
    public void updateTFlag() {
        List<Teacher> teachers = teacherMapper.selectTeacherList(null);
        for (Teacher t : teachers) {
            if (StringUtils.isNotNull(userMapper.checkLoginNameUnique(t.getIdNumber()))) {
                t.setFlag(1);
            } else {
                t.setFlag(0);
            }
            teacherMapper.updateTeacher(t);
        }
    }

    /**
     * 通过用户ID删除用户
     *
     * @param userId 用户ID
     * @return 结果
     */
    @Override
    @Transactional
    public int deleteUserById(Long userId) {
        // 删除用户与角色关联
        userRoleMapper.deleteUserRoleByUserId(userId);
        // 删除用户与岗位表
        userPostMapper.deleteUserPostByUserId(userId);

        //TODO 删除用户与教员关联
        teacherUserMapper.deleteTeacherUserByUserId(userId);

        int i = userMapper.deleteUserById(userId);
        updateTFlag(); //删除之后再更新教员注册标志

        return i;
    }

    /**
     * 批量删除用户信息
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    @Transactional
    public int deleteUserByIds(String ids) {
        Long[] userIds = Convert.toLongArray(ids);
        for (Long userId : userIds) {
            checkUserAllowed(new SysUser(userId));
            checkUserDataScope(userId);
        }
        // 删除用户与角色关联
        userRoleMapper.deleteUserRole(userIds);
        // 删除用户与岗位关联
        userPostMapper.deleteUserPost(userIds);
        //TODO 批量删除用户与教员、学员关联
        studentUserMapper.deleteStudentUser(userIds);
        teacherUserMapper.deleteTeacherUser(userIds);
        //TODO 设置学员、教员flag为0

        int i = userMapper.deleteUserByIds(userIds);
        updateTFlag();

        return i;
    }

    /**
     * 新增保存用户信息
     *
     * @param user 用户信息
     * @return 结果
     */
    @Override
    @Transactional
    public int insertUser(SysUser user) {
        // 新增用户信息
        int rows = userMapper.insertUser(user);
        // 新增用户岗位关联
        insertUserPost(user);
        // 新增用户与角色管理
        insertUserRole(user.getUserId(), user.getRoleIds());

        if (StringUtils.isNotEmpty(user.getRoleIds())) {
            if (Arrays.asList(user.getRoleIds()).contains(3L)) {//教员
                Teacher t = teacherMapper.getTeacherByIdNumber(user.getLoginName());
                TeacherUser teacherUser = new TeacherUser();
                if (ObjectUtils.isNotEmpty(t)) {
                    teacherUser.setUserId(user.getUserId());
                    teacherUser.setTeacherId(t.getId());
                    //新增用户与教员关联表
                    teacherUserMapper.insertTeacherUser(teacherUser);
                } else {
                    Teacher teacher = new Teacher();
                    teacher.setIdNumber(user.getLoginName());
                    teacher.setTeacherName(user.getUserName());
                    teacher.setGender(user.getSex().equals("1") ? 1 : 0);
                    teacherService.insertTeacher(teacher);
                    teacherUser.setUserId(user.getUserId());
                    teacherUser.setTeacherId(teacher.getId());
                    teacherUserMapper.insertTeacherUser(teacherUser);
                }
            } else if (Arrays.asList(user.getRoleIds()).contains(4L)) {//学员
                Student s = studentMapper.getStudentByIdNumber(user.getLoginName());
                StudentUser studentUser = new StudentUser();
                if (ObjectUtils.isNotEmpty(s)) {
                    //新增用户与学员关联表
                    studentUser.setUserId(user.getUserId());
                    studentUser.setStudentId(s.getStudentId());
                    studentUserMapper.insertStudentUser(studentUser);
                } else {
                    Student student = new Student();
                    student.setSNumber(user.getLoginName());
                    student.setStudentName(user.getUserName());
                    student.setGender(user.getSex().equals("1") ? 1 : 0);
                    studentService.insertStudent(student);
                    studentUser.setStudentId(student.getStudentId());
                    studentUser.setUserId(user.getUserId());
                    studentUserMapper.insertStudentUser(studentUser);
                }
            }
        }

        return rows;
    }

    /**
     * 注册用户信息
     *
     * @param user 用户信息
     * @return 结果
     */
    @Override
    public boolean registerUser(SysUser user) {
        user.setUserType(UserConstants.REGISTER_USER_TYPE);
        return userMapper.insertUser(user) > 0;
    }

    /**
     * 修改保存用户信息
     *
     * @param user 用户信息
     * @return 结果
     */
    @Override
    @Transactional
    public int updateUser(SysUser user) {
        Long userId = user.getUserId();
        // 删除用户与角色关联
        userRoleMapper.deleteUserRoleByUserId(userId);
        // 新增用户与角色管理
        insertUserRole(user.getUserId(), user.getRoleIds());
        // 删除用户与岗位关联
        userPostMapper.deleteUserPostByUserId(userId);
        // 新增用户与岗位管理
        insertUserPost(user);

        if (StringUtils.isNotEmpty(user.getRoleIds())) {
            if (Arrays.asList(user.getRoleIds()).contains(-1L)) {//清除身份
                //是否在学员表中存在，存在则删除
                Student student = studentMapper.checkNotUniqueWithIdNumber(user.getLoginName());
                if (ObjectUtils.isNotEmpty(student)) {
                    //删除关联信息
                    studentUserMapper.deleteStudentUserBySuId(studentUserMapper.getsuIdByStudentId(student.getStudentId()));
                }
                //是否在教员表中存在，存在则删除
                Teacher teacher = teacherMapper.checkUniqueByLoginName(user.getLoginName());
                if (ObjectUtils.isNotEmpty(teacher)) {
                    //删除关联信息
                    teacherUserMapper.deleteTeacherUserByTuId(teacherUserMapper.getTuIdByTeacherId(teacher.getId()));
                }
            }


            if (Arrays.asList(user.getRoleIds()).contains(3L)) {//教员
                //是否在学员表中存在，存在则删除
                Student student = studentMapper.checkNotUniqueWithIdNumber(user.getLoginName());
                if (ObjectUtils.isNotEmpty(student)) {
                    //删除该学员信息,以及关联信息
                    studentMapper.deleteStudentByStudentId(student.getStudentId());
                    studentUserMapper.deleteStudentUserBySuId(studentUserMapper.getsuIdByStudentId(student.getStudentId()));
                }
                //教员表中是否存在该登录名，存在则提示，不存在则新增
                Teacher t1 = teacherMapper.getTeacherByIdNumber(user.getLoginName());
                Teacher t = teacherMapper.getTeacherByIdNumberOnEdit(user.getLoginName(), user.getUserId());
                if (ObjectUtils.isNotEmpty(t)) {
                    //教员信息修改
//                t.setTeacherName(user.getUserName());
//                t.setGender(Integer.valueOf(user.getSex()));
//                t.setPhoneNumber(user.getPhonenumber());
//                teacherMapper.updateTeacher(t);
                    return -3; //代表该教员的登录名已存在
                }
                if(ObjectUtils.isEmpty(t1)){
                    Teacher teacher = new Teacher();
                    teacher.setIdNumber(user.getLoginName());
                    teacher.setTeacherName(user.getUserName());
                    teacher.setGender(Integer.valueOf(user.getSex()));
                    teacher.setPhoneNumber(user.getPhonenumber());
                    teacherService.insertTeacher(teacher);
                    //关联
                    TeacherUser teacherUser = new TeacherUser();
                    teacherUser.setTeacherId(teacher.getId());
                    teacherUser.setUserId(user.getUserId());
                    teacherUserMapper.insertTeacherUser(teacherUser);
                }

            } else if (Arrays.asList(user.getRoleIds()).contains(4L)) {//学员
                //是否在教员表中存在，存在则删除
                Teacher teacher = teacherMapper.checkUniqueByLoginName(user.getLoginName());
                if (ObjectUtils.isNotEmpty(teacher)) {
                    //删除该教员信息,以及关联信息
                    teacherMapper.deleteTeacherById(teacher.getId());
                    teacherUserMapper.deleteTeacherUserByTuId(teacherUserMapper.getTuIdByTeacherId(teacher.getId()));
                }
                Student s1 = studentMapper.getStudentByIdNumber(user.getLoginName());
                Student s = studentMapper.getStudentByIdNumberOnEdit(user.getLoginName(), user.getUserId());
                if (ObjectUtils.isNotEmpty(s)) {
                    //学员信息修改
//                s.setStudentName(user.getUserName());
//                s.setGender(Integer.valueOf(user.getSex()));
//                s.setPhoneNumber(user.getPhonenumber());
//                studentMapper.updateStudent(s);
                    return -4; //代表该学员的登录名已存在
                }
                if (ObjectUtils.isEmpty(s1)) {
                    Student student = new Student();
                    student.setSNumber(user.getLoginName());
                    student.setStudentName(user.getUserName());
                    student.setGender(Integer.valueOf(user.getSex()));
                    student.setPhoneNumber(user.getPhonenumber());
                    studentService.insertStudent(student);
                    //关联
                    StudentUser studentUser = new StudentUser();
                    studentUser.setStudentId(student.getStudentId());
                    studentUser.setUserId(user.getUserId());
                    studentUserMapper.insertStudentUser(studentUser);
                }
            }
        }


        return userMapper.updateUser(user);
    }

    /**
     * 修改用户个人详细信息
     *
     * @param user 用户信息
     * @return 结果
     */
    @Override
    public int updateUserInfo(SysUser user) {
        return userMapper.updateUser(user);
    }

    /**
     * 用户授权角色
     *
     * @param userId  用户ID
     * @param roleIds 角色组
     */
    @Override
    @Transactional
    public void insertUserAuth(Long userId, Long[] roleIds) {
        userRoleMapper.deleteUserRoleByUserId(userId);
        insertUserRole(userId, roleIds);
    }

    /**
     * 修改用户密码
     *
     * @param user 用户信息
     * @return 结果
     */
    @Override
    public int resetUserPwd(SysUser user) {
        return updateUserInfo(user);
    }

    /**
     * 新增用户角色信息
     *
     * @param userId  用户ID
     * @param roleIds 角色组
     */
    public void insertUserRole(Long userId, Long[] roleIds) {
        if (StringUtils.isNotNull(roleIds)) {
            // 新增用户与角色管理
            List<SysUserRole> list = new ArrayList<SysUserRole>();
            for (Long roleId : roleIds) {
                SysUserRole ur = new SysUserRole();
                ur.setUserId(userId);
                ur.setRoleId(roleId);
                list.add(ur);
            }
            if (list.size() > 0) {
                userRoleMapper.batchUserRole(list);
            }
        }
    }

    /**
     * 新增用户岗位信息
     *
     * @param user 用户对象
     */
    public void insertUserPost(SysUser user) {
        Long[] posts = user.getPostIds();
        if (StringUtils.isNotNull(posts)) {
            // 新增用户与岗位管理
            List<SysUserPost> list = new ArrayList<SysUserPost>();
            for (Long postId : posts) {
                SysUserPost up = new SysUserPost();
                up.setUserId(user.getUserId());
                up.setPostId(postId);
                list.add(up);
            }
            if (list.size() > 0) {
                userPostMapper.batchUserPost(list);
            }
        }
    }

    /**
     * 校验用户名称是否唯一
     *
     * @param user 用户信息
     * @return 结果
     */
    @Override
    public boolean checkLoginNameUnique(SysUser user) {
        Long userId = StringUtils.isNull(user.getUserId()) ? -1L : user.getUserId();
        SysUser info = userMapper.checkLoginNameUnique(user.getLoginName());
        if (StringUtils.isNotNull(info) && info.getUserId().longValue() != userId.longValue()) {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }

    /**
     * 校验手机号码是否唯一
     *
     * @param user 用户信息
     * @return
     */
    @Override
    public boolean checkPhoneUnique(SysUser user) {
        Long userId = StringUtils.isNull(user.getUserId()) ? -1L : user.getUserId();
        SysUser info = userMapper.checkPhoneUnique(user.getPhonenumber());
        if (StringUtils.isNotNull(info) && info.getUserId().longValue() != userId.longValue()) {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }

    /**
     * 校验email是否唯一
     *
     * @param user 用户信息
     * @return
     */
    @Override
    public boolean checkEmailUnique(SysUser user) {
        Long userId = StringUtils.isNull(user.getUserId()) ? -1L : user.getUserId();
        SysUser info = userMapper.checkEmailUnique(user.getEmail());
        if (StringUtils.isNotNull(info) && info.getUserId().longValue() != userId.longValue()) {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }

    /**
     * 校验用户是否允许操作
     *
     * @param user 用户信息
     */
    @Override
    public void checkUserAllowed(SysUser user) {
        if (StringUtils.isNotNull(user.getUserId()) && user.isAdmin()) {
            throw new ServiceException("不允许操作超级管理员用户");
        }
    }

    /**
     * 校验用户是否有数据权限
     *
     * @param userId 用户id
     */
    @Override
    public void checkUserDataScope(Long userId) {
        if (!SysUser.isAdmin(ShiroUtils.getUserId())) {
            SysUser user = new SysUser();
            user.setUserId(userId);
            List<SysUser> users = SpringUtils.getAopProxy(this).selectUserList(user);
            if (StringUtils.isEmpty(users)) {
                throw new ServiceException("没有权限访问用户数据！");
            }
        }
    }

    /**
     * 查询用户所属角色组
     *
     * @param userId 用户ID
     * @return 结果
     */
    @Override
    public String selectUserRoleGroup(Long userId) {
        List<SysRole> list = roleMapper.selectRolesByUserId(userId);
        if (CollectionUtils.isEmpty(list)) {
            return StringUtils.EMPTY;
        }
        return list.stream().map(SysRole::getRoleName).collect(Collectors.joining(","));
    }

    /**
     * 查询用户所属岗位组
     *
     * @param userId 用户ID
     * @return 结果
     */
    @Override
    public String selectUserPostGroup(Long userId) {
        List<SysPost> list = postMapper.selectPostsByUserId(userId);
        if (CollectionUtils.isEmpty(list)) {
            return StringUtils.EMPTY;
        }
        return list.stream().map(SysPost::getPostName).collect(Collectors.joining(","));
    }

    /**
     * 导入用户数据
     *
     * @param userList        用户数据列表
     * @param isUpdateSupport 是否更新支持，如果已存在，则进行更新数据
     * @param operName        操作用户
     * @return 结果
     */
    @Override
    public String importUser(List<SysUser> userList, Boolean isUpdateSupport, String operName) {
        if (StringUtils.isNull(userList) || userList.size() == 0) {
            throw new ServiceException("导入用户数据不能为空！");
        }
        int successNum = 0;
        int failureNum = 0;
        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();
        String password = configService.selectConfigByKey("sys.user.initPassword");
        for (SysUser user : userList) {
            try {
                // 验证是否存在这个用户
                SysUser u = userMapper.selectUserByLoginName(user.getLoginName());
                if (StringUtils.isNull(u)) {
                    BeanValidators.validateWithException(validator, user);
                    user.setPassword(Md5Utils.hash(user.getLoginName() + password));
                    user.setCreateBy(operName);
                    this.insertUser(user);
                    successNum++;
                    successMsg.append("<br/>" + successNum + "、账号 " + user.getLoginName() + " 导入成功");
                } else if (isUpdateSupport) {
                    BeanValidators.validateWithException(validator, user);
                    checkUserAllowed(user);
                    checkUserDataScope(user.getUserId());
                    user.setUpdateBy(operName);
                    this.updateUser(user);
                    successNum++;
                    successMsg.append("<br/>" + successNum + "、账号 " + user.getLoginName() + " 更新成功");
                } else {
                    failureNum++;
                    failureMsg.append("<br/>" + failureNum + "、账号 " + user.getLoginName() + " 已存在");
                }
            } catch (Exception e) {
                failureNum++;
                String msg = "<br/>" + failureNum + "、账号 " + user.getLoginName() + " 导入失败：";
                failureMsg.append(msg + e.getMessage());
                log.error(msg, e);
            }
        }
        if (failureNum > 0) {
            failureMsg.insert(0, "很抱歉，导入失败！共 " + failureNum + " 条数据格式不正确，错误如下：");
            throw new ServiceException(failureMsg.toString());
        } else {
            successMsg.insert(0, "恭喜您，数据已全部导入成功！共 " + successNum + " 条，数据如下：");
        }
        return successMsg.toString();
    }

    /**
     * 用户状态修改
     *
     * @param user 用户信息
     * @return 结果
     */
    @Override
    public int changeStatus(SysUser user) {
        //修改对应身份的用户的状态
        if (user.getIdentityFlag().equals(1)) {
            //学员
            Student student = new Student();
            Long studentIdByUserId = studentUserMapper.getStudentIdByUserId(user.getUserId());
            student.setStudentId(studentIdByUserId);
            student.setSNumber(userMapper.selectUserById(user.getUserId()).getLoginName());
            student.setStatus(Integer.valueOf(user.getStatus()));
            studentMapper.updateStudent(student);
        }
        if (user.getIdentityFlag().equals(2)) {
            //教员
            Teacher teacher = new Teacher();
            teacher.setId(teacherUserMapper.getTeacherIdByUserId(user.getUserId()));
            teacher.setIdNumber(userMapper.selectUserById(user.getUserId()).getLoginName());
            teacher.setStatus(Integer.valueOf(user.getStatus()));
            teacherMapper.updateTeacher(teacher);
        }
        return userMapper.updateUser(user);
    }

    /**
     * 查询拥有教员身份的用户
     *
     * @return
     */
    @Override
    public List<SysUser> getUserWithTc() {
        return userMapper.getUserWithTc();
    }

    /**
     * 关联角色与教员
     *
     * @param userId
     * @param teacherId
     */
    public void insertTeacherAuth(Long userId, Long teacherId) {
        if (teacherId != -1) {
            //插入选择的教员身份
            teacherUserMapper.insertTeacherWithUser(userId, teacherId);
        }
    }

    /**
     * 更新角色与教员关联表
     *
     * @param userId
     * @param teacherId
     */
    @Override
    public void updateTeacherWithUser(Long userId, Long teacherId) {
        teacherUserMapper.updateTeacherWithUser(userId, teacherId);
    }

    /**
     * 获取当前用户的课题的小组所有成员
     *
     * @param courseId
     * @return
     */
    @Override
    public List<SysUser> groupUsers(Long courseId) {
        //获取当前登录用户的id
        Long userId = ShiroUtils.getUserId();
        return userMapper.getGroupUsersByCourseIdAndUserId(courseId, userId);
    }

    /**
     * 根据课题id查询没有加入该课题的学员
     *
     * @param courseId
     * @param sysUser
     * @return
     */
    @Override
    public List<SysUser> studentListByInvite(Long courseId, SysUser sysUser) {
        return userMapper.studentListByInvite(courseId, sysUser);
    }

    /**
     * 保存用户与课题关联
     *
     * @param courseId
     * @param userIds
     * @return
     */
    @Override
    public int saveUserOnCourse(Long courseId, Long[] userIds) {
        //保存用户与课题关联
        return courseUserMapper.insertCourseUserBatch(courseId, userIds);
    }

    /**
     * 根据文件id查询拥有该文件权限的用户
     *
     * @param sysUser
     * @param fId
     * @return
     */
    @Override
    public List<SysUser> getusersByAssignPublic(SysUser sysUser, Long fId) {
        return userMapper.getusersByAssignPublic(sysUser, fId);
    }


}
