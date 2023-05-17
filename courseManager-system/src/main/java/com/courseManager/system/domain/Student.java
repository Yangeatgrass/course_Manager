package com.courseManager.system.domain;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.courseManager.common.xss.Xss;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.courseManager.common.annotation.Excel;
import com.courseManager.common.core.domain.BaseEntity;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * 学员对象 student
 *
 * @author xiaoyang
 * @date 2023-03-17
 */
public class Student extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @Excel(name = "学员序号" , cellType = Excel.ColumnType.NUMERIC, prompt = "学员编号")
    private Long studentId;

    /**
     * 学员姓名
     */
    @Excel(name = "学员姓名")
    private String studentName;

    /**
     * 年级
     */
    @Excel(name = "年级")
    private Integer grade;

    /**
     * 性别
     */
    @Excel(name = "性别" , readConverterExp = "0=女,1=男")
    private Integer gender;

    /**
     * 出生日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "出生日期" , width = 30, dateFormat = "yyyy-MM-dd")
    private Date birth;

    /**
     * 籍贯
     */
    @Excel(name = "籍贯")
    private String origin;

    /**
     * 民族
     */
    @Excel(name = "民族")
    private String nation;

    /**
     * 身份证号码
     */
    @Excel(name = "身份证号码")
    private String idCard;

    /**
     * 电话号码
     */
    @Excel(name = "电话号码")
    private String phoneNumber;

    /**
     * 学号
     */
    @Excel(name = "学号")
    private String sNumber;

    /**
     * 入学时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "入学时间" , width = 30, dateFormat = "yyyy-MM-dd")
    private Date enrollmentTime;

    /**
     * 政治面貌
     */
    @Excel(name = "政治面貌" , readConverterExp = "0=无,1=共青团员,2=党员")
    private Integer political;

    /**
     * 所学专业
     */
    @Excel(name = "所学专业")
    private String majorsStudied;

    /**
     * 专业方向
     */
    @Excel(name = "专业方向")
    private String professional;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 删除标识
     */
    private Integer delFlag;

    /**
     * 学号是否可用
     */
    private Integer flag;

    /**
     * 状态
     */
    @Excel(name = "状态" , readConverterExp = "0=可用,1=不可用")
    private Integer status;

    /**
     * 是否已注册
     */
    private Integer registerFlag;

    /**
     * 用户id
     * @return
     */
    private Long userId;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Integer getRegisterFlag() {
        return registerFlag;
    }

    public void setRegisterFlag(Integer registerFlag) {
        this.registerFlag = registerFlag;
    }

    public String getNation() {
        return nation;
    }

    public void setNation(String nation) {
        this.nation = nation;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    @Xss(message = "学员名称不能包含脚本字符")
    @NotBlank(message = "学员名称不能为空")
    @Size(min = 0, max = 30, message = "学员名称长度不能超过30个字符")
    public String getStudentName() {
        return studentName;
    }

    public void setGrade(Integer grade) {
        this.grade = grade;
    }

    public Integer getGrade() {
        return grade;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public Integer getGender() {
        return gender;
    }

    public void setBirth(Date birth) {
        this.birth = birth;
    }

    public Date getBirth() {
        return birth;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getOrigin() {
        return origin;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    @Xss(message = "身份证号不能包含脚本字符")
    public String getIdCard() {
        return idCard;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }


    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setSNumber(String sNumber) {
        this.sNumber = sNumber;
    }

    @Xss(message = "学号不能包含脚本字符")
    @NotBlank(message = "学号不能为空")
    @Size(min = 0, max = 30, message = "学号长度不能超过30个字符")
    public String getSNumber() {
        return sNumber;
    }

    public void setEnrollmentTime(Date enrollmentTime) {
        this.enrollmentTime = enrollmentTime;
    }

    public Date getEnrollmentTime() {
        return enrollmentTime;
    }

    public void setPolitical(Integer political) {
        this.political = political;
    }

    public Integer getPolitical() {
        return political;
    }

    public void setMajorsStudied(String majorsStudied) {
        this.majorsStudied = majorsStudied;
    }

    public String getMajorsStudied() {
        return majorsStudied;
    }

    public void setProfessional(String professional) {
        this.professional = professional;
    }

    public String getProfessional() {
        return professional;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setDelFlag(Integer delFlag) {
        this.delFlag = delFlag;
    }

    public Integer getDelFlag() {
        return delFlag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }

    public Integer getFlag() {
        return flag;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("studentId" , getStudentId())
                .append("studentName" , getStudentName())
                .append("grade" , getGrade())
                .append("gender" , getGender())
                .append("birth" , getBirth())
                .append("origin" , getOrigin())
                .append("idCard" , getIdCard())
                .append("phoneNumber" , getPhoneNumber())
                .append("sNumber" , getSNumber())
                .append("enrollmentTime" , getEnrollmentTime())
                .append("political" , getPolitical())
                .append("majorsStudied" , getMajorsStudied())
                .append("professional" , getProfessional())
                .append("avatar" , getAvatar())
                .append("remark" , getRemark())
                .append("createBy" , getCreateBy())
                .append("createTime" , getCreateTime())
                .append("updateBy" , getUpdateBy())
                .append("updateTime" , getUpdateTime())
                .append("delFlag" , getDelFlag())
                .append("flag" , getFlag())
                .toString();
    }
}
