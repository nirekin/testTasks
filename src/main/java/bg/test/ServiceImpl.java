package bg.test;

import bg.test.model.CloudProvider;
import bg.test.model.Environment;
import bg.test.model.Organization;
import bg.test.model.OrganizationEnvironment;

public class ServiceImpl {

	public Step<CloudProvider, Organization> createOrganizationStep = (t) -> {return create(t);};
	public Step<CloudProvider, Environment> createEnvironmentStep1 = (t) -> { return createPhase1(t);};
	public Step<CloudProvider, Environment> createEnvironmentStep2 = (t) -> {return createPhase2(t);};
	public Step<CloudProvider, Environment> createEnvironmentStep3 = (t) -> {return createPhase3(t);};
	public Step<CloudProvider, Environment> createEnvironmentStep4 = (t) -> {return createPhase4(t);};
	public Step<CloudProvider, Environment> createEnvironmentStep5 = (t) -> {return createPhase5(t);};
	public Step<CloudProvider, OrganizationEnvironment> createMergeOrganizationEnvironment = (t) -> {return createMerge(t);}; 
	
	
	private Organization create(TaskImpl<CloudProvider, Organization> task) {
		CloudProvider cp = task.getSupport();
		Organization o = task.getTarget();
		System.out.println("Running org " + cp.getName()+ "/" + o.getName());
		o.setName(o.getName() + "OK");
		return o;
	}

	private Environment createPhase1(TaskImpl<CloudProvider, Environment> task) {
		CloudProvider cp = task.getSupport();
		Environment e = task.getTarget();
		System.out.println("Running env " + cp.getName() + "/" + e.getName() + " - Phase 1");
		e.setCurrentStep(e.getCurrentStep() +1);
		return e;
	}

	private Environment createPhase2(TaskImpl<CloudProvider, Environment> task) {
		CloudProvider cp = task.getSupport();
		Environment e = task.getTarget();
		System.out.println("Running env " + cp.getName() + "/" + e.getName() + " - Phase 2");
		e.setCurrentStep(e.getCurrentStep() +1);
		return e;
	}

	private Environment createPhase3(TaskImpl<CloudProvider, Environment> task) {
		CloudProvider cp = task.getSupport();
		Environment e = task.getTarget();
		System.out.println("Running env " + cp.getName() + "/" + e.getName() + " - Phase 3");
		e.setCurrentStep(e.getCurrentStep() +1);
		return e;
	}

	private Environment createPhase4(TaskImpl<CloudProvider, Environment> task) {
		CloudProvider cp = task.getSupport();
		Environment e = task.getTarget();
		System.out.println("Running env " + cp.getName() + "/" + e.getName() + " - Phase 4");
		e.setCurrentStep(e.getCurrentStep() +1);
		return e;
	}

	private Environment createPhase5(TaskImpl<CloudProvider, Environment> task) {
		CloudProvider cp = task.getSupport();
		Environment e = task.getTarget();
		System.out.println("Running env " + cp.getName() + "/" + e.getName() + " - Phase 5");
		e.setCurrentStep(e.getCurrentStep() +1);
		return e;
	}

	private OrganizationEnvironment createMerge(TaskImpl<CloudProvider, OrganizationEnvironment> task) {
		CloudProvider cp = task.getSupport();
		OrganizationEnvironment oe = task.getTarget();
		System.out.println("Running org / env :" + cp.getName() + "/" + oe.getOrg().getName() + " - " + oe.getEnv().getName() + "/" + oe.getEnv().getCurrentStep());
		return oe;
	}

}
