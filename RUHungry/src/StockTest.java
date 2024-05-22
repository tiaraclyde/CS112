import restaurant.Ingredient;
import restaurant.RUHungry;
import restaurant.StockNode;

    public class StockTest {
        public static void main(String[] args) {
            // Creating Ingredients
            Ingredient tomato = new Ingredient(1, "Tomato", 100, 0.50);
            Ingredient lettuce = new Ingredient(2, "Lettuce", 50, 0.30);
            Ingredient cheese = new Ingredient(3, "Cheese", 75, 1.00);
    
            // Manually creating a linked list of StockNodes
            StockNode cheeseNode = new StockNode(cheese, null); // End of the list
            StockNode lettuceNode = new StockNode(lettuce, cheeseNode);
            StockNode tomatoNode = new StockNode(tomato, lettuceNode); // Head of the list
    
            // Assume we're starting with tomatoNode as the head of our stock list
            System.out.println("Initial Stock:");
            printStock(tomatoNode);
    
            // Example of adding a new ingredient and updating stock (manual linked list operations)
            Ingredient bread = new Ingredient(4, "Bread", 120, 0.25);
            StockNode breadNode = new StockNode(bread, tomatoNode); // Adding to the beginning
    
            System.out.println("\nUpdated Stock:");
            printStock(breadNode); // Now breadNode is the head of our list
        }
    
        // Helper method to traverse and print the stock list
        private static void printStock(StockNode head) {
            StockNode current = head;
            while (current != null) {
                Ingredient item = current.getIngredient();
                System.out.println(item.getName() + ": " + item.getStockLevel() + " units");
                current = current.getNextStockNode();
            }
        }
    }