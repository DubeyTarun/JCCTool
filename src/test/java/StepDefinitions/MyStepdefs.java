package StepDefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.IOException;

public class MyStepdefs extends MyStepsActions {
    WebDriver driver;

    @Given("^the user launches the web browser$")
    public void launch_browser() throws InterruptedException {
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\HP\\IdeaProjects\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
//        driver.get("https://www.instagram.com/accounts/login/");
        driver.get("https://www.google.com/maps/uv?pb=!1s0x396db759273c8899%3A0x10267213a0cdcded!3m1!7e115!4shttps%3A%2F%2Flh5.googleusercontent.com%2Fp%2FAF1QipP-yV0E-Tu91AXfYI-YU_rFMQ8NfvTQkcuQYEXF%3Dw216-h218-n-k-no!5sjaipur%20comedy%20club%20-%20Google%20Search!15sCgIgAQ&imagekey=!1e10!2sAF1QipP-yV0E-Tu91AXfYI-YU_rFMQ8NfvTQkcuQYEXF&hl=en");
        Thread.sleep(2000);
    }

    @Then("^tool collects the data from JCC response$")
    public void toolCollectsTheData() throws IOException {
        collectDataFromJCCResponse();
    }

    @Then("^tool closes the browser$")
    public void toolClosesTheBrowser() {
        driver.close();
    }

    @Then("^tool collects the old comics data from JCC open mic$")
    public void toolCollectsTheOldComicsDataFromJCCOpenMic() throws IOException {
        collectOldComicsDataFromJCCDatabase();
    }

    @Then("^tool combine old comics names and number of times they have performed$")
    public void toolCombineOldComicsNamesAndNumberOfTimesTheyHavePerformed() {
        combineOldComicsDataIntoMap();
    }

    @Then("^tool collects the new comics data from JCC open mic$")
    public void toolCollectsTheNewComicsDataFromJCCOpenMic() throws IOException {
        collectNewComicsDataFromJCCDatabase();
    }

    @Then("^tool combine new comics names and number of times they have performed$")
    public void toolCombineNewComicsNamesAndNumberOfTimesTheyHavePerformed() {
        combineNewComicsDataIntoMap();
    }

    @Then("^tool update the database with the new comics name$")
    public void toolUpdateTheDatabaseWithTheNewComicsName() throws IOException {
        databaseUpdate();
    }

    @Then("^tool collects the comics name for \"([^\"]*)\" venue$")
    public void toolCollectsTheComicsNameForEachVenue(String number) throws IOException {
        collectComicNamesForEachVenue(number);
    }

    @Then("^tool creates new sheet for open mics$")
    public void toolCreatesNewSheetForOpenMics() throws IOException {
//        newSheetCreater();
    }

    @Then("^tool combine comic names and their contact number$")
    public void toolCombineComicNamesAndTheirContactNumber() {
        combineComicsNameWithMobileNumber();
    }
    

    @Then("^tool identifies which sorting it has to do$")
    public void toolIdentifiesWhichSortingItHasToDo() {
        identifyTypeOfToolRunning();
    }

}
