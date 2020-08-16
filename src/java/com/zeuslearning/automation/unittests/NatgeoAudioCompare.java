package com.zeuslearning.automation.unittests;

import java.io.File;

import com.zeuslearning.automation.io.LogToExternalFile;

public class NatgeoAudioCompare {

    public static void main(String[] args) throws Exception {
        AudioComparer audioComparer = new AudioComparer();
        String[] bookNew = getFileList("E:/Nat-Geo-Resources-New/myngconnect/v/webassets/ourworld/book");
        String[] bookOld = getFileList("E:/GIT/natgeo-ourworld-newcontent/leveldata-new");
        String[][] chapterNew = new String[3][6];
        String[][] chapterOld = new String[3][6];
        LogToExternalFile logger = new LogToExternalFile("E:\\natgeoAudio.log");

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 6; j++) {
                chapterNew[i][j] = bookNew[i] + "\\" + (j + 1) + "\\media\\audio";
                chapterOld[i][j] = bookOld[i] + "\\" + (j + 1) + "\\media\\audio";
                String[] audioNew;
                String[] audioOld;
                try {
                    audioNew = getFileList(chapterNew[i][j]);
                    audioOld = getFileList(chapterOld[i][j]);

                    for (int x = 0; x < audioNew.length; x++) {
                        boolean found = false;

                        System.out.println(audioNew[x]);
                        String[] pathSplit = audioNew[x].split("\\\\");
                        String fileNameNew = pathSplit[pathSplit.length - 1];
                        for (int y = 0; y < audioOld.length; y++) {
                            if (audioOld[y].contains(fileNameNew)) {
                                found = true;
                                audioComparer.compareAudioDuration(audioNew[x], audioOld[y], logger);
                                break;
                            }
                        }
                        if (!found) {
                            logger.warn("Audio `" + audioNew[x] + "` not found in old data");
                        }
                    }
                } catch (NullPointerException e) {}
            }
        }
        logger.closeAll();
    }

    public static String[] getFileList(String path) {
        File dir;
        try {
            dir = new File(path);
        } catch (Exception e) {
            return null;
        }
        File[] files = dir.listFiles();
        int length = files.length;
        if (length > 0) {
            String[] fileList = new String[length];
            for (int i = 0; i < length; i++) {
                fileList[i] = files[i].getAbsolutePath();
            }
            return fileList;
        }
        return null;
    }
}
