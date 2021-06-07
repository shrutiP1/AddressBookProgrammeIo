package com.bridgelab.AddressBookProblem;

public class ContactInfo
{
    private  int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private String firstName;
    private String lastName;
    private String address;
    private String city;
    private String state;
    private String zip;
    private String phoneNumber;
    private String emailId;

    public ContactInfo(String firstName, String lastName, String address, String city, String state, String zip, String phoneNumber, String emailId) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.city = city;
        this.state = state;
        this.zip = zip;
        this.phoneNumber = phoneNumber;
        this.emailId = emailId;
    }

    public ContactInfo(int id, String firstName, String lastName, String phoneNo, String email, String address, String city, String state, String zip)
    {
        this.id=id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.city = city;
        this.state = state;
        this.zip = zip;
        this.phoneNumber = phoneNumber;
        this.emailId = emailId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    @Override
    public String toString() {
        return "<----Contact of " + firstName + " is---->\n" +
                "First Name: " + firstName + '\n' +
                "Last Name: " + lastName + '\n' +
                "Address: " + address + '\n' +
                "City: " + city + '\n' +
                "State: " + state + '\n' +
                "Zip: " + zip + '\n' +
                "PhoneNumber: " + phoneNumber + '\n' +
                "EmailId: " + emailId + '\n';
    }

}
