package com.courseManager.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.courseManager.system.mapper.ResourceGroupFileMapper;
import com.courseManager.system.domain.ResourceGroupFile;
import com.courseManager.system.service.IResourceGroupFileService;
import com.courseManager.common.core.text.Convert;

/**
 * 小组资源Service业务层处理
 * 
 * @author ruoyi
 * @date 2023-04-11
 */
@Service
public class ResourceGroupFileServiceImpl implements IResourceGroupFileService 
{
    @Autowired
    private ResourceGroupFileMapper resourceGroupFileMapper;

    /**
     * 查询小组资源
     * 
     * @param groupFileId 小组资源主键
     * @return 小组资源
     */
    @Override
    public ResourceGroupFile selectResourceGroupFileByGroupFileId(Long groupFileId)
    {
        return resourceGroupFileMapper.selectResourceGroupFileByGroupFileId(groupFileId);
    }

    /**
     * 查询小组资源列表
     * 
     * @param resourceGroupFile 小组资源
     * @return 小组资源
     */
    @Override
    public List<ResourceGroupFile> selectResourceGroupFileList(ResourceGroupFile resourceGroupFile)
    {
        return resourceGroupFileMapper.selectResourceGroupFileList(resourceGroupFile);
    }

    /**
     * 新增小组资源
     * 
     * @param resourceGroupFile 小组资源
     * @return 结果
     */
    @Override
    public int insertResourceGroupFile(ResourceGroupFile resourceGroupFile)
    {
        return resourceGroupFileMapper.insertResourceGroupFile(resourceGroupFile);
    }

    /**
     * 修改小组资源
     * 
     * @param resourceGroupFile 小组资源
     * @return 结果
     */
    @Override
    public int updateResourceGroupFile(ResourceGroupFile resourceGroupFile)
    {
        return resourceGroupFileMapper.updateResourceGroupFile(resourceGroupFile);
    }

    /**
     * 批量删除小组资源
     * 
     * @param groupFileIds 需要删除的小组资源主键
     * @return 结果
     */
    @Override
    public int deleteResourceGroupFileByGroupFileIds(String groupFileIds)
    {
        return resourceGroupFileMapper.deleteResourceGroupFileByGroupFileIds(Convert.toStrArray(groupFileIds));
    }

    /**
     * 删除小组资源信息
     * 
     * @param groupFileId 小组资源主键
     * @return 结果
     */
    @Override
    public int deleteResourceGroupFileByGroupFileId(Long groupFileId)
    {
        return resourceGroupFileMapper.deleteResourceGroupFileByGroupFileId(groupFileId);
    }
}
