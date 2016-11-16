package fr.unice.polytech.qgl.qdd.ai.sequences.aerialsequences;

import fr.unice.polytech.qgl.qdd.Action;
import fr.unice.polytech.qgl.qdd.ai.CheckList;
import fr.unice.polytech.qgl.qdd.ai.sequences.Sequences;
import fr.unice.polytech.qgl.qdd.navigation.Navigator;

/**
 * Created by danial on 12/13/2015.
 */
public class EchoForGroundSequence extends Sequences {
    private int counter;
    private static int maxIterations = 3;

    public EchoForGroundSequence(Navigator nav, CheckList checkList){
        super(nav, checkList);
        counter = 0;
    }

    @Override
    public Action execute() {
        Action action;
        if (!checkList.isTilesAtLeftDiscovered()){
            action = echo( nav.left());
        }
        else if (!checkList.isTilesAtRightDiscovered()){
            action = echo( nav.right());
        }
        else if (!checkList.isTilesInFrontDiscovered()){
            action = echo( nav.front());
        }
        else if (checkList.isCloseToAerialBoundary()){
            action = chooseTurningDirection();
        }
        else {
            action = fly();
        }

        counter++;
        return action;
    }

    @Override
    public boolean completed() {
        return counter > maxIterations;
    }
}
