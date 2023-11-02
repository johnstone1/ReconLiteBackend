package com.emtechhouse.reconmasterLitebackend.Repositories;

import com.emtechhouse.reconmasterLitebackend.Models.Colu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ColuRepository extends JpaRepository<Colu,Long> {
    Optional<Colu> findByColumnName(String columnName);
}
