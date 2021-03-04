package StepDefinition;

import Page.SearchPage;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.model.Log;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.vimalselvam.cucumber.listener.Reporter;
import io.cucumber.core.backend.TestCaseState;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.util.List;

public class SearchStepDefinition {
    protected WebDriver driver;
    protected SearchPage page;
    ExtentReports reports;
    ExtentHtmlReporter htmlReporter;
    ExtentTest test;


    @Before("@Search")
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        driver = new ChromeDriver();
        page = new SearchPage(driver);
        driver.get("https://www.pedidosya.com.uy/");
        reports = new ExtentReports();
        htmlReporter = new ExtentHtmlReporter("/Users/nadiasanchez/IdeaProjects/PedidosYa/com.cucumber.listener.ExtentCucumberFormatter:target/report/report-extent.html");
        reports.attachReporter(htmlReporter);


    }

    @Given("user is in the page")
    public void user_is_in_the_page() {
        Assert.assertEquals(driver.getTitle(), "Delivery Online - Elegí, Pedí y Ya | PedidosYa");
      test=reports.createTest("user_is_in_the_page");
      test.log(Status.PASS,"Given user is in the page");
    }

    @And("user search pizza")
    public void user_search_pizza() {
        page.setSearch("pizza");
       test=reports.createTest("user_search_pizza");
       test.log(Status.PASS,"And user search pizza");
    }

    @And("click on search button")
    public void click_on_search_button() {
        page.clickSearchButton();
        test=reports.createTest("click_on_search_button");
        test.log(Status.PASS,"And click on search button");
    }

    @And("user insert an address")
    public void user_insert_an_address() {
        page.setAddress("Rambla Republica de Argentina", "1205");
        test=reports.createTest("user_insert_an_address");
        test.log(Status.PASS,"And user insert an address");
    }

    @When("a list a option is display")
    public void a_list_a_option_is_display() {
        Assert.assertEquals(driver.getTitle(), "Delivery para Rambla Republica de Argentina 1205, Montevideo | Pedí Online con PedidosYa");
        test=reports.createTest("a_list_a_option_is_display");
        test.log(Status.PASS,"Then a list a option is display");


    }

    @Then("count results of search")
    public void count_results_of_search() {
        System.out.println("Total of results: " + page.totalSearchResults());
        System.out.println("Results in current page: " + page.resultInCurrentPage());
        test=reports.createTest("count_results_of_search").createNode("2");
        test.log(Status.PASS,"And count results of search");
        test.log(Status.PASS,String.valueOf(page.totalSearchResults()));
        test.log(Status.PASS,String.valueOf(page.resultInCurrentPage()));
    }

    @When("user add a filter")
    public void user_add_a_filter() {
        page.addFilter();
    }

    @And("count results with filter")
    public void a_list_a_of_results_with_filter_is_display() {
        System.out.println("Total with filters apply: " + page.totalWithFilter());
        test= reports.createTest("a_list_a_of_results_with_filter_is_display").createNode("3");
        test.log(Status.PASS,"Filter"+String.valueOf(page.totalWithFilter()));
        reports.setTestRunnerOutput("count results with filter");
    }

    @When("user sort results alphabetically")
    public void user_sort_results_alphabetically() {
        page.setOrderAlphabetic("Mejor puntuados");
    }

    @Then("is display the results sorted")
    public void is_display_the_results_sorted() {
        System.out.println("if returns true is correctly order by rating: " + page.bestScored());
        test=reports.createTest("is display the results sorted");
        test.log(Status.PASS,String.valueOf(page.bestScored()));

    }

    @Then("return ratings of the first page")
    public void ratings_of_the_first_page() {
         test=reports.createTest("ratings_of_the_first_page");
        for (int i = 0; i < page.ratingOfFirstPage().length; i++) {
            System.out.println(page.ratingOfFirstPage()[i]);
            test.log(Status.PASS,page.ratingOfFirstPage()[i]);
        }

    }
    @When("open first result")
    public void open_first_result(){
        page.clickFirstResult();
    }

    @Then("show details")
    public void show_details(){
        System.out.println("Distance: "+page.distance());
        System.out.println("Open hours for everyday of the week: "+page.openHours());
        System.out.println("Velocity rating "+page.velocityRating());
        System.out.println("Food rating "+page.foodRating());
        System.out.println("Service rating "+page.serviceRating());
        System.out.println(page.firstReview());
    }

    @After("@Search")
    public void tearDown() {
      //  driver.quit();
    }


}
