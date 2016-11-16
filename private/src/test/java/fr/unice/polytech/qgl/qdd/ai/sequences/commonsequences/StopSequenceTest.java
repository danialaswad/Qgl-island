package fr.unice.polytech.qgl.qdd.ai.sequences.commonsequences;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import fr.unice.polytech.qgl.qdd.Action;

import java.lang.reflect.Method;


/**
 * Created by Ulysse on 16/1/2016.
 * */

public class StopSequenceTest
{

	private StopSequence stopSequence;

	private Method execute;
	private Method completed;

	@Before
	public void Setup() 
	{

		stopSequence = new StopSequence(null,null);

		try 
		{
            		execute = StopSequence.class.getDeclaredMethod("execute");
            		execute.setAccessible(true);

			completed = StopSequence.class.getDeclaredMethod("completed");
            		completed.setAccessible(true);

        	} 
		catch (NoSuchMethodException e) 
		{
        	}
	}

	
	@Test
	public void testStopSequence()
	{
		Action toto = stopSequence.execute();
		Assert.assertEquals( "{\"action\":\"stop\"}" , toto.toJSON() );
		Assert.assertEquals( true , stopSequence.completed() );
	}
}

