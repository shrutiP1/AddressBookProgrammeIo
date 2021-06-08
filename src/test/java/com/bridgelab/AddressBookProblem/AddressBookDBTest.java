package com.bridgelab.AddressBookProblem;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AddressBookDBTest {
    @Test
    public void givenAddressBookDb_WhenRetrieved_shouldMatchCount() throws CustomeException {
        AddressBookDb addressBookDb = new AddressBookDb();
        List<ContactInfo> addressBookDataList = addressBookDb.readAddressBookDb();
        Assertions.assertEquals(4, addressBookDataList.size());
    }

    @Test
    public void givenNewCity_WhenUpdated_shouldSyncWithDb() throws CustomeException {
        AddressBookDb addressBookDb = new AddressBookDb();
        List<ContactInfo> addressBookDataList = addressBookDb.readAddressBookDb();
        addressBookDb.updateEmployeeCity("Shruti", "Mumbai");
        boolean result = addressBookDb.checkAddressBookNameshouldSyncWithDB("Shruti");
        Assertions.assertTrue(result);
    }

    @Test
    public void givenDateRange_WhenRetrieved_shouldMatchWithCount() throws CustomeException {
        AddressBookDb addressBookDb = new AddressBookDb();
        List<ContactInfo> addressBookDataList = addressBookDb.readAddressBookDb();
        LocalDate startDate = LocalDate.of(2019, 01, 01);
        LocalDate endDate = LocalDate.now();
        List<ContactInfo> contactInfoList = addressBookDb.readAddressBookContactsModifiedWithinRange(startDate, endDate);
        Assertions.assertEquals(2, contactInfoList.size());
    }
    @Test
    public void givenState_WhenManipulated_shouldMatchWithCount() throws CustomeException {
        AddressBookDb addressBookDb = new AddressBookDb();
        List<ContactInfo> addressBookDataList = addressBookDb.readAddressBookDb();
        Map<String, Integer> contactCountToStateMap = addressBookDb.readAddressBookContactsGroupedByState();
        Assertions.assertTrue(contactCountToStateMap.get("Maharashtra").equals(2) && contactCountToStateMap.get("Karnataka").equals(1));
    }
    @Test
    public void givenNewContact_WhenManipulated_shouldSyncWithDb() throws CustomeException {
        AddressBookDb addressBookDb = new AddressBookDb();
        List<ContactInfo> addressBookDataList = addressBookDb.readAddressBookDb();
        LocalDate date=LocalDate.of(2020,02,01);
        addressBookDb.addNewContactsInAddressBook("Ayesha","Patil","92929929","sheyru@gmail.com","Building no 8","Mangaon","Raigad","402104", date);
        boolean result=addressBookDb.checkAddressBookNameshouldSyncWithDB("Ayesha");
        Assertions.assertTrue(result);

    }

}
