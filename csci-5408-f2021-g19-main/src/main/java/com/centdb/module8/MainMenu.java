package com.centdb.module8;

import java.util.List;
import java.util.Scanner;

import com.centdb.module2.QueryRunner;
import com.centdb.module6.Export;
import com.centdb.module7.Analysis;
import com.centdb.module3.IdentifyTransaction;
import com.centdb.module3.Transaction;
import com.centdb.module5.DataModelling;

public class MainMenu {
	public static Scanner scanner = new Scanner(System.in);

	MainMenu() {
	}

	public static void showLoginMenu() {

		System.out.println("---------------Welcome to CentDb---------------");
		System.out.println("Please chose appropriate option");
		System.out.println(" 1. Login \n 2. Register \n 3. Exit");
		System.out.println("Enter your option: ");
		String input = scanner.nextLine();
		switch (input) {
			case "1":
				Login login = new Login();
				login.userLogin();
				break;
			case "2":
				Register register = new Register();
				register.registerUser();
				break;
			default:
				System.exit(0);
		}
	}

	public void showMainMenu() {
		String database = "";
		while (true) {
			try {
				System.out.println("\n\n---------------Welcome to Main Menu---------------");
				System.out.println("Please chose appropriate option");
				System.out.println(
						" 1. Write Queries \n 2. Export \n 3. Data Model \n 4. Analytics \n 5. LogOut \n 6. Exit");
				System.out.println("\nEnter your option: ");
				String input = scanner.nextLine();

				switch (input) {
					case "1":
						IdentifyTransaction transaction = new IdentifyTransaction();
						List<String> queries = transaction.readInput();
						if (queries.size() == 1) {
							// System.out.println("its query");
							QueryRunner.run(queries.get(0));
						} else if (queries.size() > 1) {
							System.out.println("Committing transaction statements");
							if (Transaction.run(queries)) {
								System.out.println("Transaction completed successfully.");
							}
						} else if (queries.size() == 0) {
							System.out.println("Rollback was called");
						}
						break;
					case "2":
						System.out.println("\nAvailable databases: ");
						Export.showDatabases();
						System.out.println("\nEnter the database name: ");
						database = scanner.nextLine();
						Export export = new Export(database);
						export.toSql();
						break;
					case "3":
						System.out.println("\nAvailable databases: ");
						DataModelling.showDatabases();
						System.out.println("\nEnter the database name: ");
						database = scanner.nextLine();
						DataModelling dataModelling = new DataModelling(database);
						dataModelling.generateERD();
						break;
					case "4":
						Analysis analysis = new Analysis();
						analysis.readInput();
						break;
					case "5":
						Login login = new Login();
						login.logUser("Logout");
						showLoginMenu();
						break;
					default:
						Login login2 = new Login();
						login2.logUser("Logout");
						System.exit(0);
				}
			} catch (Exception e) {
			}
		}
	}
}
