package com.courseManager.system.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.courseManager.common.annotation.Excel;
import com.courseManager.common.core.domain.BaseEntity;

/**
 * 个人资源对象 resource_person_file
 * 
 * @author ruoyi
 * @date 2023-04-13
 */
public class ResourcePersonFile extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long personFileId;

    /** 学员id */
    @Excel(name = "学员id")
    private Long studentId;

    /** 文件id */
    @Excel(name = "文件id")
    private Long fileId;

    public void setPersonFileId(Long personFileId) 
    {
        this.personFileId = personFileId;
    }

    public Long getPersonFileId() 
    {
        return personFileId;
    }

    public void setFileId(Long fileId) {
        this.fileId = fileId;
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public Long getFileId()
    {
        return fileId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("personFileId", getPersonFileId())
            .append("fileId", getFileId())
            .toString();
    }
}
