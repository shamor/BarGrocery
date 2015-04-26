package Servlets;

import Controllers.AddItemController;
import Controllers.addPriceController;
import Controllers.getItemController;
import Controllers.getPriceAssociationController;
import JSON.JSON;
import modelclasses.PriceAssociation;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by sam on 3/22/2015.
 */
public class MyPriceServlet extends HttpServlet{
    private static final long serialVersionUID = 1L;

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    	PriceAssociation price = JSON.getObjectMapper().readValue(req.getReader(), PriceAssociation.class);
        // Use a GetUser controller to find the item in the database
        addPriceController controller = new addPriceController();
        controller.addItem(price);
        // Set status code and content type
        resp.setStatus(HttpServletResponse.SC_OK);
        resp.setContentType("application/json");

        // writing the operation out.
        JSON.getObjectMapper().writeValue(resp.getWriter(), 1);
    }
}
