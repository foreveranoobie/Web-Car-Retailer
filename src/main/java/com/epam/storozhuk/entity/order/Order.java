package com.epam.storozhuk.entity.order;

import java.math.BigDecimal;
import java.util.List;

public class Order {
    private int id;
    private String status;
    private String statusDescription;
    private String creationDate;
    private String userLogin;
    private String userPaymentCard;
    private List<OrderedProduct> orderedProductList;

    public Order(int id, String status, String statusDescription, String creationDate, String userLogin, String userPaymentCard, List<OrderedProduct> orderedProductList) {
        this.id = id;
        this.status = status;
        this.statusDescription = statusDescription;
        this.creationDate = creationDate;
        this.userLogin = userLogin;
        this.userPaymentCard = userPaymentCard;
        this.orderedProductList = orderedProductList;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setStatusDescription(String statusDescription) {
        this.statusDescription = statusDescription;
    }

    public int getId() {
        return id;
    }

    public BigDecimal getTotalPrice() {
        BigDecimal sum = new BigDecimal("0");
        BigDecimal multiply = new BigDecimal("0");
        for (OrderedProduct orderedProduct : orderedProductList) {
            multiply = multiply.add(orderedProduct.getProduct().getPrice().multiply(new BigDecimal(orderedProduct.getAmount())));
            sum = sum.add(multiply);
        }
        return sum;
    }

    public String getStatus() {
        return status;
    }

    public String getStatusDescription() {
        return statusDescription;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public String getUserLogin() {
        return userLogin;
    }

    public String getUserPaymentCard() {
        return userPaymentCard;
    }

    public List<OrderedProduct> getOrderedProductList() {
        return orderedProductList;
    }
}
