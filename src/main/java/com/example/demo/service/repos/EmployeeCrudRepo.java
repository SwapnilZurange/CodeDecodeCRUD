package com.example.demo.service.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Employee;

@Repository
public interface EmployeeCrudRepo extends JpaRepository<Employee, Long> {

}
