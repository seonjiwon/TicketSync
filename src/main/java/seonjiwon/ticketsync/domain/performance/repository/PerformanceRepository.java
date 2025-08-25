package seonjiwon.ticketsync.domain.performance.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import seonjiwon.ticketsync.domain.performance.entity.Performance;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface PerformanceRepository extends JpaRepository<Performance, Long> {

    @Query("SELECT p FROM Performance  p")
    List<Performance> findByCursorDefault(Pageable pageable);

    @Query("SELECT p FROM Performance  p " +
            "WHERE p.createdAt > :cursor")
    List<Performance> findByCursor(@Param("cursor") LocalDateTime cursor, Pageable pageable);
}
