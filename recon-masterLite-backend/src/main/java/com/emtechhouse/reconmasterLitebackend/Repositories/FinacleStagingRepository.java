package com.emtechhouse.reconmasterLitebackend.Repositories;

import com.emtechhouse.reconmasterLitebackend.Models.FinacleStaging;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FinacleStagingRepository extends JpaRepository<FinacleStaging,Long> {
    @Override
    Optional<FinacleStaging> findById(Long id);
}
