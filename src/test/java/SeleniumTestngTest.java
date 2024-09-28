import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.openqa.selenium.interactions.Actions;


// This test class inherits BasicSetupTest class, where the browser is initialized
// browser variable is available here as it's inherited, so you'll have it available at any place
public class SeleniumTestngTest extends BasicSetupTest {

    @Test
    public void abTestingPageHasSpecificTextTest() throws InterruptedException {
        browser.get("https://the-internet.herokuapp.com/");
        browser.manage().window().fullscreen();
        //Thread.sleep(500);

        WebElement abTestingTaskLink = browser.findElement(By.linkText("A/B Testing"));
        browser.findElement(By.linkText("A/B Testing")).click();
        //Thread.sleep(500);
        Assert.assertTrue(browser.getPageSource().contains("A/B Test Control"));
    }


    @Test
    public void addRemoveElementsTest() throws InterruptedException {
        // Open the "Add/Remove Elements" page
        browser.get("https://the-internet.herokuapp.com/add_remove_elements/");
        //Thread.sleep(500);

        // Add 3 "Delete" buttons
        browser.findElement(By.xpath("//button[text()='Add Element']")).click();
        browser.findElement(By.xpath("//button[text()='Add Element']")).click();
        browser.findElement(By.xpath("//button[text()='Add Element']")).click();

        // Check that the first "Delete" button has appeared and is visible
        WebElement deleteButton = browser.findElement(By.className("added-manually"));
        Assert.assertTrue(deleteButton.isDisplayed(), "The button must be visible");

        // Remove all "Delete" buttons
        browser.findElement(By.className("added-manually")).click();
        browser.findElement(By.className("added-manually")).click();
        browser.findElement(By.className("added-manually")).click();

        // Check that there are no more buttons
        boolean isElementPresent = browser.findElements(By.className("added-manually")).isEmpty();
        Assert.assertTrue(isElementPresent, "The buttons should be removed");
    }


    @Test
    public void checkboxesTest() throws InterruptedException {
        browser.get("https://the-internet.herokuapp.com/checkboxes");
        //Thread.sleep(500);

        // Click on both checkboxes
        if (!browser.findElement(By.xpath("//*[@id='checkboxes']/input[1]")).isSelected()) {
            browser.findElement(By.xpath("//*[@id='checkboxes']/input[1]")).click();
        }
        if (!browser.findElement(By.xpath("//*[@id='checkboxes']/input[2]")).isSelected()) {
            browser.findElement(By.xpath("//*[@id='checkboxes']/input[2]")).click();
        }

        Assert.assertTrue(browser.findElement(By.xpath("//*[@id='checkboxes']/input[1]")).isSelected(), "Checkbox 1 is selected");
        Assert.assertTrue(browser.findElement(By.xpath("//*[@id='checkboxes']/input[2]")).isSelected(), "Checkbox 2 is selected");
    }


    @Test
    public void dropdownTest() throws InterruptedException {
        browser.get("https://the-internet.herokuapp.com/dropdown");
        //Thread.sleep(500);

        browser.findElement(By.id("dropdown")).click();
        browser.findElement(By.xpath("//*[@id='dropdown']/option[text()='Option 2']")).click();

        WebElement selectedOption = browser.findElement(By.xpath("//*[@id='dropdown']/option[@selected='selected']"));
        Assert.assertEquals(selectedOption.getText(), "Option 2", "Option 2 was selected");
    }


    @Test
    public void formAuthenticationTest() throws InterruptedException {
        browser.get("https://the-internet.herokuapp.com/login");
        //Thread.sleep(500);

        browser.findElement(By.id("username")).sendKeys("tomsmith");
        browser.findElement(By.id("password")).sendKeys("SuperSecretPassword!");

        // Click on the button Login
        browser.findElement(By.xpath("//button[@type='submit']")).click();
        //Thread.sleep(500);

        WebElement successMessage = browser.findElement(By.id("flash"));
        Assert.assertTrue(successMessage.getText().contains("You logged into a secure area!"), "Login was successful");

        // Click on the button Logout
        browser.findElement(By.xpath("//a[@href='/logout']")).click();
        //Thread.sleep(500);

        WebElement logoutMessage = browser.findElement(By.id("flash"));
        Assert.assertTrue(logoutMessage.getText().contains("You logged out of the secure area!"), "Logout was succerrful");
    }


    @Test
    public void dragAndDropTest() throws InterruptedException {
        browser.get("https://the-internet.herokuapp.com/drag_and_drop");
        //Thread.sleep(500);

        WebElement elementA = browser.findElement(By.id("column-a"));
        WebElement elementB = browser.findElement(By.id("column-b"));

        // Creating an object of the Actions class for dragging elements
        Actions actions = new Actions(browser);
        actions.dragAndDrop(elementA, elementB).perform();

        // Checking column changes
        String textA = browser.findElement(By.id("column-a")).getText();
        String textB = browser.findElement(By.id("column-b")).getText();

        Assert.assertEquals(textA, "B");
        Assert.assertEquals(textB, "A");
    }


    @Test
    public void horizontalSliderTest() throws InterruptedException {
        browser.get("https://the-internet.herokuapp.com/horizontal_slider");
        //Thread.sleep(500);

        WebElement slider = browser.findElement(By.xpath("//input[@type='range']"));

        new Actions(browser)
                .clickAndHold(slider)
                .moveByOffset(50, 0)
                .release()
                .perform();

        String sliderValue = slider.getAttribute("value");
        Assert.assertNotEquals(sliderValue, "4,5", "The slider is moved to the right");
    }

}
