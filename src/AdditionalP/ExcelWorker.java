package AdditionalP;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;

public class ExcelWorker {

    private static HSSFWorkbook workbook = new HSSFWorkbook();

    public static void Export(String list_name, String file, String[] column_names, ArrayList<String[]> data) {

        HSSFSheet sheet = workbook.createSheet(list_name);

        Row row = sheet.createRow(0);
        for (int i = 0; i < column_names.length; i++) {
            row.createCell(i).setCellValue(column_names[i]);
        }

        for (int i = 0; i < data.size(); i++) {
            createSheet(sheet, i, data);
        }

        HSSFFont font = workbook.createFont();
        font.setBold(true);
        HSSFCellStyle style = workbook.createCellStyle();
        style.setFont(font);
        style.setWrapText(true);
        row = sheet.getRow(0);
        for (int i = 0; i < row.getPhysicalNumberOfCells(); i++) {
            row.getCell(i).setCellStyle(style);
        }
        String file_way = "D:\\Systemf\\Desktop\\test\\" + file;
        try ( FileOutputStream out = new FileOutputStream(new File(file_way))) {
            workbook.write(out);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Runtime r = Runtime.getRuntime();
        try {
            String cmd = "cmd /c start " + file_way;
            r.exec(cmd);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void createSheet(HSSFSheet sheet, int rowNum, ArrayList<String[]> data) {
        HSSFCellStyle style = workbook.createCellStyle();
        style.setWrapText(true);
        Row row = sheet.createRow(rowNum + 1);
        for (int i = 0; i < data.get(rowNum).length; i++) {
            row.createCell(i).setCellValue(data.get(rowNum)[i]);
            row.getCell(i).setCellStyle(style);
        }
    }
}
