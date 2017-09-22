## Stack of Tasks 
---
### Step

A step represents the action which will be executed by a task.

It accepts two types :
* S: The type of the object supporting the action ( IE: CloudProvider )
* T: The type of the object target of the action ( IE: Organization, Environment...)

```java
Step<S, T>
```

Steps should be defined by the implementations of the CloudProvider services to map their methods

```java
public Step<CloudProvider, Organization> createOrganizationStep = (t) -> {return create(t);};
public Step<CloudProvider, Environment> createEnvironmentStep1 = (t) -> { return createPhase1(t);};
public Step<CloudProvider, Environment> createEnvironmentStep2 = (t) -> {return createPhase2(t);};
        
private Organization create(TaskImpl<CloudProvider, Organization> task) {
  ...
}

private Environment createPhase1(TaskImpl<CloudProvider, Environment> task) {
    ...
}

private Environment createPhase2(TaskImpl<CloudProvider, Environment> task) {
    ...
}
```

### Task

A task will be create using a `Step`, a supporting object and a target orbjet

It also accepts two types :
* S: The type of the object supporting the action ( IE: CloudProvider )
* T: The type of the object target of the action ( IE: Organization, Environment...)
```java
TaskImpl<S, T>
```
Task creation:

```java
CloudProvider cp1 = new CloudProvider("OPENSTACK");
Organization o = new Organization("Organinization 1");
TaskImpl<CloudProvider, Organization> t = new TaskImpl<>(1,s.createOrganizationStep, cp1, o)
```

Tasks can be chained:

```java
CloudProvider cp1 = new CloudProvider("OPENSTACK");
Organization o = new Organization("Organinization 1");
Environment e = new Environment("Environment 1");
		
TaskImpl<?, ?> t = new TaskImpl<>(1,s.createOrganizationStep, cp1, o)
    .then(new TaskImpl<>(2,s.createEnvironmentStep1, cp1, e))
    .then(new TaskImpl<>(3,s.createEnvironmentStep2, cp1, e))
    .then(new TaskImpl<>(4,s.createEnvironmentStep3, cp1, e))
    .then(new TaskImpl<>(5,s.createEnvironmentStep4, cp1, e))
    .then(new TaskImpl<>(6,s.createEnvironmentStep5, cp1, e));
```
If we decide to use a chain of tasks the task `t` returned by the chaining operation will be the last one (`TaskImpl<>(6...)`).

The last task of the chain is the one which is supposed to be invoked to execute the chain content, to mark a task as completed or to obtain information about the status of the execution of the whole chain content.

### Chain execution

```java
TaskImpl<?, ?> t = new TaskImpl<>(1,...)
    .then(new TaskImpl<>(2,...))
    .then(new TaskImpl<>(3,...))
    .then(new TaskImpl<>(4,...))
    .then(new TaskImpl<>(5,...));
    
t.execute();    
```
Calling `t.execute();` 
* will trigger the execution of the fisrt task of the chain with the status `NOT_STARTED` so `TaskImpl<>(1,...)`.
* the task status will change from `NOT_STARTED` to `LAUNCHED`
* the execution will stop until `TaskImpl<>(1,...)` has been completed

Calling `t.complete(1);`  
* the `TaskImpl<>(1,...)` status will change from `LAUNCHED` to `COMPLETED`
* the execution of the next task into the chain `TaskImpl<>(2,...)` will be triggered 
* the execution will stop until `TaskImpl<>(2,...)` has been completed

...and so forth until the end of the chain.

### Task methods:

`public void execute()` : executes the first task available into the chain
		
`public S getSupport()` : returns the object supporting the action of the task 
	
`public T getTarget()` : returns the object target of the action of the class
	
`public void complete(int id)` : completes the task with the given id

`public void markAsFailed(int id)` : taghs the task with the given id as failed (Not done yet)

`public Integer getLastCompleted()` : returns the id of the last completed task or null

`public Integer getLastLaunched()` : returns the id of the last launched task or null
		
`public Integer getNextRunning()` : returns the id of the next task to be launched or null
		
`public boolean isCompleted()` : indicates if the whole chain is completed
		
`public TaskImpl<?, ?> then(TaskImpl<?, ?> next)` : allows to chain a task 
		
