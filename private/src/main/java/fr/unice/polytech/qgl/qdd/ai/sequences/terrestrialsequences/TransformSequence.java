package fr.unice.polytech.qgl.qdd.ai.sequences.terrestrialsequences;

import fr.unice.polytech.qgl.qdd.Action;
import fr.unice.polytech.qgl.qdd.ai.CheckList;
import fr.unice.polytech.qgl.qdd.ai.sequences.Sequences;
import fr.unice.polytech.qgl.qdd.enums.Resource;
import fr.unice.polytech.qgl.qdd.navigation.Navigator;

import java.util.Map;

/**
 * Created by Ulysse on 23/02/16.
 */
public class TransformSequence extends Sequences {

    private Map<Resource, Integer> contractedManufacturedResources;

    public TransformSequence(Navigator nav, CheckList checkList , Map<Resource,Integer> contractedManufacturedResources) {
        super(nav, checkList);
        this.contractedManufacturedResources = contractedManufacturedResources;
    }

    @Override
    public Action execute() 
    {
        Resource resourceToManufacture = selectResourceToManufacture();
        int manufacturingQuantity = checkList.nbResourceToManufacture(resourceToManufacture);
        int nbresource1 = (int) (resourceToManufacture.getNbResource1() * manufacturingQuantity);
        if (resourceToManufacture.getResource2() != null){
            int nbresource2 = (int) (resourceToManufacture.getNbResource2() * manufacturingQuantity);
            return transform(resourceToManufacture.getResource1(),nbresource1,resourceToManufacture.getResource2(), nbresource2);
        }

        return transform(resourceToManufacture.getResource1(),nbresource1);
    }

    @Override
    public boolean completed() {
        return !checkList.enoughResourcesToManufacture();
    }

    public Resource selectResourceToManufacture(){

    for ( Map.Entry<Resource, Integer> theEntry : contractedManufacturedResources.entrySet() ){

        Resource resource = theEntry.getKey();

           if (checkList.manufacturedResourceIsReady(resource)){
               return resource;
           }
       }
        return null;
    }

}
