package com.courseManager.system.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.courseManager.common.core.domain.entity.SysUser;
import com.courseManager.common.exception.ServiceException;
import com.courseManager.common.utils.DateUtils;
import com.courseManager.common.utils.ShiroUtils;
import com.courseManager.common.utils.StringUtils;
import com.courseManager.common.utils.bean.BeanUtils;
import com.courseManager.common.utils.bean.BeanValidators;
import com.courseManager.common.utils.security.Md5Utils;
import com.courseManager.common.verification.VerificationUtil;
import com.courseManager.system.domain.Student;
import com.courseManager.system.mapper.SysUserMapper;
import com.courseManager.system.mapper.SysUserRoleMapper;
import com.courseManager.system.mapper.TeacherUserMapper;
import com.courseManager.system.service.ISysConfigService;
import com.courseManager.system.service.ISysUserService;
import org.apache.shiro.authc.credential.PasswordService;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.courseManager.system.mapper.TeacherMapper;
import com.courseManager.system.domain.Teacher;
import com.courseManager.system.service.ITeacherService;
import com.courseManager.common.core.text.Convert;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import javax.validation.Validator;

/**
 * 教员Service业务层处理
 *
 * @author ruoyi
 * @date 2023-03-13
 */
@Service
public class TeacherServiceImpl implements ITeacherService {

    private static final Logger log = LoggerFactory.getLogger(TeacherServiceImpl.class);


    @Autowired
    private TeacherMapper teacherMapper;

    @Autowired
    private TeacherUserMapper teacherUserMapper;

    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private ISysConfigService configService;

    @Autowired
    protected Validator validator;

    @Autowired
    private ISysUserService sysUserService;

    @Autowired
    private SysUserRoleMapper sysUserRoleMapper;

    /**
     * 查询教员
     *
     * @param id 教员主键
     * @return 教员
     */
    @Override
    public Teacher selectTeacherById(Long id) {
        Teacher teacher = teacherMapper.selectTeacherById(id);
        return teacher;
    }

    /**
     * 查询教员列表
     *
     * @param teacher 教员
     * @return 教员
     */
    @Override
    public List<Teacher> selectTeacherList(Teacher teacher) {
        return teacherMapper.selectTeacherList(teacher);
    }

    /**
     * 新增教员
     *
     * @param teacher 教员
     * @return 结果
     */
    @Override
    public int insertTeacher(Teacher teacher) {
        teacher.setCreateTime(DateUtils.getNowDate());
        teacher.setUpdateTime(DateUtils.getNowDate());
        teacher.setCreateBy(ShiroUtils.getLoginName());
        teacher.setUpdateBy(ShiroUtils.getLoginName());
        return teacherMapper.insertTeacher(teacher);
    }

    /**
     * 修改教员
     *
     * @param teacher 教员
     * @return 结果
     */
    @Override
    public int updateTeacher(Teacher teacher) {
        teacher.setUpdateTime(DateUtils.getNowDate());
        teacher.setUpdateBy(ShiroUtils.getLoginName());
        //更新用户的信息
//        SysUser sysUser = sysUserMapper.selectUserByLoginName(teacher.getIdNumber());
//        if(!ObjectUtils.isEmpty(sysUser)){
//            sysUser.setStatus(teacher.getStatus().toString());
//            sysUser.setPhonenumber(teacher.getPhoneNumber());
//            sysUser.setUserName(teacher.getTeacherName());
//            sysUser.setSex(teacher.getGender()==1?"男":"女");
//            sysUser.setAvatar(teacher.getAvatar());
//            sysUserMapper.updateUser(sysUser);
//        }
        return teacherMapper.updateTeacher(teacher);
    }


    /**
     * 教员状态修改
     * @param teacher
     * @return
     */
    @Override
    @Transactional
    public int changeStatus(Teacher teacher) {
        teacher.setUpdateTime(DateUtils.getNowDate());
        teacher.setUpdateBy(ShiroUtils.getLoginName());
        //更新用户的信息
        SysUser sysUser = sysUserMapper.selectUserByLoginName(teacher.getIdNumber());
        if(!ObjectUtils.isEmpty(sysUser)){
            sysUser.setStatus(teacher.getStatus().toString());
            sysUserMapper.updateUser(sysUser);
        }else{
            return -9;
        }
        return teacherMapper.updateTeacher(teacher);
    }

    @Override
    public boolean validDeletion(String ids) {
        Long[] longs = Convert.toLongArray(ids);
        for (Long l : longs) {
//            if(.getUserIdByCourseId(new SysUser(),l).size()>0){
//                return false;
//            }
        }
        return true;
    }

    /**
     * 批量删除教员
     *
     * @param ids 需要删除的教员主键
     * @return 结果
     */
    @Override
    @Transactional
    public int deleteTeacherByIds(String ids) {
        //根据教员信息查询到关联表id
//        Long[] tuIds= teacherUserMapper.getTuIdByTeacherId(ids);
        //删除教员与用户关联
//        teacherUserMapper.deleteTeacherUserByTuId()
        return teacherMapper.deleteTeacherByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除教员信息
     *
     * @param id 教员主键
     * @return 结果
     */
    @Override
    public int deleteTeacherById(Long id) {
        return teacherMapper.deleteTeacherById(id);
    }



    /**
     * 批量导入教员
     *
     * @param teachers
     * @param updateSupport
     * @param loginName
     * @return
     */
    @Override
    public String importTeacher(List<Teacher> teachers, boolean updateSupport, String loginName) {

        if (StringUtils.isNull(teachers) || teachers.size() == 0) {
            throw new ServiceException("导入教员数据不能为空！");
        }
        int successNum = 0;
        int failureNum = 0;
        int vailNum = 0; //如果大于零说明有校验错误，直接continue
        int size = teachers.size();
        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();
//        String password = configService.selectConfigByKey("sys.user.initPassword");
        int index = 1; //插入序号
        for (Teacher teacher : teachers) {
            vailNum=0; //新的对象则重新置为0
            try {
                /**
                 * 数据校验 start
                 */
                //校验身份证号
                if(!VerificationUtil.idCardVail(teacher.getIdCard())&&!StringUtils.isEmpty(teacher.getIdCard())){
                    vailNum++;
                    failureMsg.append("<br/> 序号" + index +":" + teacher.getTeacherName() + " 身份证格式错误");
                }
                //校验电话号码
                if(!VerificationUtil.phoneNumberVail(teacher.getPhoneNumber())&&!StringUtils.isEmpty(teacher.getPhoneNumber())){
                    vailNum++;
                    failureMsg.append("<br/> 序号" + index +":" + teacher.getTeacherName() + " 电话号码格式错误");
                }
                //校验电话号码唯一性
                if(!StringUtils.isNull(sysUserMapper.checkPhoneUnique(teacher.getPhoneNumber()))&&!StringUtils.isEmpty(teacher.getPhoneNumber())){
                    vailNum++;
                    failureMsg.append("<br/> 序号" + index +":" + teacher.getTeacherName() + " 电话号码已存在");
                }
                //TODO 后续校验....

                //如果vailNum大于零说明有校验错误，直接continue
                if(vailNum>0){
                    failureNum++;
                    index++;
                    continue;
                }
                /**
                 * 数据校验 end
                 */

                // 验证教员的证件号是否已存在用户表中
                SysUser u = sysUserMapper.checkNotUniqueWithIdNumber(teacher.getIdNumber());
                if (StringUtils.isNull(u)) {
                    BeanValidators.validateWithException(validator, teacher); //实体校验
//                    teacher.setFlag(0);//将证件号是否可用设为可用
                    teacher.setCreateBy(loginName); //设置创建人
                    teacher.setStatus(0);
                    this.insertTeacher(teacher);
                    successNum++;
                    successMsg.append("<br/> 序号" + index + ":" +teacher.getTeacherName() + " 导入成功");
                } else if (updateSupport) {
                    BeanValidators.validateWithException(validator, teacher);
//                    checkUserAllowed(user);
//                    checkUserDataScope(user.getUserId());
//                    teacher.setFlag(1);//将证件号是否可用设为不可用
                    teacher.setUpdateBy(loginName);
                    this.updateTeacher(teacher);
                    successNum++;
                    successMsg.append("<br/> 序号" + index +":" + teacher.getTeacherName() + " 更新成功");
                } else {
                    BeanValidators.validateWithException(validator, teacher);
//                    teacher.setFlag(1);//将证件号是否可用设为不可用
//                    teacher.setCreateBy(loginName);
//                    teacher.setStatus(0);
//                    this.insertTeacher(teacher);
                    failureNum++;
                    failureMsg.append("<br/> 序号" + index +": 证件号->" + teacher.getTeacherName() + " 已存在");
//                    failureNum++;
//                    failureMsg.append("<br/>" + failureNum + "、账号 " + teacher.getTeacherName() + " 已存在");
                }
            } catch (Exception e) {
                failureNum++;
                String msg = "<br/> 序号" + index +":" + teacher.getTeacherName() + " 导入失败：";
                failureMsg.append(msg + e.getMessage());
                log.error(msg, e);
            }
            index++;
        }
        if (failureNum > 0) {
            failureMsg.insert(0, "很抱歉，导入数据异常！共导入"+size+"条数据,有"+successNum+"条数据已导入!<br/>"+"有 <nobr style='color:red'>"+failureNum + "<nobr/> 条数据格式不正确，错误如下：");
            throw new ServiceException(failureMsg.toString());
        } else {
            successMsg.insert(0, "恭喜您，数据已全部导入成功！共 " + successNum + " 条，数据如下：");
        }
        return successMsg.toString();
    }

    /**
     * 教员注册到用户中
     * @param teacherIds
     * @return
     */
    @Override
//    @Transactional(rollbackFor = RuntimeException.class) //开启事务
    public String registerTeacher(Integer[] teacherIds) {

        int successNum = 0;
        int failureNum = 0;

        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();

        for (Integer integer : Arrays.asList(teacherIds)) {
            Teacher teacher = teacherMapper.selectTeacherById(integer.longValue());
            SysUser sysUser = sysUserMapper.checkNotUniqueWithIdNumber(teacher.getIdNumber());
            if(!ObjectUtils.isEmpty(sysUser)){
                //已存在
                failureNum++;
                failureMsg.append("<br/> 教员：<nobr style='color:red'>"+teacher.getTeacherName() + "<nobr/>"+" 注册失败!  原因：<nobr style='color:pink'>证件号已注册!<nobr/>");
                continue;
            }
            if(StringUtils.isNotEmpty(teacher.getPhoneNumber()) && StringUtils.isNotNull(sysUserMapper.checkPhoneUnique(teacher.getPhoneNumber()))){
                //已存在
                failureNum++;
                failureMsg.append("<br/> 教员：<nobr style='color:red'>"+teacher.getTeacherName() + "<nobr/>"+" 注册失败!  原因：<nobr style='color:pink'>手机号已存在<nobr/>");
                continue;
            }
            //将用户基本信息插入
            SysUser user = new SysUser();
            user.setLoginName(teacher.getIdNumber());//登录名(教员证件号)
            String password = "123456";//TODO 测试阶段，默认密码（后续可修改为证件号后六位）
            user.setPassword(password); //设置密码
            user.setUserName(teacher.getTeacherName());//用户名（教员姓名）
            user.setPhonenumber(teacher.getPhoneNumber());//手机号码
            user.setSalt(ShiroUtils.randomSalt());//密钥
            user.setPassword(encryptPassword(teacher.getIdNumber(),password, user.getSalt())); //密码加密
            user.setCreateBy(ShiroUtils.getLoginName());//创建人
            user.setCreateTime(DateUtils.getNowDate());//创建时间
            user.setPwdUpdateDate(DateUtils.getNowDate());

            sysUserMapper.insertUser(user);
            //设置教员角色
            sysUserService.insertUserAuth(user.getUserId(),new Long[]{3L});
            //新增教员与用户关联
            teacherUserMapper.insertTeacherWithUser(user.getUserId(),teacher.getId());
            successNum++;
        }

        if (failureNum > 0) {
            failureMsg.insert(0, "<br/>有" + successNum + "位教员注册成功!<br/>" + "有" + failureNum + " 位教员注册失败!");
            failureMsg.insert(0, "注册教员失败！"+"教员注册失败错误如下：");
            throw new ServiceException(failureMsg.toString());
        } else {
            successMsg.insert(0, "教员注册成功！密码默认为:123456");
        }
        return successMsg.toString();
    }

    /**
     * 验证手机号的唯一性
     * @param phoneNumber
     * @param id
     * @return
     */
    @Override
    public Teacher checkEditPhoneUnique(String phoneNumber, Long id) {
        return teacherMapper.checkEditPhoneUnique(phoneNumber,id);
    }
    @Override
    public Teacher checkPhoneUnique(String phoneNumber) {
        return teacherMapper.checkPhoneUnique(phoneNumber);
    }

    @Override
    public Teacher getTeacherByIdNumber(String userLoginName) {
        return teacherMapper.getTeacherByIdNumber(userLoginName);
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
