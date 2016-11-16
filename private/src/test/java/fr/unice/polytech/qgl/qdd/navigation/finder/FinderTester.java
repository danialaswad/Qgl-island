package fr.unice.polytech.qgl.qdd.navigation.finder;

import fr.unice.polytech.qgl.qdd.navigation.*;
import org.junit.Before;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by danial on 25/12/2015.
 * */
public class FinderTester {
    protected Navigator nav;

    private static final int DEFAULT_WIDTH = 18, DEFAULT_HEIGHT = 18, DEFAULT_START_X = 1, DEFAULT_START_Y = 1;
    private static final Compass DEFAULT_START_FACING =  Compass.NORTH;

    private Method createTileMethod;
    private Method getTileMethod;
    private Method setFacingDirectionMethod;
    private Method getXMethod;
    private Method getYMethod;
    private Method setXMethod;
    private Method setYMethod;
    private Field heightField;
    private Field widthField;

    @Before
    public void setupTester() {
        try {
            setFacingDirectionMethod = Navigator.class.getDeclaredMethod("setFacingDirection", Compass.class);
            setFacingDirectionMethod.setAccessible(true);

            createTileMethod = IslandMap.class.getDeclaredMethod("createTile", int.class, int.class);
            createTileMethod.setAccessible(true);

            getTileMethod = IslandMap.class.getDeclaredMethod("getTile", int.class, int.class);
            getTileMethod.setAccessible(true);

            getXMethod = IslandMap.class.getDeclaredMethod("getX", Tile.class);
            getXMethod.setAccessible(true);

            getYMethod = IslandMap.class.getDeclaredMethod("getY", Tile.class);
            getYMethod.setAccessible(true);

            setXMethod = IslandMap.class.getDeclaredMethod("setX", int.class);
            setXMethod.setAccessible(true);

            setYMethod = IslandMap.class.getDeclaredMethod("setY", int.class);
            setYMethod.setAccessible(true);

            heightField = IslandMap.class.getDeclaredField("height");
            heightField.setAccessible(true);

            widthField = IslandMap.class.getDeclaredField("width");
            widthField.setAccessible(true);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }

        nav = new Navigator(DEFAULT_START_FACING);
        initializeMap(DEFAULT_WIDTH, DEFAULT_HEIGHT, DEFAULT_START_X, DEFAULT_START_Y);
    }

    public Navigator getNav(){
        return nav;
    }

    public void initializeMap(int width, int height, int startX, int startY) {
        nav = new Navigator(DEFAULT_START_FACING);
        setWidth(width);
        setHeight(height);
        setX(startX);
        setY(startY);

        for (int x = 0; x < width; x++) {
            for (int y = 0; y< width; y++) {
                createTile(x, y);
            }
        }
    }

    public void setFacingDirection(Compass direction) {
        try {
            setFacingDirectionMethod.invoke(nav, direction);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    public Tile getTileMethod(int x, int y) {
        Tile tile = null;
        try {
            tile =  (Tile)getTileMethod.invoke(nav.map(), x, y);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        return tile;
    }

    public int getX(Tile tile) {
        try {
            return (int) getXMethod.invoke(nav.map(), tile);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public int getY(Tile tile) {
        try {
            return (int) getYMethod.invoke(nav.map(), tile);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public void setPosition(int x, int y) {
        setX(x);
        setY(y);
    }

    public void setY(int y) {
        try {
            setYMethod.invoke(nav.map(), y);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    public void setX(int x) {
        try {
            setXMethod.invoke(nav.map(), x);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    public Tile getTile(int x, int y) {
        try {
            return (Tile) getTileMethod.invoke(nav.map(), x, y);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void setHeight(int h) {
        try {
            heightField.set(nav.map(), h);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public void setWidth(int w) {
        try {
            widthField.set(nav.map(), w);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public void createTile(int x, int y) {
        try {
            createTileMethod.invoke(nav.map(), x, y);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}

