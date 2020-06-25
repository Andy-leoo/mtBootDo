package com.bootdo.train.utils;

import com.bootdo.train.commons.Const;
import com.bootdo.train.pojo.ExcelSheetPO;
import com.bootdo.train.pojo.UserDO;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class ExcelUtil {

    public static final int EXPORT_07_LEAST_SIZE = 50000;

    /**
     * 标题样式
     */
    private final static String STYLE_HEADER = "header";
    /**
     * 表头样式
     */
    private final static String STYLE_TITLE = "title";
    /**
     * 数据样式
     */
    private final static String STYLE_DATA = "data";

    /**
     * 存储样式
     */
    private static final HashMap<String, CellStyle> cellStyleMap = new HashMap<>();


    //解析 excel
    public static List readExcel(File file, String uploadPath) throws IOException {
        Workbook wb = null;
        // 获得文件名称
        String fileName = file.getName();
        File fileNewPath = new File(uploadPath + fileName);
        // 获得后缀
        String extName = fileName.substring(fileName.indexOf("."));
        // 根据后缀名称判断excel的版本
        if (ExcelVersion.V2003.getSuffix().equals(extName)) {
            wb = new HSSFWorkbook(new FileInputStream(fileNewPath));
        } else if (ExcelVersion.V2007.getSuffix().equals(extName)) {
            wb = new XSSFWorkbook(new FileInputStream(fileNewPath));
        } else {
            // 无效后缀名称，这里之能保证excel的后缀名称，不能保证文件类型正确，不过没关系，在创建Workbook的时候会校验文件格式
            throw new IllegalArgumentException("Invalid excel version");
        }
        // 开始读取数据
        List<ExcelSheetPO> sheetPOs = new ArrayList<>();
        // 解析sheet
        for (int numSheet = 0; numSheet < wb.getNumberOfSheets(); numSheet++) {
            Sheet sheet = wb.getSheetAt(numSheet);
            
            List<List<Object>> dataList = new ArrayList<>();
            ExcelSheetPO sheetPO = new ExcelSheetPO();
            sheetPO.setSheetName(sheet.getSheetName());
            sheetPO.setDataList(dataList);
            int readRowCount = 0;
            readRowCount = sheet.getPhysicalNumberOfRows();

            // 解析sheet 的行
            for (int j = sheet.getFirstRowNum(); j < readRowCount; j++) {
                Row row = sheet.getRow(j);
                if (row == null) {
                    continue;
                }
                if (row.getFirstCellNum() < 0) {
                    continue;
                }
                int readColumnCount = 0;

                readColumnCount = (int) row.getLastCellNum();

                List<Object> rowValue = new LinkedList<Object>();
                // 解析sheet 的列
                for (int k = 0; k < readColumnCount; k++) {
                    Cell cell = row.getCell(k);
                    rowValue.add(getCellValue(wb, cell));
                }
                dataList.add(rowValue);
            }
            sheetPOs.add(sheetPO);
        }
        return sheetPOs;
    }

    private static Object getCellValue(Workbook wb, Cell cell) {
        Object columnValue = null;
        if (cell != null) {
            DecimalFormat df = new DecimalFormat("0");// 格式化 number
            // String
            // 字符
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 格式化日期字符串
            DecimalFormat nf = new DecimalFormat("0.00");// 格式化数字
            switch (cell.getCellType()) {
                case Cell.CELL_TYPE_STRING:
                    columnValue = cell.getStringCellValue();
                    break;
                case Cell.CELL_TYPE_NUMERIC:
                    if ("@".equals(cell.getCellStyle().getDataFormatString())) {
                        columnValue = df.format(cell.getNumericCellValue());
                    } else if ("General".equals(cell.getCellStyle().getDataFormatString())) {
                        columnValue = nf.format(cell.getNumericCellValue());
                    } else {
                        columnValue = sdf.format( HSSFDateUtil.getJavaDate(cell.getNumericCellValue()));
                    }
                    break;
                case Cell.CELL_TYPE_BOOLEAN:
                    columnValue = cell.getBooleanCellValue();
                    break;
                case Cell.CELL_TYPE_BLANK:
                    columnValue = "";
                    break;
                case Cell.CELL_TYPE_FORMULA:
                    // 格式单元格
                    FormulaEvaluator evaluator = wb.getCreationHelper().createFormulaEvaluator();
                    evaluator.evaluateFormulaCell(cell);
                    CellValue cellValue = evaluator.evaluate(cell);
                    columnValue = cellValue.getNumberValue();
                    break;
                default:
                    columnValue = cell.toString();
            }
        }
        return columnValue;
    }

    public static List<UserDO> readXls(File file, String uploadPath) throws IOException {
        Workbook wb = null;
        List<UserDO> list = new ArrayList();
        String fileName = file.getName();
        File fileNewPath = new File(uploadPath + fileName);
        // 获得后缀
        String extName = fileName.substring(fileName.indexOf("."));
        // 根据后缀名称判断excel的版本
        if (ExcelVersion.V2003.getSuffix().equals(extName)) {
            wb = new HSSFWorkbook(new FileInputStream(fileNewPath));
        } else if (ExcelVersion.V2007.getSuffix().equals(extName)) {
            wb = new XSSFWorkbook(new FileInputStream(fileNewPath));
        } else {
            // 无效后缀名称，这里之能保证excel的后缀名称，不能保证文件类型正确，不过没关系，在创建Workbook的时候会校验文件格式
            throw new IllegalArgumentException("Invalid excel version");
        }
         // 循环工作表Sheet
         for (int numSheet = 0; numSheet < wb.getNumberOfSheets(); numSheet++) {
             Sheet sheetAt = wb.getSheetAt(numSheet);
                 if (sheetAt == null) {
                         continue;
                     }
                 // 循环行Row
                 for (int rowNum = 1; rowNum <= sheetAt.getLastRowNum(); rowNum++) {
                     Row row = sheetAt.getRow(rowNum);
                     if (row != null) {
                         UserDO userDO= new UserDO();
                         Cell name = row.getCell(0);
                         Cell sex = row.getCell(1);
                         Cell mail = row.getCell(2);
                         Cell phone = row.getCell(3);
                         userDO.setName(getValue(name));
                         userDO.setMobile(getValue(phone));
                         userDO.setEmail(getValue(mail));
                         userDO.setSex(Const.BASE_SEX_MAN);
                         /*
                            初始化数据
                          */
                         userDO.setUsername("aabbcc");
                         userDO.setPassword(MD5Util.MD5EncodeUtf8(Const.BASE_PASSWORD));
                         userDO.setDeptId(Const.BASE_DEPT_ID);
                         userDO.setStatus(1);
                         userDO.setUserIdCreate(Long.valueOf(1));
                         userDO.setGmtCreate(new Date());
                         userDO.setGmtModified(new Date());
                         userDO.setRole(0);//默认普通用户
                         userDO.setBirth(new Date());
                         userDO.setPicId(Const.BASE_DEPT_ID);
                         userDO.setHobby("122");
                         userDO.setProvince("北京");
                         userDO.setLiveAddress("北京");
                         userDO.setCity("北京");
                         userDO.setDistrict("丰台区");
                         list.add(userDO);
                     }
                 }
          }
        return list;
     }
    @SuppressWarnings("static-access")
     private static String getValue(Cell hssfCell) {
         if (hssfCell.getCellType() == hssfCell.CELL_TYPE_BOOLEAN) {
                 // 返回布尔类型的值
                 return String.valueOf(hssfCell.getBooleanCellValue());
             } else if (hssfCell.getCellType() == hssfCell.CELL_TYPE_NUMERIC) {
                 // 返回数值类型的值
                 return String.valueOf(hssfCell.getNumericCellValue());
             } else {
                 // 返回字符串类型的值
                 return String.valueOf(hssfCell.getStringCellValue());
             }
     }


    /**
     * 导出Excel
     * @param sheetName sheet名称
     * @param title 标题
     * @param values 内容
     * @param wb HSSFWorkbook对象
     * @return
     */
    public static HSSFWorkbook exportExcel(String sheetName,String []title,String [][]values, HSSFWorkbook wb){

        // 第一步，创建一个HSSFWorkbook，对应一个Excel文件
        if(wb == null){
            wb = new HSSFWorkbook();
        }

        // 第二步，在workbook中添加一个sheet,对应Excel文件中的sheet
        HSSFSheet sheet = wb.createSheet(sheetName);

        // 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制
        HSSFRow row = sheet.createRow(0);

        // 第四步，创建单元格，并设置值表头 设置表头居中
        HSSFCellStyle style = wb.createCellStyle();
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式

        //声明列对象
        HSSFCell cell = null;

        //创建标题
        for(int i=0;i<title.length;i++){
            cell = row.createCell(i);
            cell.setCellValue(title[i]);
            cell.setCellStyle(style);
        }

        //创建内容
        for(int i=0;i<values.length;i++){
            row = sheet.createRow(i + 1);
            for(int j=0;j<values[i].length;j++){
                //将内容按顺序赋给对应的列对象
                row.createCell(j).setCellValue(values[i][j]);
            }
        }
        return wb;
    }

    //发送响应流方法
    public static void setResponseHeader(HttpServletResponse response, String fileName) {
        try {
            try {
                fileName = new String(fileName.getBytes(),"ISO8859-1");
            } catch (UnsupportedEncodingException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            response.setContentType("application/octet-stream;charset=ISO8859-1");
            response.setHeader("Content-Disposition", "attachment;filename="+ fileName);
            response.addHeader("Pargam", "no-cache");
            response.addHeader("Cache-Control", "no-cache");
            response.setContentType("application/msexcel");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    enum ExcelVersion {
        /**
         * 虽然V2007版本支持最大支持1048575 * 16383 ，
         * V2003版支持65535*255
         * 但是在实际应用中如果使用如此庞大的对象集合会导致内存溢出，
         * 因此这里限制最大为10000*100，如果还要加大建议先通过单元测试进行性能测试。
         * 1000*100 全部导出预计时间为27s左右
         */
        V2003(".xls", 10000, 100), V2007(".xlsx", 100, 100);

        private String suffix;

        private int maxRow;

        private int maxColumn;

        ExcelVersion(String suffix, int maxRow, int maxColumn) {
            this.suffix = suffix;
            this.maxRow = maxRow;
            this.maxColumn = maxColumn;
        }

        public String getSuffix() {
            return this.suffix;
        }

        public int getMaxRow() {
            return maxRow;
        }

        public void setMaxRow(int maxRow) {
            this.maxRow = maxRow;
        }

        public int getMaxColumn() {
            return maxColumn;
        }

        public void setMaxColumn(int maxColumn) {
            this.maxColumn = maxColumn;
        }

        public void setSuffix(String suffix) {
            this.suffix = suffix;
        }
    }
}


