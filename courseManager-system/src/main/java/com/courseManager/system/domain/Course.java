package com.courseManager.system.domain;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.courseManager.common.core.domain.entity.SysUser;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.courseManager.common.annotation.Excel;
import com.courseManager.common.core.domain.BaseEntity;

/**
 * 课程对象 course
 *
 * @author ruoyi
 * @date 2023-03-12
 */
public class Course extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 课程主键
     */
    private Long courseId;

    /**
     * 课程所属的教员
     */
    @Excel(name = "课程所属的教员")
    private Long userId;

    /**
     * 课程名称
     */
    @Excel(name = "课程名称")
    private String courseName;

    /**
     * 课程简介
     */
    @Excel(name = "课程简介")
    private String introduction;

    /**
     * 课程学期
     */
    @Excel(name = "课程学期")
    private String courseDate;

    /**
     *
     */
    @Excel(name = "")
    private String courseContent;

    /**
     * 课程开始时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "课程开始时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date startTime;

    /**
     * 课程结束时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "课程结束时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date endTime;

    /**
     * 课程状态
     */
    @Excel(name = "课程状态")
    private Integer status;

    /**
     * 删除标识
     */
    private Integer delFlag;

    /**
     * 教员名(用户)
     */
    private String userName;

    /**
     * 已选标识
     * @return
     */
    private Integer checked;

    /**
     * 已选人数
     * @return
     */
    private Integer checkedSum;

    /** 开课所需人数 */
    @Excel(name = "开课所需人数")
    private Long startNum;

    /**
     * 小组数
     */
    private Integer groupSum;

    /**
     * 课题状态(学员可见)(0正在报名,1即将开始,2正在进行)
     * @return
     */
    private Integer statusPlus;

    /**
     * 课程所有文件大小(未换算)
     */
    private Integer fileNoUnitSize;

    public Integer getFileNoUnitSize() {
        return fileNoUnitSize;
    }

    public void setFileNoUnitSize(Integer fileNoUnitSize) {
        this.fileNoUnitSize = fileNoUnitSize;
    }

    public Integer getGroupSum() {
        return groupSum;
    }

    public void setGroupSum(Integer groupSum) {
        this.groupSum = groupSum;
    }

    public Long getStartNum() {
        return startNum;
    }

    public void setStartNum(Long startNum) {
        this.startNum = startNum;
    }

    public Integer getStatusPlus() {
        return statusPlus;
    }

    public void setStatusPlus(Integer statusPlus) {
        this.statusPlus = statusPlus;
    }

    public Integer getCheckedSum() {
        return checkedSum;
    }

    public void setCheckedSum(Integer checkedSum) {
        this.checkedSum = checkedSum;
    }

    public Integer getChecked() {
        return checked;
    }

    public void setChecked(Integer checked) {
        this.checked = checked;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setCourseDate(String courseDate) {
        this.courseDate = courseDate;
    }

    public String getCourseDate() {
        return courseDate;
    }

    public void setCourseContent(String courseContent) {
        this.courseContent = courseContent;
    }

    public String getCourseContent() {
        return courseContent;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getStatus() {
        return status;
    }

    public void setDelFlag(Integer delFlag) {
        this.delFlag = delFlag;
    }

    public Integer getDelFlag() {
        return delFlag;
    }


    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("courseId", getCourseId())
                .append("userId", getUserId())
                .append("courseName", getCourseName())
                .append("introduction", getIntroduction())
                .append("courseDate", getCourseDate())
                .append("courseContent", getCourseContent())
                .append("startTime", getStartTime())
                .append("endTime", getEndTime())
                .append("status", getStatus())
                .append("createBy", getCreateBy())
                .append("createTime", getCreateTime())
                .append("updateBy", getUpdateBy())
                .append("updateTime", getUpdateTime())
                .append("delFlag", getDelFlag())
                .toString();
    }
}
