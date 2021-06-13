package com.bridgelab.AddressBookProblem;

import java.util.ArrayList;
import java.util.List;

public class ContactRestAPI 
{

    private  List<ContactData> dataList;

    public ContactRestAPI(List<ContactData> contactData) 
    {
        this.dataList=new ArrayList<>(contactData);
    }

    public long countEntires()
    {
        System.out.println(dataList);
        return dataList.size();
    }
}
