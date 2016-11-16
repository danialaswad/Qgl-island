package fr.unice.polytech.qgl.qdd;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import fr.unice.polytech.qgl.qdd.QddSimulator;
import fr.unice.polytech.qgl.qdd.Action;

//import fr.unice.polytech.qgl.qdd.navigation.islandmap.IslandMapTester;

import org.json.JSONObject;

import java.lang.reflect.Method;
import java.lang.reflect.Field;


/**
 * Created by Ulysse on 16/1/2016.
 * */

public class QddSimulatorTest// extends IslandMapTester
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
 			//createXByYMap(1000, 1000);          

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
	public void testSimulator()
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
		try
		{
			action.set( simulator , new Action("heading").addParameter("direction","N") );
			phase.set( simulator , 1 );	
			analyseAnswer.invoke( simulator, new JSONObject("{\"cost\":4,\"extras\":{},\"status\":\"OK\"}"));
		}		
		catch (java.lang.IllegalAccessException e) 
		{
        	}
		catch (java.lang.reflect.InvocationTargetException e) 
		{
        	}
		try
		{
			action.set( simulator , new Action("echo").addParameter("direction","N") );
			phase.set( simulator , 1 );	
			analyseAnswer.invoke( simulator, new JSONObject("{\"cost\": 1,\"extras\":{\"range\":2,\"found\":\"GROUND\"}, \"status\":\"OK\"}"));
		}		
		catch (java.lang.IllegalAccessException e) 
		{
        	}
		catch (java.lang.reflect.InvocationTargetException e) 
		{
        	}
		try
		{
			action.set( simulator , new Action("echo").addParameter("direction","N") );
			phase.set( simulator , 1 );	
			analyseAnswer.invoke( simulator, new JSONObject("{\"cost\":1,\"extras\":{ \"range\":0,\"found\":\"OUT_OF_RANGE\"},\"status\":\"OK\"}"));
		}		
		catch (java.lang.IllegalAccessException e) 
		{
        	}
		catch (java.lang.reflect.InvocationTargetException e) 
		{
        	}
		try
		{
			action.set( simulator , new Action("scan") );
			phase.set( simulator , 1 );	
			analyseAnswer.invoke( simulator, new JSONObject("{\"cost\":2,\"extras\":{\"biomes\":[\"GLACIER\",\"ALPINE\"], \"creeks\": []}, \"status\": \"OK\"}"));
		}		
		catch (java.lang.IllegalAccessException e) 
		{
        	}
		catch (java.lang.reflect.InvocationTargetException e) 
		{
        	}
		try
		{
			action.set( simulator , new Action("scan") );
			phase.set( simulator , 1 );	
			analyseAnswer.invoke( simulator, new JSONObject("{\"cost\":2,\"extras\":{\"biomes\":[\"BEACH\"],\"creeks\": [\"id\"]},\"status\":\"OK\"}"));
		}		
		catch (java.lang.IllegalAccessException e) 
		{
        	}
		catch (java.lang.reflect.InvocationTargetException e) 
		{
        	}
		try
		{
			action.set( simulator , new Action("scan") );
			phase.set( simulator , 1 );	
			analyseAnswer.invoke( simulator, new JSONObject("{\"cost\":2,\"extras\":{\"biomes\":[],\"creeks\":[]},\"status\": \"OK\"}"));
		}		
		catch (java.lang.IllegalAccessException e) 
		{
        	}
		catch (java.lang.reflect.InvocationTargetException e) 
		{
        	}
		try
		{	
			action.set( simulator , new Action("stop") );
			phase.set( simulator , 1 );	
			analyseAnswer.invoke( simulator, new JSONObject("{\"cost\":3,\"extras\":{},\"status\":\"OK\"}"));
		}		
		catch (java.lang.IllegalAccessException e) 
		{
        	}
		catch (java.lang.reflect.InvocationTargetException e) 
		{
        	}
		try
		{
			action.set( simulator , new Action("stop") );
			phase.set( simulator , 2 );	
			analyseAnswer.invoke( simulator, new JSONObject("{\"cost\":3,\"extras\":{},\"status\":\"OK\"}"));
		}		
		catch (java.lang.IllegalAccessException e) 
		{
        	}
		catch (java.lang.reflect.InvocationTargetException e) 
		{
        	}
		try
		{
			Action action1 = new Action("land").addParameter("creek","007");
			action.set( simulator , action1.addParameter("people",30) );
			phase.set( simulator , 1 );	
			analyseAnswer.invoke( simulator, new JSONObject("{\"cost\":15,\"extras\":{},\"status\":\"OK\"}"));
		}		
		catch (java.lang.IllegalAccessException e) 
		{
        	}
		catch (java.lang.reflect.InvocationTargetException e) 
		{
        	}
		try
		{
			Action action1 = new Action("land").addParameter("creek","007");
			action.set( simulator , action1.addParameter("people",30) );
			phase.set( simulator , 2 );	
			analyseAnswer.invoke( simulator, new JSONObject("{\"cost\":15,\"extras\":{},\"status\":\"OK\"}"));
		}		
		catch (java.lang.IllegalAccessException e) 
		{
        	}
		catch (java.lang.reflect.InvocationTargetException e) 
		{
        	}
		try
		{
			action.set( simulator , new Action("move_to").addParameter("direction","N") );
			phase.set( simulator , 2 );	
			analyseAnswer.invoke( simulator, new JSONObject("{\"cost\":6,\"extras\":{},\"status\":\"OK\"}"));
		}		
		catch (java.lang.IllegalAccessException e) 
		{
        	}
		catch (java.lang.reflect.InvocationTargetException e) 
		{
        	}
		try
		{
			action.set( simulator , new Action("scout") );
			phase.set( simulator , 2 );	
			analyseAnswer.invoke( simulator, new JSONObject("{\"cost\":5,\"extras\":{\"altitude\":1,\"resources\":[\"FUR\", \"WOOD\"]},\"status\":\"OK\"}"));
		}		
		catch (java.lang.IllegalAccessException e) 
		{
        	}
		catch (java.lang.reflect.InvocationTargetException e) 
		{
        	}
		try
		{
			action.set( simulator , new Action("glimpse") );
			phase.set( simulator , 2 );	
			analyseAnswer.invoke( simulator, new JSONObject("{\"cost\":3,\"status\":\"OK\",\"extras\":{\"asked_range\":4,\"report\":[[[\"BEACH\", 59.35],[\"OCEAN\",40.65]],[[\"OCEAN\",100]],[\"OCEAN\"],[\"OCEAN\"]]}}"));
		}		
		catch (java.lang.IllegalAccessException e) 
		{
        	}
		catch (java.lang.reflect.InvocationTargetException e) 
		{
        	}
		try
		{
			action.set( simulator , new Action("explore") );
			phase.set( simulator , 2 );	
			analyseAnswer.invoke( simulator, new JSONObject("{\"cost\":5,\"extras\":{\"resources\":[{\"amount\":\"HIGH\",\"resource\":\"FUR\",\"cond\":\"FAIR\"},{\"amount\":\"LOW\",\"resource\":\"WOOD\",\"cond\":\"HARSH\"}],\"pois\":[\"creek-id\"]},\"status\":\"OK\"}"));
		}		
		catch (java.lang.IllegalAccessException e) 
		{
        	}
		catch (java.lang.reflect.InvocationTargetException e) 
		{
        	}
		try
		{			
			action.set( simulator , new Action("exploit").addParameter("resource","GLASS") );
			phase.set( simulator , 2 );	
			analyseAnswer.invoke( simulator, new JSONObject("{\"cost\":3,\"extras\":{\"amount\":9},\"status\":\"OK\"}"));
		}		
		catch (java.lang.IllegalAccessException e) 
		{
        	}
		catch (java.lang.reflect.InvocationTargetException e) 
		{
        	}
		try
		{
			action.set( simulator , new Action("transform") );
			phase.set( simulator , 2 );	
			analyseAnswer.invoke( simulator, new JSONObject("{\"cost\":5,\"extras\":{\"production\":1,\"kind\": \"GLASS\"},\"status\":\"OK\"}"));
		}		
		catch (java.lang.IllegalAccessException e) 
		{
        	}
		catch (java.lang.reflect.InvocationTargetException e) 
		{
        	}
		try
		{
			phase.set( simulator , 1 );	
			Assert.assertEquals( "{\"action\":\"echo\",\"parameters\":{\"direction\":\"N\"}}" , nextDecision.invoke(simulator) );
		}		
		catch (java.lang.IllegalAccessException e) 
		{
        	}
		catch (java.lang.reflect.InvocationTargetException e) 
		{
        	}
	}
}

