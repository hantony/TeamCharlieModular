package com.umgc.application.attendancelog;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

// Spring Data JPA creates CRUD implementation at runtime automatically.
public interface AttendanceLogRepository extends JpaRepository<AttendanceLog, Long> {

   List<AttendanceLog> findByUserId(Long userId);



Optional<AttendanceLog> findByTerminalId(Long terminalId);

//   Custom query
//       @Query("SELECT b FROM Book b WHERE b.publishDate > :date")
//       List<User> findByPublishedDateAfter(@Param("date") LocalDate date);

}