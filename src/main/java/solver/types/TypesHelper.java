package solver.types;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.stream.Collectors;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;

import solver.types.tokens.Variable;

public class TypesHelper {

	private static final int INDEX_TYP = 0;
	private static final int INDEX_OBJ = 1;

	public static List<Problem> loadProblemsFromFile(String filePath) throws IOException {
		List<Problem> problems = new LinkedList<>();

		List<String> lines = FileUtils.readLines(new File(filePath), "UTF-8");
		loadProblems(lines, problems);

		return problems;
	}

	private static void loadProblems(List<String> lines, List<Problem> problems) {
		Problem problem = new Problem();
		int index = 0;
		while (!lines.isEmpty()) {
			String line = lines.remove(0).trim();
			if (StringUtils.isEmpty(line)) {
				problems.add(problem);
				problem = new Problem();
				index = 0;
			} else {
				switch (index) {
				case INDEX_TYP:
					setProblemType(line, problem);
					break;
				case INDEX_OBJ:
					setObjective(line, problem);
					break;
				default:
					setRestriction(line, problem);
					break;
				}
			}
		}
	}

	private static void setProblemType(String line, Problem problem) {
		ProblemType problemType = ProblemType.getEnum(line);
		problem.setProblemType(problemType);
	}

	private static void setObjective(String line, Problem problem) {
		Function function = getFunction(line);
		problem.setObjectiveFunction((Objective) function);
	}

	private static void setRestriction(String line, Problem problem) {
		Function function = getFunction(line);
		problem.addRestriction((Restriction) function);

	}

	private static Function getFunction(String line) {
		Function function = new Function();
		List<String> elements = getElements(line);
		for (String element : elements) {
			processElement(element, function);
		}
		return function;
	}

	private static void processElement(String element, Function function) {
		if (isVariable(element)) {
			function.addToken(new Variable(element));
		} else if (isComparation(element)) {

		}
		if (isComposition(element)) {

		}
		if (isFloat(element)) {

		}
		if (isOperation(element)) {

		}
	}

	private static boolean isOperation(String element) {
		return element.equals("+") || element.equals("-"); // || element.equals(".") || element.equals("/");
	}

	private static boolean isFloat(String element) {
		return StringUtils.isNumeric(element);
	}

	private static boolean isComposition(String element) {
		return element.matches("[0-9]+[a-z]+");
	}

	private static boolean isComparation(String element) {
		return element.equals("=") || element.equals(">=") || element.equals("<=");
	}

	private static boolean isVariable(String element) {
		if (StringUtils.isNumeric(element.substring(0, 1))) {
			return false;
		}
		return true;
	}

	private static List<String> getElements(String str) {
		return Collections.list(new StringTokenizer(str, "\\s")).stream().map(token -> ((String) token).trim())
				.collect(Collectors.toList());
	}

}
