package fr.unice.polytech.qgl.qdd.ai.sequences.aerialsequences;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import fr.unice.polytech.qgl.qdd.ai.sequences.SequencesTest;
import fr.unice.polytech.qgl.qdd.enums.Biome;

public class ScanForBiomeSequenceTest extends SequencesTest{
	private ScanForBiomeSequence scanSequence;
    private List<Biome> biomes;

    @Before
    public void setup(){
        newExplorer();
        setPosition(9,9);
        biomes = new ArrayList<>();
    }

    @Test
    public void scanSequenceBasicTest(){

        scanSequence = new ScanForBiomeSequence(nav,getExplorerAI().getCheckList());

        Assert.assertFalse(scanSequence.completed());
        //Checking if we are on a tile to scan
        //Assert.assertEquals(nav.finder().getNumberOfUnknownTilesNearBy(nav.map().currentTile()), 25);
        //Checking if we scan this tile
        Assert.assertEquals(scanSequence.execute().getAction(),"scan");
        //Checking if the next action is a FlyToDestinationSequence action
        Assert.assertEquals(scanSequence.execute().getAction(),new FlyToDestinationSequence(nav,getExplorerAI().getCheckList(),nav.finder().getNearestUnscannedGroundTileWithInterest(nav.map().currentTile())).execute().getAction());
	}
}
    