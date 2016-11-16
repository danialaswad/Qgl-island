package fr.unice.polytech.qgl.qdd.ai.sequences.aerialsequences;

import fr.unice.polytech.qgl.qdd.Action;
import fr.unice.polytech.qgl.qdd.ai.CheckList;
import fr.unice.polytech.qgl.qdd.ai.sequences.Sequences;
import fr.unice.polytech.qgl.qdd.navigation.Navigator;
import fr.unice.polytech.qgl.qdd.navigation.Tile;

public class ScanForBiomeSequence extends Sequences {
    private FlyToDestinationSequence flyToDestinationSequence;

    public ScanForBiomeSequence(Navigator nav, CheckList checkList) {
        super(nav, checkList);
    }

    @Override
    public Action execute() {
        if(nav.map().currentTile().isSea()){
            flyToDestinationSequence =  new FlyToDestinationSequence(nav, checkList, nav.finder().getNearestUnscannedGroundTileWithInterest(nav.finder().getNearestPotentiallyExploitableTile()));
            return flyToDestinationSequence.execute();
        }
        if (flyToDestinationSequence != null && flyToDestinationSequence.completed()){
            flyToDestinationSequence=null;
            return scan();
        }
        else{
            Tile currentTile = nav.map().currentTile();
            Tile objectiveTile = nav.finder().getNearestUnscannedGroundTileWithInterest(nav.map().currentTile());
            if(objectiveTile.equals(currentTile)){
                return scan();
            }
            if(flyToDestinationSequence == null){
                flyToDestinationSequence = new FlyToDestinationSequence(nav, checkList, objectiveTile);
            }
            return flyToDestinationSequence.execute();
            
        }
    }

    @Override
    public boolean completed() {
        return !checkList.haveEnoughBudgetToSearchForMoreBiomes() || checkList.enoughPotentialTilesToLand();
    }
}
