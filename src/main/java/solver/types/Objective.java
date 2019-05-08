package solver.types;

import lombok.Getter;
import solver.types.tokens.Composition;
import solver.types.tokens.Token;
import solver.types.tokens.Variable;

public class Objective extends Function {

	@Getter
	private Variable variable;

	@Override
	public void addToken(Token<?> token) {
		if (variable == null && token instanceof Composition) {
			variable = ((Composition) token).getVariable();
		} else {
			super.addToken(token);
		}
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(variable.toString());
		sb.append(" ").append(comparation.toString());
		for (Token<?> token : tokens) {
			sb.append(" ");
			sb.append(token.toString());
		}
		return sb.toString().trim();
	}

}
