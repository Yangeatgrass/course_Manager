package com.courseManager.web.controller.system;

import java.io.*;
import java.util.Collections;
import java.util.List;

import com.courseManager.common.config.RuoYiConfig;
import com.courseManager.common.config.ServerConfig;
import com.courseManager.common.constant.Constants;
import com.courseManager.common.core.domain.entity.SysUser;
import com.courseManager.common.core.text.Convert;
import com.courseManager.common.utils.StringUtils;
import com.courseManager.common.utils.file.FileUtils;
import com.courseManager.system.mapper.SysUserMapper;
import com.courseManager.system.service.IResourcePublicFileService;
import com.courseManager.system.service.ISysUserService;
import com.courseManager.web.controller.common.CommonController;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import com.courseManager.common.annotation.Log;
import com.courseManager.common.enums.BusinessType;
import com.courseManager.system.domain.ResourceFile;
import com.courseManager.system.service.IResourceFileService;
import com.courseManager.common.core.controller.BaseController;
import com.courseManager.common.core.domain.AjaxResult;
import com.courseManager.common.utils.poi.ExcelUtil;
import com.courseManager.common.core.page.TableDataInfo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 资源管控controller
 *
 * @author ruoyi
 * @date 2023-04-07
 */
@Controller
@RequestMapping("/system/resource")
public class ResourceGovernanceController extends BaseController {

    private String prefix = "system/resource";

    private static final Logger log = LoggerFactory.getLogger(ResourceGovernanceController.class);

    @Autowired
    private IResourceFileService resourceFileService;

    @Autowired
    private IResourcePublicFileService publicFileService;

    @Autowired
    private ISysUserService sysUserService;

    @Autowired
    private SysUserMapper sysUserMapper;

    /**
     * 进入公共资源管理
     * @return
     */
    @RequiresPermissions("system:publish:view")
    @GetMapping("/publish")
    public String publish() {
        return prefix + "/publish/list";
    }

    /**
     * 进入公共文件权限设置
     * @return
     */
    @RequiresPermissions("system:publish:assign")
    @GetMapping("/setAssign/{fId}/{visible}")
    public String setAssign(@PathVariable Long fId,@PathVariable Integer visible,ModelMap mmap) {
        mmap.put("fId",fId);
        mmap.put("visible",visible);
        mmap.put("users",sysUserService.getusersByAssignPublic(new SysUser(), fId));
        return prefix + "/publish/setAssign";
    }

    /**
     * 上传公共资源页面
     * @return
     */
    @RequiresPermissions("system:publish:upload")
    @GetMapping("/updatePublishResource")
    public String updatePublishResource()
    {
        return prefix + "/publish/updatePublishResource";
    }

    /**
     * 根据文件id查询拥有该文件权限的用户
     */
    @RequiresPermissions("system:publish:assign")
    @PostMapping("/getAssignedStudents/{fId}")
    @ResponseBody
    public TableDataInfo getAssignedStudents(SysUser sysUser,@PathVariable Long fId) {
        startPage();
        List<SysUser> users = sysUserService.getusersByAssignPublic(sysUser, fId);
        return getDataTable(users);
    }

    /**
     * 【公共资源】设为全部人可见
     */
    @RequiresPermissions("system:publish:list")
    @PostMapping("/assignAll")
    @ResponseBody
    public AjaxResult assignAll(Long fId) {
        if(publicFileService.assignAll(fId)>0){
            return success("设置成功！");
        }
        return success("设置失败！");
    }

    /**
     * 查询【公共】资源列表
     */
    @RequiresPermissions("system:publish:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo publishList(ResourceFile resourceFile) {
        startPage();
        List<ResourceFile> list = resourceFileService.getPublishList(resourceFile);
        return getDataTable(list);
    }

    /**
     * 根据不同学员id查询可见的【公共】资源列表
     */
    @RequiresPermissions("system:publish:StudentList")
    @PostMapping("/StudentVisibleList")
    @ResponseBody
    public TableDataInfo StudentVisibleList(ResourceFile resourceFile) {
        startPage();
        List<ResourceFile> list = resourceFileService.getPublishListByUserId(resourceFile);
        return getDataTable(list);
    }

    /**
     * 保存【公共】文件权限信息
     */
    @RequiresPermissions("system:publish:assign")
    @PostMapping("/saveAssignUser")
    @ResponseBody
    public AjaxResult saveAssignUser(Long fileId,Long[] uIds) {
        if(resourceFileService.saveAssignUser(fileId,uIds)>0){
            return success("保存成功!");
        }
        return error("保存失败!");
    }

    /**
     * 保存【公共】文件信息
     */
    @RequiresPermissions("system:publish:upload")
    @PostMapping("/savePublishFile")
    @ResponseBody
    public AjaxResult savePublishFile(ResourceFile resourceFile) {
        if(resourceFileService.savePublishFile(resourceFile)>0){
            return success("上传成功!");
        }
        return error("上传失败!");
    }

    /**
     * 删除【公共】资源
     */
    @RequiresPermissions("system:publish:remove")
    @Log(title = "文件资源", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String fIds) {
        return toAjax(resourceFileService.deletePublishFileByFileIds(fIds));
    }

}
