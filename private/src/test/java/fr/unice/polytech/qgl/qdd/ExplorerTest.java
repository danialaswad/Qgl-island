package fr.unice.polytech.qgl.qdd;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


/**
 * Created by Ulysse on 16/1/2016.
 * */

public class ExplorerTest 
{

	private Explorer explorer;

	private String init;

	@Before
	public void Setup() 
	{

		init = new String("{\"men\": 12,\"budget\": 10000,\"contracts\": [{ \"amount\": 600, \"resource\": \"WOOD\" },{ \"amount\": 200, \"resource\": \"GLASS\" }],\"heading\": \"W\"}");
		explorer = new Explorer();
	}

	
	@Test
	public void testExplorer()
	{
		explorer.initialize(init);
		 Assert.assertEquals( "{\"action\":\"echo\",\"parameters\":{\"direction\":\"W\"}}" , explorer.takeDecision() );
		 Assert.assertEquals( "{\"action\":\"echo\",\"parameters\":{\"direction\":\"N\"}}" , explorer.takeDecision() );
		 Assert.assertEquals( "{\"action\":\"echo\",\"parameters\":{\"direction\":\"S\"}}" , explorer.takeDecision() );
	
		explorer = new Explorer();
		explorer.initialize(init);
		 Assert.assertEquals( "{\"action\":\"echo\",\"parameters\":{\"direction\":\"W\"}}" , explorer.takeDecision() );
		explorer.acknowledgeResults("{\"cost\":1,\"extras\":{\"range\":2,\"found\":\"GROUND\"},\"status\":\"OK\"}");
	}
}

