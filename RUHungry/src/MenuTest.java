import restaurant.MenuNode;
import restaurant.RUHungry;
public class MenuTest {
    public static void main(String[] args) {
        RUHungry ruHungry = new RUHungry();
        ruHungry.menu("menu.in");
        printRestaurant(ruHungry);
    }

    public static void printRestaurant(RUHungry ruHungry) {
        // Print the menu
        MenuNode[] menu = ruHungry.getMenu();
        String[] categories = ruHungry.getCategoryArray();
        System.out.println("Menu:");
        for (int i = 0; i < menu.length; i++) {
            System.out.println(categories[i] + ":");
            MenuNode current = menu[i];
            while (current != null) {
                System.out.println(current.getDish());
                current = current.getNextMenuNode();
            }
            System.out.println();
        }
    }
}
