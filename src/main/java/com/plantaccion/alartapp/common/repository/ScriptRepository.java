package com.plantaccion.alartapp.common.repository;

import com.plantaccion.alartapp.common.model.Script;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScriptRepository extends JpaRepository<Script, Long> {
}
