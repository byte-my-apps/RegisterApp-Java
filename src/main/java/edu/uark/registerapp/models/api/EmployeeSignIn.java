package edu.uark.registerapp.models.api;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;

import edu.uark.registerapp.models.entities.EmployeeEntity;
import edu.uark.registerapp.commands.employees.helpers.EmployeeHelper;

public class EmployeeSignIn extends ApiResponse {
  private String employeeId;
  public String getEmployeeID() {
    return this.employeeId;
  }
  public EmployeeSignIn setEmployeeId(final String employeeId) {
    this.employeeId = employeeId;
    return this;
  }
  private String password;
  public String getPassword() {
    return this.password;
  }
  public EmployeeSignIn setPassword(final String password) {
    this.password = password;
    return this;
  }

  public EmployeeSignIn() {
      super();
      this.employeeId = "";
      this.password = "";
  }

  public EmployeeSignIn(final EmployeeEntity employeeEntity) {
      super(false);
      this.employeeId = EmployeeHelper.padEmployeeId(employeeEntity.getEmployeeId());
      this.password = new String(employeeEntity.getPassword());
  }

}

