package com.zeuslearning.automation.io;

import java.awt.AWTException;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.zeuslearning.automation.selenium.interactions.QueryHtml;

import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.shooting.ShootingStrategies;

import com.google.common.io.Files;

public class ScreenCapture extends BasicFileOps {

    private File scrFile;
    private WebDriver _driver;
    private QueryHtml query;
    private int counter;

    public ScreenCapture(Object driver) {
        _driver = (WebDriver) driver;
        scrFile = null;
        counter = 0;
        query = new QueryHtml(_driver);
    }

    public File getViewPort(String screenshotPath) {
        makeDirectory(screenshotPath);
        scrFile = ((TakesScreenshot) _driver).getScreenshotAs(OutputType.FILE);
        try {
            FileUtils.copyFile(scrFile, new File(screenshotPath));
        } catch (IOException e) {}
        return scrFile;
    }

    public File getScreenshotByElement(Object locator, String screenshotPath) throws Exception {
        WebElement element = (WebElement) query.findElement(locator);
        counter++;
        makeDirectory(screenshotPath);
        scrFile = ((TakesScreenshot) _driver).getScreenshotAs(OutputType.FILE);
        String timeStamp = new SimpleDateFormat("yyyy-MM-dd-hh-mm-ss-ss").format(new Date());
        String ext = Files.getFileExtension(screenshotPath);
        BufferedImage img = ImageIO.read(scrFile);
        Point p = element.getLocation();
        BufferedImage dest = img.getSubimage(p.getX(), p.getY(), element.getSize().getWidth(), element.getSize().getHeight());
        int index = screenshotPath.indexOf(ext);
        String path = screenshotPath.substring(0, index - 1) + timeStamp + counter + "." + ext;

        ImageIO.write(dest, ext, scrFile);
        FileUtils.copyFile(scrFile, new File(path));
        return scrFile;
    }

    public File getScreenshotByOffset(int xOffset, int yOffset, String screenshotPath) {
        counter++;
        Robot robot = null;
        BufferedImage bufferedImage = null;
        String timeStamp;
        String ext;
        File imageFile;
        try {
            robot = new Robot();
        } catch (AWTException e1) {}
        if (robot != null) {
            bufferedImage = robot.createScreenCapture(new Rectangle(new Dimension(xOffset, yOffset)));
        }
        timeStamp = new SimpleDateFormat("yyyy-MM-dd-hh-mm-ss-ss").format(new Date());
        ext = Files.getFileExtension(screenshotPath);
        int index = screenshotPath.indexOf(ext);
        String path = screenshotPath.substring(0, index - 1) + timeStamp + counter + "." + ext;
        imageFile = new File(path);
        makeDirectory(imageFile);
        try {
            if (bufferedImage != null) {
                ImageIO.write(bufferedImage, ext, imageFile);
            }
        } catch (IOException e) {}
        return imageFile;
    }

    public File getScreenshotCompletePage(String screenshotPath) {
        String[] fileSplit = screenshotPath.split("\\.");
        String fileExt = fileSplit[(fileSplit.length - 1)].toUpperCase();
        scrFile = new File(screenshotPath);
        makeDirectory(scrFile);

        Screenshot fpScreenshot = new AShot().shootingStrategy(ShootingStrategies.viewportPasting(1000)).takeScreenshot(_driver);
        try {
            ImageIO.write(fpScreenshot.getImage(), fileExt, scrFile);
        } catch (IOException e) {}
        return scrFile;
    }

    public void makeDirectory(File path) {
        File folder = path.getParentFile();
        if (!folder.exists()) {
            folder.mkdirs();
        }
    }

    public void makeDirectory(String path) {
        scrFile = new File(path);
        File folder = scrFile.getParentFile();
        if (!folder.exists()) {
            folder.mkdirs();
        }
    }
}
