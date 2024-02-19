package com.plantaccion.alartapp.common.repository;

import com.plantaccion.alartapp.common.model.Script;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ScriptRepository extends JpaRepository<Script, Long> {

    @Query("SELECT COUNT(s) FROM Script s WHERE s.isActive = true")
    Long countOfActiveScripts();



}
