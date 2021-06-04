package com.bridgelab.AddressBookProblem;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class MutipleAddressBook
{
    List<ContactInfo> contacts = new ArrayList<>();
    Scanner input = new Scanner(System.in);
    int index;
    String firstName;
    String name;

    public MutipleAddressBook(String name) {
        this.name = name;
    }

    public boolean checkName() {
        System.out.println("Enter First Name");
        firstName = input.nextLine();
        for (index = 0; index < contacts.size(); index++) {
            if (firstName.equals(contacts.get(index).getFirstName())) {
                System.out.println("Contact found");
                return true;
            }
        }
        return false;
    }

    public void addContact() {
        if (!checkName()) {
            System.out.print("Enter Last Name: ");
            String lastName = input.nextLine();
            System.out.print("Enter Address: ");
            String address = input.nextLine();
            System.out.print("Enter City: ");
            String city = input.nextLine();
            System.out.print("Enter State: ");
            String state = input.nextLine();
            System.out.print("Enter Pin Code: ");
            String zip = input.nextLine();
            System.out.print("Enter Phone Number: ");
            String phone = input.nextLine();
            System.out.print("Enter Email ID: ");
            String email = input.nextLine();
            contacts.add(new ContactInfo(firstName, lastName, address, city, state, zip, phone, email));
        }
    }

    public void editContact() {
        if (checkName()) {
            System.out.print("Enter New Name: ");
            contacts.get(index).setFirstName(input.nextLine());
            System.out.print("Enter New Address: ");
            contacts.get(index).setAddress(input.nextLine());
            System.out.print("Enter New City: ");
            contacts.get(index).setCity(input.nextLine());
            System.out.print("Enter New State: ");
            contacts.get(index).setState(input.nextLine());
            System.out.print("Enter New Pin Code: ");
            contacts.get(index).setZip(input.nextLine());
            System.out.print("Enter New Phone Number: ");
            contacts.get(index).setPhoneNumber(input.nextLine());
            System.out.print("Enter New Email ID: ");
            contacts.get(index).setEmailId(input.nextLine());
            System.out.println("Successfully Edited The Contact");
        } else {
            System.out.println("Name not Found!!");
        }
    }

    public void deleteContact() {
        System.out.println("Enter the name of Contact you want to Delete");
        for (index = 0; index < contacts.size(); index++) {
            if (checkName()) {
                contacts.remove(index);
                System.out.println("successfully Deleted The Contact");
            }
        }
    }

    public void printContacts() {
        System.out.println("Contact is Address Book are :");
        if (contacts.isEmpty()) {
            System.out.println("No Contact Found");
        }
        List<ContactInfo> sortedArray = contacts.stream().sorted(Comparator.comparing(ContactInfo::getFirstName)).collect(Collectors.toList());
        System.out.println(sortedArray);
    }

    public void searchContactWithCity() {
        System.out.println("Enter the city you want to search ; ");
        String city = input.nextLine();
        System.out.println("Name of the people  who are from " + city + " are:");
        List<ContactInfo> cities = contacts.stream().sorted(Comparator.comparing(ContactInfo::getFirstName)).filter(contacts -> contacts.getCity().equals(city)).collect(Collectors.toList());
        System.out.println(cities);
        System.out.println("Number of People from " + city + " are " + (long) cities.size());
    }

    public void searchContactsWiyhState() {
        System.out.println("Enter the state you want to search : ");
        String state = input.nextLine();
        System.out.println("Number of peoples who are from " + state + " are: ");
        List<ContactInfo> states = contacts.stream().sorted(Comparator.comparing(ContactInfo::getFirstName)).filter(contacts -> state.equals(contacts.getState())).collect(Collectors.toList());
        System.out.println(states);
        System.out.println("Number of peoples from " + state + " are " + (long) states.size());
    }

    public void printContactsSortedByCity() {
        List<ContactInfo> sortedArray = contacts.stream().sorted(Comparator.comparing(ContactInfo::getCity)).collect(Collectors.toList());
        System.out.println(sortedArray);
    }

    public void printContactsSortedByState() {
        List<ContactInfo> sortedArray = contacts.stream().sorted(Comparator.comparing(ContactInfo::getState)).collect(Collectors.toList());
        System.out.println(sortedArray);
    }

    public void printContactsSortedByZip() {
        List<ContactInfo> sortedArray = contacts.stream().sorted(Comparator.comparing(ContactInfo::getZip)).collect(Collectors.toList());
        System.out.println(sortedArray);
    }
}
