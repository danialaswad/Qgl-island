package fr.unice.polytech.qgl.qdd.ai.sequences.terrestrialsequences;

import fr.unice.polytech.qgl.qdd.Action;
import fr.unice.polytech.qgl.qdd.ai.CheckList;
import fr.unice.polytech.qgl.qdd.ai.sequences.Sequences;
import fr.unice.polytech.qgl.qdd.ai.sequences.commonsequences.StopSequence;
import fr.unice.polytech.qgl.qdd.navigation.Navigator;

/**
 * Created by danial on 26/12/15.
 */

public class ScoutSequence extends Sequences {
    private MoveSequence moveSequence;

    public ScoutSequence(Navigator nav, CheckList checkList) {
        super(nav, checkList);
    }

    @Override
    public Action execute() {
        if (moveSequence != null && moveSequence.completed()) {
            moveSequence=null;
            if (checkList.isExplorable(nav.map().currentTile())) {
                return explore();
            }
        }

        if (moveSequence == null) {
            if ( nav.map().currentTile().hasResources() && (nav.getDirectionToScout()) != null ) {
                return scout(nav.getDirectionToScout());
            }
            if (nav.finder().getExploitableTile() != null) {
                moveSequence = new MoveSequence(nav, checkList, nav.finder().getNearestExploitableTile());
            } else if (nav.finder().getPotentiallyExploitableTile() != null) {
                moveSequence = new MoveSequence(nav, checkList, nav.finder().getNearestPotentiallyExploitableTile());
            } else {
                return new StopSequence(nav,checkList).execute();
            }
        }
        return moveSequence.execute();
    }

    @Override
    public boolean completed() {
        return nav.getDirectionToScout() == null;
    }
}
