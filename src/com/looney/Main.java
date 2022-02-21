package com.looney;

import java.util.Map;

public class Main {
    private static StockList stockList = new StockList();

    public static void main(String[] args) {
	    StockItem temp = new StockItem("bread", 0.86, 100);
        stockList.addStock(temp);

        temp = new StockItem("cheese", 1.10, 7);
        stockList.addStock(temp);

        temp = new StockItem("fish", 12.50, 2);
        stockList.addStock(temp);

        temp = new StockItem("candies", 2.0, 10);
        stockList.addStock(temp);

        temp = new StockItem("potato", 0.50, 200);
        stockList.addStock(temp);
        temp = new StockItem("sweats", 0.45, 7);
        stockList.addStock(temp);

        temp = new StockItem("noodles", 2.95, 4);
        stockList.addStock(temp);

        temp = new StockItem("juice", 2.50, 36);
        stockList.addStock(temp);

        temp = new StockItem("carrot", 0.99, 35);
        stockList.addStock(temp);

        temp = new StockItem("onion", 2.40, 80);
        stockList.addStock(temp);

        temp = new StockItem("cucumber", 0.76, 40);
        stockList.addStock(temp);

        System.out.println(stockList);

        for(String s: stockList.Items().keySet()) {
            System.out.println(s);
        }

        Basket timsBasket = new Basket("Tim");

        sellItem(timsBasket, "onion", 10);
        System.out.println(timsBasket);

        sellItem(timsBasket, "fish", 10);
        System.out.println(timsBasket);

        if(sellItem(timsBasket, "onion", 1) != 1) {
            System.out.println("There are no more onion in stock");
        }

        sellItem(timsBasket, "juice", 6);
        sellItem(timsBasket, "sandwich", 12);
        sellItem(timsBasket, "bread", 1);

        Basket basket = new Basket("customer");
        sellItem(basket, "cucumber", 100);
        sellItem(basket, "juice", 5);
        removeItem(basket, "sandwich", 1);
        System.out.println(basket);

        removeItem(timsBasket, "juice", 1);
        System.out.println("juice removed: " + removeItem(timsBasket, "juice", 1));  // should not remove any

        System.out.println(timsBasket);

        removeItem(timsBasket, "juice", 1);
        System.out.println(timsBasket);

        System.out.println("\nDisplay stock list before and after checkout");
        System.out.println(basket);
        System.out.println(stockList);
        checkOut(basket);
        System.out.println(basket);
        System.out.println(stockList);

        StockItem cheese = stockList.Items().get("cheese");
        if(cheese != null) {
            cheese.adjustStock(2000);
        }
        if(cheese != null) {
            stockList.get("cheese").adjustStock(-1000);
        }

        System.out.println(stockList);
        System.out.println(timsBasket);
        checkOut(timsBasket);
        System.out.println(timsBasket);
    }

    public static int sellItem(Basket basket, String item, int quantity) {
        // retrieve the item from stock list
        StockItem stockItem = stockList.get(item);
        if(stockItem == null) {
            System.out.println("We don't sell " + item);
            return 0;
        }
        if(stockList.reserveStock(item, quantity) != 0) {
            return basket.addToBasket(stockItem, quantity);
        }
        return 0;
    }

    public static int removeItem(Basket basket, String item, int quantity) {
        // retrieve the item from stock list
        StockItem stockItem = stockList.get(item);
        if(stockItem == null) {
            System.out.println("We don't sell " + item);
            return 0;
        }
        if(basket.removeFromBasket(stockItem, quantity) == quantity) {
            return stockList.unreserveStock(item, quantity);
        }
        return 0;
    }

    public static void checkOut(Basket basket) {
        for (Map.Entry<StockItem, Integer> item : basket.Items().entrySet()) {
            stockList.sellStock(item.getKey().getName(), item.getValue());
        }
        basket.clearBasket();
    }

}
