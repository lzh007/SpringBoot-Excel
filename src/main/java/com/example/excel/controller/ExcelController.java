package com.example.excel.controller;

import com.example.excel.model.User;
import com.example.excel.service.UserService;
import com.example.excel.utils.ExcelUtil;
import com.example.excel.utils.ReportExcelUtil;
import com.example.excel.utils.Result;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description: ExcelController
 * @Author: Joke
 * @Date: 2020/4/14
 */
@RestController
@RequestMapping(value = "/excelCommand")
public class ExcelController {
    private static final Logger LOG = LoggerFactory.getLogger(ExcelController.class);

    @Autowired
    private UserService userService;
    
    @ApiOperation(value = "导入excel", httpMethod = "POST")
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    @ResponseBody
    public Result<?> importExcel(
            @ApiParam("文件上传") @RequestParam("uploadFile") MultipartFile uploadFile) {
        List<User> data = null;
        try {
            data = this.getExcelData(uploadFile);
        } catch (Exception e) {
            LOG.error("读取导入excel有问题，请检查excel", e);
            return Result.fail("500", "读取导入excel有问题，请检查excel");
        }
        if (null != data) {
            //保存
            userService.saveAll(data);
            
        } else {
            return Result.fail("500", "请求参数不正确");
        }
        return Result.success("200", "导入成功");
    }

    private List<User> getExcelData(MultipartFile uploadFile) {
        Workbook workbook = ExcelUtil.getWorkBook(uploadFile);
        Sheet sheet = workbook.getSheetAt(0);
        int lastRowNum = sheet.getLastRowNum();

        List<User> importDtos = new ArrayList<>();
        for (int rowNum = 1; rowNum <= lastRowNum; rowNum++) {
            Row row = sheet.getRow(rowNum);
            User user = new User();
            if (null != row.getCell(0)) {
                user.setUserName(row.getCell(0).toString());
            }
            if (null != row.getCell(1)) {
                user.setPassWord(row.getCell(1).toString());
            }
            if (null != row.getCell(2)) {
                user.setQq(row.getCell(2).toString());
            }
            importDtos.add(user);
        }
        return importDtos;
    }


    @ApiOperation(value = "导出excel", httpMethod = "GET")
    @RequestMapping(value = "/exportExcel", method = RequestMethod.GET)
    public void exportExcel(
            @ApiParam(value = "页码", defaultValue = "1") @RequestParam(value = "pageIndex", required = false, defaultValue = "1") int pageIndex,
            @ApiParam(value = "每页记录数", defaultValue = "3000") @RequestParam(value = "pageSize", defaultValue = "3000") int pageSize,
            HttpServletResponse response
    ) {
        Pageable pageable = PageRequest.of(pageIndex, pageSize, Sort.Direction.DESC, "id");

        Page<User> dtoList = userService.findAllUser(pageable);
        if (CollectionUtils.isEmpty(dtoList.getContent())) {
            LOG.error("无数据");
            return;
        }
        Workbook wb = null;
        try {
            // 创建 wb
            wb = new HSSFWorkbook();
            Sheet sheet = null;
            // 导出报表的名称
            String name = "用户表";
            sheet = wb.createSheet(name);
            reportForm(dtoList.getContent(), sheet);
            if (wb != null) {
                ReportExcelUtil.reportFormExcel(response, wb, name);
            }
        } catch (Exception e) {
            LOG.error("导出 用户表失败", e);
            e.printStackTrace();
        } finally {
            if (wb != null) {
                try {
                    wb.close();
                } catch (IOException e) {
                    LOG.error("导出 用户表 记录报表关闭Workbook失败", e);
                    e.printStackTrace();
                }
            }
        }

    }


    private void reportForm(List<User> dtoList, Sheet sheet) {
        // 表头
        Row headRow = sheet.createRow(0);
        headRow.createCell(0).setCellValue("用户姓名");
        headRow.createCell(1).setCellValue("用户密码");
        headRow.createCell(2).setCellValue("用户QQ");

        for (User user : dtoList) {
            Row dataRow = sheet.createRow(sheet.getLastRowNum() + 1);
            //用户姓名
            dataRow.createCell(0).setCellValue(user.getUserName());
            //用户密码
            dataRow.createCell(1).setCellValue(user.getPassWord());
            //用户QQ
            dataRow.createCell(2).setCellValue(user.getQq());
        }
    }


}
