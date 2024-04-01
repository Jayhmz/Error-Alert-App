package com.plantaccion.alartapp.common.repository.app;

import com.plantaccion.alartapp.common.model.app.Script;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ScriptRepository extends JpaRepository<Script, Long> {

    @Query("SELECT COUNT(s) FROM Script s WHERE s.isActive = true")
    Long countOfActiveScripts();

    @Query("SELECT s FROM Script s")
    Page<Script> findAllScripts(Pageable pageable);
}
