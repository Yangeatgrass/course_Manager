package com.courseManager.web.controller.common;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.courseManager.common.annotation.Log;
import com.courseManager.common.enums.BusinessType;
import com.courseManager.common.utils.file.FileTypeUtils;
import com.courseManager.system.domain.ResourceFile;
import com.courseManager.system.mapper.ResourceFileMapper;
import com.courseManager.system.service.IResourceFileService;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import com.courseManager.common.config.RuoYiConfig;
import com.courseManager.common.config.ServerConfig;
import com.courseManager.common.constant.Constants;
import com.courseManager.common.core.domain.AjaxResult;
import com.courseManager.common.utils.StringUtils;
import com.courseManager.common.utils.file.FileUploadUtils;
import com.courseManager.common.utils.file.FileUtils;

/**
 * 通用请求处理
 * 
 * @author ruoyi
 */
@Controller
@RequestMapping("/common")
public class CommonController
{
    private static final Logger log = LoggerFactory.getLogger(CommonController.class);

    @Autowired
    private ServerConfig serverConfig;

    @Autowired
    private ResourceFileMapper resourceFileMapper;

    private static final String FILE_DELIMETER = ",";

    /**
     * 通用下载请求
     * 
     * @param fileName 文件名称
     * @param delete 是否删除
     */
    @GetMapping("/download")
    public void fileDownload(String fileName, Boolean delete, HttpServletResponse response, HttpServletRequest request)
    {
        try
        {
            if (!FileUtils.checkAllowDownload(fileName))
            {
                throw new Exception(StringUtils.format("文件名称({})非法，不允许下载。 ", fileName));
            }
            String realFileName = System.currentTimeMillis() + fileName.substring(fileName.indexOf("_") + 1);
            String filePath = RuoYiConfig.getDownloadPath() + fileName;

            response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
            FileUtils.setAttachmentResponseHeader(response, realFileName);
            FileUtils.writeBytes(filePath, response.getOutputStream());
            if (delete)
            {
                FileUtils.deleteFile(filePath);
            }
        }
        catch (Exception e)
        {
            log.error("下载文件失败", e);
        }
    }

    /**
     * 通用上传请求（单个）
     */
    @PostMapping("/upload")
    @ResponseBody
    public AjaxResult uploadFile(MultipartFile file) throws Exception
    {
        try
        {
            // 上传文件路径
            String filePath = RuoYiConfig.getUploadPath();
            // 上传并返回新文件名称
            String fileName = FileUploadUtils.upload(filePath, file);
            String url = serverConfig.getUrl() + fileName;
            AjaxResult ajax = AjaxResult.success();
            ajax.put("url", url);
            ajax.put("fileName", fileName);
            ajax.put("newFileName", FileUtils.getName(fileName));
            ajax.put("originalFilename", file.getOriginalFilename());
            return ajax;
        }
        catch (Exception e)
        {
            return AjaxResult.error(e.getMessage());
        }
    }

    /**
     * 通用上传请求（多个）
     */
    @PostMapping("/uploads")
    @ResponseBody
    public AjaxResult uploadFiles(List<MultipartFile> files) throws Exception
    {
        try
        {
            // 上传文件路径
            String filePath = RuoYiConfig.getUploadPath();
            List<String> urls = new ArrayList<String>();
            List<String> fileNames = new ArrayList<String>();
            List<String> newFileNames = new ArrayList<String>();
            List<String> originalFilenames = new ArrayList<String>();
            for (MultipartFile file : files)
            {
                // 上传并返回新文件名称
                String fileName = FileUploadUtils.upload(filePath, file);
                String url = serverConfig.getUrl() + fileName;
                urls.add(url);
                fileNames.add(fileName);
                newFileNames.add(FileUtils.getName(fileName));
                originalFilenames.add(file.getOriginalFilename());
            }
            AjaxResult ajax = AjaxResult.success();
            ajax.put("urls", StringUtils.join(urls, FILE_DELIMETER));
            ajax.put("fileNames", StringUtils.join(fileNames, FILE_DELIMETER));
            ajax.put("newFileNames", StringUtils.join(newFileNames, FILE_DELIMETER));
            ajax.put("originalFilenames", StringUtils.join(originalFilenames, FILE_DELIMETER));
            return ajax;
        }
        catch (Exception e)
        {
            return AjaxResult.error(e.getMessage());
        }
    }

    /**
     * 【资源】多文件上传
     * @param files
     * @return
     * @throws Exception
     */
    @Log(title = "资源上传", businessType = BusinessType.UPLOAD)
    @RequiresPermissions("system:resource:add")
    @PostMapping("/ResourceUploadFiles")
    @ResponseBody
    public AjaxResult ResourceUploadFiles(List<MultipartFile> files) throws Exception
    {
        try
        {
            // 上传文件路径
            String filePath = RuoYiConfig.getUploadPath();
            List<String> urls = new ArrayList<String>();
            List<String> fileNames = new ArrayList<String>();
            List<String> newFileNames = new ArrayList<String>();
            List<String> originalFilenames = new ArrayList<String>();
            List<Integer> fileTypes = new ArrayList<Integer>();
            List<String> MD5Flags = new ArrayList<String>();
            List<String> fileSizes = new  ArrayList<String>();
            List<Long> fileSizesNoUnit = new  ArrayList<Long>();
            for (MultipartFile file : files)
            {
                String md5 = FileUtils.getMd5(file);
                //查询所有文件的md5，如果遇到一致的则返回已上传的，避免上传重复的资源
                ResourceFile resourceFile = resourceFileMapper.selectFileMd5Flag(md5); //此文件是最新的一条
                if(ObjectUtils.isEmpty(resourceFile)){
                    //上传并返回新文件名称
                    String fileName = FileUploadUtils.upload(filePath, file);
                    String url = serverConfig.getUrl() + fileName; //加地址
                    urls.add(fileName);
                    fileNames.add(FileUtils.getName(file.getOriginalFilename()));
                    newFileNames.add(FileUtils.getName(fileName));
                    originalFilenames.add(file.getOriginalFilename());
                    //文件类型
                    fileTypes.add(FileTypeUtils.getMutityType(file.getOriginalFilename()));
                    //MD5标识
                    MD5Flags.add(md5);
                    //文件大小
                    fileSizes.add(FileUtils.getFileSize(file.getSize(),2,null));
                    fileSizesNoUnit.add(file.getSize());//未换算
                }else{
                    //不重复上传
                    urls.add(resourceFile.getFilePath());
                    originalFilenames.add(resourceFile.getFileName());
                    //文件类型
                    fileTypes.add(resourceFile.getFileType());
                    //MD5标识
                    MD5Flags.add(md5);
                    //文件大小
                    fileSizes.add(resourceFile.getFileSize());
                    fileSizesNoUnit.add(resourceFile.getFileNounitSize());//未换算
                }


            }
            AjaxResult ajax = AjaxResult.success();
            ajax.put("urls",urls);
            ajax.put("fileNames", fileNames);
            ajax.put("newFileNames", newFileNames);
            ajax.put("originalFilenames", originalFilenames);
            ajax.put("fileTypes", fileTypes);
            ajax.put("MD5Flags", MD5Flags);
            ajax.put("fileSizes", fileSizes);
            ajax.put("fileSizesNoUnit", fileSizesNoUnit);
            return ajax;
        }
        catch (Exception e)
        {
            return AjaxResult.error(e.getMessage());
        }
    }

    /**
     * 本地资源通用下载
     */
    @Log(title = "资源下载", businessType = BusinessType.DOWNLOAD)
    @GetMapping("/download/resource")
    public void resourceDownload(Long fileId, HttpServletRequest request, HttpServletResponse response)
            throws Exception
    {
        try
        {
            //获取文件
            ResourceFile resourceFile = resourceFileMapper.selectResourceFileByFileId(fileId);
            String filePath = resourceFile.getFilePath();
            if (!FileUtils.checkAllowDownload(filePath))
            {
                throw new Exception(StringUtils.format("资源文件({})非法，不允许下载。 ", filePath));
            }
            // 本地资源路径
            String localPath = RuoYiConfig.getProfile();
            // 数据库资源地址
            String downloadPath = localPath + StringUtils.substringAfter(filePath, Constants.RESOURCE_PREFIX);
            // 下载名称
            String downloadName = StringUtils.substringAfterLast(downloadPath, "/");
            response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
            FileUtils.setAttachmentResponseHeader(response, downloadName);
            FileUtils.writeBytes(downloadPath, response.getOutputStream());
            //下载次数增加
            resourceFile.setDownloadCount(resourceFile.getDownloadCount()+1);
            resourceFileMapper.updateResourceFile(resourceFile);
        }
        catch (Exception e)
        {
            log.error("下载文件失败", e);
        }
    }

}
