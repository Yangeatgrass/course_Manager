package com.courseManager.system.mapper;

import java.util.List;

import com.courseManager.system.domain.ResourceFile;
import com.courseManager.system.domain.ResourcePersonFile;
import org.apache.ibatis.annotations.Param;

/**
 * 个人资源Mapper接口
 * 
 * @author ruoyi
 * @date 2023-04-13
 */
public interface ResourcePersonFileMapper 
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
     * 删除个人资源
     * 
     * @param personFileId 个人资源主键
     * @return 结果
     */
    public int deleteResourcePersonFileByPersonFileId(Long personFileId);

    /**
     * 批量删除个人资源
     * 
     * @param personFileIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteResourcePersonFileByPersonFileIds(String[] personFileIds);

    /**
     * 验证【个人】文件夹的唯一性
     * @param resourceFile
     * @param userId
     * @return
     */
    int verifyFileFolderUnionByPerson(@Param("resourceFile") ResourceFile resourceFile,@Param("userId") Long userId);
}
