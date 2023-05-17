package com.courseManager.system.service;

import java.util.List;

import com.courseManager.common.core.domain.entity.SysUser;
import com.courseManager.system.domain.GroupUser;

/**
 * 小组学员Service接口
 * 
 * @author ruoyi
 * @date 2023-03-31
 */
public interface IGroupUserService 
{
    /**
     * 查询小组学员
     * 
     * @param guId 小组学员主键
     * @return 小组学员
     */
    public GroupUser selectGroupUserByGuId(Long guId);

    /**
     * 查询小组学员列表
     * 
     * @param groupUser 小组学员
     * @return 小组学员集合
     */
    public List<GroupUser> selectGroupUserList(GroupUser groupUser);

    /**
     * 新增小组学员
     * 
     * @param groupUser 小组学员
     * @return 结果
     */
    public int insertGroupUser(GroupUser groupUser);

    /**
     * 修改小组学员
     * 
     * @param groupUser 小组学员
     * @return 结果
     */
    public int updateGroupUser(GroupUser groupUser);

    /**
     * 批量删除小组学员
     * 
     * @param guIds 需要删除的小组学员主键集合
     * @return 结果
     */
    public int deleteGroupUserByGuIds(String guIds);

    /**
     * 删除小组学员信息
     * 
     * @param guId 小组学员主键
     * @return 结果
     */
    public int deleteGroupUserByGuId(Long guId);

    /**
     * 根据小组id查询到在该小组的学员
     * @param groupId
     * @return
     */
    List<SysUser> selectInGroupStudent(Long groupId);

    /**
     * 批量保存小组成员
     * @param gid
     * @param sIds
     * @return
     */
    void insertGroupUserBacht(Long gid, long[] sIds);

    /**
     * 设置小组组长
     * @param groupId 小组id
     * @param sId   用户id
     * @return
     */
    int savaGroupManager(Long groupId, Long sId);

    /**
     * 退出小组
     * @param groupUser
     * @return
     */
    int deleteGroupUser(GroupUser groupUser);

    /**
     * 加入小组
     * @param groupUser
     * @return
     */
    int JoinGroup(GroupUser groupUser,Long courseId);
}
