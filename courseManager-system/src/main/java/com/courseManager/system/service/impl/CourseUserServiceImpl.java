package com.courseManager.system.service.impl;

import java.util.List;

import com.courseManager.common.core.domain.entity.SysUser;
import com.courseManager.common.utils.ShiroUtils;
import com.courseManager.system.domain.Course;
import com.courseManager.system.domain.Student;
import com.courseManager.system.service.IStudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.courseManager.system.mapper.CourseUserMapper;
import com.courseManager.system.domain.CourseUser;
import com.courseManager.system.service.ICourseUserService;
import com.courseManager.common.core.text.Convert;

/**
 * 课程用户关联表Service业务层处理
 * 
 * @author ruoyi
 * @date 2023-03-13
 */
@Service
public class CourseUserServiceImpl implements ICourseUserService 
{
    @Autowired
    private CourseUserMapper courseUserMapper;

    @Autowired
    private IStudentService studentService;


    /**
     * 查询课程用户关联表
     * 
     * @param cuId 课程用户关联表主键
     * @return 课程用户关联表
     */
    @Override
    public CourseUser selectCourseUserByCuId(Long cuId)
    {
        return courseUserMapper.selectCourseUserByCuId(cuId);
    }

    /**
     * 查询课程用户关联表列表
     * 
     * @param courseUser 课程用户关联表
     * @return 课程用户关联表
     */
    @Override
    public List<CourseUser> selectCourseUserList(CourseUser courseUser)
    {
        return courseUserMapper.selectCourseUserList(courseUser);
    }

    /**
     * 新增课程用户关联表
     * 
     * @param courseUser 课程用户关联表
     * @return 结果
     */
    @Override
    public int insertCourseUser(CourseUser courseUser)
    {
        return courseUserMapper.insertCourseUser(courseUser);
    }

    /**
     * 修改课程用户关联表
     * 
     * @param courseUser 课程用户关联表
     * @return 结果
     */
    @Override
    public int updateCourseUser(CourseUser courseUser)
    {
        return courseUserMapper.updateCourseUser(courseUser);
    }

    /**
     * 批量删除课程用户关联表
     * 
     * @param cuIds 需要删除的课程用户关联表主键
     * @return 结果
     */
    @Override
    public int deleteCourseUserByCuIds(String cuIds)
    {
        return courseUserMapper.deleteCourseUserByCuIds(Convert.toStrArray(cuIds));
    }

    /**
     * 删除课程用户关联表信息
     * 
     * @param cuId 课程用户关联表主键
     * @return 结果
     */
    @Override
    public int deleteCourseUserByCuId(Long cuId)
    {
        return courseUserMapper.deleteCourseUserByCuId(cuId);
    }

    /**
     * 学员确认选课
     * @param course
     * @return
     */
    @Override
    public int ACKcheke(Course course) {
        //获取当前登录用户(学员)
        SysUser sysUser = ShiroUtils.getSysUser();
        //根据当前登录用户查询到学员信息
        Student student =  studentService.getStudentByUserId(sysUser.getUserId());
        //插入到关联表中
        CourseUser courseUser = new CourseUser();
        courseUser.setStudentId(student.getStudentId());
        courseUser.setCourseId(course.getCourseId());
        return courseUserMapper.insertCourseUser(courseUser);
    }

}
