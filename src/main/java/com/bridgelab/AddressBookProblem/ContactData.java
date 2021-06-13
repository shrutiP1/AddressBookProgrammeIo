package com.bridgelab.AddressBookProblem;

public class ContactData
{
    public int id;
    public String firstname;
    public String lastName;
    public String address;
    public String city;
    public String state;
    public int zip;
    public String phoneNumber;
    public String emailId;

    public ContactData(int id, String firstname, String lastName, String address, String city, String state, int zip, String phoneNumber, String emailId)
    {
        this.id = id;
        this.firstname = firstname;
        this.lastName = lastName;
        this.address = address;
        this.city = city;
        this.state = state;
        this.zip = zip;
        this.phoneNumber = phoneNumber;
        this.emailId = emailId;
    }

    @Override
    public String toString() {
        return "ContactData{" +
                "id=" + id +
                ", firstName='" + firstname + '\'' +
                ", lastName='" + lastName + '\'' +
                ", address='" + address + '\'' +
                ", city='" + city + '\'' +
                ", state='" + state + '\'' +
                ", zip=" + zip +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", emailId='" + emailId + '\'' +
                '}';
    }
}

