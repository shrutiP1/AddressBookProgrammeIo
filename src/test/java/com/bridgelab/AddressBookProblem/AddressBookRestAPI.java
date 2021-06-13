package com.bridgelab.AddressBookProblem;

import com.google.gson.Gson;
import io.restassured.RestAssured;
import io.restassured.internal.common.assertion.Assertion;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

public class AddressBookRestAPI
{
    @BeforeEach
    void setUp()
    {
        RestAssured.baseURI="http://localhost:3000";
    }
    private ContactData[] getContactDetails()
    {
        Response response=RestAssured.get(RestAssured.baseURI+"/contacts");
        System.out.println("Employess in Json Server are "+response.asString());
        return new Gson().fromJson(response.asString(),ContactData[].class);
    }
    private Response addContactToJsonServer(ContactData contactData1)
    {
        String contactsJson=new Gson().toJson(contactData1);
        RequestSpecification requestSpecification=RestAssured.given();
        requestSpecification.header("Content-Type","application/json");
        requestSpecification.body(contactsJson);
        return requestSpecification.post(RestAssured.baseURI+"/contacts");

    }

    @Test
    public void givenContactInJsonServer_WhenFetched_ShouldMatchCount()
    {
        ContactData[] contactData=getContactDetails();
        ContactRestAPI contactRestAPI=new ContactRestAPI(Arrays.asList(contactData));
        long entries=contactRestAPI.countEntires();
        Assertions.assertEquals(1,entries);
    }
    @Test
    public void givenNewContact_WhenAdded_shouldMatchCount()
    {
        ContactData[] dataArray=getContactDetails();
        ContactRestAPI contactRestAPI=new ContactRestAPI(Arrays.asList(dataArray));
        ContactData contactData1;
        contactData1=new ContactData(0,"SHRUTI","Patil","Pune","pune","Maharshtra",402104,"9130416631","shrutipatil13798@gmail.com");
        Response response=addContactToJsonServer(contactData1);
        contactData1=new Gson().fromJson(response.asString(),ContactData.class);
        contactRestAPI.addEmployeeToList(contactData1);
        System.out.println("After adding to Json Server "+getContactDetails());
        long entries=contactRestAPI.countEntires();
        Assertions.assertEquals(2,entries);

    }
    @Test
    public void givenMultipleContact_WhenAdded_shouldMatchCount()
    {
        ContactRestAPI contactRestAPI;
        ContactData[] dataArray=getContactDetails();
        contactRestAPI=new ContactRestAPI(Arrays.asList(dataArray));
        ContactData[] arrayOfData={
                new ContactData(0, "vijay", "darade", "Mangalmurti Building", "pune", "maharashtra", 422013, "9999999999", "vijay@gmail.com"),
                new ContactData(0, "shashank", "adka", "Kachri rd", "Mumbai", "maharashtra", 422013, "8888888888", "shashank@gmail.com"),
                new ContactData(0, "yash", "kulkarni", "gangapur rd", "Ahemdbad", "maharashtra", 422013, "6666666666", "yash@gmail.com"),

        };

        for(ContactData contactData:arrayOfData)
        {
            Response response=addContactToJsonServer(contactData);
            contactData=new Gson().fromJson(response.asString(),ContactData.class);
            contactRestAPI.addEmployeeToList(contactData);

        }
        System.out.println("<--------After adding to Json Server--------->");
        getContactDetails();
        long entries=contactRestAPI.countEntires();
        Assertions.assertEquals(5,entries);
    }
    @Test
    public void givenUpdatedSalary_whenUpdated_shouldReturn2000ResponseCode()
    {
        ContactRestAPI contactRestAPI;
        ContactData[] dataArray=getContactDetails();
        contactRestAPI=new ContactRestAPI(Arrays.asList(dataArray));
        contactRestAPI.updateContact("bhushan","3333333");
        ContactData contactData=contactRestAPI.getContact("bhushan");
        RequestSpecification requestSpecification=RestAssured.given();
        requestSpecification.header("Content-Type","application/json");
        String contactJson=new Gson().toJson(contactData);
        requestSpecification.body(contactJson);
        Response response=requestSpecification.put(RestAssured.baseURI+"/contacts/"+contactData.id);
        System.out.println("After updating we have "+response.asString());
        int statuscode=response.statusCode();
        Assertions.assertEquals(200,statuscode);

    }

}
