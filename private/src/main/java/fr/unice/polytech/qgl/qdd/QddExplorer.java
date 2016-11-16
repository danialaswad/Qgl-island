package fr.unice.polytech.qgl.qdd;

import fr.unice.polytech.qgl.qdd.enums.Biome;
import fr.unice.polytech.qgl.qdd.enums.Resource;
import fr.unice.polytech.qgl.qdd.explorer.Contract;
import fr.unice.polytech.qgl.qdd.navigation.Compass;
import fr.unice.polytech.qgl.qdd.navigation.IslandMap;
import fr.unice.polytech.qgl.qdd.navigation.Navigator;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.*;

/**
 * Created by danial on 04/12/15.
 */
public class QddExplorer {
    private int budget;
    private int initialBudget;
    private int men;
    private Contract resources;
    private Contract contract;
    private Navigator nav;

    private static final String EXTRA = "extras";

    public QddExplorer(String context){
        initializeExplorer(context);
    }
    
    /*======================================================================
        Updater methods called after actions are successfully acknowledged
     =======================================================================*/
    
    /**
     * To update the map after an echo action
     * @param direction the direction of the echo
     * @param result the result of the echo
     */
    public void echo(String direction, JSONObject result) {
        int range = result.getJSONObject(EXTRA).getInt("range");
        if(!nav.map().isInitialized()) {
            nav.map().initializeMapThroughEcho(Compass.fromString(direction),range);
        }
        else{
            if ( "GROUND".equals( result.getJSONObject(EXTRA).getString("found") ) ) {
                getMap().updateMapThroughEcho(true, range,  Compass.fromString(direction));
            }
            else {
                getMap().updateMapThroughEcho(false, range,  Compass.fromString(direction));
            }
        }
    }
    
    /**
     * To update the IslandMap after a scan
     * @param result the result of the scan
     */
    public void scan(JSONObject result) {
        JSONArray biomesJson = result.getJSONObject(EXTRA).getJSONArray("biomes");
        JSONArray creeksJson = result.getJSONObject(EXTRA).getJSONArray("creeks");
        List<String> creeks = new ArrayList<>();
        for(int i=0; i<creeksJson.length(); i++) {
            creeks.add(creeksJson.getString(i));
        }

        List<Biome> biomes = new ArrayList<>();
        for (int i=0; i<biomesJson.length(); i++) {
            biomes.add(Biome.valueOf(biomesJson.getString(i)));
        }
        Set<Biome> preferredBiomes = new HashSet<>();
        contract.getKeyset().stream().filter(resource1 -> !resource1.getIsManufactured())
                .forEach(resource -> preferredBiomes.addAll(resource.getBiomes()));
        for (Resource resource : contract.getKeyset()){
            if (resource.getIsManufactured()){
                preferredBiomes.addAll(resource.getResource1().getBiomes());
                if (resource.getResource2()!=null){
                    preferredBiomes.addAll(resource.getResource2().getBiomes());
                }
            }
        }
        boolean potentiallyExploitable = biomes.stream().filter(preferredBiomes::contains).count() > 0;
        getMap().updateMapThroughScan(biomes, potentiallyExploitable);
        getMap().updateMapWithCreeks(creeks);
    }
    
    /**
     * Update the navigator after a fly action
     */

    public void fly(){
        nav.move().fly();
    }

    /**
     * Update the navigator after a heading action
     * @param direction
     */
    public void turn(String direction){
        nav.move().turn( Compass.fromString(direction));
    }

    /**
     * Update the navigator after a land action
     * @param creekId the id of the designated creek
     * @param numberOfPeople the number of men used
     */
    public void land(String creekId, int numberOfPeople){
        sendMen(numberOfPeople);
        getMap().setCurrentTile(creekId);
    }
    
    /**
     * Update the map and the navigator after a move action
     * @param direction the direction of the move action
     */
    public void move(String direction){
        nav.move().walk(Compass.fromString(direction));
        nav.map().currentTile().setBeenHere();
    }
    
    /**
     * Update the map and the navigator after an explore action
     * @param resourcesJSON the resource on the tile explored
     */
    public void explore(JSONArray resourcesJSON){

        EnumMap<Resource, String> aResources = new EnumMap<>( Resource.class );

        for(int i = 0; i < resourcesJSON.length(); i++) {
            JSONObject resource = resourcesJSON.getJSONObject(i);
            aResources.put(Resource.valueOf(resource.getString("resource")), resource.getString("amount"));
        }

        getMap().updateMapWithResources(aResources);
    }
    
    /**
     * Update the map after a scout action
     * @param direction the direction to scout
     * @param result the result of the scout action
     */
    public void scout(String direction,JSONObject result){
        JSONArray resourcesJson = result.getJSONObject(EXTRA).getJSONArray("resources");

        int altitudeInt = result.getJSONObject(EXTRA).getInt("altitude");
        String altitude = String.valueOf(altitudeInt);

        EnumMap<Resource,String> resourceMap = new EnumMap<>( Resource.class );
        Set<Resource> resourceSet = new HashSet<>();
        for (int i = 0; i < resourcesJson.length(); i++){
            resourceMap.put(Resource.valueOf(resourcesJson.getString(i)),altitude);
            resourceSet.add(Resource.valueOf(resourcesJson.getString(i)));
        }
        
        boolean exploitable = resourceSet.stream().filter( getResourcesToCollect()::contains).count() > 0;

        getMap().updateMapThroughScout(resourceMap,altitudeInt,direction,exploitable);
    }
    
    /**
     * Update the navigator after a exploit action
     * @param resource the resource exploited
     * @param amount the amount exploited
     */
    public void exploit(Resource resource, int amount) {
        getMap().updateMapAfterExploit(resource);
        resources.addRessource(resource, resources.keyExist(resource)?
                resources.getResourceQuantity(resource) + amount : amount);

        getMap().currentTile().getResources().remove(resource);

        boolean fullyExploited = true;
        for (Resource resourceContracted : getResourcesToCollect()){
            if (getMap().currentTile().getResources().containsKey(resourceContracted)) {
                fullyExploited = false;
            }
        }
        getMap().currentTile().setExploited(fullyExploited);
        getMap().updatePotentiallyExploitableTiles(getResourcesToCollect());
    }
    
    /**
     * Update the navigator after a transform action
     * @param resourceManufactured the resource to manufacture
     * @param amountManufactured ....
     * @param action the transform action data
     */
    public void transform(Resource resourceManufactured, int amountManufactured, Action action){
        Set<Resource> primaryResources = new HashSet<>();
        int amount1 = resources.getResourceQuantity(resourceManufactured.getResource1())
                - action.getIntParam(resourceManufactured.getResource1().toString());
        resources.addRessource(resourceManufactured.getResource1(),amount1);
        primaryResources.add(resourceManufactured.getResource1());
        if (resourceManufactured.getResource2()!=null){
            int amount2 = resources.getResourceQuantity(resourceManufactured.getResource2())
                    - action.getIntParam(resourceManufactured.getResource2().toString());
            resources.addRessource(resourceManufactured.getResource2(),amount2);
            primaryResources.add(resourceManufactured.getResource2());
        }
        resources.addRessource(resourceManufactured, resources.keyExist(resourceManufactured)?
                resources.getResourceQuantity(resourceManufactured) + amountManufactured : amountManufactured);

        if (contract.getResourceQuantity(resourceManufactured) > resources.getResourceQuantity(resourceManufactured)) {
            getMap().reUpdatePotentiallyExploitableTiles(primaryResources);
        }
    }

    /*====================
        Private methods
    =====================*/
    private void initializeExplorer(String context) {

        JSONObject initialValues = new JSONObject(context);
        resources = new Contract();

        Resource resource;
        nav = new Navigator( Compass.fromString(initialValues.getString("heading")));
        budget = initialValues.getInt("budget");
        initialBudget = budget;
        men = initialValues.getInt("men");

        contract = new Contract();
        JSONArray contractList = initialValues.getJSONArray("contracts");
        for (int i = 0; i < contractList.length(); i++ )
        {
            resource = Resource.valueOf(contractList.getJSONObject(i).getString("resource"));
            int amount = contractList.getJSONObject(i).getInt("amount");

            contract.addRessource(resource, amount);
        }

        contract.contractFiltrationProcess(budget);

    }

    /*============================
        Getters and decrementers
     =============================*/

    public Set<Resource> getResourcesToCollect() {
        return contract.getResourcesToCollect(resources);
    }

    public IslandMap getMap() {
        return nav.map();
    }

    public Navigator getNavigator() {
        return nav;
    }

    public int getBudget() {
        return budget;
    }

    public int getInitialBudget(){return initialBudget;}

    public void decreaseBudget(int budget) {
        this.budget -= budget;
    }

    public int getMen() {
        return men;
    }

    public void sendMen(int men) {
        this.men -= men;
    }

    public Map<Resource, Integer> getResources() {
        return resources.getContract();
    }

    public Map<Resource,Integer> getContract(){
        return contract.getContract();
    }

    public Map<Resource,Integer> getManufacturedResources(){
        return contract.getManufacturedResources();
    }

    public int getResourceQuantity(Resource resource) {
        return resources.getResourceQuantity(resource);
    }
}
