package bg.test;


import org.junit.Assert;
import org.junit.Test;

import bg.test.model.CloudProvider;
import bg.test.model.Environment;
import bg.test.model.Organization;
import bg.test.model.OrganizationEnvironment;

public class SequenceTest {

	@Test
	public void testEquals() {
		
		CloudProvider cp1 = new CloudProvider("OPENSTACK");
		Organization o = new Organization("Organinization 1");
		Environment e = new Environment("Environment 1");
		
		ServiceImpl s = new ServiceImpl();

		
		TaskImpl<?, ?> t = new TaskImpl<>(1,s.createOrganizationStep, cp1, o)
				.then(new TaskImpl<>(2,s.createEnvironmentStep1, cp1, e))
				.then(new TaskImpl<>(3,s.createEnvironmentStep2, cp1, e))
				.then(new TaskImpl<>(4,s.createEnvironmentStep3, cp1, e))
				.then(new TaskImpl<>(5,s.createEnvironmentStep4, cp1, e))
				.then(new TaskImpl<>(6,s.createEnvironmentStep5, cp1, e))
				.then(new TaskImpl<>(7,s.createMergeOrganizationEnvironment, cp1, new OrganizationEnvironment(o, e)));
		
		Assert.assertNull(t.getLastLaunched());
		Assert.assertNull(t.getLastCompleted());
		Assert.assertTrue(t.getNextRunning() == 1);
		Assert.assertFalse(t.isCompleted());
		
		t.execute();
		
		Assert.assertNotNull(t.getLastLaunched());
		Assert.assertTrue(t.getLastLaunched() ==1);
		Assert.assertNull(t.getLastCompleted());
		Assert.assertTrue(t.getNextRunning() == 2);
		Assert.assertFalse(t.isCompleted());
		
		t.complete(1);
		Assert.assertNotNull(t.getLastCompleted());
		Assert.assertNotNull(t.getLastLaunched());
		Assert.assertTrue(t.getLastCompleted() == 1);
		Assert.assertTrue(t.getLastLaunched() == 2);
		Assert.assertTrue(t.getNextRunning() == 3);
		Assert.assertFalse(t.isCompleted());
		
		t.complete(2);
		Assert.assertNotNull(t.getLastCompleted());
		Assert.assertNotNull(t.getLastLaunched());
		Assert.assertTrue(t.getLastCompleted() == 2);
		Assert.assertTrue(t.getLastLaunched() == 3);
		Assert.assertTrue(t.getNextRunning() == 4);
		Assert.assertFalse(t.isCompleted());
		
		t.complete(3);
		Assert.assertNotNull(t.getLastCompleted());
		Assert.assertNotNull(t.getLastLaunched());
		Assert.assertTrue(t.getLastCompleted() == 3);
		Assert.assertTrue(t.getLastLaunched() == 4);
		Assert.assertTrue(t.getNextRunning() == 5);
		Assert.assertFalse(t.isCompleted());

		t.complete(4);
		Assert.assertNotNull(t.getLastCompleted());
		Assert.assertNotNull(t.getLastLaunched());
		Assert.assertTrue(t.getLastCompleted() == 4);
		Assert.assertTrue(t.getLastLaunched() == 5);
		Assert.assertTrue(t.getNextRunning() == 6);
		Assert.assertFalse(t.isCompleted());
		
		t.complete(5);
		Assert.assertNotNull(t.getLastCompleted());
		Assert.assertNotNull(t.getLastLaunched());
		Assert.assertTrue(t.getLastCompleted() == 5);
		Assert.assertTrue(t.getLastLaunched() == 6);
		Assert.assertTrue(t.getNextRunning() == 7);
		Assert.assertFalse(t.isCompleted());

		t.complete(6);
		Assert.assertNotNull(t.getLastCompleted());
		Assert.assertNotNull(t.getLastLaunched());
		Assert.assertTrue(t.getLastCompleted() == 6);
		Assert.assertTrue(t.getLastLaunched() == 7);
		Assert.assertNull(t.getNextRunning());
		Assert.assertFalse(t.isCompleted());
		
		t.complete(7);
		Assert.assertNotNull(t.getLastCompleted());
		Assert.assertTrue(t.getLastCompleted() == 7);
		Assert.assertNull(t.getLastLaunched());
		Assert.assertNull(t.getNextRunning());
		Assert.assertTrue(t.isCompleted());
	}
}
