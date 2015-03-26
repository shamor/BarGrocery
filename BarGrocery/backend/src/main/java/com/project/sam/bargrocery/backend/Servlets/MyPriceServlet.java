package com.project.sam.bargrocery.backend.Servlets;

import com.project.sam.bargrocery.backend.Controllers.AddItemController;
import com.project.sam.bargrocery.backend.Controllers.getItemController;
import com.project.sam.bargrocery.backend.Controllers.getPriceAssociationController;
import com.project.sam.bargrocery.backend.JSON;
import com.project.sam.modelclases.Items;
import com.project.sam.modelclases.PriceAssociation;

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

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pathInfo = req.getPathInfo();
        if (pathInfo == null || pathInfo.equals("") || pathInfo.equals("/")) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.setContentType("text/plain");
            resp.getWriter().println("Getting entire UserList not supported yet");
            return;
        }

        // Get the user name
        if (pathInfo.startsWith("/")){
            pathInfo = pathInfo.substring(1);
        }

        // Use a GetUsercontroller to find the user in the database
        getItemController controller = new getItemController();
        Items item = controller.getItem(pathInfo,pathInfo);

        getPriceAssociationController con = new getPriceAssociationController();
        PriceAssociation pa = con.getPriceInfo(item.getId());


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
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Items item = JSON.getObjectMapper().readValue(req.getReader(), Items.class);
        // Use a GetUser controller to find the item in the database
        AddItemController controller = new AddItemController();
        controller.addItem(item);
        // Set status code and content type
        resp.setStatus(HttpServletResponse.SC_OK);
        resp.setContentType("application/json");

        // writing the operation out.
        JSON.getObjectMapper().writeValue(resp.getWriter(), item);
    }
}
