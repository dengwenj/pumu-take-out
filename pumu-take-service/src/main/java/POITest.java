import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;

public class POITest {
    public static void main(String[] args) throws IOException {
        write();
    }

    public static void write() throws IOException {
        // 创建一个 excel 文件
        XSSFWorkbook excel = new XSSFWorkbook();
        // 创建一个标签
        XSSFSheet sheet = excel.createSheet("test");
        // 创建行对象，从 0 开始的
        XSSFRow row = sheet.createRow(1);// 第二行
        // 在第二行上创建单元格，也是从0开始的
        row.createCell(1).setCellValue("姓名");
        row.createCell(2).setCellValue("年龄");

        XSSFRow row1 = sheet.createRow(2);
        row1.createCell(1).setCellValue("朴睦");
        row1.createCell(2).setCellValue("24");

        XSSFRow row2 = sheet.createRow(3);
        row2.createCell(1).setCellValue("李雷");
        row2.createCell(2).setCellValue("20");

        // 输出到磁盘中
        FileOutputStream out = new FileOutputStream("/Users/dengwenjie/Desktop/test.xlsx");
        excel.write(out);
        out.close();
    }
}
