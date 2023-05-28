package et.tk.api.ticket;

import et.tk.api.ticket.Dto.ScheduleInfo;
import et.tk.api.ticket.Dto.SeatInfo;
// import et.tk.api.ticket.Dto.TicketPost;
import et.tk.api.ticket.Dto.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.aggregation.DateOperators.DayOfWeek;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class TicketService {
    @Autowired
    private TicketRepository ticketRepository;
    @Autowired
    private RestTemplate restTemplate;

    public UserInfo userInfo (String userId){
        ResponseEntity<UserInfo> userInfoResponseEntity = restTemplate
                .getForEntity("http://localhost:8084/api/users/" + userId, UserInfo.class);
        UserInfo userInfo = userInfoResponseEntity.getBody();
        System.out.println(userInfoResponseEntity.getStatusCode());
        return userInfo;
    }

    public ScheduleInfo scheduleInfo (String scheduleId){

        ResponseEntity<ScheduleInfo> scheduleInfoResponseEntity = restTemplate
                .getForEntity("http://localhost:8082/api/schedules/" + scheduleId, ScheduleInfo.class);
        ScheduleInfo scheduleInfo = scheduleInfoResponseEntity.getBody();
        System.out.println(scheduleInfoResponseEntity.getStatusCode());
        return scheduleInfo;
    }

    public ResponseEntity<SeatInfo> seatInfo (String seatId){

        ResponseEntity<SeatInfo> seatInfoResponseEntity;
        try {
            seatInfoResponseEntity =  restTemplate
                    .getForEntity("http://localhost:8085/api/seats/" + seatId, SeatInfo.class);
            return new ResponseEntity<>(seatInfoResponseEntity.getBody(), HttpStatus.OK);
        } catch (HttpClientErrorException e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private boolean makePayment(String token, double amount) {
        String paymentUrl = "http://localhost:8090/api/payment/charge";

        HttpHeaders headers = new HttpHeaders();
        headers.set("token", token);
        headers.set("amount", String.valueOf(amount));

        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<String> response;

        try {
            response = restTemplate.exchange(paymentUrl, HttpMethod.POST, entity, String.class);
        } catch (Exception e) {
            return false;
        }

        if (response.getStatusCode() == HttpStatus.OK) {
            return true;
        } else {
            return false;
        }
    }

    public ResponseEntity<String> createTicket(Ticket ticket) {

        double totalPrice = 0.0;  // holds the total price of the seats

        UserInfo userInfo = this.userInfo(ticket.getUserId()); // check if user exists
        if (userInfo == null)
            return new ResponseEntity<>("User not found!", HttpStatus.NOT_FOUND);

        ScheduleInfo scheduleInfo = this.scheduleInfo(ticket.getScheduleId()); // check if schedule exists
        if (scheduleInfo == null)
            return new ResponseEntity<>("Schedule not found!", HttpStatus.NOT_FOUND);


        for (String seatId : ticket.getSeatIds()) {

            SeatInfo seatInfo = this.seatInfo(seatId).getBody();
            if (seatInfo == null)
                return new ResponseEntity<>("Seat not found!", HttpStatus.NOT_FOUND);

            for (String scheduleId : seatInfo.getScheduleIds()) {
                if (Objects.equals(ticket.getScheduleId(), scheduleId))
                    return new ResponseEntity<>("Seat taken : " + seatInfo.getRow() + seatInfo.getNumber(), HttpStatus.BAD_REQUEST);
            }
            
        totalPrice += seatInfo.getPrice(); // Add the price of each seat to the total ticket price
        }

        ticket.setId(null);
        ticket.setDateOfPublish(LocalDateTime.now().toString());
        ticket.setTicketStatus(true);

        // change this to check the date of the schedule
        LocalDate today = LocalDate.now();
        java.time.DayOfWeek dayOfWeek = today.getDayOfWeek();

        String getToday = dayOfWeek.toString();

        // Check if the day of the week is a weekend day (Saturday or Sunday)
        if (Objects.equals(getToday, "SATURDAY") || Objects.equals(getToday, "SUNDAY")) {
            totalPrice = totalPrice * 1.15; // add 15% to the total price
        }
        else if (Objects.equals(getToday, "FRIDAY")) {
            totalPrice = totalPrice * 1.1; // add 10% to the total price
        }

        ticket.setTicketPrice(totalPrice); // Set the ticket price to the total calculated price

        // make payment
        boolean paymentSuccessful = makePayment(ticket.getPaymentToken(), totalPrice);
        if (!paymentSuccessful) {
            return new ResponseEntity<>("Payment failed", HttpStatus.PAYMENT_REQUIRED);
        }

        ticketRepository.save(ticket);

        for (String seatId : ticket.getSeatIds()) {
            try {
                restTemplate.put("http://localhost:8085/api/seats/" + ticket.getScheduleId() + "/" + seatId , ticket.getId());
            } catch (HttpClientErrorException e) {
                return new ResponseEntity<>("Venue service error", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        return new ResponseEntity<>("Ticket created!", HttpStatus.CREATED);
    }



    // public String createTicket(TicketPost ticketPost){
    //        UserInfo userInfo = this.userInfo(ticketPost.getUserId()); // check if user exists
    //        if (userInfo == null)
    //            return "user";
    //
    //        ScheduleInfo scheduleInfo = this.scheduleInfo(ticketPost.getScheduleId()); // check if schedule exists
    //        if (scheduleInfo == null)
    //            return "schedule";
    //
    //
    //        for (String seat : ticketPost.getSeatIds()) {
    //            SeatInfo seatInfo = this.seatInfo(seat).getBody();
    //            assert seatInfo != null;
    //
    //            for (String scheduleId : seatInfo.ge()) {
    //                if (ticketId == )
    //            }
    //            if (seatInfo.isSeatStatus())
    //                return ("seat : " + seatInfo.getRow() + seatInfo.getNumber() + " is unavailable");
    //            else {          // setting the seat status true
    //                seatInfo.setSeatStatus(true);
    //                restTemplate.put("http://localhost:8081/api/seats/" + seatInfo.getId(), seatInfo);
    //            }
    //        }
    //        Ticket ticket = new Ticket(ticketPost);
    //        ticketRepository.save(ticket);
    //        userInfo.setTicketId(ticket.getId());
    //        System.out.println(userInfo.getId());
    //        System.out.println(ticket.getId());
    //        restTemplate.put("http://localhost:8084/api/users/ticket/" + userInfo.getId() , ticket.getId());
    //        System.out.println(ticket.getId());
    //        return "created";
    //    }

    public List<Ticket> getAllTickets(){

        List<Ticket> ticketList = ticketRepository.findAll();
        if (ticketList.isEmpty())
            return null;
        return ticketList;
    }

    public Ticket getById(String id) {
        Optional<Ticket> ticketOptional = ticketRepository.findById(id);
        return ticketOptional.orElse(null);
    }

    public List<Ticket> getTicketsByUserId(String userId) {
        return ticketRepository.findByUserId(userId);
    }

    public List<Ticket> getTicketsByScheduleId(String scheduleId) {
        return ticketRepository.findByScheduleId(scheduleId);
    }

    // Tobe used only by the gatekeeper
    public String updateTicketStatus(String id) {
        Optional<Ticket> ticketOptional = ticketRepository.findById(id);

        if (ticketOptional.isEmpty()) // checking if the ticket exists
            return "not found";

        if (ticketOptional.get().isTicketStatus()) {
            ticketRepository.deleteById(id);
            Ticket updatedTicket = ticketOptional.get();
            updatedTicket.setTicketStatus(false);
            ticketRepository.save(updatedTicket);
            return "valid";
        } else {
            return "invalid";
        }
    }
}
