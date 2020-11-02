package housingapp;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Scanner;
import java.util.Date;
import java.io.FileWriter;
import java.io.BufferedWriter;

public class Lease {
	
	public void generateLease() {
		String readSentence;
		String readWord = "";
		String[] acceptWord;
		
		try {
			File leaseTemplate = new File("Lease Agreement.txt");
			Scanner myReader = new Scanner(leaseTemplate);
			while (myReader.hasNextLine()) {
				readSentence = myReader.nextLine();
				acceptWord = readSentence.split(" ");
				for (int i = 0; i < acceptWord.length; i++) {
					if (acceptWord[i].equals("<DATE>")) {
						Date date = new Date();
						DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
						acceptWord[i] = dateFormat.format(date);
					} else if (acceptWord[i].equals("<LANDLOARD>")) {
						acceptWord[i] = "Johnathon Duong";
					} else if (acceptWord[i].equals("<TENANT(s)>.")) {
						acceptWord[i] = "Johnathon Duong";
					} else if(acceptWord[i].equals("<NUM_BED>")) {
						acceptWord[i] = "2";
					} else if(acceptWord[i].equals("<NUM_BATH>")) {
						acceptWord[i] = "2";
					} else if(acceptWord[i].equals("<PROPERTY_ADDRESS>,")) {
						acceptWord[i] = "1051 Southern Dr.";
					} else if(acceptWord[i].equals("<ZIP>.")) {
						acceptWord[i] = "29201";
					} else if(acceptWord[i].equals("<START_DATE>")) {
						acceptWord[i] = "10/27/2020";
					} else if(acceptWord[i].equals("<END_DATE>.")) {
						acceptWord[i] = "10/27/2020";
					} else if(acceptWord[i].equals("<RENT>")) {
						acceptWord[i] = "$500";
					} else if(acceptWord[i].equals("<PAYMENT_ADDRESS>")) {
						acceptWord[i] = "1051 Southern Dr.";
					} else if(acceptWord[i].equals("<DAMAGE_COST>.")) {
						acceptWord[i] = "$500";
					}
					readWord += " " + acceptWord[i];
				}
				readWord += "\n";
					
			}
			File file = new File();
			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter writer = new BufferedWriter(fw);
			fw.write(readWord);
			fw.close();
		} catch (FileNotFoundException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		}
		
	}
	
	

}
