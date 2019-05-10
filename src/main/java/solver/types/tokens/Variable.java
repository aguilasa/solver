package solver.types.tokens;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Variable implements Token<String> {

	private String value;

	@EqualsAndHashCode.Exclude
	private String name;

	@Override
	public String toString() {
		return value;
	}

}
