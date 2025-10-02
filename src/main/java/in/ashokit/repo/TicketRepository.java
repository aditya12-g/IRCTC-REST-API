package in.ashokit.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import in.ashokit.entity.TicketInfo;

public interface TicketRepository extends JpaRepository<TicketInfo, Integer> {

}
