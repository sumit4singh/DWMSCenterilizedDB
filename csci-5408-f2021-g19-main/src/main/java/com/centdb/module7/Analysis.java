package com.centdb.module7;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Scanner;


public class Analysis {

	public void readInput() {
		String beginstr="count";
		String cmtstr="query";
		String dbName = "";
		System.out.println("Enter the db name");
		Scanner input = new Scanner(System.in);
		String lineNew=input.nextLine();
		String[] tokens= lineNew.split(" ");
		if(tokens[0].equalsIgnoreCase(beginstr) && tokens[1].equalsIgnoreCase(cmtstr)) {
			dbName = tokens[2];
			queresForDb(dbName);
		} else {
			System.out.println("Wrong Query");
		}
	}

	public void queresForDb(String dbName) {
		HashMap<String, Integer> tabledata = new HashMap<String, Integer>();
		List<String> result= ExtractText("C:\\sumit\\studies\\Dmws\\project\\csci-5408-f2021-g19\\LogResources\\LogQuery.txt", dbName);
		System.out.println("total " +result.size()  +" queries were perforemed on DB1");
		for(int i=0;i<result.size();i++) {
			String[] logs=  result.get(i).split("\\|");
			if(tabledata.containsKey(logs[1])) {
				tabledata.put(logs[1], tabledata.get(logs[1]) + 1);
			}else {
				tabledata.put(logs[1], 1);
			}
		}
		for(Entry<String, Integer> entry : tabledata.entrySet()) {
			System.out.println(entry.getKey());
			System.out.println(entry.getValue());
			System.out.println("total of " + entry.getValue() + " queries where performed on table " +
					entry.getKey() + " in DataBase " + dbName);
		}
	}

	public List<String> ExtractText(String fullFilePath, String dbName)
	{
		List<String> matchinglog = new ArrayList<String>();
		try {
			String data = "";
			data = new String(Files.readAllBytes(Paths.get(fullFilePath)));
			String[] logs= data.split("\n");
			for( String log : logs ) {
				log = log.toLowerCase();
				if(log.contains(dbName.toLowerCase())) {

					matchinglog.add(log);
				}
			}

		}
		catch (IOException e) {
			e.printStackTrace();
		}
		return matchinglog;

	}
}