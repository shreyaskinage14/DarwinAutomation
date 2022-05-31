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
import java.util.*;

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
        } else {
            System.out.println("Directory already exists!");
        }
    }

    public static void launchBrowser() throws Exception {
        System.setProperty("webdriver.chrome.driver", "extLibraries/chromedriver.exe");
        driver=new ChromeDriver();
//        driver.navigate().to("www.google.com");
        driver.get("https://annalectindia.darwinbox.in");
        driver.manage().window().maximize();

        String mainFolder = "C:\\Users\\" + System.getProperty("user.name") +"\\Downloads\\AutomationFolderCreation";
        revCreateDirectory(mainFolder);
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

    public static void filter_data() throws Exception {
        driver.findElement(By.xpath("/html/body/div[2]/div/section/div/div[2]/form/button")).click();
        driver.manage().timeouts().implicitlyWait(40, TimeUnit.SECONDS);
        Thread.sleep(5000);

//        to click the clear button
//        driver.findElement(By.xpath("/html/body/div[2]/div/div[4]/div/form/div[1]/div[2]/div[1]/div[1]/span")).click();

//        Selecting date (i.e. 01-07-2020) from the dropdown to filter the data

//        Opening the calander dropdown
          driver.findElement(By.id("ReimbAdminFilters_created_from_date")).click();
          Thread.sleep(1000);

//        Opening the dropdown to select the month
          driver.findElement(By.xpath("/html/body/div[3]/div/div/select[1]")).click();
          Thread.sleep(1000);

//        Selecting the month i.e. July Month
          driver.findElement(By.xpath("/html/body/div[3]/div/div/select[1]/option[7]")).click();
          Thread.sleep(1000);

//        Opening the dropdown to select the year
          driver.findElement(By.xpath("/html/body/div[3]/div/div/select[2]")).click();
          Thread.sleep(1000);

//        Selecting the year i.e. 2020
        int year = Calendar.getInstance().get(Calendar.YEAR);
        System.out.println(year);

        int difference = year - 2020;

        driver.findElement(By.xpath("/html/body/div[3]/div/div/select[2]/option[" + (11 -  difference) + "]")).click();
          Thread.sleep(1000);

//        Selecting the date i.e. 01
          driver.findElement(By.xpath("/html/body/div[3]/table/tbody/tr[1]/td[4]/a")).click();
          Thread.sleep(1000);

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
            String folderPath = "C:\\Users\\" + System.getProperty("user.name") +"\\Downloads\\AutomationFolderCreation\\" + folderName;
            revCreateDirectory(folderPath);

            driver.findElement(By.xpath("/html/body/div[2]/div/section/div/div[4]/form/div[1]/div/div/table/tbody/tr[" + i + "]/td[11]/div/div/button")).click();
            driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);

            driver.findElement(By.xpath("/html/body/div[2]/div/section/div/div[4]/form/div[1]/div/div/table/tbody/tr[" + i + "]/td[11]/div/ul/li[1]/a")).click();
            Thread.sleep(5000);

            try {
                String downloadPath = "C:\\Users\\" + System.getProperty("user.name") + "\\Downloads";
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
        File file = new File("C:\\Users\\" + System.getProperty("user.name") + "\\IdeaProjects\\DarwinAutomation");
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
