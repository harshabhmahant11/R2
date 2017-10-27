package com.accenture.adf.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.accenture.adf.businesstier.dao.VisitorDAO;
import com.accenture.adf.businesstier.entity.Visitor;
import com.accenture.adf.exceptions.FERSGenericException;
import com.accenture.adf.helper.FERSDataConnection;

/**
 * JUnit test case for VisitorDAO class for testing all repository methods to
 * call database sub-routines
 * 
 */
public class TestVisitorDAO {

	private Visitor visitor;
	private VisitorDAO visitorDAO;
	private ArrayList<Object[]> registeredEvents;

	/**
	 * Setting up initial objects
	 * 
	 * @throws Exception
	 */
	@Before
	public void setUp() throws Exception {
		visitor = new Visitor();
		visitorDAO = new VisitorDAO();
		registeredEvents = new ArrayList<Object[]>();
	}

	/**
	 * Deallocating objects after execution of every method
	 * @throws Exception
	 */
	@After
	public void tearDown() throws Exception {
		/**
		 * @TODO: Release all the objects here by assigning them null  
		 */
	}

	/**
	 * Test case for method insertData
	 */
	@Test
	public void testInsertData() {
		/**
		 * @TODO: Create visitor object by setting appropriate values
		 * Call insertData method by passing this visitor object
		 * Search this new visitor object by calling searchUser method
		 * Assert the values of username
		 */	
		visitor.setFirstName("Dhruv");
		visitor.setAddress("ds");
		visitor.setAdmin(false);
		visitor.setEmail("Dhruv2@gmail.com");
		visitor.setLastName("Arora");
		visitor.setPassword("123");
		visitor.setPhoneNumber("98979865");
		visitor.setVisitorId(1012);
		visitor.setUserName("dhruv12");
		
		boolean flag=false;
		Visitor v1 = null;
		try{
			flag=visitorDAO.insertData(visitor);
			 v1 = new Visitor();
			//v1=visitorDAO.searchUser(visitor.getUserName(), visitor.getPassword());
			v1=visitorDAO.searchUser("dhruv12", "123");	
		}catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			fail("runtime"+e);
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertEquals(true,flag);
		//assertEquals(visitor.getUserName(),v1.getUserName());
	
	}	

	/**
	 * Test case for method searchUser
	 */
	@Test
	public void testSearchUser() {
		/**
		 * @TODO: Call searchUser method for valid values of username
		 * and password and assert the value of username for the returned type of method
		 */		
		visitor.setUserName("bsmith");
		visitor.setVisitorId(1001);
		Visitor v1 = null;
		try {
			v1 = visitorDAO.searchUser("bsmith", "password");
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		assertEquals(visitor.getVisitorId(),v1.getVisitorId());
	
	}

	/**
	 * Test case for method registerVisitorToEvent
	 */
	@Test
	public void testRegisterVisitorToEvent() {
		/**
		 * @TODO: Fetch visitor object by calling searchUser for valid values of username and password
		 * Pass this visitor object and valid eventid to registerVisitorToEvent method
		 * and assert the value
		 */		
		Visitor visitor = new Visitor();
		VisitorDAO vDao = new VisitorDAO();
		try {
			visitor=vDao.searchUser("jjones", "password");
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		if(visitor!=null)
		{
			
			try {
				
				vDao.registerVisitorToEvent(visitor, 10001,1001);
				String query = "Select count(*) from eventsessionsignup where visitorid=1003";
				Connection c = FERSDataConnection.createConnection();
				PreparedStatement s = c.prepareStatement(query);
				ResultSet rs=s.executeQuery();
				rs.next();
				assertEquals(rs.getInt(1)==1,true);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				//System.out.println(e.getMessage());
				new FERSGenericException("Unable to Register",new Exception());
			}
		}
		
		
	}	

	/**
	 * Test case for method registeredEvents
	 */
	@Test
	public void testRegisteredEvents() {
		/**
		 * @TODO: Fetch visitor object by calling searchUser for valid values of username and password
		 * Pass this visitor object and valid eventid to registeredEvents method
		 * and assert the value
		 */		
		
		try {
			visitor = visitorDAO.searchUser("bsmith", "password");
			//int eventid = 1002;
			registeredEvents = visitorDAO.registeredEvents(visitor);
			Iterator<Object[]> its = registeredEvents.iterator();

			while(its.hasNext()){
				
			for(int i=0;i<registeredEvents.size();i++){
				Object[] obj = new Object[11];
				obj = registeredEvents.get(i);
				System.out.println("Printing the events registered by the visiorId = "+visitor.getVisitorId());;
				for(Object o:obj){
					System.out.print(" "+o+" ");
				}
				its.next();
			}
			
			}
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Test case for method updateVisitor
	 */
	@Test
	public void testUpdateVisitor() {
		/**
		 * @TODO: Fetch visitor object by calling searchUser for valid values of username and password
		 * Update the value in this visitor object
		 * Pass this visitor object to updateVisitor method
		 * and assert the value of changed value
		 */		
	}

	/**
	 * Test case for method registeredEvents
	 */
	@Test
	public void testUnregisterEvent() {
		/**
		 * @TODO: Fetch visitor object by calling searchUser for valid values of username and password
		 * Pass this visitor object and valid eventid to unregisterEvent method
		 * and assert the value
		 */		
	}
	
	/**
	 * Test case for method change password
	 */
	/*@Test
	public void testChangePassword_VisitorNull() {
		*//**
		 * @TODO: Call changePassword method by passing visitor object as null
		 *//*		
	}*/
	
	/**
	 * Test case for method change password
	 */
	@Test
	public void testChangePassword_VisitorNull() {
		try {
			visitor = null;
			visitorDAO.changePassword(visitor);
		} catch (SQLException exception) {
			fail("SQL Exception");
		} catch (ClassNotFoundException exception) {
			fail("Class Not Found Exception");
		} catch (Exception exception) {
			fail("NULL Exception");
		}
	}

}
