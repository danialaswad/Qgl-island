package fr.unice.polytech.qgl.qdd.navigation;

/**
 * Created by danial on 12/13/2015.
 */
public interface TileListener {
    void typeDiscovered(Tile tile, String previousType, String currentType);

    void biomeDiscovered(Tile tile);

    void creekDiscovered(Tile tile);

    void tileExploited(Tile tile);
}
