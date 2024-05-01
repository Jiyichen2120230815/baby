package com.babyTalk.base.utils.excel;

import com.alibaba.excel.EasyExcel;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;

public class ExcelUtil {

    public static void generateExcel(HttpServletResponse response,List<?> data) throws IOException {
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        // 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
        String fileName = URLEncoder.encode("接种人员信息", "UTF-8").replaceAll("\\+", "%20");
        System.out.println(fileName);
        response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");
        EasyExcel.write(response.getOutputStream(), data.get(0).getClass()).sheet("接种人员信息").doWrite(data);
    }
}
