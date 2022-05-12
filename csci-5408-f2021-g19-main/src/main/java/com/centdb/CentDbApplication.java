package com.centdb;

import com.centdb.LogManagement.SqlLogger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.centdb.module8.MainMenu;

@SpringBootApplication
public class CentDbApplication {

	public static void main(String[] args) {
		// SpringApplication.run(CentDbApplication.class, args);
		MainMenu.showLoginMenu();


		//new SqlLogger().generalLog();
		//new SqlLogger().generalLog();
		//new SqlLogger().eventLog();
		//new SqlLogger().queryLog();


	}
}
