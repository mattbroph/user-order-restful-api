package edu.matc.restfulapi;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.matc.entity.User;
import edu.matc.persistence.GenericDao;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;


@Path("/users")

public class UserService {

    @GET
    @Produces("application/json")
    public Response getUsers() throws JsonProcessingException {


        // Call http://localhost:8080/userDisplay/services/users
        GenericDao userDao = new GenericDao(User.class);
        List<User> users = userDao.getAll();
        ObjectMapper objectMapper = new ObjectMapper();
        /*
         * Referenced site below on how to convert List to JSON using Jackson
         * https://www.baeldung.com/java-converting-list-to-json-array
         * Reference site below to handle infinite loop by using
         * https://stackoverflow.com/questions/3325387/infinite-recursion-with-jackson-json-and-hibernate-jpa-issue
         */
        String usersJson = objectMapper.writeValueAsString(users);
        return Response.status(200).entity(usersJson).build();
    }

    // Call http://localhost:8080/userDisplay/services/users/{idToGet}
    @GET
    @Path("{id}")
    @Produces("application/json")
    public Response getUserFromId(@PathParam("id") int id) throws JsonProcessingException {

        GenericDao userDao = new GenericDao(User.class);
        User user = (User)userDao.getById(id);
        ObjectMapper objectMapper = new ObjectMapper();
        String userJson = objectMapper.writeValueAsString(user);

        return Response.status(200).entity(userJson).build();

    }

    // Call http://localhost:8080/userDisplay/services/users/{idToDelete}
    @DELETE
    @Path("{id}")
    @Produces("application/json")
    public Response deleteUserWithId(@PathParam("id") int id) throws JsonProcessingException {

        GenericDao userDao = new GenericDao(User.class);
        User userToDelete = (User)userDao.getById(id);
        userDao.delete(userToDelete);

        String successResponse = "{0}";
        return Response.status(200).entity(successResponse).build();
    }
    // Call http://localhost:8080/userDisplay/services/users
    @POST
    @Consumes("application/json")
    @Produces("application/json")
    public Response createUser(String userJson) throws JsonProcessingException {

        GenericDao userDao = new GenericDao(User.class);

        ObjectMapper mapper = new ObjectMapper();

        /*
        * Reference section 3.5 Creating Java Map From JSON String
        * https://www.baeldung.com/jackson-object-mapper-tutorial
         */
        Map<String, String> map = mapper.readValue(userJson, Map.class);

        String firstName = map.get("firstName");
        String lastName = map.get("lastName");
        String userName = map.get("userName");
        LocalDate dateOfBirth = LocalDate.parse(map.get("dateOfBirth"));

        User newUser = new User(firstName, lastName, userName, dateOfBirth);

        int insertedId = userDao.insert(newUser);

        return Response.status(201).entity(String.valueOf(insertedId)).build();

    }

    // Call http://localhost:8080/userDisplay/services/users/{idToUpdate}
    @PUT
    @Path("{id}")
    @Consumes("application/json")
    @Produces("application/json")
    public Response createUser(String userJson, @PathParam("id") int id) throws JsonProcessingException {

        GenericDao userDao = new GenericDao(User.class);
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

        // Update the user in the DB
        userDao.update(userToUpdate);

        String successResponse = "{0}";

        return Response.status(200).entity(successResponse).build();

    }



}
