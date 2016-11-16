package fr.unice.polytech.qgl.qdd;

import org.json.JSONObject;

/**
 * Created by danial on 11/12/15.
 */
public class Action {
    public static final String
        //Phase 1
        HEADING = "heading", FLY = "fly", ECHO = "echo", SCAN = "scan",
        //Phase 1 & 2
        STOP = "stop", LAND = "land",
        //Phase 2
        MOVE_TO = "move_to", SCOUT = "scout", GLIMPSE = "glimpse", EXPLORE = "explore", EXPLOIT = "exploit", TRANSFORM = "transform";

    private String theAction;
    private JSONObject parameters;

    public Action(String action) {
        this.theAction = action;
        parameters = new JSONObject();
    }

    /**
     * The last/current action executed
     * @return String
     */
    public String getAction() {
        return theAction;
    }

    /**
     * Return a String from a given key in a Json object
     * @param param key
     * @return String
     */
    public String getStringParam(String param) {
        return parameters.getString(param);
    }

    /**
     * Return an Integer from a given key in a Json object
     * @param param key
     * @return int
     */
    public int getIntParam(String param) {
        return parameters.getInt(param);
    }

    /**
     * Adding a String with key associated in a Json object
     * @param key
     * @param value
     * @return an action
     */
    public Action addParameter(String key, String value){
        parameters.put(key, value);
        return this;
    }

    /**
     * Adding an Integer with key associated in a Json object
     * @param key
     * @param value
     * @return an action
     */
    public Action addParameter(String key, int value){
        parameters.put(key, value);
        return this;
    }

    /**
     * Parsing a Json object to String format
     * @return String
     */
    public String toJSON() {
        JSONObject action = new JSONObject();
        action.put("action", this.theAction);
        if(parameters.length() != 0) {
            action.put("parameters", this.parameters);
        }

        return action.toString();
    }
}
