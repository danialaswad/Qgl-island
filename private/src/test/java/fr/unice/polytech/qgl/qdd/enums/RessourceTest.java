package fr.unice.polytech.qgl.qdd.enums;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by Ulysse on 16/1/2016.
 * */

public class RessourceTest
{

	private Biome[] biomeWOOD;
	private Biome[] biomeFISH;
	private Biome[] biomeFLOWER;
	private Biome[] biomeFUR;
	private Biome[] biomeQUARTZ;
	private Biome[] biomeFRUITS;
	private Biome[] biomeSUGAR_CANE;
	private Biome[] biomeORE;
	private Biome[] biomeGLASS;
	private Biome[] biomeINGLOT;
	private Biome[] biomeLEATHER;
	private Biome[] biomePLANK;
	private Biome[] biomeRUM;

	@Before
	public void Setup() 
	{
		
		biomeWOOD = new Biome[]
		{
			Biome.MANGROVE,
			Biome.TEMPERATE_RAIN_FOREST,
			Biome.TEMPERATE_DECIDUOUS_FOREST,
			Biome.TROPICAL_RAIN_FOREST,			
			Biome.TROPICAL_SEASONAL_FOREST
		};

		biomeFISH = new Biome[]
		{
			Biome.OCEAN,
			Biome.LAKE
		};

		biomeFLOWER = new Biome[]
		{
			Biome.MANGROVE,
			Biome.ALPINE,
			Biome.GLACIER
		};
		
		biomeFUR = new Biome[]
		{
			Biome.GRASSLAND,
			Biome.TEMPERATE_RAIN_FOREST,
			Biome.TUNDRA, 
			Biome.SHRUBLAND
		};
		
		biomeQUARTZ = new Biome[]
		{
			Biome.BEACH,
			Biome.TEMPERATE_DESERT
		};

		biomeFRUITS = new Biome[]
		{
			Biome.TROPICAL_RAIN_FOREST,
			Biome.TROPICAL_SEASONAL_FOREST
		};
		
		biomeSUGAR_CANE = new Biome[]
		{
			Biome.TROPICAL_RAIN_FOREST,
			Biome.TROPICAL_SEASONAL_FOREST
		};

		biomeORE = new Biome[]
		{
			Biome.TEMPERATE_DESERT,
			Biome.ALPINE,
			Biome.GLACIER,
			Biome.SNOW,
			Biome.TAIGA,
			Biome.TUNDRA
		};

		biomeGLASS = new Biome[]
		{
			
		};

		biomeINGLOT = new Biome[]
		{
			
		};

		biomeLEATHER = new Biome[]
		{
			
		};

		biomePLANK = new Biome[]
		{
			
		};
		
		biomeRUM = new Biome[]
		{
			
		};

	}

	
	@Test
	public void testRessource()
	{

		Assert.assertEquals( Resource.WOOD.getBiomes().size() , biomeWOOD.length );
		for ( int i = 0; i < Resource.WOOD.getBiomes().size() ; i++ )
			Assert.assertEquals( true , Resource.WOOD.getBiomes().contains( biomeWOOD[i] ) );
		Assert.assertEquals( false , Resource.WOOD.getIsManufactured() );
		

		Assert.assertEquals( Resource.FISH.getBiomes().size() , biomeFISH.length );
		for ( int i = 0; i < Resource.FISH.getBiomes().size() ; i++ )
			Assert.assertEquals( true , Resource.FISH.getBiomes().contains( biomeFISH[i] ) );
		Assert.assertEquals( false , Resource.FISH.getIsManufactured() );

		Assert.assertEquals( Resource.FLOWER.getBiomes().size() , biomeFLOWER.length );
		for ( int i = 0; i < Resource.FLOWER.getBiomes().size() ; i++ )
			Assert.assertEquals( true , Resource.FLOWER.getBiomes().contains( biomeFLOWER[i] ) );
		Assert.assertEquals( false , Resource.FLOWER.getIsManufactured() );

		Assert.assertEquals( Resource.FUR.getBiomes().size() , biomeFUR.length );
		for ( int i = 0; i < Resource.FUR.getBiomes().size() ; i++ )
			Assert.assertEquals( true , Resource.FUR.getBiomes().contains( biomeFUR[i] ) );
		Assert.assertEquals( false , Resource.FUR.getIsManufactured() );

		Assert.assertEquals( Resource.QUARTZ.getBiomes().size() , biomeQUARTZ.length );
		for ( int i = 0; i < Resource.QUARTZ.getBiomes().size() ; i++ )
			Assert.assertEquals( true , Resource.QUARTZ.getBiomes().contains( biomeQUARTZ[i] ) );
		Assert.assertEquals( false , Resource.QUARTZ.getIsManufactured() );

		Assert.assertEquals( Resource.FRUITS.getBiomes().size() , biomeFRUITS.length );
		for ( int i = 0; i < Resource.FRUITS.getBiomes().size() ; i++ )
			Assert.assertEquals( true , Resource.FRUITS.getBiomes().contains( biomeFRUITS[i] ) );
		Assert.assertEquals( false , Resource.FRUITS.getIsManufactured() );

		Assert.assertEquals( Resource.SUGAR_CANE.getBiomes().size() , biomeSUGAR_CANE.length );
		for ( int i = 0; i < Resource.SUGAR_CANE.getBiomes().size() ; i++ )
			Assert.assertEquals( true , Resource.SUGAR_CANE.getBiomes().contains( biomeSUGAR_CANE[i] ) );
		Assert.assertEquals( false , Resource.SUGAR_CANE.getIsManufactured() );

		Assert.assertEquals( Resource.ORE.getBiomes().size() , biomeORE.length );
		for ( int i = 0; i < Resource.ORE.getBiomes().size() ; i++ )
			Assert.assertEquals( true , Resource.ORE.getBiomes().contains( biomeORE[i] ) );
		Assert.assertEquals( false , Resource.ORE.getIsManufactured() );

		Assert.assertEquals( Resource.GLASS.getBiomes().size() , biomeGLASS.length );
		for ( int i = 0; i < Resource.GLASS.getBiomes().size() ; i++ )
			Assert.assertEquals( true , Resource.GLASS.getBiomes().contains( biomeGLASS[i] ) );
		Assert.assertEquals( true , Resource.GLASS.getIsManufactured() );

		Assert.assertEquals( Resource.INGOT.getBiomes().size() , biomeINGLOT.length );
		for ( int i = 0; i < Resource.INGOT.getBiomes().size() ; i++ )
			Assert.assertEquals( true , Resource.INGOT.getBiomes().contains( biomeINGLOT[i] ) );
		Assert.assertEquals( true , Resource.INGOT.getIsManufactured() );

		Assert.assertEquals( Resource.LEATHER.getBiomes().size() , biomeLEATHER.length );
		for ( int i = 0; i < Resource.LEATHER.getBiomes().size() ; i++ )
			Assert.assertEquals( true , Resource.LEATHER.getBiomes().contains( biomeLEATHER[i] ) );
		Assert.assertEquals( true , Resource.LEATHER.getIsManufactured() );

		Assert.assertEquals( Resource.PLANK.getBiomes().size() , biomePLANK.length );
		for ( int i = 0; i < Resource.PLANK.getBiomes().size() ; i++ )
			Assert.assertEquals( true , Resource.PLANK.getBiomes().contains( biomePLANK[i] ) );
		Assert.assertEquals( true , Resource.PLANK.getIsManufactured() );

		Assert.assertEquals( Resource.RUM.getBiomes().size() , biomeRUM.length );
		for ( int i = 0; i < Resource.RUM.getBiomes().size() ; i++ )
			Assert.assertEquals( true , Resource.RUM.getBiomes().contains( biomeRUM[i] ) );
		Assert.assertEquals( true , Resource.RUM.getIsManufactured() );

	}

}
