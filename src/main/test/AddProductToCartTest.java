import com.epam.storozhuk.constant.Const;
import com.epam.storozhuk.entity.Cart;
import com.epam.storozhuk.entity.Product;
import com.epam.storozhuk.service.CartService;
import com.epam.storozhuk.service.ProductService;
import com.epam.storozhuk.servlet.cart.AddProductToCartServlet;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyBoolean;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class AddProductToCartTest {
    private AddProductToCartServlet addProductToCartServlet;

    @Mock
    private ProductService productService;

    @Mock
    private CartService cartService;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private ServletContext context;

    @Mock
    private RequestDispatcher requestDispatcher;

    @Mock
    private ServletConfig servletConfig;

    @Mock
    private HttpSession session;

    @Before
    public void setUpValues() {
        MockitoAnnotations.initMocks(this);
        addProductToCartServlet = Mockito.spy(AddProductToCartServlet.class);
        when(addProductToCartServlet.getServletConfig()).thenReturn(servletConfig);
        when(servletConfig.getServletContext()).thenReturn(context);
        when(request.getRequestDispatcher(any())).thenReturn(requestDispatcher);
        when(request.getServletContext()).thenReturn(context);
        when(context.getAttribute(Const.PRODUCT_SERVICE)).thenReturn(productService);
        when(context.getAttribute(Const.CART_SERVICE)).thenReturn(cartService);
        when(request.getSession()).thenReturn(session);
    }

    @Test
    public void testOnUnauthorizedCartCreation() {
        Cart expectedCart = new Cart();
        addProductToCartServlet.init();
        when(request.getSession(anyBoolean())).thenReturn(session);
        when(session.getAttribute(Const.CART_PARAMETER)).thenReturn(expectedCart);
        when(request.getParameter(Const.PRODUCT_ID_PARAM)).thenReturn("1");

        addProductToCartServlet.doGet(request, response);

        verify(session).setAttribute(eq(Const.CART_PARAMETER), eq(expectedCart));
    }

    @Test
    public void testOnCartAlreadyContainsProduct() {
        Cart initialCart = getCart();
        addProductToCartServlet.init();
        when(request.getSession(anyBoolean())).thenReturn(session);
        when(session.getAttribute(Const.CART_PARAMETER)).thenReturn(initialCart);
        when(request.getParameter(Const.PRODUCT_ID_PARAM)).thenReturn("2");

        addProductToCartServlet.doGet(request, response);

        assertEquals(2, initialCart.getSummaryProductsAmount());
    }

    private Cart getCart() {
        int productId = 2;
        String mark = "Volvo";
        String model = "850R";
        String category = "universal";
        BigDecimal price = new BigDecimal(10000);
        int productionYear = 1997;
        String fuelType = "Petrol";
        int mileage = 175000;
        Cart cart = new Cart();
        cart.addProduct(new Product(productId, mark, model, category, price, productionYear, fuelType, mileage, null), 1);
        return cart;
    }
}
