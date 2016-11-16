package fr.unice.polytech.qgl.qdd.ai.sequences.terrestrialsequences;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import fr.unice.polytech.qgl.qdd.Action;
import fr.unice.polytech.qgl.qdd.navigation.Compass;
import fr.unice.polytech.qgl.qdd.navigation.Navigator;

public class GlimpseExplorationTest {
	
	@Test
	public void GlimpseExplorationBasicTest(){
		Navigator nav = Mockito.spy(new Navigator(Compass.EAST));
		GlimpseSequence glimpse = new GlimpseSequence(nav, null, 1);
		
		Action glimpse1 = glimpse.execute();
		Assert.assertEquals(glimpse1.getAction(),"glimpse");
		Assert.assertEquals(glimpse1.getIntParam("range"), 1);
		Assert.assertEquals(glimpse1.getStringParam("direction"),"E");
		
		Action glimpse2 = glimpse.execute();
		Assert.assertEquals(glimpse2.getAction(),"glimpse");
		Assert.assertEquals(glimpse2.getIntParam("range"), 1);
		Assert.assertEquals(glimpse2.getStringParam("direction"),"S");
		
		Action glimpse3 = glimpse.execute();
		Assert.assertEquals(glimpse3.getAction(),"glimpse");
		Assert.assertEquals(glimpse3.getIntParam("range"), 1);
		Assert.assertEquals(glimpse3.getStringParam("direction"),"N");
		
		Action move = glimpse.execute();
		Assert.assertEquals(move.getAction(), "move_to");
		Assert.assertEquals(move.getStringParam("direction"),"E");
		
		boolean completed = glimpse.completed();
		Assert.assertEquals(completed, true);
	}
}
