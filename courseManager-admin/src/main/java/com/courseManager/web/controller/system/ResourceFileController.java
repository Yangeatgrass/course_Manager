package com.courseManager.web.controller.system;

import java.io.*;
import java.util.List;

import com.courseManager.common.config.RuoYiConfig;
import com.courseManager.common.config.ServerConfig;
import com.courseManager.common.constant.Constants;
import com.courseManager.common.core.text.Convert;
import com.courseManager.common.utils.ShiroUtils;
import com.courseManager.common.utils.StringUtils;
import com.courseManager.common.utils.file.FileUtils;
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
 * 文件资源Controller
 *
 * @author ruoyi
 * @date 2023-04-07
 */
@Controller
@RequestMapping("/system/file")
public class ResourceFileController extends BaseController {
    private String prefix = "system/file";

    private static final Logger log = LoggerFactory.getLogger(ResourceFileController.class);


    @Autowired
    private IResourceFileService resourceFileService;

    @Autowired
    private ServerConfig serverConfig;

    @RequiresPermissions("system:resource:view")
    @GetMapping()
    public String file() {
        return prefix + "/file";
    }

    /**
     * PDF预览
     */
//    @RequiresPermissions("system:resource:preview")
    @PostMapping("/pdfPreview/{fId}")
    @ResponseBody
    public AjaxResult pdfPreview(@PathVariable Long fId) {
        //根据文件id查询文件
        ResourceFile resourceFile = resourceFileService.selectResourceFileByFileId(fId);
        String s = serverConfig.getUrl() + resourceFile.getFilePath();
        return success(s);
    }


    /**
     * 保存【个人资源】上传的文件信息
     *
     * @param resourceFile
     * @return
     */
    @RequiresPermissions("system:resource:add")
    @PostMapping("/addPResource")
    @ResponseBody
    public AjaxResult addPResource(ResourceFile resourceFile, Long userId, Long flag) {
        if (resourceFileService.addPResource(resourceFile, userId, flag) > 0) {
            return success("上传成功!");
        }
        return error("上传异常!");
    }

    /**
     * 保存【小组资源】上传的文件信息
     *
     * @param resourceFile
     * @return
     */
    @RequiresPermissions("system:resource:add")
    @PostMapping("/addGResource")
    @ResponseBody
    public AjaxResult addGResource(ResourceFile resourceFile, Long groupId, Long flag) {
        if (resourceFileService.addGResource(resourceFile, groupId, flag) > 0) {
            if (ShiroUtils.getSubject().hasRole("student")) {
                return success("上传成功,请等待审核!");
            }
            return success("上传成功!");
        }
        return error("上传异常!");
    }

    /**
     * 保存【课题资源】上传的文件信息
     *
     * @param resourceFile
     * @return
     */
    @RequiresPermissions("system:resource:add")
    @PostMapping("/addCResource")
    @ResponseBody
    public AjaxResult addCResource(ResourceFile resourceFile, Long courseId, Long flag) {
        if (resourceFileService.addCResource(resourceFile, courseId, flag) > 0) {
            if (ShiroUtils.getSubject().hasRole("student")) {
                return success("上传成功,请等待审核!");
            }
            return success("上传成功!");
        }
        return error("上传异常!");
    }

    /**
     * 新建文件夹
     */
    @RequiresPermissions("system:resource:addfolder")
    @PostMapping("/addFolder")
    @ResponseBody
    public AjaxResult addFolder(ResourceFile resourceFile, Long courseId, Long groupId, Long userId) {
        if (null != groupId) {
            if (resourceFileService.addFolderByGroup(resourceFile, groupId) <= 0) {
                return error("新建失败，请检查文件夹名称是否重复!");
            }
        } else if (null != userId) {
            if (resourceFileService.addFolderByPerson(resourceFile, userId) <= 0) {
                return error("新建失败，请检查文件夹名称是否重复!");
            }
        } else {
            if (resourceFileService.addFolder(resourceFile, courseId) <= 0) {
                return error("新建失败，请检查文件夹名称是否重复!");
            }
        }
        if (ShiroUtils.getSubject().hasRole("student")) {
            return success("新建文件夹成功，请等待审核!");
        }
        return success("新建文件夹成功!");
    }

    /**
     * 打开文件夹
     */
    @RequiresPermissions("system:resource:list")
    @PostMapping("/openFolder/{fId}")
    @ResponseBody
    public TableDataInfo openFolder(@PathVariable Long fId, ResourceFile resourceFile) {
        startPage();
        List<ResourceFile> list = resourceFileService.openFolder(fId, resourceFile);
        return getDataTable(list);
    }

    /**
     * 小组资源【审核】列表
     */
    @RequiresPermissions("system:resource:audit")
    @PostMapping("/getGroupAuditList/{groupId}")
    @ResponseBody
    public TableDataInfo getGroupAuditList(@PathVariable Long groupId, ResourceFile resourceFile) {
        startPage();
        List<ResourceFile> list = resourceFileService.getGroupAuditList(resourceFile, groupId);
        return getDataTable(list);
    }

    /**
     * 课题资源【审核】列表
     */
    @RequiresPermissions("system:resource:audit")
    @PostMapping("/getCourseAuditList/{courseId}")
    @ResponseBody
    public TableDataInfo getCourseAuditList(@PathVariable Long courseId, ResourceFile resourceFile) {
        startPage();
        List<ResourceFile> list = resourceFileService.getCourseAuditList(resourceFile, courseId);
        return getDataTable(list);
    }

    /**
     * 小组文件资源列表
     */
    @RequiresPermissions("system:resource:list")
    @PostMapping("/getResourceListByGroupIdAndCourseId/{courseId}")
    @ResponseBody
    public TableDataInfo getResourceListByGroupIdAndCourseId(@PathVariable Long courseId, Long groupId, ResourceFile resourceFile) {
        startPage();
        List<ResourceFile> list = null;
        if(null!=resourceFile.getParentId()){
            //打开文件夹操作
            list = resourceFileService.getResourceListByGroupIdAndCourseId(resourceFile, courseId, groupId);
        }else{
            //搜索操作
            list = resourceFileService.getResourceListByGroupIdAndCourseIdToSearch(resourceFile, courseId, groupId);
        }
        return getDataTable(list);
    }

    /**
     * 个人文件资源列表
     */
    @RequiresPermissions("system:resource:list")
    @PostMapping("/getResourceListByUserIdAndCourseId/{courseId}")
    @ResponseBody
    public TableDataInfo getResourceListByUserIdAndCourseId(@PathVariable Long courseId, Long userId, ResourceFile resourceFile) {
        startPage();
        List<ResourceFile> list = null;
        if(null!=resourceFile.getParentId()){
            //打开文件夹操作
            list = resourceFileService.getResourceListByUserIdAndCourseId(resourceFile, courseId, userId);
        }else{
            //搜索操作
            list = resourceFileService.getResourceListByUserIdAndCourseIdToSearch(resourceFile, courseId, userId);
        }
        return getDataTable(list);
    }

    /**
     *修改文件审核状态
     */
    @RequiresPermissions("system:resource:audit")
    @PostMapping("/changeStatus")
    @ResponseBody
    public AjaxResult changeStatus(Long fileId,Integer audit) {
       return toAjax(resourceFileService.changeStatus(fileId,audit));
    }

    /**
     * 查询课题文件资源列表
     */
    @RequiresPermissions("system:resource:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(ResourceFile resourceFile) {
        startPage();
        List<ResourceFile> list = resourceFileService.selectResourceFileList(resourceFile);
        return getDataTable(list);
    }

    /**
     * 导出文件资源列表
     */
    @RequiresPermissions("system:resource:export")
    @Log(title = "文件资源", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(ResourceFile resourceFile) {
        List<ResourceFile> list = resourceFileService.selectResourceFileList(resourceFile);
        ExcelUtil<ResourceFile> util = new ExcelUtil<ResourceFile>(ResourceFile.class);
        return util.exportExcel(list, "文件资源数据");
    }

    /**
     * 新增文件资源
     */
    @GetMapping("/add")
    public String add() {
        return prefix + "/add";
    }

    /**
     * 新增保存文件资源
     */
    @RequiresPermissions("system:resource:add")
    @Log(title = "文件资源", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(ResourceFile resourceFile) {
        return toAjax(resourceFileService.insertResourceFile(resourceFile));
    }

    /**
     * 修改文件资源
     */
    @RequiresPermissions("system:resource:edit")
    @GetMapping("/edit/{fileId}")
    public String edit(@PathVariable("fileId") Long fileId, ModelMap mmap) {
        ResourceFile resourceFile = resourceFileService.selectResourceFileByFileId(fileId);
        mmap.put("resourceFile", resourceFile);
        return prefix + "/edit";
    }

    /**
     * 修改保存文件资源
     */
    @RequiresPermissions("system:resource:edit")
    @Log(title = "文件资源", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(ResourceFile resourceFile) {
        return toAjax(resourceFileService.updateResourceFile(resourceFile));
    }

    /**
     * 删除文件资源
     */
    @RequiresPermissions("system:resource:remove")
    @Log(title = "文件资源", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        Integer[] integers = Convert.toIntArray(ids);
        for (Integer integer : integers) {
            ResourceFile resourceFile = resourceFileService.selectResourceFileByFileId(integer.longValue());
            if (resourceFile.getFileType().equals(-1) && resourceFileService.havaFile(resourceFile.getFileId()) > 0) {
                return error("文件夹中还有文件，无法删除!");
            }
        }
        return toAjax(resourceFileService.deleteResourceFileByFileIds(ids));
    }
}
