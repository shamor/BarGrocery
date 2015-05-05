package Servlets;

import Controllers.*;
import JSON.JSON;
import modelclasses.Item;
import modelclasses.PriceAssociation;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.*;

public class MyShopListServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pathInfo = req.getPathInfo();
        
        if (pathInfo == null || pathInfo.equals("") || pathInfo.equals("/")) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.setContentType("text/plain");
            resp.getWriter().println("Getting entire ItemList not supported yet");
            return;
        }

        // Get the user name
        if (pathInfo.startsWith("/")){
            pathInfo = pathInfo.substring(1);
        }

        // Use a GetUsercontroller to find the user in the database
        getItemController controller = new getItemController();
        Item item = controller.getItem(pathInfo,pathInfo);


        if (item == null) {
            // No such item, so return a NOT FOUND response
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            resp.setContentType("text/plain");
            resp.getWriter().println("No such item: " + pathInfo);
            return;
        }
        // Set status code and content type
        resp.setStatus(HttpServletResponse.SC_OK);
        resp.setContentType("application/json");

        // Return the item in JSON format
        JSON.getObjectMapper().writeValue(resp.getWriter(), item);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    	
    	
    	try {
	    	Item[] itemsList = JSON.getObjectMapper().readValue(req.getReader(), Item[].class);
	    	
	        //given a list return another list of the cheapest prices
	        getLowPricesController lpc = new getLowPricesController();
	        List<PriceAssociation> priceList = lpc.getlowPrice(itemsList);
	
	        // Set status code and content type
	        resp.setStatus(HttpServletResponse.SC_OK);
	        resp.setContentType("application/json");
	        PriceAssociation[] pL = priceList.toArray(new PriceAssociation[priceList.size()]);

	        // Send back a list of prices and locations
	        JSON.getObjectMapper().writeValue(resp.getWriter(), pL);
    	} catch (Throwable e) {
    		System.err.println("Error occurred");
    		e.printStackTrace();
    	}
    }

}
