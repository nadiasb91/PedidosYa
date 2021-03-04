package Page;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;

import java.lang.reflect.Array;
import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class SearchPage {
    private WebDriver driver;

    public SearchPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(name = "street")
    WebElement addressInput;
    @FindBy(name = "optional")
    WebElement searchInput;
    @FindBy(id = "search")
    WebElement searchButton;
    @FindBy(name = "number")
    WebElement doorNumberInput;
    @FindBy(id = "detailedSearch")
    WebElement addressButton;
    @FindBy(id = "confirm")
    WebElement addressConfirmButton;
    @FindBy(xpath = "//*[@id=\"resultList\"]/li[@class=\"restaurant-wrapper peyaCard show \"]")
    List<WebElement> searchResults;
    @FindBy(xpath = "//ul[@class=\"pagination\"]/li[@data-auto=\"pagination_item\"]")
    List<WebElement> pagination;
    @FindBy(xpath = "//ul[@class=\"pagination\"]/li[@class=\"arrow next\"]")
    WebElement nextPage;
    @FindBy(xpath = "/html/body/div/div[1]/div[2]/div[1]/section[1]/div/dl[2]/dd/ul/li[5]/a")
    WebElement deliveryFilter;
    @FindBy(xpath = "//*[@id=\"drop1\"]/li")
    List<WebElement> orderList;
    @FindBy(xpath = "//*[@id=\"filterListContainer\"]/div/dl[2]/dd/ul/li[5]/a/i")
    WebElement totalWithFilter;
    @FindBy(xpath = "//*[@id=\"listContent\"]/footer/ul/li[2]/a")
    WebElement firstPage;
    @FindBy(xpath = "//*[@id=\"resultListContainer\"]/header/section[3]/a")
    WebElement orderSection;
    @FindBy(xpath = "//*[@id=\"resultList\"]/li[1]/section/header/h3/a")
    WebElement firstResult;
    @FindBy(xpath = "//*[@id=\"profileDetails\"]/span[1]")
    WebElement distance;
    @FindBy(xpath = "//*[@id=\"restaurantSchedule\"]/ul/li[1]/div/div[2]")
    WebElement openHours;
    @FindBy(xpath = "//*[@id=\"profileTabs\"]/button[2]")
    WebElement informationTab;
    @FindBy(xpath = "//*[@id=\"profileInfo\"]/div[2]/ul/li[1]/span[1]")
    WebElement velocityRating;
    @FindBy(xpath = "//*[@id=\"profileInfo\"]/div[2]/ul/li[2]/span[1]")
    WebElement foodRating;
    @FindBy(xpath = "//*[@id=\"profileInfo\"]/div[2]/ul/li[3]/span[1]")
    WebElement serviceRating;
    @FindBy(xpath = "//*[@id=\"profileTabs\"]/button[3]")
    WebElement reviewsTab;
    @FindBy(xpath = "//*[@id=\"reviewList\"]/li[1]")
    WebElement firstReview;


    public void setSearch(String searchCriteria) {
        searchInput.sendKeys(searchCriteria);

    }

    public void setAddress(String address, String doorNumber) {
        driver.switchTo().activeElement();
        addressInput.sendKeys(address);
        doorNumberInput.sendKeys(doorNumber);
        addressButton.click();
        driver.switchTo().activeElement();
        waitUntilClickable(addressConfirmButton);
        addressConfirmButton.click();
    }

    public void clickSearchButton() {
        searchButton.click();
    }

    public void waitUntilClickable(WebElement element) {

        FluentWait wait = new FluentWait(driver).
                pollingEvery(Duration.ofSeconds(2)).
                withTimeout(Duration.ofSeconds(10)).
                ignoring(ElementNotInteractableException.class);
        wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    public void waitUntilPresence(WebElement element, String message) {

        FluentWait wait = new FluentWait(driver).
                pollingEvery(Duration.ofSeconds(2)).
                withTimeout(Duration.ofSeconds(10)).
                ignoring(ElementNotInteractableException.class);
        wait.until(ExpectedConditions.textToBePresentInElement(element, message));
    }

    public int totalSearchResults() {
        // int firstPageResults = searchResults.size();
        int sum = 0;
        if (pagination.size() > 0) {
            int amountOfPages = (Integer.valueOf(pagination.get(pagination.size() - 1).getText()));
            int i = 1;
            while (i <= amountOfPages) {
                sum += searchResults.size();
                if (i != amountOfPages) {
                    nextPage.click();
                } else {
                    waitUntilClickable(firstPage);
                    firstPage.click();
                }
                i++;
            }
        }
        return sum;
    }

    public int resultInCurrentPage() {
        return searchResults.size();
    }

    public void addFilter() {
        deliveryFilter.click();
    }

    public String totalWithFilter() {
        //waitUntilPresence(totalWithFilter, totalWithFilter.getText());
        return totalWithFilter.getText();
    }

    /*el ejercicio dice albabeticamente, pero te redirecciona a un pagina de registro con captcha
     * lo hice con los mejor puntuados*/
    public void setOrderAlphabetic(String option) {
        orderSection.click();
        for (WebElement e : orderList) {
            if (e.findElement(By.tagName("a")).getText().equals(option)) {
                e.findElement(By.tagName("a")).click();
                break;
            }
        }

    }

    public boolean bestScored() {
        boolean check = false;
        double firstRating = 0;
        double mayor = 0;
        for (WebElement e : searchResults) {
            firstRating = Double.valueOf(e.findElement(By.xpath("./section/div/span/i")).getText());
            if (firstRating >= mayor)
                mayor = firstRating;
            check = true;
        }
        return check;

    }

    public String[] ratingOfFirstPage() {
        String[] arr = new String[searchResults.size()];
        int i = 0;
        for (WebElement e : searchResults) {
            arr[i] = e.findElement(By.xpath("./section/header/h3/a")).getText() + " - " + "Rating " + e.findElement(By.xpath("./section/div/span/i")).getText();
            i++;
        }
        return arr;
    }

    public void clickFirstResult() {
        firstResult.click();
    }

    public String distance() {
        return distance.getText();

    }

    public String openHours() {
        informationTab.click();
        return openHours.getText();
    }

    public String velocityRating() {
        return velocityRating.getText();
    }

    public String foodRating() {
        return foodRating.getText();
    }

    public String serviceRating() {
        return serviceRating.getText();
    }

    public String firstReview() {
        reviewsTab.click();
        String date = firstReview.findElement(By.xpath("./section/header/p")).getText();
        String comment = firstReview.findElement(By.xpath("./section/p")).getText();
        String rating = firstReview.findElement(By.xpath("./section/header/i")).getText();
        return "Date: " + date + "\n" + "Review: " + comment + "\n" + "Rating: " + rating;
    }


}
