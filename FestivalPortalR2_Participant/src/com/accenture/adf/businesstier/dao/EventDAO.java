package com.accenture.adf.businesstier.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.accenture.adf.businesstier.entity.Event;
import com.accenture.adf.businesstier.entity.EventCoordinator;
import com.accenture.adf.businesstier.entity.Visitor;
import com.accenture.adf.exceptions.FERSGenericException;
import com.accenture.adf.helper.FERSDataConnection;
import com.accenture.adf.helper.FERSDbQuery;

/**
 * <br/>
 * CLASS DESCRIPTION: <br/>
 * A Data Access Object (DAO) class for handling and managing event related data
 * requested, updated, and processed in the application and maintained in the
 * database. The interface between the application and event data persisting in
 * the database. <br/>
 * 
 * @author krishna.kishore
 * 
 */
public class EventDAO {

	// LOGGER for handling all transaction messages in EVENTDAO
	private static Logger log = Logger.getLogger(EventDAO.class);

	// JDBC API classes for data persistence
	private Connection connection = null;
	private PreparedStatement statement = null;
	private ResultSet resultSet = null;
	private FERSDbQuery query;

	// Default constructor for injecting Spring dependencies for SQL queries
	public EventDAO() {
		ApplicationContext context = new ClassPathXmlApplicationContext(
				"applicationContext.xml");
		query = (FERSDbQuery) context.getBean("SqlBean");
	}

	/**
	 * <br/>
	 * METHOD DESCRIPTION: <br/>
	 * DAO for displaying all the Events in the Event Table in the Database <br/>
	 * 
	 * PSEUDOCODE: <br/>
	 * Create a new connection to the database. <br/>
	 * Prepare a statement object using the connection that gets all the events
	 * from the event table. <br/>
	 * Execute the SQL statement and keep a reference to the result set.<br/>
	 * Using a WHILE LOOP, store each record in the result set returned in an
	 * Event object by setting the values of the Event attributes as the
	 * corresponding values in the Result set.<br/>
	 * Return the ArrayList to the calling method. <br/>
	 * 
	 * @return Collection of Events
	 * 
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * 
	 */
	public ArrayList<Object[]> showAllEvents() throws ClassNotFoundException,
			SQLException {
		connection = FERSDataConnection.createConnection();
		statement = connection.prepareStatement(query.getSearchEvent());
		resultSet = statement.executeQuery();
		ArrayList<Object[]> eventList = new ArrayList<Object[]>();
		log.info("All Events retreived from Database :" + eventList);
		while (resultSet.next()) {
			Object[] eventObject = new Object[8];
			eventObject[0] = resultSet.getInt("eventid");
			eventObject[1] = resultSet.getString("name");
			eventObject[2] = resultSet.getString("description");
			eventObject[3] = resultSet.getString("duration");
			eventObject[4] = resultSet.getString("eventtype");
			eventObject[5] = resultSet.getString("places");
			eventObject[6] = resultSet.getInt("seatsavailable");
			eventObject[7] = resultSet.getInt("eventsessionid");
			eventList.add(eventObject);
		}
		resultSet.close();
		
		FERSDataConnection.closeConnection();
		return eventList;
	}

	/**
	 * <br/>
	 * METHOD DESCRIPTION:<br/>
	 * DAO for updating events after the visitor registers for an event <br/>
	 * 
	 * @return void
	 * 
	 * @param eventid
	 * @param sessionid
	 * 
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws Exception
	 * 
	 */

	public void updateEventNominations(int eventid, int sessionid)
			throws ClassNotFoundException, SQLException, Exception {
		connection = FERSDataConnection.createConnection();
		statement = connection.prepareStatement(query.getUpdateEvent());
		statement.setInt(1, sessionid);
		statement.setInt(2, eventid);
		int status = statement.executeUpdate();
		if (status <= 0)
			throw new FERSGenericException("Records not updated properly",
					new Exception());
		log.info("Event registration status was updated in Database and Seat allocated");
		FERSDataConnection.closeConnection();

	}

	/**
	 * <br/>
	 * METHOD DESCRIPTION:<br/>
	 * DAO for checking visitor has already registered for EVENT or not. Sends
	 * boolean values about status.<br/>
	 * 
	 * @return boolean
	 * 
	 * @param visitor
	 * @param eventid
	 * @param sessionid
	 * 
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * 
	 */
	public boolean checkEventsofVisitor(Visitor visitor, int eventid,
			int sessionid) throws ClassNotFoundException, SQLException {
		connection = FERSDataConnection.createConnection();
		log.info("Status obtained for Visitor :" + visitor.getFirstName()
				+ " to event with ID :" + eventid);
		statement = connection.prepareStatement(query.getCheckEvent());
		statement.setInt(1, sessionid);
		statement.setInt(2, visitor.getVisitorId());
		statement.setInt(3, eventid);
		resultSet = statement.executeQuery();
		int status = 0;
		while (resultSet.next()) {
			status = resultSet.getInt(1);
		}
		resultSet.close();
		log.info("No of times visitor registered for Event :" + status);
		FERSDataConnection.closeConnection();
		if (status >= 1)
			return true;
		else
			return false;
	}

	/**
	 * <br/>
	 * METHOD DESCRIPTION: <br/>
	 * DAO for update event database after unregistering event by visitor <br/>
	 * 
	 * @return void
	 * 
	 * @param eventid
	 * @param eventsessionid
	 * 
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws Exception
	 * 
	 */

	public void updateEventDeletions(int eventid, int eventsessionid)
			throws ClassNotFoundException, SQLException, Exception {
		connection = FERSDataConnection.createConnection();
		statement = connection.prepareStatement(query.getUpdateDeleteEvent());
		statement.setInt(1, eventsessionid);
		statement.setInt(2, eventid);
		int status = statement.executeUpdate();
		if (status <= 0)
			throw new FERSGenericException("Records not updated properly",
					new Exception());
		log.info("Event registration status was updated in Database and Seat released");
		FERSDataConnection.closeConnection();

	}

	/**
	 * <br/>
	 * METHOD DESCRIPTION: <br/>
	 * DAO for displaying all the Events in the Event Table in the Database with
	 * names that contain the text entered by the user. <br/>
	 * 
	 * PSEUDOCODE: <br/>
	 * Create a new connection to the database. <br/>
	 * Prepare a statement object using the connection that gets all the events
	 * from the event table. <br/>
	 * Execute the SQL statement and keep a reference to the result set. <br/>
	 * Using a WHILE LOOP, store each record in the result set returned in an
	 * Event object by setting the values of the Event attributes as the
	 * corresponding values in the Result set. <br/>
	 * Return the ArrayList to the calling method. <br/>
	 * 
	 * @param eventname
	 * 
	 * @return Collection of Events
	 * 
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * 
	 */

	public ArrayList<Object[]> showAllEvents(String eventname)
			throws ClassNotFoundException, SQLException {

		ArrayList<Object[]> eventList = new ArrayList<Object[]>();
	
		connection = FERSDataConnection.createConnection();
		String sql = query.getSearchByEventName();
		statement = connection.prepareStatement(sql);
		String ename = "%"+eventname+"%";
		statement.setString(1, ename);
		
		resultSet = statement.executeQuery();

		while(resultSet.next())
		{
			Object[] eventObject = new Object[8];
			eventObject[0] = resultSet.getInt("eventid");
			eventObject[1] = resultSet.getString("name");
			eventObject[2] = resultSet.getString("description");
			eventObject[3] = resultSet.getString("duration");
			eventObject[4] = resultSet.getString("eventtype");
			eventObject[5] = resultSet.getString("places");
			eventObject[6] = resultSet.getInt("seatsavailable");
			eventObject[7] = resultSet.getInt("eventsessionid");
			eventList.add(eventObject);
			
		}
		
		
		resultSet.close();
		FERSDataConnection.closeConnection();
		return eventList;
	}

	/**
	 * <br/>
	 * METHOD DESCRIPTION: <br/>
	 * DAO for displaying all the Events in the Event Table in the Database in
	 * ascending order. <br/>
	 * 
	 * PSEUDOCODE: <br/>
	 * Create a new connection to the database. <br/>
	 * Prepare a statement object using the connection that gets all the events
	 * from the event table in ascending order. <br/>
	 * Execute the SQL statement and keep a reference to the result set. <br/>
	 * Using a WHILE LOOP, store each record in the result set returned in an
	 * Event object by setting the values of the Event attributes as the
	 * corresponding values in the Result set. <br/>
	 * Return the ArrayList to the calling method. <br/>
	 * 
	 * 
	 * @return Collection of Events
	 * 
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * 
	 */

	public ArrayList<Object[]> showAllEventsAsc()
			throws ClassNotFoundException, SQLException {

		ArrayList<Object[]> eventList = new ArrayList<Object[]>();
		connection = FERSDataConnection.createConnection();
		String sql = query.getSearchEventAsc();
		statement = connection.prepareStatement(sql);
		
		
		resultSet = statement.executeQuery();

		while(resultSet.next())
		{
			Object[] eventObject = new Object[8];
			eventObject[0] = resultSet.getInt("eventid");
			eventObject[1] = resultSet.getString("name");
			eventObject[2] = resultSet.getString("description");
			eventObject[3] = resultSet.getString("duration");
			eventObject[4] = resultSet.getString("eventtype");
			eventObject[5] = resultSet.getString("places");
			eventObject[6] = resultSet.getInt("seatsavailable");
			eventObject[7] = resultSet.getInt("eventsessionid");
			eventList.add(eventObject);
			
		}
		
	
		resultSet.close();
		FERSDataConnection.closeConnection();
		return eventList;

	}

	/**
	 * <br/>
	 * METHOD DESCRIPTION: <br/>
	 * DAO for displaying all the Events in the Event Table in the Database in
	 * descending order. <br/>
	 * 
	 * PSEUDOCODE: <br/>
	 * Create a new connection to the database. <br/>
	 * Prepare a statement object using the connection that gets all the events
	 * from the event table in descending order. <br/>
	 * Execute the SQL statement and keep a reference to the result set. <br/>
	 * Using a WHILE LOOP, store each record in the result set returned in an
	 * Event object by setting the values of the Event attributes as the
	 * corresponding values in the Result set. <br/>
	 * Return the ArrayList to the calling method. <br/>
	 * 
	 * 
	 * @return Collection of Events
	 * 
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * 
	 */
	public ArrayList<Object[]> showAllEventsDesc()
			throws ClassNotFoundException, SQLException {

		ArrayList<Object[]> eventList = new ArrayList<Object[]>();

		connection = FERSDataConnection.createConnection();
		String sql = query.getSearchEventDesc();
		statement = connection.prepareStatement(sql);
		
		
		resultSet = statement.executeQuery();

		while(resultSet.next())
		{
			Object[] eventObject = new Object[8];
			eventObject[0] = resultSet.getInt("eventid");
			eventObject[1] = resultSet.getString("name");
			eventObject[2] = resultSet.getString("description");
			eventObject[3] = resultSet.getString("duration");
			eventObject[4] = resultSet.getString("eventtype");
			eventObject[5] = resultSet.getString("places");
			eventObject[6] = resultSet.getInt("seatsavailable");
			eventObject[7] = resultSet.getInt("eventsessionid");
			eventList.add(eventObject);
			
		}

		resultSet.close();
		FERSDataConnection.closeConnection();

		return eventList;

	}
	
	/**
	 * This method fetch the event on basis of eventId
	 * 
	 * PSEUDOCODE: <br/>
	 * Create a new connection to the database. <br/>
	 * Prepare a statement object using the connection that get an event
	 * from the event table for provided event id. <br/>
	 * Execute the SQL statement and keep a reference to the result set by using getGetEvent method. <br/>
	 * Using a WHILE LOOP, store each record in the result set returned in an
	 * Event object by setting the values of the Event attributes as the
	 * corresponding values in the Result set. <br/>
	 * Return the Event object to the calling method. <br/>
	 * 
	 * @param eventId
	 * @param sessionId
	 * @return
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public Event getEvent(int eventId, int sessionId)
			throws ClassNotFoundException, SQLException {
		
		Event event = new Event();
		
		connection = FERSDataConnection.createConnection();
		String qry = query.getGetEvent();
		statement = connection.prepareStatement(qry);
		statement.setInt(1, eventId);
		statement.setInt(2, sessionId);	
		resultSet = statement.executeQuery();
		
		while(resultSet.next())
		{
			event.setEventid(resultSet.getInt("eventid"));
			event.setName(resultSet.getString("name"));
			event.setDescription(resultSet.getString("description"));
			event.setDuration(resultSet.getString("duration"));
			event.setEventtype(resultSet.getString("eventtype"));
			event.setPlace(resultSet.getString("places"));
			event.setSeatsavailable(resultSet.getInt("seatsavailable"));
			event.setSessionId(resultSet.getInt("eventsessionid"));
		}
		
	
		return event;
	}

	/**
	 * This method updates the event
	 * 
	 * PSEUDOCODE: <br/>
	 * Create a new connection to the database. <br/>
	 * Prepare a statement object using the connection that get an event
	 * from the event table for provided event id. <br/>
	 * Execute the SQL statement and keep a reference to the result set. <br/>
	 * Update the event object by calling getUpdateEventSession method
	 * Event is updated in database. <br/>
	 * Return the status of executed query. <br/>
	 * 
	 * @param updateEvent
	 * @return
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public int updateEvent(Event updateEvent) throws ClassNotFoundException,
			SQLException {

		// TODO: Add code here.....
		// TODO: Pseudo-code are in the block comments above this method.
		// TODO: For more comprehensive pseudo-code with details,
		// refer to the Component/Class Detail Design Document
		connection = FERSDataConnection.createConnection();
		int statusevent=-1,statussession=-1;
		int sessionid=0;
		sessionid=updateEvent.getSessionId();
		try{	
		//updateTEvent:UPDATE EVENT E1 SET E1.NAME=?, E1.DESCRIPTION=?, E1.PLACES=?, E1.DURATION=?, E1.EVENTTYPE=? WHERE E1.EVENTID=?"
		statement = connection.prepareStatement(query.getUpdateTEvent());
		statement.setString(1,updateEvent.getName());
		statement.setString(2,updateEvent.getDescription());
		statement.setString(3,updateEvent.getPlace());
		statement.setString(4,updateEvent.getDuration());
		statement.setString(5,updateEvent.getEventtype());
		statement.setInt(6,updateEvent.getEventid());
		statusevent = statement.executeUpdate();
		
		//UpdateEventSession:UPDATE EVENTSESSION SET SEATSAVAILABLE=? WHERE EVENTSESSIONID = ? AND EVENTID = ?;
		statement =connection.prepareStatement(query.getUpdateEventSession());
		statement.setInt(1,updateEvent.getSeatsavailable());
		statement.setInt(2,sessionid);
		statement.setInt(3,updateEvent.getEventid());
		statussession=statement.executeUpdate();
		}
		catch(Exception e)
		{
			new FERSGenericException("Error in Updating",new Exception());
			System.out.println(e.getMessage());
		}
		System.out.println(statusevent+""+statussession);
		if(statusevent>0 && statussession>0)
			statusevent=1;
		else
			statusevent=-1;
		return statusevent;
	}

	/**
	 * This method inserts new event in database
	 * 
	 * PSEUDOCODE: <br/>
	 * Create a new connection to the database. <br/>
	 * Prepare a statement object using the connection that get an event
	 * from the event table for provided event id. <br/>
	 * Execute the SQL statement and keep a reference to the result set. <br/>
	 * Insert the event object by calling getInsertEvent method
	 * Event object is inserted in database  <br/>
	 * Return the status of executed query. <br/>
	 * 
	 * @param insertEvent
	 * @return
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public int insertEvent(Event iEvent) throws ClassNotFoundException,
			SQLException {
		
		// TODO: Add code here.....
		// TODO: Pseudo-code are in the block comments above this method.
		// TODO: For more comprehensive pseudo-code with details,
		// refer to the Component/Class Detail Design Document
		int estatus=0, sstatus=0;
		connection = FERSDataConnection.createConnection();
		int maxeventid,maxsessionid;
		statement = connection.prepareStatement(query.getSelectMaxEventId());
      	resultSet= statement.executeQuery();
		resultSet.next();
		maxeventid = resultSet.getInt(1);
		
		
		statement = connection.prepareStatement(query.getSelectMaxEventSessionId());
      	resultSet= statement.executeQuery();
		resultSet.next();
		maxsessionid = resultSet.getInt(1);
		
      	
		
		
		String qry = query.getInsertEvent();
		statement = connection.prepareStatement(qry);
		//SERT INTO EVENT(EVENTID, NAME, DESCRIPTION, PLACES, DURATION, EVENTTYPE) VALUES(?,?,?,?,?,?)"></property>
		statement.setInt(1, ++maxeventid);
		statement.setString(2, iEvent.getName());
		statement.setString(3, iEvent.getDescription());
		statement.setString(4, iEvent.getPlace());
		statement.setString(5, iEvent.getDuration());
		statement.setString(6, iEvent.getEventtype());
		
	 estatus = statement.executeUpdate();
		
		int no_of_sessions = iEvent.getEventSession();
		
//INSERT INTO EVENTSESSION(EVENTSESSIONiD, EVENTCOORDINATORID, EVENTID, SEATSAVAILABLE) VALUES (?,?,?,?)"></property>

		String sessionqry = query.getInsertEventSession();
		for(int i=1 ; i<=no_of_sessions ; i++)
		{
		statement = connection.prepareStatement(sessionqry);
		statement.setInt(1, ++maxsessionid);
		statement.setInt(2, iEvent.getEventCoordinatorId());
		statement.setInt(3, maxeventid);;
		statement.setInt(4, iEvent.getSeatsavailable());	
		sstatus= statement.executeUpdate();
		}
	
		if(estatus>0 && sstatus>0)
		{
			return estatus;
		}
		else
		{
			return -1;
		}
		//return status;
	}	
	

	/**
	 * This method deletes the event on basis of eventid and eventsessionid from database
	 * 
	 * PSEUDOCODE: <br/>
	 * Create a new connection to the database. <br/>
	 * Prepare a statement object using the connection that get an event
	 * from the event table for provided event id. <br/>
	 * Execute the SQL statement and keep a reference to the result set. <br/>
	 * Delete the event object by calling getDeleteEventSession and getDeleteEvent method
	 * Event and EventSession object is deleted from database  <br/>
	 * Return the status of executed query. <br/>
	 * 
	 * @param eventId
	 * @param sessionId
	 * @return
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws FERSGenericException
	 */
	public int deleteEvent(int eventId, int sessionId)
			throws ClassNotFoundException, SQLException, FERSGenericException {

		// TODO: Add code here.....
		// TODO: Pseudo-code are in the block comments above this method.
		// TODO: For more comprehensive pseudo-code with details,
		// refer to the Component/Class Detail Design Document
		
		//"DELETE FROM EVENTSESSION WHERE EVENTSESSIONID=?"
		///"DELETE FROM EVENT WHERE EVENTID=?"
		//DELETE FROM EVENTSESSIONSIGNUP WHERE EVENTSESSIONID = ? AND EVENTID = ?"
		
		
		
		connection = FERSDataConnection.createConnection();
		String dsignup = query.getDeleteEventSessionSignup();
		String dSession = query.getDeleteEventSession();
		String dEvent = query.getDeleteEvent();
		
		
		statement = connection.prepareStatement(dsignup);
		statement.setInt(1, sessionId);
		statement.setInt(2, eventId);
		statement.executeUpdate();
				
		statement = connection.prepareStatement(dSession);
		statement.setInt(1, sessionId);
		int status = statement.executeUpdate();
		
		
		
		
		
		return status;	
	}

	/**
	 * This method fetches the list of event coordinator from database
	 * 
	 * PSEUDOCODE: <br/>
	 * Create a new connection to the database. <br/>
	 * Prepare a statement object using the connection that gets all the eventcoordinator username
	 * from the eventcoordinator table in descending order. <br/>
	 * Execute the SQL statement and keep a reference to the result set. <br/>
	 * Using a WHILE LOOP, store each record in the result set returned in an
	 * EventCoordinator object by setting the values of the Event attributes as the
	 * corresponding values in the Result set. <br/>
	 * Return the List to the calling method. <br/>
	 * 
	 * @return
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public List<EventCoordinator> getEventCoordinator()
			throws ClassNotFoundException, SQLException {
		
		List<EventCoordinator> eventCoordinatorList = new ArrayList<EventCoordinator>();
		connection  =  FERSDataConnection.createConnection();
		statement = connection.prepareStatement(query.getSelectEventCoordinator());
		resultSet=statement.executeQuery();
		while(resultSet.next())
		{
			EventCoordinator ec = new EventCoordinator();
			ec.setEventcoordinatorid(Integer.parseInt(resultSet.getString(1)));
			ec.setUserName(resultSet.getString(2));
			eventCoordinatorList.add(ec);
		}
		eventCoordinatorList.sort(new Comparator<EventCoordinator>(){

			@Override
			public int compare(EventCoordinator ec, EventCoordinator ec1) 
			{
				return Integer.compare(ec1.getEventcoordinatorid(),ec.getEventcoordinatorid());
			}
			
		});
		// TODO: Add code here.....
		// TODO: Pseudo-code are in the block comments above this method.
		// TODO: For more comprehensive pseudo-code with details,
		// refer to the Component/Class Detail Design Document		
		
		return eventCoordinatorList;
	}
}
