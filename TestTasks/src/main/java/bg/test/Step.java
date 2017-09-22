package bg.test;


public interface Step<T, U> {
	U runStep(TaskImpl<T, U> task);
}