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

	public void setObjective(Objective objective, List<String> names) {
		this.objective = objective;
		processVariables(names);
	}

	public void addRestriction(Restriction restriction) {
		restrictions.add(restriction);
	}

	private void processVariables(List<String> names) {
		int i = 0;
		variables.clear();
		List<Token<?>> tokens = objective.getTokens();
		for (Token<?> token : tokens) {
			if (token instanceof Composition) {
				Variable variable = ((Composition) token).getVariable();
				if (i < names.size()) {
					variable.setName(names.get(i++));
				}
				variables.add(variable);
			}
		}
	}

}
