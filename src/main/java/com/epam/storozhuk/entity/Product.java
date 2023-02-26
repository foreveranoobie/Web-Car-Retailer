package com.epam.storozhuk.entity;

import com.epam.storozhuk.constant.Const;

import java.math.BigDecimal;

public class Product {
    private int productId;
    private String mark;
    private String model;
    private String category;
    private BigDecimal price;
    private int productionYear;
    private String fuelType;
    private int mileage;
    private String image;

    public Product() {
    }

    public Product(int id, String mark, String model, String category, BigDecimal price, int productionYear, String fuelType, int mileage,
                   String image) {
        this.productId = id;
        this.mark = mark;
        this.model = model;
        this.category = category;
        this.price = price;
        this.productionYear = productionYear;
        this.fuelType = fuelType;
        this.mileage = mileage;
        setImage(image);
    }

    public int getProductId() {
        return productId;
    }

    public String getMark() {
        return mark;
    }

    public String getModel() {
        return model;
    }

    public String getCategory() {
        return category;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public int getProductionYear() {
        return productionYear;
    }

    public String getFuelType() {
        return fuelType;
    }

    public int getMileage() {
        return mileage;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public void setProductionYear(int productionYear) {
        this.productionYear = productionYear;
    }

    public void setFuelType(String fuelType) {
        this.fuelType = fuelType;
    }

    public void setMileage(int mileage) {
        this.mileage = mileage;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        if (image == null || image.isEmpty()) {
            this.image = Const.IMAGES_CARS_DEFAULT;
        } else {
            this.image = image;
        }
    }
}
