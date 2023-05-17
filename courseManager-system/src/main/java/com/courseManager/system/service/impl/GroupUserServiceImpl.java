package com.courseManager.system.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import com.courseManager.common.core.domain.entity.SysUser;
import com.courseManager.common.utils.DateUtils;
import com.courseManager.common.utils.ShiroUtils;
import com.courseManager.common.utils.StringUtils;
import com.courseManager.system.domain.Group;
import com.courseManager.system.domain.Student;
import com.courseManager.system.domain.SysUserRole;
import com.courseManager.system.mapper.GroupMapper;
import com.courseManager.system.mapper.SysUserMapper;
import com.courseManager.system.mapper.SysUserRoleMapper;
import com.courseManager.system.service.IStudentService;
import com.courseManager.system.service.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.courseManager.system.mapper.GroupUserMapper;
import com.courseManager.system.domain.GroupUser;
import com.courseManager.system.service.IGroupUserService;
import com.courseManager.common.core.text.Convert;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

/**
 * 小组学员Service业务层处理
 *
 * @author ruoyi
 * @date 2023-03-31
 */
@Service
public class GroupUserServiceImpl implements IGroupUserService {
    @Autowired
    private GroupUserMapper groupUserMapper;

    @Autowired
    private GroupMapper groupMapper;


    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private ISysUserService iSysUserService;

    @Autowired
    private SysUserRoleMapper userRoleMapper;

    @Autowired
    private IStudentService studentService;

    /**
     * 查询小组学员
     *
     * @param guId 小组学员主键
     * @return 小组学员
     */
    @Override
    public GroupUser selectGroupUserByGuId(Long guId) {
        return groupUserMapper.selectGroupUserByGuId(guId);
    }

    /**
     * 查询小组学员列表
     *
     * @param groupUser 小组学员
     * @return 小组学员
     */
    @Override
    public List<GroupUser> selectGroupUserList(GroupUser groupUser) {
        return groupUserMapper.selectGroupUserList(groupUser);
    }

    /**
     * 新增小组学员
     *
     * @param groupUser 小组学员
     * @return 结果
     */
    @Override
    public int insertGroupUser(GroupUser groupUser) {
        return groupUserMapper.insertGroupUser(groupUser);
    }

    /**
     * 修改小组学员
     *
     * @param groupUser 小组学员
     * @return 结果
     */
    @Override
    public int updateGroupUser(GroupUser groupUser) {
        return groupUserMapper.updateGroupUser(groupUser);
    }

    /**
     * 批量删除小组学员
     *
     * @param guIds 需要删除的小组学员主键
     * @return 结果
     */
    @Override
    public int deleteGroupUserByGuIds(String guIds) {
        return groupUserMapper.deleteGroupUserByGuIds(Convert.toStrArray(guIds));
    }

    /**
     * 删除小组学员信息
     *
     * @param guId 小组学员主键
     * @return 结果
     */
    @Override
    public int deleteGroupUserByGuId(Long guId) {
        return groupUserMapper.deleteGroupUserByGuId(guId);
    }

    /**
     * 根据小组id查询到在该小组的学员
     *
     * @param groupId
     * @return
     */
    @Override
    public List<SysUser> selectInGroupStudent(Long groupId) {
        return sysUserMapper.selectInGroupStudent(groupId);
    }

    /**
     * 批量保存小组成员
     *
     * @param gid
     * @param sIds
     * @return
     */
    @Override
    @Transactional //开启事务
    public void insertGroupUserBacht(Long gid, long[] sIds) {

        //获取当前登录的用户id
        Long userId = ShiroUtils.getUserId();
        //先删除该小组的所有学员
        groupUserMapper.deleteGroupUserByGroupId(gid);
        int Managerflag = 0; //表明组长还在该组的标识符，1存在0不存在
        //获取当前小组
        Group group = groupMapper.selectGroupByGroupId(gid);
        //获取小组组长id
        Long groupManager = group.getGroupManager();
        //批量插入
        for (int i = 0; i < sIds.length; i++) {
            GroupUser groupUser = new GroupUser();
            groupUser.setInvite(userId);//邀请人id
            groupUser.setGroupId(gid);//小组id
            groupUser.setStudentId(sIds[i]);//学员id
            groupUser.setJoinTime(DateUtils.getNowDate());
            groupUserMapper.insertGroupUser(groupUser);

            if (!StringUtils.isNull(groupManager) && groupManager == sIds[i]) { //sids中有组长的id则表明组长未被移除
                Managerflag = 1;
            }
        }
        if (Managerflag == 0) {
            //组长不在小组了则移除该小组的组长
            group.setGroupManager(-1L);
            groupMapper.updateGroup(group);
        }

    }

    /**
     * 设置小组组长
     *
     * @param groupId 小组id
     * @param sId     用户id
     * @return
     */
    @Override
    @Transactional //开启事务
    public int savaGroupManager(Long groupId, Long sId) {
        Group group = groupMapper.selectGroupByGroupId(groupId); //获取当前小组
        if (sId == -1) {
            group.setGroupManager(-1L);
            return groupMapper.updateGroup(group);
        }
        //更新小组组长
        group.setGroupManager(sId);
        return groupMapper.updateGroup(group);
    }

    /**
     * 退出小组
     * @param groupUser
     * @return
     */
    @Override
    public int deleteGroupUser(GroupUser groupUser) {
        return groupUserMapper.deleteGroupUser(groupUser);
    }


    /**
     * 加入小组
     * @param groupUser
     * @return
     */
    @Override
    @Transactional
    public int JoinGroup(GroupUser groupUser, Long courseId) {
        //验证当前登录用户是否在该课题的未分配学员中
        List<Student> notAssignment = studentService.getCourseNotAssignment(courseId,new Student());
        List<Student> collect = notAssignment.stream().filter(s -> {
            if (s.getStudentId().equals(groupUser.getStudentId())) {
                return true;
            } else {
                return false;
            }
        }).collect(Collectors.toList());

        if(collect.size()>=1){
            //可分组
            groupUser.setJoinTime(new Date());
            groupUser.setInvite(groupUser.getStudentId());
            return groupUserMapper.insertGroupUser(groupUser);
        }
        return -1;
    }

}
