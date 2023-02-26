import com.epam.storozhuk.constant.Const;
import com.epam.storozhuk.entity.User;
import com.epam.storozhuk.service.CartService;
import com.epam.storozhuk.service.UserService;
import com.epam.storozhuk.service.UserSessionService;
import com.epam.storozhuk.service.impl.UserSessionServiceImpl;
import com.epam.storozhuk.servlet.LoginUserServlet;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyBoolean;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class UserLoginTest {
    ArgumentCaptor<String> argumentCaptor;
    private LoginUserServlet loginUserServlet;

    @Mock
    private UserService userService;

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

    @Mock
    private UserSessionService userSessionService;

    @Before
    public void setUpValues() throws ServletException, IOException {
        argumentCaptor = ArgumentCaptor.forClass(String.class);
        loginUserServlet = Mockito.spy(LoginUserServlet.class);
        MockitoAnnotations.initMocks(this);
        when(loginUserServlet.getServletConfig()).thenReturn(servletConfig);
        when(servletConfig.getServletContext()).thenReturn(context);
        when(context.getAttribute(Const.USER_SERVICE)).thenReturn(userService);
        when(request.getRequestDispatcher(any())).thenReturn(requestDispatcher);
        when(context.getAttribute(Const.USER_SESSION_SERVICE)).thenReturn(userSessionService);
        when(request.getServletContext()).thenReturn(context);
        when(request.getSession(anyBoolean())).thenReturn(session);
        when(request.getSession()).thenReturn(session);
        doNothing().when(requestDispatcher).forward(any(), any());
        doNothing().when(session).setAttribute(any(), any());
        when(context.getAttribute(Const.CART_SERVICE)).thenReturn(cartService);
        loginUserServlet.init();
    }

    @Test
    public void testForReturningUserError() {
        when(userService.loginUser(any(), any())).thenReturn(null);

        loginUserServlet.doPost(request, response);

        verify(request).getRequestDispatcher(argumentCaptor.capture());
        verify(request).setAttribute(Const.USER_ERROR, Const.WRONG_DATA_ERROR);
        assertEquals(Const.LOGIN_PAGE, argumentCaptor.getValue());
    }

    @Test
    public void testForSuccessfulLogin() throws IOException {
        String expectedLogin = "login";
        when(userService.loginUser(any(), any())).thenReturn(createUser(expectedLogin));
        when(request.getParameter(Const.LOGIN_PARAMETER)).thenReturn(expectedLogin);
        when(request.getParameter(Const.PASSWORD_PARAMETER)).thenReturn("password123");
        when(request.getServletContext().getAttribute(Const.USER_SESSION_SERVICE)).thenReturn(new UserSessionServiceImpl(Mockito.mock(CartService.class)));
        doNothing().when(response).sendRedirect(any());

        loginUserServlet.doPost(request, response);

        verify(session).setAttribute(eq(Const.USER_STATE_ATTRIBUTE), eq(expectedLogin));
        verify(response).sendRedirect(argumentCaptor.capture());
        assertEquals(Const.MAIN_PAGE, argumentCaptor.getValue());
    }

    private User createUser(String login) {
        return new User(login, "pass", "mail@gmail.com", "John", "Smith", true, "");
    }
}
