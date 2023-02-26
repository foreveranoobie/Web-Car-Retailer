import com.epam.storozhuk.constant.Const;
import com.epam.storozhuk.dao.ManagerDB;
import com.epam.storozhuk.dao.ProductDAO;
import com.epam.storozhuk.service.ProductService;
import com.epam.storozhuk.service.impl.ProductServiceImpl;
import com.epam.storozhuk.servlet.GetProductsServlet;
import com.epam.storozhuk.sql.RequestWrapper;
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
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ProductsListPage {
    ArgumentCaptor<String> argumentCaptor;
    ArgumentCaptor<Integer> argumentCaptorNumber;
    private ProductService productService;

    @Spy
    private GetProductsServlet getProductsServlet;

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
    private RequestWrapper requestWrapper;

    @Before
    public void setUpValues() throws ServletException, IOException {
        productService = Mockito.spy(new ProductServiceImpl(Mockito.mock(ProductDAO.class), Mockito.mock(ManagerDB.class)));
        argumentCaptor = ArgumentCaptor.forClass(String.class);
        argumentCaptorNumber = ArgumentCaptor.forClass(Integer.class);
        getProductsServlet = Mockito.spy(GetProductsServlet.class);
        MockitoAnnotations.initMocks(this);
        when(getProductsServlet.getServletConfig()).thenReturn(servletConfig);
        when(servletConfig.getServletContext()).thenReturn(context);
        when(request.getRequestDispatcher(any())).thenReturn(requestDispatcher);
        when(request.getServletContext()).thenReturn(context);
        when(context.getAttribute(Const.PRODUCT_SERVICE)).thenReturn(productService);
        doNothing().when(requestDispatcher).forward(any(), any());
        Mockito.doReturn(Collections.emptyMap()).when(productService).getCheckboxValues();
        getProductsServlet.init();
    }

    @Test
    public void testOnRequestingDefaultProductsPage() {
        int expectedCountOfPages = 8;
        Mockito.doReturn(expectedCountOfPages).when(productService).countProducts(any(), any());
        Mockito.doReturn(Collections.emptyList()).when(productService).getProducts();
        Mockito.doReturn(Collections.emptyMap()).when(productService).getRangedValues();
        Mockito.doReturn(Collections.emptyMap()).when(request).getParameterMap();

        getProductsServlet.doGet(request, response);

        verify(request).setAttribute(eq(Const.PAGES_COUNT_PARAMETER), argumentCaptorNumber.capture());
        assertTrue(argumentCaptorNumber.getValue().equals(expectedCountOfPages));
    }

    @Test
    public void testOnPageOutOfBoundsCorrecting() {
        int expectedCountOfPages = 8;
        Map<String, String[]> paginationMap = Mockito.spy(new HashMap<>());
        Map<String, String[]> requestMap = Mockito.mock(Map.class);
        String[] array = {Const.PAGINATION_LIMIT_DEFAULT, "10"};
        when(paginationMap.get(Const.WRAPPER_PAGE)).thenReturn(array);
        when(requestWrapper.getPagination()).thenReturn(paginationMap);
        when(requestMap.isEmpty()).thenReturn(false);
        when(request.getParameterMap()).thenReturn(requestMap);
        Mockito.doReturn(expectedCountOfPages).when(productService).countProducts(any(), any());
        Mockito.doReturn(requestWrapper).when(productService).getRequestWrapper(any());
        Mockito.doReturn(Collections.emptyList()).when(productService).getFilteredProducts(any());
        Mockito.doCallRealMethod().when(productService).checkForCorrectPage(requestWrapper, expectedCountOfPages);

        getProductsServlet.doGet(request, response);

        verify(request).setAttribute(eq(Const.CURRENT_PAGE_PARAMETER), argumentCaptor.capture());
        assertEquals(expectedCountOfPages - 1, argumentCaptor.getValue());
    }
}
