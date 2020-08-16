package com.zeuslearning.automation.demo;

import org.openqa.selenium.By;

public class ZeusLearningAboutTabLocators {

    public static Object awardsSection = By.cssSelector("#awards");
    public static Object teamSection = By.cssSelector("#team");
    public static Object disableLeftArrowAwardsCarousel = By.cssSelector("#awards .carousel-control.visbilityHidden.left");
    public static Object activeLeftArrowAwardsCarousel = By.cssSelector("#awards [href='#myCarousel2'].carousel-control.left .fa-angle-left");
    public static Object disableLeftArrowTeamCarousel = By.cssSelector("#awards .carousel-control.visbilityHidden.left");
    public static Object activeLeftArrowTeamCarousel = By.cssSelector("#team [href='#myCarousel1'].carousel-control.left .fa-angle-left");
}
