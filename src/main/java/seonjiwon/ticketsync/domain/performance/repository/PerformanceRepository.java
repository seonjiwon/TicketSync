package seonjiwon.ticketsync.domain.performance.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import seonjiwon.ticketsync.domain.performance.entity.Performance;

public interface PerformanceRepository extends JpaRepository<Performance, Long> {
}
