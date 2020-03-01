package edu.uark.registerapp.models.api;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;

import edu.uark.registerapp.models.entities.ProductEntity;

public class EmployeeSignIn extends ApiResponse {
  private String employeeId;
  public String getEmployeeID() {
      return this.employeeId;
  }
  public EmployeeSignIn setEmployeeId(final String employeeId) {
      this.employeeId = employeeId;
      return this;
  }
  private String employeeId;
  public String getEmployeeID() {
      return this.employeeId;
  }
  public EmployeeSignIn setEmployeeId(final String employeeId) {
      this.employeeId = employeeId;
      return this;
  }

}

