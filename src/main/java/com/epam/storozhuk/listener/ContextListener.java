package com.epam.storozhuk.listener;

import com.epam.storozhuk.captcha.CaptchaDescriptor;
import com.epam.storozhuk.captcha.cleaner.Cleaner;
import com.epam.storozhuk.captcha.strategy.CaptchaStrategiesContainer;
import com.epam.storozhuk.constant.Const;
import com.epam.storozhuk.dao.CartDAO;
import com.epam.storozhuk.dao.ManagerDB;
import com.epam.storozhuk.dao.impl.CartDAOImpl;
import com.epam.storozhuk.dao.impl.OrderDAOImpl;
import com.epam.storozhuk.dao.impl.ProductDAOImpl;
import com.epam.storozhuk.dao.impl.UserDAOImpl;
import com.epam.storozhuk.service.CartService;
import com.epam.storozhuk.service.impl.CartServiceImpl;
import com.epam.storozhuk.service.impl.OrderServiceImpl;
import com.epam.storozhuk.service.impl.ProductServiceImpl;
import com.epam.storozhuk.service.impl.UserServiceImpl;
import com.epam.storozhuk.service.impl.UserSessionServiceImpl;
import com.mysql.cj.jdbc.MysqlDataSource;
import org.apache.log4j.Logger;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@WebListener
public class ContextListener implements ServletContextListener {
    private static final Logger LOGGER = Logger.getLogger(ContextListener.class);

    public void contextInitialized(ServletContextEvent event) {
        ServletContext context = event.getServletContext();
        String captchaType = context.getInitParameter(Const.CAPTCHA_TYPE_PARAMETER);
        DataSource dataSource = getDataSource();
        ManagerDB managerDB = new ManagerDB(dataSource);
        Map<String, CaptchaDescriptor> mapOfCaptchas = new HashMap<>();
        final UserServiceImpl userService = new UserServiceImpl(new UserDAOImpl(), managerDB);
        final ProductServiceImpl productService = new ProductServiceImpl(new ProductDAOImpl(), managerDB);
        final CartDAO cartDAO = new CartDAOImpl();
        CaptchaStrategiesContainer captchaStrategiesContainer = new CaptchaStrategiesContainer(mapOfCaptchas);
        context.setAttribute(Const.STRATEGY_TYPE,
                captchaStrategiesContainer.getStrategy(captchaType));
        context.setAttribute(Const.USER_SERVICE, userService);
        context.setAttribute(Const.PRODUCT_SERVICE, productService);
        launchCleaner(context.getInitParameter(Const.CAPTCHA_LIFETIME_PARAMETER), mapOfCaptchas);
        context.setAttribute(Const.CAPTCHA_MAP_CONTEXT, mapOfCaptchas);
        CartService cartService = new CartServiceImpl(cartDAO, managerDB);
        context.setAttribute(Const.CART_SERVICE, cartService);
        context.setAttribute(Const.USER_SESSION_SERVICE, new UserSessionServiceImpl(cartService));
        context.setAttribute(Const.ORDER_SERVICE, new OrderServiceImpl(new OrderDAOImpl(), managerDB));
    }

    private DataSource getDataSource() {
        Properties properties = getProperties();
        MysqlDataSource dataSource = new MysqlDataSource();
        dataSource.setURL(properties.getProperty("sql_url"));
        dataSource.setUser(properties.getProperty("login"));
        dataSource.setPassword(properties.getProperty("password"));
        LOGGER.debug("Returning dataSource");
        return dataSource;
    }

    private void launchCleaner(String captchaLifetime, Map<String, CaptchaDescriptor> mapOfCaptchas) {
        ScheduledExecutorService ses = Executors.newSingleThreadScheduledExecutor();
        LOGGER.debug("Initialized executor service");
        Cleaner captchaCleaner = new Cleaner(Long.parseLong(captchaLifetime), mapOfCaptchas);
        ses.scheduleAtFixedRate(captchaCleaner, 5, 30, TimeUnit.SECONDS);
        LOGGER.debug("Launched cleaner for captchas map");
    }

    private Properties getProperties() {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("keywords.properties");
        Properties prop = new Properties();
        try {
            prop.load(inputStream);
        } catch (IOException e) {
            LOGGER.debug(e.getMessage());
        }
        return prop;
    }

}
