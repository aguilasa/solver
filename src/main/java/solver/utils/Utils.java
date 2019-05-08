package solver.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;

import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class Utils {

	private static final DecimalFormat DF = new DecimalFormat("#.##");

	public static final XSSFWorkbook workbook() {
		return new XSSFWorkbook();
	}

	public static final void writeWorkbook(XSSFWorkbook workbook, String fileName) throws IOException {
		try (FileOutputStream out = new FileOutputStream(new File(fileName));) {
			workbook.write(out);
		}
	}

	public static final XSSFWorkbook readWorkbook(String fileName) throws Exception {
		FileInputStream in = new FileInputStream(new File(fileName));
		return (XSSFWorkbook) WorkbookFactory.create(in);
	}

	public static final XSSFCellStyle alignRight(XSSFWorkbook workbook) {
		XSSFCellStyle style = workbook.createCellStyle();
		style.setAlignment(HorizontalAlignment.RIGHT);
		style.setVerticalAlignment(VerticalAlignment.BOTTOM);
		return style;
	}

	public static final XSSFCellStyle alignLeft(XSSFWorkbook workbook) {
		XSSFCellStyle style = workbook.createCellStyle();
		style.setAlignment(HorizontalAlignment.LEFT);
		style.setVerticalAlignment(VerticalAlignment.BOTTOM);
		return style;
	}

	public static final XSSFCellStyle alignCenter(XSSFWorkbook workbook) {
		XSSFCellStyle style = workbook.createCellStyle();
		style.setAlignment(HorizontalAlignment.CENTER);
		style.setVerticalAlignment(VerticalAlignment.BOTTOM);
		return style;
	}

	public static final String getTemporaryFile(String name) {
		return getTemporaryFile(name, false);
	}

	public static final String getTemporaryFile(String name, boolean macro) {
		File currDir = new File(".");
		String path = currDir.getAbsolutePath();
		return path.substring(0, path.length() - 1).concat(name).concat(".xls").concat(macro ? "m" : "x");
	}

	public static final String formatFloat(Float number) {
		return DF.format(number);
	}

}
