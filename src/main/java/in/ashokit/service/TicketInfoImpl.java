package in.ashokit.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.ashokit.binding.Passenger;
import in.ashokit.entity.TicketInfo;
import in.ashokit.repo.TicketRepository;
@Service
public class TicketInfoImpl implements TicketInfoService {

    @Autowired
    private TicketRepository ticketRepo;

    @Override
    public TicketInfo bookTicket(Passenger passenger) {
        TicketInfo ticket = new TicketInfo();
        
        
        ticket.setTicketNum("TICKET-" + System.currentTimeMillis());
        ticket.setName(passenger.getName());
        ticket.setDoj(passenger.getDoj());
        ticket.setFromStation(passenger.getFromStation());
        ticket.setToStation(passenger.getToStation());
        ticket.setTrainNum(passenger.getTrainNum());
        ticket.setStatus("BOOKED");

        return ticketRepo.save(ticket);
    }

    @Override
    public TicketInfo getTicketById(Integer ticketId) {
        return ticketRepo.findById(ticketId).orElse(null);
    }

    @Override
    public List<TicketInfo> getAllTickets() {
        return ticketRepo.findAll();
    }

    @Override
    public String cancelTicket(Integer ticketId) {
        Optional<TicketInfo> ticketOpt = ticketRepo.findById(ticketId);
        if(ticketOpt.isPresent()) {
            TicketInfo ticket = ticketOpt.get();
            ticket.setStatus("CANCELLED");
            ticketRepo.save(ticket);
            return "Ticket Cancelled Successfully";
        }
        return "Ticket Not Found";
    }

    @Override
    public TicketInfo updateTicket(Integer ticketId, Passenger passenger) {
        TicketInfo existing = ticketRepo.findById(ticketId).orElse(null);
        if(existing != null) {
            existing.setName(passenger.getName());
            existing.setDoj(passenger.getDoj());
            existing.setFromStation(passenger.getFromStation());
            existing.setToStation(passenger.getToStation());
            existing.setTrainNum(passenger.getTrainNum());
            existing.setStatus("UPDATED");
            return ticketRepo.save(existing);
        }
        return null;
    }
}
