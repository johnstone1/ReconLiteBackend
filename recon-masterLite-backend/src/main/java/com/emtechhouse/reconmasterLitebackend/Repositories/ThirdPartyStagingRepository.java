package com.emtechhouse.reconmasterLitebackend.Repositories;

import com.emtechhouse.reconmasterLitebackend.Models.ThirdPartyStaging;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ThirdPartyStagingRepository extends JpaRepository<ThirdPartyStaging, String> {
    Optional<ThirdPartyStaging> findByRrn(String rrn);
}
