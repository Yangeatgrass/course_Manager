package com.courseManager.system.mapper;

import java.util.List;
import com.courseManager.system.domain.ResourceCourseFile;

/**
 * 【请填写功能名称】Mapper接口
 * 
 * @author ruoyi
 * @date 2023-04-10
 */
public interface ResourceCourseFileMapper 
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
     * 删除【请填写功能名称】
     * 
     * @param courseFileId 【请填写功能名称】主键
     * @return 结果
     */
    public int deleteResourceCourseFileByCourseFileId(Long courseFileId);

    /**
     * 批量删除【请填写功能名称】
     * 
     * @param fileIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteResourceCourseFileByCourseFileIds(String[] fileIds);
}
