package com.st.lmssql.utils;

import java.util.Scanner;

public class getInput {
	private static Scanner scan = new Scanner(System.in);
	public static void close() {
		scan.close();
	}
	
	//simple scan.nextLine until a specific parsing is implemented.
	public static String getName() {
		return scan.nextLine();
	}
	
	//simple scan.nextLine until a specific parsing is implemented.
	public static String getAddr() {
		return scan.nextLine();
	}
	
	//asks user for a positive integer.
	//if user enters a blank line, returns -1
	public static int getPosInt() {
		String input;
		int output = -1;
		
		do {
			input = scan.nextLine();
			if(input.length()==0)
				return -1;
			try {
				output = Integer.parseInt(input);
			}catch(NumberFormatException e){
				output = 0;
				System.out.println("ERROR: That is not a valid number!");
			}
			if(output < 0) {
				System.out.println("ERROR: Please enter in a positive integer!");
			}
		}while(output<1);
		return output;
	}
	
	public static int getValidOption(int options) {//prompts user for a number less than options-1 
		System.out.println("Which option would you like to choose?");
		String input;
		int option;
		do {
			input = scan.nextLine();
			try {
				option = Integer.parseInt(input);
				if(option<1 || option>options) {
					option = 0;
					System.out.println("ERROR: That is not one of the options listed!");
				}
			}catch(NumberFormatException e) {
				System.out.println("ERROR: Please enter a number from the options available!");
				option = 0;
			}
		}while(option == 0);
		return option;
	}
	
	public static String getValidPhone() {
		String phone = "";
		do {
			phone = scan.nextLine();
			if(phone.isEmpty())
				return null;
			try {
				Long.parseLong(phone);
			}catch(NumberFormatException e){
				System.out.println("ERROR: Input contains spaces or non-numeric characters!");
			}
		}while(phone.length()==0); 
		return phone;
	}
}
