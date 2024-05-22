import restaurant.StdIn;

public void teststock() {
    // Open the stock.in file for reading
    StdIn.setFile("stock.in");

    // Read the size of StockVar
    int stockVarSize = StdIn.readInt();

    // Create the StockVar hashtable
    createStockVar(stockVarSize);

    // Print the stockroom
    System.out.println("Stockroom:");
    printStockRoom();

    // Print the updated menu
    System.out.println("\nUpdated Menu:");
    printMenu();
}

