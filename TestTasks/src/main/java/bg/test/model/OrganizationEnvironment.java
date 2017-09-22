package bg.test.model;

public class OrganizationEnvironment {
	Organization org;
	Environment env;

	public OrganizationEnvironment(Organization o, Environment e){
		this.org = o;
		this.env = e;
	}

	public Organization getOrg() {
		return org;
	}

	public void setOrg(Organization org) {
		this.org = org;
	}

	public Environment getEnv() {
		return env;
	}

	public void setEnv(Environment env) {
		this.env = env;
	}
	
	
}
