package fr.unice.polytech.qgl.qdd.ai.sequences.aerialsequences;

import fr.unice.polytech.qgl.qdd.ai.sequences.SequencesTest;
import fr.unice.polytech.qgl.qdd.navigation.Compass;
import fr.unice.polytech.qgl.qdd.navigation.Tile;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Set;

/**
 * Created by danialaswad on 04/02/2016.
 */
public class FlyToUnscannedGroundSequenceTest extends SequencesTest{

    private FlyToUnscannedGroundSequence flyToUnscannedGroundSequence;

    @Before
    public void setup(){
        newExplorer();
        setPosition(4,4);
        setFacingDirection(Compass.NORTH);
        nav.map().currentTile().setSea();
        Set<Tile> tiles = nav.finder().getSurroundingTiles(nav.map().currentTile());
        for(Tile t:tiles){
            t.setSea();
        }
        getTile(7,10).setGround();
    }
    @Test
    public void flyToUnscannedGroundSequenceTest(){
        flyToUnscannedGroundSequence = new FlyToUnscannedGroundSequence(nav,getExplorerAI().getCheckList());

        Assert.assertEquals(flyToUnscannedGroundSequence.execute().getAction(),"fly");

        setPosition(4,7);

        Assert.assertEquals(flyToUnscannedGroundSequence.execute().getAction(),"heading");

        setPosition(7,10);

        Assert.assertEquals(flyToUnscannedGroundSequence.completed(),true);
    }
}
