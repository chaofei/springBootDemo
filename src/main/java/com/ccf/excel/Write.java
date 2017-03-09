package com.ccf.excel;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.xssf.usermodel.*;

import java.io.*;
import java.util.Date;

/**
 * Created by chenchaofei on 2017/3/9.
 */
public class Write {
    /***
     * 创建表头
     *
     * @param workbook
     * @param sheet
     */
    private void createTitle(XSSFWorkbook workbook, XSSFSheet sheet) {
        XSSFRow row = sheet.createRow(0);
        //设置列宽，setColumnWidth的第二个参数要乘以256，这个参数的单位是1/256个字符宽度
        sheet.setColumnWidth(2, 12 * 256);
        sheet.setColumnWidth(3, 17 * 256);

        //设置为居中加粗
        XSSFCellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        style.setAlignment(XSSFCellStyle.ALIGN_CENTER);
        style.setFont(font);

        XSSFCell cell;
        cell = row.createCell(0);
        cell.setCellValue("序号");
        cell.setCellStyle(style);

        cell = row.createCell(1);
        cell.setCellValue("金额");
        cell.setCellStyle(style);

        cell = row.createCell(2);
        cell.setCellValue("描述");
        cell.setCellStyle(style);

        cell = row.createCell(3);
        cell.setCellValue("日期");
        cell.setCellStyle(style);
    }

    public String gen() throws IOException {
        String pathname = "/tmp/test.xlsx";
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("统计表");
        createTitle(workbook, sheet);

        //新增数据行，并且设置单元格数据
        Date dt = new Date();
        for (int rowNum = 1; rowNum < 100000; rowNum++) {
            XSSFRow row = sheet.createRow(rowNum);
            row.createCell(0).setCellValue(rowNum);
            row.createCell(1).setCellValue(100.00 + rowNum);
            row.createCell(2).setCellValue("描述" + rowNum);
            XSSFCell cell = row.createCell(3);
            cell.setCellValue(dt.toString());
//            cell.setCellStyle(style);
        }

        // 文件流
        File file = new File(pathname);
        OutputStream os = new FileOutputStream(file);
        os.flush();
        workbook.write(os);
        os.close();

        return pathname;

    }
}
