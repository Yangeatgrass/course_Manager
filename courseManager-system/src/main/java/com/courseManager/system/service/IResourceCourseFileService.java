package com.courseManager.system.service;

import java.util.List;
import com.courseManager.system.domain.ResourceCourseFile;

/**
 * 【请填写功能名称】Service接口
 * 
 * @author ruoyi
 * @date 2023-04-10
 */
public interface IResourceCourseFileService 
{
    /**
     * 查询【请填写功能名称】
     * 
     * @param courseFileId 【请填写功能名称】主键
     * @return 【请填写功能名称】
     */
    public ResourceCourseFile selectResourceCourseFileByCourseFileId(Long courseFileId);

    /**
     * 查询【请填写功能名称】列表
     * 
     * @param resourceCourseFile 【请填写功能名称】
     * @return 【请填写功能名称】集合
     */
    public List<ResourceCourseFile> selectResourceCourseFileList(ResourceCourseFile resourceCourseFile);

    /**
     * 新增【请填写功能名称】
     * 
     * @param resourceCourseFile 【请填写功能名称】
     * @return 结果
     */
    public int insertResourceCourseFile(ResourceCourseFile resourceCourseFile);

    /**
     * 修改【请填写功能名称】
     * 
     * @param resourceCourseFile 【请填写功能名称】
     * @return 结果
     */
    public int updateResourceCourseFile(ResourceCourseFile resourceCourseFile);

    /**
     * 批量删除【请填写功能名称】
     * 
     * @param courseFileIds 需要删除的【请填写功能名称】主键集合
     * @return 结果
     */
    public int deleteResourceCourseFileByCourseFileIds(String courseFileIds);

    /**
     * 删除【请填写功能名称】信息
     * 
     * @param courseFileId 【请填写功能名称】主键
     * @return 结果
     */
    public int deleteResourceCourseFileByCourseFileId(Long courseFileId);
}
