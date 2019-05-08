package solver;

import static solver.utils.Utils.alignCenter;
import static solver.utils.Utils.alignRight;
import static solver.utils.Utils.getTemporaryFile;
import static solver.utils.Utils.workbook;
import static solver.utils.Utils.writeWorkbook;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import solver.types.Problem;
import solver.types.Restriction;
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
			cell.setCellFormula(getFormulaFromTokens(problem.getObjective().getTokens(), variableCells));

			rowNumber++;
			row = sheet.getRow(++rowNumber);
			cell = row.createCell(0);
			cell.setCellStyle(center);
			cell.setCellValue("Restrições");

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

			writeWorkbook(workbook, getTemporaryFile("temp"));
		}
	}

	private static String getFormulaFromTokens(List<Token<?>> tokens, Map<Variable, String> variableCells) {
		StringBuilder sb = new StringBuilder();
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
		return sb.toString();
	}

}
