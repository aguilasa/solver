package solver;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Regex {

	public static void main(String[] args) {
		String[] s = { "z", "2x1", "7x2", "0,3x3" };
		for (String e : s) {
			System.out.println(e + ": ");
			runTest("([0-9]+(,[0-9]+)*)*([a-zA-Z]+[0-9]*)", e);
			System.out.println();
		}
	}

	public static int runTest(String regex, String text) {
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(text);
		int matches = 0;
		while (matcher.find()) {
			System.out.println("group(1): " + matcher.group(1));
//			System.out.println("group(2): " + matcher.group(2));
			System.out.println("group(3): " + matcher.group(3));
			matches++;
		}
		return matches;
	}

}
