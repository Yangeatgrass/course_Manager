package com.courseManager.system.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.courseManager.common.annotation.Excel;
import com.courseManager.common.core.domain.BaseEntity;

/**
 * 公共资源对象 resource_public_file
 * 
 * @author ruoyi
 * @date 2023-04-18
 */
public class ResourcePublicFile extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /**  */
    private Long publicFileId;

    /**  */
    @Excel(name = "")
    private Long fileId;

    /** 文件权限（0全部可见，1需要权限） */
    @Excel(name = "文件权限", readConverterExp = "0=全部可见，1需要权限")
    private Long visible;

    public Long getPublicFileId() {
        return publicFileId;
    }

    public void setPublicFileId(Long publicFileId) {
        this.publicFileId = publicFileId;
    }

    public Long getFileId() {
        return fileId;
    }

    public void setFileId(Long fileId) {
        this.fileId = fileId;
    }

    public Long getVisible() {
        return visible;
    }

    public void setVisible(Long visible) {
        this.visible = visible;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("publicFileId", getPublicFileId())
            .append("fileId", getFileId())
            .toString();
    }
}
