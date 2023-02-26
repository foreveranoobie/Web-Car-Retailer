import com.epam.storozhuk.constant.Const;
import com.epam.storozhuk.filters.LanguageFilter;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Locale;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyBoolean;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class LanguageFilterTest {
    private LanguageFilter languageFilter;

    @Mock
    private FilterConfig filterConfig;

    @Mock
    private HttpServletRequest servletRequest;

    @Mock
    private HttpServletResponse servletResponse;

    @Mock
    private FilterChain filterChain;

    @Mock
    private ServletContext servletContext;

    @Mock
    private HttpSession httpSession;

    @Mock
    private Enumeration<Locale> localesEnum;

    @Before
    public void setUpValues() {
        MockitoAnnotations.initMocks(this);
        when(filterConfig.getServletContext()).thenReturn(servletContext);
        doNothing().when(servletContext).setAttribute(any(), any());
        languageFilter = Mockito.spy(new LanguageFilter());
        when(servletRequest.getSession()).thenReturn(httpSession);
        when(servletRequest.getSession(anyBoolean())).thenReturn(httpSession);
        when(filterConfig.getInitParameter(Const.LANGUAGE_TYPES)).thenReturn("en,ru");
        when(filterConfig.getInitParameter(Const.DEFAULT_LANGUAGE)).thenReturn("en");
        when(servletRequest.getLocales()).thenReturn(localesEnum);
        when(filterConfig.getInitParameter(Const.LOCALES_STORAGE)).thenReturn("Session");
    }

    @Test
    public void testOnSwitchLanguageEqualsToStorage() throws ServletException, IOException {
        when(httpSession.getAttribute(Const.LOCALE_PARAM)).thenReturn("en");
        when(servletRequest.getParameter(Const.LANG_PARAM)).thenReturn("en");
        languageFilter.init(filterConfig);
        languageFilter.doFilter(servletRequest, servletResponse, filterChain);
        verify(httpSession).setAttribute(Const.LOCALE_PARAM, "en");
    }

    @Test
    public void testOnSwitchLanguageNotEqualsToStorage() throws ServletException, IOException {
        when(httpSession.getAttribute(Const.LOCALE_PARAM)).thenReturn("en");
        when(servletRequest.getParameter(Const.LANG_PARAM)).thenReturn("ru");
        languageFilter.init(filterConfig);
        languageFilter.doFilter(servletRequest, servletResponse, filterChain);
        verify(httpSession).setAttribute(eq(Const.LOCALE_PARAM), eq("ru"));
    }

    @Test
    public void testOnSwitchLanguageNotNull() throws ServletException, IOException {
        when(httpSession.getAttribute(Const.LOCALE_PARAM)).thenReturn(null);
        when(servletRequest.getParameter(Const.LANG_PARAM)).thenReturn("ru");
        languageFilter.init(filterConfig);
        languageFilter.doFilter(servletRequest, servletResponse, filterChain);
        verify(httpSession).setAttribute(eq(Const.LOCALE_PARAM), eq("ru"));
    }

    @Test
    public void testOnStorageLanguageNotNull() throws ServletException, IOException {
        when(httpSession.getAttribute(Const.LOCALE_PARAM)).thenReturn("en");
        when(servletRequest.getParameter(Const.LANG_PARAM)).thenReturn(null);
        languageFilter.init(filterConfig);
        languageFilter.doFilter(servletRequest, servletResponse, filterChain);
        verify(httpSession).setAttribute(eq(Const.LOCALE_PARAM), eq("en"));
    }

    @Test
    public void testOnStorageDoesntContainsLanguage() throws ServletException, IOException {
        when(localesEnum.hasMoreElements()).thenReturn(false);
        when(filterConfig.getInitParameter(Const.LOCALES_STORAGE)).thenReturn("Session");
        when(httpSession.getAttribute(Const.LOCALE_PARAM)).thenReturn(null);
        when(servletRequest.getParameter(Const.LANG_PARAM)).thenReturn("ua");
        languageFilter.init(filterConfig);
        languageFilter.doFilter(servletRequest, servletResponse, filterChain);
        verify(httpSession).setAttribute(eq(Const.LOCALE_PARAM), eq("en"));
    }
}
