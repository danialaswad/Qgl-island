package fr.unice.polytech.qgl.qdd.navigation.compass;

import fr.unice.polytech.qgl.qdd.navigation.Compass;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by danial on 17/01/16.
 */
public class CompassEnumTest {

    @Test
    public void compassToStringTest(){
        Assert.assertEquals("N",Compass.NORTH.toString());

        Assert.assertEquals("E",Compass.EAST.toString());

        Assert.assertEquals("W",Compass.WEST.toString());

        Assert.assertEquals("S",Compass.SOUTH.toString());

    }

    @Test
    public void compassFromStringTest(){
        Assert.assertEquals(Compass.NORTH, Compass.fromString("N"));

        Assert.assertEquals(Compass.EAST, Compass.fromString("E"));

        Assert.assertEquals(Compass.WEST, Compass.fromString("W"));

        Assert.assertEquals(Compass.SOUTH, Compass.fromString("S"));

	Assert.assertEquals( null , Compass.fromString("Z"));

    }

	@Test
    public void compassValueOfTest()
	{
        	Assert.assertEquals( Compass.NORTH , Compass.valueOf("NORTH") );

        	Assert.assertEquals( Compass.EAST , Compass.valueOf("EAST") );

        	Assert.assertEquals( Compass.SOUTH , Compass.valueOf("SOUTH") );

        	Assert.assertEquals( Compass.WEST , Compass.valueOf("WEST") );

    }

    @Test
    public void compassAirTest(){

        /*
            NORTH
         */
        Assert.assertEquals(Compass.NORTH.airX(), 0);
        Assert.assertEquals(Compass.NORTH.airY(), 3);
        Assert.assertEquals(Compass.NORTH.airBackX(), 0);
        Assert.assertEquals(Compass.NORTH.airBackY(), -3);
        Assert.assertEquals(Compass.NORTH.airLeftX(), 3);
        Assert.assertEquals(Compass.NORTH.airLeftY(), 3);
        Assert.assertEquals(Compass.NORTH.airRightX(), -3);
        Assert.assertEquals(Compass.NORTH.airRightY(), 3);

        /*
            SOUTH
         */
        Assert.assertEquals(Compass.NORTH.airX(), 0);
        Assert.assertEquals(Compass.NORTH.airY(), 3);
        Assert.assertEquals(Compass.SOUTH.airBackX(), 0);
        Assert.assertEquals(Compass.SOUTH.airBackY(), 3);
        Assert.assertEquals(Compass.SOUTH.airLeftX(), -3);
        Assert.assertEquals(Compass.SOUTH.airLeftY(), -3);
        Assert.assertEquals(Compass.SOUTH.airRightX(), 3);
        Assert.assertEquals(Compass.SOUTH.airRightY(), -3);

        /*
            EAST
         */
        Assert.assertEquals(Compass.EAST.airX(), 3);
        Assert.assertEquals(Compass.EAST.airY(), 0);
        Assert.assertEquals(Compass.EAST.airBackX(), -3);
        Assert.assertEquals(Compass.EAST.airBackY(), 0);
        Assert.assertEquals(Compass.EAST.airLeftX(), 3);
        Assert.assertEquals(Compass.EAST.airLeftY(), -3);
        Assert.assertEquals(Compass.EAST.airRightX(), 3);
        Assert.assertEquals(Compass.EAST.airRightY(), 3);

        /*
            WEST
         */
        Assert.assertEquals(Compass.WEST.airX(), -3);
        Assert.assertEquals(Compass.WEST.airY(),0);
        Assert.assertEquals(Compass.WEST.airBackX(), 3);
        Assert.assertEquals(Compass.WEST.airBackY(), 0);
        Assert.assertEquals(Compass.WEST.airLeftX(), -3);
        Assert.assertEquals(Compass.WEST.airLeftY(), 3);
        Assert.assertEquals(Compass.WEST.airRightX(), -3);
        Assert.assertEquals(Compass.WEST.airRightY(), -3);
    }

    @Test
    public void compassGroundTest(){

        /*
            NORTH
         */
        Assert.assertEquals(Compass.NORTH.groundX(), 0);
        Assert.assertEquals(Compass.NORTH.groundY(), 1);

        /*
            SOUTH
         */
        Assert.assertEquals(Compass.SOUTH.groundX(), 0);
        Assert.assertEquals(Compass.SOUTH.groundY(), -1);

        /*
            EAST
         */
        Assert.assertEquals(Compass.EAST.groundX(), 1);
        Assert.assertEquals(Compass.EAST.groundY(), 0);

        /*
            WEST
         */
        Assert.assertEquals(Compass.WEST.groundX(), -1);
        Assert.assertEquals(Compass.WEST.groundY(), 0);
    }
}
