package solver.types;

import java.util.LinkedList;
import java.util.List;

import lombok.Getter;
import solver.types.tokens.Token;

public class Function {

	@Getter
	private List<Token<?>> tokens = new LinkedList<>();

	public void addToken(Token<?> token) {
		tokens.add(token);
	}

	public void assign(Function other) {
		tokens.clear();
		tokens.addAll(other.getTokens());
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
