/*
 * Siu Hin Nicholas Cheng
 * 11656445
 * Recitation 03
 */

public class OrganismNode {
    private String name;
    private boolean isPlant;
    private boolean isHerbivore;
    private boolean isCarnivore;
    private OrganismNode left;
    private OrganismNode middle;
    private OrganismNode right;
    private OrganismNode[] children = {left, middle, right};

    /**
     * Default constructor.
     */
    public OrganismNode(){
        name = "";
        isPlant = false;
        isCarnivore = false;
        isHerbivore = false;
    }

    /**
     * Constructor with name parameter;
     */
    public OrganismNode(String name){
        this.name = name;
        isPlant = false;
        isCarnivore = false;
        isHerbivore = false;
    }
    /**
     * Constructor with all parameters.
     */
    public OrganismNode(String name, boolean isPlant, boolean isHerbivore, boolean isCarnivore){
        this.name = name;
        this.isPlant = isPlant;
        this.isHerbivore = isHerbivore;
        this.isCarnivore = isCarnivore;
    }

    /**
     * Seters and Getters for all private variables.
     */
    public void setName(String name){ this.name = name; };
    public String getName(){ return name; }
    public void setIsPlant(boolean isPlant){ this.isPlant = isPlant; };
    public boolean getIsPlant(){ return isPlant; }
    public void setIsHerbivore(boolean isHerbivore){ this.isHerbivore = isHerbivore; };
    public boolean getIsHerbivore(){ return isHerbivore; }
    public void setIsCarnivore(boolean isCarnivore){ this.isCarnivore = isCarnivore; };
    public boolean getIsCarnivore(){ return isCarnivore; }
    public OrganismNode getLeft(){ return left; }
    public void setLeft(OrganismNode left){ this.left = left; }
    public OrganismNode getMiddle(){ return middle; }
    public void setMiddle(OrganismNode middle){ this.middle = middle; }
    public OrganismNode getRight(){ return right; }
    public void setRight(OrganismNode right){ this.right = right; }
    public OrganismNode[] getChildren(){ return children; }
    public void setChildren(OrganismNode[] children){ this.children = children; }

    public void shiftChildren(){
        children[0] = left;
        children[1] = middle;
        children[2] = null;
    }

    /**
     * Adds preyNode as prey to this node.
     * @param preyNode The OrganismNode to be added as prey of this organism.
     * @custom.precondition The node is not a plan, has at least one of the three child node positions available, and has a type of prey that correctly corresponds to its diet.
     * @custom.postcondition Either an exception is thrown, or preyNode is added as a child of this node.
     * @throws PositionNotAvailableException Thrown if there is no available child position for preyNode to be added.
     * @throws IsPlantException Thrown if this node is a plant node.
     * @throws DietMismatchException Thrown if preyNode does not correctly correspond to the diet of this animal.
     */
    public void addPrey(OrganismNode preyNode) throws PositionNotAvailableException, IsPlantException, DietMismatchException {
        isOpen();
        if(isPlant)
            throw new IsPlantException("Plants cannot have prey.");
        if((preyNode.isPlant && !isHerbivore) || (!preyNode.isPlant && !isCarnivore))
            throw new DietMismatchException("Prey does not match diet.");
        if(left == null){
            left = preyNode;
            children[0] = left;
        } else if(middle == null){
            middle = preyNode;
            children[1] = middle;
        } else {
            right = preyNode;
            children[2] = right;
        }
    }

    /**
     * Checks if the OrganismNode has any open prey positions.
     * @return True if there is an open slot, throws an error otherwise.
     * @throws PositionNotAvailableException Thrown if there are no available prey positions.
     */
    public boolean isOpen() throws PositionNotAvailableException {
        if(left == null || middle == null || right == null)
            return true;
        throw new PositionNotAvailableException("No available prey positions.");
    }

    /**
     * Checks if the OrganismNode is a leaf.
     * @return True if OrganismNode is a leaf, false otherwise.
     */
    public boolean isLeaf(){
        return left == null && middle == null && right == null;
    }

    /**
     * Checks if the OrganismNode contains a child with the specified name.
     * @param name Specified child to be searched for.
     * @return True if there is a child with the specified name, false otherwise.
     */
    public boolean hasChild(String name){
        if(isLeaf())
            return false;
        for(OrganismNode child: children)
            if(child != null && child.getName().equals(name))
                return true;
        return false;
    }

    /**
     * Returns the child with the specified name.
     * @param name Child to be searched for.
     * @return Child with the specified name.
     */
    public OrganismNode findChild(String name){
        for(OrganismNode child: children){
            if(child != null && child.getName().equals(name))
                return child;
        }
        throw new IllegalArgumentException("Child not found");
    }

    /**
     * String version of the OrganismNode.
     * @return name of OrganismNode.
     */
    public String toString(){
        return name;
    }
}
