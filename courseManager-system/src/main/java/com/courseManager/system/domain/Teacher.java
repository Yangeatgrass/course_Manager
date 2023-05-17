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
 * 教员对象 teacher
 *
 * @author ruoyi
 * @date 2023-03-15
 */
public class Teacher extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 教员id */
    @Excel(name = "教员序号", cellType = Excel.ColumnType.NUMERIC, prompt = "教员编号")
    private Long id;

    /** 教员姓名 */
    @Excel(name = "教员姓名")
    private String teacherName;

    /** 人员类别 */
    @Excel(name = "人员类别")
    private Integer type;

    /** 性别 */
    @Excel(name = "性别", readConverterExp = "0=女,1=男")
    private Integer gender;

    /** 出生日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "出生日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date birth;

    /** 籍贯 */
    @Excel(name = "籍贯")
    private String origin;

    /** 民族 */
    @Excel(name = "民族")
    private String nation;

    /** 身份证号 */
    @Excel(name = "身份证号")
    private String idCard;

    /** 电话号码 */
    @Excel(name = "电话号码")
    private String phoneNumber;

    /** 证件号码 */
    @Excel(name = "证件号码")
    private String idNumber;

    /** 入职时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "入职时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date joinTime;

    /** 政治面貌 */
    @Excel(name = "政治面貌",readConverterExp = "0=无,1=共青团员,2=党员")
    private String political;

    /** 毕业院校 */
    @Excel(name = "毕业院校")
    private String graduateSchool;

    /** 所学专业 */
    @Excel(name = "所学专业")
    private String majorsStudied;

    /** 专业方向 */
    @Excel(name = "专业方向")
    private String professional;

    /** 毕业时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "毕业时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date graduationTime;

    /** 职称 */
    @Excel(name = "职称")
    private String jobName;

    /** 个人简介 */
    private String biography;

    /** 详情介绍 */
    private String details;

    /** 头像 */
    private String avatar;

    /** 删除标识(0存在1删除) */
    private Integer delFlag;

    /** 状态(0可用，1不可用) */
    @Excel(name = "状态",readConverterExp = "0=可用,1=不可用")
    private Integer status;

    /** 证件号是否可用 */
    private Integer flag;

    /** 审核标识(0未审核，1审核通过，2审核未通过) */
    @Excel(name = "审核标识(0未审核，1审核通过，2审核未通过)")
    private Integer audit;

    /**
     * 是否已注册
     */
    private Integer registerFlag;

    public Integer getRegisterFlag() {
        return registerFlag;
    }

    public void setRegisterFlag(Integer registerFlag) {
        this.registerFlag = registerFlag;
    }

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId()
    {
        return id;
    }
    public void setTeacherName(String teacherName)
    {
        this.teacherName = teacherName;
    }

    @Xss(message = "教员名称不能包含脚本字符")
    @NotBlank(message = "教员名称不能为空")
    @Size(min = 0, max = 30, message = "教员名称长度不能超过30个字符")
    public String getTeacherName()
    {
        return teacherName;
    }
    public void setType(Integer type)
    {
        this.type = type;
    }

    public Integer getType()
    {
        return type;
    }
    public void setGender(Integer gender)
    {
        this.gender = gender;
    }

    public Integer getGender()
    {
        return gender;
    }
    public void setBirth(Date birth)
    {
        this.birth = birth;
    }

    public Date getBirth()
    {
        return birth;
    }
    public void setOrigin(String origin)
    {
        this.origin = origin;
    }

    public String getOrigin()
    {
        return origin;
    }
    public void setIdCard(String idCard)
    {
        this.idCard = idCard;
    }

    @Xss(message = "身份证号不能包含脚本字符")
//    @NotBlank(message = "身份证号不能为空")
    public String getIdCard()
    {
        return idCard;
    }
    public void setPhoneNumber(String phoneNumber)
    {
        this.phoneNumber = phoneNumber;
    }

    public String getPhoneNumber()
    {
        return phoneNumber;
    }
    public void setIdNumber(String idNumber)
    {
        this.idNumber = idNumber;
    }

    @Xss(message = "证件号不能包含脚本字符")
    @NotBlank(message = "证件号不能为空")
    @Size(min = 0, max = 30, message = "证件号长度不能超过30个字符")
    public String getIdNumber()
    {
        return idNumber;
    }
    public void setJoinTime(Date joinTime)
    {
        this.joinTime = joinTime;
    }

    public Date getJoinTime()
    {
        return joinTime;
    }
    public void setPolitical(String political)
    {
        this.political = political;
    }

    public String getPolitical()
    {
        return political;
    }
    public void setGraduateSchool(String graduateSchool)
    {
        this.graduateSchool = graduateSchool;
    }

    public String getGraduateSchool()
    {
        return graduateSchool;
    }
    public void setMajorsStudied(String majorsStudied)
    {
        this.majorsStudied = majorsStudied;
    }

    public String getMajorsStudied()
    {
        return majorsStudied;
    }
    public void setProfessional(String professional)
    {
        this.professional = professional;
    }

    public String getProfessional()
    {
        return professional;
    }
    public void setGraduationTime(Date graduationTime)
    {
        this.graduationTime = graduationTime;
    }

    public Date getGraduationTime()
    {
        return graduationTime;
    }
    public void setJobName(String jobName)
    {
        this.jobName = jobName;
    }

    public String getJobName()
    {
        return jobName;
    }
    public void setBiography(String biography)
    {
        this.biography = biography;
    }

    public String getBiography()
    {
        return biography;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public void setAvatar(String avatar)
    {
        this.avatar = avatar;
    }

    public String getAvatar()
    {
        return avatar;
    }
    public void setDelFlag(Integer delFlag)
    {
        this.delFlag = delFlag;
    }

    public Integer getDelFlag()
    {
        return delFlag;
    }
    public void setStatus(Integer status)
    {
        this.status = status;
    }

    public Integer getStatus()
    {
        return status;
    }

    public String getNation() {
        return nation;
    }
    public void setNation(String nation) {
        this.nation = nation;
    }

    public Integer getAudit() {
        return audit;
    }

    public void setAudit(Integer audit) {
        this.audit = audit;
    }



    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("teacherName", getTeacherName())
                .append("type", getType())
                .append("gender", getGender())
                .append("birth", getBirth())
                .append("origin", getOrigin())
                .append("idCard", getIdCard())
                .append("phoneNumber", getPhoneNumber())
                .append("idNumber", getIdNumber())
                .append("joinTime", getJoinTime())
                .append("political", getPolitical())
                .append("graduateSchool", getGraduateSchool())
                .append("majorsStudied", getMajorsStudied())
                .append("professional", getProfessional())
                .append("graduationTime", getGraduationTime())
                .append("jobName", getJobName())
                .append("biography", getBiography())
                .append("details", getDetails())
                .append("avatar", getAvatar())
                .append("remark", getRemark())
                .append("createBy", getCreateBy())
                .append("createTime", getCreateTime())
                .append("updateBy", getUpdateBy())
                .append("updateTime", getUpdateTime())
                .append("delFlag", getDelFlag())
                .append("status", getStatus())
                .append("flag", getFlag())
                .toString();
    }
}
