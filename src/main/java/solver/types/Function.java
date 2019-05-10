package solver.types;

import java.util.LinkedList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import solver.types.tokens.Comparation;
import solver.types.tokens.Token;

public abstract class Function {

	@Getter
	protected List<Token<?>> tokens = new LinkedList<>();
	
	@Getter
	protected Comparation comparation;

	@Getter
	@Setter
	protected String name;

	public void addToken(Token<?> token) {
		if (token instanceof Comparation) {
			comparation = (Comparation) token;
		} else {
			tokens.add(token);
		}
	}
	
	@Override
	public abstract String toString();

}
