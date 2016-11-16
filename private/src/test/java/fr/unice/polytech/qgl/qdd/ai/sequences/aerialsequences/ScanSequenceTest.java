package fr.unice.polytech.qgl.qdd.ai.sequences.aerialsequences;

import fr.unice.polytech.qgl.qdd.ai.sequences.SequencesTest;
import fr.unice.polytech.qgl.qdd.enums.Biome;
import fr.unice.polytech.qgl.qdd.navigation.Compass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by danialaswad on 04/02/2016.
 */
public class ScanSequenceTest extends SequencesTest {

    private ScanSequence scanSequence;
    private List<Biome> biomes;

    @Before
    public void setup(){
        newExplorer();
        setPosition(4,4);
        nav.map().currentTile().setGround();
        biomes = new ArrayList<>();
    }

    @Test
    public void scanSequenceBasicTest(){

        scanSequence = new ScanSequence(nav,getExplorerAI().getCheckList());


        Assert.assertFalse(scanSequence.completed());

        Assert.assertEquals(scanSequence.execute().getAction(),"scan");

        biomes.add(Biome.MANGROVE);

        nav.map().currentTile().addBiomes(biomes);
        getTile(4,7).setGround();

        Assert.assertEquals(scanSequence.execute().getAction(),"fly");

        setPosition(4,7);

        Assert.assertEquals(scanSequence.execute().getAction(),"scan");
    }

    /*@Test
    public void scanSequenceFurtherTest(){

        scanSequence = new ScanSequence(nav,getExplorerAI().getCheckList());

        Assert.assertEquals(scanSequence.execute().getAction(),"scan");

        biomes.add(Biome.MANGROVE);

        nav.map().currentTile().addBiomes(biomes);
        getTile(10,10).setGround();

        Assert.assertEquals(scanSequence.execute().getAction(),"fly");

        setPosition(4,7);

        Assert.assertEquals(scanSequence.execute().getAction(),"scan");
       // Assert.assertEquals(scanSequence.execute().getStringParam("direction"), "E");

        setPosition(7,10);
        setFacingDirection(Compass.EAST);

        Assert.assertEquals(scanSequence.execute().getAction(),"fly");

        setPosition(10,10);

        Assert.assertEquals(scanSequence.execute().getAction(),"scan");

        biomes.add(Biome.MANGROVE);

        nav.map().currentTile().addBiomes(biomes);
        getTile(13,13).setGround();

        Assert.assertEquals(scanSequence.execute().getAction(),"heading");
        Assert.assertEquals(scanSequence.execute().getStringParam("direction"), "N");

        setPosition(13,13);

        Assert.assertEquals(scanSequence.execute().getAction(),"scan");

        biomes.add(Biome.MANGROVE);

        nav.map().currentTile().addBiomes(biomes);
        getTile(13,13).setGround();
        Assert.assertTrue(scanSequence.completed());
    }*/
}
