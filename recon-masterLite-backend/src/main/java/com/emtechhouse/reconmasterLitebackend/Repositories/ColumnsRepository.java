package com.emtechhouse.reconmasterLitebackend.Repositories;

import com.emtechhouse.reconmasterLitebackend.Models.Columns;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ColumnsRepository extends JpaRepository<Columns, Long> {
    @Override
    Optional<Columns> findById(Long id);
}
