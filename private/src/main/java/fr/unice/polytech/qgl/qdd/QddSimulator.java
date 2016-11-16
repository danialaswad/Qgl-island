package fr.unice.polytech.qgl.qdd;

import fr.unice.polytech.qgl.qdd.ai.ExplorerAI;
import fr.unice.polytech.qgl.qdd.enums.Resource;
import org.json.JSONObject;

/**
 * Created by danial on 04/12/15.
 */
public class QddSimulator {
    private QddExplorer explorer;
    private ExplorerAI explorerAI;
    private Action action;
    private int phase;
    private int actionCounter;
    JSONObject result;

    private static final String EXTRAS = "extras";
    private static final String DIRECTION = "direction";

    public QddSimulator(String context) {
        explorer = new QddExplorer(context);
        explorerAI = new ExplorerAI(explorer);
        actionCounter = 0;
        action = null;
        phase = 1;
    }

    /**
     * The next action to be executed will be returned
     * @return String
     */
    public String nextDecision() {
        actionCounter++;
        if(phase == 1) {
            action = explorerAI.computeAerialStrategy();
            return action.toJSON();
        }
        else {
            action = explorerAI.computeTerrestrialStrategy();
            return action.toJSON();
        }
    }

    /**
     * Answer will be analysed regarding the last type of action executed.
     * @param result
     */
    public void analyseAnswer(JSONObject result) {
        this.result = result;
        if ( "OK".equals( result.getString("status") ) ){
            explorer.decreaseBudget(result.getInt("cost"));

            if(phase == 1) {
        analyseAnswerAerial();
            }

            else {
                switch (action.getAction()) {
                    case Action.LAND: 
                         explorer.land(action.getStringParam("creek"), action.getIntParam("people"));
                         break;
                    case Action.MOVE_TO: 
                         explorer.move(action.getStringParam(DIRECTION));
                         break;
                    case Action.EXPLORE: 
                         explorer.explore(result.getJSONObject(EXTRAS).getJSONArray("resources"));
                         break;
                    case Action.EXPLOIT: 
                         explorer.exploit(Resource.valueOf(action.getStringParam("resource")),result.getJSONObject(EXTRAS).getInt("amount")); 
                         break;
                    case Action.SCOUT: 
                         explorer.scout(action.getStringParam(DIRECTION), result);
                         break;
                    case Action.TRANSFORM: 
                         explorer.transform(Resource.valueOf(result.getJSONObject(EXTRAS).getString("kind")),result.getJSONObject(EXTRAS).getInt("production"),action);
                         break;
                    default:break;
                }
            }
        }
    }

    private void analyseAnswerAerial()
    {
        switch (action.getAction()) {
            case Action.FLY:
                explorer.fly();
                break;
            case Action.ECHO:
                explorer.echo(action.getStringParam(DIRECTION), result);
                break;
            case Action.SCAN:
                explorer.scan(result);
                break;
            case Action.HEADING:
                explorer.turn(action.getStringParam(DIRECTION));
                break;
            case Action.LAND:
                explorer.land(action.getStringParam("creek"), action.getIntParam("people"));
                phase = 2;
                break;
            default:break;
        }

    }
}
