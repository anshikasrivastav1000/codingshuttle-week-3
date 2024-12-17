package com.springbootweb.springbootweb.repositories;

import com.springbootweb.springbootweb.entities.DepartmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepository extends JpaRepository<DepartmentEntity , Long> {

}
