package com.codigo.examen.repository;

import com.codigo.examen.entity.RolEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RolRepository extends JpaRepository<RolEntity,Long> {
    Optional<RolEntity> findByNombreRol(String nombreRol);


}
