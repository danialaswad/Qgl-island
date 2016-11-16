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
 * Created by danialaswad on 24/02/2016.
 */
public class TransformSequenceTest extends SequencesTest {

    private TransformSequence transformSequence;

    @Before
    public void setup(){
        newExplorer();
        setPosition(7,7);
        Set<Tile> tiles = nav.finder().neighbouringTiles();
        List<Biome> biomes = new ArrayList<>();
        biomes.add(Biome.MANGROVE);
        Map<Resource,String> resources = new HashMap<>();
        resources.put(Resource.WOOD,"HIGH");
        for (Tile t : tiles){
            t.setGround();
            t.addBiomes(biomes);
            t.addResources(resources);
        }
    }

    @Test
    public void selectResourceToManufactureTest(){
        transformSequence = new TransformSequence(nav,getExplorerAI().getCheckList(),getExplorer().getManufacturedResources());
        Assert.assertEquals(null, transformSequence.selectResourceToManufacture());

        Assert.assertEquals(false, getExplorerAI().getCheckList().enoughResourcesToManufacture());

        getExplorer().exploit(Resource.FRUITS,22);

        Assert.assertEquals(null, transformSequence.selectResourceToManufacture());

        getExplorer().exploit(Resource.SUGAR_CANE,2200);

        Assert.assertEquals(Resource.RUM,getExplorer().getManufacturedResources().keySet().stream().findFirst().orElse(null));

        Assert.assertEquals(true, getExplorerAI().getCheckList().enoughResourcesToManufacture());


        Assert.assertEquals(Resource.RUM, transformSequence.selectResourceToManufacture());

        Assert.assertEquals(200, transformSequence.execute().getIntParam("SUGAR_CANE"));
        Assert.assertEquals(20, transformSequence.execute().getIntParam("FRUITS"));

    }

}
