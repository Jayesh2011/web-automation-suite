package com.zeuslearning.automation.unittests;

import java.util.HashMap;

import org.json.simple.JSONArray;

import com.zeuslearning.automation.io.JSONFileOps;

public class readJson {

    public static void main(String[] args) {
        JSONFileOps json = new JSONFileOps();
        HashMap<String, Object> rawJsonData = json.readJson("C:/Users/kenil.fadia/Documents/Downloads/1_5111.json");
        int tasks[] = getAllTasks((JSONArray)rawJsonData.get("Tasks"));
        for (int i = 0; i < tasks.length; i++)
        System.out.println(tasks[i]);
        getAllPathways((JSONArray)rawJsonData.get("Pathways"));
    }

    public static int[] getAllTasks(JSONArray taskArr) {
        int tasks[] = new int[taskArr.size()];
        for (int i = 0; i < tasks.length; i++) {
            tasks[i] = ((Long) taskArr.get(i)).intValue();
        }
        return tasks;
    }

    public static int[][] getAllPathways(JSONArray pathArr) {
        int path[][] = new int[pathArr.size()][((JSONArray)pathArr.get(0)).size()];
        for (int i = 0; i < path.length; i++) {
            JSONArray ja = (JSONArray)pathArr.get(i);
            for (int j = 0; j < path[0].length; j++) {
                path[i][j] = ((Long)ja.get(j)).intValue();
            }
        }
        return path;
    }
}
