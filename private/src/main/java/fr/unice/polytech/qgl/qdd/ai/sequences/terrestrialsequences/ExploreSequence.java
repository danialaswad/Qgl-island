package fr.unice.polytech.qgl.qdd.ai.sequences.terrestrialsequences;

import fr.unice.polytech.qgl.qdd.Action;
import fr.unice.polytech.qgl.qdd.ai.CheckList;
import fr.unice.polytech.qgl.qdd.ai.sequences.Sequences;
import fr.unice.polytech.qgl.qdd.navigation.Navigator;

/**
 * Created by danial on 26/12/15.
 */
public class ExploreSequence extends Sequences {
    private MoveSequence moveSequence;
    private int counter;
    private static final int MAX_ITERATIONS = 10;

    public ExploreSequence(Navigator nav, CheckList checkList) {
        super(nav, checkList);
        counter = 0;
    }

    @Override
    public Action execute() {
        counter++;
        if (checkList.isExplorable(nav.map().currentTile())) {
            moveSequence = null;
            return explore();
        }

        if (nav.finder().getPotentiallyExploitableTile() != null) {
            moveSequence = new MoveSequence(nav,checkList, nav.finder().getNearestPotentiallyExploitableTile());
        }
        else {
            moveSequence = new MoveSequence(nav,checkList,nav.finder().getUnknownTileNeighbouringToPotentiallyExploitableTile());
        }

        return moveSequence.execute();
    }

    @Override
    public boolean completed() {
        return !checkList.isExplorable(nav.map().currentTile()) || counter>MAX_ITERATIONS;
    }
}
