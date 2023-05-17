package com.courseManager.system.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.courseManager.common.annotation.Excel;
import com.courseManager.common.core.domain.BaseEntity;

/**
 * 教员与用户关联对象 teacher_user
 * 
 * @author ruoyi
 * @date 2023-03-15
 */
public class TeacherUser extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long tuId;

    /** 教员id */
    @Excel(name = "教员id")
    private Long teacherId;

    /** 用户id */
    @Excel(name = "用户id")
    private Long userId;

    public void setTuId(Long tuId) 
    {
        this.tuId = tuId;
    }

    public Long getTuId() 
    {
        return tuId;
    }
    public void setTeacherId(Long teacherId) 
    {
        this.teacherId = teacherId;
    }

    public Long getTeacherId() 
    {
        return teacherId;
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
            .append("tuId", getTuId())
            .append("teacherId", getTeacherId())
            .append("userId", getUserId())
            .toString();
    }
}
