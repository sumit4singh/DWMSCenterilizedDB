package com.centdb.module8;

import java.util.Scanner;

import com.centdb.util.Hashing;
import com.centdb.util.Utility;

public class Register {
	
	private final String BASE_USER_DIR_PATH = System.getProperty("user.dir") + "/userDetails/userLoginDetails.txt";
	
	Scanner scanner = new Scanner(System.in);
	
	Register() {		
	}
	
	public void registerUser() {
		
        System.out.println("Enter User Name: ");
        String userName = scanner.nextLine();
        System.out.println("Enter a password: ");
        String password = scanner.nextLine();
        System.out.println("Enter a security question: ");
        String securityQuestion = scanner.nextLine();
        System.out.println("Enter answer to your security question: ");
        String securityAnswer = scanner.nextLine();

        Hashing hash = new Hashing(password);
        
        String body = userName + "#" + hash.passwordHashed + "#" + securityQuestion + "#" + securityAnswer;
        boolean response = Utility.writeTofile(BASE_USER_DIR_PATH, body);
        if(response) {
        	System.out.println("Registration successfully");
        	MainMenu.showLoginMenu();
        } else {
        	System.out.println("Registration Failed");
        }
    }
}
