package fr.unice.polytech.qgl.qdd.navigation;


import fr.unice.polytech.qgl.qdd.enums.Biome;
import fr.unice.polytech.qgl.qdd.enums.Resource;

import java.util.*;

/**
 * Created by danial on 10/12/15.
 */
public class Tile {
    static final String UNKNOWN = "UNKNOWN", GROUND = "GROUND", SEA = "SEA";
    private String type = null;
    private int altitude;
    private boolean explored = false;
    private boolean exploited = false;
    private boolean beenHere= false;
    private boolean calledForScan = false;
    private List<Biome> biomes = new ArrayList<>();
    private List<String> creeks = new ArrayList<>();
    private EnumMap<Resource, String> resources = new EnumMap<>( Resource.class );
    private TileListener listener;

    public boolean isUnscanned() {
        return biomes.isEmpty();
    }

    public boolean hasResource(Resource resource) {
        return resources.containsKey(resource);
    }

    public boolean hasResources() {
        return !resources.isEmpty();
    }

    public boolean hasUniqueBiome(Biome uniqueBiomeType) {
        if (biomes.isEmpty())
        { 
              return false; 
        }
        return biomes.stream().filter(biome -> biome == uniqueBiomeType).count() == biomes.size();
    }

    public boolean hasOrPotentiallyHasResourcesOfType(Set<Resource> resourcesToCollect) {
        if (resources.keySet().stream().filter(resourcesToCollect::contains).count() > 0) {
            return true;
        }/*
        if (biomes.isEmpty()){
            return false;
        }*/
        Set<Biome> preferredBiomes = new HashSet<>();
        resourcesToCollect.forEach(resource -> preferredBiomes.addAll(resource.getBiomes()));

        return biomes.stream().filter(preferredBiomes::contains).count() > 0;
    }

    public boolean hasResourceOfType(Set<Resource> resourcesToCollect){
        return resources.keySet().stream().filter(resource -> resourcesToCollect.contains(resource)).count() > 0;
    }

    public void removeResource(Resource resource) {
        resources.remove(resource);
    }

    public Tile(TileListener listener) {
        this.listener = listener;
        setType(Tile.UNKNOWN);
    }

    /*=====================
        Getters and Setters
     ======================*/

    public void setSea() {
        setType(SEA);
    }

    public void setGround() {
        setType(GROUND);
    }

    public void setUnknown(){
        setType(UNKNOWN);
    }

    public boolean isUnknown() {
        return type.equals(Tile.UNKNOWN);
    }

    public boolean isGround() {
        return type.equals(Tile.GROUND);
    }

    public boolean isSea() { return type.equals(Tile.SEA); }

    public String getType() {
        return type;
    }

    public boolean isExplored() {
        return explored;
    }

    public void setExplored() {
        explored = true;
    }

    public void setExploited(boolean exploited) {
        this.exploited = exploited;
    }

    public boolean isNotExploited() {
        return !exploited;
    }

    public boolean haveNotBeenHere(){
        return !beenHere;
    }

    public void setBeenHere(){
        beenHere = true;
    }

    public boolean isNotCalledForScan() { return !calledForScan; }

    public void setCalledForScan(){calledForScan = true;}

    public List<Biome> getBiomes() {
        return biomes;
    }

    public void addBiomes(List<Biome> biomes) {
        this.biomes.addAll(biomes);
        if(this.isGround()) {
            //do not change the type
        }
        else if (!this.isSea() && !this.hasUniqueBiome(Biome.OCEAN)){
            this.setType(Tile.GROUND);
        }
        else if (this.hasUniqueBiome(Biome.OCEAN) && this.isUnknown()) {
            this.setType(Tile.SEA);
        }
        listener.biomeDiscovered(this);
    }

    public Map<Resource, String> getResources() {
        return resources;
    }

    public void addResources(Map<Resource, String> resources) {
        for ( Map.Entry<Resource, String> theEntry : resources.entrySet() ){

            Resource resource = theEntry.getKey();
            if(this.resources.containsKey(resource)) {
                this.resources.put(resource, this.resources.get(resource) + resources.get(resource));
            }
            else{
                this.resources.put(resource, resources.get(resource));
            }
        }
    }

    public List<String> getCreeks() {
        return creeks;
    }

    public void addCreeks(List<String> creeks) {
        this.creeks.addAll(creeks);
        listener.creekDiscovered(this);
    }

    public int getAltitude(){
        return altitude;
    }

    public void setAltitude(int altitude){
        this.altitude = altitude;
    }

    /*===================
        Private methods
     ===================*/
    private void setType(String type) {
        String previousType = this.type;
        this.type = type;
        listener.typeDiscovered(this, previousType, type);
    }
}
