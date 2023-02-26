package com.epam.storozhuk.constant.order;

public class OrderConst {
    public static final String SUCCESSFUL_ORDER_PAGE = "pages/order/orderAnswers/successful_order.jsp";
    public static final String FAILED_ORDER_PAGE = "pages/order/orderAnswers/failed_order.jsp";
    public static final String ORDERS_PAGE = "pages/order/orders.jsp";
    public static final String CARD_NUMBER = "cardNumber";
    public static final String RECEIVER_NAME = "receiverName";
    public static final String ORDERS_LIST_PARAM = "orders_list";
    public static final String SQL_USER_ORDERS_ID = "SELECT order_id FROM webautoshop.order where order_user_login = ?";
    public static final String SQL_ORDER_INSERT = "insert into webautoshop.order(order_user_login, user_name, order_date, user_card, status_id)" +
            " values(?,?,?,?,?)";
    public static final String SQL_LATEST_ORDER = "select order_id from webautoshop.order where order_user_login=?" +
            " and order_date=(select MAX(order_date) from webautoshop.order where order_user_login=?)";
    public static final String SQL_GET_ORDER = "SELECT * FROM webautoshop.order o inner join webautoshop.order_products op" +
            " on o.order_id = op.order_id inner join order_status os on o.status_id = os.status_id" +
            " inner join products p on op.product_id = p.products_id where o.order_id = ?";
    public static final String SQL_COLUMN_PRODUCT_ID = "product_id";
    public static final String SQL_COLUMN_ORDER_ID = "order_id";
    public static final String SQL_COLUMN_PRODUCT_PRICE = "product_price";
    public static final String SQL_COLUMN_PRODUCT_AMOUNT = "product_amount";
    public static final String SQL_COLUMN_PRODUCT_MARK = "products_mark";
    public static final String SQL_COLUMN_PRODUCT_MODEL = "products_model";
    public static final String SQL_COLUMN_PRODUCT_CATEGORY = "products_category";
    public static final String SQL_COLUMN_PRODUCT_YEAR = "products_year";
    public static final String SQL_COLUMN_PRODUCT_FUELTYPE = "products_fuelType";
    public static final String SQL_COLUMN_PRODUCT_MILEAGE = "products_mileage";
    public static final String SQL_COLUMN_PRODUCT_PICTURE = "products_picture";
    public static final String SQL_COLUMN_ORDER_STATUS = "order_status";
    public static final String SQL_COLUMN_STATUS_DESCRIPTION = "status_description";
    public static final String SQL_COLUMN_ORDER_DATE = "order_date";
    public static final String SQL_COLUMN_ORDER_USER_LOGIN = "order_user_login";
    public static final String SQL_COLUMN_ORDER_USER_CARD = "user_card";
    public static final String SQL_ORDER_PRODUCTS_TABLE = "webautoshop.order_products";
}
