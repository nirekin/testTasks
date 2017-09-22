package bg.test;

/**
 * 
 *
 * @param <S> The type of the object supporting the action 
 * @param <T> The target of the action
 */
public interface Step<S, T> {
	
	T runStep(TaskImpl<S, T> task);
}