package com.mattbroph.restfulapi;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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


    // Call http://localhost:8080/userDisplay/services/users
    @GET
    @Produces("application/json")
    public Response getUsers(
            @QueryParam("lastName") String lastName,
            @Context HttpHeaders headers)
            throws JsonProcessingException {

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

    // Call http://localhost:8080/userDisplay/services/users/{idToGet}
    @GET
    @Path("{id}")
    @Produces("application/json")
    public Response getUserFromId(
            @PathParam("id") int id,
            @Context HttpHeaders headers) throws JsonProcessingException {

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

    // Call http://localhost:8080/userDisplay/services/users/{idToDelete}
    @DELETE
    @Path("{id}")
    @Produces("application/json")
    public Response deleteUserWithId(
            @PathParam("id") int id,
            @Context HttpHeaders headers) {

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

    // Call http://localhost:8080/userDisplay/services/users
    @POST
    @Consumes("application/json")
    @Produces("application/json")
    public Response createUser(
            String userJson,
            @Context HttpHeaders headers) {

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

    // Call http://localhost:8080/userDisplay/services/users/{idToUpdate}
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



    /*
     * Referenced site below on how to convert List to JSON using Jackson
     * https://www.baeldung.com/java-converting-list-to-json-array
     * Reference site below to handle infinite loop by using
     * https://stackoverflow.com/questions/3325387/infinite-recursion-with-jackson-json-and-hibernate-jpa-issue
     */
    /*
     * Reference section 3.5 Creating Java Map From JSON String
     * https://www.baeldung.com/jackson-object-mapper-tutorial
     */


}
