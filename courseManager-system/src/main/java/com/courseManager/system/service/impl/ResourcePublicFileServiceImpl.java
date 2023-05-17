package com.courseManager.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.courseManager.system.mapper.ResourcePublicFileMapper;
import com.courseManager.system.domain.ResourcePublicFile;
import com.courseManager.system.service.IResourcePublicFileService;
import com.courseManager.common.core.text.Convert;
import org.springframework.transaction.annotation.Transactional;

/**
 * 公共资源Service业务层处理
 * 
 * @author ruoyi
 * @date 2023-04-18
 */
@Service
public class ResourcePublicFileServiceImpl implements IResourcePublicFileService 
{
    @Autowired
    private ResourcePublicFileMapper resourcePublicFileMapper;

    /**
     * 查询公共资源
     * 
     * @param pubulicFileId 公共资源主键
     * @return 公共资源
     */
    @Override
    public ResourcePublicFile selectResourcePublicFileByPubulicFileId(Long pubulicFileId)
    {
        return resourcePublicFileMapper.selectResourcePublicFileByPubulicFileId(pubulicFileId);
    }

    /**
     * 查询公共资源列表
     * 
     * @param resourcePublicFile 公共资源
     * @return 公共资源
     */
    @Override
    public List<ResourcePublicFile> selectResourcePublicFileList(ResourcePublicFile resourcePublicFile)
    {
        return resourcePublicFileMapper.selectResourcePublicFileList(resourcePublicFile);
    }

    /**
     * 新增公共资源
     * 
     * @param resourcePublicFile 公共资源
     * @return 结果
     */
    @Override
    public int insertResourcePublicFile(ResourcePublicFile resourcePublicFile)
    {
        return resourcePublicFileMapper.insertResourcePublicFile(resourcePublicFile);
    }

    /**
     * 修改公共资源
     * 
     * @param resourcePublicFile 公共资源
     * @return 结果
     */
    @Override
    public int updateResourcePublicFile(ResourcePublicFile resourcePublicFile)
    {
        return resourcePublicFileMapper.updateResourcePublicFile(resourcePublicFile);
    }

    /**
     * 批量删除公共资源
     * 
     * @param pubulicFileIds 需要删除的公共资源主键
     * @return 结果
     */
    @Override
    public int deleteResourcePublicFileByPubulicFileIds(String pubulicFileIds)
    {
        return resourcePublicFileMapper.deleteResourcePublicFileByPubulicFileIds(Convert.toStrArray(pubulicFileIds));
    }

    /**
     * 删除公共资源信息
     * 
     * @param pubulicFileId 公共资源主键
     * @return 结果
     */
    @Override
    public int deleteResourcePublicFileByPubulicFileId(Long pubulicFileId)
    {
        return resourcePublicFileMapper.deleteResourcePublicFileByPubulicFileId(pubulicFileId);
    }

    /**
     * 【公共资源】设为全部人可见
     * @param fId
     * @return
     */
    @Override
    @Transactional
    public int assignAll(Long fId) {
        //根据文件id获取公共文件关联id
        ResourcePublicFile publicFile = resourcePublicFileMapper.getPublicFileByfId(fId);
        //设置公共文件关联visible字段为0
        publicFile.setVisible(0L);
        resourcePublicFileMapper.updateResourcePublicFile(publicFile);
        //删除该公共文件与所有学员的关联
        return resourcePublicFileMapper.deletePublicFileWithStudentByFId(fId);
    }
}
