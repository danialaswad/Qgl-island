package fr.unice.polytech.qgl.qdd.ai.sequences.aerialsequences;

import fr.unice.polytech.qgl.qdd.ai.sequences.SequencesTest;
import fr.unice.polytech.qgl.qdd.navigation.Compass;
import fr.unice.polytech.qgl.qdd.enums.Direction;
import fr.unice.polytech.qgl.qdd.navigation.Tile;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Set;

/**
 * Created by danialaswad on 04/02/2016.
 */
public class EchoForGroundSequenceTest extends SequencesTest
{
    private EchoForGroundSequence echoForGroundSequence;
    private Set<Tile> tiles;

    @Before
    public void setup(){
        newExplorer();
    }

    @Test
    public void echoForGroundSequenceFacingNorth(){
        Assert.assertEquals(getX(nav.map().currentTile()),1);
        Assert.assertEquals(getY(nav.map().currentTile()),1);

        echoForGroundSequence = new EchoForGroundSequence(nav,getExplorerAI().getCheckList());

        Assert.assertFalse(echoForGroundSequence.completed());

        Assert.assertEquals(echoForGroundSequence.execute().getAction(),"echo");
        Assert.assertEquals(echoForGroundSequence.execute().getStringParam("direction"),"E");

        tiles = nav.finder().allTiles(Direction.RIGHT);
        for (Tile t : tiles){
            t.setSea();
        }

        Assert.assertEquals(echoForGroundSequence.execute().getAction(),"echo");
        Assert.assertEquals(echoForGroundSequence.execute().getStringParam("direction"),"N");

       tiles = nav.finder().allTiles(Direction.FRONT);
        for (Tile t : tiles){
            t.setSea();
        }

        Assert.assertEquals(echoForGroundSequence.execute().getAction(),"fly");

        Assert.assertTrue(echoForGroundSequence.completed());
    }

    @Test
    public void echoForGroundSequenceFacingEast(){
        setFacingDirection(Compass.EAST);
        Assert.assertEquals(getX(nav.map().currentTile()),1);
        Assert.assertEquals(getY(nav.map().currentTile()),1);

        echoForGroundSequence = new EchoForGroundSequence(nav,getExplorerAI().getCheckList());

        Assert.assertFalse(echoForGroundSequence.completed());

        Assert.assertEquals(echoForGroundSequence.execute().getAction(),"echo");
        Assert.assertEquals(echoForGroundSequence.execute().getStringParam("direction"),"N");

        tiles = nav.finder().allTiles(Direction.LEFT);
        for (Tile t : tiles){
            t.setSea();
        }

        Assert.assertEquals(echoForGroundSequence.execute().getAction(),"echo");
        Assert.assertEquals(echoForGroundSequence.execute().getStringParam("direction"),"E");

        tiles = nav.finder().allTiles(Direction.FRONT);
        for (Tile t : tiles){
            t.setSea();
        }

        Assert.assertEquals(echoForGroundSequence.execute().getAction(),"fly");

        Assert.assertTrue(echoForGroundSequence.completed());
    }

    @Test
    public void echoForGroundSequenceFacingSouth(){
        setPosition(17,17);
        setFacingDirection(Compass.SOUTH);
        Assert.assertEquals(getX(nav.map().currentTile()),17);
        Assert.assertEquals(getY(nav.map().currentTile()),17);

        echoForGroundSequence = new EchoForGroundSequence(nav,getExplorerAI().getCheckList());

        Assert.assertFalse(echoForGroundSequence.completed());

        Assert.assertEquals(echoForGroundSequence.execute().getAction(),"echo");
        Assert.assertEquals(echoForGroundSequence.execute().getStringParam("direction"),"W");

        tiles = nav.finder().allTiles(Direction.RIGHT);
        for (Tile t : tiles){
            t.setSea();
        }

        Assert.assertEquals(echoForGroundSequence.execute().getAction(),"echo");
        Assert.assertEquals(echoForGroundSequence.execute().getStringParam("direction"),"S");

        tiles = nav.finder().allTiles(Direction.FRONT);
        for (Tile t : tiles){
            t.setSea();
        }

        Assert.assertEquals(echoForGroundSequence.execute().getAction(),"fly");

        Assert.assertTrue(echoForGroundSequence.completed());
    }

    @Test
    public void echoForGroundSequenceFacingWest(){
        setPosition(17,17);
        setFacingDirection(Compass.WEST);
        Assert.assertEquals(getX(nav.map().currentTile()),17);
        Assert.assertEquals(getY(nav.map().currentTile()),17);

        echoForGroundSequence = new EchoForGroundSequence(nav,getExplorerAI().getCheckList());

        Assert.assertFalse(echoForGroundSequence.completed());

        Assert.assertEquals(echoForGroundSequence.execute().getAction(),"echo");
        Assert.assertEquals(echoForGroundSequence.execute().getStringParam("direction"),"S");

        tiles = nav.finder().allTiles(Direction.LEFT);
        for (Tile t : tiles){
            t.setSea();
        }

        Assert.assertEquals(echoForGroundSequence.execute().getAction(),"echo");
        Assert.assertEquals(echoForGroundSequence.execute().getStringParam("direction"),"W");

        tiles = nav.finder().allTiles(Direction.FRONT);
        for (Tile t : tiles){
            t.setSea();
        }

        Assert.assertEquals(echoForGroundSequence.execute().getAction(),"fly");

        Assert.assertTrue(echoForGroundSequence.completed());
    }

    @Test
    public void echoForGroundSequenceAtBoundry(){
        setPosition(1,14);
        setFacingDirection(Compass.NORTH);
        Assert.assertEquals(getX(nav.map().currentTile()),1);
        Assert.assertEquals(getY(nav.map().currentTile()),14);

        echoForGroundSequence = new EchoForGroundSequence(nav,getExplorerAI().getCheckList());

        Assert.assertFalse(echoForGroundSequence.completed());

        Assert.assertEquals(echoForGroundSequence.execute().getAction(),"echo");
        Assert.assertEquals(echoForGroundSequence.execute().getStringParam("direction"),"E");

        tiles = nav.finder().allTiles(Direction.RIGHT);
        for (Tile t : tiles){
            t.setSea();
        }

        Assert.assertEquals(echoForGroundSequence.execute().getAction(),"echo");
        Assert.assertEquals(echoForGroundSequence.execute().getStringParam("direction"),"N");

        tiles = nav.finder().allTiles(Direction.FRONT);
        for (Tile t : tiles){
            t.setSea();
        }

        Assert.assertEquals(echoForGroundSequence.execute().getAction(),"heading");
        Assert.assertEquals(echoForGroundSequence.execute().getStringParam("direction"),"E");

        Assert.assertTrue(echoForGroundSequence.completed());

    }
}
