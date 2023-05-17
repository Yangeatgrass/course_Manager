package com.courseManager.system.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.courseManager.common.annotation.Excel;
import com.courseManager.common.core.domain.BaseEntity;

import java.util.Objects;

/**
 * 文件资源对象 resource_file
 * 
 * @author ruoyi
 * @date 2023-04-07
 */
public class ResourceFile extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 文件id */
    private Long fileId;

    /** 文件名称 */
    @Excel(name = "文件名称")
    private String fileName;

    /** 文件类型 */
    @Excel(name = "文件类型")
    private Integer fileType;

    /** 文件大小 */
    @Excel(name = "文件大小")
    private String fileSize;

    /** 文件大小，无单位 */
    @Excel(name = "文件大小，无单位")
    private Long fileNounitSize;

    /** 文件路径 */
    @Excel(name = "文件路径")
    private String filePath;

    /** 磁盘路径 */
    @Excel(name = "磁盘路径")
    private String diskPath;

    /** 下载次数 */
    @Excel(name = "下载次数")
    private Integer downloadCount;

    /** 是否为文件夹(0不是1是) */
    @Excel(name = "是否为文件夹(0不是1是)")
    private Integer isFolder;

    /** 是否为父文件夹(默认-1,表示没有父文件夹) */
    @Excel(name = "是否为父文件夹(默认-1,表示没有父文件夹)")
    private Long parentId;

    /** MD5唯一标识 */
    @Excel(name = "MD5唯一标识")
    private String identifier;

    /** 删除标识(0未删除,1删除) */
    private Integer delFlag;

    /** 权限范围(0全部可见,1需要权限) */
    private Integer visible;

    /** 审核标识，一般只在学员上传时会修改为1，经过教员或者管理员审核后可修改为0，默认为0 */
    @Excel(name = "审核标识，一般只在学员上传时会修改为1，经过教员或者管理员审核后可修改为0，默认为0")
    private Integer audit;

    /** 课题ID */
    private Long courseId;

    /** 课题名称 */
    private String courseName;

    /** 学期 */
    private String courseDate;

    public String getCourseDate() {
        return courseDate;
    }

    public void setCourseDate(String courseDate) {
        this.courseDate = courseDate;
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public Integer getAudit() {
        return audit;
    }

    public void setAudit(Integer audit) {
        this.audit = audit;
    }

    public Integer getVisible() {
        return visible;
    }

    public void setVisible(Integer visible) {
        this.visible = visible;
    }

    public Long getFileNounitSize() {
        return fileNounitSize;
    }

    public void setFileNounitSize(Long fileNounitSize) {
        this.fileNounitSize = fileNounitSize;
    }

    public void setFileId(Long fileId)
    {
        this.fileId = fileId;
    }

    public Long getFileId() 
    {
        return fileId;
    }
    public void setFileName(String fileName) 
    {
        this.fileName = fileName;
    }

    public String getFileName() 
    {
        return fileName;
    }
    public void setFileType(Integer fileType)
    {
        this.fileType = fileType;
    }

    public Integer getFileType()
    {
        return fileType;
    }
    public void setFileSize(String fileSize) 
    {
        this.fileSize = fileSize;
    }

    public String getFileSize() 
    {
        return fileSize;
    }
    public void setFilePath(String filePath) 
    {
        this.filePath = filePath;
    }

    public String getFilePath() 
    {
        return filePath;
    }
    public void setIsFolder(Integer isFolder) 
    {
        this.isFolder = isFolder;
    }

    public Integer getIsFolder()
    {
        return isFolder;
    }
    public void setParentId(Long parentId)
    {
        this.parentId = parentId;
    }

    public Long getParentId()
    {
        return parentId;
    }
    public void setIdentifier(String identifier) 
    {
        this.identifier = identifier;
    }

    public String getIdentifier() 
    {
        return identifier;
    }
    public void setDelFlag(Integer delFlag) 
    {
        this.delFlag = delFlag;
    }

    public Integer getDelFlag() 
    {
        return delFlag;
    }

    public String getDiskPath() {
        return diskPath;
    }

    public void setDiskPath(String diskPath) {
        this.diskPath = diskPath;
    }

    public Integer getDownloadCount() {
        return downloadCount;
    }

    public void setDownloadCount(Integer downloadCount) {
        this.downloadCount = downloadCount;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("fileId", getFileId())
            .append("fileName", getFileName())
            .append("fileType", getFileType())
            .append("fileSize", getFileSize())
            .append("filePath", getFilePath())
            .append("isFolder", getIsFolder())
            .append("parentId", getParentId())
            .append("identifier", getIdentifier())
            .append("createTime", getCreateTime())
            .append("createBy", getCreateBy())
            .append("updateTime", getUpdateTime())
            .append("updateBy", getUpdateBy())
            .append("delFlag", getDelFlag())
            .toString();
    }

    /**
     * 重写一下 equals 方法
     * @param o
     * @return
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ResourceFile person = (ResourceFile) o;
        return Objects.equals(courseName, person.courseName) &&
                Objects.equals(courseId, person.courseId);
    }

    /**
     * 重写一下 hashCode
     * @return
     */
    @Override
    public int hashCode() {
        return Objects.hash(courseName, courseId);
    }

}
