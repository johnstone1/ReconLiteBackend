package com.emtechhouse.reconmasterLitebackend.Repositories;
import com.emtechhouse.reconmasterLitebackend.Models.PriorityTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PriorityRepository extends JpaRepository<PriorityTable, Long> {
    @Override
    Optional<PriorityTable> findById(Long id);
    List<PriorityTable> findByDeletedFlag(Character deletedFlag);

}
