package fr.unice.polytech.qgl.qdd.navigation;

import fr.unice.polytech.qgl.qdd.enums.Direction;

/**
 * Created by danial on 14/12/2015.
 */
public class Navigator {
    private IslandMap map;
    private Compass facing;

    private static final int TURNING_BUFFER = Move.AIR_DISTANCE + 1;
    private static final int BORDER_BUFFER = 2 * Move.AIR_DISTANCE;
    private static final String CONSTFACING = "FACING_";
    
    public Navigator(Compass facing) {
        this.facing = facing;
        map = new IslandMap();
        Move.init(map, this);
        Finder.init(map, this);
    }

    public Move move() {
        return Move.facing(facing);
    }

    public Finder finder() {
        return Finder.getInstance();
    }

    public IslandMap map() {
        return map;
    }

    /*=================
        Compass method
     =================*/
    public Compass front() {
        return facing;
    }

    public Compass back() {
        return RelativeDirection.valueOf( CONSTFACING.concat(facing.name())).back;
    }

    public Compass right() {
        return RelativeDirection.valueOf(CONSTFACING.concat(facing.name())).right;
    }

    public Compass left() {
        return RelativeDirection.valueOf(CONSTFACING.concat(facing.name())).left;
    }

    /**
     * Finds the direction of a given tile relative to the current position
     * @param tile target tile
     * @return a direction (ie. Direction.FRONT)
     */
    public Direction relativeDirectionOfTile(Tile tile) {
        return relativeDirection(map.direction(map.currentTile(), tile));
    }

    /**
     * Finds the direction of a given tile relative to the current position
     * @param tile target tile
     * @return a direction (ie. Direction.FRONT)
     */
    public Direction relativeDirectionOfTileByAir(Tile tile) {
        int xDiff = map.xDiff(tile), yDiff = map.yDiff(tile);
        boolean verticallyAligned = map.isVerticallyAligned(tile), horizontallyAligned = map.isHorizontallyAligned(tile);
        switch (facing){
            case NORTH:
                return relativeDirectionOfTileByAirNorth(verticallyAligned, horizontallyAligned, yDiff, xDiff);
            case EAST:
                return relativeDirectionOfTileByAirEast(verticallyAligned, horizontallyAligned, yDiff, xDiff);
            case SOUTH:
                return relativeDirectionOfTileByAirSouth(verticallyAligned, horizontallyAligned, yDiff, xDiff);
            case WEST:
                return relativeDirectionOfTileByAirWest(verticallyAligned, horizontallyAligned, yDiff, xDiff);
            default:
                return Direction.FRONT;
        }
    }

    /**
     * Chooses the turning direction when flying when the tile is at the BACK of the drone
     * @param destinationTile target tile
     * @return a direction (ie. Direction.LEFT)
     */
    public Direction chooseTurningDirection(Tile destinationTile) {
        if(facing == Compass.NORTH || facing == Compass.SOUTH) {
            return map.xDiff(destinationTile) < 0 ? Direction.LEFT : Direction.RIGHT;
        }
        else{
            return map.yDiff(destinationTile) < 0 ? Direction.LEFT : Direction.RIGHT;
        }
    }

    /**
     * Choose the direction in which the action scout has yet to be executed
     * @return a direction (ie. Direction.LEFT)
     */
    public Compass getDirectionToScout(){
        if(!finder().adjacentTile(Direction.FRONT).isExplored() && finder().adjacentTile(Direction.FRONT).haveNotBeenHere()){
            return front();
        }
        if(!finder().adjacentTile(Direction.LEFT).isExplored() && finder().adjacentTile(Direction.LEFT).haveNotBeenHere()){
            return left();
        }
        if(!finder().adjacentTile(Direction.RIGHT).isExplored() && finder().adjacentTile(Direction.RIGHT).haveNotBeenHere()){
            return right();
        }
        if(!finder().adjacentTile(Direction.BACK).isExplored() && finder().adjacentTile(Direction.BACK).haveNotBeenHere()){
            return back();
        }
        return null;
    }

    /*=================================================================
        Package-private methods: only usable in the navigation package
     ==================================================================*/

    void setFacingDirection(Compass facing) {
        this.facing = facing;
    }

    /**
     *
     * @param relativeDirection
     * @return
     */
    Compass absoluteDirection(Direction relativeDirection) {
        return RelativeDirection.valueOf(CONSTFACING.concat(facing.name())).getDirection(relativeDirection);
    }

    /**
     *
     * @param absoluteDirection
     * @return
     */
    Direction relativeDirection(Compass absoluteDirection) {
        switch (absoluteDirection.name().concat("_FROM_").concat(facing.name())) {
            case "NORTH_FROM_NORTH" : //fallthrough
            case "EAST_FROM_EAST"   : //fallthrough
            case "SOUTH_FROM_SOUTH" : //fallthrough
            case "WEST_FROM_WEST"   : return Direction.FRONT;

            case "EAST_FROM_NORTH" : //fallthrough
            case "SOUTH_FROM_EAST" : //fallthrough
            case "WEST_FROM_SOUTH" : //fallthrough
            case "NORTH_FROM_WEST" : return Direction.RIGHT;

            case "NORTH_FROM_EAST" : //fallthrough
            case "EAST_FROM_SOUTH" : //fallthrough
            case "SOUTH_FROM_WEST" : //fallthrough
            case "WEST_FROM_NORTH" : return Direction.LEFT;

            case "NORTH_FROM_SOUTH" : //fallthrough
            case "SOUTH_FROM_NORTH" : //fallthrough
            case "EAST_FROM_WEST"   : //fallthrough
            case "WEST_FROM_EAST"   : return Direction.BACK;

            default : return null;
        }
    }

    /*==================================================
        private method for relativeDirectionOfTileByAir
     ===================================================*/
    private Direction relativeDirectionOfTileByAirNorth(boolean verticallyAligned, boolean horizontallyAligned, int yDiff, int xDiff){
        if ((verticallyAligned && yDiff > 0 && map.y() < map.height() - BORDER_BUFFER) ||(horizontallyAligned && Math.abs(xDiff) <= TURNING_BUFFER) || (yDiff > TURNING_BUFFER)) {
            return Direction.FRONT;
        }
        else if (xDiff >= 1 && map.x() < map.width() - BORDER_BUFFER || map.x() < BORDER_BUFFER) {
            return Direction.RIGHT;
        }
        else{
            return Direction.LEFT;
        }
    }

    private Direction relativeDirectionOfTileByAirEast(boolean verticallyAligned, boolean horizontallyAligned, int yDiff, int xDiff){
        if ((horizontallyAligned && xDiff > 0 && map.x() < map.width() - BORDER_BUFFER) || (verticallyAligned && Math.abs(yDiff) <= TURNING_BUFFER) || (xDiff > TURNING_BUFFER)) {
            return Direction.FRONT;
        }
        else if (yDiff <= -1 && map.y() > BORDER_BUFFER || map.y() > map.height() - BORDER_BUFFER) {
            return Direction.RIGHT;
        }
        else {
            return Direction.LEFT;
        }
    }

    private Direction relativeDirectionOfTileByAirSouth(boolean verticallyAligned, boolean horizontallyAligned, int yDiff, int xDiff){
        if ((verticallyAligned && yDiff < 0 && map.y() > BORDER_BUFFER) || (horizontallyAligned && Math.abs(xDiff) <= TURNING_BUFFER) || (yDiff < -TURNING_BUFFER)) {
            return Direction.FRONT;
        }
        else if (xDiff <= -1 && map.x() > BORDER_BUFFER || map.x() > map.width() - BORDER_BUFFER) {
            return Direction.RIGHT;
        }
        else{
            return Direction.LEFT;
        }
    }

    private Direction relativeDirectionOfTileByAirWest(boolean verticallyAligned, boolean horizontallyAligned, int yDiff, int xDiff){
        if ((horizontallyAligned && xDiff < 0 && map.x() > BORDER_BUFFER) || (verticallyAligned && Math.abs(yDiff) <= TURNING_BUFFER) || (xDiff < -TURNING_BUFFER)) {
            return Direction.FRONT;
        }
        else if (yDiff >= 1 && map.y() < map.height() - BORDER_BUFFER || map.y() < BORDER_BUFFER) {
            return Direction.RIGHT;
        }
        else{
            return Direction.LEFT;
        }
    }

    /*================
        Private enum
     =================*/
    private enum RelativeDirection {
        FACING_NORTH(Compass.NORTH, Compass.EAST, Compass.SOUTH, Compass.WEST),
        FACING_EAST(Compass.EAST, Compass.SOUTH, Compass.WEST, Compass.NORTH),
        FACING_SOUTH(Compass.SOUTH, Compass.WEST, Compass.NORTH, Compass.EAST),
        FACING_WEST(Compass.WEST, Compass.NORTH, Compass.EAST, Compass.SOUTH);
        private Compass front, right, back, left;

        RelativeDirection(Compass front, Compass right, Compass back, Compass left){
            this.front = front;
            this.right = right;
            this.back = back;
            this.left = left;
        }

        private Compass getDirection(Direction direction) {
            switch (direction) {
                case FRONT: return front;
                case RIGHT: return right;
                case LEFT: return left;
                case BACK: return back;
                default: return null;
            }
        }
    }
}
