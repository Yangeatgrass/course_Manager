package com.courseManager.system.mapper;

import java.util.List;
import java.util.Map;

import com.courseManager.system.domain.Course;
import com.courseManager.system.domain.ResourceFile;
import org.apache.ibatis.annotations.Param;

/**
 * 文件资源Mapper接口
 * 
 * @author ruoyi
 * @date 2023-04-07
 */
public interface ResourceFileMapper 
{

    /**
     * 查询文件资源
     * 
     * @param fileId 文件资源主键
     * @return 文件资源
     */
    public ResourceFile selectResourceFileByFileId(Long fileId);

    /**
     * 查询文件资源列表
     * 
     * @param resourceFile 文件资源
     * @return 文件资源集合
     */
    public List<ResourceFile> selectResourceFileList(ResourceFile resourceFile);

    /**
     * 新增文件资源
     * 
     * @param resourceFile 文件资源
     * @return 结果
     */
    public int insertResourceFile(ResourceFile resourceFile);

    /**
     * 修改文件资源
     * 
     * @param resourceFile 文件资源
     * @return 结果
     */
    public int updateResourceFile(ResourceFile resourceFile);

    /**
     * 删除文件资源
     * 
     * @param fileId 文件资源主键
     * @return 结果
     */
    public int deleteResourceFileByFileId(Long fileId);

    /**
     * 批量删除文件资源
     * 
     * @param fileIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteResourceFileByFileIds(String[] fileIds);

    /**
     * 根据课题的id获取课题的资源列表
     * @param courseId
     * @return
     */
    List<ResourceFile> getResourceListByCourseId(@Param("courseId") Long courseId, @Param("resourceFile")ResourceFile resourceFile);

    /**
     * 验证文件夹的唯一性
     * @param resourceFile
     * @param courseId
     * @return
     */
    int verifyFileFolderUnion(@Param("resourceFile") ResourceFile resourceFile,@Param("courseId") Long courseId);

    /**
     * 根据小组id查询到当前课题的小组资源
     * @param resourceFile
     * @param courseId
     * @param groupId
     * @return
     */
    List<ResourceFile> getResourceListByGroupIdAndCourseId(@Param("resourceFile") ResourceFile resourceFile,
                                                           @Param("courseId") Long courseId,@Param("groupId") Long groupId);

    /**
     * 根据md5查询是否存在该文件
     * @param md5
     * @return
     */
    ResourceFile selectFileMd5Flag(String md5);

    /**
     * 判断文件夹下是否还有文件
     * @param fileId
     * @return
     */
    int havaFile(Long fileId);


    /**
     * 个人文件资源列表
     * @param resourceFile
     * @param userId
     * @param courseId
     * @return
     */
    List<ResourceFile> getResourceListByUserIdAndCourseId(@Param("resourceFile") ResourceFile resourceFile,
                                                          @Param("userId") Long userId,@Param("courseId") Long courseId
                                                          );

    /**
     * 判断【课题】是否有重复文件名
     * @param identifier
     * @param parentId
     * @return
     */
    List<ResourceFile> reCFileName(@Param("identifier") String identifier,@Param("parentId") Long parentId,@Param("courseId") Long courseId);

    /**
     * 判断【小组】是否有重复文件名
     * @param identifier
     * @param parentId
     * @return
     */
    List<ResourceFile> reGFileName(@Param("identifier") String identifier,@Param("parentId") Long parentId,@Param("groupId") Long groupId);

    /**
     * 判断【个人】是否有重复文件名
     * @param identifier
     * @param parentId
     * @return
     */
    List<ResourceFile> rePFileName(@Param("identifier") String identifier,@Param("parentId") Long parentId,@Param("userId") Long userId);

    /**
     * 查询【公共】资源列表
     * @param resourceFile
     * @return
     */
    List<ResourceFile> getPublishList(@Param("resourceFile") ResourceFile resourceFile);

    /**
     * 判断【公共资源】是否有重复文件名
     * @param identifier
     * @return
     */
    List<ResourceFile> rePublishFileName(String identifier);

    /**
     * 根据不同学员id查询可见的【公共】资源列表
     * @param resourceFile
     * @param userId
     * @return
     */
    List<ResourceFile> getPublishListByUserId(@Param("resourceFile") ResourceFile resourceFile,@Param("userId") Long userId);

    /**
     * 删除此文件的所有权限用户
     * @param fileId
     */
    void deletePublishUserAssign(Long fileId);

    /**
     * 添加此文件的权限用户集uIds
     * @param fileId
     * @param uIds
     * @return
     */
    int insertPublishUserAssig(@Param("fileId") Long fileId,@Param("uIds") Long[] uIds);

    /**
     * 根据文件id查询公共文件的id
     * @param fileId
     * @return
     */
    Long getPublishByFId(Long fileId);

    /**
     * 根据小组ID获取小组资源【审核】列表
     * @param resourceFile
     * @param groupId
     * @return
     */
    List<ResourceFile> getGroupAuditList(@Param("resourceFile") ResourceFile resourceFile,@Param("groupId") Long groupId);

    /**
     * 根据课题ID获取课题资源【审核】列表
     * @param resourceFile
     * @param courseId
     * @return
     */
    List<ResourceFile> getCourseAuditList(@Param("resourceFile") ResourceFile resourceFile,@Param("courseId") Long courseId);

    /**
     * 获取所有课题文件资源
     * @param course
     * @return
     */
    List<ResourceFile> selectResourceFileRetrievalList(@Param("course") Course course,
                                                       @Param("resourceFile") ResourceFile resourceFile,
                                                       @Param("params")Map<String,Object> params);

    /**
     * 根据小组ids查询出所有小组的资源
     * @param longs
     * @return
     */
    List<ResourceFile> getResourceRetrievalListByGroup(@Param("longs") List<Long> longs,
                                                       @Param("resourceFile") ResourceFile resourceFile,
                                                       @Param("params")Map<String,Object> params);

    /**
     * 查询所有资源文件
     * @param course
     * @param resourceFile
     * @param params
     * @return
     */
    List<ResourceFile> selectResourceFileRetrievalList2(@Param("course") Course course,
                                                        @Param("resourceFile") ResourceFile resourceFile,
                                                        @Param("params")Map<String,Object> params);

    /**
     * 获取所有文件个数
     * @return
     */
    Integer getFileCount();

    /**
     * 获取文件下载总次数
     * @return
     */
    Integer getdownloadCount();

    /**
     * 文件检索
     * @param resourceFile
     * @param courseId
     * @param groupId
     * @return
     */
    List<ResourceFile> getResourceListByGroupIdAndCourseIdToSearch(@Param("resourceFile") ResourceFile resourceFile,
                                                                   @Param("courseId") Long courseId,@Param("groupId") Long groupId);

    List<ResourceFile> getResourceListByUserIdAndCourseIdToSearch(@Param("resourceFile") ResourceFile resourceFile,
                                                                  @Param("courseId") Long courseId,@Param("userId") Long userId);

    List<ResourceFile> getResourceListByCourseIdToSearch(@Param("courseId") Long courseId,@Param("resourceFile") ResourceFile resourceFile
                                                         );
}
