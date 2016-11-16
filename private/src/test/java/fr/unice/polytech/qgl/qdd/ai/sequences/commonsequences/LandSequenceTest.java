package fr.unice.polytech.qgl.qdd.ai.sequences.commonsequences;

import fr.unice.polytech.qgl.qdd.ai.sequences.SequencesTest;
import fr.unice.polytech.qgl.qdd.enums.Biome;
import fr.unice.polytech.qgl.qdd.navigation.Tile;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Ulysse on 16/1/2016.
 * */

public class LandSequenceTest extends SequencesTest{

	private LandSequence landSequence;

	@Before
	public void setup(){
		newExplorer();
	}

	@Test
	public void testLandSequence()
	{
		setPosition(7,7);
		Tile tile = getNav().map().currentTile();
		Assert.assertEquals(getX(tile),7 );
		Assert.assertEquals(getY(tile),7 );

		landSequence = new LandSequence(getExplorer().getNavigator(),getExplorerAI().getCheckList(),1);

		Assert.assertEquals( false , landSequence.completed() );

		List<String> creeks = new ArrayList<>();
		creeks.add("danial");
		List<Biome> biomes = new ArrayList<>();
		biomes.add(Biome.BEACH);
		biomes.add(Biome.OCEAN);
		getNav().map().updateMapThroughScan(biomes,false);
		getNav().map().updateMapWithCreeks(creeks);

		Assert.assertEquals(getNav().map().getCreeks().get(0), "danial");

		Assert.assertEquals(landSequence.execute().getStringParam("creek"), "danial");

		Assert.assertEquals( true , landSequence.completed() );
	}

	@Test
	public void testLandSequenceWithPottentiallyExploitable()
	{
		setPosition(7,7);
		Tile tile = getNav().map().currentTile();
		Assert.assertEquals(getX(tile),7 );
		Assert.assertEquals(getY(tile),7 );

		landSequence = new LandSequence(getExplorer().getNavigator(),getExplorerAI().getCheckList(),1);

		Assert.assertEquals( false , landSequence.completed() );

		List<String> creeks = new ArrayList<>();
		creeks.add("danial");
		List<Biome> biomes = new ArrayList<>();
		biomes.add(Biome.BEACH);
		biomes.add(Biome.OCEAN);
		getNav().map().updateMapThroughScan(biomes,false);
		getNav().map().updateMapWithCreeks(creeks);

		setPosition(13,7);

		List<String> creeks2 = new ArrayList<>();
		creeks2.add("aswad");
		List<Biome> biomes2 = new ArrayList<>();
		biomes2.add(Biome.MANGROVE);
		biomes2.add(Biome.OCEAN);
		getNav().map().updateMapThroughScan(biomes2,true);
		getNav().map().updateMapWithCreeks(creeks2);

		Assert.assertEquals(landSequence.execute().getStringParam("creek"), "aswad");
		Assert.assertEquals( true , landSequence.completed() );
	}
}

