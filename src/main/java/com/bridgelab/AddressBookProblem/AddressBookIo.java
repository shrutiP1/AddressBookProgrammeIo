package com.bridgelab.AddressBookProblem;

import com.google.gson.Gson;
import com.opencsv.CSVWriter;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class AddressBookIo {
    private static final String File = "addressbook.txt";
    private static final String SAMPLE_CSV_FILE_TO_WRITE = "./data.csv";
    private static final String SAMPLE_JSON_FILE = "./data.json";

    public static void main(String[] args) throws IOException, CsvRequiredFieldEmptyException, CsvDataTypeMismatchException {
        ContactInfo[] contactsArr = {
                new ContactInfo("shruti", "Patil", "Mangaon", "Pune", "Maharashtra", "402104", "9130416631", "shruti13798@gmail.com"),
                new ContactInfo("Dadu", "Patil", "building", "pune", "maharshtra", "402104", "2020020200", "shsheueu@gmail.com"),
                new ContactInfo("RINKU", "bhingare", "building", "pune", "maharshtra", "402104", "9130416631", "shssjs@gmail.com")
        };
        AddressBookIo io = new AddressBookIo();
        io.writeDataToFile(Arrays.asList(contactsArr));
        io.writeDataToCSVFile(Arrays.asList(contactsArr));
        io.writeDataToJSON(Arrays.asList(contactsArr));
        io.readContacts();
        io.readContactsOfCSVFile();
        io.readContactsOfJSONFile();

    }

    public void writeDataToFile(List<ContactInfo> contacts) {
        StringBuffer buffer = new StringBuffer();
        contacts.forEach(contact -> {
            String contactsString = contact.toString().concat("\n");
            buffer.append(contactsString);
        });
        try {
            Files.write(Paths.get(File), buffer.toString().getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeDataToCSVFile(List<ContactInfo> contacts) throws CsvRequiredFieldEmptyException, CsvDataTypeMismatchException {
        try (
                Writer writer = Files.newBufferedWriter(Paths.get(SAMPLE_CSV_FILE_TO_WRITE));
        ) {
            StatefulBeanToCsv<ContactInfo> beanToCsv = new StatefulBeanToCsvBuilder(writer).withQuotechar(CSVWriter.NO_QUOTE_CHARACTER).build();
            beanToCsv.write(contacts);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeDataToJSON(List<ContactInfo> contacts) throws IOException {
        Gson gson = new Gson();
        Writer writer = Files.newBufferedWriter(Paths.get(SAMPLE_JSON_FILE));
        gson.toJson(contacts, writer);
        writer.close();
    }


    public void readContacts() {
        System.out.println("\n<----- The Content Of Txt file is ----->");
        try {
            Files.lines(new File(File).toPath()).forEach(System.out::println);
            long entries = Files.lines(new File(File).toPath()).count();
            System.out.println("The Number of entries in Txt File are: " + entries);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void readContactsOfCSVFile() {
        System.out.println("\n<----- The Content Of CSV file is ----->");
        try {
            Files.lines(new File(SAMPLE_CSV_FILE_TO_WRITE).toPath()).forEach(System.out::println);
            long entries = Files.lines(new File(SAMPLE_CSV_FILE_TO_WRITE).toPath()).count();
            System.out.println("\nThe Number of entries in CSVFile are: " + entries);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void readContactsOfJSONFile() {
        System.out.println("\n<----- The Content Of JSON file is ----->");
        try {
            Files.lines(new File(SAMPLE_JSON_FILE).toPath()).forEach(System.out::println);
            long entries = Files.lines(new File(SAMPLE_JSON_FILE).toPath()).count();
            System.out.println("\nThe Number of entries in JSOnFile are: " + entries);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static long countEntries()
    {
        long entries=0;
        try
        {
            entries=Files.lines(new File(File).toPath()).count();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return entries;
    }

}
