package fr.unice.polytech.qgl.qdd.ai.sequences.commonsequences;

import fr.unice.polytech.qgl.qdd.Action;
import fr.unice.polytech.qgl.qdd.ai.CheckList;
import fr.unice.polytech.qgl.qdd.ai.sequences.Sequences;
import fr.unice.polytech.qgl.qdd.navigation.Navigator;

/**
 * Created by danial on 12/13/2015.
 */
public class LandSequence extends Sequences {
    private int men;
    private boolean completed;

    public LandSequence(Navigator nav, CheckList checkList, int men) {
        super(nav, checkList);
        this.men = men;
        completed = false;
    }

    @Override
    public Action execute() {
        completed = true;

        String creek;
        if (nav.map().getCreeksWithPotentiallyExploitableRessources() != null
                && !nav.map().getCreeksWithPotentiallyExploitableRessources().isEmpty()){
            creek = nav.map().getCreeksWithPotentiallyExploitableRessources().get(0);
        }else{
            creek = nav.map().getCreeks().get(0);
        }

        return new Action(Action.LAND).addParameter("creek", creek).addParameter("people", Integer.toString(men));
    }

    @Override
    public boolean completed() {
        return completed;
    }
}
