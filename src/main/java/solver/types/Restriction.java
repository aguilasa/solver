package solver.types;

import lombok.Getter;
import solver.types.tokens.FloatPrimitive;
import solver.types.tokens.Token;

public class Restriction extends Function {

	@Getter
	private FloatPrimitive limit;

	@Override
	public void addToken(Token<?> token) {
		if (comparation != null && token instanceof FloatPrimitive) {
			limit = (FloatPrimitive) token;
		} else {
			super.addToken(token);
		}
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (Token<?> token : tokens) {
			sb.append(" ");
			sb.append(token.toString());
		}
		sb.append(" ").append(comparation.toString());
		sb.append(" ").append(limit.toString());
		return sb.toString().trim();
	}

}
