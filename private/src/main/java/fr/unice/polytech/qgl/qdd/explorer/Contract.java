package fr.unice.polytech.qgl.qdd.explorer;

import fr.unice.polytech.qgl.qdd.enums.Resource;

import java.util.EnumMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by danialaswad on 07/03/2016.
 */
public class Contract {
    private static final int BUDGET_TRESHOLD = 10000;
    private EnumMap<Resource, Integer> contract = new EnumMap<>( Resource.class );

    public Contract(){
        contract = new EnumMap<>( Resource.class );
    }

    /**
     * To add a resource in the contract
     * @param resource Enum Resource
     * @param amount the amount
     */
    public void addRessource( Resource resource, int amount){
        this.contract.put(resource,amount);
    }

    /**
     * Get the contract
     * @return EnumMap of contract
     */
    public EnumMap<Resource,Integer> getContract(){
        return contract;
    }

    /**
     * Get all the resources in the contract
     * @return set of resources
     */
    public Set<Resource> getKeyset(){
        return contract.keySet();
    }

    /**
     * Verify the given resource is present in the contract
     * @param resource
     * @return true if exist
     */
    public boolean keyExist(Resource resource){
        return contract.containsKey(resource);
    }

    /**
     * Filters the manufactured resource from the contract
     * @return a set of manufactured resource
     */
    public Map<Resource,Integer> getManufacturedResources(){
        EnumMap<Resource, Integer> manufacturedResources = new EnumMap<>( Resource.class );

        for ( Map.Entry<Resource, Integer> theEntry : contract.entrySet() ){

            Resource resource = theEntry.getKey();
            if (resource.getIsManufactured() ){
                manufacturedResources.put(resource,contract.get(resource));
            }
        }
        return manufacturedResources;
    }

    /**
     * This method filters the primary resources that is no longer need to be collected to create
     * a manufacture resource given in the argument
     *
     * @param resourceCollected resources that we already have collected
     * @param manufacturedResource manufactured resource
     * @return resource(s) that need(s) to be collected
     */
    public Set<Resource> resourceToCollectForManufacturing(Contract resourceCollected, Resource manufacturedResource){
        int resourceAmount, manufacturedAmount;
        Set<Resource> resourcesToCollect = new HashSet<>();
        resourceAmount = getResourceQuantity(manufacturedResource.getResource1());
        // Calculate the amount of resource left to be collected
        manufacturedAmount = getResourceQuantity(manufacturedResource)
                - resourceCollected.getResourceQuantity(manufacturedResource);

        if ((int)(manufacturedResource.getNbResource1() * manufacturedAmount) + resourceAmount
                > resourceCollected.getResourceQuantity(manufacturedResource.getResource1())){
            resourcesToCollect.add(manufacturedResource.getResource1());
        }

        if (manufacturedResource.getResource2() != null){
            resourceAmount = getResourceQuantity(manufacturedResource.getResource2());
            if ((int)(manufacturedResource.getNbResource2() * manufacturedAmount) + resourceAmount
                    > resourceCollected.getResourceQuantity(manufacturedResource.getResource2())){
                resourcesToCollect.add(manufacturedResource.getResource2());
            }
        }
        return resourcesToCollect;
    }

    /**
     * Filters the resources that have not yet meet the demand (quantity in the contract)
     * @param resourceCollected resource already collected
     * @return a set of resource needed to be collected
     */
    public Set<Resource> getResourcesToCollect(Contract resourceCollected) {
        Set<Resource> resourcesToCollect = new HashSet<>();
        this.contract.keySet().stream().filter(resource -> !resource.getIsManufactured() && (getResourceQuantity(resource) > resourceCollected.getResourceQuantity(resource)))
                .forEach( resourcesToCollect::add );

        for ( Map.Entry<Resource, Integer> theEntry : this.contract.entrySet() ){
            Resource resource = theEntry.getKey();
            if (resource.getIsManufactured() && (getResourceQuantity(resource) > resourceCollected.getResourceQuantity(resource))){
                resourcesToCollect.addAll(resourceToCollectForManufacturing(resourceCollected, resource));
            }
        }
        return resourcesToCollect;
    }

    /**
     * Returns the quantity of the resource given in the argument
     * 0 if the resource don't exist
     * @param resource type of resource
     * @return quantity
     */
    public int getResourceQuantity(Resource resource) {
        return contract.containsKey(resource)? contract.get(resource) : 0;
    }

    /**
     * Filters the contract according to the strategy
     * If there are more manufactured resource in the contract than the primary resource, the bot
     * will eliminate all the primary contracted resources
     * If the budget given is less than 10000 and primary resource(s) is present in the contract,
     * the bot will only collect non manufactured resources.
     * @param budget
     */
    public void contractFiltrationProcess(int budget){

        if (contract.keySet().stream().filter(resource -> !resource.getIsManufactured()).count()
                < getManufacturedResources().keySet().stream().count()){
            contract.keySet().removeIf(resource -> !resource.getIsManufactured());
            return;
        }

        if (budget <= BUDGET_TRESHOLD && contract.keySet().stream().filter(resource1 -> !resource1.getIsManufactured()).count()>0){
            contract.keySet().removeIf(resource -> resource.getIsManufactured());
            return;
        }
    }

}
