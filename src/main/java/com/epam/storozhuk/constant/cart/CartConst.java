package com.epam.storozhuk.constant.cart;

public class CartConst {
    public static final String SQL_GET_CART_ID = "select cart_id from user_cart where user_login=?";
    public static final String SQL_INSERT_USER_TO_CART = "insert into user_cart(user_login) values (?)";
    public static final String SQL_SELECT_PRODUCT = "select * from cart where cart_id=? and product_id=?";
    public static final String SQL_REMOVE_CART = "delete from cart where cart_id=?";
    public static final String SQL_INCREMENT_AMOUNT = "update cart set amount = amount + 1 where cart_id=? and product_id=?";
    public static final String SQL_PUT_PRODUCT = "insert into cart(cart_id, product_id, amount) values(?, ?, ?)";
    public static final String SQL_GET_PRODUCTS = "SELECT p.products_id, p.products_mark, p.products_model, p.products_category, p.products_price, p.products_year, p.products_fuelType, p.products_mileage, p.products_picture, c.amount " +
            "FROM webautoshop.products p inner join webautoshop.cart c " +
            "on p.products_id = c.product_id " +
            "where c.cart_id = ?";
}
