package fr.unice.polytech.qgl.qdd.ai.sequences.terrestrialsequences;

import fr.unice.polytech.qgl.qdd.ai.sequences.SequencesTest;
import fr.unice.polytech.qgl.qdd.enums.Biome;
import fr.unice.polytech.qgl.qdd.enums.Resource;
import fr.unice.polytech.qgl.qdd.navigation.Tile;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

/**
 * Created by danialaswad on 04/02/2016.
 */
public class ExploreSequenceTest extends SequencesTest {

    private ExploreSequence exploreSequence;
    private Map<Resource,String> resources;

    @Before
    public void setup(){
        newExplorer();
        setPosition(7,7);
        Set<Tile> tiles = nav.finder().neighbouringTiles();
        List<Biome> biomes = new ArrayList<>();
        biomes.add(Biome.MANGROVE);
        for (Tile t : tiles){
            t.setGround();
            t.addBiomes(biomes);
        }
        resources = new HashMap<>();
        resources.put(Resource.WOOD,"HIGH");
    }


    @Test
    public void exploreSequenceTest() {
        exploreSequence = new ExploreSequence(nav, getExplorerAI().getCheckList());

        nav.map().currentTile().addResources(resources);
        nav.map().currentTile().setExplored();

        Assert.assertEquals(exploreSequence.execute().getAction(), "move_to");
        setPosition(6, 7);

        Assert.assertEquals(exploreSequence.execute().getAction(), "explore");

        nav.map().currentTile().addResources(resources);
        nav.map().currentTile().setExplored();

        Assert.assertEquals(exploreSequence.execute().getAction(), "move_to");

        setPosition(6, 6);

        Assert.assertEquals(exploreSequence.execute().getAction(), "explore");

        nav.map().currentTile().addResources(resources);
        nav.map().currentTile().setExplored();

        Assert.assertEquals(exploreSequence.execute().getAction(), "move_to");

        setPosition(7, 6);

        Assert.assertEquals(exploreSequence.execute().getAction(), "explore");

        nav.map().currentTile().addResources(resources);
        nav.map().currentTile().setExplored();

        Assert.assertEquals(exploreSequence.execute().getAction(), "move_to");

        setPosition(8, 6);

        Assert.assertEquals(exploreSequence.execute().getAction(), "explore");

        nav.map().currentTile().addResources(resources);
        nav.map().currentTile().setExplored();

        Assert.assertEquals(exploreSequence.execute().getAction(), "move_to");

        setPosition(8, 7);

        Assert.assertEquals(exploreSequence.execute().getAction(), "explore");

        nav.map().currentTile().addResources(resources);
        nav.map().currentTile().setExplored();

        Assert.assertEquals(exploreSequence.execute().getAction(), "move_to");

        setPosition(8, 8);

        Assert.assertEquals(exploreSequence.execute().getAction(), "explore");

        nav.map().currentTile().addResources(resources);
        nav.map().currentTile().setExplored();

        Assert.assertEquals(exploreSequence.execute().getAction(), "move_to");

        setPosition(7, 8);

        Assert.assertEquals(exploreSequence.execute().getAction(), "explore");

        nav.map().currentTile().addResources(resources);
        nav.map().currentTile().setExplored();

        Assert.assertEquals(exploreSequence.execute().getAction(), "move_to");
    }
}
