package com.bridgelab.AddressBookProblem;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;

public class AddressBookDb {
    private static AddressBookDbService addressBookDbService;
    List<ContactInfo> contactDataList = new ArrayList<>();

    AddressBookDb() {
        addressBookDbService = AddressBookDbService.getInstance();
    }

    ;

    AddressBookDb(List<ContactInfo> contactDataList) {
        this();
        this.contactDataList = contactDataList;
    }

    public List<ContactInfo> readAddressBookDb() throws CustomeException {
        this.contactDataList = addressBookDbService.readData();
        return contactDataList;
    }

    public List<ContactInfo> readAddressBookContactsModifiedWithinRange(LocalDate startDate, LocalDate endDate) throws CustomeException {
        return addressBookDbService.getAddressBookModifiedWithinRange(startDate, endDate);
    }

    public Map<String, Integer> readAddressBookContactsGroupedByState() throws CustomeException {
        return addressBookDbService.getContactsOfAddressBookGroupedByState();
    }

    public void addNewContactsInAddressBook(String firstName, String lastName, String phone, String email, String address, String city, String state, String pincode, LocalDate start) throws CustomeException, SQLException {
       this.contactDataList.add(addressBookDbService.addContactsInAddressBook(firstName, lastName, phone, email, address, city, state, pincode, start));

    }

    public void updateEmployeeCity(String firstName, String city) throws CustomeException {
        int result = addressBookDbService.updateCityUsingStatement(firstName, city);
        if (result == 0)
            throw new CustomeException("Query Failed To Process");
        ContactInfo contactInfo = this.getInformation(firstName);
        if (contactInfo != null) contactInfo.setFirstName(firstName);
    }


    private ContactInfo getInformation(String firstName) {
        return contactDataList.stream()
                .filter(addressBookItems -> addressBookItems.getFirstName().equals(firstName))
                .findFirst()
                .orElse(null);
    }

    public boolean checkAddressBookNameshouldSyncWithDB(String firstName) throws CustomeException {
        List<ContactInfo> contactInfoList = addressBookDbService.getContactList(firstName);
        return contactInfoList.get(0).equals(getInformation(firstName));

    }
    public void addMultipleContactsInAddressBook(String firstName, String lastName, String phone, String email, String address, String city, String state, String pincode, LocalDate start) throws CustomeException, SQLException {
        this.contactDataList.add(addressBookDbService.addMultipleContactsUsingThread(firstName, lastName, phone, email, address, city, state, pincode, start));

    }
    synchronized void addAddressBookContactsUsingThread(List<ContactInfo> contactDataList) {
        Map<Integer, Boolean> addressAdditionStatus = new HashMap<>();
        contactDataList.forEach(contactList -> {
            System.out.println("Contact Being Added " + Thread.currentThread().getName());
            Runnable task = () -> {
                try {
                    addressAdditionStatus.put(addressAdditionStatus.hashCode(), false);
                    this.addMultipleContactsInAddressBook(contactList.getFirstName(), contactList.getLastName(), contactList.getPhoneNumber(), contactList.getEmailId(), contactList.getAddress(), contactList.getCity(), contactList.getState(), contactList.getZip(), contactList.getStart());
                    addressAdditionStatus.put(addressAdditionStatus.hashCode(), true);
                    System.out.println("contact added " + Thread.currentThread().getName());
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                } catch (CustomeException e) {
                    e.printStackTrace();
                }


            };
            Thread thread = new Thread(task, contactList.getFirstName());
            thread.start();
            while (addressAdditionStatus.containsValue(false)) {
                try {
                    System.out.println("inner");
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        });
    }
    public int countEntries()  {
        return contactDataList.size();
    }


}
