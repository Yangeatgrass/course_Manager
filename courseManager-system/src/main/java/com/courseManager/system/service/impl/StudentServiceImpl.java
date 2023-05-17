package com.courseManager.system.service.impl;

import java.util.Arrays;
import java.util.List;

import com.courseManager.common.core.domain.entity.SysUser;
import com.courseManager.common.exception.ServiceException;
import com.courseManager.common.utils.DateUtils;
import com.courseManager.common.utils.ShiroUtils;
import com.courseManager.common.utils.StringUtils;
import com.courseManager.common.utils.bean.BeanValidators;
import com.courseManager.common.verification.VerificationUtil;
import com.courseManager.system.domain.StudentUser;
import com.courseManager.system.domain.Teacher;
import com.courseManager.system.mapper.StudentUserMapper;
import com.courseManager.system.mapper.SysUserMapper;
import com.courseManager.system.service.ISysUserService;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.courseManager.system.mapper.StudentMapper;
import com.courseManager.system.domain.Student;
import com.courseManager.system.service.IStudentService;
import com.courseManager.common.core.text.Convert;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import javax.validation.Validator;

/**
 * 学员Service业务层处理
 *
 * @author xiaoyang
 * @date 2023-03-17
 */
@Service
public class StudentServiceImpl implements IStudentService {
    private static final Logger log = LoggerFactory.getLogger(StudentServiceImpl.class);

    @Autowired
    private StudentMapper studentMapper;

    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    protected Validator validator;

    @Autowired
    private ISysUserService sysUserService;

    @Autowired
    private StudentUserMapper studentUserMapper;

    /**
     * 查询学员
     *
     * @param studentId 学员主键
     * @return 学员
     */
    @Override
    public Student selectStudentByStudentId(Long studentId) {
        return studentMapper.selectStudentByStudentId(studentId);
    }

    /**
     * 查询学员列表
     *
     * @param student 学员
     * @return 学员
     */
    @Override
    public List<Student> selectStudentList(Student student) {
//        if(ShiroUtils.getSysUser().isAdmin()){
            return studentMapper.selectStudentList(student);
//        }
//        return studentMapper.selectStudentListTeacherView(student);
    }

    /**
     * 新增学员
     *
     * @param student 学员
     * @return 结果
     */
    @Override
    public int insertStudent(Student student) {
        student.setCreateTime(DateUtils.getNowDate());
        student.setCreateBy(ShiroUtils.getLoginName());
        return studentMapper.insertStudent(student);
    }

    /**
     * 修改学员
     *
     * @param student 学员
     * @return 结果
     */
    @Override
    @Transactional
    public int updateStudent(Student student) {
        student.setUpdateTime(DateUtils.getNowDate());
        student.setUpdateBy(ShiroUtils.getLoginName());
        //更新用户的信息
//        SysUser sysUser = sysUserMapper.selectUserByLoginName(student.getSNumber());
//        if(!ObjectUtils.isEmpty(sysUser)){
//            sysUser.setStatus(student.getStatus().toString());
//            sysUser.setPhonenumber(student.getPhoneNumber());
//            sysUser.setUserName(student.getStudentName());
//            sysUser.setSex(student.getGender()==1?"男":"女");
//            sysUser.setAvatar(student.getAvatar());
//            sysUserMapper.updateUser(sysUser);
//        }
        return studentMapper.updateStudent(student);
    }

    /**
     * 学员状态修改
     * @param student
     * @return
     */
    @Override
    @Transactional
    public int changeStatus(Student student) {
        student.setUpdateTime(DateUtils.getNowDate());
        student.setUpdateBy(ShiroUtils.getLoginName());
        //更新用户的信息
        SysUser sysUser = sysUserMapper.selectUserByLoginName(student.getSNumber());
        if(!ObjectUtils.isEmpty(sysUser)){
            sysUser.setStatus(student.getStatus().toString());
            sysUserMapper.updateUser(sysUser);
        }else{
            return -9;
        }
        return studentMapper.updateStudent(student);
    }

    /**
     * 根据登录名获取用户
     * @param userLoginName
     * @return
     */
    @Override
    public Student getStudentByIdNumber(String userLoginName) {
        return studentMapper.getStudentByIdNumber(userLoginName);
    }

    /**
     * 根据课题id查询没有加入该课题的学员
     * @param courseId
     * @param student
     * @param sysUser
     * @return
     */
    @Override
    public List<Student> studentListByInvite(Long courseId, Student student, SysUser sysUser) {
        return studentMapper.studentListByInvite(courseId,student,sysUser);
    }

    /**
     * 获取加入了该课题的学员
     * @param courseId
     * @param student
     * @return
     */
    @Override
    public List<Student> getStudentList(Long courseId, Student student) {
        return studentMapper.getStudentList(courseId,student);
    }

    /**
     * 根据当前登录用户查询到学员信息
     * @param userId
     * @return
     */
    @Override
    public Student getStudentByUserId(Long userId) {
        return studentMapper.getStudentByUserId(userId);
    }

    /**
     * 获取当前课题未分配小组的学员
     * @param courseId
     * @return
     */
    @Override
    public List<Student> getCourseNotAssignment(Long courseId,Student student) {
        student.setStatus(0);
        return studentMapper.getCourseNotAssignment(courseId,student);
    }

    /**
     * 根据小组id查询到在该小组的学员
     * @param groupId
     * @return
     */
    @Override
    public List<Student> selectInGroupStudent(Long groupId,Student student) {
        student.setStatus(0);
        return studentMapper.selectInGroupStudent(groupId,student);
    }

    /**
     * 根据学员学号查询学员
     * @param username
     * @return
     */
    @Override
    public Student getStudentBySNumber(String username) {
        return studentMapper.getStudentBySNumber(username);
    }

    @Override
    public Student checkInGroup(Long groupId, Long studentId) {
        return studentMapper.checkInGroup(groupId,studentId);
    }

    /**
     * 批量删除学员
     *
     * @param studentIds 需要删除的学员主键
     * @return 结果
     */
    @Override
    public int deleteStudentByStudentIds(String studentIds) {
        return studentMapper.deleteStudentByStudentIds(Convert.toStrArray(studentIds));
    }

    /**
     * 删除学员信息
     *
     * @param studentId 学员主键
     * @return 结果
     */
    @Override
    public int deleteStudentByStudentId(Long studentId) {
        return studentMapper.deleteStudentByStudentId(studentId);
    }

    /**
     * 批量导入学员
     *
     * @param students
     * @param updateSupport
     * @param loginName
     * @return
     */
    @Override
    public String importTeacher(List<Student> students, boolean updateSupport, String loginName) {
        if (StringUtils.isNull(students) || students.size() == 0) {
            throw new ServiceException("导入学员数据不能为空！");
        }
        int successNum = 0;
        int failureNum = 0;
        int vailNum = 0; //如果大于零说明有校验错误，直接continue
        int size = students.size();
        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();
//        String password = configService.selectConfigByKey("sys.user.initPassword");
        int index = 1; //插入序号
        for (Student student : students) {
            vailNum = 0; //新的对象则重新置为0
            try {
                /**
                 * 数据校验 start
                 */
                //校验身份证号
                if (!VerificationUtil.idCardVail(student.getIdCard())&&!StringUtils.isEmpty(student.getIdCard())) {
                    vailNum++;
                    failureMsg.append("<br/> 序号" + index + ":" + student.getStudentName() + " 身份证格式错误");
                }
                //校验电话号码
                if (!VerificationUtil.phoneNumberVail(student.getPhoneNumber())&&!StringUtils.isEmpty(student.getPhoneNumber())) {
                    vailNum++;
                    failureMsg.append("<br/> 序号" + index + ":" + student.getStudentName() + " 电话号码格式错误");
                }
                //校验电话号码唯一性
                if (StringUtils.isNotEmpty(student.getPhoneNumber()) && !StringUtils.isNull(sysUserMapper.checkPhoneUnique(student.getPhoneNumber()))&&!StringUtils.isEmpty(student.getPhoneNumber())) {
                    vailNum++;
                    failureMsg.append("<br/> 序号" + index + ":" + student.getStudentName() + " 电话号码已存在");
                }
                //TODO 后续校验....

                //如果vailNum大于零说明有校验错误，直接continue
                if (vailNum > 0) {
                    failureNum++;
                    index++;
                    continue;
                }
                /**
                 * 数据校验 end
                 */

                // 验证学员的学号是否已存在用户表中
                Student nowStudent = studentMapper.checkNotUniqueWithIdNumber(student.getSNumber());
                if (StringUtils.isNull(nowStudent)) {
                    BeanValidators.validateWithException(validator, student); //实体校验
//                    student.setFlag(0);//将学号设为可用
                    student.setCreateBy(loginName); //设置创建人
                    student.setStatus(0);
                    this.insertStudent(student);
                    successNum++;
                    successMsg.append("<br/> 序号" + index + ":" + student.getStudentName() + " 导入成功");
                } else if (updateSupport) {
                    BeanValidators.validateWithException(validator, student);
//                    checkUserAllowed(user);
//                    checkUserDataScope(user.getUserId());
//                    student.setFlag(1);//将证件号是否可用设为不可用
                    student.setUpdateBy(loginName);
                    this.updateStudent(student);
                    successNum++;
                    successMsg.append("<br/> 序号" + index + ":" + student.getStudentName() + " 更新成功");
                } else {
                    //存在不导入
                    BeanValidators.validateWithException(validator, student);
//                    student.setFlag(1);//将证件号是否可用设为不可用
//                    student.setCreateBy(loginName);
//                    student.setStatus(0);
//                    this.insertStudent(student);
                    failureNum++;
                    failureMsg.append("<br/> 序号" + index + ": 学号->" + student.getSNumber() + " 已存在");
//                    failureNum++;
//                    failureMsg.append("<br/>" + failureNum + "、账号 " + teacher.getTeacherName() + " 已存在");
                }
            } catch (Exception e) {
                failureNum++;
                String msg = "<br/> 序号" + index + ":" + student.getStudentName() + " 导入失败：";
                failureMsg.append(msg + e.getMessage());
                log.error(msg, e);
            }
            index++;
        }
        if (failureNum > 0) {
            failureMsg.insert(0, "很抱歉，导入数据异常！共导入" + size + "条数据,有" + successNum + "条数据已导入!<br/>" + "有" + failureNum + " 条数据格式不正确，错误如下：");
            throw new ServiceException(failureMsg.toString());
        } else {
            successMsg.insert(0, "恭喜您，数据已全部导入成功！共 " + successNum + " 条，数据如下：");
        }
        return successMsg.toString();
    }

    /**
     * 注册学员
     * @param studentIds
     * @return
     */
    @Override
//    @Transactional(rollbackFor = RuntimeException.class) //开启事务
    public String registerTeacher(Integer[] studentIds) {

        int successNum = 0;
        int failureNum = 0;

        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();

        for (Integer integer : Arrays.asList(studentIds)) {
            Student student = studentMapper.selectStudentByStudentId(integer.longValue());
            SysUser sysUser = sysUserMapper.checkNotUniqueWithIdNumber(student.getSNumber());
            if(!ObjectUtils.isEmpty(sysUser)){
                //已存在
                failureNum++;
                failureMsg.append("<br/> 学员：<nobr style='color:red'>"+student.getStudentName() + "<nobr/>"+" 注册失败!  原因：<nobr style='color:pink'>学号已注册!<nobr/>");
                continue;
            }
            if(StringUtils.isNotEmpty(student.getPhoneNumber()) && StringUtils.isNotNull(sysUserMapper.checkPhoneUnique(student.getPhoneNumber()))){
                //已存在
                failureNum++;
                failureMsg.append("<br/> 学员：<nobr style='color:red'>"+student.getStudentName() + "<nobr/>"+" 注册失败!  原因：<nobr style='color:pink'>手机号已存在<nobr/>");
                continue;
            }

            //将用户基本信息插入
            SysUser user = new SysUser();
            user.setLoginName(student.getSNumber());//登录名(学员学号)
            String password = "123456";//TODO 测试阶段，默认密码（后续可修改为证件号后六位）
            user.setPassword(password); //设置密码
            user.setUserName(student.getStudentName());//用户名（教员姓名）
            user.setPhonenumber(student.getPhoneNumber());//手机号码
            user.setSalt(ShiroUtils.randomSalt());//密钥
            user.setPassword(encryptPassword(student.getSNumber(),password, user.getSalt())); //密码加密
            user.setCreateBy(ShiroUtils.getLoginName());//创建人
            user.setCreateTime(DateUtils.getNowDate());//创建时间
            user.setPwdUpdateDate(DateUtils.getNowDate());

            sysUserMapper.insertUser(user);
            //设置学员角色
            sysUserService.insertUserAuth(user.getUserId(),new Long[]{4L});
            //新增学员与用户关联
            StudentUser studentUser = new StudentUser();
            studentUser.setStudentId(student.getStudentId());
            studentUser.setUserId(user.getUserId());
            studentUserMapper.insertStudentUser(studentUser);
            successNum++;
        }

        if (failureNum > 0) {
            failureMsg.insert(0, "<br/>有" + successNum + "位同学注册成功!<br/>" + "有" + failureNum + " 位同学注册失败!");
            failureMsg.insert(0, "注册学员失败！"+"学员注册失败错误如下：");
            throw new ServiceException(failureMsg.toString());
        } else {
            successMsg.insert(0, "学员注册成功！密码默认为:123456");
        }
        return successMsg.toString();
    }



    /**
     * 密码加密
     * @param loginName
     * @param password
     * @param salt
     * @return
     */
    public String encryptPassword(String loginName, String password, String salt)
    {
        return new Md5Hash(loginName + password + salt).toHex();
    }

}

