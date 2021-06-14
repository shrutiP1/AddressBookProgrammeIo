package com.bridgelab.AddressBookProblem;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddressBookDbService {
    private static AddressBookDbService addressBookDbService;
    private PreparedStatement contactDataStatement;


    public static AddressBookDbService getInstance() {
        if (addressBookDbService == null)
            return addressBookDbService = new AddressBookDbService();
        return addressBookDbService;
    }

    private Connection getConnection() throws CustomeException {
        String jdbcURL = "jdbc:mysql://localhost:3306/addressbook_db?useSSL=false";
        String userName = "root";
        String password = "mysql";
        Connection con = null;
        System.out.println("Connecting to database");
        try {
            con = DriverManager.getConnection(jdbcURL, userName, password);
            System.out.println("connected to database" + con);
        } catch (SQLException throwables) {
            throw new CustomeException("Query Failed !");
        }
        return con;

    }

    private List<ContactInfo> getEmployeePayRollData(ResultSet resultSet) throws SQLException {
        List<ContactInfo> empPayRollList = new ArrayList<>();
        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            String firstName = resultSet.getString("firstName");
            String lastName = resultSet.getString("lastName");
            String phoneNo = resultSet.getString("phone_no");
            String email = resultSet.getString("email_id");
            String address = resultSet.getString("address");
            String city = resultSet.getString("city");
            String state = resultSet.getString("state");
            String zip = resultSet.getString("zip");
            LocalDate startDate = resultSet.getDate("start").toLocalDate();
            empPayRollList.add(new ContactInfo(id, firstName, lastName, phoneNo, email, address, city, state, zip, startDate));

        }
        return empPayRollList;

    }

    private List<ContactInfo> getDataWhenSqlIsGiven(String sql) throws CustomeException {
        Connection connection = this.getConnection();
        List<ContactInfo> empPayRollList = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            empPayRollList = this.getEmployeePayRollData(resultSet);

        } catch (SQLException throwables) {
            throw new CustomeException("Query Failed !");
        }
        return empPayRollList;


    }

    public List<ContactInfo> getContactList(String firstName) throws CustomeException {
        List<ContactInfo> contactInfoList = null;
        if (this.contactDataStatement == null)
            this.prepareStatementForEmployeeData();
        try {
            contactDataStatement.setString(1, firstName);
            ResultSet resultSet = contactDataStatement.executeQuery();

            contactInfoList = this.getEmployeePayRollData(resultSet);
            System.out.println(contactInfoList);
            return contactInfoList;

        } catch (SQLException throwables) {
            throw new CustomeException("Query Failed !");
        }
    }

    public List<ContactInfo> getAddressBookModifiedWithinRange(LocalDate startDate, LocalDate endDate) throws CustomeException {
        String sql = String.format("select *from contact_db where start BETWEEN '%s' AND '%s' ", Date.valueOf(startDate), Date.valueOf(endDate));
        return this.getDataOfAddressBookWithingRange(sql);
    }

    private List<ContactInfo> getDataOfAddressBookWithingRange(String sql) throws CustomeException {

        List<ContactInfo> contactInfoList = new ArrayList<>();
        try (Connection connection = this.getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            contactInfoList = this.getEmployeePayRollData(resultSet);
        } catch (SQLException | CustomeException throwables) {
            throw new CustomeException("Query Failed !");
        }

        return contactInfoList;
    }

    public Map<String, Integer> getContactsOfAddressBookGroupedByState() throws CustomeException {
        Map<String, Integer> contactsToCountOfStateMap = new HashMap<>();
        String sql = "select state,count(id) as count from contact_db group by state;";
        try (Connection connection = this.getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                String state = resultSet.getString("state");
                int count = resultSet.getInt("count");
                contactsToCountOfStateMap.put(state, count);

            }
            return contactsToCountOfStateMap;

        } catch (SQLException throwable) {
            throw new CustomeException("Query Failed !");
        }

    }

    public int updateCityUsingStatement(String firstname, String name) throws CustomeException {
        String sql = String.format("Update contact_db set city='%s' where firstName='%s'", name, firstname);
        try (Connection connection = this.getConnection()) {
            Statement statement = connection.createStatement();
            return statement.executeUpdate(sql);
        } catch (SQLException throwables) {
            throw new CustomeException("Query Failed !");
        }

    }

    public List<ContactInfo> readData() throws CustomeException {
        String sql = "SELECT * FROM contact_db;";
        return this.getDataWhenSqlIsGiven(sql);

    }

    private void prepareStatementForEmployeeData() throws CustomeException {
        try {
            Connection connection = this.getConnection();
            String sql = "Select * from contact_db where firstName=?;";
            contactDataStatement = connection.prepareStatement(sql);
        } catch (SQLException e) {
            throw new CustomeException("Query Failed !");
        }
    }

    public ContactInfo addContactsInAddressBook(String firstName, String lastName, String phone, String email, String address, String city, String state, String pincode, LocalDate start) throws CustomeException, SQLException {
        int empId = -1;
        ContactInfo contactInfo = null;
        Connection connection = null;
        connection = this.getConnection();
        try {
            connection.setAutoCommit(false);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        try (Statement statement = connection.createStatement()) {

            String sql = String.format("insert into contact_db(firstName,lastName,phone_no,email_id,address,city,state,zip,start) values('%s','%s','%s','%s','%s','%s','%s','%s','%s')", firstName, lastName, phone, email, address, city, state, pincode, Date.valueOf(start));
            int rowAffected = statement.executeUpdate(sql,statement.RETURN_GENERATED_KEYS);

            if (rowAffected == 1) {
                ResultSet resultSet = statement.getGeneratedKeys();
                if (resultSet.next()) {
                    empId = resultSet.getInt(1);
                }
                System.out.println("empid " +empId);
                contactInfo = new ContactInfo(empId, firstName, lastName, phone, email, address, city, state, pincode, start);
                connection.commit();
                return contactInfo;
            }

        }
        catch (SQLException throwables)
        {
            connection.rollback();
            return contactInfo;
        }
        finally
        {
            try {
                connection.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

        }
        return null;
    }
    synchronized ContactInfo  addMultipleContactsUsingThread(String firstName, String lastName, String phone, String email, String address, String city, String state, String pincode, LocalDate start) throws CustomeException, SQLException {
        int empID=-1;
        ContactInfo contactInfo = null;
        Connection connection = null;
        connection = this.getConnection();

            Statement statement = connection.createStatement();
            String sql = String.format("insert into contact_db(firstName,lastName,phone_no,email_id,address,city,state,zip,start) values('%s','%s','%s','%s','%s','%s','%s','%s','%s')", firstName, lastName, phone, email, address, city, state, pincode, Date.valueOf(start));
            int rowAffected = statement.executeUpdate(sql,statement.RETURN_GENERATED_KEYS);

            if (rowAffected == 1)
            {
                ResultSet resultSet = statement.getGeneratedKeys();
                if (resultSet.next()) {
                    empID = resultSet.getInt(1);
                }
                System.out.println("empid " +empID);

            }
            contactInfo = new ContactInfo(firstName, lastName, phone, email, address, city, state, pincode, start);
            System.out.println(contactInfo.getFirstName()+" "+contactInfo.getAddress());
            return contactInfo;




    }
}


