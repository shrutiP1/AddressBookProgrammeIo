package com.bridgelab.AddressBookProblem;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AddressBookDbService
{
       private static AddressBookDbService addressBookDbService;
       public static AddressBookDbService getInstance()
        { if(addressBookDbService==null)
                   return   addressBookDbService=new AddressBookDbService();
          return addressBookDbService;
        }
       private Connection getConnection() throws CustomeException {
           String jdbcURL = "jdbc:mysql://localhost:3306/addressbook_db?useSSL=false";
           String userName = "root";
           String password = "mysql";
           Connection con = null;
           System.out.println("Connecting to database");
           try
           {
               con=DriverManager.getConnection(jdbcURL,userName,password);
               System.out.println("connected to database"+con);
           }
           catch (SQLException throwables)
           {
              throw new CustomeException("Query Failed !");
           }
           return con;

       }
       private List<ContactInfo> getEmployeePayRollData(ResultSet resultSet) throws SQLException {
           List<ContactInfo> empPayRollList=new ArrayList<>();
           while(resultSet.next())
           {
               int id=resultSet.getInt("id");
               String firstName=resultSet.getString("firstName");
               String lastName=resultSet.getString("lastName");
               String  phoneNo=resultSet.getString("phone_no");
               String email=resultSet.getString("email_id");
               String address=resultSet.getString("address");
               String city=resultSet.getString("city");
               String state=resultSet.getString("state");
               String zip=resultSet.getString("zip");

               empPayRollList.add(new ContactInfo(id,firstName,lastName,phoneNo,email,address,city,state,zip));

           }
           return empPayRollList;

       }
      private List<ContactInfo> getDataWhenSqlIsGiven(String sql) throws CustomeException {
          Connection connection=this.getConnection();
          List<ContactInfo> empPayRollList=new ArrayList<>();
          try
          {
              Statement statement=connection.createStatement();
              ResultSet resultSet=statement.executeQuery("Select *from contact_db");
              empPayRollList=this.getEmployeePayRollData(resultSet);

          }
          catch (SQLException throwables) {
              throwables.printStackTrace();
          }
          return empPayRollList;


      }
      public List<ContactInfo> readData() throws CustomeException {
            String sql="SELECT * FROM contact_db;";
            return this.getDataWhenSqlIsGiven(sql);

       }


}
