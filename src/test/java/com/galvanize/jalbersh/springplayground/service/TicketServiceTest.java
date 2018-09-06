package com.galvanize.jalbersh.springplayground.service;

import com.galvanize.jalbersh.springplayground.model.Passenger;
import com.galvanize.jalbersh.springplayground.model.Ticket;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static java.util.Arrays.asList;

@RunWith(SpringRunner.class)
@WebMvcTest(MathService.class)
@AutoConfigureMockMvc(secure=false)
public class TicketServiceTest {
    TicketService ts;

    @Before
    public void setup() throws Exception {
        ts = new TicketService();
    }

    @Test
    public void testSumTickets() throws Exception {
        Passenger passenger1 = new Passenger("Dwayne","Johnson");
        Passenger passenger2 = new Passenger("John","Cena");
        Passenger passenger3 = new Passenger("The Rock",null);
        List<Ticket> tickets = asList(new Ticket(passenger1,200.0),new Ticket(passenger2,180.0),new Ticket(passenger3,160.0));
        assert(ts.sumTickets(tickets)).equals("result: 540.0");
    }

}