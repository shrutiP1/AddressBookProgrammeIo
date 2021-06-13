package com.bridgelab.AddressBookProblem;

import java.util.ArrayList;
import java.util.List;

public class ContactRestAPI 
{

    public static List<ContactData> dataList;

    public ContactRestAPI(List<ContactData> contactData) 
    {
        this.dataList=new ArrayList<>(contactData);
    }

    public long countEntires()
    {
        System.out.println(dataList);
        return dataList.size();
    }

    public void addEmployeeToList(ContactData contactData1)
    {
        this.dataList.add(contactData1);
    }
    public ContactData getContact(String name)
    {
        return this.dataList.stream().filter(dataItem->dataItem.firstname.equals(name)).findFirst().orElse(null);
    }
    public void updateContact(String name, String phone)
    {
        ContactData contactData=this.getContact(name);
        if(contactData!=null)
        {
            contactData.phoneNumber=phone;
        }
    }


}
