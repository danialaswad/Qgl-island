package fr.unice.polytech.qgl.qdd.navigation.finder;

import fr.unice.polytech.qgl.qdd.enums.Biome;
import fr.unice.polytech.qgl.qdd.enums.Direction;
import fr.unice.polytech.qgl.qdd.enums.Resource;
import fr.unice.polytech.qgl.qdd.navigation.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

/**
 * Created by danial on 25/12/2015.
 * */
public class FinderTileGetterTest extends FinderTester{
    @Before
    public void setup() {
        initializeMap(18, 18, 1, 1);
        setFacingDirection(Compass.NORTH);
    }

    @Test
    public void sanityCheck() {
        setPosition(4, 4);
        setFacingDirection(Compass.NORTH);

        Tile x4y4 = getTile(4, 4);

        //sanity check
        Assert.assertTrue(nav.map().currentTile() == x4y4);
    }

    @Test
    public void testGetRandomNearbyTile() {

        Tile currentTile = nav.map().currentTile();

        Assert.assertEquals(1, getX(currentTile));
        Assert.assertEquals(1, getY(currentTile));

        Set<Tile> randomNearbyTiles = new HashSet<>();
        for(int i = 0; i < 50; i++) {
            randomNearbyTiles.add(nav.finder().getRandomNearbyUnScannedTile());
        }

        for(Tile t: randomNearbyTiles) {
            Assert.assertTrue(getX(t) >= 0 && getX(t) <= 9);
            Assert.assertTrue(getY(t) >= 0 && getY(t) <= 9);
        }
    }
    
    @Test
    public void testAerialNeighbourInterestTiles(){
    	setPosition(1,1);
        Tile currentTile = nav.map().currentTile();

        Assert.assertEquals(1, getX(currentTile));
        Assert.assertEquals(1, getY(currentTile));
        
        Set<Tile> aerialNeighbour = nav.finder().aerialNeighbourInterestTiles(currentTile);
        Assert.assertEquals(aerialNeighbour.size(), 9);
        
        setPosition(5,5);
        currentTile = nav.map().currentTile();
        Set<Tile> aerialNeighbours = nav.finder().aerialNeighbourInterestTiles(currentTile);
        Assert.assertEquals(aerialNeighbours.size(), 16);
        
    }
    
    @Test
    public void testAerialNeighbouringTiles(){
        Tile currentTile = nav.map().currentTile();

        Assert.assertEquals(1, getX(currentTile));
        Assert.assertEquals(1, getY(currentTile));

        Set<Tile> aerialNeighbour = nav.finder().aerialNeighbouringTiles();

        for (Tile t : aerialNeighbour){
            Assert.assertTrue(getX(t) >= 0 && getX(t) <= 2);
            Assert.assertTrue(getY(t) >= 0 && getY(t) <= 2);
        }
    }

    @Test
    public void testGetSurroundingTiles(){

        Tile currentTile = nav.map().currentTile();

        Assert.assertEquals(1, getX(currentTile));
        Assert.assertEquals(1, getY(currentTile));

        Set<Tile> surroundingTile = nav.finder().getSurroundingTiles(currentTile);

        for (Tile t : surroundingTile){
            Assert.assertTrue(getX(t) >= 0 && getX(t) <= 2);
            Assert.assertTrue(getY(t) >= 0 && getX(t) <= 2);
        }
    }

    @Test
    public void testGetNearestUnscannedGroundTile(){
        setPosition(1,4);

        Assert.assertEquals(1, getX(nav.map().currentTile()));
        Assert.assertEquals(4, getY(nav.map().currentTile()));


        nav.map().updateMapThroughEcho(true,0,Compass.EAST);

        setPosition(1,7);

        Assert.assertEquals(1, getX(nav.map().currentTile()));
        Assert.assertEquals(7, getY(nav.map().currentTile()));

        nav.map().updateMapThroughEcho(true,3,Compass.EAST);

        Tile tile = nav.finder().getNearestUnscannedGroundTile();
        Assert.assertEquals(getX(tile), 3);
        Assert.assertEquals(getY(tile), 5);
    }
    
    @Test
    public void testGetNearestUnscannedGroundTileWithInterest(){
        setPosition(0,0);

        Assert.assertEquals(0, getX(nav.map().currentTile()));
        Assert.assertEquals(0, getY(nav.map().currentTile()));

        Tile tile = nav.finder().getNearestUnscannedGroundTileWithInterest(nav.map().currentTile());
        Assert.assertEquals(getX(tile), 6);
        Assert.assertEquals(getY(tile), 6);
        
        setPosition(16,16);
        tile = nav.finder().getNearestUnscannedGroundTileWithInterest(nav.map().currentTile());
        Assert.assertEquals(getX(tile), 11);
        Assert.assertEquals(getY(tile), 11);
        
        nav.map().updateMapThroughEcho(true,2,Compass.WEST);
        nav.map().updateMapThroughEcho(true,2,Compass.SOUTH);
        tile = nav.finder().getNearestUnscannedGroundTileWithInterest(nav.map().currentTile());
        Assert.assertEquals(getX(tile), 8);
        Assert.assertEquals(getY(tile), 8);
        
        
        //TO DO
    }

    @Test
    public void testAdjacentTileByAir(){


        setPosition(7,7);
        Tile currentTile = nav.map().currentTile();

        Assert.assertEquals(7, getX(currentTile));
        Assert.assertEquals(7, getY(currentTile));

        /*===============
            Facing North
         ================*/
        setFacingDirection(Compass.NORTH);

        Tile t = nav.finder().adjacentTileByAir(Direction.FRONT);

        Assert.assertEquals(getX(t),7);
        Assert.assertEquals(getY(t),10);


        Tile t2 = nav.finder().adjacentTileByAir(Direction.RIGHT);

        Assert.assertEquals(getX(t2), 10);
        Assert.assertEquals(getY(t2), 10);

        Tile t3 = nav.finder().adjacentTileByAir(Direction.LEFT);

        Assert.assertEquals(getX(t3), 4);
        Assert.assertEquals(getY(t3), 10);

        /*===============
            Facing East
         ================*/
        setFacingDirection(Compass.EAST);

        Tile t4 = nav.finder().adjacentTileByAir(Direction.FRONT);

        Assert.assertEquals(getX(t4), 10);
        Assert.assertEquals(getY(t4), 7);


        Tile t5 = nav.finder().adjacentTileByAir(Direction.RIGHT);

        Assert.assertEquals(getX(t5),10);
        Assert.assertEquals(getY(t5),4);

        Tile t6 = nav.finder().adjacentTileByAir(Direction.LEFT);

        Assert.assertEquals(getX(t6),10);
        Assert.assertEquals(getY(t6),10);

        /*===============
            Facing South
         ================*/
        setFacingDirection(Compass.SOUTH);

        Tile t7 = nav.finder().adjacentTileByAir(Direction.FRONT);

        Assert.assertEquals(getX(t7), 7);
        Assert.assertEquals(getY(t7), 4);


        Tile t8 = nav.finder().adjacentTileByAir(Direction.RIGHT);

        Assert.assertEquals(getX(t8),4);
        Assert.assertEquals(getY(t8),4);

        Tile t9 = nav.finder().adjacentTileByAir(Direction.LEFT);

        Assert.assertEquals(getX(t9),10);
        Assert.assertEquals(getY(t9),4);

         /*===============
            Facing West
         ================*/
        setFacingDirection(Compass.WEST);

        Tile t10 = nav.finder().adjacentTileByAir(Direction.FRONT);

        Assert.assertEquals(getX(t10), 4);
        Assert.assertEquals(getY(t10), 7);


        Tile t11 = nav.finder().adjacentTileByAir(Direction.RIGHT);

        Assert.assertEquals(getX(t11),4);
        Assert.assertEquals(getY(t11),10);

        Tile t12 = nav.finder().adjacentTileByAir(Direction.LEFT);

        Assert.assertEquals(getX(t12),4);
        Assert.assertEquals(getY(t12),4);
    }

    @Test
    public void testGetTile(){
        Assert.assertEquals(nav.finder().getTile(5,5), getTile(5,5));

        Assert.assertEquals(nav.finder().getTile(4,3), getTile(4,3));

        Assert.assertEquals(nav.finder().getTile(5,6), getTile(5,6));

        Assert.assertEquals(nav.finder().getTile(10,5), getTile(10,5));

        Assert.assertEquals(nav.finder().getTile(5,-5), getTile(5,-5));
    }

    @Test
    public void testChooseTurningDirection(){

        Tile dest = getTile(7, 7);

        setPosition(1,1);

        Assert.assertEquals(nav.chooseTurningDirection(dest), Direction.RIGHT);

        setPosition(14,7);

        Assert.assertEquals(nav.chooseTurningDirection(dest), Direction.LEFT);

        setPosition(17,17);

        setFacingDirection(Compass.WEST);

        Assert.assertEquals(nav.chooseTurningDirection(dest), Direction.LEFT);

        setPosition(17,1);

        Assert.assertEquals(nav.chooseTurningDirection(dest), Direction.RIGHT);
    }


    @Test
    public void getUnknownNeighbouringTileToPotentiallyExploitableTileTest(){

        setPosition(7,7);
        Tile tile;
        List<Biome> biomes = new ArrayList<>();
        biomes.add(Biome.OCEAN);
        nav.map().updateMapThroughScan(biomes,true);

        for(Tile t :nav.finder().neighbouringTiles()){
            t.setExplored();
        }
        nav.map().currentTile().setExplored();

        setPosition(6,7);

        tile = nav.finder().getUnknownTileNeighbouringToPotentiallyExploitableTile();

        Assert.assertEquals(5,getX(tile));
        Assert.assertEquals(7,getY(tile));

        setPosition(7,8);

        tile = nav.finder().getUnknownTileNeighbouringToPotentiallyExploitableTile();

        Assert.assertEquals(7,getX(tile));
        Assert.assertEquals(9,getY(tile));

        setPosition(8,7);

        tile = nav.finder().getUnknownTileNeighbouringToPotentiallyExploitableTile();

        Assert.assertEquals(9,getX(tile));
        Assert.assertEquals(7,getY(tile));

        setPosition(7,6);

        tile = nav.finder().getUnknownTileNeighbouringToPotentiallyExploitableTile();

        Assert.assertEquals(7,getX(tile));
        Assert.assertEquals(5,getY(tile));
    }
    /*
    to test:adjacentTile, getTilesOnSide
     */

    @Test
    public void getNearestExploitableTileTest(){
        setPosition(7,7);
        Map<Resource,String> resourceSetMap = new HashMap<>();
        resourceSetMap.put(Resource.WOOD,"1");
        nav.map().updateMapThroughScout(resourceSetMap,1,"N",true);

        nav.map().updateMapThroughScout(resourceSetMap,1,"W",false);

        Tile tile = nav.finder().getNearestExploitableTile();

        Assert.assertEquals(7,getX(tile));
        Assert.assertEquals(8,getY(tile));

        setPosition(8,7);

        nav.map().updateMapThroughScout(resourceSetMap,1,"N",true);
        tile = nav.finder().getNearestExploitableTile();
        Assert.assertEquals(8,getX(tile));
        Assert.assertEquals(8,getY(tile));

    }
}
