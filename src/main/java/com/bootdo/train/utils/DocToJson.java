package com.bootdo.train.utils;

import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.usermodel.*;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;

/**
 * <Description> <br>
 *
 * @author Andy-J<br>
 * @version 1.0<br>
 * @taskId: <br>
 * @createDate 2019/08/09 14:20 <br>
 * @ word 2 json
 * @see com.bootdo.train.utils <br>
 */
public class DocToJson {

    public static void main(String[] args) {
        try {
            readDoc("C:\\Users\\Andy-J\\Desktop\\7c06ad1c-6df9-4293-b3fd-da4aaa62ba49.doc");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void readDoc(String path) throws Exception {
        InputStream is = new FileInputStream(path);
        HWPFDocument document = new HWPFDocument(is);
//        XWPFDocument document = new XWPFDocument(is);
        //输出书签信息
        printLabInfo(document.getBookmarks());
        //输出文本
        System.out.println(document.getDocumentText());
        Range range = document.getRange();
        printRangeInfo(range);
        //读表格
        readTableInfo(range);
        //读列表
        readListInfo(range);
        //将当前HWPFDocument写到输出流中
        document.write(new FileOutputStream("E:\\POI技术测试\\test.docx"));
    }



    /**
     * @Author charlesYan
     * @Description 输出书签信息
     * @Date 11:02 2019/8/9
     * @Param [bookmarks]
     * @return void
     **/
    public static void printLabInfo(Bookmarks bookmarks){
        int bookmarksCount = bookmarks.getBookmarksCount();
        System.out.println("书签数量：" + bookmarksCount);
        Bookmark bookmark;
        for (int i=0; i<bookmarksCount; i++) {
            bookmark = bookmarks.getBookmark(i);
            System.out.println("书签" + (i+1) + "的名称是：" + bookmark.getName());
            System.out.println("开始位置：" + bookmark.getStart());
            System.out.println("结束位置：" + bookmark.getEnd());
        }

    }

    /**
     * @Author charlesYan
     * @Description 输出Range
     * @Date 13:09 2019/8/9
     * @Param [range]
     * @return void
     **/
    private static void printRangeInfo(Range range) {
        //获取段落数
        int paraNum = range.numParagraphs();
        System.out.println(paraNum);
        for (int i=0; i<paraNum; i++) {
            System.out.println("段落" + (i+1) + "：" + range.getParagraph(i).text());
        }
        int secNum = range.numSections();
        System.out.println(secNum);
        Section section;
        for (int i=0; i<secNum; i++) {
            section = range.getSection(i);
            System.out.println(section.getMarginLeft());
            System.out.println(section.getMarginRight());
            System.out.println(section.getMarginTop());
            System.out.println(section.getMarginBottom());
            System.out.println(section.getPageHeight());
            System.out.println(section.text());
        }
    }

    /**
     * @Author charlesYan
     * @Description 输出表格信息
     *                  每一个回车代表一个段落，所以对于表格而言，每一个单元格至少包含一个段落，每行结束都是一个段落
     * @Date 13:10 2019/8/9
     * @Param [range]
     * @return void
     **/
    private static void readTableInfo(Range range) {
        //遍历range范围内的table。
        TableIterator tableIter = new TableIterator(range);
        Table table;
        TableRow row;
        TableCell cell;
        while (tableIter.hasNext()) {
            table = tableIter.next();
            int rowNum = table.numRows();
            for (int j=0; j<rowNum; j++) {
                row = table.getRow(j);
                int cellNum = row.numCells();
                for (int k=0; k<cellNum; k++) {
                    cell = row.getCell(k);
                    //输出单元格的文本
                    System.out.println(cell.text().trim());
                }
            }
        }
    }

    /**
     * @Author charlesYan
     * @Description 读列表信息
     * @Date 13:17 2019/8/9
     * @Param [range]
     * @return void
     **/
    private static void readListInfo(Range range) {
        int num = range.numParagraphs();
        Paragraph para;
        for (int i = 0; i < num; i++) {
            para = range.getParagraph(i);
            if(para.isInList()){
                System.out.println("list：" + para.text());
            }

        }
    }
}
