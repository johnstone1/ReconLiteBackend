package com.emtechhouse.reconmasterLitebackend.Repositories;
import com.emtechhouse.reconmasterLitebackend.Models.ConfigurationTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ConfigurationTableRepository extends JpaRepository<ConfigurationTable,Long> {
    @Override
    Optional<ConfigurationTable> findById(Long id);
    Optional<ConfigurationTable>findByFileType(String fileType);
    List<ConfigurationTable> findByDeletedFlag(Character N);
    //List<ConfigurationTable> findById(Long id);


}
