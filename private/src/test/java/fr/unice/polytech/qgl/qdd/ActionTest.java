package fr.unice.polytech.qgl.qdd;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import fr.unice.polytech.qgl.qdd.Action;

/**
 * Created by Ulysse on 16/1/2016.
 * */

public class ActionTest
{

	private Action action;

	@Before
	public void Setup() 
	{
		
	}

	
	@Test
	public void testAction()
	{

		action = new Action("fly");
		Assert.assertEquals( "{\"action\":\"fly\"}" , action.toJSON() );

		action = new Action("heading");
		action.addParameter( "direction" , "N" );
		Assert.assertEquals( "{\"action\":\"heading\",\"parameters\":{\"direction\":\"N\"}}" , action.toJSON() );
		
		action = new Action("heading");
		action.addParameter( "direction" , "S" );
		Assert.assertEquals( "{\"action\":\"heading\",\"parameters\":{\"direction\":\"S\"}}" , action.toJSON() );
		
		action = new Action("echo");
		action.addParameter( "direction" , "E" );
		Assert.assertEquals( "{\"action\":\"echo\",\"parameters\":{\"direction\":\"E\"}}" , action.toJSON() );
	
		action = new Action("scan");
		Assert.assertEquals( "{\"action\":\"scan\"}" , action.toJSON() );

		action = new Action("stop");
		Assert.assertEquals( "{\"action\":\"stop\"}" , action.toJSON() );

		action = new Action("land");
		action.addParameter( "creek" , "id" );
		action.addParameter( "people" , 42 );
		Assert.assertEquals( "{\"action\":\"land\",\"parameters\":{\"creek\":\"id\",\"people\":42}}" , action.toJSON() );
		
		action = new Action("move_to");
		action.addParameter( "direction" , "N" );
		Assert.assertEquals( "{\"action\":\"move_to\",\"parameters\":{\"direction\":\"N\"}}" , action.toJSON() );
		
		action = new Action("scout");
		action.addParameter( "direction" , "N" );
		Assert.assertEquals( "{\"action\":\"scout\",\"parameters\":{\"direction\":\"N\"}}" , action.toJSON() );
		
		action = new Action("glimpse");
		action.addParameter( "direction" , "N" );
		action.addParameter( "range" , 2 );
		Assert.assertEquals( 2 , action.getIntParam("range") );
		Assert.assertEquals( "N" , action.getStringParam("direction") );
		
		action = new Action("glimpse");
		action.addParameter( "direction" , "W" );
		action.addParameter( "range" , 4 );
		Assert.assertEquals( "{\"action\":\"glimpse\",\"parameters\":{\"range\":4,\"direction\":\"W\"}}" , action.toJSON() );
		Assert.assertEquals( "{\"action\":\"glimpse\",\"parameters\":{\"range\":4,\"direction\":\"W\"}}" , action.toJSON() );
		

		action = new Action("explore");
		Assert.assertEquals( "{\"action\":\"explore\"}" , action.toJSON() );

		action = new Action("exploit");
		action.addParameter( "resource" , "FUR" );
		Assert.assertEquals( "{\"action\":\"exploit\",\"parameters\":{\"resource\":\"FUR\"}}" , action.toJSON() );
		Assert.assertEquals( "exploit" , action.getAction() );		
		
		action = new Action("transform");
		action.addParameter( "WOOD" , 6 );
		action.addParameter( "QUARTZ" , 11 );
		Assert.assertEquals( "{\"action\":\"transform\",\"parameters\":{\"WOOD\":6,\"QUARTZ\":11}}" , action.toJSON() );
		
	}
	
}

