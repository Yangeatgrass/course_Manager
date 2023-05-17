package com.courseManager.system.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import com.courseManager.common.constant.UserConstants;
import com.courseManager.common.core.domain.Ztree;
import com.courseManager.common.core.domain.entity.SysDept;
import com.courseManager.common.core.domain.entity.SysRole;
import com.courseManager.common.core.domain.entity.SysUser;
import com.courseManager.common.utils.DateUtils;
import com.courseManager.common.utils.ShiroUtils;
import com.courseManager.common.utils.StringUtils;
import com.courseManager.system.domain.Course;
import com.courseManager.system.domain.GroupUser;
import com.courseManager.system.domain.Student;
import com.courseManager.system.mapper.CourseMapper;
import com.courseManager.system.mapper.GroupUserMapper;
import com.courseManager.system.mapper.SysUserMapper;
import com.courseManager.system.service.IStudentService;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.courseManager.system.mapper.GroupMapper;
import com.courseManager.system.domain.Group;
import com.courseManager.system.service.IGroupService;
import com.courseManager.common.core.text.Convert;

/**
 * 小组Service业务层处理
 *
 * @author ruoyi
 * @date 2023-03-30
 */
@Service
public class GroupServiceImpl implements IGroupService {
    @Autowired
    private GroupMapper groupMapper;

    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private GroupUserMapper groupUserMapper;

    @Autowired
    private CourseMapper courseMapper;

    @Autowired
    private IStudentService studentService;

    /**
     * 查询小组
     *
     * @param groupId 小组主键
     * @return 小组
     */
    @Override
    public Group selectGroupByGroupId(Long groupId) {
        return groupMapper.selectGroupByGroupId(groupId);
    }

    /**
     * 查询小组列表
     *
     * @param group 小组
     * @return 小组
     */
    @Override
    public List<Group> selectGroupList(Group group) {
        List<Group> groups = groupMapper.selectGroupList(group);

        if(ShiroUtils.getSubject().hasRole("student")){
            List<Group> InG = groupMapper.selectGroupListByUserId(group, studentService.getStudentByUserId(ShiroUtils.getUserId()).getStudentId());
            //根据用户id查询当前课题的小组
            List<Group> collect = groups.stream().filter(new Predicate<Group>() {
                @Override
                public boolean test(Group group) {
                    if(InG.size() != 0 && group.getGroupId().equals(InG.get(0).getGroupId())){
                        return true;
                    }
                    return false;
                }
            }).collect(Collectors.toList());
            return collect;
        }
        return groups;
    }

    /**
     * 新增小组
     *
     * @param group 小组
     * @return 结果
     */
    @Override
    public int insertGroup(Group group) {
        group.setCreateTime(DateUtils.getNowDate());
        group.setCreateBy(ShiroUtils.getLoginName());
        return groupMapper.insertGroup(group);
    }

    /**
     * 修改小组
     *
     * @param group 小组
     * @return 结果
     */
    @Override
    public int updateGroup(Group group) {
        group.setUpdateTime(DateUtils.getNowDate());
        group.setUpdateBy(ShiroUtils.getLoginName());
        return groupMapper.updateGroup(group);
    }

    /**
     * 批量删除小组
     *
     * @param groupIds 需要删除的小组主键
     * @return 结果
     */
    @Override
    public int deleteGroupByGroupIds(String groupIds) {
        //移除该组的所有学员
        groupUserMapper.deleteGroupUserByGroupIds(Convert.toStrArray(groupIds));
        return groupMapper.deleteGroupByGroupIds(Convert.toStrArray(groupIds));
    }

    /**
     * 删除小组信息
     *
     * @param groupId 小组主键
     * @return 结果
     */
    @Override
    public int deleteGroupByGroupId(Long groupId) {
        return groupMapper.deleteGroupByGroupId(groupId);
    }

    /**
     * 根据课题id获取选该课题的所有用户id
     *
     * @param courseId
     * @return
     */
    @Override
    public List<SysUser> getUserIdByCourseId(Long courseId) {
        return sysUserMapper.getUserIdByCourseId(new SysUser(),courseId);
    }

    /**
     * 校验小组名称唯一性
     *
     * @param groupName
     * @param courseId
     * @return
     */
    @Override
    public boolean checkGroupNameUnique(String groupName, Long courseId) {
        Group group = groupMapper.checkGroupNameUnique(groupName, courseId);
        if (ObjectUtils.isEmpty(group)) {
            return true;
        }
        return false;
    }

    /**
     * 编辑时校验小组名称唯一性
     * @param groupName
     * @param courseId
     * @return
     */
    @Override
    public boolean checkEditGroupNameUnique(String groupName, Long courseId, Long groupId) {
        Group group = groupMapper.checkEditGroupNameUnique(groupName,courseId,groupId);
        if (ObjectUtils.isEmpty(group)) {
            return true;
        }
        return false;
    }

    /**
     * 加载学员查看课题的小组列表树
     * @param courseId
     * @return
     */
    @Override
    public List<Ztree> selectGroupTreeToReview(Long courseId) {
        //根据课题id查询小组列表
        Group group = new Group();
        group.setCourseId(courseId);
        group.setStatus(0);
        List<Group> groups = groupMapper.selectGroupList(group);
        List<Ztree> ztrees = initZtreeToReview(groups,courseId);
        return ztrees;
    }

    private List<Ztree> initZtreeToReview(List<Group> groups, Long courseId) {
        //获取当前课题信息
        Course course = courseMapper.selectCourseByCourseId(courseId);

        Ztree z1 = new Ztree();
        z1.setId(course.getCourseId());
        z1.setpId(0L);
        z1.setName(course.getCourseName().concat("<span style='color:red;margin-left:2px'>[课题]</span>"));
        z1.setTitle(course.getCourseName());

        List<Ztree> ztrees = new ArrayList<Ztree>();
        ztrees.add(z1);

        for (Group group : groups) {
            Ztree z2 = new Ztree();
            z2.setId(group.getGroupId());
            z2.setpId(course.getCourseId());
            z2.setName(group.getGroupName().concat("<span style='color:pink;margin-left:2px'>[小组]</span>"));
            z2.setTitle(group.getGroupName());
            ztrees.add(z2);
        }

        //未分配
        Ztree z2 = new Ztree();
        final String notAssi = "未分组学员";
        z2.setId(-1L);
        z2.setpId(course.getCourseId());
        z2.setName(notAssi.concat("<span style='color:purple;margin-left:2px'>[未分组]</span>"));
        z2.setTitle("未分配的学员");
        ztrees.add(z2);

        return ztrees;
    }

    /**
     * 加载小组列表树
     *
     * @param group
     * @param courseId
     * @return
     */
    @Override
    public List<Ztree> selectGroupTree(Group group, Long courseId) {
        //角色标识 1表示是学员
        int roleFlag = 0;
        //获取当前登录用户的角色
        SysUser User = ShiroUtils.getSysUser();
        List<SysRole> roles = User.getRoles();
        if(roles.size()>0){
            for (SysRole role : roles) {
                if(role.getRoleId().equals(4L)){
                    roleFlag = 1;
                }
            }
        }

        if(roleFlag==1){
            //根据课题id与用户id查询小组
            group.setCourseId(courseId);
            group.setStatus(0);
            //1.根据userid查询对应的学员
            Student student = studentService.getStudentByUserId(User.getUserId());
            List<Group> groups = groupMapper.selectGroupListByUserId(group,student.getStudentId());
            List<Ztree> ztrees = initZtreeByStudent(groups,courseId,User.getUserId());
            return ztrees;
        }
        //根据课题id查询小组列表
        group.setCourseId(courseId);
        group.setStatus(0);
        List<Group> groups = groupMapper.selectGroupList(group);
        List<Ztree> ztrees = initZtree(groups,courseId);
        return ztrees;
    }


    /**
     * 小组列表树初始化
     *
     * @param groups 小组列表
     * @return
     */
    public List<Ztree> initZtree(List<Group> groups,Long courseId) {
        //获取当前课题信息
        Course course = courseMapper.selectCourseByCourseId(courseId);

        Ztree z1 = new Ztree();
        z1.setId(course.getCourseId());
        z1.setpId(0L);
        z1.setName(course.getCourseName().concat("<span style='color:red;margin-left:2px'>[课题]</span>"));
        z1.setTitle(course.getCourseName());

        List<Ztree> ztrees = new ArrayList<Ztree>();
        ztrees.add(z1);

        for (Group group : groups) {
            Ztree z2 = new Ztree();
            z2.setId(group.getGroupId());
            z2.setpId(course.getCourseId());
            z2.setName(group.getGroupName().concat("<span style='color:pink;margin-left:2px'>[小组]</span>"));
            z2.setTitle(group.getGroupName());
            ztrees.add(z2);
            //根据小组id查询小组的学员
            List<GroupUser> groupUsers = groupUserMapper.selectInGroupStudent(group.getGroupId());
            if(groupUsers.size()!=0){
                for (GroupUser gu : groupUsers) {
                    Ztree z3 = new Ztree();
                    z3.setId(gu.getStudentId());
                    z3.setpId(group.getGroupId());
                    if(!StringUtils.isEmpty(gu.getStudentName())){
                        if(group.getGroupManager().equals(gu.getStudentId())){
                            z3.setName(gu.getStudentName().concat("<span style='color:#FFCC00;margin-left:2px'>[组长]</span>"));
                        }else{
                            z3.setName(gu.getStudentName().concat("<span style='color:skyblue;margin-left:2px'>[学员]</span>"));
                        }
                        z3.setTitle(gu.getStudentName());
                    }
                    ztrees.add(z3);
                }
            }
        }

        //未分配的学员
        Ztree z2 = new Ztree();
        final String notAssi = "未分组学员";
        z2.setId(-1L);
        z2.setpId(course.getCourseId());
        z2.setName(notAssi.concat("<span style='color:purple;margin-left:2px'>[未分配]</span>"));
        z2.setTitle("未分配的学员");
        ztrees.add(z2);
        List<Student> notIn = studentService.getCourseNotAssignment(courseId,new Student());
        if(notIn.size()!=0){
            for (Student student : notIn) {
                Ztree z3 = new Ztree();
                z3.setId(student.getStudentId());
                z3.setpId(-1L);
                z3.setName(student.getStudentName());
                ztrees.add(z3);
            }
        }


        return ztrees;
    }

    /**
     * 小组列表树初始化【学员】
     *
     * @param groups 小组列表
     * @return
     */
    public List<Ztree> initZtreeByStudent(List<Group> groups,Long courseId,Long userId) {
        //获取当前课题信息
        Course course = courseMapper.selectCourseByCourseId(courseId);

        Ztree z1 = new Ztree();
        z1.setId(course.getCourseId());
        z1.setpId(0L);
        z1.setName(course.getCourseName().concat("<span style='color:red;margin-left:2px'>[课题]</span>"));
        z1.setTitle(course.getCourseName());

        List<Ztree> ztrees = new ArrayList<Ztree>();
        ztrees.add(z1);

        for (Group group : groups) {
            Ztree z2 = new Ztree();
            z2.setId(group.getGroupId());
            z2.setpId(course.getCourseId());
            z2.setName(group.getGroupName().concat("<span style='color:pink;margin-left:2px'>[小组]</span>"));
            z2.setTitle(group.getGroupName());
            ztrees.add(z2);
            //根据小组id和用户id查询小组
                //1.根据userid查询对应的学员
            Student student = studentService.getStudentByUserId(userId);
            List<GroupUser> groupUsers = groupUserMapper.selectInGroupStudentOne(group.getGroupId(),student.getStudentId());
            if(groupUsers.size()!=0){
                for (GroupUser gu : groupUsers) {
                    Ztree z3 = new Ztree();
                    z3.setId(gu.getStudentId());
                    z3.setpId(group.getGroupId());
                    if(!StringUtils.isEmpty(gu.getStudentName())){
                        if(group.getGroupManager().equals(gu.getStudentId())){
                            z3.setName(gu.getStudentName().concat("<span style='color:#FFCC00;margin-left:2px'>[组长]</span>"));
                        }else{
                            z3.setName(gu.getStudentName().concat("<span style='color:skyblue;margin-left:2px'>[学员]</span>"));
                        }
                        z3.setTitle(gu.getStudentName());
                    }
                    ztrees.add(z3);
                }
            }
        }

        return ztrees;
    }
}
