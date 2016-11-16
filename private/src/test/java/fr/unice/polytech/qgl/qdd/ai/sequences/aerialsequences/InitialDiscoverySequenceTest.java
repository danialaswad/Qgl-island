package fr.unice.polytech.qgl.qdd.ai.sequences.aerialsequences;

import fr.unice.polytech.qgl.qdd.ai.sequences.SequencesTest;
import fr.unice.polytech.qgl.qdd.navigation.Compass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by danialaswad on 04/02/2016.
 */
public class InitialDiscoverySequenceTest extends SequencesTest {
    private InitialDiscoverySequence initialDiscoverySequence;

    @Before
    public void setup(){
        newExplorer();
    }

    @Test
    public void initialDiscoverySequenceTestFacingNorth(){

        initialDiscoverySequence = new InitialDiscoverySequence(getExplorer().getNavigator(),getExplorerAI().getCheckList());

        Assert.assertEquals(initialDiscoverySequence.execute().getStringParam("direction"), "N");

        Assert.assertEquals(initialDiscoverySequence.execute().getStringParam("direction"), "E");

        Assert.assertEquals(initialDiscoverySequence.execute().getAction(),"fly");

        Assert.assertEquals(initialDiscoverySequence.completed(),true);
    }

    @Test
    public void initialDiscoverySequenceTestFacingEast(){
        setFacingDirection(Compass.EAST);
        initialDiscoverySequence = new InitialDiscoverySequence(getExplorer().getNavigator(),getExplorerAI().getCheckList());

        Assert.assertEquals(initialDiscoverySequence.execute().getStringParam("direction"), "E");

        Assert.assertEquals(initialDiscoverySequence.execute().getStringParam("direction"), "S");

        Assert.assertEquals(initialDiscoverySequence.execute().getAction(),"fly");

        Assert.assertEquals(initialDiscoverySequence.completed(),true);
    }
    @Test
    public void initialDiscoverySequenceTestFacingSouth(){
        setPosition(17,17);
        setFacingDirection(Compass.SOUTH);
        initialDiscoverySequence = new InitialDiscoverySequence(getExplorer().getNavigator(),getExplorerAI().getCheckList());

        Assert.assertEquals(initialDiscoverySequence.execute().getStringParam("direction"), "S");

        Assert.assertEquals(initialDiscoverySequence.execute().getStringParam("direction"), "W");

        Assert.assertEquals(initialDiscoverySequence.execute().getAction(),"fly");

        Assert.assertEquals(initialDiscoverySequence.completed(),true);
    }
    @Test
    public void initialDiscoverySequenceTestFacingWest(){
        setPosition(17,17);
        setFacingDirection(Compass.WEST);
        initialDiscoverySequence = new InitialDiscoverySequence(getExplorer().getNavigator(),getExplorerAI().getCheckList());

        Assert.assertEquals(initialDiscoverySequence.execute().getStringParam("direction"), "W");

        Assert.assertEquals(initialDiscoverySequence.execute().getStringParam("direction"), "N");

        Assert.assertEquals(initialDiscoverySequence.execute().getAction(),"fly");

        Assert.assertEquals(initialDiscoverySequence.completed(),true);
    }
}
