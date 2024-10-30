/*
 * Siu Hin Nicholas Cheng
 * 11656445
 * Recitation 03
 */

import java.util.Scanner;

public class FoodPyramid {

    /**
     * Default constructor.
     */
    public FoodPyramid() {

    }

    /**
     * The main method runs the program and prompts the menu below, and it allows various functions to be performed on your constructed ternary tree.
     *
     * @param args Console parameters.
     */
    public static void main(String[] args) throws PositionNotAvailableException, IsPlantException, DietMismatchException {
        OrganismTree tree;
        Scanner scan = new Scanner(System.in);
        String name;
        boolean isCarnivore = false, isHerbivore = false, quit = false;

        System.out.println("What is the name of the apex predator?: ");
        tree = new OrganismTree(new OrganismNode(scan.nextLine()));
        System.out.println("Is the organism an herbivore / a carnivore / an omnivore? (H / C / O): ");
        switch(scan.nextLine().toUpperCase()){
            case "H":
                tree.getRoot().setIsHerbivore(true);
                break;
            case "C":
                tree.getRoot().setIsCarnivore(true);
                break;
            case "O":
                tree.getRoot().setIsHerbivore(true);
                tree.getRoot().setIsCarnivore(true);
                break;
        }
        System.out.println("Constructing food pyramid. . .");
        while(!quit){
            System.out.println("\nMenu:\n\n(PC) - Create New Plant Child\n(AC) - Create New Animal Child\n(RC) - Remove Child\n(P) - Print Out Cursor's Prey\n(C) - Print Out Food Chain\n(F) - Print Out Food Pyramid At Cursor\n(LP) - List All Plants Supporting Cursor\n(R) - Reset Cursor to Root\n(M) - Move Cursor to Child\n(Q) - Quit\n\nPlease select an option: ");
            switch(scan.nextLine().toUpperCase()){
                case "PC":
                    if(!tree.validatePrey()){
                        quit = true;
                        break;
                    }
                    if(!tree.getCursor().getIsHerbivore()){
                        System.out.println("ERROR: This prey cannot be added as it does not match the diet of the predator.");
                        quit = true;
                        break;
                    }
                    if(!quit){
                        System.out.println("What is the name of the organism?: ");
                        name = scan.nextLine();
                        try{
                            tree.addPlantChild(name);
                        } catch(IllegalArgumentException e){
                            System.out.println("ERROR: This prey already exists for this predator.");
                        }
                        System.out.println("A(n) " + name + " has successfully been added as prey for the " + tree.getCursor().getName() + "!");
                    }
                    break;
                case "AC":
                    if(!tree.validatePrey()){
                        quit = true;
                        break;
                    }
                    if(!tree.getCursor().getIsCarnivore()){
                        System.out.println("ERROR: This prey cannot be added as it does not match the diet of the predator");
                        quit = true;
                        break;
                    }
                    if(!quit){
                        System.out.println("What is the name of the organism?: ");
                        name = scan.nextLine();
                        try{
                            tree.getCursor().hasChild(name);
                        } catch(IllegalArgumentException e){
                            System.out.println("ERROR: This prey already exists for this predator.");
                        }
                        System.out.println("Is the organism an herbivore / a carnivore / an omnivore? (H / C / O): ");
                        switch (scan.nextLine().toUpperCase()) {
                            case "H":
                                isHerbivore = true;
                                break;
                            case "C":
                                isCarnivore = true;
                                break;
                            case "O":
                                isHerbivore = true;
                                isCarnivore = true;
                                break;
                        }
                        try{
                            tree.addAnimalChild(name, isHerbivore, isCarnivore);
                        } catch(IllegalArgumentException e){
                            System.out.println("ERROR: This prey already exists for this predator.");
                        }
                        isHerbivore = false;
                        isCarnivore = false;
                        System.out.println("A(n) " + name + " has successfully been added as prey for the " + tree.getCursor().getName() + "!");
                    }
                    break;
                case "RC":
                    System.out.println("What is the name of the organism to be removed?: ");
                    name = scan.nextLine();
                    tree.removeChild(name);
                    System.out.println("A(n) " + name + " has been successfully removed as prey for the " + tree.getCursor().getName() + "!");
                    break;
                case "P":
                    System.out.println(tree.getCursor() + " -> " + tree.listPrey());
                    break;
                case "C":
                    System.out.println(tree.listFoodChain());
                    break;
                case "F":
                    tree.printOrganismTree();
                    break;
                case "LP":
                    System.out.println(tree.listAllPlants());
                    break;
                case "R":
                    tree.cursorReset();
                    System.out.println("Cursor successfully reset to root!");
                    break;
                case "M":
                    System.out.println("Move to?: ");
                    name = scan.nextLine();
                    if(!tree.getCursor().hasChild(name))
                        System.out.println("ERROR: This prey does not exist for this predator.");
                    tree.moveCursor(name);
                    System.out.println("Cursor successfully moved to " + name + "!");
                    break;
                case "Q":
                    System.out.println("Program terminating successfully...");
                    quit = true;
                    break;
            }
        }
    }
}