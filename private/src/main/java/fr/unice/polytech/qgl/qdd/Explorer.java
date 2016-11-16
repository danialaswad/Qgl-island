package fr.unice.polytech.qgl.qdd;

import org.json.*;
import eu.ace_design.island.bot.*;


/**
 * Created by danial on 27/11/15.
 */
public class Explorer implements IExplorerRaid 
{
    private QddSimulator qdd;

    @Override
    public void initialize(String context) 
    {
        qdd = new QddSimulator(context);
    }

    @Override
    public String takeDecision() 
    {
        return qdd.nextDecision();
    }

    @Override    
    public void acknowledgeResults( String result )
    {
        JSONObject resultMessage = new JSONObject(result);
        qdd.analyseAnswer(resultMessage);
    }
}
