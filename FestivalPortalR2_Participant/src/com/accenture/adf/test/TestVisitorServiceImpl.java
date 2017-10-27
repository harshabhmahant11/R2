package com.accenture.adf.test;

import static org.junit.Assert.assertEquals;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.accenture.adf.businesstier.dao.EventDAO;
import com.accenture.adf.businesstier.dao.VisitorDAO;
import com.accenture.adf.businesstier.entity.Event;
import com.accenture.adf.businesstier.entity.Visitor;
import com.accenture.adf.businesstier.service.VisitorServiceImpl;
import com.accenture.adf.helper.FERSDataConnection;

/**
 * Junit test class for VisitorServiceImpl
 *
 */
public class TestVisitorServiceImpl {
	private static Connection connection = null;
	private static PreparedStatement statement = null;
	private static ResultSet resultSet = null;


	private List<Event> visitorList;	
	private Visitor visitor;
	private VisitorServiceImpl visitorServiceImpl;
	private VisitorDAO visitorDAO;

	/**
	 * Set up the initial methods 
	 * @throws Exception
	 */
	@Before
	public void setUp() throws Exception {		
		visitorServiceImpl = new VisitorServiceImpl();
		visitor = new Visitor();
	}

	/**
	 * Deallocates the objects after execution of every method
	 * @throws Exception
	 */
	@After
	public void tearDown() throws Exception {
		/**
		 * @TODO: Release all the objects here by assigning them null  
		 */
		visitorServiceImpl=null;
		visitor=null;
	}

	/**
	 * Test case for method createVisitor
	 */
	@Test
	public void testCreateVisitor() {
		/**
		 * @TODO: Set the appropriate values for visitor object and
		 * call the method createVisitor by passing an argument of this visitor 
		 * object and then asserting the returned type of this method
		 */		
		visitor.setAddress("address");
		visitor.setEmail("email");
		visitor.setFirstName("firstName");
		visitor.setLastName("LastName");
		visitor.setPassword("password");
		visitor.setPhoneNumber("phoneNumber");
		visitor.setUserName("TestUserName");
		boolean status = visitorServiceImpl.createVisitor(visitor);
		
		assertEquals(true, status);
	}

	/**
	 * Test case for method createVisitor
	 */
	@Test
	public void testSearchVisitor() {
		/**
		 * @TODO: Call searchVisitor method by passing the appropriate arguments 
		 * and then asserting the returned type visitor username with the argument passed
		 */		

		visitor = visitorServiceImpl.searchVisitor("bsmith", "password");
		assertEquals("bsmith",visitor.getUserName() );

	}

	/**
	 * Test case for method RegisterVisitor
	 */
	@Test
	public void testRegisterVisitor() {
		/**
		 * @TODO: Call RegisterVisitor method by passing visitor object which 
		 * can be retrieved using searchVisitor method and then asserting the returned
		 * type of RegisterVisitor method 
		 */		
		visitor = visitorServiceImpl.searchVisitor("bsmith", "password");

		EventDAO edao = new EventDAO();
		visitorServiceImpl.RegisterVisitor(visitor, 1001,10001);

		try {
			connection = FERSDataConnection.createConnection();
			
			String qry = "SELECT COUNT(*) AS EVENTCOUNT FROM EVENTSESSIONSIGNUP WHERE EVENTSESSIONID=? AND VISITORID=? AND EVENTID=?;";
			
			
			statement = connection.prepareStatement(qry);
			
			statement.setInt(1, 10001);
			statement.setInt(2, visitor.getVisitorId());
			statement.setInt(3, 1001);
			
			resultSet = statement.executeQuery();
			
			 resultSet.next();
			
			 int count = resultSet.getInt(1);
			 
			 
			 assertEquals(1, count);
			 
			 
			 int retVal;
				int retVal2;
				connection = FERSDataConnection.createConnection();
				statement = connection.prepareStatement("SELECT SEATSAVAILABLE FROM EVENTSESSION WHERE EVENTSESSIONID = 10001");
				resultSet =	statement.executeQuery();
				resultSet.next();
				retVal = resultSet.getInt("SEATSAVAILABLE");
				retVal--;
				
				edao.updateEventNominations(1001,10001); 
				
				
				resultSet =	statement.executeQuery();
				resultSet.next();
				retVal2 = resultSet.getInt("SEATSAVAILABLE");
				
				assertEquals(retVal,retVal2 );
			 
			 
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	/**
	 * Test case for method showRegisteredEvents
	 */
	@Test
	public void testShowRegisteredEvents() {
		/**
		 * @TODO: Call showRegisteredEvents method by passing visitor object which 
		 * can be retrieved using searchVisitor method and then asserting the returned
		 * type of showRegisteredEvents method 
		 */		
		ArrayList<Object[]> registeredEventList = new ArrayList<Object[]>();

		visitor = visitorServiceImpl.searchVisitor("npatel", "password");
		
		
		registeredEventList = visitorServiceImpl.showRegisteredEvents(visitor);
		
		assertEquals(1, registeredEventList.size());
	}


	/**
	 * Test case for method updateVisitorDetails
	 */
	@Test
	public void testUpdateVisitorDetails() {
		/**
		 * @TODO: Call updateVisitorDetails method by passing the visitor object which
		 * can be retrieved using searchVisitor method and then asserting the returned
		 * type of updateVisitorDetails
		 */		
		visitorDAO = new VisitorDAO();
		int status=0;
		try {
			visitor = visitorDAO.searchUser("bsmith", "password");
			
			status = visitorServiceImpl.updateVisitorDetails(visitor);
			
			
			
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertEquals(1, status);
		
	}

	/**
	 * Test case for method unregisterEvent
	 */
	@Test
	public void testUnregisterEvent() {
		/**
		 * @TODO: Call unregisterEvent method by passing the visitor object which can be
		 * retrieved using searchVisitor method and then asserting the returned type 
		 * of unregisterEvent
		 */		
		visitorDAO = new VisitorDAO();
		int status = 0;
		
		try {
			Connection connection = FERSDataConnection.createConnection();
			
			
			visitor = visitorDAO.searchUser("bsmith", "password");
			visitorServiceImpl.unregisterEvent(visitor, 1002, 10002);
			
			//Checking if the data is deleted or not
			
			PreparedStatement statement = connection.prepareStatement("SELECT * FROM EVENTSESSIONSIGNUP WHERE EVENTSESSIONID=? AND VISITORID=? AND EVENTID =?");
			statement.setInt(1,10002);
			statement.setInt(2,visitor.getVisitorId());
			statement.setInt(3,1002);
			ResultSet resultSet = statement.executeQuery();
			if(resultSet.next()){
				status = 1;
			}else
				status = -1;
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		assertEquals(-1, status);
	}

}
