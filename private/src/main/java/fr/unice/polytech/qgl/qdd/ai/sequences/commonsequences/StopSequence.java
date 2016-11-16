package fr.unice.polytech.qgl.qdd.ai.sequences.commonsequences;

import fr.unice.polytech.qgl.qdd.Action;
import fr.unice.polytech.qgl.qdd.ai.CheckList;
import fr.unice.polytech.qgl.qdd.ai.sequences.Sequences;
import fr.unice.polytech.qgl.qdd.navigation.Navigator;

/**
 * Created by danial on 12/13/2015.
 */
public class StopSequence extends Sequences {
    public StopSequence(Navigator nav, CheckList checkList) {
        super(nav, checkList);
    }

    @Override
    public Action execute() {
        return stop();
    }

    @Override
    public boolean completed() {
        return true;
    }
}
