package fr.unice.polytech.qgl.qdd.ai;

import fr.unice.polytech.qgl.qdd.Action;
import fr.unice.polytech.qgl.qdd.QddExplorer;
import fr.unice.polytech.qgl.qdd.enums.Biome;
import fr.unice.polytech.qgl.qdd.enums.Resource;
import fr.unice.polytech.qgl.qdd.navigation.Compass;
import fr.unice.polytech.qgl.qdd.navigation.Navigator;
import fr.unice.polytech.qgl.qdd.navigation.Tile;
import fr.unice.polytech.qgl.qdd.navigation.finder.FinderTester;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Field;
import java.util.*;

/**
 * Created by danial on 17/01/16.
 */
public class CheckListTest extends FinderTester {
    private QddExplorer explorer;
    private ExplorerAI explorerAI;

    private Field navField;

    @Before
    public void setup() {


        String init = new String("{\"men\": 12,\"budget\": 20000,\"contracts\": [{ \"amount\": 600, \"resource\": \"WOOD\" },{ \"amount\": 600, \"resource\": \"FUR\" },{ \"amount\": 200, \"resource\": \"RUM\" }],\"heading\": \"N\"}");

        try {
            navField = QddExplorer.class.getDeclaredField("nav");
            navField.setAccessible(true);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        explorer = new QddExplorer(init);
        newExplorer(init);
    }

    public void newExplorer(String init){
        explorer = new QddExplorer(init);
        initializeMap(18,18,1,1);
        setNavExplorer(getNav());
        explorerAI = new ExplorerAI(explorer);
    }

    private void setNavExplorer(Navigator n) {
        try {
            navField.set(explorer, n);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testIsaboveGround(){
        setPosition(7,7);
        Assert.assertTrue(!explorerAI.getCheckList().isAboveGround());

        nav.map().currentTile().setGround();

        Assert.assertTrue(explorerAI.getCheckList().isAboveGround());
    }

    @Test
    public void testIsCloseToBoundry(){
        setPosition(11,11);

        Assert.assertTrue(!explorerAI.getCheckList().isCloseToAerialBoundary());


        setPosition(17,17);

        Assert.assertTrue(explorerAI.getCheckList().isCloseToAerialBoundary());

        setPosition(17,1);
        setFacingDirection(Compass.EAST);

        Assert.assertTrue(explorerAI.getCheckList().isCloseToAerialBoundary());


        setFacingDirection(Compass.WEST);
        Assert.assertTrue(!explorerAI.getCheckList().isCloseToAerialBoundary());

    }

    @Test
    public void  testFoundCreek(){
        Assert.assertTrue(!explorerAI.getCheckList().foundCreek());
        List<String> creeks = new ArrayList<>();
        creeks.add("creek12");
        explorer.getMap().updateMapWithCreeks(creeks);
        //Assert.assertTrue(explorerAI.getCheckList().foundCreek());
    }

    @Test
    public void testHaveEnoughtBudgetToSearchForMoreCreeks(){
        Assert.assertTrue(explorerAI.getCheckList().haveEnoughtBudgetToScanTheIsland());
        explorer.decreaseBudget(500);
        Assert.assertTrue(explorerAI.getCheckList().haveEnoughtBudgetToScanTheIsland());
        explorer.decreaseBudget(500);
        Assert.assertTrue(explorerAI.getCheckList().haveEnoughtBudgetToScanTheIsland());
        explorer.decreaseBudget(500);
        Assert.assertTrue(explorerAI.getCheckList().haveEnoughtBudgetToScanTheIsland());
        explorer.decreaseBudget(1500);
        Assert.assertTrue(!explorerAI.getCheckList().haveEnoughtBudgetToScanTheIsland());

    }

    @Test
    public void testIsEchoCoverageSufficient(){
        Assert.assertTrue(!explorerAI.getCheckList().isEchoCoverageSufficient());
        explorer.getNavigator().map().updateMapThroughEcho(false,6,Compass.NORTH);
        Assert.assertTrue(!explorerAI.getCheckList().isEchoCoverageSufficient());
        explorer.getNavigator().map().updateMapThroughEcho(true,0,Compass.EAST);
        Assert.assertTrue(!explorerAI.getCheckList().isEchoCoverageSufficient());
        setPosition(1,4);
        explorer.getNavigator().map().updateMapThroughEcho(true,2,Compass.EAST);
        Assert.assertTrue(!explorerAI.getCheckList().isEchoCoverageSufficient());
        setPosition(1,7);
        explorer.getNavigator().map().updateMapThroughEcho(true,2,Compass.EAST);
        Assert.assertTrue(!explorerAI.getCheckList().isEchoCoverageSufficient());
        setPosition(1,10);
    }

    @Test
    public void testCheckAllTilesDiscovered(){
        Set<Tile> tiles = new HashSet<>();

        for (int i = 0; i < nav.map().width(); i++ ){
            tiles.add(getTile(i,1));
        }
        Assert.assertTrue(!explorerAI.getCheckList().isTilesAtRightDiscovered());

        explorer.getNavigator().map().updateMapThroughEcho(true,3,Compass.EAST);
        tiles.clear();
        for (int i = 0; i < nav.map().width(); i++ ){
            tiles.add(getTile(i,1));
        }
        Assert.assertTrue(explorerAI.getCheckList().isTilesAtRightDiscovered());

        explorer.getNavigator().map().updateMapThroughEcho(true,3,Compass.NORTH);
        setFacingDirection(Compass.EAST);
        tiles.clear();
        for (int i = 0; i < nav.map().height(); i++ ){
            tiles.add(getTile(1,i));
        }
        Assert.assertTrue(explorerAI.getCheckList().isTilesAtLeftDiscovered());

    }

    @Test
    public void testExploitableResourceFound(){
        setPosition(7,7);
        Assert.assertTrue(!explorerAI.getCheckList().exploitableResourceFound());

        Map<Resource,String> resourceStringMap = new HashMap<>();
        resourceStringMap.put(Resource.valueOf("WOOD"), "HIGH");
        explorer.getNavigator().map().currentTile().addResources(resourceStringMap);
        Assert.assertTrue(explorerAI.getCheckList().exploitableResourceFound());

        setPosition(10,7);
        Map<Resource,String> resourceStringMap2 = new HashMap<>();
        resourceStringMap2.put(Resource.valueOf("FLOWER"), "HIGH");
        resourceStringMap2.put(Resource.valueOf("INGOT"), "HIGH");
        explorer.getNavigator().map().currentTile().addResources(resourceStringMap2);
        Assert.assertTrue(!explorerAI.getCheckList().exploitableResourceFound());

        setPosition(13,7);
        Map<Resource,String> resourceStringMap3 = new HashMap<>();
        resourceStringMap3.put(Resource.valueOf("FLOWER"), "HIGH");
        resourceStringMap3.put(Resource.valueOf("INGOT"), "HIGH");
        resourceStringMap3.put(Resource.valueOf("WOOD"), "HIGH");
        explorer.getNavigator().map().currentTile().addResources(resourceStringMap3);
        Assert.assertTrue(explorerAI.getCheckList().exploitableResourceFound());

        setPosition(3,3);
        Map<Resource,String> resourceStringMap4 = new HashMap<>();
        resourceStringMap4.put(Resource.valueOf("FRUITS"), "HIGH");
        explorer.getNavigator().map().currentTile().addResources(resourceStringMap4);
        Assert.assertTrue(explorerAI.getCheckList().exploitableResourceFound());

        setPosition(6,7);
        explorer.exploit(Resource.FRUITS,1010);
        explorer.getNavigator().map().currentTile().addResources(resourceStringMap4);
        Assert.assertFalse(explorerAI.getCheckList().exploitableResourceFound());

        setPosition(5,5);
        explorer.exploit(Resource.SUGAR_CANE,2020);
        Action action = new Action(Action.TRANSFORM);
        action.addParameter(Resource.SUGAR_CANE.toString(),2000).addParameter(Resource.FRUITS.toString(),200);
        explorer.transform(Resource.RUM, 201, action);
        explorer.getNavigator().map().currentTile().addResources(resourceStringMap4);
        Assert.assertFalse(explorerAI.getCheckList().exploitableResourceFound());
    }
    
    @Test 
    public void enoughPotentialTilesToGoTest(){
    	Assert.assertEquals(false, explorerAI.getCheckList().enoughPotentialTilesToLand());
        List<Biome> biomes = new ArrayList<>();
        biomes.add(Biome.TROPICAL_RAIN_FOREST);
        List<Biome> biomes2 = new ArrayList<>();
        biomes2.add(Biome.GRASSLAND);

    	setPosition(2,2);
        explorer.getMap().updateMapThroughScan(biomes,true);
        Assert.assertEquals(false, explorerAI.getCheckList().enoughPotentialTilesToLand());
        setPosition(2,5);
        explorer.getMap().updateMapThroughScan(biomes,true);
    	Assert.assertEquals(false, explorerAI.getCheckList().enoughPotentialTilesToLand());
        setPosition(2,8);
        explorer.getMap().updateMapThroughScan(biomes,true);
        Assert.assertEquals(false, explorerAI.getCheckList().enoughPotentialTilesToLand());
        setPosition(2,11);
        explorer.getMap().updateMapThroughScan(biomes,true);
        Assert.assertEquals(false, explorerAI.getCheckList().enoughPotentialTilesToLand());
        setPosition(2,14);
        explorer.getMap().updateMapThroughScan(biomes,true);
        Assert.assertEquals(false, explorerAI.getCheckList().enoughPotentialTilesToLand());
        setPosition(2,17);
        explorer.getMap().updateMapThroughScan(biomes,true);
        Assert.assertEquals(false, explorerAI.getCheckList().enoughPotentialTilesToLand());
        setPosition(4,2);
        explorer.getMap().updateMapThroughScan(biomes,true);
        Assert.assertEquals(false, explorerAI.getCheckList().enoughPotentialTilesToLand());
        setPosition(4,5);
        explorer.getMap().updateMapThroughScan(biomes,true);
        Assert.assertEquals(false, explorerAI.getCheckList().enoughPotentialTilesToLand());
        setPosition(4,8);
        explorer.getMap().updateMapThroughScan(biomes,true);
        Assert.assertEquals(false, explorerAI.getCheckList().enoughPotentialTilesToLand());
        setPosition(4,11);
        explorer.getMap().updateMapThroughScan(biomes,true);
        Assert.assertEquals(false, explorerAI.getCheckList().enoughPotentialTilesToLand());
        setPosition(4,14);
        explorer.getMap().updateMapThroughScan(biomes,true);
        Assert.assertEquals(false, explorerAI.getCheckList().enoughPotentialTilesToLand());
        setPosition(4,17);
        explorer.getMap().updateMapThroughScan(biomes,true);
        Assert.assertEquals(false, explorerAI.getCheckList().enoughPotentialTilesToLand());


        setPosition(6,2);
        explorer.getMap().updateMapThroughScan(biomes2,true);
        Assert.assertEquals(false, explorerAI.getCheckList().enoughPotentialTilesToLand());
        setPosition(6,5);
        explorer.getMap().updateMapThroughScan(biomes2,true);
        Assert.assertEquals(false, explorerAI.getCheckList().enoughPotentialTilesToLand());
        setPosition(6,8);
        explorer.getMap().updateMapThroughScan(biomes2,true);
        Assert.assertEquals(false, explorerAI.getCheckList().enoughPotentialTilesToLand());
        setPosition(6,11);
        explorer.getMap().updateMapThroughScan(biomes2,true);
        Assert.assertEquals(false, explorerAI.getCheckList().enoughPotentialTilesToLand());
        setPosition(6,14);
        explorer.getMap().updateMapThroughScan(biomes2,true);
        Assert.assertEquals(false, explorerAI.getCheckList().enoughPotentialTilesToLand());
        setPosition(6,17);
        explorer.getMap().updateMapThroughScan(biomes2,true);
        Assert.assertEquals(false, explorerAI.getCheckList().enoughPotentialTilesToLand());
        setPosition(8,2);
        explorer.getMap().updateMapThroughScan(biomes2,true);
        Assert.assertEquals(false, explorerAI.getCheckList().enoughPotentialTilesToLand());
        setPosition(8,5);
        explorer.getMap().updateMapThroughScan(biomes2,true);
        Assert.assertEquals(false, explorerAI.getCheckList().enoughPotentialTilesToLand());
        setPosition(8,8);
        explorer.getMap().updateMapThroughScan(biomes2,true);
        Assert.assertEquals(false, explorerAI.getCheckList().enoughPotentialTilesToLand());
        setPosition(8,11);
        explorer.getMap().updateMapThroughScan(biomes2,true);
        Assert.assertEquals(false, explorerAI.getCheckList().enoughPotentialTilesToLand());
        setPosition(8,14);
        explorer.getMap().updateMapThroughScan(biomes2,true);
        Assert.assertEquals(false, explorerAI.getCheckList().enoughPotentialTilesToLand());
        setPosition(8,17);
        explorer.getMap().updateMapThroughScan(biomes2,true);
        Assert.assertEquals(true, explorerAI.getCheckList().enoughPotentialTilesToLand());
    }

    @Test
    public void contractCompletedTest(){
        Assert.assertEquals(false, explorerAI.getCheckList().contractCompleted());

        explorer.exploit(Resource.RUM,219);
        explorer.exploit(Resource.WOOD,599);
        explorer.exploit(Resource.FUR,599);

        Assert.assertEquals(false, explorerAI.getCheckList().contractCompleted());

        explorer.exploit(Resource.RUM,1);

        Assert.assertEquals(false, explorerAI.getCheckList().contractCompleted());

        explorer.exploit(Resource.WOOD,1);

        Assert.assertEquals(false, explorerAI.getCheckList().contractCompleted());

        explorer.exploit(Resource.FUR,1);

        Assert.assertEquals(true, explorerAI.getCheckList().contractCompleted());

    }

    @Test
    public void enoughResourcesToManufactureTest(){
        Assert.assertEquals(false,explorerAI.getCheckList().enoughResourcesToManufacture());
        explorer.exploit(Resource.SUGAR_CANE,2199);
        explorer.exploit(Resource.FRUITS,219);
        Assert.assertEquals(true,explorerAI.getCheckList().enoughResourcesToManufacture());
        explorer.exploit(Resource.SUGAR_CANE,1);
        Assert.assertEquals(true,explorerAI.getCheckList().enoughResourcesToManufacture());
        explorer.exploit(Resource.FRUITS,1);
        Assert.assertEquals(true,explorerAI.getCheckList().enoughResourcesToManufacture());
    }

    @Test
    public void nbResourceToManufactureTest(){

        Assert.assertEquals(200,explorerAI.getCheckList().nbResourceToManufacture(Resource.RUM));
        explorer.exploit(Resource.SUGAR_CANE,2000);
        explorer.exploit(Resource.FRUITS,200);
        Action action = new Action(Action.TRANSFORM);
        action.addParameter(Resource.SUGAR_CANE.toString(),2000).addParameter(Resource.FRUITS.toString(),1000);
        explorer.transform(Resource.RUM,167,action);
        Assert.assertEquals(33,explorerAI.getCheckList().nbResourceToManufacture(Resource.RUM));
        explorer.exploit(Resource.SUGAR_CANE,1000);
        explorer.exploit(Resource.FRUITS,500);
        Action action2 = new Action(Action.TRANSFORM);
        action2.addParameter(Resource.SUGAR_CANE.toString(),600).addParameter(Resource.FRUITS.toString(),60);
        explorer.transform(Resource.RUM,60,action2);
        Assert.assertEquals(0,explorerAI.getCheckList().nbResourceToManufacture(Resource.RUM));

    }


}
