package edu.uark.registerapp.controllers;

import java.util.Map;
import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import edu.uark.registerapp.controllers.enums.ViewNames;
import edu.uark.registerapp.controllers.enums.ViewModelNames;
import edu.uark.registerapp.models.api.EmployeeSignIn;
import edu.uark.registerapp.models.repositories.EmployeeRepository;
import edu.uark.registerapp.models.entities.EmployeeEntity;


@Controller
@RequestMapping(value = "/")
public class SignInRouteController extends BaseRouteController {
	// TODO: Route for initial page load

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView serveSignInPage(
		@RequestParam final Map<String, String> queryParameters,
		HttpServletRequest request
	) {

		if (employeeRepository.count() > 0) {
				return new ModelAndView(ViewNames.SIGN_IN.getViewName());
		} else {
			return new ModelAndView(
				REDIRECT_PREPEND.concat(
					ViewNames.EMPLOYEE_DETAIL.getRoute()));
		}
	}

	@RequestMapping(method = RequestMethod.POST,
	 consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public ModelAndView performSignIn(
		@RequestParam final Map<String, String> queryParameters,
		EmployeeSignIn employeeSignIn,
		// TODO: Define an object that will represent the sign in request and add it as a parameter here
		HttpServletRequest request
	) {
		final ModelAndView modelAndView;
		if (!employeeIdExists(employeeSignIn.getEmployeeId()) ||
		!passwordIsCorrect(employeeSignIn.getEmployeeId(), employeeSignIn.getPassword())) {
			modelAndView = new ModelAndView(ViewNames.SIGN_IN.getViewName());
			modelAndView.addObject(
				ViewModelNames.ERROR_MESSAGE.getValue(), 
				"Invalid username / password combination.");
			return modelAndView;
		}
		return new ModelAndView(REDIRECT_PREPEND.concat(
			ViewNames.MAIN_MENU.getRoute()));
		// TODO: Use the credentials provided in the request body
		//  and the "id" property of the (HttpServletRequest)request.getSession() variable
		//  to sign in the user

	}

	/*@RequestMapping(value = "/signIn", method = RequestMethod.GET)
	public ModelAndView startSignIn(
		@RequestParam final Map<String, String> queryParameters,
		HttpServletRequest request
	){
			} */



	private boolean employeeIdExists(String employeeId) {
		System.out.println(employeeRepository.existsByEmployeeId(Integer.valueOf(employeeId)));
		return employeeRepository.existsByEmployeeId(Integer.valueOf(employeeId));
		
	}
	private boolean passwordIsCorrect(String employeeId, String password) {
		EmployeeEntity employeeEntity = 
			employeeRepository.findByEmployeeId(Integer.valueOf(employeeId)).get();
		System.out.println(employeeEntity.getEmployeeId());
		System.out.println(employeeEntity.getPassword().toString());
		System.out.println("input: " + password);
		if (Arrays.equals(employeeEntity.getPassword(), new byte[0])) {
			return true;
		} else {
			return false;
		}
	}
	@Autowired
	private EmployeeRepository employeeRepository;

	
}
