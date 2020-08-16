package com.zeuslearning.automation.io;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Set;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class JSONFileOps extends BasicFileOps {

    JSONParser parser;
    HashMap<String, Object> jsonData = new HashMap<String, Object>();
    FileReader fr;

    public String readAll() {
        return null;
    }

    public HashMap<String, Object> readJson(String filePath) {
        parser = new JSONParser();
        try {
            fr = new FileReader(filePath);
            Object obj = parser.parse(fr);
            JSONObject jsonObject = (JSONObject) obj;
            Set<String> keys = jsonObject.keySet();
            String[] a = keys.toArray(new String[keys.size()]);
            for (int i = 0; i < a.length; i++) {
                jsonData.put(a[i], jsonObject.get(a[i]));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (fr != null) {
            try {
                fr.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return jsonData;
    }

    public boolean write(String filePath, JSONObject data) {

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(data);
        try {
            FileWriter file = new FileWriter(filePath);
            file.write(json);
            file.flush();
            file.close();
        } catch (IOException e) {
            return false;
        }
        return true;
    }
}
