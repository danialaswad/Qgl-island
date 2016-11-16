package fr.unice.polytech.qgl.qdd;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import fr.unice.polytech.qgl.qdd.QddExplorer;
import fr.unice.polytech.qgl.qdd.Action;
import fr.unice.polytech.qgl.qdd.enums.Resource;
import fr.unice.polytech.qgl.qdd.explorer.Contract;
import fr.unice.polytech.qgl.qdd.ai.ExplorerAI;

import org.json.JSONObject;

import java.lang.reflect.Method;
import java.lang.reflect.Field;

import java.util.*;

/**
 * Created by Ulysse on 16/1/2016.
 * */

public class QddExplorerTest
{

	private QddExplorer explorer;

	private Method analyseAnswer;
	private Method nextDecision;
	private Method createTileMethod;

	private Field action;
	private Field phase;

	private Field resources;

	@Before
	public void Setup() 
	{

		String init = new String("{\"men\":12,\"budget\":20000,\"contracts\":[{\"amount\":600,\"resource\":\"WOOD\"},{\"amount\":200,\"resource\":\"RUM\"}],\"heading\":\"W\"}");
		
		explorer = new QddExplorer(init);	
	}

	
	@Test
	public void testExplorer()
	{
		Assert.assertEquals( 20000 , explorer.getInitialBudget() );
		Assert.assertEquals( 12 , explorer.getMen() );
		Assert.assertEquals( 0	, explorer.getResourceQuantity( Resource.FUR ) );
		
		Map<Resource,Integer> map = explorer.getContract();
		for ( int i = 0; i < map.size() ; i++ )
		{
			Assert.assertEquals( null , map.get(i) );
		}

		Set<Resource> set = explorer.getResourcesToCollect(); 		
		Assert.assertEquals( true , set.contains(Resource.WOOD) );
		Assert.assertEquals( true , set.contains(Resource.FRUITS) );
		Assert.assertEquals( true , set.contains(Resource.SUGAR_CANE) );

		Map<Resource, Integer> map1 = explorer.getResources();
		for ( int i = 0; i < map1.size() ; i++ )
		{
			Assert.assertEquals( null , map1.get(i) );
		}
	}

	@Test
	public void scoutTest() {
		Contract contract = new Contract();
		contract.addRessource(Resource.GLASS, 50);
		Contract contract2 = new Contract();
		contract2.addRessource(Resource.GLASS, 0);
		Set<Resource> resourcesContracted = contract.getResourcesToCollect(contract2);
		Assert.assertEquals(resourcesContracted.size(),2);
		Set<Resource> resourceSet = new HashSet<>();
		resourceSet.add(Resource.FISH);

		boolean exploitable = resourceSet.stream().filter(resourcesContracted::contains).count() > 0;

		Assert.assertEquals(false,exploitable);

		resourceSet.add(Resource.WOOD);

		exploitable = resourceSet.stream().filter(resourcesContracted::contains).count() > 0;
		Assert.assertEquals(true,exploitable);
		
		contract2.addRessource(Resource.WOOD, 5001);
		resourcesContracted = contract.getResourcesToCollect(contract2);
		Assert.assertEquals(resourcesContracted.size(),1);

		exploitable = resourceSet.stream().filter(resourcesContracted::contains).count() > 0;
		Assert.assertEquals(false,exploitable);
	}

	@Test
	public void getManufacturedResourcesTest(){

		Map<Resource, Integer> manufacturedResources = explorer.getManufacturedResources();

		Assert.assertEquals(1, manufacturedResources.size());
		Assert.assertEquals(Resource.RUM, manufacturedResources.keySet().stream().findFirst().orElse(null));
		Assert.assertEquals(true, manufacturedResources.containsKey(Resource.RUM));
		Assert.assertEquals(200, (int)manufacturedResources.get(Resource.RUM));
	}

	@Test
	public void getResourcesToCollectTest(){

		Assert.assertEquals(3, explorer.getResourcesToCollect().size());
		Assert.assertEquals(true,explorer.getResourcesToCollect().contains(Resource.SUGAR_CANE));
		Assert.assertEquals(true,explorer.getResourcesToCollect().contains(Resource.FRUITS));
		Assert.assertEquals(true, explorer.getResourcesToCollect().contains(Resource.WOOD));

		explorer.getResources().put(Resource.FRUITS,20);
		explorer.getResources().put(Resource.RUM,180);

		Assert.assertEquals(2, explorer.getResourcesToCollect().size());
	}

	@Test
	public void getResourcesToCollectTest2(){

		explorer.getResources().put(Resource.RUM,180);

		Assert.assertEquals(3, explorer.getResourcesToCollect().size());

		explorer.getResources().put(Resource.SUGAR_CANE,200);

		Assert.assertEquals(2, explorer.getResourcesToCollect().size());

		explorer.getResources().put(Resource.FRUITS,20);

		Assert.assertEquals(1, explorer.getResourcesToCollect().size());
	}

	@Test
	public void getResourcesToCollectTest3(){

		Set<Resource> resourceSet = new HashSet<>();

		resourceSet.add(null);

		Assert.assertEquals(1,resourceSet.size());
		Assert.assertEquals(true, resourceSet.contains(null));
	}

}

