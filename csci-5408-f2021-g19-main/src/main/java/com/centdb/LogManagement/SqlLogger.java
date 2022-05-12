package com.centdb.LogManagement;

import com.centdb.model.LogModel;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicReference;
import com.centdb.LogManagement.LogWriter.LogWriter;
public class SqlLogger {
    private LogModel logModel;
    public SqlLogger(LogModel logModel) {
        this.logModel= logModel;
    }
    public void generalLog(){
       ArrayList<String> data = new ArrayList<>();
       data.add(logModel.getDataBaseName());
       data.add(logModel.getTableName());
       data.add(logModel.getRowCount());
       data.add(logModel.getDataBaseState());
       String WriteStr = prepareString(data);
       String GeneralLogPath = "LogResources/LogGeneral.txt";
       new LogWriter().write(GeneralLogPath, WriteStr);
    }

    public void eventLog(){
        ArrayList<String> data = new ArrayList<>();
        data.add(logModel.getDataBaseName());
        data.add(logModel.getTypeOfQuery());
        data.add(logModel.getStartTime());
        data.add(logModel.getEndTime());
        String writeStr = prepareString(data);
        String eventLogPath = "LogResources/LogEvent.txt";
        new LogWriter().write(eventLogPath, writeStr);
    }

    public void queryLog(){
        ArrayList<String> data = new ArrayList<>();
        data.add(logModel.getDataBaseName());
        data.add(logModel.getTableName());
        data.add(logModel.getTypeOfQuery());
        data.add(logModel.getStartTime());
        data.add(logModel.getEndTime());
        String writeStr = prepareString(data);
        String QueryLogPath = "LogResources/LogQuery.txt";
        new LogWriter().write(QueryLogPath, writeStr);
    }


    public String prepareString(ArrayList<String> data){
        AtomicReference<String> resp= new AtomicReference<>("");
        data.forEach(str -> {
            resp.set(resp + str + "|");
        });
        resp.set(resp+"\n");
        return resp.toString();
    }
}
