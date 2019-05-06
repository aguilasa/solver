package solver.types.tokens;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Composition implements Token<FloatPrimitive> {

	private FloatPrimitive value;
	private Variable variable;

	@Override
	public String toString() {
		return String.format("%s%s", value.toString(), variable.toString());
	}

}
