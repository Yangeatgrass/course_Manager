package com.courseManager.system.service;

import java.util.List;
import com.courseManager.system.domain.ResourcePublicFile;

/**
 * 公共资源Service接口
 * 
 * @author ruoyi
 * @date 2023-04-18
 */
public interface IResourcePublicFileService 
{
    /**
     * 查询公共资源
     * 
     * @param pubulicFileId 公共资源主键
     * @return 公共资源
     */
    public ResourcePublicFile selectResourcePublicFileByPubulicFileId(Long pubulicFileId);

    /**
     * 查询公共资源列表
     * 
     * @param resourcePublicFile 公共资源
     * @return 公共资源集合
     */
    public List<ResourcePublicFile> selectResourcePublicFileList(ResourcePublicFile resourcePublicFile);

    /**
     * 新增公共资源
     * 
     * @param resourcePublicFile 公共资源
     * @return 结果
     */
    public int insertResourcePublicFile(ResourcePublicFile resourcePublicFile);

    /**
     * 修改公共资源
     * 
     * @param resourcePublicFile 公共资源
     * @return 结果
     */
    public int updateResourcePublicFile(ResourcePublicFile resourcePublicFile);

    /**
     * 批量删除公共资源
     * 
     * @param pubulicFileIds 需要删除的公共资源主键集合
     * @return 结果
     */
    public int deleteResourcePublicFileByPubulicFileIds(String pubulicFileIds);

    /**
     * 删除公共资源信息
     * 
     * @param pubulicFileId 公共资源主键
     * @return 结果
     */
    public int deleteResourcePublicFileByPubulicFileId(Long pubulicFileId);

    /**
     * 【公共资源】设为全部人可见
     * @param fId
     * @return
     */
    int assignAll(Long fId);
}
