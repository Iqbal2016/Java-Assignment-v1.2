/**
 * 
 */
package com.iqbal;

/**
 * @author iqbal
 */
import java.util.*;

class MenuItem implements Comparable<MenuItem> {
    private String name;
    private String category;
    private double price;

    public MenuItem(String name, String category, double price) {
        this.name = name;
        this.category = category;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public double getPrice() {
        return price;
    }

    @Override
    public int compareTo(MenuItem other) {
        // Compare based on category, then price, then name
        int categoryComparison = this.category.compareTo(other.category);
        if (categoryComparison != 0) {
            return categoryComparison;
        }

        int priceComparison = Double.compare(this.price, other.price);
        if (priceComparison != 0) {
            return priceComparison;
        }

        return this.name.compareTo(other.name);
    }

    @Override
    public String toString() {
        return name + " (" + category + ") - $" + price;
    }
}

public class RestaurantMenu {
    public static void main(String[] args) {
        List<MenuItem> menu = readMenuItems();

        // Sort menu items naturally based on categories
        Collections.sort(menu);

        System.out.println("Menu items sorted by category:");
        printMenu(menu);

        // Sort menu items by price
        Collections.sort(menu, Comparator.comparingDouble(MenuItem::getPrice));

        System.out.println("\nMenu items sorted by price:");
        printMenu(menu);

        // Sort menu items by name
        Collections.sort(menu, Comparator.comparing(MenuItem::getName));

        System.out.println("\nMenu items sorted by name:");
        printMenu(menu);
    }

    private static List<MenuItem> readMenuItems() {
        List<MenuItem> menu = new ArrayList<>();

        // Read menu items from input (example)
        menu.add(new MenuItem("Hamburger", "Main Course", 9.99));
        menu.add(new MenuItem("Caesar Salad", "Appetizer", 6.99));
        menu.add(new MenuItem("Cheesecake", "Dessert", 4.99));
        menu.add(new MenuItem("Pizza", "Main Course", 12.99));
        menu.add(new MenuItem("French Fries", "Side Dish", 3.49));
        menu.add(new MenuItem("Ice Cream", "Dessert", 3.99));
        menu.add(new MenuItem("Bruschetta", "Appetizer", 7.99));
        menu.add(new MenuItem("Steak", "Main Course", 18.99));

        return menu;
    }

    private static void printMenu(List<MenuItem> menu) {
        for (MenuItem item : menu) {
            System.out.println(item);
        }
    }
}
