package fr.unice.polytech.qgl.qdd.enums;

import java.util.HashSet;
import java.util.Set;

public enum Resource {

    WOOD(false,Biome.MANGROVE, Biome.TEMPERATE_RAIN_FOREST, Biome.TEMPERATE_DECIDUOUS_FOREST, Biome.TROPICAL_RAIN_FOREST, Biome.TROPICAL_SEASONAL_FOREST),
    FISH(false,Biome.OCEAN, Biome.LAKE),
    FLOWER(false,Biome.MANGROVE, Biome.ALPINE, Biome.GLACIER),
    FUR(false,Biome.GRASSLAND, Biome.TEMPERATE_RAIN_FOREST, Biome.TUNDRA, Biome.SHRUBLAND),
    QUARTZ(false,Biome.BEACH, Biome.TEMPERATE_DESERT),
    FRUITS(false,Biome.TROPICAL_RAIN_FOREST, Biome.TROPICAL_SEASONAL_FOREST),
    SUGAR_CANE(false,Biome.TROPICAL_RAIN_FOREST, Biome.TROPICAL_SEASONAL_FOREST),
    ORE(false,Biome.TEMPERATE_DESERT, Biome.ALPINE, Biome.GLACIER, Biome.SNOW, Biome.TAIGA, Biome.TUNDRA),
    
    GLASS(true , 10 , Resource.QUARTZ , 5 , Resource.WOOD),
    INGOT(true , 5 , Resource.ORE , 5 , Resource.WOOD),
    LEATHER(true , 3 , Resource.FUR ),
    PLANK(true , 0.25 , Resource.WOOD), //1 WOOD donne 4 PLANK
    RUM(true , 10 , Resource.SUGAR_CANE , 1 , Resource.FRUITS);

    private boolean isManufactured;

    private Resource resource1;
    private double nbResource1;

    private Resource resource2;
    private double nbResource2;

    private Set<Biome> biomes = new HashSet<>();

    Resource( boolean isManufactured , Biome... args){
        this.isManufactured = isManufactured;
        for( Biome arg : args )
            biomes.add( arg );
    }

    Resource( boolean isManufactured , double nbResource1 , Resource resource1  ){
        this.isManufactured = isManufactured;
        this.resource1 = resource1;
        this.nbResource1 = nbResource1;
    }


    Resource( boolean isManufactured , double nbResource1 , Resource resource1 , double nbResource2 , Resource resource2  ){
        this.isManufactured = isManufactured;
        this.resource1 = resource1;
        this.nbResource1 = nbResource1;
        this.resource2 = resource2;
        this.nbResource2 = nbResource2;
    }

    public Set<Biome> getBiomes(){
        return biomes;
    }

    public boolean getIsManufactured(){
        return this.isManufactured;
    }

    public Resource getResource1(){
        return this.resource1;
    }

    public double getNbResource1(){
        return this.nbResource1;
    }

    public Resource getResource2(){
        return this.resource2;
    }

    public double getNbResource2(){
        return this.nbResource2;
    }
}
