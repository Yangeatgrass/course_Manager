package com.courseManager.common.utils.page;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.courseManager.common.core.page.PageDomain;
import com.courseManager.common.core.page.TableDataInfo;
import com.courseManager.common.core.page.TableSupport;
import com.courseManager.common.utils.sql.SqlUtil;
import org.apache.poi.ss.formula.functions.T;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 分页工具
 */
public class PageUtils {

    public static TableDataInfo mySetPage(List list,PageDomain pageDomain) {

        Integer pageNum = pageDomain.getPageNum();
        Integer pageSize = pageDomain.getPageSize();
        TableDataInfo rspData = new TableDataInfo();
        rspData.setCode(0);

        //1.计算出需要跳过多少条数据（流切片的起始位置）
        int startPosition = (pageNum - 1) * pageSize;
        Stream<T> stream = list.stream().skip(startPosition).limit(pageSize);

        List<T> resultAccountList = stream.collect(Collectors.toList());



        rspData.setRows(resultAccountList);
        rspData.setTotal(new PageInfo(list).getTotal());

        return rspData;

    }


    public static List myStartPage(List list, Integer pageNum, Integer pageSize) {
        if (list == null) {
            return null;
        }
        if (list.size() == 0) {
            return null;
        }
        Integer count = list.size();//
        Integer pageCount = 0;//
        if (count % pageSize == 0) {
            pageCount = count / pageSize;
        } else {
            pageCount = count / pageSize + 1;
        }
        int fromIndex = 0;//
        int toIndex = 0;//
        if (pageNum != pageCount) {
            fromIndex = (pageNum - 1) * pageSize;
            toIndex = fromIndex + pageSize;
        } else {
            fromIndex = (pageNum - 1) * pageSize;
            toIndex = count;
        }
        List pageList = list.subList(fromIndex, toIndex);
        return pageList;
    }

    /**
     * @param pageSize 每页显示的数量
     * @param pageNum  当前页码
     * @Description: Java8 Stream 分页
     * <br> 1. 起始位置边界值处理： 同 subList 方法
     * <br> 2. 终止位置：无需处理，会自动处理边界问题
     * @version v1.0
     * @author wu
     * @date 2022/7/31 11:49
     */
    private static List<?> subListJava8(List<?> list, int pageSize, int pageNum) {
        int count = list.size(); // 总记录数
        // 计算总页数
        int pages = count % pageSize == 0 ? count / pageSize : count / pageSize + 1;
        // 起始位置
        int start = pageNum <= 0 ? 0 : (pageNum > pages ? (pages - 1) * pageSize : (pageNum - 1) * pageSize);
        // 终止位置
        int end = pageSize;
        return list.stream().skip(start).limit(pageSize).collect(Collectors.toList());
    }


}
