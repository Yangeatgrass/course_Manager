package com.courseManager.system.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.courseManager.common.annotation.Excel;
import com.courseManager.common.core.domain.BaseEntity;

/**
 * 小组学员对象 group_user
 * 
 * @author ruoyi
 * @date 2023-03-31
 */
public class GroupUser extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long guId;

    /** 小组id */
    @Excel(name = "小组id")
    private Long groupId;

    /** 学员id */
    @Excel(name = "学员id")
    private Long studentId;

    private String studentName; //学员姓名

    private String groupName; //小组名称

    /** 是否组长（0是1否） */
    @Excel(name = "是否组长", readConverterExp = "0=是1否")
    private Integer isManager;

    /** 加入时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "加入时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date joinTime;

    /** 邀请人id */
    @Excel(name = "邀请人id")
    private Long invite;

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public void setGuId(Long guId)
    {
        this.guId = guId;
    }

    public Long getGuId() 
    {
        return guId;
    }
    public void setGroupId(Long groupId) 
    {
        this.groupId = groupId;
    }

    public Long getGroupId() 
    {
        return groupId;
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public void setIsManager(Integer isManager)
    {
        this.isManager = isManager;
    }

    public Integer getIsManager() 
    {
        return isManager;
    }
    public void setJoinTime(Date joinTime) 
    {
        this.joinTime = joinTime;
    }

    public Date getJoinTime() 
    {
        return joinTime;
    }
    public void setInvite(Long invite) 
    {
        this.invite = invite;
    }

    public Long getInvite() 
    {
        return invite;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("guId", getGuId())
            .append("groupId", getGroupId())
            .append("isManager", getIsManager())
            .append("joinTime", getJoinTime())
            .append("invite", getInvite())
            .toString();
    }
}
