package fr.unice.polytech.qgl.qdd.ai;

import fr.unice.polytech.qgl.qdd.Action;
import fr.unice.polytech.qgl.qdd.ai.sequences.Sequences;
import fr.unice.polytech.qgl.qdd.ai.sequences.commonsequences.*;
import fr.unice.polytech.qgl.qdd.ai.sequences.aerialsequences.*;
import fr.unice.polytech.qgl.qdd.ai.sequences.terrestrialsequences.*;
import fr.unice.polytech.qgl.qdd.navigation.Navigator;
import fr.unice.polytech.qgl.qdd.QddExplorer;



/**
 * Created by danial on 12/11/2015.
 */
public class ExplorerAI {
    private QddExplorer explorer;
    private Navigator nav;
    private CheckList checkList;
    private Sequences activeSequence;

    public ExplorerAI(QddExplorer explorer){
        this.explorer = explorer;
        nav = explorer.getNavigator();
        checkList = new CheckList(nav, explorer);
        activeSequence = null;
    }

    /**
     * Aerial type sequence
     * Aerial sequence contains only the aerial type actions
     * @return Action
     */
    public Action computeAerialStrategy() {
        activeSequence = chooseSequenceAerial();

        return activeSequence.execute();
    }

    /**
     * Aerial type sequence
     * Aerial sequence contains only the aerial type actions
     * @return Action
     */
    public Action computeTerrestrialStrategy() {
        activeSequence = chooseSequenceTerrestrial();
        return activeSequence.execute();
    }


    private Sequences chooseSequenceAerial() {

        if (checkList.doWeHaveEnoughBudget())
    {
        return new StopSequence(nav,checkList); 
    }

        if(activeSequence == null) 
    { 
        return new InitialDiscoverySequence(nav, checkList); 
    }

        else if(!activeSequence.completed()) 
    { 
        return activeSequence; 
    }

        else{
            if(!checkList.isEchoCoverageSufficient()) {
                return new EchoForGroundSequence(nav, checkList);
            }
            else if(!checkList.isAboveGround()) {
                return new FlyToUnscannedGroundSequence(nav, checkList);
            }
            else if(!checkList.foundCreek()) {
                return new ScanSequence(nav, checkList);
            }
            else if (checkList.haveEnoughBudgetToSearchForMoreBiomes() && !checkList.enoughPotentialTilesToLand()){
                return new ScanForBiomeSequence(nav,checkList);
            }
            else{
                if(checkList.foundCreek()) {
                    return new LandSequence(nav, checkList, 1);
                }
                return new StopSequence(nav, checkList);
            }
        }

    }

    private Sequences chooseSequenceTerrestrial() {
        if (checkList.doWeHaveEnoughBudget()) {
            return new StopSequence(nav,checkList);
        }
        else if(!activeSequence.completed()) {
            return activeSequence;
        }
        else{
            if (checkList.enoughResourcesToManufacture()){
                return new TransformSequence(nav,checkList,explorer.getManufacturedResources());
            }
            else if(!checkList.contractCompleted()) {
                if(checkList.exploitableResourceFound()) {
                    return new ExploitSequence(nav, checkList, explorer.getContract(), explorer.getResources());
                }
                return new ScoutSequence(nav, checkList);
            }
            else{
                return new StopSequence(nav, checkList);
            }
        }
    }

    public CheckList getCheckList(){
        return checkList;
    }
}
