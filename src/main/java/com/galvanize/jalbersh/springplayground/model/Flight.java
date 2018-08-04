package com.galvanize.jalbersh.springplayground.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;
import java.util.List;
import java.util.Objects;

public class Flight {
    private @JsonProperty("departs") Date departs;
    private @JsonProperty("tickets") List<Ticket> tickets;

    public Flight(Date departs, List<Ticket> tickets) {
        this.departs = departs;
        this.tickets = tickets;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    public Date getDeparts() {
        return departs;
    }

    public void setDeparts(Date departs) {
        this.departs = departs;
    }

    public List<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(List<Ticket> tickets) {
        this.tickets = tickets;
    }

    @Override
    public String toString() {
        return "Flight{" +
                "departs=" + departs +
                ", tickets=" + tickets +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Flight flight = (Flight) o;
        return Objects.equals(departs, flight.departs) &&
                Objects.equals(tickets, flight.tickets);
    }

    @Override
    public int hashCode() {
        return Objects.hash(departs, tickets);
    }
}
