package fr.unice.polytech.qgl.qdd.navigation;

import fr.unice.polytech.qgl.qdd.enums.Direction;

import java.util.*;
import java.util.stream.Collectors;

/**
 * This handles the search for tiles in the map
 * Methods in this class only return the tile object whether a single tile, a set of tile or a list of tile
 * Created by danial on 23/12/2015.
 */
public class Finder {
    private static Finder instance;
    private IslandMap map;
    private Navigator nav;
    private Random random;

    private static final int UNKNOWN_TILE_THRESHOLD = 25;
    private static final int RANDOM_TILE_DISTANCE = 3;
    private static final int NUMBER_OF_TILE_SCANNED = 2;

    private Finder(IslandMap map, Navigator nav) {
        this.map = map;
        this.nav = nav;
        random = new Random();
    }

    /*==================================
        Methods returning a single tile
     ===================================*/

    /**
     * Finds a tile with a given coordinate x and y
     * @param x coordinate of the tile
     * @param y coordinate of the tile
     * @return a tile
     */
    public Tile getTile(int x, int y) {
        return map.getTile(x, y);
    }

    /**
     * Finds a tile according to the direction given
     * @param direction direction in which the tile is situated
     * @return a tile
     */
    public Tile adjacentTile(Direction direction) {
        return (Tile) allTiles(direction, 1).toArray()[0];
    }

    /**
     * Finds a tile according to the direction given that is accessible by air (the center of a 3X3 tile)
     * @param direction direction in which the tile is situated
     * @return a tile
     */
    public Tile adjacentTileByAir(Direction direction) {
        switch (direction) {
            case FRONT: return frontTileByAir(nav.absoluteDirection(direction));
            case RIGHT: return rightTileByAir(nav.absoluteDirection(direction));
            case LEFT: return leftTileByAir(nav.absoluteDirection(direction));
            default:
        }
        return null;
    }

    /**
     * Randomly returns a tile from a distance given from the current position
     * @param range distance from current position
     * @return a tile
     */
    public Tile getRandomNearbyTile(int range) {
        int minX = map.x() - range < 0 ? 0 : (map.x() - range);
        int maxX = (map.x() + range) >= map.getWidth() ? map.getHeight()-1 : (map.x() + range);
        int minY = map.y() - range < 0 ? 0 : (map.y() - range);
        int maxY = (map.y() + range) >= map.getHeight() ? map.getHeight()-1 : (map.y() + range);
        int randomX, randomY;

        do{
            randomX = minX + random.nextInt(maxX - minX);
            randomY = minY + random.nextInt(maxY - minY);
        }
        while (randomX == map.x() && randomY == map.y() || map.getTile(randomX, randomY) == null);

        return map.getTile(randomX, randomY);
    }

    /**
     * Finds a random tile from the range of 3
     * @return a tile
     */
    public Tile getRandomNearbyTile(){
        return getRandomNearbyTile(RANDOM_TILE_DISTANCE);
    }

    /**
     * Finds the nearest exploitable tile from the current position
     * Exploitable meaning that the tile contains resource(s) to be collected
     * @return an exploitable tile
     */
    public Tile getNearestExploitableTile() {
        return nav.map().getExploitableTiles().stream()
                .filter(tile -> tile.isNotExploited() && tile.haveNotBeenHere())
                .min((tile1, tile2) -> Integer.compare(map.distanceToTile(tile1), map.distanceToTile(tile2)))
                .orElse(null);
    }

    /**
     * Finds the nearest potentially exploitable tile from the current position
     * Potentially exploitable tile meaning that the tile contains the biome of an interesting resource, but the existence of the resource is uncertain
     * @return a potentially exploitable tile
     */
    public Tile getNearestPotentiallyExploitableTile() {
        return nav.map().getPotentiallyExploitableTiles().stream()
                .filter(tile -> tile.haveNotBeenHere() && !tile.hasResources())
                .min((tile1, tile2) -> Integer.compare(map.distanceToTile(tile1), map.distanceToTile(tile2)))
                .orElse(null);
    }

    /**
     * Finds the first exploitable tile from the map
     * @return an exploitable tile
     */
    public Tile getExploitableTile(){
        return map.getExploitableTiles().stream()
                .filter(tile1 -> tile1.isNotExploited() && tile1.haveNotBeenHere())
                .findFirst().orElse(null);
    }

    /**
     * Finds the first potentially exploitable tile from the map
     * @return a potentially exploitable tile
     */
    public Tile getPotentiallyExploitableTile(){
        return map.getPotentiallyExploitableTiles().stream()
                .filter(tile1 -> tile1.haveNotBeenHere() && !tile1.hasResources())
                .findFirst().orElse(null);
    }

    /**
     * Finds the nearest unscanned ground tile from the current position
     * @return an unscanned ground tile
     */
    public Tile getNearestUnscannedGroundTile() {
        List<Tile> unscanned = new ArrayList<>();
        map.getGroundTiles().stream().filter(tile -> tile.isUnscanned() && tile.isNotCalledForScan()).forEach( unscanned::add );
        Tile tile = unscanned.stream().min((tile1, tile2) -> Integer.compare(map.distanceToTile(tile1), map.distanceToTile(tile2))).orElse(getRandomNearbyUnScannedTile());
        tile.setCalledForScan();
        return tile;
    }

    /**
     * Finds the nearest unscanned ground tile to the given reference tile
     * @param referenceTile a reference tile
     * @return an unscanned ground tile
     */
    public Tile getNearestUnscannedGroundTileWithInterest(Tile referenceTile){
        List<Tile> unscanned = new ArrayList<>();
        map.getUnknownTiles().stream().filter(tile -> tile.isUnscanned() && tile.isNotCalledForScan() && getNumberOfUnknownTilesNearBy(tile) >= UNKNOWN_TILE_THRESHOLD).forEach( unscanned::add );
        Tile tile = unscanned.stream().min((tile1, tile2) -> Integer.compare(map.distanceToTile(referenceTile,tile1), map.distanceToTile(referenceTile,tile2))).orElse(getRandomNearbyUnScannedTile());
        tile.setCalledForScan();
        return tile;
    }

    /**
     * Finds a random tile near to current position that has yet to be scanned
     * @return an unscaned tile
     */
    public Tile getRandomNearbyUnScannedTile(){
        List<Tile> unscanned = new ArrayList<>();
        Set<Tile> tiles = new HashSet<>();
        nav.map().getScannedTiles().stream().filter(tile2 -> aerialNeighbouringTilesRefference(tile2).stream()
                .filter(tile1 -> !tile1.isNotCalledForScan()).count() > NUMBER_OF_TILE_SCANNED)
                .forEach(tile3 -> tiles.addAll(aerialNeighbouringTilesRefference(tile3)));
        tiles.stream().filter(tile -> tile.isUnknown() && tile.isNotCalledForScan()).forEach( unscanned::add );
        Tile tile = unscanned.stream().min((tile1, tile2) -> Integer.compare(map.distanceToTile(tile1), map.distanceToTile(tile2))).orElse(getRandomNearbyTile());
        tile.setCalledForScan();
        return tile;
    }

    /**
     * Finds a tile that yet to be scanned and that is near to a potentially exploitable tile
     * @return an unscanned tile
     */
    public Tile getUnknownTileNeighbouringToPotentiallyExploitableTile(){
        Tile t = map.getPotentiallyExploitableTiles().stream()
                .min((tile1, tile2) -> Integer.compare(map.distanceToTile(tile1), map.distanceToTile(tile2)))
                .orElse(null);
        if (t != null) {
            return aerialNeighbouringTilesRefference(t).stream()
                    .filter(tile -> !tile.hasResources() && tile.isUnknown())
                    .min((tile1, tile2) -> Integer.compare(map.distanceToTile(tile1), map.distanceToTile(tile2)))
                    .orElse(getRandomNearbyTile());
        }
        else { return getRandomNearbyTile();}
    }

    /**
     * Finds
     * @param direction orientation in which the desirable tile is located
     * @return a tile
     */
    public Tile detectShore(Direction direction) {
        Compass absoluteDirection = nav.absoluteDirection(direction);
        Set<Tile> tiles = map.getTiles(absoluteDirection);
        List<Tile> sortedTiles;
        if(absoluteDirection == Compass.NORTH || absoluteDirection == Compass.SOUTH) {
            sortedTiles = tiles.stream().sorted((tile1, tile2)
                    -> Integer.compare(Math.abs(map.yDiff(tile1)), Math.abs(map.yDiff(tile2))))
                    .collect(Collectors.toList());
        }

        else {
            sortedTiles = tiles.stream().sorted((tile1, tile2)
                    -> Integer.compare(Math.abs(map.xDiff(tile1)), Math.abs(map.xDiff(tile2))))
                    .collect(Collectors.toList());
        }
        return detectShore(sortedTiles);
    }

    /*====================================
        Methods returning a set of tile
     =====================================*/

    /**
     * Finds a set of tile surrounding the current tile excluding the current tile.
     * The surrounding tile is the 3X3 tiles centered on the current tile.
     * @return a set of tiles
     */
    public Set<Tile> neighbouringTiles() {
        return map.getSurroundingTiles(map.currentTile());
    }

    /**
     * Finds a set of tile surrounding the tile given in argument excluding the tile itself.
     * The surrounding tile is the 3X3 tiles centered on the given tile.
     * @param referenceTile target tile
     * @return a set of tile
     */
    public Set<Tile> getSurroundingTiles(Tile referenceTile) {
        return map.getSurroundingTiles(referenceTile);
    }

    /**
     * Finds a set of tile aerially surrounding the current tile excluding it self
     * The aerially surrounding tile is the 9x9 tiles centered on the current tile.
     * This set only return the center of each aerially neighbouring tile.
     * @return a set of tiles
     */
    public Set<Tile> aerialNeighbouringTiles(){
        return aerialNeighbouringTilesRefference(map.currentTile());
    }

    /**
     * Finds a set of tile aerially surrounding the tile given in argument excluding the tile itself
     * The aerially surrounding tile is the 12x12 tiles centered on the given tile.
     * This set only return the center of each aerially neighbouring tile.
     * @param tile target tile
     * @return a set of tiles
     */
    public Set<Tile> aerialNeighbourInterestTiles(Tile tile){
        Set<Tile> tiles = new HashSet<>();

        for (int x = map.getX(tile) - 6; x <=map.getX(tile)+6; x +=3){
            for (int y = map.getY(tile)-6; y <= map.getY(tile)+6; y+=3){
                if (y >= 0 && y < map.height() && x >= 0 && x < map.width()) {
                    tiles.add(map.getTile(x, y));
                }
            }
        }
        return tiles;
    }

    /**
     * Finds a set of tile aerially surrounding the tile given in argument excluding the tile itself
     * The aerially surrounding tile is the 9x9 tiles centered on the given tile.
     * This set only return the center of each aerially neighbouring tile.
     * @param tile target tile
     * @return a set of tiles
     */
    public Set<Tile> aerialNeighbouringTilesRefference(Tile tile){
        Set<Tile> tiles = new HashSet<>();

        for (int y = map.getY(tile)-3; y < map.getY(tile)+3; y+=3){
            for (int x = map.getX(tile) - 3; x <map.getX(tile)+3; x +=3){
                if (y >= 0 && y < map.height() && x >= 0 && x < map.width()) {
                    tiles.addAll(map.getSurroundingTiles(map.getTile(x, y)));
                }
            }
        }
        return tiles;
    }

    /**
     * A set of tile aligning to the direction given from the current position with a distance given will be return
     * @param direction Direction of interest (ie. Direction.FRONT)
     * @param range distance from current position
     * @return a set of tiles
     */
    public Set<Tile> allTiles(Direction direction, int range) {
        return map.getTiles(nav.absoluteDirection(direction), range);
    }

    /**
     * A set of tile aligning to the direction given from the current position will be return
     * @param direction Direction of interest (ie. Direction.FRONT)
     * @return a set tile
     */
    public Set<Tile> allTiles(Direction direction) {
        return map.getTiles(nav.absoluteDirection(direction));
    }

    /**
     * Finds all tiles that are on the side of the current position.
     * ie. if the you are facing NORTH, the tiles on you WEST are all the tiles that
     * have the coordinate of x < that the coordinate x of your current position
     * @param side Direction of interest (ie. Direction.FRONT)
     * @return a set of tiles
     */
    public Set<Tile> getTilesOnSide(Direction side) {
        Set<Tile> tiles = new HashSet<>();
        Compass absoluteSide = nav.absoluteDirection(side);
        if(absoluteSide == Compass.NORTH || absoluteSide == Compass.SOUTH) {
            for(int x = 0; x < map.getWidth(); x++) {
                tiles.addAll(map.getTiles(x, map.y(), absoluteSide));
            }
        }
        else{
            for(int y = 0; y < map.getHeight(); y++) {
                tiles.addAll(map.getTiles(map.x(), y, absoluteSide));
            }
        }
        return tiles;
    }

    /*=========================================================
        Private and static methods for single implementation
     ==========================================================*/

    public static void init(IslandMap map, Navigator nav) {
        instance = new Finder(map, nav);
    }

    static Finder getInstance() {
        return instance;
    }

    /*===================
        Private methods
    =====================*/

    private Tile frontTileByAir(Compass direction) {
        return map.getTile(map.x() + direction.airX(), map.y() + direction.airY());
    }

    private Tile rightTileByAir(Compass direction) {
        return map.getTile(map.x() + direction.airRightX(), map.y() + direction.airRightY());
    }

    private Tile leftTileByAir(Compass direction) {
        return map.getTile(map.x() + direction.airLeftX(), map.y()+ direction.airLeftY());
    }

    private int getNumberOfUnknownTilesNearBy(Tile t){
        return (int) aerialNeighbourInterestTiles(t).stream().filter(tile -> tile.isUnknown()).count();
    }

    private Tile detectShore(List<Tile> sortedTiles){
        for (int i = 0; i < sortedTiles.size(); i++) {
            if (sortedTiles.get(i).isGround()) {
                return detectShoreWhenNearestTileIsGround(sortedTiles,i);
            }
            else if (sortedTiles.get(i).isUnknown()) {
                return detectShoreWhenNearestTileIsUnknown(sortedTiles,i);
            }
            else if (sortedTiles.get(i).isSea()) {
                return detectShoreWhenNearestTileIsSea(sortedTiles,i);
            }
        }
        return null;
    }

    private Tile detectShoreWhenNearestTileIsGround(List<Tile> sortedTiles, int index){
        while (++index < sortedTiles.size()) {
            if (sortedTiles.get(index).isSea()) {
                return sortedTiles.get(index -1);
            }
        }
        return null;
    }

    private Tile detectShoreWhenNearestTileIsSea(List<Tile> sortedTiles, int index){
        while (++index < sortedTiles.size()) {
            if(sortedTiles.get(index).isGround()) {
                while (++index < sortedTiles.size()) {
                    if(sortedTiles.get(index).isUnknown()) {
                        return sortedTiles.get(index);
                    }
                }
                return null;
            }
        }
        return null;
    }
    private Tile detectShoreWhenNearestTileIsUnknown(List<Tile> sortedTiles, int index){
        while (++index < sortedTiles.size()) {
            if(sortedTiles.get(index).isGround()) {
                return sortedTiles.get(index -1);
            }
        }
        return null;
    }
}
