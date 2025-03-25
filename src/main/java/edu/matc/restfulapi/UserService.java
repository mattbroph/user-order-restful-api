package edu.matc.restfulapi;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.matc.entity.User;
import edu.matc.persistence.GenericDao;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.List;


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
}
