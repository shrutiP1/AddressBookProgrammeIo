package com.bridgelab.AddressBookProblem;

import java.util.ArrayList;
import java.util.List;

public class AddressBookDb
{
    private static AddressBookDbService addressBookDbService;
    List<ContactInfo> contactDataList=new ArrayList<>();
    AddressBookDb(){
         addressBookDbService = AddressBookDbService.getInstance();
    };
    AddressBookDb(List<ContactInfo> contactDataList)
    {
        this();
        this.contactDataList=contactDataList;
    }

    public List<ContactInfo> readAddressBookDb() throws CustomeException {
        this.contactDataList=addressBookDbService.readData();
        return contactDataList;
    }
}
