package fr.unice.polytech.qgl.qdd.navigation;

/**
 * Created by danial on 23/12/2015.
 */
public enum Compass {
    NORTH, EAST, SOUTH, WEST;

    /**
     * Translate a string to an enum
     * @param direction string of direction (ie. N)
     * @return the enum Compass
     */
    public static Compass fromString(String direction) {
        switch (direction) {
            case "N": return NORTH;
            case "E": return EAST;
            case "S": return SOUTH;
            case "W": return WEST;
        default:
        }
        return null;
    }

    /**
     *
     * @return
     */
    public int groundX(){
        switch(this){
            case EAST : return 1;
            case WEST : return -1;
            default : return 0;
        }
    }

    /**
     *
     * @return
     */
    public int groundY(){
        switch(this){
            case SOUTH : return -1;
            case NORTH : return 1;
            default : return 0;
        }
    }

    /**
     *
     * @return
     */
    public int airX(){
        switch(this){
            case EAST : return 3;
            case WEST : return -3;
            default : return 0;
        }
    }

    /**
     *
     * @return
     */
    public int airY(){
        switch(this){
            case SOUTH : return -3;
            case NORTH : return 3;
            default : return 0;
        }
    }

    /**
     *
     * @return
     */
    public int airBackX(){
        switch(this){
            case EAST : return -3;
            case WEST : return 3;
            default : return 0;
        }
    }

    /**
     *
     * @return
     */
    public int airBackY(){
        switch(this){
        case SOUTH : return 3;
        case NORTH : return -3;
            default : return 0;
        }
    }

    /**
     *
     * @return
     */
    public int airLeftX(){
        switch(this){
            case SOUTH : return -3;
            case NORTH : return 3;
            case EAST : return 3;
            case WEST : return -3;
            default : return 0;
        }
    }

    /**
     *
     * @return
     */
    public int airLeftY(){
        switch(this){
            case SOUTH : return -3;
            case NORTH : return 3;
            case EAST : return -3;
            case WEST : return 3;
            default : return 0;
        }
    }

    /**
     *
     * @return
     */
    public int airRightX(){
        switch(this){
            case SOUTH : return 3;
            case NORTH : return -3;
            case EAST : return 3;
            case WEST : return -3;
            default : return 0;
        }
    }

    /**
     *
     * @return
     */
    public int airRightY(){
        switch(this){
            case SOUTH : return -3;
            case NORTH : return 3;
            case EAST : return 3;
            case WEST : return -3;
            default : return 0;
        }
    }

    @Override
    public String toString() {
        return name().substring(0,1);
    }
}
