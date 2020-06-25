package com.bootdo.train.service.impl;

import com.bootdo.train.pojo.UserDO;
import com.bootdo.train.service.ExportService;
import com.bootdo.train.utils.ExcelUtil;
import org.apache.poi.hssf.usermodel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.List;

@Service
public class ExportServiceImpl implements ExportService {

    private static final Logger logger = LoggerFactory.getLogger(ExportServiceImpl.class);

    public void exportUserExcel(HttpServletRequest request, HttpServletResponse resp, List<UserDO> userList, String fileName, String[] title) throws UnsupportedEncodingException {
        /*
            第一步，创建一个HSSFWorkbook，对应一个Excel文件
         */
        HSSFWorkbook wb = new HSSFWorkbook();

        request.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("application/x-download");

        /*
            设置文件属性
         */
//        String fileNameNew = fileName+".xls";
//        fileName = URLEncoder.encode(fileName, "UTF-8");
//        resp.addHeader("Content-Disposition", "attachment;filename=" + fileNameNew);
        /*
            第二步，在workbook中添加一个sheet,对应Excel文件中的sheet
         */
        HSSFSheet sheet = wb.createSheet("记录");
        sheet.setDefaultRowHeight((short) (2 * 256));
        sheet.setColumnWidth(0, 50 * 160);
        HSSFFont font = wb.createFont();
        font.setFontName("宋体");
        font.setFontHeightInPoints((short) 16);
        /*
            第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制
         */
        HSSFRow row = sheet.createRow((int) 0);
        /*
            第四步，创建单元格，并设置值表头 设置表头居中
          */
        HSSFCellStyle style = wb.createCellStyle();
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);

        /*
            声明列对象,创建标题
        　　String[] title = {"名称","性别","年龄","学校","班级"};
         */
        HSSFCell cell = null;
        for(int i=0;i<title.length;i++){
            cell = row.createCell(i);
            cell.setCellValue(title[i]);
            cell.setCellStyle(style);
        }

        /*
            直接对list 进行遍历
         */
        for (int i = 0; i < userList.size(); i++)
        {
            UserDO userDO = userList.get(i);
            HSSFRow row1 = sheet.createRow((int) i + 1);
            /*
            这里进行将查询好的list数据 逐个 放进对应的 列下
             */
//            row1.createCell(0).setCellValue(i + 1);
            row1.createCell(0).setCellValue(userDO.getUsername());
            row1.createCell(1).setCellValue(userDO.getMobile());
            row1.createCell(2).setCellValue(userDO.getSex());
//            row.createCell(1).setCellValue(DateUtils.getFormatDateTime(vuserOrder.getCreateTime()));//日期
//            row.createCell(2).setCellValue(vuserOrder.getOrderCode());//订单号
//            row.createCell(3).setCellValue(vuserOrder.getUserName());//会员姓名
//            row.createCell(4).setCellValue(vuserOrder.getUserMphone());//会员手机号
//            row.createCell(5).setCellValue(vuserOrder.getSchoolName());//学校
//            row.createCell(6).setCellValue(vuserOrder.getFacultyName());//院系
//            row.createCell(7).setCellValue(vuserOrder.getJyrq());//交易日期
//            row.createCell(8).setCellValue(type);//消费类型
//            row.createCell(9).setCellValue(vuserOrder.getProductName());//产品名称
//            row.createCell(10).setCellValue(vuserOrder.getAmount().doubleValue());//消费金额
//            row.createCell(11).setCellValue( (vuserOrder.getStatus() == 1) ? "交易成功" : (vuserOrder.getStatus() == 2) ? "失败" : "处理中");//状态
        }
        try
        {
            ExcelUtil.setResponseHeader(resp,fileName);
            OutputStream out = resp.getOutputStream();
            wb.write(out);
            out.flush();
            out.close();
        }
        catch (Exception e)
        {
            logger.info("=====导出excel异常====");
        }
    }
}
