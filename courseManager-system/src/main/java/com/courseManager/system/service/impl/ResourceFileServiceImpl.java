package com.courseManager.system.service.impl;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import com.courseManager.common.core.domain.entity.SysRole;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.courseManager.common.core.domain.AjaxResult;
import com.courseManager.common.core.domain.entity.SysUser;
import com.courseManager.common.core.page.TableDataInfo;
import com.courseManager.common.utils.DateUtils;
import com.courseManager.common.utils.ShiroUtils;
import com.courseManager.common.utils.StringUtils;
import com.courseManager.common.utils.file.FileUtils;
import com.courseManager.system.domain.*;
import com.courseManager.system.mapper.*;
import com.courseManager.system.service.IStudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.courseManager.system.service.IResourceFileService;
import com.courseManager.common.core.text.Convert;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

/**
 * 文件资源Service业务层处理
 *
 * @author ruoyi
 * @date 2023-04-07
 */
@Service
public class ResourceFileServiceImpl implements IResourceFileService {
    @Autowired
    private ResourceFileMapper resourceFileMapper;

    @Autowired
    private ResourceCourseFileMapper courseFileMapper;

    @Autowired
    private ResourceGroupFileMapper groupFileMapper;

    @Autowired
    private ResourcePersonFileMapper personFileMapper;

    @Autowired
    private ResourcePublicFileMapper publicFileMapper;

    @Autowired
    private CourseMapper courseMapper;

    @Autowired
    private GroupMapper groupMapper;

    @Autowired
    private SysLogininforMapper sysLogininforMapper;

    @Autowired
    private SysOperLogMapper operLogMapper;

    @Autowired
    private IStudentService studentService;

    /**
     * 查询文件资源
     *
     * @param fileId 文件资源主键
     * @return 文件资源
     */
    @Override
    public ResourceFile selectResourceFileByFileId(Long fileId) {
        return resourceFileMapper.selectResourceFileByFileId(fileId);
    }

    /**
     * 查询文件资源列表
     *
     * @param resourceFile 文件资源
     * @return 文件资源
     */
    @Override
    public List<ResourceFile> selectResourceFileList(ResourceFile resourceFile) {
        return resourceFileMapper.selectResourceFileList(resourceFile);
    }

    /**
     * 新增文件资源
     *
     * @param resourceFile 文件资源
     * @return 结果
     */
    @Override
    public int insertResourceFile(ResourceFile resourceFile) {
        SysUser sysUser = ShiroUtils.getSysUser();
        resourceFile.setCreateTime(DateUtils.getNowDate());
        resourceFile.setCreateBy(sysUser.getUserName().concat("(" + sysUser.getLoginName()) + ")");
        return resourceFileMapper.insertResourceFile(resourceFile);
    }

    /**
     * 修改文件资源
     *
     * @param resourceFile 文件资源
     * @return 结果
     */
    @Override
    public int updateResourceFile(ResourceFile resourceFile) {
        resourceFile.setUpdateTime(DateUtils.getNowDate());
        resourceFile.setUpdateBy(ShiroUtils.getLoginName());
        return resourceFileMapper.updateResourceFile(resourceFile);
    }

    /**
     * 批量删除文件资源
     *
     * @param fileIds 需要删除的文件资源主键
     * @return 结果
     */
    @Override
    @Transactional
    public int deleteResourceFileByFileIds(String fileIds) {
        //删除课题、个人、小组的关联
        courseFileMapper.deleteResourceCourseFileByCourseFileIds(Convert.toStrArray(fileIds));
        groupFileMapper.deleteResourceGroupFileByGroupFileIds(Convert.toStrArray(fileIds));
        personFileMapper.deleteResourcePersonFileByPersonFileIds(Convert.toStrArray(fileIds));
        return resourceFileMapper.deleteResourceFileByFileIds(Convert.toStrArray(fileIds));
    }

    /**
     * 删除文件资源信息
     *
     * @param fileId 文件资源主键
     * @return 结果
     */
    @Override
    public int deleteResourceFileByFileId(Long fileId) {
        return resourceFileMapper.deleteResourceFileByFileId(fileId);
    }

    /**
     * 根据课题的id获取课题的资源列表
     *
     * @param courseId
     * @param resourceFile
     * @return
     */
    @Override
    public List<ResourceFile> getResourceListByCourseId(Long courseId, ResourceFile resourceFile) {
        return resourceFileMapper.getResourceListByCourseId(courseId, resourceFile);
    }

    /**
     * 根据小组id查询到当前课题的小组资源
     *
     * @param resourceFile
     * @param courseId
     * @param groupId
     * @return
     */
    @Override
    public List<ResourceFile> getResourceListByGroupIdAndCourseId(ResourceFile resourceFile, Long courseId, Long groupId) {
        return resourceFileMapper.getResourceListByGroupIdAndCourseId(resourceFile, courseId, groupId);
    }

    /**
     * 个人文件资源列表
     *
     * @param resourceFile
     * @param courseId
     * @return
     */
    @Override
    public List<ResourceFile> getResourceListByUserIdAndCourseId(ResourceFile resourceFile, Long courseId, Long userId) {
//        Student stu = studentService.getStudentByUserId(userId);
//        List<ResourceFile> rs = null;
//        if(!ObjectUtils.isEmpty(stu)){
//           rs  = resourceFileMapper.getResourceListByUserIdAndCourseId(resourceFile, stu.getStudentId(), courseId);
//        }else{
//            rs  = resourceFileMapper.getResourceListByUserIdAndCourseId(resourceFile, userId, courseId);
//        }
        return resourceFileMapper.getResourceListByUserIdAndCourseId(resourceFile, userId, courseId);
    }

    /**
     * 查询【公共】资源列表
     *
     * @param resourceFile
     * @return
     */
    @Override
    public List<ResourceFile> getPublishList(ResourceFile resourceFile) {
        return resourceFileMapper.getPublishList(resourceFile);
    }

    /**
     * 保存【公共】文件信息
     *
     * @param resourceFile
     * @return
     */
    @Override
    @Transactional
    public int savePublishFile(ResourceFile resourceFile) {
        //校验是否有重复文件名
        resourceFile.setFileName(this.RePublishFileName(resourceFile));
        //保存基本文件
        this.insertResourceFile(resourceFile);
        //保存公共文件关联
        ResourcePublicFile publicFile = new ResourcePublicFile();
        publicFile.setFileId(resourceFile.getFileId());
        return publicFileMapper.insertResourcePublicFile(publicFile);
    }

    /**
     * 删除【公共】资源
     *
     * @param fIds
     * @return
     */
    @Override
    @Transactional
    public int deletePublishFileByFileIds(String fIds) {
        String[] fs = Convert.toStrArray(fIds);
        //删除基本文件信息
        resourceFileMapper.deleteResourceFileByFileIds(fs);
        //删除公共文件关联信息
        return publicFileMapper.deletePublicFileByFIds(fs);
    }

    /**
     * 根据不同学员id查询可见的【公共】资源列表
     *
     * @param resourceFile
     * @return
     */
    @Override
    public List<ResourceFile> getPublishListByUserId(ResourceFile resourceFile) {
        //获取当前登录的用户Id
        Long userId = ShiroUtils.getUserId();
        return resourceFileMapper.getPublishListByUserId(resourceFile, userId);
    }

    /**
     * 保存【公共】文件权限信息
     *
     * @param fileId
     * @param uIds
     * @return
     */
    @Override
    @Transactional
    public int saveAssignUser(Long fileId, Long[] uIds) {
        //删除此文件的所有权限用户
        resourceFileMapper.deletePublishUserAssign(fileId);

        //根据文件id查询公共文件的id
        Long pId = resourceFileMapper.getPublishByFId(fileId);

        //更新该文件为需要权限文件
        ResourcePublicFile publicFile = new ResourcePublicFile();
        publicFile.setVisible(1L);
        publicFile.setPublicFileId(pId);
        publicFileMapper.updateResourcePublicFile(publicFile);

        //添加此文件的权限用户集uIds
        return resourceFileMapper.insertPublishUserAssig(pId, uIds);
    }

    /**
     * 根据小组ID获取小组资源【审核】列表
     *
     * @param resourceFile
     * @param groupId
     * @return
     */
    @Override
    public List<ResourceFile> getGroupAuditList(ResourceFile resourceFile, Long groupId) {
        return resourceFileMapper.getGroupAuditList(resourceFile, groupId);
    }

    /**
     * 根据课题ID获取课题资源【审核】列表
     *
     * @param resourceFile
     * @param courseId
     * @return
     */
    @Override
    public List<ResourceFile> getCourseAuditList(ResourceFile resourceFile, Long courseId) {
        return resourceFileMapper.getCourseAuditList(resourceFile, courseId);
    }

    /**
     * 修改文件审核状态
     *
     * @param fileId
     * @param audit
     * @return
     */
    @Override
    public int changeStatus(Long fileId, Integer audit) {
        ResourceFile resourceFile = resourceFileMapper.selectResourceFileByFileId(fileId);
        resourceFile.setAudit(audit);
        return resourceFileMapper.updateResourceFile(resourceFile);
    }

    /**
     * 全局检索
     *
     * @return
     */
    @Override
    public List<ResourceFile> getRetrievalList(Course course, ResourceFile resourceFile) {
        course.setCreateTime(null);
        SysUser sysUser = ShiroUtils.getSysUser();
        List<SysRole> roles = sysUser.getRoles();
        for (SysRole role : roles) {
            Map<String,Object> map = resourceFile.getParams();
            if ("teacher".equals(role.getRoleKey())) {
                map.put("isTeacher",sysUser.getUserId());
                resourceFile.setParams(map);
            }
            else if ("student".equals(role.getRoleKey())) {
                map.put("isStudent",sysUser.getUserId());
                resourceFile.setParams(map);
            }
        }
        //获取所有文件资源
        List<ResourceFile> list = resourceFileMapper.selectResourceFileRetrievalList(course, resourceFile, resourceFile.getParams());
        List<ResourceFile> courseList = resourceFileMapper.selectResourceFileRetrievalList(course, new ResourceFile(), resourceFile.getParams());
        List<ResourceFile> cs = new ArrayList<>();//存储课题资源
        List<Long> longs = new ArrayList<>();//存储小组id

        //未带条件，查所有的资源
        list.forEach(l -> {
            cs.add(l);
        });
        //去重后的课题ids
        List<ResourceFile> distinctList = courseList.stream().distinct().collect(Collectors.toList());

        distinctList.stream().distinct().forEach(c -> {
            //根据课题id查询出该课题的所有小组
            List<Long> gids = groupMapper.selectGroupIdsListByCourseId(c.getCourseId());
            if (gids.size() != 0) {
                for (Long gid : gids) {
                    longs.add(gid);
                }
            }
            //根据小组ids查询出所有小组的资源
            if (longs.size() != 0) {
                List<ResourceFile> gList = resourceFileMapper.getResourceRetrievalListByGroup(longs, resourceFile, resourceFile.getParams());
                gList.stream().forEach(g -> {
                    g.setCourseName(c.getCourseName());
                    g.setCourseId(c.getCourseId());
                    g.setCourseDate(c.getCourseDate());
                });
                list.addAll(gList);
            }
        });

        return list;
    }

    /**
     * 资源全局统计
     *
     * @param course
     * @param resourceFile
     * @param group
     * @param sysUser
     * @return
     */
    @Override
    public Map<String, Map> getStatisticsList(Course course, ResourceFile resourceFile, Group group, SysUser sysUser) {
        //返回集
        Map<String, Map> StatisticsMap = new HashMap<>();

        List<ResourceFile> retrievalList = new ArrayList<>(); //存储文件资源

        /**================== 资源查询 开始==================*/
        if (null == course.getCourseId()) {
            //查询所有资源文件
            resourceFile.setDelFlag(0);
            retrievalList = resourceFileMapper.selectResourceFileRetrievalList2(course, resourceFile, resourceFile.getParams());
        }
        if (null != course.getCourseId() && null == group.getGroupId() && null == sysUser.getUserId()) {
            //没有选小组，只选了课题,查询课题资源
            retrievalList = resourceFileMapper.selectResourceFileRetrievalList(course, resourceFile, resourceFile.getParams());
        } else if (null != course.getCourseId() && null != group.getGroupId() && null == sysUser.getUserId()) {
            //选了小组，查询该小组的资源
            List<ResourceFile> gList = resourceFileMapper.getResourceRetrievalListByGroup(Arrays.asList(group.getGroupId()), resourceFile, resourceFile.getParams());
            if (gList.size() != 0) {
                retrievalList.addAll(gList);
            }
        } else if (null != course.getCourseId() && null != group.getGroupId() && null != sysUser.getUserId()) {
            //选了学员，查询该学员在该课题下，该小组中上传的文件资源
            retrievalList = resourceFileMapper.getResourceListByUserIdAndCourseId(resourceFile, sysUser.getUserId(), course.getCourseId());
        }
        /**================== 资源查询 结束==================*/

        /**计算文件类型占比*/
        Map<Integer, Integer> counter = new HashMap<>();
        Set<Integer> fileTypes = new HashSet<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7)); // 所有可能的文件类型
        retrievalList.stream().forEach(r->{
            counter.put(r.getFileType(), counter.getOrDefault(r.getFileType(), 0) + 1);
        });
        for (Integer fileType : fileTypes) {
            counter.putIfAbsent(fileType, 0);
        }
        Map<String, Integer> percentage = new HashMap<>();
        for (Map.Entry<Integer, Integer> entry : counter.entrySet()) {
            percentage.put(typeName(entry.getKey()), entry.getValue());
        }

        /**计算文件大小*/
        int FilesSize = 0;
        Map<String, Integer> FilesSizeMap = new HashMap<>();
        for (ResourceFile file : retrievalList) {
            if(file.getFileNounitSize()!=null){
                FilesSize = FilesSize+ file.getFileNounitSize().intValue();
            }
        }
        FilesSizeMap.put("FilesSize",FilesSize);
        FilesSizeMap.put("FileCount",retrievalList.size());//文件个数


        /**计算用户登录次数*/
        Map<String, Integer> sysLogininfMap = new HashMap<>();
        SysLogininfor sysLogininfor = new SysLogininfor();
        sysLogininfor.setStatus("0");
        List<SysLogininfor> sysLogininfors = sysLogininforMapper.selectLogininforList(sysLogininfor);
        sysLogininfMap.put("LoginCount",sysLogininfors.size());

        /**用户上传次数(文件数量)*/
        Map<String, Integer> uploadCountMap = new HashMap<>();
        SysOperLog sysOperLogUpload = new SysOperLog();
        sysOperLogUpload.setBusinessType(11);//11为上传资源标识
        List<SysOperLog> uploadCount = operLogMapper.selectOperLogList(sysOperLogUpload);
        uploadCountMap.put("uploadCount",uploadCount.size());

        /**用户下载次数*/
        Map<String, Integer> downloadCountMap = new HashMap<>();
//        Integer downloadCount =  resourceFileMapper.getdownloadCount();
        SysOperLog sysOperLog = new SysOperLog();
        sysOperLog.setBusinessType(10);//10为下载资源标识
        List<SysOperLog> sysOperLogs = operLogMapper.selectOperLogList(sysOperLog);
        downloadCountMap.put("downloadCount",sysOperLogs.size());

        Date date = new Date(); //当前时间
        String week = getWeek(date);
        Date firstDayOfWeek = getFirstDayOfWeek(date);
        Date lastDayOfWeek = getLastDayOfWeek(date);

        /**=======================本周的上传，下载量 开始=======================*/
        Map<String, Map> weekStatisc = new HashMap<>();
        //按周 分组统计
        //过滤掉不在本周的数据
        List<SysOperLog> weekList = uploadCount.stream().filter(s -> s.getOperTime() != null
                && s.getOperTime().after(new Date(firstDayOfWeek.getTime()- 1 * 24 * 60 * 60 * 1000))
                && s.getOperTime().before(new Date(lastDayOfWeek.getTime()+ 1 * 24 * 60 * 60 * 1000))).collect(Collectors.toList());
        Calendar c = Calendar.getInstance();
        c.setTime(date);

        Set<String> timeTypes = new HashSet<>(Arrays.asList("星期一","星期二","星期三","星期四","星期五","星期六","星期日")); //周日期
        // 上传次数
        Map<Object, Long> collect1 = weekList.stream().collect(Collectors.groupingBy(item ->
                new SimpleDateFormat("EEEE").format(item.getOperTime()),Collectors.counting()));



        //过滤掉不在本周的数据
        List<SysOperLog> sysOperLogsList = sysOperLogs.stream().filter(s -> s.getOperTime() != null
                && s.getOperTime().after(new Date(firstDayOfWeek.getTime()- 1 * 24 * 60 * 60 * 1000))
                && s.getOperTime().before(new Date(lastDayOfWeek.getTime()+ 1 * 24 * 60 * 60 * 1000))).collect(Collectors.toList());
        //下载次数
        Map<Object, Long> collect2 = sysOperLogsList.stream().collect(Collectors.groupingBy(item ->
                new SimpleDateFormat("EEEE").format(item.getOperTime()),Collectors.counting()));

        for (String timeType : timeTypes) {
            collect1.putIfAbsent(timeType,0L);
            collect2.putIfAbsent(timeType,0L);
//            if(ObjectUtils.isEmpty(collect2.get(timeType))){
//                downMap.putIfAbsent(timeType,0L);
//            }else{
//                for (ResourceFile file : collect2.get(timeType)) {
//                    downMap.put(timeType,file.getDownloadCount().longValue());
//                }
//            }
        }
        weekStatisc.put("uploadWeekCount",collect1);
        weekStatisc.put("downloadWeekCount",collect2);
        /**=======================本周的上传，下载量 结束=======================*/


        StatisticsMap.put("weekStatisc", weekStatisc);
        StatisticsMap.put("downloadCount", downloadCountMap);
        StatisticsMap.put("uploadCount", uploadCountMap);
        StatisticsMap.put("LoginCount", sysLogininfMap);
        StatisticsMap.put("FilesSize", FilesSizeMap);
        StatisticsMap.put("percentage", percentage);
        return StatisticsMap;
    }

    /**
     * 文件检索
     * @param resourceFile
     * @param courseId
     * @param groupId
     * @return
     */
    @Override
    public List<ResourceFile> getResourceListByGroupIdAndCourseIdToSearch(ResourceFile resourceFile, Long courseId, Long groupId) {
        return resourceFileMapper.getResourceListByGroupIdAndCourseIdToSearch(resourceFile,courseId,groupId);
    }

    @Override
    public List<ResourceFile> getResourceListByUserIdAndCourseIdToSearch(ResourceFile resourceFile, Long courseId, Long userId) {
        return resourceFileMapper.getResourceListByUserIdAndCourseIdToSearch(resourceFile,courseId,userId);
    }

    @Override
    public List<ResourceFile> getResourceListByCourseIdToSearch(Long courseId, ResourceFile resourceFile) {
        return resourceFileMapper.getResourceListByCourseIdToSearch(courseId,resourceFile);
    }

    /**
     * 获取指定日期所在周的周一
     *
     * @param date 日期
     */
    public static Date getFirstDayOfWeek(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        if (c.get(Calendar.DAY_OF_WEEK) == 1) {
            c.add(Calendar.DAY_OF_MONTH, -1);
        }
        c.add(Calendar.DATE, c.getFirstDayOfWeek() - c.get(Calendar.DAY_OF_WEEK) + 1);
        return c.getTime();
    }

    /**
     * 获取指定日期所在周的周日
     *
     * @param date 日期
     */
    public static Date getLastDayOfWeek(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        // 如果是周日直接返回
        if (c.get(Calendar.DAY_OF_WEEK) == 1) {
            return date;
        }
        //System.out.println(c.get(Calendar.DAY_OF_WEEK));
        c.add(Calendar.DATE, 7 - c.get(Calendar.DAY_OF_WEEK) + 1);
        return c.getTime();
    }


    //根据日期取得星期几
    public static String getWeek(Date date){
        String[] weeks = {"星期日","星期一","星期二","星期三","星期四","星期五","星期六"};
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int week_index = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if(week_index<0){
            week_index = 0;
        }
        return weeks[week_index];
    }

    public static String typeName(Integer integer){
        if(integer==1){
            return "document";
        }
        if(integer==2){
            return "img";
        }
        if(integer==3){
            return "audio";
        }
        if(integer==4){
            return "video";
        }
        if(integer==5){
            return "ysb";
        }
        if(integer==7){
            return "code";
        }
        return "other";
    }

    /**
     * 计算数组中数字的比例
     *
     * @param arr
     * @return
     */
    public static List<Double> getDoubleRatio(double[] arr) {
        List<Double> ratioList = new ArrayList<>();
        NumberFormat instance = NumberFormat.getInstance();
        // 保留小数点后两位（四舍五入），
        instance.setMaximumFractionDigits(2);
        double sum = 0;
        // 求和
        for (int i = 0; i < arr.length; i++) {
            sum += arr[i];
        }
        // 计算各自所占百分比
        double ratioSum = 0;
        for (int i = 0; i < arr.length; i++) {
            if (arr.length > 1 && i == arr.length - 1) {
                String ratioStr = instance.format(1 - ratioSum);
                double retio = Double.parseDouble(ratioStr);
                ratioList.add(retio);
            } else {
                String ratioStr = instance.format(arr[i] / sum);
                double retio = Double.parseDouble(ratioStr);
                ratioSum = ratioSum + retio;
                ratioList.add(retio);
            }

        }
        return ratioList;
    }

    /**
     * 打开文件夹
     *
     * @param fId          文件夹id
     * @param resourceFile 文件实体
     * @return
     */
    @Override
    public List<ResourceFile> openFolder(Long fId, ResourceFile resourceFile) {
        //设置当前文件id为父id当做查询条件
        resourceFile.setParentId(fId);
        return resourceFileMapper.selectResourceFileList(resourceFile);
    }

    /**
     * 新建文件夹
     *
     * @param resourceFile 文件实体
     * @param courseId     课题id
     * @return
     */
    @Override
    @Transactional
    public int addFolder(ResourceFile resourceFile, Long courseId) {
        //获取当前用户的角色
        if (ShiroUtils.getSubject().hasRole("student")) {
            //学员上传的文件需要审核才能显示
            //修改文件的audit为1
            resourceFile.setAudit(1);
        }
        //验证文件夹在当前目录是否已存在
        if (verifyFileFolderUnion(resourceFile, courseId)) {
            return 0;
        }
        //新增文件到基础文件表
        this.insertResourceFile(resourceFile);
        //新增文件与课题关联
        ResourceCourseFile resourceCourseFile = new ResourceCourseFile();
        resourceCourseFile.setCourseId(courseId);
        resourceCourseFile.setFileId(resourceFile.getFileId());
        return courseFileMapper.insertResourceCourseFile(resourceCourseFile);
    }

    /**
     * 新建小组文件夹
     *
     * @param resourceFile
     * @param groupId
     * @return
     */
    @Override
    @Transactional
    public int addFolderByGroup(ResourceFile resourceFile, Long groupId) {
        //获取当前用户的角色
        if (ShiroUtils.getSubject().hasRole("student")) {
            //学员上传的文件需要审核才能显示
            //修改文件的audit为1
            resourceFile.setAudit(1);
        }
        //验证文件夹在当前目录是否已存在
        if (verifyFileFolderUnionByGroup(resourceFile, groupId)) {
            return 0;
        }
        //新增文件到基础文件表
        this.insertResourceFile(resourceFile);
        //新增文件与小组关联
        ResourceGroupFile resourceGroupFile = new ResourceGroupFile();
        resourceGroupFile.setGroupId(groupId);
        resourceGroupFile.setFileId(resourceFile.getFileId());
        return groupFileMapper.insertResourceGroupFile(resourceGroupFile);
    }

    /**
     * 个人新建文件夹
     *
     * @param resourceFile
     * @param userId
     * @return
     */
    @Override
    @Transactional
    public int addFolderByPerson(ResourceFile resourceFile, Long userId) {
        //验证文件夹在当前目录是否已存在
        if (verifyFileFolderUnionByPerson(resourceFile, userId)) {
            return 0;
        }
        //新增文件到基础文件表
        this.insertResourceFile(resourceFile);
        //新增文件与个人关联
        ResourcePersonFile resourcePersonFile = new ResourcePersonFile();
        resourcePersonFile.setStudentId(userId);
        resourcePersonFile.setFileId(resourceFile.getFileId());
        return personFileMapper.insertResourcePersonFile(resourcePersonFile);
    }


    /**
     * 保存【课题资源】上传的文件信息
     *
     * @param resourceFile
     * @return
     */
    @Override
    @Transactional
    public int addCResource(ResourceFile resourceFile, Long courseId, Long flag) {
        //获取当前用户的角色
        if (ShiroUtils.getSubject().hasRole("student")) {
            //学员上传的文件需要审核才能显示
            //修改文件的audit为1
            resourceFile.setAudit(1);
        }
        if (resourceFile.getParentId() == null) {
            resourceFile.setParentId(-1L);
        }
        //校验文件名称
        String newFileName = this.RefileName(resourceFile, courseId, flag);
        resourceFile.setFileName(newFileName);
        //新增文件到基础文件表
        this.insertResourceFile(resourceFile);
        //新增文件与课题关联
        ResourceCourseFile resourceCourseFile = new ResourceCourseFile();
        resourceCourseFile.setCourseId(courseId);
        resourceCourseFile.setFileId(resourceFile.getFileId());
        return courseFileMapper.insertResourceCourseFile(resourceCourseFile);
    }

    /**
     * 保存【小组资源】上传的文件信息
     *
     * @param resourceFile
     * @param groupId
     * @return
     */
    @Override
    @Transactional
    public int addGResource(ResourceFile resourceFile, Long groupId, Long flag) {
        //获取当前用户的角色
        if (ShiroUtils.getSubject().hasRole("student")) {
            //学员上传的文件需要审核才能显示
            //修改文件的audit为1
            resourceFile.setAudit(1);
        }
        if (resourceFile.getParentId() == null) {
            resourceFile.setParentId(-1L);
        }
        //校验文件名称
        String newFileName = this.RefileName(resourceFile, groupId, flag);
        resourceFile.setFileName(newFileName);
        //新增文件到基础文件表
        this.insertResourceFile(resourceFile);
        //新增文件与小组关联
        ResourceGroupFile resourceGroupFile = new ResourceGroupFile();
        resourceGroupFile.setGroupId(groupId);
        resourceGroupFile.setFileId(resourceFile.getFileId());
        return groupFileMapper.insertResourceGroupFile(resourceGroupFile);
    }

    /**
     * 保存【个人资源】上传的文件信息
     *
     * @param resourceFile
     * @param userId
     * @return
     */
    @Override
    public int addPResource(ResourceFile resourceFile, Long userId, Long flag) {
        if (resourceFile.getParentId() == null) {
            resourceFile.setParentId(-1L);
        }
        //校验文件名称
        String newFileName = this.RefileName(resourceFile, userId, flag);
        resourceFile.setFileName(newFileName);
        //新增文件到基础文件表
        this.insertResourceFile(resourceFile);
        //新增文件与个人关联
        ResourcePersonFile resourcePersonFile = new ResourcePersonFile();
        //根据用户id获取学员信息
        Student student = studentService.getStudentByUserId(userId);
        if(!ObjectUtils.isEmpty(student)){
            resourcePersonFile.setStudentId(student.getStudentId());
        }else{
            resourcePersonFile.setStudentId(userId);
        }
        resourcePersonFile.setFileId(resourceFile.getFileId());
        return personFileMapper.insertResourcePersonFile(resourcePersonFile);
    }

    /**
     * 判断【公共资源】是否有重复文件名，如果有则追加'副本'字样
     *
     * @param resourceFile
     * @return
     */
    public String RePublishFileName(ResourceFile resourceFile) {
        String fileName = resourceFile.getFileName();
        String suffix = fileName.substring(fileName.lastIndexOf(".") + 1);
        //获取最新的相同文件
        List<ResourceFile> resourceFiles = resourceFileMapper.rePublishFileName(resourceFile.getIdentifier());
        if (resourceFiles.size() > 0) {
            //拼接fileName
            return FileUtils.getNameNotSuffix(resourceFiles.get(0).getFileName()).concat("-副本.").concat(suffix);
        }
        return fileName;
    }

    /**
     * 判断是否有重复文件名，如果有则追加'副本'字样
     *
     * @param resourceFile
     * @param cgp          //课题，小组，个人
     * @param flag
     * @return
     */
    public String RefileName(ResourceFile resourceFile, Long cgp, Long flag) {
        String fileName = resourceFile.getFileName();
        String identifier = resourceFile.getIdentifier();
        Long parentId = resourceFile.getParentId();
        String suffix = fileName.substring(fileName.lastIndexOf(".") + 1);
        int i = 0;
        List<ResourceFile> resourceFiles = null;
        if (flag.equals(0L)) { //课题
            resourceFiles = resourceFileMapper.reCFileName(identifier, parentId, cgp);
            i++;
        } else if (flag.equals(1L)) { //小组
            resourceFiles = resourceFileMapper.reGFileName(identifier, parentId, cgp);
            i++;
        } else { //个人
            resourceFiles = resourceFileMapper.rePFileName(identifier, parentId, cgp);
            i++;
        }
        if (i > 0 && resourceFiles.size() > 0) {
            return FileUtils.getNameNotSuffix(resourceFiles.get(0).getFileName()).concat("-副本.").concat(suffix);
        }
        return fileName;
    }

    /**
     * 判断文件夹下是否还有文件
     *
     * @param fileId
     * @return
     */
    @Override
    public int havaFile(Long fileId) {
        return resourceFileMapper.havaFile(fileId);
    }


    /**
     * 验证文件夹的唯一性
     *
     * @param resourceFile
     * @param courseId
     * @return
     */
    public boolean verifyFileFolderUnion(ResourceFile resourceFile, Long courseId) {
        if (resourceFileMapper.verifyFileFolderUnion(resourceFile, courseId) > 0) {
            return true;
        }
        return false;
    }

    /**
     * 验证【小组】文件夹的唯一性
     *
     * @param resourceFile
     * @param groupId
     * @return
     */
    public boolean verifyFileFolderUnionByGroup(ResourceFile resourceFile, Long groupId) {
        if (groupFileMapper.verifyFileFolderUnionByGroup(resourceFile, groupId) > 0) {
            return true;
        }
        return false;
    }

    /**
     * 验证【个人】文件夹的唯一性
     *
     * @param resourceFile
     * @param userId
     * @return
     */
    public boolean verifyFileFolderUnionByPerson(ResourceFile resourceFile, Long userId) {
        if (personFileMapper.verifyFileFolderUnionByPerson(resourceFile, userId) > 0) {
            return true;
        }
        return false;
    }
}
