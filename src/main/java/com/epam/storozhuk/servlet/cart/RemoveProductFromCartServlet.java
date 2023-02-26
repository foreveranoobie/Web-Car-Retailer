package com.epam.storozhuk.servlet.cart;

import com.epam.storozhuk.constant.Const;
import com.epam.storozhuk.entity.Cart;
import com.epam.storozhuk.entity.Product;
import com.epam.storozhuk.service.CartService;
import org.apache.log4j.Logger;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@WebServlet("/removeFromCart")
public class RemoveProductFromCartServlet extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(RemoveProductFromCartServlet.class);
    private CartService cartService;

    @Override
    public void init() {
        cartService = (CartService) getServletContext().getAttribute(Const.CART_SERVICE);
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) {
        Cart cart = (Cart) request.getSession().getAttribute(Const.CART_PARAMETER);
        String id = request.getParameter(Const.PRODUCT_ID_PARAM);
        removeProduct(cart.getCartProducts(), id);
        response.setStatus(200);
    }

    private void removeProduct(Map<Product, Integer> cartMap, String id) {
        int id_num = Integer.parseInt(id);
        for (Product product : cartMap.keySet()) {
            if (product.getProductId() == id_num) {
                LOGGER.debug("Found product to remove");
                cartMap.remove(product);
                return;
            }
        }
    }
}
