package qa.guru.demowebshop;

import com.codeborne.selenide.WebDriverRunner;
import io.qameta.allure.Step;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Cookie;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static helpers.CustomApiListener.withCustomTemplates;
import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;
import static qa.guru.demowebshop.TestData.FIRST_NAME;

public class DemoWebShopsTests extends TestBase{

    @Test
    void addNewToCartAsAnonymTest() {

        String body = "product_attribute_72_5_18=53" +
                "&product_attribute_72_6_19=54" +
                "&product_attribute_72_3_20=57" +
                "&addtocart_72.EnteredQuantity=1";

        given()
                .contentType("application/x-www-form-urlencoded; charset=UTF-8")
                .body(body)
                .when()
                .post("addproducttocart/details/72/1")
                .then()
                .log().all()
                .statusCode(200)
                .body("success", is(true))
                .body("message",is("The product has been added to your <a href=\"/cart\">shopping cart</a>"))
                .body("updatetopcartsectionhtml",is("(1)"));
    }

    @Test
    void addToCartAsAnonymTest() {
        /*
        curl 'http://demowebshop.tricentis.com/addproducttocart/details/72/1' \
        -H 'Accept: *\/*' \
        -H 'Accept-Language: ru-RU,ru;q=0.9' \
        -H 'Connection: keep-alive' \
        -H 'Content-Type: application/x-www-form-urlencoded; charset=UTF-8' \
        -H 'Cookie: Nop.customer=6f285aa7-2c0f-4e58-82cb-03a039ff0b90; __utma=78382081.2010883311.1658833005.1658833005.1658833005.1; __utmc=78382081; __utmz=78382081.1658833005.1.1.utmcsr=google|utmccn=(organic)|utmcmd=organic|utmctr=(not%20provided); __utmt=1; NopCommerce.RecentlyViewedProducts=RecentlyViewedProductIds=72&RecentlyViewedProductIds=31; __atuvc=3%7C30; __atuvs=62dfc90df6daa59e002; __utmb=78382081.10.10.1658833005' \
        -H 'Origin: http://demowebshop.tricentis.com' \
        -H 'Referer: http://demowebshop.tricentis.com/build-your-cheap-own-computer' \
        -H 'User-Agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/103.0.0.0 Safari/537.36' \
        -H 'X-Requested-With: XMLHttpRequest' \
        --data-raw 'product_attribute_72_5_18=53&product_attribute_72_6_19=54&product_attribute_72_3_20=57&addtocart_72.EnteredQuantity=1' \
        --compressed \
        --insecure
         */

        String body = "product_attribute_72_5_18=53" +
                      "&product_attribute_72_6_19=54" +
                      "&product_attribute_72_3_20=57" +
                      "&addtocart_72.EnteredQuantity=1";

        given()
                .contentType("application/x-www-form-urlencoded; charset=UTF-8")
                .cookie("Nop.customer", "6f285aa7-2c0f-4e58-82cb-03a039ff0b90;")
                .body(body)
                .when()
                .post("/addproducttocart/details/72/1")
                .then()
                .log().all()
                .statusCode(200)
                .body("success", is(true))
                .body("message",is("The product has been added to your <a href=\"/cart\">shopping cart</a>"));
    }

    @Test
    void addToCartAsAuthorizedTest() {

        //-H 'Cookie: Nop.customer=6f285aa7-2c0f-4e58-82cb-03a039ff0b90;
        // NopCommerce.RecentlyViewedProducts=RecentlyViewedProductIds=72&RecentlyViewedProductIds=31;  __RequestVerificationToken=k03NLbscWhyjcHuegyzssXL9TCpkX9Aj16BS0_wi7Vn_D_LCs9lL4lWqlynvtDGQmlzF6zRel6CnDFhVQ3yCfm01cQSaDu4RNI2_Jl9YsP41; NOPCOMMERCE.AUTH=80858C6C75C772B6E10B8D966ED4E34D909DF3084CDF054F72FA834F6E2344D0B7C6CD8195F37361E387BF5DBC7C9344B829E023B00B8CD301F69D0556FBE63745919893FDF76ED9CD518126061E476770B70C42157B8839AE65B1E2EAD754E11FABD66DB6FA85A228F5C2FB1B5B39D7F9CCD1571232FDBEE75B693675899066FA8ED55635F98A6AD00E6ED6B7E6F7C0; Nop.customer=bccc3d3b-96f1-4e02-aef5-52bd4ae25d47;

        String authCookie = given()
                .contentType("application/x-www-form-urlencoded; charset=UTF-8")
                .formParam("Email", "vbdv@feferf.ru")
                .formParam("Password","itLf7@U@Bf6khGH&RememberMe")
                .when()
                .post("/login")
                .then()
                .log().all()
                .statusCode(200)
                .extract()
                .cookie("NOPCOMMERCE.AUTH");

        String body = "product_attribute_72_5_18=53" +
                "&product_attribute_72_6_19=54" +
                "&product_attribute_72_3_20=57" +
                "&addtocart_72.EnteredQuantity=1";

        given()
                .contentType("application/x-www-form-urlencoded; charset=UTF-8")
                .cookie("NOPCOMMERCE.AUTH", authCookie)
                .body(body)
                .when()
                .post("/addproducttocart/details/72/1")
                .then()
                .log().all()
                .statusCode(200)
                .body("success", is(true))
                .body("message",is("The product has been added to your <a href=\"/cart\">shopping cart</a>"));
    }

    @Test
    void addToCartAsAuthorizedSizeInWebTest() {

        String authCookieValue = given()
                .contentType("application/x-www-form-urlencoded; charset=UTF-8")
                .formParam("Email", email)
                .formParam("Password",password)
                .when()
                .post("/login")
                .then()
                .log().body()
                .log().cookies()
                .statusCode(302)
                .extract()
                .cookie(authCookieName);

        String body = "product_attribute_72_5_18=53" +
                "&product_attribute_72_6_19=54" +
                "&product_attribute_72_3_20=57" +
                "&addtocart_72.EnteredQuantity=1";

        String cartSize = given()
                .contentType("application/x-www-form-urlencoded; charset=UTF-8")
                .cookie(authCookieName, authCookieValue)
                .body(body)
                .when()
                .post("/addproducttocart/details/72/1")
                .then()
                .log().all()
                .statusCode(200)
                .body("success", is(true))
                .body("message",is("The product has been added to your <a href=\"/cart\">shopping cart</a>"))
                .extract()
                .path("updatetopcartsectionhtml");

        open("/Themes/DefaultClean/Content/images/logo.png");
        Cookie authCookie = new Cookie(authCookieName, authCookieValue);
        WebDriverRunner.getWebDriver().manage().addCookie(authCookie);

        open("");
        $(".cart-qty").shouldHave(text(cartSize));
    }

    @Test
    void addToCartWithAllureTest() {

        String authCookieValue = getAuthCookie(email, password);

        String body = "product_attribute_72_5_18=53" +
                "&product_attribute_72_6_19=54" +
                "&product_attribute_72_3_20=57" +
                "&addtocart_72.EnteredQuantity=1";

        String cartSize = getCartSize(body, authCookieValue);

        step("Open minimal content, because cookie can be set when site is opened", () ->
                open("/Themes/DefaultClean/Content/images/logo.png"));

        step("Set cookie to to browser", () -> {
            Cookie authCookie = new Cookie(authCookieName, authCookieValue);
            WebDriverRunner.getWebDriver().manage().addCookie(authCookie);
        });

        step("Open main page", () ->
                open(""));
        step("Check cart size", () ->
                $(".cart-qty").shouldHave(text(cartSize)));
    }

    @Test
    @Tag("demoWebShop")
    @DisplayName("Change user first name in profile")
    void changeUserFirstNameInProfileTest() {

        String authCookieValue = getAuthCookie(email, password);

        step("Open minimal content, because cookie can be set when site is opened", () ->
                open("/Themes/DefaultClean/Content/images/logo.png"));

        step("Set cookie to to browser", () -> {
            Cookie authCookie = new Cookie(authCookieName, authCookieValue);
            WebDriverRunner.getWebDriver().manage().addCookie(authCookie);
        });

        step("Open profile page", () ->
                account.openpage());

        step("Change user first name", () -> {
                account.changeUserFirstName(FIRST_NAME)
                       .saveChanges();
        });

        step("Check user first name", () ->
                account.checkFirstName(FIRST_NAME));
    }

    @Step("Get authorization cookie")
    String getAuthCookie(String email, String password) {
        return given()
                .filter(withCustomTemplates())
                .contentType("application/x-www-form-urlencoded; charset=UTF-8")
                .formParam("Email", email)
                .formParam("Password",password)
                .when()
                .post("/login")
                .then()
                .log().body()
                .log().cookies()
                .statusCode(302)
                .extract()
                .cookie(authCookieName);
    }

    @Step("Get cart size")
    String getCartSize(String body, String authCookieValue) {
        return given()
                .filter(withCustomTemplates())
                .contentType("application/x-www-form-urlencoded; charset=UTF-8")
                .cookie(authCookieName, authCookieValue)
                .body(body)
                .when()
                .post("/addproducttocart/details/72/1")
                .then()
                .log().all()
                .statusCode(200)
                .body("success", is(true))
                .body("message",is("The product has been added to your <a href=\"/cart\">shopping cart</a>"))
                .extract()
                .path("updatetopcartsectionhtml");
    }
}
