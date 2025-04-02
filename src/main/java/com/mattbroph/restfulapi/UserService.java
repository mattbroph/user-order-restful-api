package com.mattbroph.restfulapi;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mattbroph.entity.User;
import com.mattbroph.persistence.GenericDao;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;


@Path("/users")

public class UserService {

    // Class instance variables
    private final GenericDao userDao = new GenericDao(User.class);
    private final Logger logger = LogManager.getLogger(this.getClass());


    // Call http://localhost:8080/userDisplay/services/users
    @GET
    @Produces("application/json")
    public Response getUsers() throws JsonProcessingException {

        List<User> users = userDao.getAll();
        ObjectMapper objectMapper = new ObjectMapper();

        String usersJson = objectMapper.writeValueAsString(users);
        return Response.status(200).entity(usersJson).build();
    }

    // Call http://localhost:8080/userDisplay/services/users/{idToGet}
    @GET
    @Path("{id}")
    @Produces("application/json")
    public Response getUserFromId(@PathParam("id") int id) throws JsonProcessingException {

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
    public Response deleteUserWithId(@PathParam("id") int id) throws JsonProcessingException {

        User userToDelete = (User)userDao.getById(id);

        // If user doesn't exist send them a 404
        if (userToDelete == null) {
            return Response.status(404).entity("User not found").build();
        }

        // Delete the user
        userDao.delete(userToDelete);

        String successResponse = "{0}";
        return Response.status(200).entity(successResponse).build();
    }

    // Call http://localhost:8080/userDisplay/services/users
    @POST
    @Consumes("application/json")
    @Produces("application/json")
    public Response createUser(String userJson) throws JsonProcessingException {


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
            // Return successful response
            return Response.status(201).entity(String.valueOf("User created with id: " + insertedId)).build();

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
    public Response updateUser(String userJson, @PathParam("id") int id) throws JsonProcessingException {

        User userToUpdate = (User)userDao.getById(id);
        ObjectMapper mapper = new ObjectMapper();

        // Get the values from the json in the request body
        Map<String, String> map = mapper.readValue(userJson, Map.class);

        // Set the new values for the user
        String firstName = map.get("firstName");
        userToUpdate.setFirstName(firstName);

        String lastName = map.get("lastName");
        userToUpdate.setLastName(lastName);

        String userName = map.get("userName");
        userToUpdate.setUserName(userName);

        LocalDate dateOfBirth = LocalDate.parse(map.get("dateOfBirth"));
        userToUpdate.setDateOfBirth(dateOfBirth);

        // Update the user in the database
        userDao.update(userToUpdate);

        String successResponse = "{0}";
        return Response.status(200).entity(successResponse).build();
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
