package com.galvanize.jalbersh.springplayground.service;

import com.galvanize.jalbersh.springplayground.model.Flight;
import com.galvanize.jalbersh.springplayground.model.Passenger;
import com.galvanize.jalbersh.springplayground.model.Ticket;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static java.util.Arrays.asList;

@Service
public class TicketService {
    public Flight getFlight() {
        Calendar cal = Calendar.getInstance();
        cal.set(2017,4,21,14,34,0);
        Date departs = cal.getTime();
        Passenger passenger = new Passenger("Dwayne","Johnson");
        List<Ticket> tickets = asList(new Ticket(passenger,200.0));
        Flight flight = new Flight(departs, tickets);
        return flight;
    }

    public List<Flight> getFlights() {
        Calendar cal1 = Calendar.getInstance();
        cal1.set(2017,4,21,14,34,0);
        Date departs1 = cal1.getTime();
        Calendar cal2 = Calendar.getInstance();
        cal2.set(2017,5,21,14,34,0);
        Date departs2 = cal2.getTime();
        Passenger passenger1 = new Passenger("Dwayne","Johnson");
        Passenger passenger2 = new Passenger("John","Cena");
        Passenger passenger3 = new Passenger("The Rock",null);
        List<Ticket> tickets = asList(new Ticket(passenger1,200.0),new Ticket(passenger2,180.0),new Ticket(passenger3,160.0));
        Flight flight1 = new Flight(departs1, tickets);
        Flight flight2 = new Flight(departs2, tickets);
        return asList(flight1,flight2);
    }

}
