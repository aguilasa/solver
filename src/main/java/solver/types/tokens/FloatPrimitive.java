package solver.types.tokens;

import static solver.utils.Utils.formatFloat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FloatPrimitive extends Primitive<Float> {

	private Float value;

	@Override
	public String toString() {
		return formatFloat(value);
	}

}
