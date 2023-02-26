import com.epam.storozhuk.constant.Const;
import com.epam.storozhuk.service.CartService;
import com.epam.storozhuk.service.OrderService;
import com.epam.storozhuk.servlet.order.ApplyOrderServlet;
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

import static org.mockito.Matchers.anyBoolean;
import static org.mockito.Matchers.anyList;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ApplyOrderServletTest {
    private ApplyOrderServlet applyOrderServlet;

    @Mock
    private OrderService orderService;

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
    public void setUpParameters(){
        MockitoAnnotations.initMocks(this);
        applyOrderServlet = Mockito.spy(ApplyOrderServlet.class);
        when(applyOrderServlet.getServletConfig()).thenReturn(servletConfig);
        when(servletConfig.getServletContext()).thenReturn(context);
        when(context.getAttribute(Const.ORDER_SERVICE)).thenReturn(orderService);
        when(context.getAttribute(Const.CART_SERVICE)).thenReturn(cartService);
    }

    @Test
    public void testOnRemovedCartAfterAppliedOrder(){
        applyOrderServlet.init();
        String login = "user";
        when(session.getAttribute(Const.USER_STATE_ATTRIBUTE)).thenReturn(login);
        when(request.getSession()).thenReturn(session);
        when(request.getSession(anyBoolean())).thenReturn(session);
        when(orderService.createOrder(anyList(), anyObject())).thenReturn(true);
        applyOrderServlet.doPost(request, response);
        verify(cartService).clearCart(eq(login));
    }
}
