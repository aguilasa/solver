package solver;

import static solver.utils.Utils.alignCenter;
import static solver.utils.Utils.alignLeft;
import static solver.utils.Utils.alignRight;
import static solver.utils.Utils.getTemporaryFile;
import static solver.utils.Utils.readWorkbook;
import static solver.utils.Utils.writeWorkbook;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFDrawing;
import org.apache.poi.xssf.usermodel.XSSFShape;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFSimpleShape;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import solver.types.Problem;
import solver.types.ProblemType;
import solver.types.Restriction;
import solver.types.TypesHelper;
import solver.types.tokens.Composition;
import solver.types.tokens.Token;
import solver.types.tokens.Variable;

public class Solver {

	public static final String RESOURCES = "src/main/resources/";
	public static final String INPUT = RESOURCES.concat("input/");

	public static void main(String[] args) throws Exception {
		textBox();
	}

	public static void textBox() throws Exception {
		try (XSSFWorkbook workbook = readWorkbook(INPUT.concat("pasta.xlsx"));) {
			XSSFSheet sheet = workbook.getSheetAt(0);
			XSSFDrawing drawing = sheet.getDrawingPatriarch();
			List<XSSFShape> shapes = drawing.getShapes();
			Iterator<XSSFShape> it = shapes.iterator();
			while (it.hasNext()) {
				XSSFShape shape = it.next();
				if (shape instanceof XSSFSimpleShape) {
					XSSFSimpleShape textBox = (XSSFSimpleShape) shape;
					System.out.println(textBox.getText());
				}
			}
		}
	}

	public static void problems() throws Exception {
		try (XSSFWorkbook workbook = readWorkbook(INPUT.concat("base.xlsm"));) {
			List<Problem> problems = TypesHelper.loadProblemsFromFile(INPUT.concat("01.txt"));
			int count = 0;
			for (Problem p : problems) {
				create(p, workbook, ++count);
			}
			writeWorkbook(workbook, getTemporaryFile("temp", true));
		}
	}

	public static final void create(Problem problem, XSSFWorkbook workbook, int index) throws FileNotFoundException, IOException {
		Map<Variable, String> variableCells = new LinkedHashMap<>();
		Sheet sheet = workbook.createSheet(String.format("Problema%s", index));
		sheet.setColumnWidth(0, 4000);

		int total = problem.getVariables().size() + problem.getRestrictions().size() + 6;
		for (int i = 0; i < total; i++) {
			sheet.createRow(i);
		}

		XSSFCellStyle left = alignLeft(workbook);
		XSSFCellStyle center = alignCenter(workbook);
		XSSFCellStyle right = alignRight(workbook);

		int rowNumber = 0;
		Row row = sheet.getRow(rowNumber);
		Cell cell = row.createCell(0);
		cell.setCellStyle(left);
		ProblemType problemType = problem.getProblemType();
		cell.setCellValue(problemType.equals(ProblemType.MAX) ? "M�ximo" : "M�nimo");

		row = sheet.getRow(++rowNumber);
		cell = row.createCell(0);
		cell.setCellStyle(left);
		cell.setCellValue("Vari�veis");

		for (Variable v : problem.getVariables()) {
			row = sheet.getRow(++rowNumber);
			cell = row.createCell(0);
			cell.setCellStyle(right);
			cell.setCellValue(v.getValue());
			variableCells.put(v, String.format("B%d", rowNumber + 1));
		}

		rowNumber++;
		row = sheet.getRow(++rowNumber);
		cell = row.createCell(0);
		cell.setCellStyle(left);
		cell.setCellValue("Fun��o Objetivo");

		cell = row.createCell(1);
		cell.setCellStyle(right);
		cell.setCellFormula(getFormulaFromTokens(problem.getObjective().getTokens(), variableCells));

		rowNumber++;
		row = sheet.getRow(++rowNumber);
		cell = row.createCell(0);
		cell.setCellStyle(center);
		cell.setCellValue("Restri��es");

		int i = 0;
		for (Restriction restriction : problem.getRestrictions()) {
			row = sheet.getRow(++rowNumber);
			cell = row.createCell(0);
			cell.setCellStyle(right);
			cell.setCellValue(String.format("r%d", ++i));

			cell = row.createCell(1);
			cell.setCellStyle(right);
			cell.setCellFormula(getFormulaFromTokens(restriction.getTokens(), variableCells));

			cell = row.createCell(2);
			cell.setCellStyle(center);
			cell.setCellValue(restriction.getComparation().toString());

			cell = row.createCell(3);
			cell.setCellStyle(right);
			cell.setCellValue(restriction.getLimit().getValue());
		}

	}

	private static String getFormulaFromTokens(List<Token<?>> tokens, Map<Variable, String> variableCells) {
		StringBuilder sb = new StringBuilder();
		for (Token<?> token : tokens) {
			if (token instanceof Composition) {
				Composition composition = (Composition) token;
				sb.append(composition.getValue().toString().replace(",", "."));
				sb.append("*");
				sb.append(variableCells.get(composition.getVariable()));
			} else {
				sb.append(token.toString());
			}
		}
		return sb.toString();
	}

}
