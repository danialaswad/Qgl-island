package fr.unice.polytech.qgl.qdd.ai;

import fr.unice.polytech.qgl.qdd.Action;
import fr.unice.polytech.qgl.qdd.QddExplorer;
import fr.unice.polytech.qgl.qdd.enums.Resource;
import fr.unice.polytech.qgl.qdd.navigation.Navigator;
import fr.unice.polytech.qgl.qdd.navigation.finder.FinderTester;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by danialaswad on 01/03/2016.
 */
public class CheckListPlusTest extends FinderTester{
    private QddExplorer explorer;
    private ExplorerAI explorerAI;

    private Field navField;

    @Before
    public void setup() {


        String init = new String("{\"men\": 12,\"budget\": 20000,\"contracts\": [{ \"amount\": 10, \"resource\": \"FUR\" },{ \"amount\": 200, \"resource\": \"LEATHER\" }],\"heading\": \"N\"}");

        try {
            navField = QddExplorer.class.getDeclaredField("nav");
            navField.setAccessible(true);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        explorer = new QddExplorer(init);
        newExplorer(init);
    }

    public void newExplorer(String init){
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
    public void getResourceToCollectTest(){
        Set<Resource> resourceSet = new HashSet<>();
        resourceSet.add(Resource.FUR);

        Assert.assertEquals(1, explorerAI.getCheckList().getResourcesToCollect().size());
        Assert.assertEquals(200, explorer.getContract().get(Resource.LEATHER).intValue());

        Assert.assertFalse(explorerAI.getCheckList().contractCompleted());


        explorer.exploit(Resource.FUR, 10);

        Assert.assertEquals(resourceSet, explorer.getResourcesToCollect());


        Assert.assertFalse(explorerAI.getCheckList().contractCompleted());


        explorer.exploit(Resource.FUR, 650);



        Action action = new Action(Action.TRANSFORM);
        action.addParameter(Resource.FUR.toString(),660);

        explorer.transform(Resource.LEATHER,220,action);

        Assert.assertEquals(resourceSet, explorer.getResourcesToCollect());


        Assert.assertFalse(explorerAI.getCheckList().contractCompleted());

        Assert.assertEquals(0, explorer.getResources().get(Resource.FUR).intValue());
        Assert.assertEquals(220, explorer.getResources().get(Resource.LEATHER).intValue());
        Assert.assertEquals(resourceSet, explorer.getResourcesToCollect());

        explorer.exploit(Resource.FUR, 3);


        Assert.assertFalse(explorerAI.getCheckList().contractCompleted());

        explorer.exploit(Resource.FUR, 6);


        Assert.assertFalse(explorerAI.getCheckList().contractCompleted());

        explorer.exploit(Resource.FUR, 1);


        Assert.assertTrue(explorerAI.getCheckList().contractCompleted());
    }
    @Test
    public void getResourceToCollectFurtherTest(){
        Assert.assertEquals(1, explorerAI.getCheckList().getResourcesToCollect().size());
        Assert.assertEquals(200, explorer.getContract().get(Resource.LEATHER).intValue());

        Assert.assertFalse(explorerAI.getCheckList().contractCompleted());


        explorer.exploit(Resource.FUR, 10);


        Assert.assertFalse(explorerAI.getCheckList().contractCompleted());


        explorer.exploit(Resource.FUR, 590);


        Assert.assertFalse(explorerAI.getCheckList().contractCompleted());


        Action action = new Action(Action.TRANSFORM);
        action.addParameter(Resource.FUR.toString(),600);

        explorer.transform(Resource.LEATHER,180,action);


        Assert.assertFalse(explorerAI.getCheckList().contractCompleted());

        Assert.assertEquals(0, explorer.getResources().get(Resource.FUR).intValue());
        Assert.assertEquals(180, explorer.getResources().get(Resource.LEATHER).intValue());

        explorer.exploit(Resource.FUR, 3);


        Assert.assertFalse(explorerAI.getCheckList().contractCompleted());

        explorer.exploit(Resource.FUR, 6);


        Assert.assertFalse(explorerAI.getCheckList().contractCompleted());

        explorer.exploit(Resource.FUR, 1);


        Assert.assertFalse(explorerAI.getCheckList().contractCompleted());

        Set<Resource> resourceSet = new HashSet<>();
        resourceSet.add(Resource.FUR);
        Assert.assertEquals(resourceSet, explorer.getResourcesToCollect());

    }


}
