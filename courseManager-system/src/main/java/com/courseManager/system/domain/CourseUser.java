package com.courseManager.system.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.courseManager.common.annotation.Excel;
import com.courseManager.common.core.domain.BaseEntity;

/**
 * 课程用户关联表对象 course_user
 * 
 * @author ruoyi
 * @date 2023-03-13
 */
public class CourseUser extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long cuId;

    /** 课程id */
    @Excel(name = "课程id")
    private Long courseId;

    /** 学员id */
    @Excel(name = "学员id")
    private Long studentId;

    public void setCuId(Long cuId) 
    {
        this.cuId = cuId;
    }

    public Long getCuId() 
    {
        return cuId;
    }
    public void setCourseId(Long courseId) 
    {
        this.courseId = courseId;
    }

    public Long getCourseId() 
    {
        return courseId;
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("cuId", getCuId())
            .append("courseId", getCourseId())
            .toString();
    }
}
