package housingapp;

import housingapp.user.User;

import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Scanner;
import java.util.Date;

public class Lease {
	
	public static void generateLease(User landlord, User tenant, int numBed, int numBath, String propertyAddress,
							  String propertyZip, int leaseMonths, double price, String officeAddress, double damagesCost) {
		String leaseStr = "";
		try {
			File leaseTemplate = new File("src/housingapp/data/Lease Agreement.txt");
			Scanner reader = new Scanner(leaseTemplate);
			while (reader.hasNextLine()) {
				String currLine = reader.nextLine();
				String[] currLineParts = currLine.split(" ");
				for (int i=0; i<currLineParts.length; i++) {
					if (currLineParts[i].equals("<DATE>")) {
						Date date = new Date();
						DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
						currLineParts[i] = dateFormat.format(date);
					} else if (currLineParts[i].equals("<LANDLORD>")) {
						currLineParts[i] = landlord.getFirstName() + " " + landlord.getLastName();
					} else if (currLineParts[i].equals("<TENANT(s)>.") || currLineParts[i].equals("<TENANT_1>")) {
						currLineParts[i] = tenant.getFirstName() + " " + tenant.getLastName();
					} else if(currLineParts[i].equals("<NUM_BED>")) {
						currLineParts[i] = String.valueOf(numBed);
					} else if(currLineParts[i].equals("<NUM_BATH>")) {
						currLineParts[i] = String.valueOf(numBath);
					} else if(currLineParts[i].equals("<PROPERTY_ADDRESS>,")) {
						currLineParts[i] = propertyAddress;
					} else if(currLineParts[i].equals("<ZIP>.")) {
						currLineParts[i] = propertyZip;
					} else if(currLineParts[i].equals("<START_DATE>")) {
						currLineParts[i] = LocalDate.now().toString();
					} else if(currLineParts[i].equals("<END_DATE>.")) {
						currLineParts[i] = LocalDate.now().plusMonths(leaseMonths).toString();
					} else if(currLineParts[i].equals("<RENT>")) {
						currLineParts[i] = String.valueOf(price);
					} else if(currLineParts[i].equals("<PAYMENT_ADDRESS>")) {
						currLineParts[i] = officeAddress;
					} else if(currLineParts[i].equals("<DAMAGE_COST>.")) {
						currLineParts[i] = String.valueOf(damagesCost);
					}
					if (i != 0) {
						leaseStr += " ";
					}
					leaseStr += currLineParts[i];
				}
				leaseStr += "\n";
			}
			// write to data file
			File writeFile = new File("src/housingapp/data/lease.txt");
			if (!writeFile.exists()) {
				try {
					writeFile.createNewFile();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			try (FileWriter writer = new FileWriter(writeFile)) {
				writer.write(leaseStr);
				writer.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		}
	}
}
