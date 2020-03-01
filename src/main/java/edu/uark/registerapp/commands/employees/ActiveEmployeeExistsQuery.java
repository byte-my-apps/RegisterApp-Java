package edu.uark.registerapp.commands.employees;

import org.springframework.beans.factory.annotation.Autowired;

import edu.uark.registerapp.commands.exceptions.NotFoundException;
import edu.uark.registerapp.models.repositories.EmployeeRepository;

public class ActiveEmployeeExistsQuery {
  public boolean execute() {
    if (employeeRepository.existsByIsActive(true)) {
      return true;
    } else {
      throw new NotFoundException("No active user");
    }
  }

  @Autowired
  EmployeeRepository employeeRepository;
}