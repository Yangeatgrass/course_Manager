package com.courseManager.system.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.courseManager.common.annotation.Excel;
import com.courseManager.common.core.domain.BaseEntity;

/**
 * 小组资源对象 resource_group_file
 * 
 * @author ruoyi
 * @date 2023-04-11
 */
public class ResourceGroupFile extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long groupFileId;

    /** 小组id */
    @Excel(name = "小组id")
    private Long groupId;

    /** 文件id */
    @Excel(name = "文件id")
    private Long fileId;

    /** 审核标识 */
    @Excel(name = "审核标识")
    private Integer audit;

    /** 置顶 */
    @Excel(name = "置顶")
    private Integer isTop;

    public void setGroupFileId(Long groupFileId) 
    {
        this.groupFileId = groupFileId;
    }

    public Long getGroupFileId() 
    {
        return groupFileId;
    }
    public void setGroupId(Long groupId) 
    {
        this.groupId = groupId;
    }

    public Long getGroupId() 
    {
        return groupId;
    }
    public void setFileId(Long fileId) 
    {
        this.fileId = fileId;
    }

    public Long getFileId() 
    {
        return fileId;
    }
    public void setAudit(Integer audit) 
    {
        this.audit = audit;
    }

    public Integer getAudit() 
    {
        return audit;
    }
    public void setIsTop(Integer isTop) 
    {
        this.isTop = isTop;
    }

    public Integer getIsTop() 
    {
        return isTop;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("groupFileId", getGroupFileId())
            .append("groupId", getGroupId())
            .append("fileId", getFileId())
            .append("audit", getAudit())
            .append("isTop", getIsTop())
            .toString();
    }
}
