import com.epam.storozhuk.captcha.CaptchaDescriptor;
import com.epam.storozhuk.captcha.strategy.impl.CookieCaptchaStrategyImpl;
import com.epam.storozhuk.captcha.strategy.impl.FieldsCaptchaStrategyImpl;
import com.epam.storozhuk.captcha.strategy.impl.SessionCaptchaStrategyImpl;
import com.epam.storozhuk.constant.Const;
import com.epam.storozhuk.service.CartService;
import com.epam.storozhuk.service.UserSessionService;
import com.epam.storozhuk.service.impl.UserServiceImpl;
import com.epam.storozhuk.servlet.RegisterUserServlet;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.anyBoolean;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class UserRegisterTests {
    private ArgumentCaptor<String> requestCaptor;

    @Spy
    private RegisterUserServlet registerUserServlet;

    @Mock
    private Enumeration<String> paramEnums;

    @Mock
    private ServletConfig servletConfig;

    @Mock
    private UserServiceImpl userService;

    @Mock
    private ServletContext servletContext;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private SessionCaptchaStrategyImpl sessionCaptchaStrategy;

    @Mock
    private CookieCaptchaStrategyImpl cookieCaptchaStrategy;

    @Mock
    private FieldsCaptchaStrategyImpl fieldsCaptchaStrategy;

    @Mock
    private RequestDispatcher requestDispatcher;

    @Mock
    private HttpSession session;

    @Mock
    private Map<String, CaptchaDescriptor> mapOfCaptchas;

    @Mock
    private CartService cartService;

    @Mock
    private UserSessionService userSessionService;


    @Before
    public void setUpValues() throws ServletException, IOException {
        requestCaptor = ArgumentCaptor.forClass(String.class);
        MockitoAnnotations.initMocks(this);
        doNothing().when(requestDispatcher).forward(request, response);
        Mockito.doNothing().when(userSessionService).saveUser(any(), any(), any());
        Mockito.doNothing().when(userSessionService).deleteUser(any());
        when(request.getRequestDispatcher(any())).thenReturn(requestDispatcher);
        when(servletContext.getAttribute(Const.CAPTCHA_MAP_CONTEXT)).thenReturn(mapOfCaptchas);
        when(servletContext.getAttribute(Const.USER_SESSION_SERVICE)).thenReturn(userSessionService);
        when(servletContext.getAttribute(Const.USER_SERVICE)).thenReturn(userService);
        when(servletContext.getAttribute(Const.STRATEGY_TYPE)).thenReturn(sessionCaptchaStrategy);
        when(servletContext.getAttribute(Const.CART_SERVICE)).thenReturn(cartService);
        when(servletConfig.getServletContext()).thenReturn(servletContext);
        when(registerUserServlet.getServletConfig()).thenReturn(servletConfig);
        when(request.getServletContext()).thenReturn(servletContext);
        when(request.getSession()).thenReturn(session);
        when(request.getSession(anyBoolean())).thenReturn(session);
        doNothing().when(session).setAttribute(any(), any());
    }

    @Test
    public void testForReturningUserExistsError() {
        when(userService.registerUser(any())).thenReturn(false);
        when(sessionCaptchaStrategy.isValidCaptcha(any())).thenReturn(true);
        registerUserServlet.init();
        when(request.getParameterNames()).thenReturn(paramEnums);
        createRightUserCredentials();

        registerUserServlet.doPost(request, response);

        verify(request).setAttribute(eq(Const.LOGIN_ERROR), requestCaptor.capture());
        assertEquals(Const.LOGIN_EXISTS_ERROR, requestCaptor.getValue());
    }

    @Test
    public void testForReturningInvalidCaptchaValue() {
        SessionCaptchaStrategyImpl realSessionStrategy = new SessionCaptchaStrategyImpl(mapOfCaptchas);
        when(servletContext.getAttribute(Const.STRATEGY_TYPE)).thenReturn(realSessionStrategy);
        createRightUserCredentials();
        when(request.getParameterNames()).thenReturn(paramEnums);
        when(request.getParameter(Const.CAPTCHA_PARAMETER)).thenReturn("111");
        when(session.getAttribute(Const.CAPTCHA_ANSWER_PARAMETER)).thenReturn("1");
        when(servletContext.getInitParameter(Const.CAPTCHA_TYPE_PARAMETER)).thenReturn(Const.SESSION_CAPTCHA);
        registerUserServlet.init();

        registerUserServlet.doPost(request, response);

        verify(request).setAttribute(eq(Const.CAPTCHA_ERROR), requestCaptor.capture());
        assertEquals(Const.CAPTCHA_VALUE_ERROR, requestCaptor.getValue());
    }

    @Test
    public void testForSessionSuccessfulRegister() throws IOException, ServletException {
        createRightUserCredentials();
        Part part = Mockito.mock(Part.class);
        when(request.getPart(Const.AVATAR_PARAMETER)).thenReturn(part);
        doNothing().when(part).write(anyString());
        when(part.getHeader(Const.HEADER_CONTENT_DISPOSITION)).thenReturn("fileName=\"");
        when(userService.registerUser(any())).thenReturn(true);
        when(sessionCaptchaStrategy.isValidCaptcha(request)).thenReturn(true);
        registerUserServlet.init();

        registerUserServlet.doPost(request, response);

        verify(response).sendRedirect(requestCaptor.capture());
        assertEquals(Const.MAIN_PAGE, requestCaptor.getValue());
    }

    @Test
    public void testForCookieSuccessfulRegister() throws IOException, ServletException {
        createRightUserCredentials();
        Part part = Mockito.mock(Part.class);
        when(request.getPart(Const.AVATAR_PARAMETER)).thenReturn(part);
        doNothing().when(part).write(anyString());
        when(part.getHeader(Const.HEADER_CONTENT_DISPOSITION)).thenReturn("fileName=\"");
        when(servletContext.getAttribute(Const.STRATEGY_TYPE)).thenReturn(cookieCaptchaStrategy);
        when(userService.registerUser(any())).thenReturn(true);
        when(cookieCaptchaStrategy.isValidCaptcha(request)).thenReturn(true);
        registerUserServlet.init();

        registerUserServlet.doPost(request, response);

        verify(response).sendRedirect(requestCaptor.capture());
        assertEquals(Const.MAIN_PAGE, requestCaptor.getValue());
    }

    @Test
    public void testForHiddenFieldsSuccessfulRegister() throws IOException, ServletException {
        createRightUserCredentials();
        Part part = Mockito.mock(Part.class);
        when(request.getPart(Const.AVATAR_PARAMETER)).thenReturn(part);
        doNothing().when(part).write(anyString());
        when(part.getHeader(Const.HEADER_CONTENT_DISPOSITION)).thenReturn("fileName=\"");
        when(servletContext.getAttribute(Const.STRATEGY_TYPE)).thenReturn(fieldsCaptchaStrategy);
        when(userService.registerUser(any())).thenReturn(true);
        when(fieldsCaptchaStrategy.isValidCaptcha(request)).thenReturn(true);
        registerUserServlet.init();

        registerUserServlet.doPost(request, response);

        verify(response).sendRedirect(requestCaptor.capture());
        assertEquals(Const.MAIN_PAGE, requestCaptor.getValue());
    }

    private void createRightUserCredentials() {
        when(request.getParameter(Const.LOGIN_PARAMETER)).thenReturn("superlogin");
        when(request.getParameter(Const.PASSWORD_PARAMETER)).thenReturn("superpassword");
        when(request.getParameter(Const.FIRSTNAME_PARAMETER)).thenReturn("Super");
        when(request.getParameter(Const.LASTNAME_PARAMETER)).thenReturn("Man");
        when(request.getParameter(Const.EMAIL_PARAMETER)).thenReturn("superman@gmail.com");
    }
}
