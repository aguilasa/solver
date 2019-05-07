package solver.types;

import java.util.LinkedList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

public class Problem {

	@Getter
	@Setter
	private ProblemType problemType;

	@Getter
	@Setter
	private Objective objective;
	@Getter
	private List<Restriction> restrictions = new LinkedList<>();

	public void addRestriction(Restriction restriction) {
		restrictions.add(restriction);
	}

}
