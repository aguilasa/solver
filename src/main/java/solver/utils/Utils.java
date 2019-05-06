package solver.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;

import org.apache.poi.xslf.usermodel.XMLSlideShow;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

public class Utils {

	private static final String WORD1 = "C:\\Program Files\\Microsoft Office\\root\\Office16\\WINWORD.EXE";
	private static final String WORD2 = "C:\\Program Files\\Microsoft Office\\Office16\\WINWORD.EXE";
	private static final DecimalFormat DF = new DecimalFormat("#.##");

	public static final void writePresentation(XMLSlideShow ppt, String fileName) throws IOException {
		try (FileOutputStream out = new FileOutputStream(new File(fileName));) {
			ppt.write(out);
		}
	}

	public static final void writeDocument(XWPFDocument doc, String fileName) throws IOException {
		try (FileOutputStream out = new FileOutputStream(new File(fileName));) {
			doc.write(out);
		}
	}

	public static final void writeWorkbook(XSSFWorkbook xls, String fileName) throws IOException {
		try (FileOutputStream out = new FileOutputStream(new File(fileName));) {
			xls.write(out);
		}
	}

	public static final String getTemporaryFile(String name) {
		File currDir = new File(".");
		String path = currDir.getAbsolutePath();
		return path.substring(0, path.length() - 1).concat(name).concat(".xlsx");
	}

	public static final String formatFloat(Float number) {
		return DF.format(number);
	}

	private static final String getWord() {
		if (new File(WORD1).exists()) {
			return WORD1;
		} else if (new File(WORD2).exists()) {
			return WORD2;
		}
		return "";
	}

	public static final void execWord(String path) {
		String cmd = String.format("\"%s\" /n \"%s\"", getWord(), path);
		try {
			Runtime.getRuntime().exec(cmd);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
