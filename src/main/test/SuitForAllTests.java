import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)

@Suite.SuiteClasses({
        UserRegisterTests.class,
        UserLoginTest.class,
        ProductsListPage.class,
        AddProductToCartTest.class,
        ApplyOrderServletTest.class,
        LanguageFilterTest.class
})
public class SuitForAllTests {
}
