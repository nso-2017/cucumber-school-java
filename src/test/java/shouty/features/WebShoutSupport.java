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
        shout(message);
        rememberMessageShoutedBy(message, "Sean");

    }

    @Override
    public List<String> getMessagesHeardBy(String name) {
        loginAs(name);
        return getMessages();
    }

    private void loginAs(String personName) {
        currentBrowser = getBrowserFor(personName);
        System.out.println("Getting the stuff /? for " + personName);
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

    private WebDriver getBrowserFor(String personName) {
        if (!browsers.containsKey(personName)) {


            //  WebDriver b=new FirefoxDriver();
            WebDriver b = null;
            try {
                b = new FirefoxDriver();
                if (false) throw new MalformedURLException();
//                 System.out.println("http://localhost:9515");
//
//
//
//                Map<String, Object> chromeOptions = new HashMap<String, Object>();
//                chromeOptions.put("binary", "C:\\Program Files (x86)\\Google\\Chrome\\Application\\chrome.exe");
//                DesiredCapabilities capabilities = DesiredCapabilities.chrome();
//                capabilities.setCapability("CAPABILITY", chromeOptions);
//                b= new RemoteWebDriver(new URL("http://localhost:9515"), capabilities);
//                b.get("http://www.google.com");
            } catch(MalformedURLException e){
//                System.out.println("Bad things happen to good people " + e.getStackTrace());
//
            }

            browsers.put(personName,b);



        }
        return browsers.get(personName);
    }
}





















