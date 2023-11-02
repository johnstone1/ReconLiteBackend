package com.emtechhouse.reconmasterLitebackend.Repositories;

import com.emtechhouse.reconmasterLitebackend.Models.FixedColumnRange;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FixedColumnRangeRepository extends JpaRepository<FixedColumnRange,Long> {
}
