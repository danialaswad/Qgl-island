package fr.unice.polytech.qgl.qdd;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import fr.unice.polytech.qgl.qdd.QddSimulator;
import fr.unice.polytech.qgl.qdd.Action;

import org.json.JSONObject;

import java.lang.reflect.Method;
import java.lang.reflect.Field;


/**
 * Created by Ulysse on 16/1/2016.
 * */

public class ExplorerAiTest
{

	private QddSimulator simulator;

	private Method analyseAnswer;
	private Method nextDecision;
	private Method createTileMethod;

	private Field action;
	private Field phase;

	@Before
	public void Setup() 
	{

		String init = new String("{\"men\": 12,\"budget\": 10000,\"contracts\": [{ \"amount\": 600, \"resource\": \"WOOD\" },{ \"amount\": 200, \"resource\": \"GLASS\" }],\"heading\": \"W\"}");
		
		
		simulator = new QddSimulator(init);

		try 
		{
            		analyseAnswer = QddSimulator.class.getDeclaredMethod("analyseAnswer", JSONObject.class);
            		analyseAnswer.setAccessible(true);

			nextDecision = QddSimulator.class.getDeclaredMethod("nextDecision");
            		nextDecision.setAccessible(true);

			action = QddSimulator.class.getDeclaredField("action");
            		action.setAccessible(true);
			
			phase = QddSimulator.class.getDeclaredField("phase");
            		phase.setAccessible(true);

        	} 
		catch (NoSuchMethodException e) 
		{
        	}
		catch (NoSuchFieldException e) 
		{
        	}	
	}

	
	@Test
	public void testExplorerAi()
	{
		try
		{
			action.set( simulator , new Action("fly") );
			phase.set( simulator , 1 );	
			analyseAnswer.invoke( simulator, new JSONObject("{\"cost\":2,\"extras\":{},\"status\":\"OK\"}"));
		}		
		catch (java.lang.IllegalAccessException e) 
		{
        	}
		catch (java.lang.reflect.InvocationTargetException e) 
		{
        	}
	}
}

