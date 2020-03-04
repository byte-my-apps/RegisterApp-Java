let hideEmployeeSavedAlertTimer = undefined;

document.addEventListener("DOMContentLoaded", () => {
	//const employeeIdElement = getEmployeeIDElement();
	getSaveActionElement().addEventListener("click", saveActionClick);

});

// Save
function saveActionClick(event) {
	if (!isValidSave()) {
		return;
	}

	const saveActionElement = event.target;
	saveActionElement.disabled = true;

	const employeeId = getEmployeeId();
	const employeeIdIsDefined = (employeeId != null && employeeId.trim() !== "");
	const saveActionUrl = ("/api/employee/"
		+ (employeeIdIsDefined ? employeeId : ""));
	const saveEmployeeRequest = {
		id: employeeId,
		employeeId: getEmployeeEmployeeId(),
		firstName: getEmployeeFirstName(),
		lastName: getEmployeeLastName(),
		password: getEmployeePassword(),
		classification: getEmployeeClassification(),
		managerId: getEmployeeManagerId()
	};

	if (employeeIdIsDefined) {
		ajaxPatch(saveActionUrl, saveEmployeeRequest, (callbackResponse) => {
			saveActionElement.disabled = false;

			if (isSuccessResponse(callbackResponse)) {
				displayEmployeeSavedAlertModal();
			}
		});
	} else {
		ajaxPost(saveActionUrl, saveEmployeeRequest, (callbackResponse) => {
			saveActionElement.disabled = false;

			if (isSuccessResponse(callbackResponse)) {
				displayEmployeeSavedAlertModal();

				if ((callbackResponse.data != null)
					&& (callbackResponse.data.id != null)
					&& (callbackResponse.data.id.trim() !== "")) {
						setEmployeeId(callbackResponse.data.id.trim());
						setEmployeeEmployeeId(callbackResponse.data.employeeId.trim());
						document.getElementById("employeeEmployeeIdTableRow").classList.remove("hidden");
					}
			}
		});
	}
};

function isValidSave() {
	const firstName = getEmployeeFirstName();
	if ((firstName == null) || (firstName.trim() === "")) {
		displayError("Please provide a valid first name.");
		const firstNameElement = getEmployeeFirstNameElement();
		firstNameElement.focus();
		firstNameElement.select();
		return false;
	}

	const lastName = getEmployeeLastName();
	if ((lastName == null) || (lastName.trim() === "")) {
		displayError("Please provide a valid last name.");
		const lastNameElement = getEmployeeLastNameElement();
		lastNameElement.focus();
		lastNameElement.select();
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

	const confirmPass = getEmployeeConfirmPassword();
	if (password.trim() !== confirmPass.trim()) {
		displayError("Please enter the identical password in the confirmation field.");
		const confirmPasswordElement = getConfirmPasswordElement();
		confirmPasswordElement.focus();
		confirmPasswordElement.select();
		return false;
	}

	const classification = getEmployeeClassification();
	if (classification == null || classification === "-1") {
		displayError("Please select an employee type from the list.")
		const classificationElement = getEmployeeClassificationElement();
		classificationElement.focus();
		return false;
	}
	return true;
}

function displayEmployeeSavedAlertModal() {
	if (hideEmployeeSavedAlertTimer) {
		clearTimeout(hideEmployeeSavedAlertTimer);
	}

	const savedAlertModalElement = getSavedAlertModalElement();
	savedAlertModalElement.style.display = "none";
	savedAlertModalElement.style.display = "block";

	hideEmployeeSavedAlertTimer = setTimeout(hideEmployeeSavedAlertModal, 1200);
}

function hideEmployeeSavedAlertModal() {
	if (hideEmployeeSavedAlertTimer) {
		clearTimeout(hideEmployeeSavedAlertTimer);
	}

	getSavedAlertModalElement().style.display = "none";
}
// End save

//Getters and setters
function getSavedAlertModalElement() {
	return document.getElementById("employeeSavedAlertModal");
}
function getSaveActionElement() {
	return document.getElementById("saveButton");
}
function getEmployeeIdElement() {
	return document.getElementById("employeeId");
}
function getEmployeeId() {
	return getEmployeeIdElement().value;
}
function setEmployeeId(employeeId) {
	getEmployeeIdElement().value = employeeId;
}
function setEmployeeEmployeeId(employeeId) {
	getEmployeeEmployeeIdElement().value = employeeId;
}
function getEmployeeEmployeeIdElement() {
	return document.getElementById("employeeEmployeeId");
}
function getEmployeeEmployeeId() {
	return getEmployeeEmployeeIdElement().value;
}
function getEmployeeManagerIdElement() {
	return document.getElementById("employeeManagerId");
}
function getEmployeeManagerId() {
	return getEmployeeManagerIdElement().value;
}
function getEmployeeFirstNameElement() {
	return document.getElementById("firstName");
}
function getEmployeeFirstName() {
	return getEmployeeFirstNameElement().value;
}
function getEmployeeLastNameElement() {
	return document.getElementById("lastName");
}
function getEmployeeLastName() {
	return getEmployeeLastNameElement().value;
}
function getEmployeePasswordElement() {
	return document.getElementById("password");
}
function getEmployeePassword() {
	return getEmployeePasswordElement().value;
}
function getEmployeeConfirmPasswordElement() {
	return document.getElementById("confirmPassword");
}
function getEmployeeConfirmPassword() {
	return getEmployeeConfirmPasswordElement().value;
}
function getEmployeeClassificationElement() {
	return document.getElementById("classification");
}
function getEmployeeClassification() {
	return getEmployeeClassificationElement().value;
}
//End getters and setters
