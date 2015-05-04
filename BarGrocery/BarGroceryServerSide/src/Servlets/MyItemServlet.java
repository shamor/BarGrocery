package Servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import modelclasses.Item;
import Controllers.AddItemController;
import Controllers.getItemController;
import JSON.JSON;

public class MyItemServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	        Item item = JSON.getObjectMapper().readValue(req.getReader(), Item.class);
	        
	        // Use a GetItem controller to find the item in the database
	        AddItemController controller = new AddItemController();
	        controller.addItem(item);
	    
	        // Set status code and content type
	        resp.setStatus(HttpServletResponse.SC_OK);
	        resp.setContentType("application/json");
	        
	        getItemController gi = new getItemController();
	        Item temp = gi.getItem(item.getBrand(), item.getProduct());
	        
	        // writing the operation out.
	        JSON.getObjectMapper().writeValue(resp.getWriter(), temp);
	    }

}
