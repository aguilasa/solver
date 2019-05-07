package solver.types;

import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import lombok.Getter;
import lombok.Setter;
import solver.types.tokens.Composition;
import solver.types.tokens.Token;
import solver.types.tokens.Variable;

public class Problem {

	@Getter
	@Setter
	private ProblemType problemType;

	@Getter
	private Objective objective;
	@Getter
	private List<Restriction> restrictions = new LinkedList<>();

	@Getter
	private Set<Variable> variables = new LinkedHashSet<>();

	public void setObjective(Objective objective) {
		this.objective = objective;
		processVariables();
	}

	public void addRestriction(Restriction restriction) {
		restrictions.add(restriction);
	}

	private void processVariables() {
		variables.clear();
		List<Token<?>> tokens = new LinkedList<>(objective.getTokens());
		tokens.remove(0);
		for (Token<?> token : tokens) {
			if (token instanceof Composition) {
				variables.add(((Composition) token).getVariable());
			}
		}
	}

}
