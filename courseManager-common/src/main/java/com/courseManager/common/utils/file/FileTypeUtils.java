package com.courseManager.common.utils.file;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

/**
 * 文件类型工具类
 *
 * @author ruoyi
 */
public class FileTypeUtils
{
    /**
     * 获取文件类型
     * <p>
     * 例如: ruoyi.txt, 返回: txt
     * 
     * @param file 文件名
     * @return 后缀（不含".")
     */
    public static String getFileType(File file)
    {
        if (null == file)
        {
            return StringUtils.EMPTY;
        }
        return getFileType(file.getName());
    }

    /**
     * 获取文件类型
     * <p>
     * 例如: ruoyi.txt, 返回: txt
     *
     * @param fileName 文件名
     * @return 后缀（不含".")
     */
    public static String getFileType(String fileName)
    {
        int separatorIndex = fileName.lastIndexOf(".");
        if (separatorIndex < 0)
        {
            return "";
        }
        return fileName.substring(separatorIndex + 1).toLowerCase();
    }

    /**
     * 获取文件类型
     * 
     * @param photoByte 文件字节码
     * @return 后缀（不含".")
     */
    public static String getFileExtendName(byte[] photoByte)
    {
        String strFileExtendName = "JPG";
        if ((photoByte[0] == 71) && (photoByte[1] == 73) && (photoByte[2] == 70) && (photoByte[3] == 56)
                && ((photoByte[4] == 55) || (photoByte[4] == 57)) && (photoByte[5] == 97))
        {
            strFileExtendName = "GIF";
        }
        else if ((photoByte[6] == 74) && (photoByte[7] == 70) && (photoByte[8] == 73) && (photoByte[9] == 70))
        {
            strFileExtendName = "JPG";
        }
        else if ((photoByte[0] == 66) && (photoByte[1] == 77))
        {
            strFileExtendName = "BMP";
        }
        else if ((photoByte[1] == 80) && (photoByte[2] == 78) && (photoByte[3] == 71))
        {
            strFileExtendName = "PNG";
        }
        return strFileExtendName;
    }

    /**
     * 多种文件类型判断
     */
    public final static Map<String, Integer> FILE_TYPE_MAP = new HashMap<String, Integer>();
    static
    {
        //文档类型
        FILE_TYPE_MAP.put("docx", 1);
        FILE_TYPE_MAP.put("doc", 1);
        FILE_TYPE_MAP.put("dot", 1);
        FILE_TYPE_MAP.put("txt", 1);
        FILE_TYPE_MAP.put("xltx", 1);
        FILE_TYPE_MAP.put("xls", 1);
        FILE_TYPE_MAP.put("xlsm", 1);
        FILE_TYPE_MAP.put("xlsx", 1);
        FILE_TYPE_MAP.put("xlsb", 1);
        FILE_TYPE_MAP.put("csv", 1);
        FILE_TYPE_MAP.put("xml", 1);
        FILE_TYPE_MAP.put("pptx", 1);
        FILE_TYPE_MAP.put("ppt", 1);
        FILE_TYPE_MAP.put("xps", 1);
        FILE_TYPE_MAP.put("key", 1);
        FILE_TYPE_MAP.put("pages", 1);
        FILE_TYPE_MAP.put("ai", 1);
        FILE_TYPE_MAP.put("pdf", 1);
        //图片类型
        FILE_TYPE_MAP.put("jpg", 2);
        FILE_TYPE_MAP.put("png", 2);
        FILE_TYPE_MAP.put("gif", 2);
        FILE_TYPE_MAP.put("psd", 2);
        FILE_TYPE_MAP.put("tif", 2);
        FILE_TYPE_MAP.put("bmp", 2);
        FILE_TYPE_MAP.put("jpeg", 2);
        //音频类型
        FILE_TYPE_MAP.put("mp3", 3);
        FILE_TYPE_MAP.put("wma", 3);
        FILE_TYPE_MAP.put("Aac", 3);
        FILE_TYPE_MAP.put("ogg", 3);
        FILE_TYPE_MAP.put("mpc", 3);
        FILE_TYPE_MAP.put("flac", 3);
        FILE_TYPE_MAP.put("wv", 3);
        FILE_TYPE_MAP.put("Ape", 3);
        FILE_TYPE_MAP.put("TTA", 3);
        FILE_TYPE_MAP.put("WMA", 3);
        FILE_TYPE_MAP.put("TAK", 3);
        FILE_TYPE_MAP.put("Shorten", 3);
        FILE_TYPE_MAP.put("OptimFROG", 3);
        FILE_TYPE_MAP.put("kgm", 3);
        FILE_TYPE_MAP.put("ncm", 3);
        FILE_TYPE_MAP.put("ogg", 3);
        FILE_TYPE_MAP.put("mflac", 3);
        //视频类型
        FILE_TYPE_MAP.put("avi", 4);
        FILE_TYPE_MAP.put("mp4", 4);
        FILE_TYPE_MAP.put("wmv", 4);
        FILE_TYPE_MAP.put("mpeg", 4);
        FILE_TYPE_MAP.put("m4v", 4);
        FILE_TYPE_MAP.put("mov", 4);
        FILE_TYPE_MAP.put("asf", 4);
        FILE_TYPE_MAP.put("flv", 4);
        FILE_TYPE_MAP.put("f4v", 4);
        FILE_TYPE_MAP.put("rmvb", 4);
        FILE_TYPE_MAP.put("rm", 4);
        FILE_TYPE_MAP.put("3gp", 4);
        FILE_TYPE_MAP.put("vob", 4);
        //压缩包类型
        FILE_TYPE_MAP.put("zip", 5);
        FILE_TYPE_MAP.put("rar", 5);
        FILE_TYPE_MAP.put("7z", 5);
        FILE_TYPE_MAP.put("tar.gz", 5);
        //代码类型
        FILE_TYPE_MAP.put("java", 7);
        FILE_TYPE_MAP.put("python", 7);
        FILE_TYPE_MAP.put("go", 7);

    }

    public static Integer getMutityType(String fileName) {
        String fileTyle=fileName.substring(fileName.lastIndexOf(".")+1,fileName.length());
        Integer integer = FILE_TYPE_MAP.get(fileTyle.toLowerCase());
        if(integer == null) {
            return 6;
        }
        return integer;
    }




}