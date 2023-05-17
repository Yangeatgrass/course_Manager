package com.courseManager.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.courseManager.system.mapper.ResourceCourseFileMapper;
import com.courseManager.system.domain.ResourceCourseFile;
import com.courseManager.system.service.IResourceCourseFileService;
import com.courseManager.common.core.text.Convert;

/**
 * 【请填写功能名称】Service业务层处理
 * 
 * @author ruoyi
 * @date 2023-04-10
 */
@Service
public class ResourceCourseFileServiceImpl implements IResourceCourseFileService 
{
    @Autowired
    private ResourceCourseFileMapper resourceCourseFileMapper;

    /**
     * 查询【请填写功能名称】
     * 
     * @param courseFileId 【请填写功能名称】主键
     * @return 【请填写功能名称】
     */
    @Override
    public ResourceCourseFile selectResourceCourseFileByCourseFileId(Long courseFileId)
    {
        return resourceCourseFileMapper.selectResourceCourseFileByCourseFileId(courseFileId);
    }

    /**
     * 查询【请填写功能名称】列表
     * 
     * @param resourceCourseFile 【请填写功能名称】
     * @return 【请填写功能名称】
     */
    @Override
    public List<ResourceCourseFile> selectResourceCourseFileList(ResourceCourseFile resourceCourseFile)
    {
        return resourceCourseFileMapper.selectResourceCourseFileList(resourceCourseFile);
    }

    /**
     * 新增【请填写功能名称】
     * 
     * @param resourceCourseFile 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int insertResourceCourseFile(ResourceCourseFile resourceCourseFile)
    {
        return resourceCourseFileMapper.insertResourceCourseFile(resourceCourseFile);
    }

    /**
     * 修改【请填写功能名称】
     * 
     * @param resourceCourseFile 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int updateResourceCourseFile(ResourceCourseFile resourceCourseFile)
    {
        return resourceCourseFileMapper.updateResourceCourseFile(resourceCourseFile);
    }

    /**
     * 批量删除【请填写功能名称】
     * 
     * @param fileIds 需要删除的【请填写功能名称】主键
     * @return 结果
     */
    @Override
    public int deleteResourceCourseFileByCourseFileIds(String fileIds)
    {
        return resourceCourseFileMapper.deleteResourceCourseFileByCourseFileIds(Convert.toStrArray(fileIds));
    }

    /**
     * 删除【请填写功能名称】信息
     * 
     * @param courseFileId 【请填写功能名称】主键
     * @return 结果
     */
    @Override
    public int deleteResourceCourseFileByCourseFileId(Long courseFileId)
    {
        return resourceCourseFileMapper.deleteResourceCourseFileByCourseFileId(courseFileId);
    }
}
