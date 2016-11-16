package fr.unice.polytech.qgl.qdd.ai.sequences.terrestrialsequences;

import fr.unice.polytech.qgl.qdd.Action;
import fr.unice.polytech.qgl.qdd.ai.CheckList;
import fr.unice.polytech.qgl.qdd.ai.sequences.Sequences;
import fr.unice.polytech.qgl.qdd.navigation.Navigator;

/**
 * Created by vincent on 21/12/15.
 */
public class GlimpseSequence extends Sequences {
    int counter;
    int vitesse;

    public GlimpseSequence(Navigator nav, CheckList list, int vitesse){
        super(nav,list);
        this.counter=0;
        this.vitesse=vitesse;
    }

    @Override
    public Action execute(){
        Action action;
        switch(counter){
            case 0 : 
        action = glimpse( nav.front(),vitesse); 
        break;
            case 1 : 
        action = glimpse( nav.right(),vitesse); 
        break;
            case 2 : 
        action = glimpse( nav.left(),vitesse); 
        break;
            default : 
        action = move(nav.front()); 
        break;
        }
        counter++;
        return action;
    }

    @Override
    public boolean completed(){
        return counter>(vitesse+2);
    }
}
