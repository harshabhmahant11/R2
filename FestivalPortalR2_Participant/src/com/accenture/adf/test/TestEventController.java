package com.accenture.adf.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.web.servlet.ModelAndView;

import com.accenture.adf.businesstier.controller.EventController;
import com.accenture.adf.businesstier.dao.VisitorDAO;

/**
 * Junit test class for EventController
 * 
 */
public class TestEventController {

	private MockHttpServletRequest request;
	private MockHttpServletResponse response;
	private ModelAndView modelAndView;
	
	private EventController controller;


	/**
	 * Sets up initial objects required in other methods
	 * 		
	 * @throws Exception
	 */
	@Before
	public void setUp() throws Exception {
		modelAndView = new ModelAndView();
		controller = new EventController();
		response = new MockHttpServletResponse();	

		
		
	}

	/**
	 * Deallocate the objects after execution of every method
	 * 
	 * @throws Exception
	 */
	@After
	public void tearDown() throws Exception {
		/**
		 * @TODO: Release all the objects here by assigning them null  
		 */
		modelAndView =null;
		controller = null;
		response = null;
	
	
	
	}

	/**
	 * Test case to test the positive scenario for getAvailableEvents method
	 */
	@Test
	public void testGetAvailableEvents_Positive() {

		try {
			request = new MockHttpServletRequest("GET", "/catalog.htm");
			modelAndView = controller.getAvailableEvents(request, response);
		} catch (Exception exception) {
			fail("Exception");
		}
		assertEquals("/eventCatalog.jsp", modelAndView.getViewName());
	}

	/**
	 * Executes the negative scenario for getAvailableEvents method
	 */
	@Test
	public void testGetAvailableEvents_Negative() {
		/**
		 * @TODO: Call getAvailableEvents methods  by passing request as null
		 * and assert it for appropriate model view name
		 */	
		try {
			request = new MockHttpServletRequest("GET", "/catalog.htm");
			modelAndView = controller.getAvailableEvents(null, response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
		//	fail("Exception");
			//System.out.println(e.getMessage());
		}
		assertEquals(null, modelAndView.getViewName());
	}
	
	/**
	 * Test case to test the positive scenario for displayEvent method
	 */
	@Test
	public void testDisplayEvent_Positive() {
		/**
		 * @TODO: Call displayEvent methods and assert
		 * it for appropriate model view name
		 */	
		try {
			request = new MockHttpServletRequest("GET", "/displayEvent.htm");
			request.setParameter("eventId","1001");
			request.setParameter("sessionId","10001");
			modelAndView=controller.displayEvent(request,response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			//fail("Exception");
			
		}
		assertEquals("/addEvent.jsp",modelAndView.getViewName());
	}

	/**
	 * Executes the negative scenario for displayEvent method
	 */
	@Test
	public void testDisplayEvent_Negative() {
		/**
		 * @TODO: Call displayEvent methods  by passing request as null
		 * and assert it for appropriate model view name
		 */	
		try {
			request = new MockHttpServletRequest("GET", "/displayEvent.htm");
			modelAndView = controller.getAvailableEvents(null, response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			//System.out.println(e.getMessage());
		}
		assertEquals(null, modelAndView.getViewName());
	}	
	
	/**
	 * Test case to test the positive scenario for updateEvent method
	 */
	@Test
	public void testUpdateEvent_Positive() {
		/**
		 * @TODO: Call updateEvent methods and assert
		 * it for appropriate model view name
		 */	
		try {
			request = new MockHttpServletRequest("GET", "/updateEvent.htm");
			request.setParameter("eventId","1009");
			request.setParameter("sessionId","10008");
			request.setParameter("eventName","myevent235");
			request.setParameter("desc","My Event 525Desc");
			request.setParameter("place","Bangalore");
			request.setParameter("duration","3000-5000");
			request.setParameter("eventType","Casual");
			request.setParameter("ticket", "255");
			request.setParameter("isAdd", "true");
			request.setParameter("coordinator", "105");
			request.setParameter("eventSession", "2");
			
			modelAndView=controller.updateEvent(request, response);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail("Exception");
		}
		assertEquals("/addEvent.jsp",modelAndView.getViewName());
	}

	/**
	 * Executes the negative scenario for updateEvent method
	 */
	@Test
	public void testUpdateEvent_Negative() {
		/**
		 * @TODO: Call updateEvent methods  by passing request as null
		 * and assert it for appropriate model view name
		 */	
		try {
			request = new MockHttpServletRequest("GET", "/updateEvent.htm");
			modelAndView = controller.getAvailableEvents(null, response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			//fail("Exception");
			//e.printStackTrace();
		}
		assertEquals(null, modelAndView.getViewName());
	}
	
	/**
	 * Test case to test the positive scenario for displayEvent method
	 */
	@Test
	public void testDeleteEvent_Positive() {
		/**
		 * @TODO: Call deleteEvent methods and assert
		 * it for appropriate model view name
		 */
		try {
			request = new MockHttpServletRequest("GET", "/deleteEvent.htm");
			request.setParameter("eventId","1001");
			request.setParameter("sessionId","10001");
			modelAndView = controller.deleteEvent(request,response);
		} catch (Exception exception) {
			fail("Exception");
		}
		assertEquals("/eventCatalog.jsp", modelAndView.getViewName());
	}

		
	

	/**
	 * Executes the negative scenario for displayEvent method
	 */
	@Test
	public void testDeleteEvent_Negative() {
		/**
		 * @TODO: Call deleteEvent methods  by passing request as null
		 * and assert it for appropriate model view name
		 */	
		try {
			request = new MockHttpServletRequest("GET", "/deleteEvent.htm");
			modelAndView = controller.deleteEvent(null, response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			//fail("Exception");
		}
		assertEquals(null, modelAndView.getViewName());
	}
	}		


