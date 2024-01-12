package com.plantaccion.alartapp.common.repository;

import com.plantaccion.alartapp.common.model.Branch;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BranchRepository extends JpaRepository<Branch, Long> {
}
