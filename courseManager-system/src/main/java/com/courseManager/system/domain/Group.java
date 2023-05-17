package com.courseManager.system.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.courseManager.common.annotation.Excel;
import com.courseManager.common.core.domain.BaseEntity;

/**
 * 小组对象 group
 * 
 * @author ruoyi
 * @date 2023-03-30
 */
public class Group extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long groupId;

    /** 小组课程id */
    @Excel(name = "小组课程id")
    private Long courseId;

    /** 小组名称 */
    @Excel(name = "小组名称")
    private String groupName;

    /** 组长id（学员） */
    @Excel(name = "组长id", readConverterExp = "学=员")
    private Long groupManager;

    /** 状态（0正常1停用） */
    @Excel(name = "状态", readConverterExp = "0=正常1停用")
    private Integer status;

    /** 小组介绍 */
    @Excel(name = "小组介绍")
    private String groupContent;

    /** 删除标识（0存在1不存在） */
    private Integer delFlag;

    /**
     * 课题名称
     */
    private String courseName;

    /**
     * 小组组长
     */
    private String manageName;

    /**
     * 小组人数
     */
    private Integer userCount;

    /** 学期 */
    private String courseDate;

    public String getCourseDate() {
        return courseDate;
    }

    public void setCourseDate(String courseDate) {
        this.courseDate = courseDate;
    }

    public Integer getUserCount() {
        return userCount;
    }

    public void setUserCount(Integer userCount) {
        this.userCount = userCount;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getManageName() {
        return manageName;
    }

    public void setManageName(String manageName) {
        this.manageName = manageName;
    }

    public void setGroupId(Long groupId)
    {
        this.groupId = groupId;
    }

    public Long getGroupId() 
    {
        return groupId;
    }
    public void setCourseId(Long courseId) 
    {
        this.courseId = courseId;
    }

    public Long getCourseId() 
    {
        return courseId;
    }
    public void setGroupName(String groupName) 
    {
        this.groupName = groupName;
    }

    public String getGroupName() 
    {
        return groupName;
    }
    public void setGroupManager(Long groupManager) 
    {
        this.groupManager = groupManager;
    }

    public Long getGroupManager() 
    {
        return groupManager;
    }
    public void setStatus(Integer status) 
    {
        this.status = status;
    }

    public Integer getStatus() 
    {
        return status;
    }
    public void setGroupContent(String groupContent) 
    {
        this.groupContent = groupContent;
    }

    public String getGroupContent() 
    {
        return groupContent;
    }
    public void setDelFlag(Integer delFlag) 
    {
        this.delFlag = delFlag;
    }

    public Integer getDelFlag() 
    {
        return delFlag;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("groupId", getGroupId())
            .append("courseId", getCourseId())
            .append("groupName", getGroupName())
            .append("groupManager", getGroupManager())
            .append("remark", getRemark())
            .append("status", getStatus())
            .append("groupContent", getGroupContent())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("delFlag", getDelFlag())
            .toString();
    }
}
