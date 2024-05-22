import restaurant.Ingredient;
import restaurant.RUHungry;
import restaurant.StockNode;

public class UpdateStockTest {
    public static void main(String[] args) {
        RUHungry ruHungry = new RUHungry();
        // Add some test stock nodes
        StockNode node1 = new StockNode(new Ingredient(1, "Ingredient1", 10, 0.5), null);
        ruHungry.addStockNode(node1);
        
        // Update stock by ingredient name
        ruHungry.updateStock("Ingredient1", -1, 5); // Add 5 units of Ingredient1

        // Update stock by ingredient ID
        ruHungry.updateStock(null, 1, -2); // Remove 2 units of Ingredient1
        
        printRestaurant(ruHungry);
    }

    public static void printRestaurant(RUHungry ruHungry) {
        // Print the stock after update
        StockNode[] stock = ruHungry.getStockVar();
        System.out.println("Stock after update:");
        for (int i = 0; i < stock.length; i++) {
            System.out.println("Index " + i + ":");
            StockNode current = stock[i];
            while (current != null) {
                System.out.println(current.getIngredient());
                current = current.getNextStockNode();
            }
            System.out.println();
        }
    }
}