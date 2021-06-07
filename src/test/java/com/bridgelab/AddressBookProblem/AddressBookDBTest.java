package com.bridgelab.AddressBookProblem;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class AddressBookDBTest
{
    @Test
    public void givenAddressBookDb_WhenRetrieved_shouldMatchCount() throws CustomeException {
        AddressBookDb addressBookDb=new AddressBookDb();
        List<ContactInfo> addressBookDataList=addressBookDb.readAddressBookDb();
        Assertions.assertEquals(4,addressBookDataList.size());
    }

}
