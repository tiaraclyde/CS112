package restaurant;

import java.util.ArrayList;
import java.util.HashSet;

/**
 * RUHungry is a fictitious restaurant. 
 * You will be running RUHungry for a day by seating guests, 
 * taking orders, donation requests and restocking the pantry as necessary.
 * 
 * Compiling and executing:
 * 1. use the run or debug function to run the driver and test your methods 
 * 
 * @author Mary Buist
 * @author Kushi Sharma
*/

public class RUHungry {
    
    /*
     * Instance variables
     */

    // Menu: two parallel arrays. The index in one corresponds to the same index in the other.
    private   String[] categoryVar; // array where containing the name of menu categories (e.g. Appetizer, Dessert).
    private MenuNode[] menuVar;     // array of lists of MenuNodes where each index is a category.
    
    // Stock: hashtable using chaining to resolve collisions.
    private StockNode[] stockVar;  // array of linked lists of StockNodes (use hashfunction to organize Nodes: id % stockVarSize)
    private int stockVarSize;

    // Transactions: orders, donations, restock transactions are recorded 
    private TransactionNode transactionVar; // refers to the first front node in linked list

    // Queue keeps track of parties that left the restaurant
    private Queue<Party> leftQueueVar;  

    // Tables Information - parallel arrays
    // If tableSeats[i] has 3 seats then parties with at most 3 people can sit at tables[i]
    private Party[] tables;      // Parties currently occupying the tables
    private   int[] tableSeats;  // The number of seats at each table

    /*
     * Default constructor
     */
    public RUHungry () {
        categoryVar    = null;
        menuVar        = null;
        stockVar       = null;
        stockVarSize   = 0;
        transactionVar = null;
        leftQueueVar   = null;
        tableSeats     = null;
        tables         = null;
    }

    /*
     * Getter and Setter methods
     */
    public MenuNode[] getMenu() { return menuVar; }
    public String[] getCategoryArray() { return categoryVar;}
    public StockNode[] getStockVar() { return stockVar; } 
    public TransactionNode getFrontTransactionNode() { return transactionVar; } 
    public TransactionNode resetFrontNode() {return transactionVar = null;} // method to reset the transactions for a new day
    public Queue<Party> getLeftQueueVar() { return leftQueueVar; } 
    public Party[] getTables() { return tables; }
    public int[] getTableSeats() { return tableSeats; }

    /*
     * Menu methods
     */

    /**
     * 
     * This method populates the two parallel arrays menuVar and categoryVar.
     * 
     * Each index of menuVar corresponds to the same index in categoryVar (a menu category like Appetizers).
     * If index 0 at categoryVar is Appetizers then menuVar at index 0 contains MenuNodes of appetizer dishes.
     * 
     * 1. read the input file:
     *      a) the first number corresponds to the number of categories (aka length of menuVar and categoryVar)
     *      b) the next line states the name of the category (populate CategoryVar as you read each category name)
     *      c) the next number represents how many dishes are in that category - this will be the size of the linked list in menuVar for this category
     *      d) the next line states the name of the dish
     *      e) the first number in the next line represents how many ingredient IDs there are
     *      f) the next few numbers (all in the 100s) are each the ingredient ID
     * 
     * 2. As you read through the input file:
     *      a) populate the categoryVar array
     *      b) populate menuVar depending on which index (aka which category) you are in
     *          i) make a dish object (with filled parameters -- don't worry about "price" and "profit" in the dish object for right now)
     *          ii) create menuNode and insert at the front of menuVar (NOTE! there will be multiple menuNodes in one index)
     * 
     * @param inputFile - use menu.in file which contains all the dishes
     */
    public void menu(String inputFile) {
    StdIn.setFile(inputFile); // opens the inputFile to be read
    int numofCategories = StdIn.readInt(); // the first number corresponds to the number of categories
    menuVar = new MenuNode[numofCategories];
    categoryVar = new String[numofCategories]; //states the name of the category
        for(int i = 0; i < numofCategories; i++){
            StdIn.readLine();
            categoryVar[i] = StdIn.readLine();
            int numD = StdIn.readInt();
        for(int j = 0; j < numD; j++){
            StdIn.readLine();
            String nameD = StdIn.readLine(); //name of the dish
                int ids[] = new int[StdIn.readInt()];
                for(int k = 0; k < ids.length; k++){
                    ids[k] = StdIn.readInt();
                }
                Dish dish = new Dish(categoryVar[i], nameD, ids); //at entre if the same node is null add it
                //whatever element youre adding goes to the front of the LL
                //set it first so we dont lose access to the rest of the LL
                MenuNode nmn = new MenuNode(dish, null);       //nmn= the new menu node
                
                if (menuVar[i] == null){
                    menuVar[i] = nmn; //new menu node
                    //the first box is a ll that extends out
                    //a hashtable is a buch of ll put together
                } else{
                //if the menu is null make it the new node
                //make the menu variable the first new node
                //else
                nmn.setNextMenuNode(menuVar[i]); //set the next node to the one im already adding to the list
                //so basically first save it then add to the list
                menuVar[i] = nmn;
            }}
        }
       
    }
    
    
               

    /** 
     * Find and return the MenuNode that contains the dish with dishName in the menuVar.
     * 
     *      ** GIVEN METHOD **
     *      ** DO NOT EDIT **
     * 
     * @param dishName - the name of the dish
     * @return the dish object corresponding to searched dish, null if dishName is not found.
     */

    public MenuNode findDish ( String dishName ) {

        MenuNode quandale = null;

        // Search all categories since we don't know which category dishName is at
        for ( int dingle = 0; dingle < menuVar.length; dingle++ ) {

            MenuNode ptr = menuVar[dingle]; // set ptr at the front (first menuNode)
            
            while ( ptr != null ) { // while loop that searches the LL of the category to find the itemOrdered
                if ( ptr.getDish().getName().equalsIgnoreCase(dishName) ) {
                    return ptr;
                } else{
                    ptr = ptr.getNextMenuNode();
                }
            }
        }
        return quandale;
    }

    /**
     * Find integer that corresponds to the index in menuVar and categoryVar arrays that has that category
     *              
     *      ** GIVEN METHOD **
     *      ** DO NOT EDIT **
     *
     * @param category - the category name
     * @return index of category in categoryVar
     */

    public int findCategoryIndex ( String category ) {
        int index = 0;
        for ( int i = 0; i < categoryVar.length; i++ ){
            if ( category.equalsIgnoreCase(categoryVar[i]) ) {
                index = i;
                break;
            }
        }
        return index;
    }

    /*
     * Stockroom methods
     */

    /**
     * PICK UP LINE OF THE METHOD:
     * *can I insert myself into your life? cuz you always help me sort 
     * out my problems and bring stability to my mine*
     * 
     * ***********
     * This method adds a StockNode into the stockVar hashtable.
     * 
     * 1. get the id of the given newNode and use a hash function to get the index at which the
     *    newNode is being inserted.
     * 
     * HASH FUNCTION: id % stockVarSize
     * 
     * 2. insert at the front of the linked list at the specific index
     * 
     * @param newNode - StockNode that needs to be inserted into StockVar
     */

    public void addStockNode ( StockNode newNode) { //check

        Ingredient ingredient = newNode.getIngredient();
        int ingredientID = ingredient.getID(); //stocknode from the ingredient
        int i = ingredientID % stockVarSize; //where it will be inserted
        // Insert front index in stockVar
        if (stockVar[i] == null) {//set newNode as the first node
            stockVar[i] = newNode;
        } else {
            newNode.setNextStockNode(stockVar[i]); // Set newNode's next pointer to the current head
            stockVar[i] = newNode;
        }
}
    /**
     * This method finds an ingredient from StockVar (given the ingredientID)
     * 
     * 1. find the node based upon the ingredient ID (you can go to the specific index using the hash function!)
     *      (a) this is an efficient search as it looks only at the linked list which the key hash to
     * 2. find and return the node
     *  
     * @param ingredientID - the ID of the ingredient
     * @return the StockNode corresponding to the ingredientID, null otherwise
     */
   
    public StockNode findStockNode (int ingredientID) { //check

	int i = ingredientID % stockVarSize; //find index using has
    StockNode ptr = stockVar[i];// get the first StockNode in the linked list 
    while (ptr != null) { //iterate
        if (ptr.getIngredient().getID() == ingredientID) { //found the ingredient
            return ptr;
        }
        ptr = ptr.getNextStockNode(); //keep going through LL
    }
        return null; // update the return value
    } 
    /**
     * This method is to find an ingredient from StockVar (given the ingredient name).
     * 
     *      ** GIVEN METHOD **
     *      ** DO NOT EDIT **
     * 
     * @param ingredientName - the name of the ingredient
     * @return the specific ingredient StockNode, null otherwise
     */

    public StockNode findStockNode (String ingredientName) { //dont change
        
        StockNode stockNode = null;
        
        for ( int i = 0; i < stockVar.length; i ++ ){
            
            StockNode ptr = stockVar[i];
            
            while ( ptr != null ){
                if ( ptr.getIngredient().getName().equalsIgnoreCase(ingredientName) ){
                    return ptr;
                } else {  
                    ptr = ptr.getNextStockNode();
                }
            }
        }
        return stockNode;
    }

    /**
     * This method updates the stock amount of an ingredient.
     * 
     * 1. you will be given the ingredientName **OR** the ingredientID:
     *      a) the ingredientName is NOT null: find the ingredient and add the given stock amount to the
     *         current stock amount
     *      b) the ingredientID is NOT -1: find the ingredient and add the given stock amount to the
     *         current stock amount
     * 
     * (FOR FUTURE USE) SOMETIMES THE STOCK AMOUNT TO ADD MAY BE NEGATIVE (TO REMOVE STOCK)
     * 
     * @param ingredientName - the name of the ingredient
     * @param ingredientID - the id of the ingredient
     * @param stockAmountToAdd - the amount to add to the current stock amount
     */
    
    public void updateStock (String ingredientName, int ingredientID, int stockAmountToAdd) { //check if (ingredientName != null) {
         if (ingredientName != null) {
            StockNode ingredientNode = findStockNode(ingredientName); // Find the ingredient by name
            if (ingredientNode != null) {
                int bluetie = ingredientNode.getIngredient().getStockLevel(); // Get current stock level
                ingredientNode.getIngredient().setStockLevel(bluetie + stockAmountToAdd); // Update stock level
            }
        } else if (ingredientID != -1) {
            StockNode ingredientNode = findStockNode(ingredientID); // Find the ingredient by ID
            if (ingredientNode != null) {
                int currentStock = ingredientNode.getIngredient().getStockLevel(); // Get current stock level
                ingredientNode.getIngredient().setStockLevel(currentStock + stockAmountToAdd); // Update stock level
            }
        }
    }

    
   
    /**
     * PICK UP LINE OF THE METHOD:
     * *are you a single ‘for’ loop? cuz i only have i’s for you*
     * 
     * ***********
     * This method goes over menuVar to update the price and profit of each dish,
     * using the stockVar hashtable to lookup for ingredient's costs.
     * 
     * 1. For each dish in menuVar, add up the cost for each ingredient (found in stockVar), 
     *    and multiply the total by 1.2 to get the final price.
     *      a) update the price of each dish
     *  HINT! --> you can use the methods you've previously made!
     * 
     * 2. Calculate the profit of each dish by getting the totalPrice of ingredients and subtracting from 
     *    the price of the dish itself.
     * 
     * @return void
     */

    public void updatePriceAndProfit() { //check
      for (int i = 0; i < menuVar.length; i++) {
            MenuNode c = menuVar[i]; //cat
            while (c != null) { // Iterate over each dish in the current category
                Dish d = c.getDish();
                double totalCost = calculate(d); // Calculate total cost
                double price = 1.2 * totalCost; // Update the price of the dish
                d.setPriceOfDish(price); // Applying a markup of 20%
                double profit = price - totalCost; // Calculate the profit of the dish
                d.setProfit(profit);
                c = c.getNextMenuNode(); // Move to the next dish in the category
            }
        }
    }
    private double calculate(Dish dish) {
        double totalCost = 0.0;
        int[] ingredientIDs = dish.getStockID();
        for (int ingredientID : ingredientIDs) { // Iterate over each ingredient in the dish
            StockNode ingredientNode = findStockNode(ingredientID);
            if (ingredientNode != null) {
                totalCost += ingredientNode.getIngredient().getCost();
            }
        }
        return totalCost;
    }
    

    /**
     * PICK UP LINE OF THE METHOD:
     * *are you a decimal? cuz the thought of you 
     * always floats in my head and the two of use would make double*
     * 
     * ************
     * This method initializes and populates stockVar which is a hashtable where each index contains a 
     * linked list with StockNodes.
     * 
     * 1. set and read the inputFile (stock.in):
     *      a) first integer (on line 1) is the size of StockVar *** update stockVarSize AND create the stockVar array ***
     *      b) first integer of next line represents the ingredientID
     *          i) example: 101 on line 2
     *      c) use StdIn.readChar() to get rid of the space between the id and the name
     *      d) the string that follows is the ingredient name (NOTE! --> there are spaces between certain strings)
     *          i) example: Lettuce
     *      e) the double on the next line corresponds to the ingredient's cost
     *          i) example: 3.12 on line 3
     *      f) the next integer is the stock amount for that ingredient
     *          i) example: 30 on line 3
     * 
     * 2. create a Ingredient object followed by a StockNode then add to stockVar
     *      HINT! --> you may use previous methods written to help you!
     * 
     * @param inputFile - the input file with the ingredients and all their information (stock.in)
     */

    public void createStockHashTable (String inputFile){
        
       StdIn.setFile(inputFile); // opens inputFile to be read by StdIn
        while (!StdIn.isEmpty()) {
        stockVarSize = StdIn.readInt();
        stockVar = new StockNode[stockVarSize]; // Create the stockVar array with the specified size
      while (!StdIn.isEmpty()) {
        StdIn.readLine();
        int ingredientID = StdIn.readInt();
        StdIn.readChar(); // Read and discard the space between ID and name
        String ingredientName = StdIn.readLine();
        double ingredientCost = StdIn.readDouble();
        StdIn.readChar();
        int stockAmount = StdIn.readInt();
        Ingredient ingredient = new Ingredient(ingredientID, ingredientName, stockAmount, ingredientCost);
        StockNode newNode = new StockNode(ingredient, null);
        addStockNode(newNode);
        } print();}}
        private void print() {
            System.out.println("Stock:");
            boolean firstIndex = true;
            for (int i = 0; i < stockVar.length; i++) {
                StockNode current = stockVar[i]; 
        
                if (!firstIndex) {
                    System.out.println();
                } else {
                    firstIndex = false;
                }
            System.out.println("Index " + i);
            while (current != null) {
                Ingredient ingredient = current.getIngredient();
                System.out.println(ingredient.getName() + " ID: " + ingredient.getID() + " Price: " + ingredient.getCost()+  
                " Stock Level: " + ingredient.getStockLevel() );
                current = current.getNextStockNode(); 
            }
            }
        }
    


    /*
     * Transaction methods
     */

    /**
     * This method adds a TransactionNode to the END of the transactions linked list.
     * The front of the list is transactionVar.
     *
     * 1. create a new TransactionNode with the TransactionData paramenter.
     * 2. add the TransactionNode at the end of the linked list transactionVar.
     * 
     * @param data - TransactionData node to be added to transactionVar
     */

    public void addTransactionNode ( TransactionData data ) { // method adds new transactionNode to the end of LL
        TransactionNode turkish = new TransactionNode(data, null); // Create a new TransactionNode with the given data
        if (transactionVar == null) { // If the transactionVar is null, meaning the list is empty
        transactionVar = turkish; // Set the new node as the first node
        } else {
        TransactionNode ptr = transactionVar;
        while (ptr.getNext() != null) {
            ptr = ptr.getNext(); // Iterate to the last node in the list
        }
        ptr.setNext(turkish); // Add the new node to the end of the list
    }
	
    }

    /**
     * PICK UP LINE OF THE METHOD:
     * *are you the break command? cuz everything else stops when I see you*
     * 
     * *************
     * This method checks if there's enough in stock to prepare a dish.
     * 
     * 1. use findDish() method to find the menuNode node for dishName
     * 
     * 2. retrieve the Dish, then traverse ingredient array within the Dish
     * 
     * 3. return boolean based on whether you can sell the dish or not
     * HINT! --> once you determine you can't sell the dish, break and return
     * 
     * @param dishName - String of dish that's being requested
     * @param numberOfDishes - int of how many of that dish is being ordered
     * @return boolean
     */

    public boolean checkDishAvailability (String dishName, int numberOfDishes){ //check
    MenuNode menu = findDish(dishName);
    if (menu == null) {
        return false;
    }
    Dish dish = menu.getDish();
    int[] dingle = dish.getStockID();
    for (int ingredientID : dingle) {
        StockNode stockNode = findStockNode(ingredientID);
        if (stockNode == null || stockNode.getIngredient().getStockLevel() < numberOfDishes) {
            return false;
        }
    }
    return true;
	
    }

    /**
    
     * @param dishName - String of dish that's been ordered
     * @param quantity - int of how many of that dish has been ordered
     */
     
     public void order(String dishName, int quantity) {
        MenuNode aunt = null; //found dish in the menu
        int ind = 0; //index of the category in the menu
        for (int i = 0; i < categoryVar.length; i++) {
            MenuNode dad = menuVar[i]; // Represents the current category
            while (dad != null) {
                // Check if the current dish matches the requested dish name
                if (dad.getDish().getName().equals(dishName)) {
                    aunt = dad; // Store the found dish
                    ind = i; // Store the index of the category
                    break; }
                dad = dad.getNextMenuNode(); // Move to the next dish in the category
            } }
        if (checkDishAvailability(dishName, quantity)) {
            double x = quantity * aunt.getDish().getProfit();
            TransactionData trData = new TransactionData("order", dishName, quantity, x, true);
            addTransactionNode(trData);
            // Update the stock for each ingredient of the dish
            for (int i = 0; i < aunt.getDish().getStockID().length; i++) {
                updateStock(null, aunt.getDish().getStockID()[i], (-quantity));
            }  } else if (!checkDishAvailability(dishName, quantity)) {
            // failed order
            TransactionData trData = new TransactionData("order", dishName, quantity, 0, false);
            addTransactionNode(trData);
            if (aunt.getNextMenuNode() == null) {
                recursive(menuVar[ind], dishName, quantity, aunt, ind);
            } else {
                recursive(aunt.getNextMenuNode(), dishName, quantity, aunt, ind);
            } }  }  
    // alternative dishes
    public void recursive(MenuNode mnNode, String dishName, int mom, MenuNode sister, int brother) {
        if (mnNode.getDish().getName().equals(dishName)) {
            return; // Exit the recursion if the dish is found
        } else if (checkDishAvailability(mnNode.getDish().getName(), mom)) {
            re(mnNode, dishName, mom, sister, brother); // Call re method if the dish is available
        } else if (!checkDishAvailability(mnNode.getDish().getName(), mom)) {
            ree(mnNode, dishName, mom, sister, brother); // Call ree method if the dish is not available
        }}
    // available alternative dish
    public void re(MenuNode mn, String dishName, int num, MenuNode tVar, int cTar) {
        double trouble = num * mn.getDish().getProfit();
        // successful order
        TransactionData transactionNodeToAdd = new TransactionData("order", mn.getDish().getName(), num, trouble, true);
        addTransactionNode(transactionNodeToAdd);
        int[] sad = mn.getDish().getStockID();
        for (int i = 0; i < sad.length; i++) {
            updateStock(null, sad[i], (-num));
        } }
    public void ree(MenuNode mn, String dishName, int quant, MenuNode tVar, int cTar) { // Method for handling unavailable alternative dish
        // failed order
        TransactionData stressedaf = new TransactionData("order", mn.getDish().getName(), quant, 0, false);
        addTransactionNode(stressedaf);
        if (mn.getNextMenuNode() == null) {
            recursive(menuVar[cTar], dishName, quant, tVar, cTar);
        } else {
            recursive(mn.getNextMenuNode(), dishName, quant, tVar, cTar);
        } }

    

      

    

    /**
     * This method returns the total profit for the day
     *
     * The profit is computed by traversing the transaction linked list (transactionVar) 
     * adding up all the profits for the day
     * 
     * @return profit - double value of the total profit for the day
     */

    public double profit() {
    
    double totalProfit = 0.0;
    TransactionNode ptr = transactionVar; //head
    while (ptr != null) {
        totalProfit += ptr.getData().getProfit(); // profit of each transaction + total profit
         ptr = ptr.getNext();
    }
    return totalProfit;
    }
   

    /**
     * This method simulates donation requests, successful or not.
     * 
     * 1. check whether the profit is > 50 and whether there's enough ingredients in stock.
     * 
     * 2. add transaction to transactionVar
     * 
     * @param ingredientName - String of ingredient that's been requested
     * @param quantity - int of how many of that ingredient has been ordered
     * @return void
     */

    public void donation (String ingredientName, int quantity){ //

        StockNode stock = findStockNode(ingredientName);
        Ingredient lois = stock.getIngredient();
    
        if (profit() > 50.0 && lois.getStockLevel() >= quantity) {
            TransactionData peter = new TransactionData("donation", ingredientName, quantity, 0.0, true);
            addTransactionNode(peter);
            updateStock(ingredientName, -1, -quantity);
        } else {
            TransactionData peter = new TransactionData("donation", ingredientName, quantity, 0.0, false);
            addTransactionNode(peter);
        }
    }
   
        
    
   /*double currentProfit = profit();
    StockNode ingredientNode = findStockNode(ingredientName);
    if (ingredientNode != null && currentProfit > 50) { // Check if the ingredient exists and profit is greater than 50
        int currentStock = ingredientNode.getIngredient().getStockLevel();
        if (currentStock >= quantity) { // Check if there's enough stock
            updateStock(ingredientName, -1, -quantity); // Deduct the quantity for donation
            TransactionData data = new TransactionData("donation", ingredientName, quantity, 0, true);
            addTransactionNode(data); // Add transaction node
        } else {
            TransactionData data = new TransactionData("donation", ingredientName, quantity, 0, false);
            addTransactionNode(data); // Add transaction node
        }
    } else {
        TransactionData data = new TransactionData("donation", ingredientName, quantity, 0, false);
        addTransactionNode(data); // Add transaction node
    }
}
    /**
     * 
     * @param ingredientName - ingredient that's been requested
     * @param quantity - how many of that ingredient needs to be ordered
     */

    public void restock (String ingredientName, int quantity){
         if (profit() >= findStockNode(ingredientName).getIngredient().getCost() * quantity){
            double cooked = -findStockNode(ingredientName).getIngredient().getCost() * quantity;
            TransactionData data = new TransactionData("restock", ingredientName, quantity, cooked, true);
            addTransactionNode(data);
            updateStock(ingredientName, -1, quantity);
            return;
        }
        TransactionData data = new TransactionData("restock", ingredientName, quantity, 0, false);
            addTransactionNode(data); 
    }
   /*
    * Seat guests/customers methods
    */

    /**
     * Method to populate tables (which is a 1D integer array) based upon input file
     * 
     * The input file is formatted as follows:
     * - an integer t contains the number of tables
     * - t lines containing number of rows * seats per row for each table
     * 
     * @param inputFile - tables1.in (contains all the tables in the RUHungry restaurant)
     * @return void (aka nothing)
     */

    public void createTables ( String inputFile ) { 

        StdIn.setFile(inputFile);
	
        int numberOfTables = StdIn.readInt();
        tableSeats = new int[numberOfTables];
        tables     = new Party[numberOfTables];

        for ( int t = 0; t < numberOfTables; t++ ) {
            tableSeats[t] = StdIn.readInt() * StdIn.readInt();
        }
    }

    /**
     * PICK UP LINE OF THE METHOD:
     * *are you a linked list? cuz nothing could stock up to you and 
     * you’re pretty queue(te)*
     * 
     * ***************
     * This method simulates seating guests at tables. You are guaranteed to be able to sit everyone from the waitingQueue eventually.
     * 
     * 1. initialize a tables array for party that are currently sitting
     * 
     * 2. initialize leftQueueVar a Party queue that represents the people that have left the restaurant
     * 
     * 3. while there are parties waiting to be sat:
     *      - Starting from index 0 (zero), seat the next party in the first available table that fits their party.
     *      - If there is no available table for the next party, kick a party out from the tables array:
     *          1. starting at index 0 (zero), find the first table big enough to hold the next party in line.
     *          2. remove the current party, add them to the leftQueueVar.
     *          3. seat the next party in line.
     *
     * Parallel arrays: tableSeats[i] refers to tables[i]. If tableSeats[i] is 3 then a party with 3 or less people can sit at tables[i].
     * tableSeats contains the number of seats per table.
     * tables contains the Party object currently at the table.
     * 
     * Note: After everyone has been seated (waitingQueue is empty), remove all the parties from tables and add then to the leftQueueVar.
     * 
     * @param waitingQueue - queue containing parties waiting to be seated (each element in queue is a Party <-- you are given this class!)
     */

    public void seatAllGuests ( Queue<Party> waitingQueue ) { //optional

	// WRITE YOUR CODE HERE

    }

    /**
     * Prints all states of the restaurant.
     * 
     * Edit this method if you wish.
     */
    public void printRestaurant() {
        // 1. Print out menu
        StdOut.println("Menu:");
        if (categoryVar != null) {
            for (int i=0; i < categoryVar.length; i++) {
                StdOut.print(categoryVar[i] + ":");
                StdOut.println();

                MenuNode ptr = menuVar[i];
                while (ptr != null) {
                    StdOut.print(ptr.getDish().getName() + "  Price: $" +
                    ((Math.round(ptr.getDish().getPriceOfDish() * 100.0)) / 100.0) + " Profit: $" + ((Math.round(ptr.getDish().getProfit() * 100.0)) / 100.0));
                    StdOut.println();

                    ptr = ptr.getNextMenuNode();
                }
                StdOut.println();
            }
        }
        else {
            StdOut.println("Empty - categoryVar is null.");
        }
        // 2. Print out stock
        StdOut.println("Stock:");
        if (stockVar != null) {
            for (int i=0; i < 10; i++) {
                StdOut.println("Index " + i);
                StockNode ptr = stockVar[i];
                while (ptr != null) {
                    StdOut.print(ptr.getIngredient().getName() + "  ID: " + ptr.getIngredient().getID() + " Price: " +
                    ((Math.round(ptr.getIngredient().getCost() *100.0)) / 100.0) + " Stock Level: " + ptr.getIngredient().getStockLevel());
                    StdOut.println();
    
                    ptr = ptr.getNextStockNode();
                }
    
                StdOut.println();
            }
        }
        else {
            StdOut.println("Empty - stockVar is null.");
        }
        // 3. Print out transactions
        StdOut.println("Transactions:");
        if (transactionVar != null) {
            TransactionNode ptr = transactionVar;
            int successes = 0;
            int failures = 0;
            while (ptr != null) {
                String type = ptr.getData().getType();
                String item = ptr.getData().getItem();
                int amount = ptr.getData().getAmount();
                double profit = ptr.getData().getProfit();
                boolean success = ptr.getData().getSuccess();
                if (success == true){
                    successes += 1;
                }
                else if (success == false){
                    failures += 1;
                }

                StdOut.println("Type: " + type + ", Name: " + item + ", Amount: " + amount + ", Profit: $" + ((Math.round(profit * 100.0)) / 100.0) + ", Was it a Success? " + success);
                
                ptr = ptr.getNext();
            }
            StdOut.println("Total number of successful transactions: " + successes);
            StdOut.println("Total number of unsuccessful transactions: " + failures);
            StdOut.println("Total profit remaining: $" + ((Math.round(profit() * 100.0)) / 100.0));
        }
        else {
            StdOut.println("Empty - transactionVar is null.");
        }
        // 4. Print out tables
        StdOut.println("Tables and Parties:");
        restaurant.Queue<Party> leftQueue = leftQueueVar;
        if (leftQueueVar != null) {
            StdOut.println(("Parties in order of leaving:"));
            int counter = 0;
            while (!leftQueue.isEmpty()) {
                Party removed = leftQueue.dequeue();
                counter += 1;
                StdOut.println(counter + ": " + removed.getName());
            }
        }
        else {
            StdOut.println("Empty -- leftQueueVar is empty");
        }
    }
}
