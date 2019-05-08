package solver;

import static solver.utils.Utils.alignCenter;
import static solver.utils.Utils.alignRight;
import static solver.utils.Utils.getTemporaryFile;
import static solver.utils.Utils.workbook;
import static solver.utils.Utils.writeWorkbook;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import solver.types.Objective;
import solver.types.Problem;
import solver.types.TypesHelper;
import solver.types.tokens.Composition;
import solver.types.tokens.Token;
import solver.types.tokens.Variable;

public class Solver {

	public static final String RESOURCES = "src/main/resources/";

	public static void main(String[] args) throws IOException {
		problems();
	}

	public static void problems() throws IOException {
		List<Problem> problems = TypesHelper.loadProblemsFromFile(RESOURCES.concat("input/01.txt"));
		for (Problem p : problems) {
			create(p);
			break;
		}
	}

	public static final void create(Problem problem) throws FileNotFoundException, IOException {
		try (XSSFWorkbook workbook = workbook();) {
			Map<Variable, String> variableCells = new LinkedHashMap<>();
			Sheet sheet = workbook.createSheet("Problema");
			sheet.setColumnWidth(0, 4000);

			int total = problem.getVariables().size() + problem.getRestrictions().size() + 5;
			for (int i = 0; i < total; i++) {
				sheet.createRow(i);
			}

			XSSFCellStyle center = alignCenter(workbook);
			XSSFCellStyle right = alignRight(workbook);

			Row row = sheet.getRow(0);
			Cell cell = row.createCell(0);
			cell.setCellStyle(center);
			cell.setCellValue("Variáveis");

			int rowNumber = 0;
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
			cell.setCellStyle(right);
			cell.setCellValue("Função Objetivo");

			cell = row.createCell(1);
			cell.setCellStyle(right);
			cell.setCellFormula(getFormulaFromObjective(problem.getObjective(), variableCells));

			rowNumber++;
			row = sheet.getRow(++rowNumber);
			cell = row.createCell(0);
			cell.setCellStyle(center);
			cell.setCellValue("Restrições");

			for (int i = 1; i <= problem.getRestrictions().size(); i++) {
				row = sheet.getRow(++rowNumber);
				cell = row.createCell(0);
				cell.setCellStyle(right);
				cell.setCellValue(String.format("r%d", i));
			}

//
//			row = sheet.createRow(1);
//			cell = row.createCell(0);
//			cell.setCellValue("x1");
//			cell = row.createCell(1);
//			cell.setCellValue(4);
//
//			row = sheet.createRow(2);
//			cell = row.createCell(0);
//			cell.setCellValue("x2");
//			cell = row.createCell(1);
//			cell.setCellValue(4);

			writeWorkbook(workbook, getTemporaryFile("temp"));
		}
	}

	private static String getFormulaFromObjective(Objective objective, Map<Variable, String> variableCells) {
		StringBuilder sb = new StringBuilder();

		getFormulaFromTokens(variableCells, sb, objective.getTokens());

		return sb.toString();
	}

	private static void getFormulaFromTokens(Map<Variable, String> variableCells, StringBuilder sb, List<Token<?>> tokens) {
		for (Token<?> token : tokens) {
			if (token instanceof Composition) {
				Composition composition = (Composition) token;
				sb.append(composition.getValue().toString());
				sb.append("*");
				sb.append(variableCells.get(composition.getVariable()));
			} else {
				sb.append(token.toString());
			}
		}
	}

}
