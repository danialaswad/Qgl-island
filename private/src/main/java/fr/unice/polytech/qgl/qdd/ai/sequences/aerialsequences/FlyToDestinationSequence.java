package fr.unice.polytech.qgl.qdd.ai.sequences.aerialsequences;

import fr.unice.polytech.qgl.qdd.Action;
import fr.unice.polytech.qgl.qdd.ai.CheckList;
import fr.unice.polytech.qgl.qdd.ai.sequences.Sequences;
import fr.unice.polytech.qgl.qdd.navigation.Navigator;
import fr.unice.polytech.qgl.qdd.navigation.Tile;

/**
 * Created by danial on 12/13/2015.
 */
public class FlyToDestinationSequence extends Sequences {
    private Tile destinationTile;

    public FlyToDestinationSequence(Navigator nav, CheckList checkList, Tile destinationTile) {
        super(nav, checkList);
        this.destinationTile = destinationTile;
    }

    @Override
    public Action execute() {
        switch (nav.relativeDirectionOfTileByAir(destinationTile)) {
            case FRONT: return fly();
            case RIGHT: return heading(nav.right());
            case LEFT: return heading(nav.left());
            default:break;
        }
        return fly();
    }

    @Override
    public boolean completed() {
        return destinationReached();
    }

    private boolean destinationReached(){
        return destinationTile.equals(nav.map().currentTile())
                || (neighbouringDestinationReached()) || !checkList.haveEnoughtBudgetToScanTheIsland();
    }

    private boolean neighbouringDestinationReached() {
       for (Tile tile: nav.finder().neighbouringTiles()){
            if (destinationTile.equals(tile)){
                return true;
            }
        }
        return false;
    }
}
