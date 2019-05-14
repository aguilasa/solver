package solver.types;

public enum ProblemType {
	MAX("max"), MIN("min");

	private String type;

	private ProblemType(String type) {
		this.type = type;
	}

	public static ProblemType getEnum(String value) {
		ProblemType[] values = values();
		for (ProblemType t : values) {
			if (t.type.equalsIgnoreCase(value)) {
				return t;
			}
		}
		throw new RuntimeException(String.format("Enumeração %s não encontrada.", value));
	}

	@Override
	public String toString() {
		return type;
	}

}
