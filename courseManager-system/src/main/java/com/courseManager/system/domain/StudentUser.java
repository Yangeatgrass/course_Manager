package com.courseManager.system.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.courseManager.common.annotation.Excel;
import com.courseManager.common.core.domain.BaseEntity;

/**
 * 学员用户关联对象 student_user
 * 
 * @author ruoyi
 * @date 2023-03-29
 */
public class StudentUser extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键id */
    private Long suId;

    /** 学员id */
    @Excel(name = "学员id")
    private Long studentId;

    /** 用户id */
    @Excel(name = "用户id")
    private Long userId;

    public void setSuId(Long suId) 
    {
        this.suId = suId;
    }

    public Long getSuId() 
    {
        return suId;
    }
    public void setStudentId(Long studentId) 
    {
        this.studentId = studentId;
    }

    public Long getStudentId() 
    {
        return studentId;
    }
    public void setUserId(Long userId) 
    {
        this.userId = userId;
    }

    public Long getUserId() 
    {
        return userId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("suId", getSuId())
            .append("studentId", getStudentId())
            .append("userId", getUserId())
            .toString();
    }
}
