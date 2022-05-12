package com.centdb.LogManagement.LogWriter;

import java.io.FileWriter;

public class LogWriter {
    public void write(String filePath, String writeString){
        try{
            FileWriter logWriter = new FileWriter(filePath, true);
            logWriter.write(writeString);
            logWriter.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
