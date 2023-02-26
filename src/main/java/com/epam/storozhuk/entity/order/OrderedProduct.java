package com.epam.storozhuk.entity.order;

import com.epam.storozhuk.entity.Product;

public class OrderedProduct {
    private final Product product;
    private final int amount;

    public OrderedProduct(Product product, int amount) {
        this.product = product;
        this.amount = amount;
    }

    public Product getProduct() {
        return product;
    }

    public int getAmount() {
        return amount;
    }
}
