package in.ashokit.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import in.ashokit.binding.Passenger;
import in.ashokit.entity.TicketInfo;
import in.ashokit.service.TicketInfoService;
@RestController
public class TicketInfoRestController {
	@Autowired
	private TicketInfoService ticketInfo;
	
	@PostMapping("/ticket")
	public ResponseEntity<TicketInfo> bookTicket(@RequestBody Passenger passenger){
		TicketInfo ticket = ticketInfo.bookTicket(passenger);
		return new ResponseEntity<>(ticket,HttpStatus.CREATED);
	}
	
	@GetMapping("api/ticket/{ticketId}")
	public ResponseEntity<TicketInfo> getTicket(@PathVariable Integer ticketId){
		TicketInfo  ticket = ticketInfo.getTicketById(ticketId);
		return new ResponseEntity<>(ticket,HttpStatus.OK);
	}
	
	@GetMapping("/tickets")
	public ResponseEntity<List<TicketInfo>> getAllTickets(){
		List<TicketInfo> allTickets = ticketInfo.getAllTickets();
		return new ResponseEntity<>(allTickets,HttpStatus.OK);
	}
	
	@PutMapping("/ticket")
	public ResponseEntity<TicketInfo> updateTicket(@PathVariable Integer ticketId, @RequestBody Passenger passenger){
		TicketInfo ticket = ticketInfo.updateTicket(ticketId, passenger);
		return new ResponseEntity<>(ticket,HttpStatus.OK);
	}
	@DeleteMapping("/ticket/{ticketId}")
	public ResponseEntity<String> deleteCourse(@PathVariable Integer ticketId){
		 String ticket = ticketInfo.cancelTicket(ticketId);
		return new ResponseEntity<>(ticket,HttpStatus.OK);
	}
}