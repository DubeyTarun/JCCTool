package StepDefinitions;

import io.cucumber.java.en.Then;

import java.io.IOException;

public class SpotsDistributionStepDefs extends ShowDistributionAction {


    @Then("^the tool will update the values in the variable$")
    public void fetchingDetailsFromSheet() throws IOException {
        addShowsNameToVariable();
    }

    @Then("^the tool distributes slots to specific comic for Non-themed shows$")
    public void sortingDetailsForNTS() throws IOException {
        sortingDetailsForNonThemedShows();
    }

    @Then("^the tool distributes slots to specific comic for Themed shows$")
    public void sortingDetailsForTS() throws IOException {
        sortingDetailsForThemedShows();
    }

    @Then("^the tool distributes slots to specific comic for Hosting shows$")
    public void sortingDetailsForHS() throws IOException {
        sortingDetailsForHostingShows();
    }

    @Then("^the tool collaborate all shows into one info$")
    public void collectingResponseForALLS() throws IOException {
        collectingResponseForAllShows();
    }

}
