package fr.unice.polytech.qgl.qdd.ai.sequences;

import fr.unice.polytech.qgl.qdd.Action;
import fr.unice.polytech.qgl.qdd.ai.CheckList;
import fr.unice.polytech.qgl.qdd.enums.Resource;
import fr.unice.polytech.qgl.qdd.navigation.Compass;
import fr.unice.polytech.qgl.qdd.enums.Direction;
import fr.unice.polytech.qgl.qdd.navigation.Navigator;
import fr.unice.polytech.qgl.qdd.navigation.Tile;

import java.util.List;

/**
 * Created by danial on 26/12/15.
 */
public abstract class Sequences {

    protected CheckList checkList;
    protected Navigator nav;

    private static final String DIRECTION = "direction";

    public Sequences(Navigator nav, CheckList checkList){
        this.checkList = checkList;
        this.nav = nav;
    }

    public abstract Action execute();

    public abstract boolean completed();

    /*
        Action-building helper methods
     */
    protected Action echo(Compass direction){
        return new Action(Action.ECHO).addParameter(DIRECTION, direction.toString());
    }

    protected Action scan() { return new Action(Action.SCAN); }

    protected Action fly() {
        return checkList.isCloseToAerialBoundary()?  chooseTurningDirection(): new Action(Action.FLY);
    }

    protected Action heading(Compass direction){
        return new Action(Action.HEADING).addParameter(DIRECTION, direction.toString());
    }

    protected Action stop(){
        return new Action(Action.STOP);
    }

    protected Action move(Compass direction){
       return new Action(Action.MOVE_TO).addParameter(DIRECTION,direction.toString());
    }

    protected Action scout(Compass direction){
        return new Action(Action.SCOUT).addParameter(DIRECTION,direction.toString());
    }

    protected Action glimpse(Compass direction, int range){
        Action action =  new Action(Action.GLIMPSE).addParameter(DIRECTION,direction.toString());
        action.addParameter("range", range);
        return action;
    }

    protected Action explore() {
        return new Action(Action.EXPLORE);
    }

    protected Action exploit(List<Resource> resources)  {
        Action action = new Action(Action.EXPLOIT);
        for (Resource r: resources) {
            action.addParameter("resource", r.toString());
        }
        return action;
    }

    protected Action transform( Resource resource , int nbResource )  {
        
        Action action = new Action(Action.TRANSFORM);

        action.addParameter(resource.toString(),nbResource);

        return action;
    }

    protected Action transform( Resource resource1 , int nbResource1, Resource resource2 , int nbResource2)  {

        Action action = new Action(Action.TRANSFORM);

        action.addParameter(resource1.toString(),nbResource1).addParameter(resource2.toString(),nbResource2);

        return action;
    }

    protected Action chooseTurningDirection() {
        int unknownTilesOnRight = 0, unknownTilesOnLeft = 0;

        for (Tile t: nav.finder().getTilesOnSide(Direction.RIGHT)){
            if(t.isUnknown())
            {
                unknownTilesOnRight++;
            }
        }

        for (Tile t: nav.finder().getTilesOnSide(Direction.LEFT)){
            if(t.isUnknown())
            {
                unknownTilesOnLeft++;
            }
        }

        if (unknownTilesOnLeft > unknownTilesOnRight)
        {
            return heading(nav.left());
        }
        else { return heading(nav.right()); }
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
