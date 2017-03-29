package pl.levil.model;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by mozam on 2017-03-29.
 */
@Entity
@Table(name = "Reservation")
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private Date date;
    @Column
    private int duration;
    @Column
    private ReservationStatus status;
    @ManyToOne(fetch = FetchType.EAGER)
    private Room room;
    @Column
    private String name;
    @Column
    private String desctiption;

    public Reservation(Date date, int duration, ReservationStatus status, Room room, String name, String desctiption) {
        this.date = date;
        this.duration = duration;
        this.status = status;
        this.room = room;
        this.name = name;
        this.desctiption = desctiption;
    }

    public Reservation() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public ReservationStatus getStatus() {
        return status;
    }

    public void setStatus(ReservationStatus status) {
        this.status = status;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesctiption() {
        return desctiption;
    }

    public void setDesctiption(String desctiption) {
        this.desctiption = desctiption;
    }
}
