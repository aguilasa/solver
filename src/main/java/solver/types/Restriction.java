package solver.types;

import java.util.LinkedList;
import java.util.List;

import solver.types.tokens.Token;

public class Restriction {

	private List<Token<?>> tokens = new LinkedList<>();

	public void addToken(Token<?> token) {
		tokens.add(token);
	}

	@Override
	public String toString() {
		StringBuilder ts = new StringBuilder();
		for (Token<?> token : tokens) {
			ts.append(" ");
			ts.append(token.toString());
		}
		return ts.toString().trim();
	}

}
