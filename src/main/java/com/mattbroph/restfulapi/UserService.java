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


@Path("/users")

public class UserService implements PropertiesLoader {

    // Class instance variables
    private final GenericDao userDao = new GenericDao(User.class);
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
     * Gets a list of all users in the database.
     *
     * @param lastName optional parameter to search by user last name
     * @param headers the request headers
     * @return the response
     * @throws JsonProcessingException if error occurs while processing json
     */
    @GET
    @Produces("application/json")
    public Response getUsers(
            @QueryParam("lastName") String lastName,
            @Context HttpHeaders headers)
            throws JsonProcessingException {

        // Endpoint http://localhost:8080/userdisplayexercise_war/services/users

        List<User> users;
        ObjectMapper objectMapper = new ObjectMapper();

        // Validate the API key
        Response validationResponse = validateApiKey(headers);
        if (validationResponse != null) {
            return validationResponse;
        }

        /* If last name param was provided, use it for the search.
        * If last name param not provided, get all users.
        */
        if (lastName != null && !lastName.isEmpty()) {
            users = userDao.getByPropertyEqual("lastName", lastName);

        } else {
            users = userDao.getAll();
        }

        // If no users exist in the retrieval, respond with a 404
        if (users.isEmpty()) {
            return Response.status(404).entity("Users not found").build();
        }

        String usersJson = objectMapper.writeValueAsString(users);
        return Response.status(200).entity(usersJson).build();
    }


    /**
     * Gets a user based on user id
     * @param id the users unique id
     * @param headers the request headers
     * @return the response
     * @throws JsonProcessingException if error occurs while processing json
     */
    @GET
    @Path("{id}")
    @Produces("application/json")
    public Response getUserFromId(
            @PathParam("id") int id,
            @Context HttpHeaders headers) throws JsonProcessingException {

        // Endpoint http://localhost:8080/userdisplayexercise_war/services/users/{idToGet}

        // Validate the API key
        Response validationResponse = validateApiKey(headers);
        if (validationResponse != null) {
            return validationResponse;
        }

        ObjectMapper objectMapper = new ObjectMapper();
        User user = (User)userDao.getById(id);

        // If user doesn't exist send them a 404
        if (user == null) {
            return Response.status(404).entity("User not found").build();
        }

        String userJson = objectMapper.writeValueAsString(user);
        return Response.status(200).entity(userJson).build();
    }

    /**
     * Deletes a user based on user id
     * @param id the user's unique id
     * @param headers the request headers
     * @return the response
     */
    @DELETE
    @Path("{id}")
    @Produces("application/json")
    public Response deleteUserWithId(
            @PathParam("id") int id,
            @Context HttpHeaders headers) {

        // Endpoint http://localhost:8080/userdisplayexercise_war/services/users/{idToDelete}

        // Validate the API key
        Response validationResponse = validateApiKey(headers);
        if (validationResponse != null) {
            return validationResponse;
        }

        User userToDelete = (User)userDao.getById(id);

        // If user doesn't exist send a 404 in the response
        if (userToDelete == null) {
            return Response.status(404).entity("User not found").build();
        }

        // Delete the user
        userDao.delete(userToDelete);

        String successResponse = "User " + userToDelete.getId() + " has been deleted";
        return Response.status(200).entity(successResponse).build();
    }


    /**
     * Adds a new user to the database
     * @param userJson the data used to construct a new user
     * @param headers the request headers
     * @return the response
     */
    @POST
    @Consumes("application/json")
    @Produces("application/json")
    public Response createUser(
            String userJson,
            @Context HttpHeaders headers) {

        // Endpoint http://localhost:8080/userdisplayexercise_war/services/users

        // Validate the API key
        Response validationResponse = validateApiKey(headers);
        if (validationResponse != null) {
            return validationResponse;
        }

        try {

            ObjectMapper mapper = new ObjectMapper();
            Map<String, String> map = mapper.readValue(userJson, Map.class);

            // Check that all user fields were submitted
            if (!map.containsKey("firstName") || !map.containsKey("lastName")
                    || !map.containsKey("userName") || !map.containsKey("dateOfBirth")) {

                return Response.status(400).entity("Missing required fields: firstName," +
                 " lastName, userName, dateOfBirth").build();
            }

            String firstName = map.get("firstName");
            String lastName = map.get("lastName");
            String userName = map.get("userName");
            String dateOfBirthString = map.get("dateOfBirth");
            LocalDate dateOfBirth;

            // Check the date of birth format
            try {
                dateOfBirth = LocalDate.parse(dateOfBirthString);
            } catch (Exception e) {
                return Response.status(400).entity("Invalid date format. Expected format: YYYY-MM-DD").build();
            }

            // Create the new user and insert them into the database
            User newUser = new User(firstName, lastName, userName, dateOfBirth);
            int insertedId = userDao.insert(newUser);

            // Send user back with successful response code
            String newUserJson = mapper.writeValueAsString(newUser);

            return Response.status(201).entity(("User " + insertedId + " created: " + newUserJson)).build();

        } catch (JsonProcessingException e) {

            return Response.status(400).entity("Invalid JSON format. Please review documentation.").build();

        } catch (Exception e) {

            return Response.status(500).entity("Internal server error: " + e.getMessage()).build();
        }

    }


    /**
     * Updates a user in the database
     * @param userJson the data used to update the user
     * @param id the unique user id
     * @param headers the request headers
     * @return the response
     */
    // Call http://localhost:8080/userdisplayexercise_war/services/users/{idToUpdate}
    @PUT
    @Path("{id}")
    @Consumes("application/json")
    @Produces("application/json")
    public Response updateUser(
            String userJson,
            @PathParam("id") int id,
            @Context HttpHeaders headers) {

        // Validate the API key
        Response validationResponse = validateApiKey(headers);
        if (validationResponse != null) {
            return validationResponse;
        }

        try {
            // Get the user to update
            User userToUpdate = (User)userDao.getById(id);

            // If user doesn't exist send back a 404
            if (userToUpdate == null) {
                return Response.status(404).entity("User not found").build();
            }

            // Get the values from the json in the request body
            ObjectMapper mapper = new ObjectMapper();
            Map<String, String> map = mapper.readValue(userJson, Map.class);

            // If value exists, update the user's instance variable
            if (map.containsKey("firstName")) {
                userToUpdate.setFirstName(map.get("firstName"));
            }

            if (map.containsKey("lastName")) {
                userToUpdate.setLastName(map.get("lastName"));
            }

            if (map.containsKey("userName")) {
                userToUpdate.setUserName(map.get("userName"));
            }
            if (map.containsKey("dateOfBirth")) {
                userToUpdate.setDateOfBirth(LocalDate.parse(map.get("dateOfBirth")));
            }

            // Update the user in the database
            userDao.update(userToUpdate);

            // Send user back with successful response code
            String userToUpdateJson = mapper.writeValueAsString(userToUpdate);
            return Response.status(200).entity("User " + userToUpdate.getId() + " has been updated: " + userToUpdateJson).build();

        } catch (JsonProcessingException e) {

            return Response.status(400).entity("Invalid JSON format. Please review documentation.").build();

        } catch (Exception e) {

            return Response.status(500).entity("Internal server error: " + e.getMessage()).build();
        }

    }


    /**
     * Adds a new order to a user
     * @param orderJson the data used to construct a new user
     * @param headers the request headers
     * @return the response
     */
    @POST
    @Consumes("application/json")
    @Produces("application/json")
    @Path("{id}/orders")
    public Response createOrder(
            String orderJson,
            @PathParam("id") int id,
            @Context HttpHeaders headers) {

        // Endpoint http://localhost:8080/userdisplayexercise_war/services/users/24/orders

        // Validate the API key
        Response validationResponse = validateApiKey(headers);
        if (validationResponse != null) {
            return validationResponse;
        }

        try {

            ObjectMapper mapper = new ObjectMapper();
            Map<String, String> map = mapper.readValue(orderJson, Map.class);

            // Check that all user fields were submitted
            if (!map.containsKey("description")) {

                return Response.status(400).entity("Missing required field: description").build();
            }

            String description = map.get("description");

            // Create the new order and insert them into the database
            User user = (User)userDao.getById(id);
            Order order = new Order(description, user);
            int insertedId = orderDao.insert(order);

            // Send order back with successful response code
            Order newOrder = (Order)orderDao.getById(insertedId);
            String newOrderJson = mapper.writeValueAsString(newOrder);

            return Response.status(201).entity(("Order " + insertedId + " created: " + newOrderJson)).build();

        } catch (JsonProcessingException e) {

            return Response.status(400).entity("Invalid JSON format. Please review documentation.").build();

        } catch (Exception e) {

            return Response.status(500).entity("Internal server error: " + e.getMessage()).build();
        }

    }

}
