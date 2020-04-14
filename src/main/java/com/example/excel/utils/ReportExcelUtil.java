package com.example.excel.utils;

import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

/**
 * @Description: 导出Excel
 * @Author: Joke
 * @Date: 2020/4/14
 */
public class ReportExcelUtil {
    private static Logger logger = LoggerFactory.getLogger(ReportExcelUtil.class);

    public static void reportFormExcel(HttpServletResponse response, Workbook wb, String name) {
        ServletOutputStream outputStream = null;
        try {
            response.setContentType("application/vnd.ms-excel; charset=utf-8");
            String date = DateTimeUtil.formatDate(new Date(), "yyyyMMddHHmmss");
            String fileName = name + date + ".xls";
            // 获得输出流
            outputStream = response.getOutputStream();
            fileName = new String((fileName).getBytes(), "ISO8859-1");
            response.setHeader("content-disposition", "attachment;filename=\"" + fileName + "\"");
            wb.write(outputStream);
            outputStream.flush();
        } catch (Exception e) {
            logger.error("导出报表失败", e);
            e.printStackTrace();
        } finally {
            if (wb != null) {
                try {
                    wb.close();
                } catch (IOException e) {
                    logger.equals("导出报表关闭Workbook失败");
                    e.printStackTrace();
                }
            }
            // 关闭输出流
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    logger.error("导出报表输出流关闭失败");
                    e.printStackTrace();
                }
            }
        }
    }
}
