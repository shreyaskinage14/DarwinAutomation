import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import org.apache.commons.io.FileUtils;
import java.util.concurrent.TimeUnit;
import java.io.*;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Automation {
    public static WebDriver driver;
    public static long pending_for_processing;

    public static void revCreateDirectory(String path) {
        String directoryPath = path;
        File file = new File(directoryPath);
        if (!file.exists()) {
            if (file.mkdir()) {
                System.out.println("Directory is created!");
            } else {
                System.out.println("Failed to create directory!");
            }
        }
    }

    public static void launchBrowser() throws Exception {
        System.setProperty("webdriver.chrome.driver", "C:/Users/Administrator/Downloads/chromedriver_win32/chromedriver.exe");
        driver=new ChromeDriver();
//        driver.navigate().to("www.google.com");
        driver.get("https://annalectindia.darwinbox.in");
        driver.manage().window().maximize();
    }

    public static void login() throws Exception{
//        WebDriverWait wait = new WebDriverWait(driver);
        Thread.sleep(2000);
        driver.findElement(By.id("okta-signin-username")).sendKeys("ajay.kinage@omnicommediagroup.com");
        Thread.sleep(2000);
        driver.findElement(By.id("okta-signin-password")).sendKeys("OmniMay@2022");
        Thread.sleep(2000);
        driver.findElement(By.id("okta-signin-submit")).click();
    }

    public static void verify_okta_code() throws Exception{
        Thread.sleep(20000);
        driver.findElement(By.xpath("//*[@id=\"form61\"]/div[2]")).click();
        Thread.sleep(10000);

        boolean submitbuttonPresence = driver.findElement(By.xpath("/html/body/div[2]/div/div[1]/div/form/div/div/div/div[3]/button[1]")).isDisplayed();
        if(submitbuttonPresence == true) {
            driver.findElement(By.xpath("/html/body/div[2]/div/div[1]/div/form/div/div/div/div[3]/button[1]")).click();
        }
        Thread.sleep(5000);

        driver.findElement(By.xpath("/html/body/div[2]/div/nav/div[1]/div[2]/div[2]/div/a/div")).click();
    }

    public static void pending_and_processing() throws Exception{
        Thread.sleep(2000);
        driver.findElement(By.xpath("/html/body/div[2]/div/section/div/div[4]/div[1]/ul/li[2]/a")).click();
        Thread.sleep(20000);
    }

//    public static void close_modal() throws Exception {
//        Thread.sleep(2000);
//        boolean submitbuttonPresence = driver.findElement(By.xpath("/html/body/div[2]/div/div[1]/div/form/div/div/div/div[3]/button[1]")).isDisplayed();
//        if(submitbuttonPresence == true) {
//            driver.findElement(By.xpath("/html/body/div[2]/div/div[1]/div/form/div/div/div/div[3]/button[1]")).click();
//        }
//        Thread.sleep(5000);
//    }

    public static void filter_data() throws Exception {
        driver.findElement(By.xpath("/html/body/div[2]/div/section/div/div[2]/form/button")).click();

//        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy");
//        LocalDateTime to_date = LocalDateTime.now();
//        String stringTo_date = dtf.format(to_date);
//        LocalDateTime from_date = to_date.minusDays(365);
//        String stringFrom_date = dtf.format(from_date);
//
//        System.out.println("To_Date" + stringTo_date);
//        System.out.println("From_Date" + stringFrom_date);
//
//        System.out.println(to_date + " " + from_date);

        driver.manage().timeouts().implicitlyWait(40, TimeUnit.SECONDS);
        Thread.sleep(5000);

        driver.findElement(By.xpath("/html/body/div[2]/div/div[4]/div/form/div[1]/div[2]/div[1]/div[1]/span")).click();

        driver.findElement(By.id("apply_filter")).click();
        Thread.sleep(30000);
    }

    public static void download_pending_processes() throws Exception {
        WebElement p = driver.findElement(By.xpath("/html/body/div[2]/div/section/div/div[4]/div[1]/ul/li[2]/a/span"));
        String process = p.getText();
        pending_for_processing = Integer.parseInt(process);

        driver.manage().timeouts().implicitlyWait(120, TimeUnit.SECONDS);
        System.out.println(pending_for_processing);
        int k = 2;
        for(int i = 1; i <= pending_for_processing; i++) {
            String string_id = "/html/body/div[2]/div/section/div/div[4]/form/div[1]/div/div/table/tbody/tr[" + i +"]/td[2]";
            String string_name = "/html/body/div[2]/div/section/div/div[4]/form/div[1]/div/div/table/tbody/tr[" + i + "]/td[5]/a[2]";

            WebElement newId = driver.findElement(By.xpath(string_id));
            String id = newId.getText();

            WebElement newName = driver.findElement(By.xpath(string_name));
            String name = newName.getText();

            String folderName = id + "-" + name;
            String folderPath = "C:\\Users\\Administrator\\Downloads\\AutomationFolderCreation\\" + folderName;
            revCreateDirectory(folderPath);

            driver.findElement(By.xpath("/html/body/div[2]/div/section/div/div[4]/form/div[1]/div/div/table/tbody/tr[" + i + "]/td[11]/div/div/button")).click();
            driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);

            driver.findElement(By.xpath("/html/body/div[2]/div/section/div/div[4]/form/div[1]/div/div/table/tbody/tr[" + i + "]/td[11]/div/ul/li[1]/a")).click();
            Thread.sleep(5000);

            try {
                String downloadPath = "C:\\Users\\Administrator\\Downloads";
                File f = new File(downloadPath);

                FilenameFilter filterPDF = new FilenameFilter() {
                    @Override
                    public boolean accept(File f, String name) {
                        return name.endsWith(".pdf");
                    }
                };

                File[] filesPDF = f.listFiles(filterPDF);

                for (int j = 0; j < filesPDF.length; j++) {
                    if (filesPDF[j].getName().contains(id) && filesPDF[j].getName().contains("pdf")) {
                        try {
                            File srcFile = new File(downloadPath + "\\" + filesPDF[j].getName());
                            File destFile = new File(folderPath);
                            FileUtils.moveFileToDirectory(srcFile, destFile, false);
                            System.out.println("PDF successfully copied");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        System.out.println(filesPDF[j].getName());
                    }
                }

                driver.findElement(By.xpath("/html/body/div[2]/div/section/div/div[4]/form/div[1]/div/div/table/tbody/tr["+ i +"]/td[11]/div/div/button")).click();
                driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);

                driver.findElement(By.xpath("/html/body/div[2]/div/section/div/div[4]/form/div[1]/div/div/table/tbody/tr["+ i +"]/td[11]/div/ul/li[2]/a")).click();
                driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
                Thread.sleep(3000);

                FilenameFilter filterZIP = new FilenameFilter() {
                    @Override
                    public boolean accept(File f, String name) {
                        return name.endsWith(".zip");
                    }
                };

                File[] filesZIP = f.listFiles(filterZIP);

                for (int z = 0; z < filesZIP.length; z++) {
                    if (filesZIP[z].getName().contains(id) && filesZIP[z].getName().contains("zip")) {
                        try {
                            File srcFile = new File(downloadPath + "\\" + filesZIP[z].getName());
                            File destFile = new File(folderPath);
                            FileUtils.moveFileToDirectory(srcFile, destFile, false);
                            System.out.println("ZIP successfully copied");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        System.out.println(filesZIP[z].getName());
                    }
                }
                Thread.sleep(3000);
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
            if(i % 10 == 0) {
                if(k > 6) {
                    driver.findElement(By.xpath("/html/body/div[2]/div/section/div/div[4]/form/div[1]/div/div/table/tfoot/tr/td/a[6]")).click();
                    k++;
                    Thread.sleep(1000);
                    driver.findElement(By.tagName("body")).sendKeys(Keys.HOME);
                    driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
                    Thread.sleep(3000);
                } else {
                    driver.findElement(By.xpath("/html/body/div[2]/div/section/div/div[4]/form/div[1]/div/div/table/tfoot/tr/td/a["+ k +"]")).click();
                    k++;
                    Thread.sleep(1000);
                    driver.findElement(By.tagName("body")).sendKeys(Keys.HOME);
                    driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
                    Thread.sleep(3000);
                }
            }

        }
//
    }

    public static void log_file(){
        File file = new File("C:\\Users\\Administrator\\IdeaProjects\\DarwinAutomation");
    }

    public static void main(String[] args) throws Exception {
        Automation auto = new Automation();
        auto.launchBrowser();
        auto.login();
        auto.verify_okta_code();
        auto.pending_and_processing();
        auto.filter_data();
        auto.download_pending_processes();
    }

}