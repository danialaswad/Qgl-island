package fr.unice.polytech.qgl.qdd.ai.sequences.terrestrialsequences;

import fr.unice.polytech.qgl.qdd.ai.sequences.SequencesTest;
import fr.unice.polytech.qgl.qdd.enums.Biome;
import fr.unice.polytech.qgl.qdd.enums.Resource;
import fr.unice.polytech.qgl.qdd.navigation.Tile;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

/**
 * Created by danialaswad on 05/02/2016.
 */
public class MoveSequenceTest extends SequencesTest{

    private MoveSequence moveSequence;

    @Before
    public void setup(){
        newExplorer();
        setPosition(7,7);
    }

    @Test
    public void outOfBoundryTest(){
        Tile tile = getTile(7,1);
        moveSequence = new MoveSequence(nav,getExplorerAI().getCheckList(),tile);

        Assert.assertEquals(moveSequence.execute().getStringParam("direction"), "S");
        getExplorer().move("S");
        Assert.assertEquals(nav.front().toString(), "S");

        Assert.assertEquals(getX(nav.map().currentTile()),7);
        Assert.assertEquals(getY(nav.map().currentTile()),6);


        moveSequence = new MoveSequence(nav,getExplorerAI().getCheckList(),tile);
        Assert.assertEquals(moveSequence.execute().getStringParam("direction"), "N");
        getExplorer().move("N");
        Assert.assertEquals(nav.front().toString(), "N");

        Assert.assertEquals(getX(nav.map().currentTile()),7);
        Assert.assertEquals(getY(nav.map().currentTile()),7);
    }
}
