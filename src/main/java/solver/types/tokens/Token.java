package solver.types.tokens;

public interface Token<T> {
	
	T getValue();
	
	void setValue(T value);

}
