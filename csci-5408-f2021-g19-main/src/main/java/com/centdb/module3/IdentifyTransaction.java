package com.centdb.module3;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class IdentifyTransaction {

	public IdentifyTransaction() {
		
	}
	
	public List<String> readInput() {
		String beginstr="begin";
		String cmtstr="commit";
		String rollBack="rollback";
		System.out.println("Enter Query");
		System.out.println("\n>");
		Scanner input = new Scanner(System.in);
		List<String> lines = new ArrayList<String>();
		String lineNew=input.nextLine();
		lines.add(lineNew);
		if(lineNew.equalsIgnoreCase(beginstr)) {	
			while (input.hasNextLine()) {
				lineNew = input.nextLine();
				lines.add(lineNew);			
				if(lineNew.equalsIgnoreCase(cmtstr)) 			  
					break;
				if(lineNew.equalsIgnoreCase(rollBack)) {	
					lines = new ArrayList<String>();
					break;
				}
			}
		}
		return lines;
	}
}
