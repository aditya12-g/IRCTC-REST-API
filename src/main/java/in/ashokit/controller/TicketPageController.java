package in.ashokit.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import in.ashokit.binding.Passenger;
import in.ashokit.entity.TicketInfo;
import in.ashokit.service.TicketInfoService;

import java.util.List;

@Controller
public class TicketPageController {

    @Autowired
    private TicketInfoService ticketInfoServices;

    
    @GetMapping("/home")
    public String homePage() {
        return "home";
    }
    
    @GetMapping("/")
    public String redirectToHome() {
        return "redirect:/home";
    }

    
    @GetMapping("/book-ticket")
    public String bookTicketForm(Model model) {
        model.addAttribute("passenger", new Passenger());
        return "book-ticket";
    }

    
    @PostMapping("/book-ticket")
    public String bookTicketSubmit(@ModelAttribute Passenger passenger, Model model) {
        TicketInfo ticket = ticketInfoServices.bookTicket(passenger);
        model.addAttribute("ticket", ticket);
        return "ticket-success";
    }

   
    @GetMapping("/tickets-list")
    public String viewTickets(Model model) {
        List<TicketInfo> tickets = ticketInfoServices.getAllTickets();
        model.addAttribute("tickets", tickets);
        return "tickets-list";
    }

    
    @GetMapping("/ticket/{ticketId}")
    public String ticketDetails(@PathVariable Integer ticketId, Model model) {
        TicketInfo ticket = ticketInfoServices.getTicketById(ticketId);
        model.addAttribute("ticket", ticket);
        return "ticket-details";
    }
    @GetMapping("/cancel-ticket")
    public String showCancelForm() {
        return "cancel-ticket";
    }

    
    @GetMapping("/cancel/{ticketId}")
    public String cancelTicketFromList(@PathVariable Integer ticketId, Model model) {
        String msg = ticketInfoServices.cancelTicket(ticketId);
        model.addAttribute("msg", msg);
        model.addAttribute("ticketId", ticketId);
        return "cancel-success";
    }
    
    @PostMapping("/cancelTicket")
    public String cancelTicket(@RequestParam("ticketId") Integer ticketId, Model model) {
        String msg = ticketInfoServices.cancelTicket(ticketId);
        model.addAttribute("msg", msg);
        return "cancel-ticket"; 
    }

}

