package fr.unice.polytech.qgl.qdd.ai.sequences.terrestrialsequences;

import fr.unice.polytech.qgl.qdd.Action;
import fr.unice.polytech.qgl.qdd.ai.CheckList;
import fr.unice.polytech.qgl.qdd.ai.sequences.Sequences;
import fr.unice.polytech.qgl.qdd.navigation.Compass;
import fr.unice.polytech.qgl.qdd.navigation.Navigator;
import fr.unice.polytech.qgl.qdd.navigation.Tile;

/**
 * Created by danial on 13/12/15.
 */
public class MoveSequence extends Sequences {

    protected Tile destinationTile;
    public MoveSequence(Navigator nav, CheckList checkList, Tile destinationTile) {
        super(nav, checkList);
        this.destinationTile = destinationTile;
    }

    @Override
    public Action execute() {
        Compass direction;
        switch(nav.relativeDirectionOfTile(destinationTile)) {
            case FRONT: 
                direction = nav.front();
                break;
            case RIGHT: 
                direction = nav.right();
                break;
            case LEFT: 
                direction= nav.left();
                break;
            case BACK: 
                direction = nav.back();
                break;
            default: 
                direction = nav.front();
                break;
        }
        return checkList.isCloseToTerrestrialBoundary()? move(nav.back()) : move(direction);
    }

    @Override
    public boolean completed() {
        return destinationReached();
    }

    private boolean destinationReached(){
        return destinationTile.equals(nav.map().currentTile());
    }

}
