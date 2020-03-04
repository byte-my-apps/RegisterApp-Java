package edu.uark.registerapp.controllers;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import edu.uark.registerapp.controllers.enums.ViewModelNames;
import edu.uark.registerapp.controllers.enums.ViewNames;
import edu.uark.registerapp.models.api.Employee;
import edu.uark.registerapp.models.entities.ActiveUserEntity;
import edu.uark.registerapp.models.enums.EmployeeClassification;
import edu.uark.registerapp.models.repositories.ActiveUserRepository;
import edu.uark.registerapp.models.repositories.EmployeeRepository;

@Controller
@RequestMapping(value = "/employeeDetail")
public class EmployeeDetailRouteController extends BaseRouteController {
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView start(
		@RequestParam final Map<String, String> queryParameters,
		final HttpServletRequest request
	) {
		final Optional<ActiveUserEntity> activeUserEntity = this.getCurrentUser(request);
		if (noEmployees()) {
			return (new ModelAndView(ViewNames.EMPLOYEE_DETAIL.getViewName()))
				.addObject(
					ViewModelNames.EMPLOYEE.getValue(),
					(new Employee()).setIsInitialEmployee(true));
		} else if (!activeUserEntity.isPresent()) {
			ModelAndView modelAndView;
			modelAndView = new ModelAndView(ViewNames.SIGN_IN.getViewName());
			modelAndView.addObject(
				ViewModelNames.ERROR_MESSAGE.getValue(), 
				"Must be logged in to create employees.");
			return modelAndView;
		} else if (!EmployeeClassification.isElevatedUser(activeUserEntity.get().getClassification())) {
			ModelAndView modelAndView;
			modelAndView = new ModelAndView(ViewNames.MAIN_MENU.getViewName());
			modelAndView.addObject(ViewModelNames.ERROR_MESSAGE.getValue(), 
				"User must be General Manager or Shift Manager to Create Employee.");
			return modelAndView;
		} else {
			return (new ModelAndView(ViewNames.EMPLOYEE_DETAIL.getViewName()))
				.addObject(
					ViewModelNames.EMPLOYEE.getValue(),
					(new Employee()).setIsInitialEmployee(false));
		}
	}	

	@RequestMapping(value = "/{employeeId}", method = RequestMethod.GET)
	public ModelAndView startWithEmployee(
		@PathVariable final UUID employeeId,
		@RequestParam final Map<String, String> queryParameters,
		final HttpServletRequest request
	) {
		final Optional<ActiveUserEntity> activeUserEntity =
			this.getCurrentUser(request);
		if (!activeUserEntity.isPresent()) {
			return this.buildInvalidSessionResponse();
		} else if (!this.isElevatedUser(activeUserEntity.get())) {
			return this.buildNoPermissionsResponse();
		}

		// TODO: Query the employee details using the request route parameter
		// TODO: Serve up the page
		return new ModelAndView(ViewModelNames.EMPLOYEE.getValue());
	}

	// Helper methods
	private boolean activeUserExists() {
		if (employeeRepository.existsByIsActive(true)) {
      return true;
    } else {
      return false;
    } 
	}
	private boolean noEmployees() {
		if (employeeRepository.count() > 0) {
      return false;
    } else {
      return true;
    } 
	}

	@Autowired
	EmployeeRepository employeeRepository;
	@Autowired
	ActiveUserRepository activeUserRepository;
}
