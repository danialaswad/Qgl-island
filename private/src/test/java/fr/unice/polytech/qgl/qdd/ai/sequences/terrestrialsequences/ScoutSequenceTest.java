package fr.unice.polytech.qgl.qdd.ai.sequences.terrestrialsequences;

import fr.unice.polytech.qgl.qdd.ai.sequences.SequencesTest;
import fr.unice.polytech.qgl.qdd.enums.Biome;
import fr.unice.polytech.qgl.qdd.enums.Resource;
import fr.unice.polytech.qgl.qdd.navigation.Compass;
import fr.unice.polytech.qgl.qdd.navigation.Tile;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

/**
 * Created by danialaswad on 04/02/2016.
 */
public class ScoutSequenceTest extends SequencesTest {

    private ScoutSequence scoutSequence;
    private Map<Resource,String> resources;

    @Before
    public void setup(){
        newExplorer();
        setPosition(4,4);

        List<Biome> biomes = new ArrayList<>();
        biomes.add(Biome.MANGROVE);

        nav.map().updateMapThroughScan(biomes,true);

        resources = new HashMap<>();
        resources.put(Resource.WOOD, "HIGH");
    }


    @Test
    public void scoutSequenceTest() {

        setPosition(1,1);
        scoutSequence = new ScoutSequence(nav, getExplorerAI().getCheckList());

        Assert.assertEquals(scoutSequence.execute().getAction(), "move_to");

        setPosition(1,2);

        Assert.assertEquals(scoutSequence.execute().getAction(), "move_to");

        setPosition(1,3);

        Assert.assertEquals(scoutSequence.execute().getAction(), "move_to");

        setPosition(2,3);

        Assert.assertEquals(scoutSequence.execute().getAction(), "move_to");

        setPosition(3,3);

        Assert.assertEquals(scoutSequence.execute().getAction(), "explore");

        nav.map().updateMapWithResources(resources);

        Assert.assertEquals(scoutSequence.execute().getAction(), "scout");

        nav.map().updateMapThroughScout(resources,1,"N",true);

        Assert.assertEquals(scoutSequence.execute().getAction(), "scout");

        nav.map().updateMapThroughScout(resources,1,"W",false);

        Assert.assertEquals(scoutSequence.execute().getAction(), "scout");

        nav.map().updateMapThroughScout(resources,1,"E",true);

        Assert.assertEquals(scoutSequence.execute().getAction(), "scout");

        setPosition(3,4);
    }



}
