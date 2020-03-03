package edu.uark.registerapp.commands.employees.helpers;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.lang3.StringUtils;

public class EmployeeHelper {
	public static String padEmployeeId(final int employeeId) {
		final String employeeIdAsString = Integer.toString(employeeId);

		return ((employeeIdAsString.length() < EMPLOYEE_ID_MAXIMUM_LENGTH)
				? StringUtils.leftPad(employeeIdAsString, EMPLOYEE_ID_MAXIMUM_LENGTH, "0")
				: employeeIdAsString);
	}

	public static byte[] hashPassword(final String password) {
		// TODO: Hash the password using a MessageDigest. An example can be found at
		// http://tutorials.jenkov.com/java-cryptography/messagedigest.html
		byte[] passwordBytes = new byte[0];
		try {
			passwordBytes = password.getBytes("UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		try {
			messageDigest = MessageDigest.getInstance("SHA-256");
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		byte[] encodedPassword = messageDigest.digest(passwordBytes);
		return encodedPassword;
	}

	private static final int EMPLOYEE_ID_MAXIMUM_LENGTH = 5;
	private static MessageDigest messageDigest;
}
