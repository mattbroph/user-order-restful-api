package com.mattbroph.restfulapi;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mattbroph.entity.Order;
import com.mattbroph.entity.User;
import com.mattbroph.persistence.GenericDao;
import com.mattbroph.persistence.PropertiesLoader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Properties;


@Path("/orders")

public class OrderService implements PropertiesLoader {

    // Class instance variables
    private final GenericDao orderDao = new GenericDao(Order.class);
    private final Logger logger = LogManager.getLogger(this.getClass());
    private final Properties apiKeyProperties = loadProperties("/apiKeys.properties");;


    /**
     * Validates the API key in the header matches the api key in the properties file.
     *
     * @param headers the request headers
     * @return the response based on validation steps
     */
    private Response validateApiKey(HttpHeaders headers) {

        // Validate the API key
        String storedApiKey = apiKeyProperties.getProperty("api.key");
        String requestApiKey = headers.getHeaderString("X-API-KEY");
        // If the API key is missing, send a 401
        if (requestApiKey == null || requestApiKey.isEmpty()) {
            return Response.status(401).entity("Missing X-API-KEY").build();
        }
        // If the API key exists, but is not valid, send a 401
        if (!storedApiKey.equals(requestApiKey)) {
            return Response.status(401).entity("Invalid X-API-KEY").build();
        }
        // If API key matches what's in the properties file, return null and continue processing
        return null;

    }

    /**
     * Deletes an order based on order id
     * @param id the order's unique id
     * @param headers the request headers
     * @return the response
     */
    @DELETE
    @Path("{id}")
    @Produces("application/json")
    public Response deleteOrderWithId(
            @PathParam("id") int id,
            @Context HttpHeaders headers) {

        // Endpoint http://localhost:8080/userDisplay/services/orders/{orderId}

        // Validate the API key
        Response validationResponse = validateApiKey(headers);
        if (validationResponse != null) {
            return validationResponse;
        }

        return null;

    }


    /**
     * Updates an order in the database
     * @param orderJson the data used to update the order
     * @param id the unique order id
     * @param headers the request headers
     * @return the response
     */
    @PUT
    @Path("{id}")
    @Consumes("application/json")
    @Produces("application/json")
    public Response updateUser(
            String orderJson,
            @PathParam("id") int id,
            @Context HttpHeaders headers) {

        // Endpoint http://localhost:8080/userDisplay/services/orders/{orderId}

        // Validate the API key
        Response validationResponse = validateApiKey(headers);
        if (validationResponse != null) {
            return validationResponse;
        }

        return null;

    }


}
