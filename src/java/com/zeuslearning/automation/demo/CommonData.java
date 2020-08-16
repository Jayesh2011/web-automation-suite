package com.zeuslearning.automation.demo;

import java.util.LinkedHashMap;

public class CommonData {

    public LinkedHashMap<String, Integer> fullHD = new LinkedHashMap<String, Integer>();
    public LinkedHashMap<String, Integer> laptop = new LinkedHashMap<String, Integer>();
    public LinkedHashMap<String, Integer> mobile = new LinkedHashMap<String, Integer>();
    public LinkedHashMap<String, Integer> tabletLandscape = new LinkedHashMap<String, Integer>();
    public LinkedHashMap<String, Integer> tabletPortrait = new LinkedHashMap<String, Integer>();

    public CommonData() {
        fullHD.put("height", 1080);
        fullHD.put("width", 1920);
        laptop.put("height", 768);
        laptop.put("width", 1366);
        mobile.put("height", 650);
        mobile.put("width", 320);
        tabletLandscape.put("height", 768);
        tabletLandscape.put("width", 1024);
        tabletPortrait.put("height", 1024);
        tabletPortrait.put("width", 768);
    }
}
