package fr.unice.polytech.qgl.qdd.ai;

import fr.unice.polytech.qgl.qdd.enums.Biome;
import fr.unice.polytech.qgl.qdd.enums.Resource;
import fr.unice.polytech.qgl.qdd.enums.Direction;
import fr.unice.polytech.qgl.qdd.QddExplorer;
import fr.unice.polytech.qgl.qdd.navigation.Navigator;
import fr.unice.polytech.qgl.qdd.navigation.Tile;

import java.util.*;

/**
 * Created by danial on 12/12/2015.
 */
public class CheckList {

    private Navigator nav;
    private QddExplorer explorer;
    private static final int ECHO_COVERAGE_QUOTA = 225;
    private static final double ECHO_BUDGET_QUOTA = 0.925;
    private static final int MISSION_ABORT_BUDGET_THRESHOLD = 200;
    private static final int AERIAL_BOUNDRY_BUFFER = 6;
    private static final int TERRESTRIAL_BOUNDRY_BUFFER = 9;
    private static final double SEARCH_FOR_BIOMES_QUOTA = 0.885;
    private static final int NB_TILE_WITH_INTERESTING_BIOMES = 81;


    public CheckList(Navigator nav, QddExplorer explorer) {
        this.nav = nav;
        this.explorer = explorer;
    }

    /*
        Checks on Map
     */

    /**
     * Checks if there's enough biomes associated with contract found
     * @return boolean
     */
    public boolean enoughPotentialTilesToLand(){
        boolean isEnough = false;
        EnumMap<Resource,Integer> counterResources = new EnumMap<>( Resource.class );
        Set<Resource> resourceSet = new HashSet<>();

        for (Resource r : explorer.getResourcesToCollect()) {
            if (!nav.map().getPotentiallyExploitableTiles().isEmpty()) {
                counterResources.put(r, 0);
                resourceSet.add(r);
                for (Tile t : nav.map().getPotentiallyExploitableTiles()) {
                    if (t.hasOrPotentiallyHasResourcesOfType(resourceSet)) {
                        resourceSet.stream().filter( counterResources::containsKey )
                                .forEach(resource -> counterResources.replace(resource, counterResources.get(resource) + 1));
                    }
                }
                resourceSet.clear();
            }
        }
        for ( Map.Entry<Resource, Integer> theEntry : counterResources.entrySet() ){

            Resource resource = theEntry.getKey();

            if (resource!= Resource.QUARTZ && counterResources.get(resource) > NB_TILE_WITH_INTERESTING_BIOMES)
            {
                isEnough = true;
            }
            else if (resource == Resource.QUARTZ && counterResources.get(resource) > NB_TILE_WITH_INTERESTING_BIOMES * 2.5)
            {
                isEnough = true;
            }
            else
            {
                return false;
            }
        }
        return isEnough;
    }

    /**
     * Checks if the current tile contains exploitable resource
     * @return boolean
     */
    public boolean exploitableResourceFound() {
        for (Resource resource : explorer.getResourcesToCollect()){
            if (nav.map().currentTile().hasResource(resource)){
                return true;
            }
        }
        return false;
    }

    /**
     * Checks number of resource left to manufacture
     * @param resource
     * @return boolean
     */
    public int nbResourceToManufacture(Resource resource){
        int amount = explorer.getManufacturedResources().get(resource);
        if(explorer.getResources().containsKey(resource)){
            amount =  amount > explorer.getResources().get(resource)?
                    amount - explorer.getResources().get(resource) : 0;
        }
        if((resource == Resource.PLANK) && (amount < 4))
        {
            return 4;
        }

        return amount;
    }

    /**
     * Checks that the tile if its explorable.
     * Return false if the tile has already been exploited/explored
     * @param tile
     * @return boolean
     */
    public boolean isExplorable(Tile tile) {
        if (tile.isNotExploited() && !tile.hasResources()) {
            if(tile.getBiomes().isEmpty()) {
                return true;
            }
            Set<Biome> preferredBiomes = new HashSet<>();
            explorer.getResourcesToCollect().forEach(resource -> preferredBiomes.addAll(resource.getBiomes()));
            return tile.getBiomes().stream().filter(preferredBiomes::contains).toArray().length >0;
        }
        return false;
    }

    /**
     * Checks if the current tiles are of ground type
     * @return boolean
     */
    public boolean isAboveGround(){
        if (nav.map().currentTile().isGround()) {
            return true;
        }
        for(Tile t: nav.finder().neighbouringTiles()) {
            if (t.isGround()){
                return  true;
            }
        }
        return false;
    }

    /**
     * Checks if creek is discovered in the map
     * @return boolean
     */
    public boolean foundCreek() { return !nav.map().getCreeks().isEmpty(); }

    /**
     * Checks if is close to the boundary in the aerial phase
     * @return boolean
     */
    public boolean isCloseToAerialBoundary(){
        return boundryCheck(AERIAL_BOUNDRY_BUFFER);
    }

    /**
     * Checks if is close to the boundary in the terrestrial phase
     * @return boolean
     */
    public boolean isCloseToTerrestrialBoundary(){
        return boundryCheck(TERRESTRIAL_BOUNDRY_BUFFER);
    }

    private boolean boundryCheck(int buffer){
        switch (nav.front()){
            case NORTH:return nav.map().y() > nav.map().height() - buffer - 1;
            case EAST: return nav.map().x() > nav.map().width() - buffer - 1;
            case SOUTH: return nav.map().y() < buffer;
            case WEST: return nav.map().x() < buffer;
            default:
        }
        return false;
    }


    /*
        Checks for echo
     */

    /**
     * Checks if coverage of the ground tile is enough
     * @return boolean
     */
    public boolean isEchoCoverageSufficient() {
        float groundTiles = nav.map().getGroundTileCount();

        return groundTiles > ECHO_COVERAGE_QUOTA || explorer.getBudget() < explorer.getInitialBudget()*ECHO_BUDGET_QUOTA;
    }

    /**
     * Checks if all the front tiles have been discovered
     * @return boolean
     */
    public boolean isTilesInFrontDiscovered(){
        return checkAllTilesDiscovered(nav.finder().allTiles(Direction.FRONT));
    }

    /**
     * Checks if all the left tiles have been discovered
     * @return boolean
     */
    public boolean isTilesAtLeftDiscovered(){
        return checkAllTilesDiscovered(nav.finder().allTiles(Direction.LEFT));
    }

    /**
     * Checks if all the right tiles have been discovered
     * @return boolean
     */
    public boolean isTilesAtRightDiscovered(){
        return checkAllTilesDiscovered(nav.finder().allTiles(Direction.RIGHT));
    }

    /**
     * Checks if all the tiles given have been discovered
     * @return boolean
     */
    private boolean checkAllTilesDiscovered(Set<Tile> tiles){
        tiles.removeAll(nav.finder().neighbouringTiles());
        for(Tile t: tiles) 
        {
           if (t.isGround())
              return true;
        }

        for(Tile t: tiles)
        {
           if (t.isUnknown())
              return false;
        }

        return true;
    }


    /*
        Checks on contract
     */

    /**
     * Checks if all the contracted resources has been collected
     * @return boolean
     */
    public boolean contractCompleted() {
        if (!explorer.getResourcesToCollect().isEmpty())
        {
           return false;
        }
        for (Resource resource : explorer.getContract().keySet()) {
            if (explorer.getResourceQuantity(resource) < explorer.getContract().get(resource)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Checks if primary resources are enough to make manufactured resource
     * @return boolean
     */
    public boolean enoughResourcesToManufacture(){
        if (explorer.getManufacturedResources().isEmpty() || explorer.getResources().isEmpty())
        {
            return false;
        }
        for (Resource resource : explorer.getManufacturedResources().keySet()){
            if (explorer.getResourceQuantity(resource) < explorer.getManufacturedResources().get(resource) && manufacturedResourceIsReady(resource) )
            {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if the given resource is ready to be manufactured
     * @param resource
     * @return boolean
     */
    public boolean manufacturedResourceIsReady(Resource resource){
        if(nbResourceToManufacture(resource) == 0) {
            return false;
        }
        int amount = nbResourceToManufacture(resource);
        Resource resource1 = resource.getResource1();
        int nbResource1 = (int) (resource.getNbResource1() * amount);
        if (resource.getResource2() != null) {
            Resource resource2 = resource.getResource2();
            int nbResource2 = (int) (resource.getNbResource2() * amount);
            if ((explorer.getResourceQuantity(resource1) >= nbResource1) && (explorer.getResourceQuantity(resource2) >= nbResource2)){
                return true;
            }
        }
        else {
            if( explorer.getResourceQuantity(resource1) >= nbResource1){
                return true;
            }
        }
        return false;
    }

    /**
     * Checks the resources to be collected
     * @return set of uncollected resources
     */
    public Set<Resource> getResourcesToCollect() {
        return explorer.getResourcesToCollect();
    }


    /*
        Checks on budget
     */

    /**
     * Checks if the current budget is superior to the budget threshold
     * @return boolean
     */
    public boolean doWeHaveEnoughBudget(){
        return explorer.getBudget() < MISSION_ABORT_BUDGET_THRESHOLD;
    }

    /**
     * Checks if the current budget is superior to the island scanning budget quota
     * @return boolean
     */
    public boolean haveEnoughtBudgetToScanTheIsland(){
        return explorer.getBudget() > explorer.getInitialBudget() * SEARCH_FOR_BIOMES_QUOTA;
    }

    /**
     * Checks if the current budget is superior to the biomes searching budget quota
     * @return boolean
     */
    public boolean haveEnoughBudgetToSearchForMoreBiomes(){
        return explorer.getBudget() > explorer.getInitialBudget() * SEARCH_FOR_BIOMES_QUOTA;
    }
}
