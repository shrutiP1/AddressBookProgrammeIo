package com.bridgelab.AddressBookProblem;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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


}
