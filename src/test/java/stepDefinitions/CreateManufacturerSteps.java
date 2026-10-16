package stepDefinitions;

import io.cucumber.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import utils.Commons;
import utils.CreateManufacturers;
import utils.objects.Doc;
import utils.objects.Item;
import utils.objects.Manufacturer;

import java.util.List;

public class CreateManufacturerSteps {

    Manufacturer manufacturer;

    public static WebDriver driver = CreateClassSteps.driver;
    Commons commons = new Commons();

    @When("^user tries to create new manufacturers$")
    public void createManufacturer() {
        // check for the element to be present or not and then if the user is supposed to not have it then return aSSERT TRUE
        commons.home();
        CreateManufacturers tasksManufacturers = new CreateManufacturers();
        manufacturer = new Manufacturer();

        try{
            tasksManufacturers.createManufacturer(driver, manufacturer);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
