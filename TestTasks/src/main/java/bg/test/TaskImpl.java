package bg.test;

public class TaskImpl<S, T>{

	enum Status{
		NOT_STARTED,
		LAUNCHED,
		COMPLETED
	}
	
	int id;
	Step<S, T> step;
	S support;
	T target;
	TaskImpl<?, ?> previous;
	TaskImpl<?, ?> next;
	Status status = Status.NOT_STARTED;

	public TaskImpl(int id, Step<S, T> step, S support, T target) {
		this.id = id;
		this.step = step;
		this.support = support;
		this.target = target;
	}

	public void execute() {
		if(previous != null && previous.status == Status.NOT_STARTED) {
			previous.execute();
		}else {
			if(status == Status.NOT_STARTED) {
				status = Status.LAUNCHED;
				step.runStep(this);
			}
		}
	}

	public S getSupport() {
		return support;
	}
	
	public T getTarget() {
		return target;
	}
	
	public void complete(int id) {
		if(this.id == id) {
			status = Status.COMPLETED;
			if(next != null)
				next.execute();
		}else if(previous != null){
			previous.complete(id);
		}
	}

	public Integer getLastCompleted() {
		if(status == Status.COMPLETED) {
			return id; 
		}else if(previous != null){
			return previous.getLastCompleted();
		}
		return null;
	}

	public Integer getLastLaunched() {
		if(status == Status.LAUNCHED) {
			return id; 
		}else if(previous != null){
			return previous.getLastLaunched();
		}
		return null;
	}
	
	public Integer getNextRunning() {
		if(status == Status.NOT_STARTED && previous != null && (previous.status == Status.COMPLETED || previous.status == Status.LAUNCHED)) {
			return id; 
		}else if(previous == null && status == Status.NOT_STARTED){
			return id;
		}else if(previous != null) { 
			return previous.getNextRunning();
		}
		return null;
	}

	public boolean isCompleted() {
		return status == Status.COMPLETED;
	}
	
	public TaskImpl<?, ?> then(TaskImpl<?, ?> next){
		this.next = next;
		next.previous = this;
		return next;
	}
}