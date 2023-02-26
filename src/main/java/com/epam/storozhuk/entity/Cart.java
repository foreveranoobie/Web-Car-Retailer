package com.epam.storozhuk.entity;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Cart {
    private Map<Product, Integer> cartProducts;

    public Cart() {
        initMap();
    }

    public Cart(Product product, int amount) {
        initMap();
        cartProducts.put(product, amount);
    }

    public Cart(List<Product> productsList, List<Integer> amountsList) {
        initMap();
        int index = 0;
        for (Product product : productsList) {
            cartProducts.put(product, amountsList.get(index++));
        }
    }

    public Cart(Map<Product, Integer> cartProducts) {
        initMap();
        this.cartProducts = new LinkedHashMap<>(cartProducts);
    }

    public Map<Product, Integer> getCartProducts() {
        return cartProducts;
    }

    public BigDecimal getSummaryPrice() {
        BigDecimal sum = new BigDecimal(0);
        BigDecimal multiply = new BigDecimal(0);
        for (Product product : cartProducts.keySet()) {
            multiply = multiply.add(new BigDecimal(cartProducts.get(product)).multiply(product.getPrice()));
            sum = sum.add(multiply);
        }
        return sum;
    }

    public int getSummaryProductsAmount() {
        int sum = 0;
        for (Product product : cartProducts.keySet()) {
            sum += cartProducts.get(product);
        }
        return sum;
    }

    public void removeProduct(int id) {
        for (Product product : cartProducts.keySet()) {
            if (product.getProductId() == id) {
                cartProducts.remove(product);
                break;
            }
        }
    }

    public void addProduct(Product product, int amount) {
        cartProducts.put(product, amount);
    }

    private void initMap() {
        cartProducts = new LinkedHashMap<>();
    }
}
