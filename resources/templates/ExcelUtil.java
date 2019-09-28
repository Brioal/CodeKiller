package com.atmo.atmo.util;

import com.atmo.atmo.bean.system.FileBean;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;

public class ExcelUtil {

    private static Logger logger = LoggerFactory.getLogger(ExcelUtil.class);


    public static void readExcel(FileBean fileBean, OnExcelReadListener onExcelReadListener) {
        logger.info("开始读取Excel文件");
        File excelFile = fileBean.getLocalFile();
        if (excelFile == null) {
            logger.error("文件不存在,读取Excel终止");
            onExcelReadListener.fileNotExit();
            return;
        }
        // 读取excel
        XSSFWorkbook workbook = null;
        try {
            workbook = new XSSFWorkbook(new FileInputStream(excelFile));
            // 获得工作表
            XSSFSheet sheet = workbook.getSheetAt(0);
            int rowCount = sheet.getPhysicalNumberOfRows();
            logger.info("表格行数:" + rowCount);
            // 获取第i行数据
            for (int i = 0; i < rowCount; i++) {
                logger.info("当前正在读取第" + i + "行");
                XSSFRow row = sheet.getRow(i);
                int colCount = row.getPhysicalNumberOfCells();
                logger.info("当前行一共" + colCount + "列");
                String[] array = new String[colCount];
                for (int j = 0; j < colCount; j++) {
                    // 数据读取
                    String value = getCellValue(row.getCell(j));
                    logger.info("第" + i + "行 第" + j + "列 数据为:" + value);
                    array[j] = value;
                }
                // 回调
                onExcelReadListener.read(i, array);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getCellValue(XSSFCell cell) {
        String cellValue = "";
        if (cell == null) {
            return cellValue;
        }
        // 判断数据的类型
        switch (cell.getCellType()) {
            case NUMERIC: // 数字
                cellValue = String.valueOf(cell.getNumericCellValue());
                if (TextUtil.isNotValid(cellValue)) {
                    cellValue = "0";
                }
                break;
            case STRING: // 字符串
                cellValue = String.valueOf(cell.getStringCellValue());
                break;
            case BOOLEAN: // Boolean
                cellValue = String.valueOf(cell.getBooleanCellValue());
                if (TextUtil.isNotValid(cellValue)) {
                    cellValue = "false";
                }
                break;
            case FORMULA: // 公式
                cellValue = String.valueOf(cell.getCellFormula());
                break;
            case BLANK: // 空值
                cellValue = "";
                break;
            case ERROR: // 故障
                cellValue = "非法字符";
                break;
            default:
                cellValue = "";
                break;
        }

        return cellValue;
    }


    /**
     * 获取字符串值
     *
     * @param cell
     * @return
     */
    public static String getStringValueFromCell(XSSFCell cell, boolean turnInt) {
        if (cell == null) {
            return "";
        }
        if (cell.getCellType() == CellType.STRING) {
            return cell.getStringCellValue();
        } else if (cell.getCellType() == CellType.NUMERIC) {
            double doubleValue = cell.getNumericCellValue();
            if (turnInt) {
                return (int) doubleValue + "";
            }
            return doubleValue + "";
        }
        return "";
    }

    /**
     * 获取int值
     *
     * @param cell
     * @return
     */
    public static int getIntValueFromCell(XSSFCell cell) {
        if (cell == null) {
            return 0;
        }
        try {
            if (cell.getCellType() == CellType.STRING) {
                return Integer.valueOf(cell.getStringCellValue());
            } else if (cell.getCellType() == CellType.NUMERIC) {
                double doubleValue = cell.getNumericCellValue();
                return (int) doubleValue;
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 获取double值
     *
     * @param cell
     * @return
     */
    public static double getDoubleValueFromCell(XSSFCell cell) {
        if (cell == null) {
            return 0;
        }
        try {
            if (cell.getCellType() == CellType.STRING) {
                return Double.parseDouble(cell.getStringCellValue());
            } else if (cell.getCellType() == CellType.NUMERIC) {
                return cell.getNumericCellValue();
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 返回大标题的样式
     *
     * @return
     */
    public static XSSFCellStyle getBigTitleStyle(XSSFWorkbook workbook) {
        XSSFFont fontTitle = workbook.createFont();
        fontTitle.setBold(true);
        fontTitle.setFontHeightInPoints((short) 20);
        XSSFCellStyle styleTitle = workbook.createCellStyle();
        styleTitle.setFont(fontTitle);
        styleTitle.setAlignment(HorizontalAlignment.LEFT);
        return styleTitle;
    }

    /**
     * 返回标题的样式
     *
     * @return
     */
    public static XSSFCellStyle getTitleStyle(XSSFWorkbook workbook) {
        XSSFFont fontHead = workbook.createFont();
        fontHead.setFontHeightInPoints((short) 17);
        XSSFCellStyle styleHead = workbook.createCellStyle();
        styleHead.setFont(fontHead);
        styleHead.setAlignment(HorizontalAlignment.CENTER);
        styleHead.setFillBackgroundColor((short) 74);
        styleHead.setFillForegroundColor((short) 1);
        return styleHead;
    }

    /**
     * 返回内容的样式
     *
     * @return
     */
    public static XSSFCellStyle getContentStyle(XSSFWorkbook workbook) {
        XSSFFont fontContent = workbook.createFont();
        fontContent.setFontHeightInPoints((short) 14);
        XSSFCellStyle styleContent = workbook.createCellStyle();
        styleContent.setFont(fontContent);
        styleContent.setAlignment(HorizontalAlignment.CENTER);
        return styleContent;
    }

    /**
     * 设置一个大标题
     *
     * @param workbook
     * @param row
     * @param title    标题
     * @param sheet
     * @param startX   开始的列
     * @param startY   开始的行
     * @param xSPan    列数量
     * @param endY     结束的行
     */
    public static void addBigTitle(XSSFWorkbook workbook, XSSFRow row, String title, XSSFSheet sheet, int startX, int startY, int xSPan, int endY) {
        if (row == null) {
            row = sheet.createRow(sheet.getLastRowNum());
        }
        XSSFCell cell = row.createCell(0);
        cell.setCellValue(title);
        cell.setCellStyle(getBigTitleStyle(workbook));
        if (startX != -1) {
            CellRangeAddress region = new CellRangeAddress(startY, endY, startX, xSPan);
            sheet.addMergedRegion(region);
        }
    }

    /**
     * 添加标题
     *
     * @param workbook
     * @param row
     * @param title
     * @param sheet
     * @param rowSpan
     * @param colSpan
     * @return
     */
    public static XSSFRow addHead(XSSFWorkbook workbook, XSSFRow row, String title, XSSFSheet sheet, int startRow, int startCol, int rowSpan, int colSpan) {
        if (row == null) {
            row = sheet.createRow(startRow);
        }
        XSSFCell headCell = row.createCell(startCol);
        headCell.setCellValue(title);
        headCell.setCellStyle(getTitleStyle(workbook));
        if (rowSpan > 1 || colSpan > 1) {
            int firstRow = startRow;
            int lastRow = startRow + (rowSpan > 1 ? rowSpan : 0);
            int firstCol = startCol;
            int lastCol = startCol + (colSpan > 1 ? colSpan : 0);
            CellRangeAddress region = new CellRangeAddress(firstRow, lastRow, firstCol, lastCol);
            sheet.addMergedRegion(region);
        }
        return row;
    }

    /**
     * 添加标题
     *
     * @param workbook
     * @param row
     * @param title
     * @param sheet
     * @return
     */
    public static XSSFRow addHead(XSSFWorkbook workbook, XSSFRow row, String title, XSSFSheet sheet) {
        return addHead(workbook, row, title, sheet, 0, 0, 1, 1);
    }

    /**
     * 添加标题
     *
     * @param workbook
     * @param row
     * @param sheet
     * @return
     */
    public static XSSFRow addHeads(XSSFWorkbook workbook, XSSFRow row, String[] titles, XSSFSheet sheet) {
        XSSFRow result = row;
        for (int i = 0; i < titles.length; i++) {
            int startRow = sheet.getPhysicalNumberOfRows();
            if (startRow == -1) {
                startRow = 0;
            }
            int startCol = 0;
            if (result == null) {
                startCol = 0;
            } else {
                startCol = result.getPhysicalNumberOfCells();
            }
            result = addHead(workbook, result, titles[i], sheet, startRow+1, startCol, 1, 1);
        }
        return result;
    }

    /**
     * 添加内容
     *
     * @param workbook
     * @param row
     * @param content
     * @param sheet
     * @param rowSpan
     * @param colSpan
     * @return
     */
    public static XSSFRow addContent(XSSFWorkbook workbook, XSSFRow row, String content, XSSFSheet sheet, int startRow, int startCol, int rowSpan, int colSpan) {
        int rowIndex = sheet.getPhysicalNumberOfRows();
        if (rowIndex == -1) {
            rowIndex = 0;
        }
        if (row == null) {
            row = sheet.createRow(rowIndex + 1);
        }
        int startIndex = row.getPhysicalNumberOfCells();
        if (startIndex == -1) {
            startIndex = 0;
        }
        XSSFCell headCell = row.createCell(startIndex);
        headCell.setCellValue(content);
        headCell.setCellStyle(getContentStyle(workbook));
        if (rowSpan > 1 || colSpan > 1) {
            CellRangeAddress region = new CellRangeAddress(startRow, startRow + rowSpan, startCol, startCol + colSpan);
            sheet.addMergedRegion(region);
        }
        return row;
    }

    /**
     * 添加标题
     *
     * @param workbook
     * @param row
     * @param sheet
     */
    public static XSSFRow addContent(XSSFWorkbook workbook, XSSFRow row, String content, XSSFSheet sheet) {
        return addContent(workbook, row, content, sheet, -1, -1, 1, 1);
    }

}
