package fr.unice.polytech.qgl.qdd.ai.sequences.aerialsequences;

import fr.unice.polytech.qgl.qdd.Action;
import fr.unice.polytech.qgl.qdd.ai.CheckList;
import fr.unice.polytech.qgl.qdd.ai.sequences.Sequences;
import fr.unice.polytech.qgl.qdd.enums.Direction;
import fr.unice.polytech.qgl.qdd.navigation.Navigator;
import fr.unice.polytech.qgl.qdd.navigation.Tile;

/**
 * Created by danial on 12/13/2015.
 */
public class ScanSequence extends Sequences {
    private int counter;
    private FlyToDestinationSequence flyToDestinationSequence;

    public ScanSequence(Navigator nav, CheckList checkList) {
        super(nav, checkList);
        counter = 0;
    }

    @Override
    public Action execute() {
        counter++;

        if(flyToDestinationSequence !=null && flyToDestinationSequence.completed()) {
            flyToDestinationSequence = null;
            return scan();
        }


        if(isAboveIsland() && nav.map().currentTile().isUnknown()) {
            return scan();
        }

        if(isUnscannedGround(nav.map().currentTile())) {
            return scan();
        }
        if(isUnscannedGround(nav.finder().adjacentTileByAir(Direction.FRONT))) {
            return fly();
        }
        if(isUnscannedGround(nav.finder().adjacentTileByAir(Direction.RIGHT))) {
            return heading(nav.right());
        }
        if(isUnscannedGround(nav.finder().adjacentTileByAir(Direction.LEFT))) {
            return  heading(nav.left());
        }
        else{
            if (flyToDestinationSequence == null) {

                flyToDestinationSequence = new FlyToDestinationSequence(nav,checkList, nav.finder().getNearestUnscannedGroundTile());
            }
            return flyToDestinationSequence.execute();
        }

    }


    @Override
    public boolean completed() {
        return !nav.map().currentTile().isUnscanned() || counter > 20;
    }

    private static boolean isUnscannedGround(Tile tile) {
        if (tile == null)
    {
        return false;
    }
        return tile.isGround() && tile.isUnscanned();
    }

    private boolean isAboveIsland() {
        if(nav.finder().neighbouringTiles().stream().filter(Tile::isSea).toArray().length > 0) {
            return false;
        }
        return !nav.map().currentTile().isSea() && (nav.finder().detectShore(Direction.FRONT) != null && nav.finder().detectShore(Direction.BACK) != null
                || condition() );
    }


    private boolean condition()
    {
        return nav.finder().detectShore(Direction.LEFT) != null && nav.finder().detectShore(Direction.RIGHT) != null;
    }

}
