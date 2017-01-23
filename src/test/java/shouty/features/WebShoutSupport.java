package shouty.features;

import cucumber.api.java.After;
import cucumber.api.java.Before;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import shouty.core.Person;
import shouty.web.ShoutyServer;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class WebShoutSupport extends ShoutSupport {

    private Map<String, WebDriver> browsers = new HashMap<>();
    private WebDriver currentBrowser;

    private ShoutyServer server = new ShoutyServer();

    @Before
    public void startServer() throws Exception {
        server.start(getPeople(), 3000);
    }

    @After
    public void stopServer() throws Exception {
        server.stop();
    }

    @After
    public void closeAllBrowsers() {
        for (WebDriver browser : browsers.values()) {
            browser.close();
        }
    }

    @Override
    public void seanShout(String message) {
        loginAs("Sean");
        Person S = getPeople().get("Sean");

        if(S.canShout(message)) {
            shout(message);
            rememberMessageShoutedBy(message, "Sean");
        }

    }

    @Override
    public List<String> getMessagesHeardBy(String name) {
        loginAs(name);
        return getMessages();
    }

    private void loginAs(String personName) {
        currentBrowser = getBrowserFor(personName);
        //System.out.println("Getting the stuff /? for " + personName);
        currentBrowser.get("http://192.168.134.190:3000/?name=" + personName);
    }

    private void shout(String message) {
        currentBrowser.findElement(By.name("message")).sendKeys(message);
        currentBrowser.findElement(By.cssSelector("button[type=submit]")).click();
    }

    private List<String> getMessages() {
        List<WebElement> elements = currentBrowser.findElements(By.cssSelector(".message"));
        return elements.stream().map(WebElement::getText).collect(Collectors.toList());
    }

    public int getCreditsOf(String name) {
        loginAs(name);
        return getCredits();
    }

    private int getCredits() {
        WebElement elements = currentBrowser.findElement(By.name("cred"));

        return new Integer(elements.getText().toString());
    }

    private WebDriver getBrowserFor(String personName) {
        if (!browsers.containsKey(personName)) {
           WebDriver b  = new FirefoxDriver();
            browsers.put(personName,b);
        }
        return browsers.get(personName);
    }
}





















