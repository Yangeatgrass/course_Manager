package com.courseManager.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.courseManager.system.mapper.ResourcePersonFileMapper;
import com.courseManager.system.domain.ResourcePersonFile;
import com.courseManager.system.service.IResourcePersonFileService;
import com.courseManager.common.core.text.Convert;

/**
 * 个人资源Service业务层处理
 * 
 * @author ruoyi
 * @date 2023-04-13
 */
@Service
public class ResourcePersonFileServiceImpl implements IResourcePersonFileService 
{
    @Autowired
    private ResourcePersonFileMapper resourcePersonFileMapper;

    /**
     * 查询个人资源
     * 
     * @param personFileId 个人资源主键
     * @return 个人资源
     */
    @Override
    public ResourcePersonFile selectResourcePersonFileByPersonFileId(Long personFileId)
    {
        return resourcePersonFileMapper.selectResourcePersonFileByPersonFileId(personFileId);
    }

    /**
     * 查询个人资源列表
     * 
     * @param resourcePersonFile 个人资源
     * @return 个人资源
     */
    @Override
    public List<ResourcePersonFile> selectResourcePersonFileList(ResourcePersonFile resourcePersonFile)
    {
        return resourcePersonFileMapper.selectResourcePersonFileList(resourcePersonFile);
    }

    /**
     * 新增个人资源
     * 
     * @param resourcePersonFile 个人资源
     * @return 结果
     */
    @Override
    public int insertResourcePersonFile(ResourcePersonFile resourcePersonFile)
    {
        return resourcePersonFileMapper.insertResourcePersonFile(resourcePersonFile);
    }

    /**
     * 修改个人资源
     * 
     * @param resourcePersonFile 个人资源
     * @return 结果
     */
    @Override
    public int updateResourcePersonFile(ResourcePersonFile resourcePersonFile)
    {
        return resourcePersonFileMapper.updateResourcePersonFile(resourcePersonFile);
    }

    /**
     * 批量删除个人资源
     * 
     * @param personFileIds 需要删除的个人资源主键
     * @return 结果
     */
    @Override
    public int deleteResourcePersonFileByPersonFileIds(String personFileIds)
    {
        return resourcePersonFileMapper.deleteResourcePersonFileByPersonFileIds(Convert.toStrArray(personFileIds));
    }

    /**
     * 删除个人资源信息
     * 
     * @param personFileId 个人资源主键
     * @return 结果
     */
    @Override
    public int deleteResourcePersonFileByPersonFileId(Long personFileId)
    {
        return resourcePersonFileMapper.deleteResourcePersonFileByPersonFileId(personFileId);
    }
}
