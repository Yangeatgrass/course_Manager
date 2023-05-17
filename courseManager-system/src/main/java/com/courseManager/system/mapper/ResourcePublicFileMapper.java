package com.courseManager.system.mapper;

import java.util.List;
import com.courseManager.system.domain.ResourcePublicFile;

/**
 * 公共资源Mapper接口
 * 
 * @author ruoyi
 * @date 2023-04-18
 */
public interface ResourcePublicFileMapper 
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
     * 删除公共资源
     * 
     * @param pubulicFileId 公共资源主键
     * @return 结果
     */
    public int deleteResourcePublicFileByPubulicFileId(Long pubulicFileId);

    /**
     * 批量删除公共资源
     * 
     * @param pubulicFileIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteResourcePublicFileByPubulicFileIds(String[] pubulicFileIds);


    /**
     * 删除公共文件关联信息
     * @param fs
     * @return
     */
    int deletePublicFileByFIds(String[] fs);

    /**
     * 根据文件id删除该公共文件与所有学员的关联
     * @return
     */
    int deletePublicFileWithStudentByFId(Long fileId);

    /**
     * 根据文件id获取公共文件关联id
     * @param fId
     * @return
     */
    ResourcePublicFile getPublicFileByfId(Long fId);
}
