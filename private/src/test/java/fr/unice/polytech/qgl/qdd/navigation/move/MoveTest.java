package fr.unice.polytech.qgl.qdd.navigation.move;

import fr.unice.polytech.qgl.qdd.navigation.*;
import fr.unice.polytech.qgl.qdd.navigation.finder.FinderTester;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


/**
 * Created by danialaswad on 01/02/2016.
 */
public class MoveTest extends FinderTester {

    @Before
    public void setup(){

        initializeMap(18, 18, 1, 1);
        setFacingDirection(Compass.NORTH);
    }

    @Test
    public void flyTest(){

        /*=================================/
            Facing North at coordinate 1,1
        /================================*/
        Assert.assertEquals(1, getX(nav.map().currentTile()));
        Assert.assertEquals(1, getY(nav.map().currentTile()));

        nav.move().fly();

        Assert.assertNotEquals(1, getY(nav.map().currentTile()));
        Assert.assertEquals(4, getY(nav.map().currentTile()));
        Assert.assertEquals(1, getX(nav.map().currentTile()));

        nav.move().fly();

        Assert.assertEquals(7, getY(nav.map().currentTile()));
        Assert.assertEquals(1, getX(nav.map().currentTile()));

        /*=================================/
            Facing East at coordinate 1,7
        /================================*/
        setFacingDirection(Compass.EAST);

        nav.move().fly();

        Assert.assertEquals(7, getY(nav.map().currentTile()));
        Assert.assertEquals(4, getX(nav.map().currentTile()));

        nav.move().fly();

        Assert.assertEquals(7, getY(nav.map().currentTile()));
        Assert.assertEquals(7, getX(nav.map().currentTile()));

        /*=================================/
            Facing South at coordinate 7,7
        /================================*/
        setFacingDirection(Compass.SOUTH);

        nav.move().fly();

        Assert.assertEquals(4, getY(nav.map().currentTile()));
        Assert.assertEquals(7, getX(nav.map().currentTile()));

        nav.move().fly();

        Assert.assertEquals(1, getY(nav.map().currentTile()));
        Assert.assertEquals(7, getX(nav.map().currentTile()));

        /*=================================/
            Facing South at coordinate 7,1
        /================================*/
        setFacingDirection(Compass.WEST);

        nav.move().fly();

        Assert.assertEquals(1, getY(nav.map().currentTile()));
        Assert.assertEquals(4, getX(nav.map().currentTile()));

        nav.move().fly();

        Assert.assertEquals(1, getY(nav.map().currentTile()));
        Assert.assertEquals(1, getX(nav.map().currentTile()));
    }

    @Test
    public void turnTest(){
        /*=================================/
            Facing North at coordinate 1,1
        /================================*/

        Assert.assertEquals(1,getX(nav.map().currentTile()));
        Assert.assertEquals(1,getY(nav.map().currentTile()));

        nav.move().turn(Compass.EAST);

        Assert.assertEquals(4,getX(nav.map().currentTile()));
        Assert.assertEquals(4,getY(nav.map().currentTile()));

        /*=================================/
            Facing East at coordinate 4,4
        /================================*/

        nav.move().turn(Compass.NORTH);

        Assert.assertEquals(7,getX(nav.map().currentTile()));
        Assert.assertEquals(7,getY(nav.map().currentTile()));

        /*=================================/
            Facing North at coordinate 7,7
        /================================*/

        nav.move().turn(Compass.WEST);

        Assert.assertEquals(4,getX(nav.map().currentTile()));
        Assert.assertEquals(10,getY(nav.map().currentTile()));

        /*=================================/
            Facing West at coordinate 4,10
        /================================*/

        nav.move().turn(Compass.NORTH);

        Assert.assertEquals(1,getX(nav.map().currentTile()));
        Assert.assertEquals(13,getY(nav.map().currentTile()));

        setFacingDirection(Compass.SOUTH);
        /*=================================/
            Facing South at coordinate 1,13
        /================================*/

        nav.move().turn(Compass.EAST);

        Assert.assertEquals(4,getX(nav.map().currentTile()));
        Assert.assertEquals(10,getY(nav.map().currentTile()));

        /*=================================/
            Facing East at coordinate 4,10
        /================================*/

        nav.move().turn(Compass.SOUTH);

        Assert.assertEquals(7,getX(nav.map().currentTile()));
        Assert.assertEquals(7,getY(nav.map().currentTile()));

        /*=================================/
            Facing South at coordinate 7,7
        /================================*/

        nav.move().turn(Compass.WEST);

        Assert.assertEquals(4,getX(nav.map().currentTile()));
        Assert.assertEquals(4,getY(nav.map().currentTile()));

         /*=================================/
            Facing West at coordinate 4,4
        /================================*/

        nav.move().turn(Compass.SOUTH);

        Assert.assertEquals(1,getX(nav.map().currentTile()));
        Assert.assertEquals(1,getY(nav.map().currentTile()));
    }

    @Test
    public void walkTest(){
        /*=================================/
            Facing North at coordinate 1,1
        /================================*/

        Assert.assertEquals(1,getX(nav.map().currentTile()));
        Assert.assertEquals(1,getY(nav.map().currentTile()));

        nav.move().walk(Compass.NORTH);

        Assert.assertEquals(1,getX(nav.map().currentTile()));
        Assert.assertEquals(2,getY(nav.map().currentTile()));

        /*=================================/
            Facing North at coordinate 1,2
        /================================*/

        nav.move().walk(Compass.EAST);

        Assert.assertEquals(2,getX(nav.map().currentTile()));
        Assert.assertEquals(2,getY(nav.map().currentTile()));

        /*=================================/
            Facing East at coordinate 2,2
        /================================*/

        nav.move().walk(Compass.SOUTH);

        Assert.assertEquals(2,getX(nav.map().currentTile()));
        Assert.assertEquals(1,getY(nav.map().currentTile()));

        /*=================================/
            Facing South at coordinate 2,1
        /================================*/

        nav.move().walk(Compass.WEST);

        Assert.assertEquals(1,getX(nav.map().currentTile()));
        Assert.assertEquals(1,getY(nav.map().currentTile()));
    }
}
