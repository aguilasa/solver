package solver.types.tokens;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Fraction {

	private int numerator;
	private int denominator;

	@Override
	public String toString() {
		return String.format("%d/%d", numerator, denominator);
	}

}
