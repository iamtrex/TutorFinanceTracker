package com.rweqx.io;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDate;

public class FileBackup {

    public static boolean backupFile(String fileName){
        String date = LocalDate.now().toString();
        String fileWithoutExt = fileName.substring(0, fileName.lastIndexOf("."));
        String ext = fileName.substring(fileName.lastIndexOf("."));
        String backupName = fileWithoutExt + " " + date + ext;

        File og = new File(fileName);
        File back = new File(backupName);

        try {
            if(back.exists()){
                back.delete();
            }
            Files.copy(og.toPath(), back.toPath());

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }catch(Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}

