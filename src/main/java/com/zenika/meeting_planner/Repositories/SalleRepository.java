package com.zenika.meeting_planner.Repositories;

import com.zenika.meeting_planner.Entities.Salle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SalleRepository extends JpaRepository<Salle,Long> {
}
