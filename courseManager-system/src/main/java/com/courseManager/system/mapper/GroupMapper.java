package com.courseManager.system.mapper;

import java.util.List;
import com.courseManager.system.domain.Group;
import org.apache.ibatis.annotations.Param;

/**
 * 小组Mapper接口
 * 
 * @author ruoyi
 * @date 2023-03-30
 */
public interface GroupMapper 
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
     * 删除小组
     * 
     * @param groupId 小组主键
     * @return 结果
     */
    public int deleteGroupByGroupId(Long groupId);

    /**
     * 批量删除小组
     * 
     * @param groupIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteGroupByGroupIds(String[] groupIds);

    /**
     * 校验小组名称唯一性
     * @param groupName
     * @param courseId
     */
    Group checkGroupNameUnique(@Param("groupName") String groupName,@Param("courseId") Long courseId);

    /**
     * 根据课题id与用户id查询小组
     * @param group
     * @param userId
     * @return
     */
    List<Group> selectGroupListByUserId(@Param("group") Group group,@Param("userId") Long userId);

    /**
     * 编辑时校验小组名称唯一性
     * @param groupName
     * @param courseId
     * @param groupId
     * @return
     */
    Group checkEditGroupNameUnique(@Param("groupName") String groupName,@Param("courseId") Long courseId,@Param("groupId") Long groupId);

    /**
     * 根据课题id查询出该课题的所有小组id
     * @param courseId
     * @return
     */
    List<Long> selectGroupIdsListByCourseId(Long courseId);


}
