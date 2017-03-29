package pl.levil.resources;


import pl.levil.Main;
import pl.levil.model.Reservation;
import pl.levil.model.ReservationStatus;
import pl.levil.model.Room;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.Date;

/**
 * Created by mozam on 2017-03-29.
 */
public class StaticResources {
    public static void populateDB(){
        addTestRooms();
        addTestReservations();
    }

    private static void addTestReservations() {
        addItemToDatabase(new Reservation(new Date(),15, ReservationStatus.pending,getRoomFromDatabase("P4.1"),"test","test"));
    }

    private static void addTestRooms() {
        addItemToDatabase(new Room("P4.1"));
        addItemToDatabase(new Room("P3.1"));
        addItemToDatabase(new Room("P2.1"));
        addItemToDatabase(new Room("P1.2"));
        addItemToDatabase(new Room("P1.1"));
        addItemToDatabase(new Room("P5.2"));
    }

    private static void addItemToDatabase(Object obj){
        EntityManager entityManager = Main.getEntityManagerFactory().createEntityManager();
        entityManager.getTransaction().begin();
        try {
            entityManager.persist(obj);
            entityManager.getTransaction().commit();
        }catch (Exception ex){
            System.out.println(ex.getMessage());
            entityManager.getTransaction().rollback();
        }finally {
            entityManager.close();
        }
    }

    private static Room getRoomFromDatabase(String name){
        EntityManager entityManager = Main.getEntityManagerFactory().createEntityManager();
        entityManager.getTransaction().begin();
        Room room = null;
        try {
            CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            CriteriaQuery<Room> roomCriteriaQuery = criteriaBuilder.createQuery(Room.class);
            Root<Room> roomRoot = roomCriteriaQuery.from(Room.class);
            roomCriteriaQuery.select(roomRoot)
                    .where(criteriaBuilder.equal(roomRoot.get("name"), name));
            room = entityManager.createQuery(roomCriteriaQuery).getSingleResult();
        }catch (Exception ex){
            System.out.println(ex.getMessage());
            entityManager.getTransaction().rollback();
        }finally {
            entityManager.close();
        }

        return room;
    }

}
