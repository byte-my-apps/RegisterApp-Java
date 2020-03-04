package edu.uark.registerapp.commands.employees;

import java.util.Optional;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.uark.registerapp.commands.VoidCommandInterface;
import edu.uark.registerapp.commands.exceptions.ConflictException;
import edu.uark.registerapp.commands.exceptions.UnprocessableEntityException;
import edu.uark.registerapp.models.api.Employee;
import edu.uark.registerapp.models.api.EmployeeSignIn;
import edu.uark.registerapp.models.entities.EmployeeEntity;
import edu.uark.registerapp.models.entities.ActiveUserEntity;
import edu.uark.registerapp.models.repositories.EmployeeRepository;
import edu.uark.registerapp.models.repositories.ActiveUserRepository;

@Service
public class EmployeeSignInCommand implements VoidCommandInterface {
  @Transactional
	@Override
	public void execute() {
    
    UUID id = employeeRepository.findByEmployeeId(
      Integer.valueOf(employeeSignIn.getEmployeeId())).get().getId();
    String sessionKey = request.getSession().getId();
    employeeEntity = employeeRepository.findById(id).get();
    if (activeUserRepository.findByEmployeeId(id).isPresent()) {
      activeUserEntity = activeUserRepository.findByEmployeeId(id).get();
      activeUserEntity.setSessionKey(sessionKey);
      activeUserRepository.save(activeUserEntity);
    } else {
      activeUserEntity = new ActiveUserEntity();
      activeUserEntity.setEmployeeId(id);
      activeUserEntity.setSessionKey(sessionKey);
      activeUserEntity.setClassification(employeeEntity.getClassification());
      activeUserEntity.setName(employeeEntity.getFirstName());
      activeUserRepository.save(activeUserEntity);
    }
  }


	// Properties
  private EmployeeSignIn employeeSignIn;
  private HttpServletRequest request;
  private ActiveUserEntity activeUserEntity;
  private EmployeeEntity employeeEntity;
  public EmployeeSignIn getEmployeeSignIn() {
    return this.employeeSignIn;
  }
  
  public EmployeeSignInCommand setRequest(HttpServletRequest request) {
    this.request = request;
    return this;
  } 
  public EmployeeSignInCommand setEmployeeSignIn(final EmployeeSignIn employeeSignIn) {
		this.employeeSignIn = employeeSignIn;
		return this;
  }

  @Autowired
  private EmployeeRepository employeeRepository;
  @Autowired
	private ActiveUserRepository activeUserRepository;
}
