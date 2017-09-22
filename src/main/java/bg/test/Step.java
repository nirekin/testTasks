package bg.test;

/**
 * 
 *
 * @param <T> The type of the object supporting the action 
 * @param <U> The target of the action
 */
public interface Step<T, U> {
	
	U runStep(TaskImpl<T, U> task);
}