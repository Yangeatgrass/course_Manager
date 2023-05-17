package com.courseManager.system.service;

import java.util.List;

import com.courseManager.common.core.domain.Ztree;
import com.courseManager.common.core.domain.entity.SysUser;
import com.courseManager.system.domain.Group;

/**
 * 小组Service接口
 * 
 * @author ruoyi
 * @date 2023-03-30
 */
public interface IGroupService 
{
    /**
     * 查询小组
     * 
     * @param groupId 小组主键
     * @return 小组
     */
    public Group selectGroupByGroupId(Long groupId);

    /**
     * 查询小组列表
     * 
     * @param group 小组
     * @return 小组集合
     */
    public List<Group> selectGroupList(Group group);

    /**
     * 新增小组
     * 
     * @param group 小组
     * @return 结果
     */
    public int insertGroup(Group group);

    /**
     * 修改小组
     * 
     * @param group 小组
     * @return 结果
     */
    public int updateGroup(Group group);

    /**
     * 批量删除小组
     * 
     * @param groupIds 需要删除的小组主键集合
     * @return 结果
     */
    public int deleteGroupByGroupIds(String groupIds);

    /**
     * 删除小组信息
     * 
     * @param groupId 小组主键
     * @return 结果
     */
    public int deleteGroupByGroupId(Long groupId);

    /**
     * 根据课题id获取选该课题的所有用户id
     * @param courseId
     * @return
     */
    public List<SysUser> getUserIdByCourseId(Long courseId);

    /**
     * 校验小组名称唯一性
     * @param groupName
     * @param courseId
     * @return
     */
    boolean checkGroupNameUnique(String groupName, Long courseId);

    /**
     * 加载小组列表树
     * @param group
     * @param courseId
     * @return
     */
    List<Ztree> selectGroupTree(Group group, Long courseId);

    /**
     * 编辑时校验小组名称唯一性
     * @param groupName
     * @param courseId
     * @return
     */
    boolean checkEditGroupNameUnique(String groupName, Long courseId, Long groupId);

    /**
     * 加载学员查看课题的小组列表树
     * @param courseId
     * @return
     */
    List<Ztree> selectGroupTreeToReview(Long courseId);
}
