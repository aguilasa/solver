package solver.types.tokens;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Comparation implements Token<String> {

	private String value;

	@Override
	public String toString() {
		return value;
	}

}
