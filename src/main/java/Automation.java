import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.util.concurrent.TimeUnit;
import java.io.File;
import java.time.Duration;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import java.io.*;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.usermodel.HSSFRow;

public class Automation {
    public static WebDriver driver;
    public static Duration waitDuration = Duration.ofSeconds(120);
    public static WebDriverWait wait;
    public static String chromeD = "C:\\chromedriver.exe";
    public static long pending_for_processing;
    static Logger log = Logger.getLogger(Automation.class);

    public static void setLog(String msg) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        log.info( " ---- "  + dtf.format(now) + " ---- " + msg);
    }

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
        String logFolder = "C:\\logs";
        revCreateDirectory(logFolder);
        PropertyConfigurator.configure("ssrc/log4j.properties");
        System.setProperty("webdriver.chrome.driver", chromeD);

        driver=new ChromeDriver();
        wait = new WebDriverWait(driver, waitDuration);

        driver.get("https://annalectindia.darwinbox.in");

        driver.manage().window().maximize();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        log.info("********************** " + dtf.format(now) + " **********************");
        setLog("Automation Started on");
        String mainFolder = "C:\\Users\\" + System.getProperty("user.name") +"\\Downloads\\AutomationFolderCreation";
        revCreateDirectory(mainFolder);
        setLog("AutomationFolderCreation folder created at Downloads location");
    }

    public static void login() throws Exception{
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("okta-signin-username"))).sendKeys("ajay.kinage@omnicommediagroup.com");
        setLog("Username entered");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("okta-signin-password"))).sendKeys("OmniMay@2022");
        setLog("Password entered");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("okta-signin-submit"))).click();
        setLog("Submitted");
    }

    public static void verify_okta_code() throws Exception{
        Thread.sleep(20000);
        driver.findElement(By.xpath("//*[@id=\"form61\"]/div[2]")).click();
        setLog("Okta Code Verified!");
        Thread.sleep(20000);

//        boolean submitbuttonPresence = driver.findElement(By.xpath("/html/body/div[2]/div/div[1]/div/form/div/div/div/div[3]/button[1]")).isDisplayed();
//        if(submitbuttonPresence) {
//            driver.findElement(By.xpath("/html/body/div[2]/div/div[1]/div/form/div/div/div/div[3]/button[1]")).click();
//            setLog("Modal Closed!");
//            Thread.sleep(1500);
//        }
        driver.findElement(By.tagName("body")).sendKeys(Keys.END);
        Thread.sleep(5000);
        driver.findElement(By.xpath("/html/body/div[2]/div/nav/div[1]/div[2]/div[2]/div/a")).click();
        setLog("Navigated to Admin access module");
        setLog("Redirecting to Travel & Expense module");
    }

    public static void pending_and_processing() throws Exception{
//        Thread.sleep(2000);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/div[2]/div/section/div/div[4]/div[1]/ul/li[2]/a"))).click();
        setLog("Selecting Pending for processing Tab");
        Thread.sleep(10000);
    }

    public static void filter_data() throws Exception {
        driver.findElement(By.xpath("/html/body/div[2]/div/section/div/div[2]/form/button")).click();
        setLog("Opening the filter");
        driver.manage().timeouts().implicitlyWait(40, TimeUnit.SECONDS);
//        Thread.sleep(5000);
        Thread.sleep(5000);

//        to click the clear button
//        driver.findElement(By.xpath("/html/body/div[2]/div/div[4]/div/form/div[1]/div[2]/div[1]/div[1]/span")).click();

//        Selecting date (i.e. 01-07-2020) from the dropdown to filter the data

//        Opening the calander dropdown
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("ReimbAdminFilters_created_from_date"))).click();
//          Thread.sleep(1000);

//        Opening the dropdown to select the month
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/div[3]/div/div/select[1]"))).click();
//          Thread.sleep(1000);

//        Selecting the month i.e. July Month
        driver.findElement(By.xpath("/html/body/div[3]/div/div/select[1]/option[1]")).click();

//          Thread.sleep(1000);

//        Opening the dropdown to select the year
//        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/div[3]/div/div/select[2]"))).click();
//          Thread.sleep(1000);

//        Selecting the year i.e. 2020
//        int year = Calendar.getInstance().get(Calendar.YEAR);
//        int difference = year - 2020;


//        driver.findElement(By.xpath("/html/body/div[3]/div/div/select[2]/option[" + (11 -  difference) + "]")).click();
//        driver.findElement(By.xpath("/html/body/div[3]/div/div/select[2]/option[]")).click();
        //          Thread.sleep(1000);

//        Selecting the date i.e. 01
        driver.findElement(By.xpath("/html/body/div[3]/table/tbody/tr[1]/td[7]")).click();
//          Thread.sleep(1000);
        setLog("Selected date for Created Date filter");

        driver.findElement(By.id("apply_filter")).click();
        setLog("Filter Applied");
        Thread.sleep(30000);
    }

    public static void download_pending_processes() throws Exception {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/div[2]/div/section/div/div[2]/form/button")));
        WebElement p = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/div[2]/div/section/div/div[4]/div[1]/ul/li[2]/a/span")));
        String process = p.getText();
        pending_for_processing = Integer.parseInt(process);

        driver.manage().timeouts().implicitlyWait(120, TimeUnit.SECONDS);
        System.out.println(pending_for_processing);

        int k = 2;
        for(int i = 1; i <= pending_for_processing; i++) {
            String string_id = "/html/body/div[2]/div/section/div/div[4]/form/div[1]/div/div/table/tbody/tr[" + i +"]/td[2]";
            String string_name = "/html/body/div[2]/div/section/div/div[4]/form/div[1]/div/div/table/tbody/tr[" + i + "]/td[5]/a[2]";
            String string_title = "/html/body/div[2]/div/section/div/div[4]/form/div[1]/div/div/table/tbody/tr[" + i + "]/td[4]/a";
            String string_date = "/html/body/div[2]/div/section/div/div[4]/form/div[1]/div/div/table/tbody/tr[" + i + "]/td[6]/span[2]";
            String string_claimedAmount = "/html/body/div[2]/div/section/div/div[4]/form/div[1]/div/div/table/tbody/tr[" + i + "]/td[7]";
            String string_approvedAmount = "/html/body/div[2]/div/section/div/div[4]/form/div[1]/div/div/table/tbody/tr[" + i + "]/td[8]";
            String string_approvedBy = "/html/body/div[2]/div/section/div/div[4]/form/div[1]/div/div/table/tbody/tr[" + i + "]/td[9]/a[1]";
            String string_approvedOn = "/html/body/div[2]/div/section/div/div[4]/form/div[1]/div/div/table/tbody/tr[" + i + "]/td[9]";

            // to get the request id
            WebElement newId = driver.findElement(By.xpath(string_id));
            String id = newId.getText();

            // to get the name along with the employee code/id
            WebElement newName = driver.findElement(By.xpath(string_name));
            String name = newName.getText();

            // to get the employee id
            String empId = getEmpId(name);
            String[] properName = name.split("\\(");

            // to get the title
            WebElement getTitle = driver.findElement(By.xpath(string_title));
            String title = getTitle.getText();

            // to get the claimed date
            WebElement getClaimedDate = driver.findElement(By.xpath(string_date));
            String claimedDate = getClaimedDate.getText();

            // to get the claimed amount
            WebElement getClaimedAmount = driver.findElement(By.xpath(string_claimedAmount));
            String claimedAmount = getClaimedAmount.getText();

            // to get the claimed amount
            WebElement getApprovedAmount = driver.findElement(By.xpath(string_approvedAmount));
            String approvedAmount = getApprovedAmount.getText();

            // to get the claimed amount
            WebElement getApprovedBy = driver.findElement(By.xpath(string_approvedBy));
            String approvedBy = getApprovedBy.getText();

            // to get the claimed amount
            WebElement getApprovedOn = driver.findElement(By.xpath(string_approvedOn));
            String approvedOn = getApprovedOn.getText();

//            excelReport(empId, properName[0], id, title, name, claimedDate, claimedAmount, approvedAmount, approvedBy, approvedOn);

            String folderName = id + "-" + name;
            String folderPath = "C:\\Users\\" + System.getProperty("user.name") +"\\Downloads\\AutomationFolderCreation\\" + folderName;
            revCreateDirectory(folderPath);
            setLog("Folder Created for " + folderName);

            driver.findElement(By.xpath("/html/body/div[2]/div/section/div/div[4]/form/div[1]/div/div/table/tbody/tr[" + i + "]/td[11]/div/div/button")).click();
            Thread.sleep(1000);
            driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);

            driver.findElement(By.xpath("/html/body/div[2]/div/section/div/div[4]/form/div[1]/div/div/table/tbody/tr[" + i + "]/td[11]/div/ul/li[1]/a")).click();
            setLog("PDF Downloaded");
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
                            setLog(filesPDF[j].getName() + " moved to " + destFile);
                            System.out.println("PDF successfully copied");
                        } catch (IOException e) {
                            setLog("Error moving pdf files: " + e.getMessage());
                            e.printStackTrace();
                        }
                        System.out.println(filesPDF[j].getName());
                    }
                }

                driver.findElement(By.xpath("/html/body/div[2]/div/section/div/div[4]/form/div[1]/div/div/table/tbody/tr["+ i +"]/td[11]/div/div/button")).click();
                Thread.sleep(1000);
                driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);

                driver.findElement(By.xpath("/html/body/div[2]/div/section/div/div[4]/form/div[1]/div/div/table/tbody/tr["+ i +"]/td[11]/div/ul/li[2]/a")).click();
                driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
                setLog("ZIP Downloaded");
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
                            setLog(filesZIP[z].getName() + " moved to " + destFile);
                            System.out.println("ZIP successfully copied");
                        } catch (IOException e) {
                            setLog("Error moving pdf files: " + e.getMessage());
                            e.printStackTrace();
                        }
                        System.out.println(filesZIP[z].getName());
                    }
                }

                Thread.sleep(3000);
//                excelReport(empId, properName[0], id, title, name, claimedDate, claimedAmount, approvedAmount, approvedBy, approvedOn);
            } catch (Exception e) {
                setLog("Error moving files: " + e.getMessage());
                System.err.println(e.getMessage());
            }
            if(i % 10 == 0) {
                if(k > 6) {
                    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/div[2]/div/section/div/div[4]/form/div[1]/div/div/table/tfoot/tr/td/a[6]"))).click();
                    k++;
                    Thread.sleep(1000);
                    driver.findElement(By.tagName("body")).sendKeys(Keys.HOME);
                    driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
                    setLog("Navigated to next page");
                    Thread.sleep(3000);
                } else {
                    driver.findElement(By.xpath("/html/body/div[2]/div/section/div/div[4]/form/div[1]/div/div/table/tfoot/tr/td/a["+ k +"]")).click();
                    k++;
                    Thread.sleep(1000);
                    driver.findElement(By.tagName("body")).sendKeys(Keys.HOME);
                    driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
                    setLog("Navigated to next page");
                    Thread.sleep(3000);
                }
            }
        }

        log.info("********************** Exited **********************");
    }

    public static void excelReport(String empID, String name, String expID, String claimTitle, String claimBy, String claimDate, String expAmount, String appAmount, String approvedBy, String approvedOn) throws IOException {
        try
        {
            String filename = "src\\report\\example.xls";
            HSSFWorkbook workbook = new HSSFWorkbook();
            HSSFSheet sheet = workbook.createSheet("January");
            HSSFRow rowhead = sheet.createRow((short)0);

            rowhead.createCell(0).setCellValue("Employee ID");
            rowhead.createCell(1).setCellValue("Employee Name");
            rowhead.createCell(2).setCellValue("Expense ID");
            rowhead.createCell(3).setCellValue("Claim Title");
            rowhead.createCell(4).setCellValue("Claim By");
            rowhead.createCell(5).setCellValue("Claim Date");
            rowhead.createCell(6).setCellValue("Expense Amount");
            rowhead.createCell(7).setCellValue("Approved Amount");
            rowhead.createCell(8).setCellValue("Approved By");
            rowhead.createCell(9).setCellValue("Approved On");

            for(int j = 1; j < pending_for_processing; j++) {
                HSSFRow row = sheet.createRow((short)j);
                int k = 0;
                row.createCell(k).setCellValue(empID);
                k++;
                row.createCell(k).setCellValue(name);
                k++;
                row.createCell(k).setCellValue(expID);
                k++;
                row.createCell(k).setCellValue(claimTitle);
                k++;
                row.createCell(k).setCellValue(claimBy);
                k++;
                row.createCell(k).setCellValue(claimDate);
                k++;
                row.createCell(k).setCellValue(expAmount);
                k++;
                row.createCell(k).setCellValue(appAmount);
                k++;
                row.createCell(k).setCellValue(approvedBy);
                k++;
                row.createCell(k).setCellValue(approvedOn);
            }
            FileOutputStream fileOut = new FileOutputStream(filename);
            workbook.write(fileOut);
            fileOut.close();
            workbook.close();
            setLog("Data Added to report successfully");
            System.out.println("Data Added to report successfully");
        }
        catch (Exception e)
        {
            setLog("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static String getEmpId(String name) {
        String[] one = name.split("\\(");
        System.out.println(one[0]);
        String[] last = one[1].split("\\)");
        return last[0];
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
