package in.ashokit.service;

import java.util.List;

import in.ashokit.binding.Passenger;
import in.ashokit.entity.TicketInfo;

public interface TicketInfoService {
	
	public TicketInfo bookTicket(Passenger passenger);
	
	public TicketInfo getTicketById(Integer ticketId);
	
	public List<TicketInfo> getAllTickets();
	
	public String cancelTicket(Integer ticketId);
	
	public TicketInfo updateTicket(Integer ticketId, Passenger passenger);

}
