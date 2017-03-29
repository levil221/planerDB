package pl.levil.API;


import pl.levil.Main;
import pl.levil.model.Reservation;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * Created by levil on 2017-03-28.
 */
@Path("reservations")
public class ReservationAPI {

    @Path("all")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Reservation> getAllReservations(){

        List<Reservation> reservations = null;

        EntityManager entityManager = Main.getEntityManagerFactory().createEntityManager();
        entityManager.getTransaction().begin();

        try{
            CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            CriteriaQuery<Reservation> reservationCriteriaQuery = criteriaBuilder.createQuery(Reservation.class);
            Root<Reservation> reservationRoot = reservationCriteriaQuery.from(Reservation.class);

            reservationCriteriaQuery.select(reservationRoot);

            reservations = entityManager.createQuery(reservationCriteriaQuery).getResultList();
        }
        catch (Exception ex){
            System.out.println(ex.getMessage());
            entityManager.getTransaction().rollback();
        }
        finally {
            entityManager.close();
        }

        return reservations;
    }

    @Path("reservation/{id}")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateReservation(@PathParam("id") int reservationID, Reservation send){

        EntityManager entityManager = Main.getEntityManagerFactory().createEntityManager();
        entityManager.getTransaction().begin();

        try{
            Reservation reservation = entityManager.find(Reservation.class,reservationID);

            reservation.setDate(send.getDate());
            reservation.setDesctiption(send.getDesctiption());
            reservation.setDuration(send.getDuration());
            reservation.setName(send.getName());
            reservation.setRoom(send.getRoom());
            reservation.setStatus(send.getStatus());

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

    @Path("reservation/add")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addReservation(Reservation send){

        EntityManager entityManager = Main.getEntityManagerFactory().createEntityManager();
        entityManager.getTransaction().begin();

        try{
            entityManager.persist(send);
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
    @Path("reservation/remove/{id}")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addReservation(@PathParam("id") int reservationID){

        EntityManager entityManager = Main.getEntityManagerFactory().createEntityManager();
        entityManager.getTransaction().begin();

        try{
            Reservation toRemove = entityManager.find(Reservation.class,reservationID);
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
