package com.courseManager.system.mapper;

import java.util.List;

import com.courseManager.common.core.domain.entity.SysUser;
import com.courseManager.system.domain.GroupUser;
import org.apache.ibatis.annotations.Param;

/**
 * 小组学员Mapper接口
 * 
 * @author ruoyi
 * @date 2023-03-31
 */
public interface GroupUserMapper 
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
     * 删除小组学员
     * 
     * @param guId 小组学员主键
     * @return 结果
     */
    public int deleteGroupUserByGuId(Long guId);

    /**
     * 批量删除小组学员
     * 
     * @param guIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteGroupUserByGuIds(String[] guIds);

    /**
     * 根据小组id删除小组学员关联信息
     * @param gid
     */
    void deleteGroupUserByGroupId(Long gid);

    /**
     * 根据小组id查询小组的学员
     * @param groupId
     * @return
     */
    List<GroupUser> selectInGroupStudent(Long groupId);

    /**
     * 根据小组id和用户id查询小组
     * @param groupId
     * @param userId
     * @return
     */
    List<GroupUser> selectInGroupStudentOne(@Param("groupId") Long groupId,@Param("userId") Long userId);

    /**
     * 根据小组id批量删除小组学员关联信息
     * @param gids
     */
    void deleteGroupUserByGroupIds(String[] gids);

    void deleteGourpUserByuIds(@Param("aLong") Long aLong,@Param("uIds") Long[] uIds);

    /**
     * 退出小组
     * @param groupUser
     * @return
     */
    int deleteGroupUser(@Param("groupUser") GroupUser groupUser);
}
