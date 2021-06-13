package com.bridgelab.AddressBookProblem;

import com.google.gson.Gson;
import io.restassured.RestAssured;
import io.restassured.internal.common.assertion.Assertion;
import io.restassured.response.Response;
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
    private ContactData[] getEmployeeDetails()
    {
        Response response=RestAssured.get(RestAssured.baseURI+"/contacts");
        System.out.println("Employess in Json Server are "+response.asString());
        return new Gson().fromJson(response.asString(),ContactData[].class);
    }

    @Test
    public void givenContactInJsonServer_WhenFetched_ShouldMatchCount()
    {
        ContactData[] contactData=getEmployeeDetails();
        ContactRestAPI contactRestAPI=new ContactRestAPI(Arrays.asList(contactData));
        long entries=contactRestAPI.countEntires();
        Assertions.assertEquals(1,entries);
    }


}
