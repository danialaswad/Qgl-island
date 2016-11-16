package fr.unice.polytech.qgl.qdd.navigation.tile;

import fr.unice.polytech.qgl.qdd.enums.Biome;
import fr.unice.polytech.qgl.qdd.enums.Resource;
import fr.unice.polytech.qgl.qdd.navigation.Tile;
import fr.unice.polytech.qgl.qdd.navigation.TileListener;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

/**
 * Created by danial on 17/01/16.
 */
public class TileTest{

    private Tile tile;
    @Before
    public void setup(){
        TileListener t = new TileListener() {
            @Override
            public void typeDiscovered(Tile tile, String previousType, String currentType) {

            }

            @Override
            public void biomeDiscovered(Tile tile) {

            }

            @Override
            public void creekDiscovered(Tile tile) {

            }

            @Override
            public void tileExploited(Tile tile) {

            }
        };
        tile = new Tile(t);
    }

    @Test
    public void isUnknownTest() {
        Assert.assertTrue(tile.isUnknown());
    }

    @Test
    public void setGroundTest() {
        tile.setGround();
        Assert.assertTrue(tile.isGround());
    }

    @Test
    public void isSeaTest() {
        tile.setSea();
        Assert.assertTrue(tile.isSea());
    }

    @Test
    public void isUnscannedTest() {
        tile.setSea();
        Assert.assertTrue(tile.isUnscanned());
        List<Biome> biomes = new ArrayList<>();
        biomes.add(Biome.BEACH);
        tile.addBiomes(biomes);
        Assert.assertFalse(tile.isUnscanned());
    }

    @Test
    public void hasResourcesTest() {
        tile.setSea();
        Assert.assertFalse(tile.hasResources());
        List<Biome> biomes = new ArrayList<>();
        biomes.add(Biome.BEACH);
        tile.addBiomes(biomes);
        Map<Resource,String> resources = new HashMap<>();
        resources.put(Resource.FISH,"High");
        tile.addResources(resources);
        Assert.assertTrue(tile.hasResources());
    }

    @Test
    public void hasResourceTest() {
        tile.setSea();
        Assert.assertFalse(tile.hasResource(Resource.FISH));
        List<Biome> biomes = new ArrayList<>();
        biomes.add(Biome.BEACH);
        tile.addBiomes(biomes);
        Map<Resource,String> resources = new HashMap<>();
        resources.put(Resource.FISH,"High");
        tile.addResources(resources);
        Assert.assertTrue(tile.hasResource(Resource.FISH));
        Assert.assertFalse(tile.hasResource(Resource.WOOD));
    }

    @Test
    public void getTypeTest(){
        Assert.assertNotNull(tile.getType());
        Assert.assertEquals(tile.getType(), "UNKNOWN");

        tile.setGround();
        Assert.assertEquals(tile.getType(), "GROUND");

        tile.setSea();
        Assert.assertEquals(tile.getType(), "SEA");
    }

    @Test
    public void getCreeksTest(){
        Assert.assertTrue(tile.getCreeks().isEmpty());

        List<String> creeks = new ArrayList<>();
        creeks.add("danialaswad");
        tile.addCreeks(creeks);

        Assert.assertFalse(tile.getCreeks().isEmpty());

        Assert.assertEquals(tile.getCreeks().get(0),"danialaswad");
    }

    @Test
    public void removeResourceTest(){
        List<Biome> biomes = new ArrayList<>();
        biomes.add(Biome.BEACH);

        Map<Resource,String> resources = new HashMap<>();
        resources.put(Resource.FISH,"High");

        tile.addBiomes(biomes);
        tile.addResources(resources);

        Assert.assertEquals(resources,tile.getResources());

        Map<Resource,String> resources2 = new HashMap<>();
        resources2.put(Resource.FLOWER,"High");

        Assert.assertNotEquals(resources2,tile.getResources());

        tile.removeResource(Resource.FISH);
        Assert.assertTrue(tile.getResources().isEmpty());

    }

    @Test
    public void addBiomesTest(){
        List<Biome> biomes = new ArrayList<>();
        biomes.add(Biome.OCEAN);

        Map<Resource,String> resources = new HashMap<>();
        resources.put(Resource.FISH,"High");

        Assert.assertEquals(tile.getType(), "UNKNOWN");

        tile.addBiomes(biomes);

        Assert.assertEquals(tile.getBiomes().get(0),Biome.OCEAN);

        Assert.assertEquals(tile.getType(), "SEA");
    }

    @Test
    public void hasUniqueBiomeTest(){
        Assert.assertFalse(tile.hasUniqueBiome(Biome.ALPINE));

        List<Biome> biomes = new ArrayList<>();

        biomes.add(Biome.BEACH);
        tile.addBiomes(biomes);

        Assert.assertTrue(tile.hasUniqueBiome(Biome.BEACH));

        biomes.add(Biome.OCEAN);
        tile.addBiomes(biomes);

        Assert.assertEquals(tile.getType(), "GROUND");
        Assert.assertFalse(tile.hasUniqueBiome(Biome.OCEAN));
        Assert.assertFalse(tile.hasUniqueBiome(Biome.MANGROVE));
    }

    @Test
    public void hasOrPotentiallyHasResourcesOfType(){
        List<Biome> biomes = new ArrayList<>();

        biomes.add(Biome.MANGROVE);
        tile.addBiomes(biomes);

        Set<Resource> resources = new HashSet<>();

        resources.add(Resource.FUR);
        resources.add(Resource.WOOD);

        Assert.assertTrue(tile.hasOrPotentiallyHasResourcesOfType(resources));

        Set<Resource> resources2 = new HashSet<>();

        resources2.add(Resource.FISH);

        Assert.assertFalse(tile.hasOrPotentiallyHasResourcesOfType(resources2));
    }

    @Test
    public void hasResourceOfType(){
        EnumMap<Resource, String> resources = new EnumMap<>( Resource.class );

        resources.put(Resource.WOOD,"HIGH");

        tile.addResources(resources);

        Set<Resource> resourceSet = new HashSet<>();

        resourceSet.add(Resource.FLOWER);

        Assert.assertFalse(tile.hasResourceOfType(resourceSet));

        resourceSet.add(Resource.WOOD);

        Assert.assertTrue(tile.hasResourceOfType(resourceSet));
    }
}
