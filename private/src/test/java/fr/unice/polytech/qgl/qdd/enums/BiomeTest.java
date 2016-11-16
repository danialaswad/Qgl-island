package fr.unice.polytech.qgl.qdd.enums;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by Ulysse on 16/1/2016.
 * */

public class BiomeTest
{

	private Biome[] biome;
	private Biome[] biome1;

	@Before
	public void Setup() 
	{
		
		biome = new Biome[]
		{
			Biome.ALPINE,
			Biome.BEACH,
			Biome.GLACIER,
			Biome.GRASSLAND,
			Biome.LAKE,
			Biome.MANGROVE,
			Biome.OCEAN,
			Biome.SHRUBLAND,
			Biome.SNOW,
			Biome.SUB_TROPICAL_DESERT,
			Biome.TAIGA,
			Biome.TEMPERATE_DECIDUOUS_FOREST,
			Biome.TEMPERATE_DESERT,
			Biome.TEMPERATE_RAIN_FOREST,
			Biome.TROPICAL_RAIN_FOREST,
			Biome.TROPICAL_SEASONAL_FOREST,
			Biome.TUNDRA
		};
		
		biome1 = Biome.values();
		
	}

	
	@Test
	public void testBiome()
	{
		
		Assert.assertEquals( biome1.length , biome.length );
		for ( int i = 0; i < biome1.length ; i++ )
			Assert.assertEquals( biome1[i] , biome[i] );

	}

}
