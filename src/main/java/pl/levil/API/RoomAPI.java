package pl.levil.API;

import pl.levil.Main;
import pl.levil.model.Room;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * Created by mozam on 2017-03-29.
 */
@Path("rooms")
public class RoomAPI {

    @Path("all")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Room> getAllRooms(){

        List<Room> rooms = null;

        EntityManager entityManager = Main.getEntityManagerFactory().createEntityManager();
        entityManager.getTransaction().begin();

        try{
            CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            CriteriaQuery<Room> roomCriteriaQuery = criteriaBuilder.createQuery(Room.class);
            Root<Room> roomsRoot = roomCriteriaQuery.from(Room.class);

            roomCriteriaQuery.select(roomsRoot);

            rooms = entityManager.createQuery(roomCriteriaQuery).getResultList();
        }
        catch (Exception ex){
            entityManager.getTransaction().rollback();
        }
        finally {
            entityManager.close();
        }

        return rooms;
    }

    @Path("room/add")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addReservation(Room send){

        EntityManager entityManager = Main.getEntityManagerFactory().createEntityManager();
        entityManager.getTransaction().begin();

        try{
            entityManager.persist(send);
            entityManager.getTransaction().commit();
        }
        catch (Exception ex){
            entityManager.getTransaction().rollback();
            System.out.println(ex.getMessage());
            return Response.serverError().build();
        }
        finally {
            entityManager.close();
        }

        return Response.accepted().build();
    }
    @Path("room/remove/{id}")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addReservation(@PathParam("id") int roomID){

        EntityManager entityManager = Main.getEntityManagerFactory().createEntityManager();
        entityManager.getTransaction().begin();

        try{
            Room toRemove = entityManager.find(Room.class,roomID);
            entityManager.remove(toRemove);
            entityManager.getTransaction().commit();
        }
        catch (Exception ex){
            System.out.println(ex.getMessage());
            entityManager.getTransaction().rollback();
            return Response.serverError().build();
        }
        finally {
            entityManager.close();
        }

        return Response.accepted().build();
    }
}
