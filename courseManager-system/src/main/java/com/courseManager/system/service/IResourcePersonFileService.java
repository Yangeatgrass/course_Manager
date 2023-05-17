package com.courseManager.system.service;

import java.util.List;
import com.courseManager.system.domain.ResourcePersonFile;

/**
 * 个人资源Service接口
 * 
 * @author ruoyi
 * @date 2023-04-13
 */
public interface IResourcePersonFileService 
{
    /**
     * 查询个人资源
     * 
     * @param personFileId 个人资源主键
     * @return 个人资源
     */
    public ResourcePersonFile selectResourcePersonFileByPersonFileId(Long personFileId);

    /**
     * 查询个人资源列表
     * 
     * @param resourcePersonFile 个人资源
     * @return 个人资源集合
     */
    public List<ResourcePersonFile> selectResourcePersonFileList(ResourcePersonFile resourcePersonFile);

    /**
     * 新增个人资源
     * 
     * @param resourcePersonFile 个人资源
     * @return 结果
     */
    public int insertResourcePersonFile(ResourcePersonFile resourcePersonFile);

    /**
     * 修改个人资源
     * 
     * @param resourcePersonFile 个人资源
     * @return 结果
     */
    public int updateResourcePersonFile(ResourcePersonFile resourcePersonFile);

    /**
     * 批量删除个人资源
     * 
     * @param personFileIds 需要删除的个人资源主键集合
     * @return 结果
     */
    public int deleteResourcePersonFileByPersonFileIds(String personFileIds);

    /**
     * 删除个人资源信息
     * 
     * @param personFileId 个人资源主键
     * @return 结果
     */
    public int deleteResourcePersonFileByPersonFileId(Long personFileId);
}
