package com.plantaccion.alartapp.common.repository.app;

import com.plantaccion.alartapp.common.model.app.Branch;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BranchRepository extends JpaRepository<Branch, Long> {
    Optional<Branch> findBySolId(String solId);
}
