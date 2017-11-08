package com.accenture.adf.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.accenture.adf.businesstier.entity.Event;
import com.accenture.adf.businesstier.entity.EventCoordinator;
import com.accenture.adf.businesstier.entity.Visitor;
import com.accenture.adf.businesstier.service.EventServiceImpl;
import com.accenture.adf.exceptions.FERSGenericException;
import com.accenture.adf.helper.FERSDataConnection;

/**
 * Junit test case to test class EventServiceImpl
 * 
 */
public class TestEventServiceImpl {

	private static Connection connection = null;
	private static PreparedStatement statement = null;
	private static ResultSet resultSet = null;
	private List<Object[]> eventList;
	private Visitor visitor;
	private EventServiceImpl eventServiceImpl;


	/**
	 * Set up the objects required before execution of every method
	 * 
	 * @throws Exception
	 */
	@Before
	public void setUp() throws Exception {
		eventServiceImpl = new EventServiceImpl();
		visitor = new Visitor();
	}

	/**
	 * Deallocates the objects after execution of every method
	 * 
	 * @throws Exception
	 */
	@After
	public void tearDown() throws Exception {
		/**
		 * @TODO: Release all the objects here by assigning them null  
		 */
	}

	/**
	 * Test case to test the method getAllEvents
	 */
	@Test
	public void testGetAllEvents() {
		/**
		 * @TODO: Call getAllEvents method and assert it for the size of returned array
		 */	
		eventList=eventServiceImpl.getAllEvents();
		assertEquals(12,eventList.size());


	}

	/**
	 * Test case to test the method checkEventsofVisitor
	 */
	@Test
	public void testCheckEventsofVisitor() {
		/**
		 * @TODO: Call checkEventsofVisitor and assert the returned type of this method
		 * for appropriate return type
		 */	
		
		Boolean flag=false;

		visitor.setVisitorId(1003);

		flag=eventServiceImpl.checkEventsofVisitor(visitor,1002,10002);

		assertEquals(true,flag);


	}

	/**
	 * Test case to test the method updateEventDeletions
	 */
	@Test
	public void testUpdateEventDeletions() {
		/**
		 * @TODO: Call updateEventDeletions and assert the return type of this method
		 */	
		try {

			connection = FERSDataConnection.createConnection();

			statement = connection.prepareStatement("SELECT SEATSAVAILABLE FROM EVENTSESSION WHERE EVENTSESSIONID = ? AND EVENTID = ?");

			statement.setInt(1, 10002);

			statement.setInt(2, 1002);

			resultSet = statement.executeQuery();

			resultSet.next();

			int val1 = resultSet.getInt(1);
			
			eventServiceImpl.updateEventDeletions(1002,10002);

			resultSet = statement.executeQuery();

			resultSet.next();

			int val2 = resultSet.getInt(1);
		
			assertEquals(true,((++val1)==val2));

			

	} catch (ClassNotFoundException e) {

		// TODO Auto-generated catch block

		e.printStackTrace();

	} catch (SQLException e) {

		// TODO Auto-generated catch block

		e.printStackTrace();	

	} catch (Exception e) {

		// TODO Auto-generated catch block

		e.printStackTrace();

	}


	}

	/**
	 * Junit test case for getEventCoordinator
	 */
	@Test
	public void testGetEventCoordinator() {
		/**
		 * @TODO: Call getAllEventCoordinators and assert the size of return type of this method
		 */		
		
		   boolean flag=false;
		    if(eventServiceImpl.getAllEventCoordinators().size()>0)
		     flag=true;
		    assertEquals(true, flag);
	}

	/**
	 * Junit test case for getEvent
	 */
	@Test
	public void testGetEvent() {
		/**
		 * @TODO: Call getEvent and assert the event id of this event with 
		 * passed event id 
		 */		
		boolean flag = false;
		   Event event = new Event();
		   //event.setEventid(1005);
		   event = eventServiceImpl.getEvent(1005, 10005);
		   if(event.getEventid()==1005)
		    flag = true;
		   assertEquals(true, flag);

	}
	
	/**
	 * Junit test case for updateEvent
	 */
	@Test
	public void testInsertEvent() {
		/**
		 * @TODO: Call insertEvent
		 * Create event object by setting appropriate values
		 * Assert the status of insertEvent method
		 */		
		 int status = 0;
		   boolean flag = false;
		   Event event = new Event();
//		   event.setEventid(1050);
//		   event.setSessionId(10050);
		   event.setName("Meditation");
		   event.setDescription("option");
		   event.setPlace("Accenture");
		   event.setDuration("1300-1400");
		   event.setEventtype("Nature");
		   event.setEventSession(1);
		   event.setEventCoordinatorId(105);
		   event.setSeatsavailable(200);
		   status=eventServiceImpl.insertEvent(event);
		   if(status>0)
		    flag = true;
		   assertEquals(true, flag);
	}

	/**
	 * Junit test case for updateEvent
	 */
	@Test
	public void testUpdateEvent() {
		/**
		 * @TODO: Fetch Event object by calling getAllEvents method 
		 * Update event object by setting appropriate values
		 * Call updateEvent method
		 * Assert the status of updateEvent method
		 */	
		
		int status = 0;
		   boolean flag = false;
		   Event event = new Event();
		   event.setSessionId(10003);
		   event.setEventid(1004);
		   event.setSeatsavailable(5000);
		   event.setDescription("describe");
		   event.setDescription("describe");
		   
		   status = eventServiceImpl.updateEvent(event);
		   if(status>0)
		    flag = true;
		   assertEquals(true, flag);
	}

	/**
	 * Junit test case for deleteEvent
	 */
	@Test
	public void testDeleteEvent() {
		/**
		 * @TODO: Fetch Event object by calling getAllEvents method 
		 * Update event object by setting appropriate values
		 * Call deleteEvent method
		 * Assert the status of deleteEvent method
		 */	
		
		 int status = 0;
		   boolean flag = false;
//		   Event event = new Event();
		   status = eventServiceImpl.deleteEvent(1003,10003);
		   if(status>0)
		    flag = true;
		   assertEquals(true, flag);
	}

}
