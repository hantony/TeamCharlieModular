package com.umgc.remoteterminal;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

// Spring Data JPA creates CRUD implementation at runtime automatically.
public interface RemoteTerminalRepository extends JpaRepository<RemoteTerminal, Long> {

   List<RemoteTerminal> findByLocationId(Long locationId);

//   Custom query
//       @Query("SELECT b FROM Book b WHERE b.publishDate > :date")
//       List<User> findByPublishedDateAfter(@Param("date") LocalDate date);

}