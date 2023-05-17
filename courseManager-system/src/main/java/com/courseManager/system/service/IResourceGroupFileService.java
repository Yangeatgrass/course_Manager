package com.courseManager.system.service;

import java.util.List;
import com.courseManager.system.domain.ResourceGroupFile;

/**
 * 小组资源Service接口
 * 
 * @author ruoyi
 * @date 2023-04-11
 */
public interface IResourceGroupFileService 
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
     * 批量删除小组资源
     * 
     * @param groupFileIds 需要删除的小组资源主键集合
     * @return 结果
     */
    public int deleteResourceGroupFileByGroupFileIds(String groupFileIds);

    /**
     * 删除小组资源信息
     * 
     * @param groupFileId 小组资源主键
     * @return 结果
     */
    public int deleteResourceGroupFileByGroupFileId(Long groupFileId);
}
