package com.courseManager.system.mapper;

import java.util.List;

import com.courseManager.system.domain.ResourceFile;
import com.courseManager.system.domain.ResourceGroupFile;
import org.apache.ibatis.annotations.Param;

/**
 * 小组资源Mapper接口
 * 
 * @author ruoyi
 * @date 2023-04-11
 */
public interface ResourceGroupFileMapper 
{
    /**
     * 查询小组资源
     * 
     * @param groupFileId 小组资源主键
     * @return 小组资源
     */
    public ResourceGroupFile selectResourceGroupFileByGroupFileId(Long groupFileId);

    /**
     * 查询小组资源列表
     * 
     * @param resourceGroupFile 小组资源
     * @return 小组资源集合
     */
    public List<ResourceGroupFile> selectResourceGroupFileList(ResourceGroupFile resourceGroupFile);

    /**
     * 新增小组资源
     * 
     * @param resourceGroupFile 小组资源
     * @return 结果
     */
    public int insertResourceGroupFile(ResourceGroupFile resourceGroupFile);

    /**
     * 修改小组资源
     * 
     * @param resourceGroupFile 小组资源
     * @return 结果
     */
    public int updateResourceGroupFile(ResourceGroupFile resourceGroupFile);

    /**
     * 删除小组资源
     * 
     * @param groupFileId 小组资源主键
     * @return 结果
     */
    public int deleteResourceGroupFileByGroupFileId(Long groupFileId);

    /**
     * 批量删除小组资源
     * 
     * @param fileIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteResourceGroupFileByGroupFileIds(String[] fileIds);

    /**
     * 验证小组文件夹的唯一性
     * @param resourceFile
     * @param groupId
     * @return
     */
    int verifyFileFolderUnionByGroup(@Param("resourceFile") ResourceFile resourceFile,@Param("groupId") Long groupId);

    /**
     * 验证【个人】文件夹的唯一性
     * @param resourceFile
     * @param userId
     * @return
     */
    int verifyFileFolderUnionByPerson(@Param("resourceFile") ResourceFile resourceFile,@Param("userId") Long userId);
}
