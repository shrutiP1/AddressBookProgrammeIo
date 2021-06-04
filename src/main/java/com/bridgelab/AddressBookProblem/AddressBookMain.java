package com.bridgelab.AddressBookProblem;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class AddressBookMain
{
    List<MutipleAddressBook> addressBook = new ArrayList<>();
    Scanner input = new Scanner(System.in);
    int bookNumber = 0;
    String name;

    public static void main(String[] args) {
        AddressBookMain menu = new AddressBookMain();
        menu.menu();
    }

    public void MutipleAddressBook() {
        System.out.println("Enter name of AddressBook");
        name = input.next();
        addressBook.add(new MutipleAddressBook(name));
        System.out.println("New Address Book Created With name " + name);
    }

    public void selectAddressBook() {
        System.out.println("You are Currently in " + addressBook.get(bookNumber) + " Addressbook");
        if (addressBook.size() > 0) {
            for (int i = 0; i < addressBook.size(); i++) {
                System.out.println(i + "-" + addressBook.get(i));
            }
            System.out.println("Pick Address Book Number");
            bookNumber = Integer.parseInt(input.next());
        }
    }

    public void menu() {
        Scanner scan = new Scanner(System.in);
        MutipleAddressBook();
        System.out.println("-----Address Book Menu-----");
        System.out.println("1. Add Contact.");
        System.out.println("2. Edit Contact.");
        System.out.println("3. Delete Contact.");
        System.out.println("4. Add Another Address Book: ");
        System.out.println("5. See Contacts: ");
        System.out.println("6. Search Contacts with Same City: ");
        System.out.println("7. Search Contacts with Same State: ");
        System.out.println("8. Print Contacts Sorted By City: ");
        System.out.println("9:Print Contacts Sorted By State");
        System.out.println("10:Print Contacts Sorted By Zip");
        System.out.print("Enter Your Choice: ");
        int option = scan.nextInt();
        switch (option) {
            case 1:
                selectAddressBook();
                addressBook.get(bookNumber).addContact();
                break;
            case 2:
                addressBook.get(bookNumber).editContact();
                break;
            case 3:
                addressBook.get(bookNumber).deleteContact();
                break;
            case 4:
                MutipleAddressBook();
                break;
            case 5:
                System.out.println("--These are the Address Book Presesnt--");
                selectAddressBook();
                addressBook.get(bookNumber).printContacts();
                break;
            case 6:
                addressBook.get(bookNumber).searchContactWithCity();
                break;
            case 7:
                addressBook.get(bookNumber).searchContactsWiyhState();
                break;
            case 8:
                addressBook.get(bookNumber).printContactsSortedByCity();
                break;
            case 9:
                addressBook.get(bookNumber).printContactsSortedByState();
                break;
            case 10:
                addressBook.get(bookNumber).printContactsSortedByZip();
                break;
            default:
                System.exit(0);

        }


    }

}
