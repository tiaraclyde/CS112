package restaurant;
/**
 * Use this class to test your Menu method. 
 * This class takes in two arguments:
 * - args[0] is the menu input file
 * - args[1] is the output file
 * 
 * This class:
 * - Reads the input and output file names from args
 * - Instantiates a new RUHungry object
 * - Calls the menu() method 
 * - Sets standard output to the output and prints the restaurant
 *   to that file
 * 
 * To run: java -cp bin restaurant.Menu menu.in menu.out
 * 
 */
public class Menu {
    public static void main(String[] args) {

	// 1. Read input files
	// Option to hardcode these values if you don't want to use the command line arguments
	   
        String inputFile = args[0];
        String outputFile = args[1];
       
	
        // 2. Instantiate an RUHungry object
        RUHungry rh = new RUHungry();
        //System.out.println("Input File:" + inputFile);

	// 3. Call the menu() method to read the menu
        rh.menu(inputFile);
        rh.createStockHashTable("Stock.in"); //menu.in
        rh.updatePriceAndProfit();
       
        StdIn.setFile("order1.in");
       int n = StdIn.readInt();
         StdIn.readLine();

         for(int i = 0; i < n; i ++){
                 int amount = StdIn.readInt();
                StdIn.readChar();
                 String name = StdIn.readLine();
                 rh.order(name, amount);
        }

        StdIn.setFile("donate1.in");
         int z = StdIn.readInt();
          StdIn.readLine();

       for(int i = 0; i < z; i ++){
                  int amount = StdIn.readInt();
               StdIn.readChar();
     String name = StdIn.readLine();
               rh.donation(name, amount);
          }


       //  StdIn.setFile("restock1.in");
  //int x = StdIn.readInt();
    //    StdIn.readLine();
//
      //   for(int i = 0; i < x; i ++){
      //      int amount = StdIn.readInt();
         //     StdIn.readChar();
         //    String name = StdIn.readLine();
          //     rh.restock(name, amount);
         //}


	// 4. Set output file
	// Option to remove this line if you want to print directly to the screen
        StdOut.setFile(outputFile);

	// 5. Print restaurant
        rh.printRestaurant();
        
        
        
    }

}
