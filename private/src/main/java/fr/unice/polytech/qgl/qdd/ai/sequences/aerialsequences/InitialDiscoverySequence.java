package fr.unice.polytech.qgl.qdd.ai.sequences.aerialsequences;

import fr.unice.polytech.qgl.qdd.Action;
import fr.unice.polytech.qgl.qdd.ai.CheckList;
import fr.unice.polytech.qgl.qdd.ai.sequences.Sequences;
import fr.unice.polytech.qgl.qdd.navigation.Navigator;

/**
 * Created by danial on 12/13/2015.
 */
public class InitialDiscoverySequence extends Sequences {
    private int counter = 1;

    public InitialDiscoverySequence(Navigator nav, CheckList checkList) {
        super(nav, checkList);
    }

    @Override
    public Action execute() {
        switch(counter) {
            case 1: 
        counter++; 
        return echo(nav.front()); //First echo; echo front
            case 2: 
        counter++; 
        return echo(nav.right()); //Second echo; echo right
            case 3: 
        counter++; 
        if(!nav.map().isInitialized()) 
        { 
            return echo(nav.left()); 
        }
            default: 
        return fly();
        }
    }

    @Override
    public boolean completed() {
        return nav.map().isInitialized();
    }
}
