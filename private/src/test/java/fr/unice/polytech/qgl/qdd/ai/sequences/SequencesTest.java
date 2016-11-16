package fr.unice.polytech.qgl.qdd.ai.sequences;

import fr.unice.polytech.qgl.qdd.QddExplorer;
import fr.unice.polytech.qgl.qdd.ai.ExplorerAI;
import fr.unice.polytech.qgl.qdd.enums.Resource;
import fr.unice.polytech.qgl.qdd.navigation.Navigator;
import fr.unice.polytech.qgl.qdd.navigation.finder.FinderTester;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Field;

/**
 * Created by danialaswad on 03/02/2016.
 */
public class SequencesTest extends FinderTester {

    private QddExplorer explorer;
    private ExplorerAI explorerAI;
    private String init;
    private Field navField;

    @Before
    public void Setup()
    {
       init = new String("{\"men\": 12,\"budget\": 20000,\"contracts\": [{ \"amount\": 600, \"resource\": \"WOOD\" },{ \"amount\": 200, \"resource\": \"FISH\" },{ \"amount\": 20, \"resource\": \"RUM\" }],\"heading\": \"N\"}");

        try {
            navField = QddExplorer.class.getDeclaredField("nav");
            navField.setAccessible(true);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        explorer = new QddExplorer(init);
        newExplorer();
    }

    public void newExplorer(){
        explorer = new QddExplorer(init);
        initializeMap(18,18,1,1);
        setNavExplorer(getNav());
        explorerAI = new ExplorerAI(explorer);
    }

    private void setNavExplorer(Navigator n) {
        try {
            navField.set(explorer, n);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getResourcesToCollectTest(){

        Assert.assertEquals(4, explorer.getResourcesToCollect().size());
        Assert.assertEquals(true,explorer.getResourcesToCollect().contains(Resource.SUGAR_CANE));
        Assert.assertEquals(true,explorer.getResourcesToCollect().contains(Resource.FRUITS));

    }

    public QddExplorer getExplorer(){
        return explorer;
    }

    public ExplorerAI getExplorerAI(){
        return explorerAI;
    }
}
