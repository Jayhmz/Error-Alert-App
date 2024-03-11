package com.plantaccion.alartapp.common.repository.app;

import com.plantaccion.alartapp.common.model.app.Branch;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BranchRepository extends JpaRepository<Branch, Long> {
}
