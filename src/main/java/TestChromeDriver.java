import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class TestChromeDriver {

    private static ChromeDriverService service;

    public static String cookie = "3BF53B60796A47B836F92D6933BA6CAE";

    public static WebDriver getChromeDriver() throws IOException {
        System.setProperty("webdriver.chrome.driver","C:\\Users\\dhht\\AppData\\Local\\Google\\Chrome\\Application\\chrome.exe");
        // 创建一个 ChromeDriver 的接口，用于连接 Chrome（chromedriver.exe 的路径可以任意放置，只要在newFile（）的时候写入你放的路径即可）
        service = new ChromeDriverService.Builder().usingDriverExecutable(new File("D:\\SpiderDriver\\chromedriver.exe")) .usingAnyFreePort().build();
        service.start();
        // 创建一个 Chrome 的浏览器实例
        return new RemoteWebDriver(service.getUrl(), DesiredCapabilities.chrome());
    }

    public static void main(String[] args) throws IOException {

        WebDriver driver = TestChromeDriver.getChromeDriver();
        // 让浏览器访问 Baidu
        driver.get("https://seal.donghuahongtai.com/user/login");
        driver.manage().window().maximize();

        System.out.println(" Page title is: " +driver.getTitle());
        WebElement element =driver.findElement(By.className("ant-form-horizontal"));
        WebElement username = element.findElement(By.id("username"));
        username.sendKeys("ADMIN");
        WebElement password = element.findElement(By.id("password"));
        password.sendKeys("123456");
        element.submit();
//        new WebDriverWait(driver, 10).until(new ExpectedCondition() {
//            @Override
//            public Object apply(Object input) {
//                return ((WebDriver)input).getTitle().toLowerCase().startsWith("电视剧");
//            }
//        });

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        File file = new File("D:\\broswer.data");
        file.delete();
        file.createNewFile();
        FileWriter fw = new FileWriter(file);
        BufferedWriter bw = new BufferedWriter(fw);
        for (Cookie ck : driver.manage().getCookies()) {
            bw.write(ck.getName() + ";" + ck.getValue() + ";"
                    + ck.getDomain() + ";" + ck.getPath() + ";"
                    + ck.getExpiry() + ";" + ck.isSecure());
            bw.newLine();
        }
        bw.flush();
        bw.close();
        fw.close();
        System.out.println(" Page title is: " +driver.getTitle());
        // 关闭浏览器
        //driver.quit();
        // 关闭 ChromeDriver 接口
        //service.stop();
    }



}
