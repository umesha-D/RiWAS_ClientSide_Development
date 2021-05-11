package com;
import java.sql.*;

	
	

	public class Item {

		public Connection connect()
		{
		 Connection con = null;

		 try
		 {
			 Class.forName("com.mysql.jdbc.Driver");
			 con= DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/items ","root", "");
			 
			 
		 //For testing
		 System.out.print("Successfully connected");
		 }
		 
		 catch(Exception e)
		 {
			 e.printStackTrace();
		 }

		 return con;
		}
		
		public String readItems()
		{ 
				String output = "";
				
				try
				{ 
						Connection con = connect();
						
						if (con == null) 
						{ 
							return "Error while connecting to the database for reading."; 
						} 
					 
			 // Prepare the html table to be displayed
					 output = "<table border='1'><tr><th>Item Code</th>" 
								 +"<th>Item Name</th><th>Item Price</th>"
								 + "<th>Item Description</th>" 
								 + "<th>Update</th><th>Remove</th></tr>"; 
					 
					 String query = "select * from item"; 
					 Statement stmt = con.createStatement(); 
					 ResultSet rs = stmt.executeQuery(query); 
					 
			 // iterate through the rows in the result set
					 while (rs.next()) 
					 { 
						 String itemID = Integer.toString(rs.getInt("itemID")); 
						 String itemCode = rs.getString("itemCode"); 
						 String itemName = rs.getString("itemName"); 
						 String itemPrice = Double.toString(rs.getDouble("itemPrice")); 
						 String itemDesc = rs.getString("itemDesc"); 
						 
			 // Add a row into the html table
						 output += "<tr><td><input id ='hidItemIDUpdate' name ='hidItemIDUpdate' type='hidden' value='" + itemID + " '>"	+ itemCode + "</td>";
						
						 output += "<td>" + itemName + "</td>"; 
						 output += "<td>" + itemPrice + "</td>"; 
						 output += "<td>" + itemDesc + "</td>";
			 // buttons
						 output += "<td><input name='btnUpdate' id ='" + itemID + " ' type='button' value='Update' class=' btnUpdate btn btn-secondary'></td><td><input name='btnRemove' type='button' value='Remove' class='btnRemove btn btn-danger' data-rechearcher='"+ itemID + " '>" + "</td></tr>";  
					 } 
					 con.close(); 
					 
			 // Complete the html table
					 output += "</table>"; 
					 
					 
					 
			 } 
				catch (Exception e) 
				{ 
					output = "Error while reading the items."; 
					System.err.println(e.getMessage()); 
				} 
				return output; 
		}

		public String insertItem(String code, String name,String price, String desc)
	    {
				 String output = "";
				 
				 try
				 {
					 Connection con = connect();
					 
					 if (con == null)
					 {
						 return "Error while connecting to the database for inserting.";
					 }
					 
					 // create a prepared statement
					 String query = " insert into item(`itemID`,`itemCode`,`itemName`,`itemPrice`,`itemDesc`)"+ " values (?, ?, ?, ?, ?)";
				 
				 
					 PreparedStatement preparedStmt = con.prepareStatement(query);
				 
					 // binding values
					 preparedStmt.setInt(1, 0);
					 preparedStmt.setString(2, code);
					 preparedStmt.setString(3, name);
					 preparedStmt.setDouble(4, Double.parseDouble(price));
					 preparedStmt.setString(5, desc);
				 
				 
					 // execute the statement
					 preparedStmt.execute();
					 con.close();
					 
					 String newItems = readItems();
					 output = "{\"status\":\"success\", \"data\": \"" + newItems + "\"}";
				 }
				 catch (Exception e)
				 {
					 output = "{\"status\":\"error\", \"data\":\"Error while inserting the item.\"}";
					 System.err.println(e.getMessage());
					 
				 }
				 return output;
				 
				 
	    
	     
			
	    }
		public String updateItem(int itemID,String itemCode,String itemName,String itemPrice,String itemDesc)
		{ 
				String output = ""; 
				try
				 { 
					 Connection con = connect(); 
					 if (con == null) 
					 { 
						 return "Error while connecting to the database for updating."; 
					 } 
				 // create a prepared statement
					 String query = "update item set  itemCode = ?,  itemName = ?, itemPrice = ?, itemDesc = ? where itemID = ?"; 
					 
					 PreparedStatement preparedStmt = con.prepareStatement(query); 
					 
					 // binding values
					 preparedStmt.setString(1, itemCode);
					 preparedStmt.setString(2, itemName);
					 preparedStmt.setDouble(3, Double.parseDouble(itemPrice));
					 preparedStmt.setString(4, itemDesc);
					 preparedStmt.setInt(5, itemID);


					 // execute the statement
					 preparedStmt.execute(); 
					 con.close(); 
					 
					 String newItems = readItems();
					 output = "{\"status\":\"success\", \"data\": \"" +newItems + "\"}";
					
				 } 
				catch (Exception e) 
				 { 
					output = "{\"status\":\"error\", \"data\":\"Error while updating the item.\"}";
					 System.err.println(e.getMessage()); 
				 } 
				return output; 
		}

		public String deleteItem(String itemIDData)
		{ 
				String output = ""; 
				try
			    { 
					 Connection con = connect(); 
					 if (con == null) 
					 { 
						 return "Error while connecting to the database for deleting."; 
					 } 
				 // create a prepared statement
					 String query = "delete from item where itemID=?"; 
					 PreparedStatement preparedStmt = con.prepareStatement(query); 
					 // binding values
					 preparedStmt.setInt(1, Integer.parseInt(itemIDData)); 

					 // execute the statement
					 preparedStmt.execute(); 
					 con.close(); 
					
					 String newItems = readItems();
					 output = "{\"status\":\"success\", \"data\": \"" +
			 newItems + "\"}";
					 
				} 
				catch (Exception e) 
				{ 
					output = "{\"status\":\"error\", \"data\": \"Error while deleting the item.\"}"; 
					 System.err.println(e.getMessage()); 
			    } 
				return output; 
			}


		
	}
	


