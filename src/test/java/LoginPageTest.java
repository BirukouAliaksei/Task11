import com.codeborne.selenide.Browser;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.WebDriverRunner.url;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class LoginPageTest {
    private By logOutForm = By.xpath("//*[@class='btn dropdown-toggle btn-success navbar-btn']");
    private By logOuteFormButton = By.partialLinkText("Logout");
    String emeilField = "//*[@class='col-lg-9']//*[@class='well form-horizontal auth']//*[@name='email']";
    private By passwordField = By.xpath("//*[@class='col-lg-9']//*[@class='well form-horizontal auth']//*[@name='password']");
    private By logInButton = By.xpath("//*[@class='col-lg-9']//*[@class='well form-horizontal auth']//*[@type='submit']");
    private By eShopButton = By.xpath("//*[@class='glyphicon glyphicon-th']");
    private By selectCouponButton = By.xpath("//*[@title='Coupons']");
    private By newCoupon = By.xpath("//*[@class='btn btn-primary pull-right']");
    private By couponName = By.xpath("//*[@id='name']");
    private By couponDiscount = By.xpath("//*[@id='discount_amount']");
    private By couponDate = By.xpath("//*[@name='valid_date']");
    private By couponNum = By.xpath("//*[@id='number_coupons']");
    private By submitBtn = By.xpath("//*[@type='submit']");
    private By submitBtnForSearchCoupon = By.xpath("//*[@class='btn']");
    private By searchForm = By.xpath("//*[@name='name']");
    private By delBtn = By.xpath("//*[@class='glyphicon glyphicon-trash']");
    private By confirmBtn = By.xpath("//div[@class='sweet-alert showSweetAlert visible']//*[@class='confirm']");
    private String email = "demo@open-eshop.com";
    private String password = "demo";
    String afterLoginUrl = "http://open-eshop.stqa.ru/oc-panel";
    String loginPageLink = "http://open-eshop.stqa.ru/oc-panel/auth/login";

    @Test
    @Order(1)
    public void runTest() {
        Configuration.headless = true;
        Configuration.browserSize = "1920x1080";
        Configuration.timeout = 6000;
        open("http://open-eshop.stqa.ru/oc-panel/auth/login");
    }

    @Test
    @Order(2)
    public void login() {
        $(By.xpath(emeilField)).setValue(email);
        $(passwordField).setValue(password);
        $(logInButton).click();
    }

    @Test
    @Order(3)
    public void createCoupone() {
        $(eShopButton).click();
        $(selectCouponButton).click();
        $(newCoupon).click();
        $(couponName).shouldBe(visible);
        $(couponName).setValue("Shaurma");
        $(couponDiscount).setValue("55");
        $(couponDate).setValue("2020-01-09");
        $(couponNum).clear();
        $(couponNum).setValue("555");
        $(submitBtn).click();
        assertEquals("Success", $(By.xpath("//*[@class='alert-heading']")).getText());
    }

    @Test
    @Order(4)
    public void couponeSearch(){
        $(searchForm).shouldBe(visible).setValue("shaurma");
        $(submitBtnForSearchCoupon).click();
    }

    @Test
    @Order(5)
    public void couponeDelete() {
        $(By.xpath("//*[@class='glyphicon glyphicon-trash']")).click();
        $(By.xpath("//div[@class='sweet-alert showSweetAlert visible']//*[@class='confirm']")).click();
        assertFalse($(By.xpath("//*[@class='table table-striped table-bordered']//*[@style='display: none;']")).isDisplayed());
    }

    @Test
    @Order(6)
    public void logOut() throws InterruptedException {
        Thread.sleep(1000);
        $(By.xpath("//*[@class='btn dropdown-toggle btn-success navbar-btn']")).click();
        $(By.partialLinkText("Logout")).click();
        assertEquals(loginPageLink, url());
    }
}
