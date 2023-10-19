package com.emtechhouse.reconmasterLitebackend.Repositories;
import com.emtechhouse.reconmasterLitebackend.Models.ReconManager;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface ReconManagerRepository extends JpaRepository<ReconManager, Long> {
    Optional<ReconManager> findByRrn(String rrn);
}
