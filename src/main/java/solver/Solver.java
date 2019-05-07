package solver;

import static solver.utils.Utils.getTemporaryFile;
import static solver.utils.Utils.writeWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import solver.types.Problem;
import solver.types.Restriction;
import solver.types.TypesHelper;

public class Solver {

	public static final String RESOURCES = "src/main/resources/";

	public static void main(String[] args) throws IOException {
		List<Problem> problems = TypesHelper.loadProblemsFromFile(RESOURCES.concat("input/01.txt"));
		for (Problem p : problems) {
			System.out.println(p.getObjective().toString());
			List<Restriction> restrictions = p.getRestrictions();
			for (Restriction r : restrictions) {
				System.out.println(r.toString());
			}
		}
	}

	public static final void open() throws FileNotFoundException, IOException {
		try (FileInputStream file = new FileInputStream(new File("c:/temp/base.xlsx"));) {
			try (XSSFWorkbook workbook = new XSSFWorkbook();) {
				Sheet sheet = workbook.createSheet("Persons");
				sheet.setColumnWidth(0, 6000);
				sheet.setColumnWidth(1, 4000);

				Row header = sheet.createRow(0);

				CellStyle headerStyle = workbook.createCellStyle();
				headerStyle.setFillForegroundColor(IndexedColors.LIGHT_BLUE.getIndex());
				headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

				XSSFFont font = ((XSSFWorkbook) workbook).createFont();
				font.setFontName("Arial");
				font.setFontHeightInPoints((short) 16);
				font.setBold(true);
				headerStyle.setFont(font);

				Cell headerCell = header.createCell(0);
				headerCell.setCellValue("Name");
				headerCell.setCellStyle(headerStyle);

				headerCell = header.createCell(1);
				headerCell.setCellValue("Age");
				headerCell.setCellStyle(headerStyle);

				CellStyle style = workbook.createCellStyle();
				style.setWrapText(true);

				Row row = sheet.createRow(2);
				Cell cell = row.createCell(0);
				cell.setCellValue("John Smith");
				cell.setCellStyle(style);

				cell = row.createCell(1);
				cell.setCellValue(20);
				cell.setCellStyle(style);

				writeWorkbook(workbook, getTemporaryFile("temp"));
			}
		}
	}

}
