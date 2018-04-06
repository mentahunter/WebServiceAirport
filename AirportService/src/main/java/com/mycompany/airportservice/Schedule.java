package com.mycompany.airportservice;
import javax.persistence.*;

@Entity
@Table(name = "schedules")
public class Schedule{
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;
    private String startPort;
    private String destination;
    private long airplaneId;

    public long getId(){
        return id;
    }

    public void setId(long id){
        this.id = id;
    }

    public String getStartPort(){
        return startPort;
    }

    public void setStartPort(String startPort){
        this.startPort = startPort;
    }

    public String getDestination(){
        return destination;
    }

    public void setDestination(String destination){
        this.destination = destination;
    }

    public long getAirplaneId(){
        return airplaneId;
    }

    public void setAirplaneId(long airplaneId){
        this.airplaneId = airplaneId;
    }
}
