package com.galvanize.jalbersh.springplayground.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

public class Ticket {
    private @JsonProperty("passenger") Passenger passenger;
    private @JsonProperty("price") Double price;

    public Ticket() {}

    public Ticket(Passenger passenger, Double price) {
        this.passenger = passenger;
        this.price = price;
    }

    public Passenger getPassenger() {
        return passenger;
    }

    public void setPassenger(Passenger passenger) {
        this.passenger = passenger;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "passenger=" + passenger +
                ", price=" + price +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ticket ticket = (Ticket) o;
        return Objects.equals(passenger, ticket.passenger) &&
                Objects.equals(price, ticket.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(passenger, price);
    }
}
