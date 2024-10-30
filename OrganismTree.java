/*
 * Siu Hin Nicholas Cheng
 * 11656445
 * Recitation 03
 */

import java.util.ArrayList;

public class OrganismTree {
    private final OrganismNode root;
    private OrganismNode cursor;
    private final ArrayList<OrganismNode> path;

    /**
     * Constructor that creates a new OrganismTree with apexPredator as the root.
     * @param apexPredator The apex predator of the food pyramid.
     * @custom.postcondition An OrganismTree object is made, with apexPredator representing the apex predator.
     */
    public OrganismTree(OrganismNode apexPredator){
        root = apexPredator;
        cursor = root;
        path = new ArrayList<>();
    }

    /**
     * Moves the cursor back to the root of the tree.
     * @custom.postcondition cursor now references the root of the tree.
     */
    public void cursorReset(){
        cursor = root;
    }

    /**
     * Getter method for cursor.
     * @return cursor.
     */
    public OrganismNode getCursor(){
        return cursor;
    }
    /**
     * Moves cursor to one of the cursor's children.
     * @param name The name of the node to be moved to.
     * @custom.precondition name references a valid name of one of cursor's children.
     * @custom.postcondition Either an exception is thrown, or cursor now points ot the node whose name is references by name, and cursor now points to a child of the original cursor reference.
     * @throws IllegalArgumentException Thrown if name does not reference a direct, valid child of cursor.
     */
    public void moveCursor(String name) throws  IllegalArgumentException {
        cursor = cursor.findChild(name);
    }

    /**
     * Validates if the cursor is currently not a plant and has a position.
     * @return True if it passes both exceptions.
     * @throws PositionNotAvailableException Thrown if there is no open prey positions.
     */
    public boolean validatePrey() throws PositionNotAvailableException {
        if(cursor.getIsPlant()){
            System.out.println("ERROR: The cursor is at a plant node. Plants cannot be predators.");
            return false;
        }
        try{
            cursor.isOpen();
        } catch (PositionNotAvailableException e){
            System.out.println("ERROR: There is no more room for more prey for this predator.");
            return false;
        }
        return true;
    }
    /**
     * Returns a String including the organism at cursor and all its possible prey.
     * @custom.postcondition cursor has not moved.
     * @return A String containing the name of the cursor, and all the cursor's possible prey.
     * @throws IsPlantException Thrown if cursor currently references a plant object.
     */
    public String listPrey() throws IsPlantException {
        String str = "";
        int len;
        if(cursor.getIsPlant() || cursor.isLeaf())
            throw new IsPlantException("Cursor has no prey.");
        for(OrganismNode children: cursor.getChildren()){
            if(children == null)
                break;
            str += children + ", ";
        }
        if((len = str.length()) >= 2)
            str = str.substring(0, len - 2);
        return str;
    }

    /**
     * Returns a String containing the path of organisms that leads from the apex predator to the organism at cursor.
     * @return
     */
    public String listFoodChain(){
        StringBuilder str = new StringBuilder();
        findPath(root);
        for(int i = path.size(); i > 0; i--){
            str.append(path.remove(0));
            if(i != 1)
               str.append(" -> ");
        }
        path.clear();
        return str.toString();
    }

    /**
     * Finds a path from the root to the cursor.
     * @param pointer The root of the tree.
     * @return True if there is a path, false otherwise.
     */
    public boolean findPath(OrganismNode pointer){
        if(pointer == null)
            return false;
        path.add(pointer);
        if(pointer == cursor) {
            return true;
        }
        for (OrganismNode child : pointer.getChildren()) {
            if (findPath(child)){
                return true;
            }
        }
        path.remove(path.size()-1);
        return false;
    }

    /**
     * Prints out a layered, indented tree by performing a preorder traversal starting at cursor.
     * @custom.postcondition Neither cursor and root have not moved.
     */
    public void printOrganismTree(){
        System.out.println(treeToString(cursor, 0));
    }

    /**
     * Creates a String representation of the OrganismTree.
     * @param pointer The root of the tree to be converted.
     * @param level The height of which the pointer is in.
     * @return
     */
    public String treeToString(OrganismNode pointer, int level){
        String str = "";
        for(int i = level; i > 0; i--){
            str += "\t";
        }
        if (pointer.getIsPlant()) {
            str += "- " + pointer.getName();
        } else {
            str += "|- " + pointer.getName();
        }
        for (OrganismNode children : pointer.getChildren()) {
            if(children != null){
                str += "\n" + treeToString(children, level + 1);
            }
        }
        return str;
    }

    /**
     * Returns a list of all plants currently at cursor and beneath cursor in the food pyramid.
     * @return A String containing a list of all the plants in the food pyramid.
     * @custom.postcondition Neither cursor nor root has moved.
     */
    public String listAllPlants(){
        String str = "";
        findPlants(cursor);
        for(OrganismNode plants: path){
            str += plants;
            if(plants != path.get(path.size()-1))
                str += ", ";
        }
        path.clear();
        return str;
    }

    /**
     * Finds all the plants under pointer.
     * @param pointer Starting organism to check for plant supporters.
     */
    public void findPlants(OrganismNode pointer){
            if (pointer != null){
                if(pointer.getIsPlant()) {
                    path.add(pointer);
                } else{
                    for (OrganismNode children : pointer.getChildren()) {
                        findPlants(children);
                    }
                }
            }
    }

    /**
     * Creates a new animal node with a specific name and a diet and adds it as a child of the cursor node.
     * @param name The name of the child node.
     * @param isHerbivore Value depending on whether the animal consumes plants.
     * @param isCarnivore value depending on whether the animal consumes other animals.
     * @custom.precondition name does not reference another direct child of the cursor. There is an available position for a child at cursor.
     * @custom.postcondition Either an exception is thrown or a new animal node named name is added as a child of the cursor with a specific diet. The cursor does not move.
     * @throws IllegalArgumentException Thrown if name references an exact name with one of its would-be siblings.
     * @throws PositionNotAvailableException Thrown if there is no available child position for a new node to be added.
     */
    public void addAnimalChild(String name, boolean isHerbivore, boolean isCarnivore) throws IllegalArgumentException, PositionNotAvailableException {
        if(cursor.hasChild(name))
            throw new IllegalArgumentException("Cursor already has a child with the same name.");
        try {
            cursor.addPrey(new OrganismNode(name, false, isHerbivore, isCarnivore));
        } catch (IsPlantException e) {
            System.out.println("Cursor is a plant.");
        } catch (DietMismatchException e) {
            System.out.println("ERROR: This prey cannot be added as it does not match the diet of the predator.");
        }
    }

    /**
     * Creates a new plant node with a specific name and adds it as a child of the cursor node.
     * @param name The name of the child node.
     * @throws IllegalArgumentException Thrown if name references an exact name with one of its would-be siblings.
     * @throws PositionNotAvailableException Thrown if there is no available child position for a new node to be added.
     */
    public void addPlantChild(String name) throws IllegalArgumentException, PositionNotAvailableException {
        if(cursor.hasChild(name))
            throw new IllegalArgumentException("Cursor already has a child with the same name.");
        try {
            cursor.addPrey(new OrganismNode(name, true, false, false));
        } catch (IsPlantException e) {
            System.out.println("Cursor is a plant.");
        } catch (DietMismatchException e) {
            System.out.println("Animal has a mismatched diet.");
        }
    }

    /**
     * Removes the child node of cursor with the specified name, and properly shifts the deleted node's other siblings if necessary. If the deleted node has any descendants, those nodes are deleted as well.
     * @param name The name of the node to be deleted.
     * @custom.precondition name references a direct child of cursor.
     * @custom.postcondition The child node of the cursor with the specified name and all of its descendants will be removed. The other children will be shifted as necessary. Cursor has not moved.
     * @throws IllegalArgumentException Thrown if name does not reference a direct child of the cursor.
     */
    public void removeChild(String name) throws IllegalArgumentException {
        OrganismNode remove = cursor.findChild(name);
        if(remove == cursor.getLeft()) {
            cursor.setLeft(cursor.getMiddle());
            cursor.setMiddle(cursor.getRight());
        } else if(remove == cursor.getMiddle()){
            cursor.setMiddle(cursor.getRight());
        }
        cursor.setRight(null);
        cursor.shiftChildren();
    }

    /**
     * Getter for root.
     * @return Root of the tree.
     */
    public OrganismNode getRoot(){ return root; }
}
