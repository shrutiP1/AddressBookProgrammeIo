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

}
