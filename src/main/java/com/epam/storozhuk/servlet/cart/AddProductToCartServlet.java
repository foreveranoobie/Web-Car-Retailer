package com.epam.storozhuk.servlet.cart;

import com.epam.storozhuk.constant.Const;
import com.epam.storozhuk.entity.Cart;
import com.epam.storozhuk.entity.Product;
import com.epam.storozhuk.service.ProductService;
import org.apache.log4j.Logger;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@WebServlet("/addProductToCart")
public class AddProductToCartServlet extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(AddProductToCartServlet.class);
    private ProductService productService;

    @Override
    public void init() {
        productService = (ProductService) getServletContext().getAttribute(Const.PRODUCT_SERVICE);
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) {
        Cart cart = (Cart) request.getSession().getAttribute(Const.CART_PARAMETER);
        if (cart == null) {
            LOGGER.debug("Cart is null. Creating new cart");
            cart = new Cart();
        }
        String id = request.getParameter(Const.PRODUCT_ID_PARAM);
        if (cartContainsProduct(cart, id)) {
            LOGGER.debug("Cart already has necessary product. Increasing it's amount");
            incrementProductAmount(cart.getCartProducts(), id);
        } else {
            LOGGER.debug("Cart doesn't contain necessary product. Adding it to cart");
            Product product = productService.getProductById(Integer.parseInt(id));
            if (product != null) {
                LOGGER.debug("Successfully received product by id. Adding it to the carts list");
                cart.addProduct(product, 1);
            }
        }
        request.getSession().setAttribute(Const.CART_PARAMETER, cart);
    }

    private void incrementProductAmount(Map<Product, Integer> cartMap, String id) {
        int id_num = Integer.parseInt(id);
        for (Product product : cartMap.keySet()) {
            if (product.getProductId() == id_num) {
                cartMap.put(product, cartMap.get(product) + 1);
                return;
            }
        }
    }

    private boolean cartContainsProduct(Cart cart, String id) {
        int id_num = Integer.parseInt(id);
        boolean contains = false;
        for (Product product : cart.getCartProducts().keySet()) {
            if (product.getProductId() == id_num) {
                contains = true;
                break;
            }
        }
        return contains;
    }
}
