package com.example.restservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.restservice.models.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

}