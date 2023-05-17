package com.courseManager.system.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.courseManager.common.annotation.Excel;
import com.courseManager.common.core.domain.BaseEntity;

/**
 * 【请填写功能名称】对象 resource_course_file
 * 
 * @author ruoyi
 * @date 2023-04-10
 */
public class ResourceCourseFile extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 课题文件id */
    private Long courseFileId;

    /** 课题id */
    @Excel(name = "课题id")
    private Long courseId;

    /** 文件id */
    @Excel(name = "文件id")
    private Long fileId;

    public void setCourseFileId(Long courseFileId) 
    {
        this.courseFileId = courseFileId;
    }

    public Long getCourseFileId() 
    {
        return courseFileId;
    }
    public void setCourseId(Long courseId) 
    {
        this.courseId = courseId;
    }

    public Long getCourseId() 
    {
        return courseId;
    }
    public void setFileId(Long fileId) 
    {
        this.fileId = fileId;
    }

    public Long getFileId() 
    {
        return fileId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("courseFileId", getCourseFileId())
            .append("courseId", getCourseId())
            .append("fileId", getFileId())
            .toString();
    }
}
