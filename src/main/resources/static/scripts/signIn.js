document.addEventListener("DOMContentLoaded", function(event) {
	// TODO: Anything you want to do when the page is loaded?
});

function validateForm() {
	const employeeId = getEmployeeId();
	if ((employeeId == null) || (isNaN(employeeId))) {
		displayError("Please provide a valid Employee ID.");
		const firstNameElement = getEmployeeFirstNameElement();
		firstNameElement.focus();
		firstNameElement.select();
		return false;
	}

	const password = getEmployeePassword();
		if (password == null || password.trim() === "" ) {
		displayError("Please provide a valid password.");
		const passwordElement = getPasswordElement();
		passwordElement.focus();
		passwordElement.select();
		return false;
	}
}
function getEmployeeIdElement() {
	return document.getElementById("employeeId");
}
function getEmployeeId() {
	return getEmployeeIdElement().value;
}
function getEmployeePasswordElement() {
	return document.getElementById("password");
}
function getEmployeePassword() {
	return getEmployeePasswordElement().value;
}