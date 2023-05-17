package com.courseManager.system.service;

import java.util.List;
import java.util.Map;

import com.courseManager.common.core.domain.AjaxResult;
import com.courseManager.common.core.domain.entity.SysUser;
import com.courseManager.system.domain.Course;
import com.courseManager.system.domain.Group;
import com.courseManager.system.domain.ResourceFile;

/**
 * 文件资源Service接口
 * 
 * @author ruoyi
 * @date 2023-04-07
 */
public interface IResourceFileService 
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
     * 批量删除文件资源
     * 
     * @param fileIds 需要删除的文件资源主键集合
     * @return 结果
     */
    public int deleteResourceFileByFileIds(String fileIds);

    /**
     * 删除文件资源信息
     * 
     * @param fileId 文件资源主键
     * @return 结果
     */
    public int deleteResourceFileByFileId(Long fileId);

    /**
     * 根据课题的id获取课题的资源列表
     * @param courseId
     * @param resourceFile
     * @return
     */
    List<ResourceFile> getResourceListByCourseId(Long courseId, ResourceFile resourceFile);

    /**
     * 打开文件夹
     * @param fId 文件id
     * @param resourceFile 文件实体
     * @return
     */
    List<ResourceFile> openFolder(Long fId, ResourceFile resourceFile);



    /**
     * 根据小组id查询到当前课题的小组资源
     * @param resourceFile
     * @param courseId
     * @param groupId
     * @return
     */
    List<ResourceFile> getResourceListByGroupIdAndCourseId(ResourceFile resourceFile, Long courseId, Long groupId);

    /**
     * 课题新建文件夹
     * @param resourceFile 文件实体
     * @param courseId 课题id
     * @return
     */
    int addFolder(ResourceFile resourceFile, Long courseId);

    /**
     * 小组新建文件夹
     * @param resourceFile
     * @param groupId
     * @return
     */
    int addFolderByGroup(ResourceFile resourceFile, Long groupId);

    /**
     * 个人新建文件夹
     * @param resourceFile
     * @param userId
     * @return
     */
    int addFolderByPerson(ResourceFile resourceFile, Long userId);


    /**
     * 保存【课题资源】上传的文件信息
     * @param resourceFile
     * @return
     */
    int addCResource(ResourceFile resourceFile,Long courseId,Long flag);

    /**
     * 保存【小组资源】上传的文件信息
     * @param resourceFile
     * @param groupId
     * @return
     */
    int addGResource(ResourceFile resourceFile, Long groupId,Long flag);

    /**
     * 保存【个人资源】上传的文件信息
     * @param resourceFile
     * @param userId
     * @return
     */
    int addPResource(ResourceFile resourceFile, Long userId,Long flag);


    /**
     * 判断文件夹下是否还有文件
     * @param fileId
     * @return
     */
    int havaFile(Long fileId);

    /**
     * 个人文件资源列表
     * @param resourceFile
     * @param courseId
     * @return
     */
    List<ResourceFile> getResourceListByUserIdAndCourseId(ResourceFile resourceFile, Long courseId,Long userId);

    /**
     * 查询【公共】资源列表
     * @param resourceFile
     * @return
     */
    List<ResourceFile> getPublishList(ResourceFile resourceFile);

    /**
     * 保存【公共】文件信息
     * @param resourceFile
     * @return
     */
    int savePublishFile(ResourceFile resourceFile);

    /**
     * 删除【公共】资源
     * @param fIds
     * @return
     */
    int deletePublishFileByFileIds(String fIds);

    /**
     * 根据不同学员id查询可见的【公共】资源列表
     * @param resourceFile
     * @return
     */
    List<ResourceFile> getPublishListByUserId(ResourceFile resourceFile);

    /**
     * 保存【公共】文件权限信息
     * @param fileId
     * @param uIds
     * @return
     */
    int saveAssignUser(Long fileId, Long[] uIds);

    /**
     * 根据小组ID获取小组资源【审核】列表
     * @param resourceFile
     * @param groupId
     * @return
     */
    List<ResourceFile> getGroupAuditList(ResourceFile resourceFile, Long groupId);

    /**
     * 根据课题ID获取课题资源【审核】列表
     * @param resourceFile
     * @param courseId
     * @return
     */
    List<ResourceFile> getCourseAuditList(ResourceFile resourceFile, Long courseId);

    /**
     * 修改文件审核状态
     * @param fileId
     * @param audit
     * @return
     */
    int changeStatus(Long fileId, Integer audit);

    /**
     * 全局检索
     * @return
     */
    List<ResourceFile> getRetrievalList(Course course,ResourceFile resourceFile);

    /**
     * 资源全局统计
     * @param course
     * @param resourceFile
     * @param group
     * @param sysUser
     * @return
     */
    Map<String, Map> getStatisticsList(Course course, ResourceFile resourceFile, Group group, SysUser sysUser);

    /**
     * 文件检索
     * @param resourceFile
     * @param courseId
     * @param groupId
     * @return
     */
    List<ResourceFile> getResourceListByGroupIdAndCourseIdToSearch(ResourceFile resourceFile, Long courseId, Long groupId);

    List<ResourceFile> getResourceListByUserIdAndCourseIdToSearch(ResourceFile resourceFile, Long courseId, Long userId);

    List<ResourceFile> getResourceListByCourseIdToSearch(Long courseId, ResourceFile resourceFile);
}
