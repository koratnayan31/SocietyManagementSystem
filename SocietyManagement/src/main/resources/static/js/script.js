function adjustTextbox() {
	var isOwner = document.getElementById("isOwnerYes");
	var ownerEmailGroup = document.getElementById("ownerEmailGroup");
	ownerEmailGroup.hidden = isOwner.checked ? true : false;
	if(ownerEmailGroup.hidden){
		document.getElementById("ownerEmail").value=document.getElementById("email").value;
	}
}

 

function validateEmailPattern(email) {
	const re = /^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
	return re.test(String(email).toLowerCase());
}

function check(event) {
	event.preventDefault();
	var isFocus = false;

	var firstNameHasError = validateName("firstName", true);
	var middleNameHasError = validateName("middleName", false);
	var lastNameHasError = validateName("lastName", true);
	var mobileHasError = validateMobile();
	var emailHasError = validateEmail();
	var societyHouseNoHasError = validateSocietyHouseNo();
	var adharcardNoHasError = validateAdharcard();
	var ownerEmailHasError = false; document.getElementById("ownerEmailMsg").innerHTML = "";
	if (document.getElementById("isOwnerNo").checked) {
		ownerEmailHasError = validateOwnerEmail();
	}
	var adharcardHasError = validateAdharcardProof();
	var ownerProofHasError = validateOwnerProof();

	var passwordHasError = validatePassword();

	//password check
	function validatePassword() {
		var password = document.getElementById("password").value;
		var confirmPassword = document
			.getElementById("confirmPassword").value;
		clearMsg("passwordMsg");
		if (password.length < 8) {
			if (!isFocus) {
				document.getElementById("password").focus();
				isFocus = true;
			}
			document.getElementById("passwordMsg").innerHTML = "Minimum length should be 8";
			return true;
		}

		if (password === confirmPassword) {
			return false;
		} else {
			if (!isFocus) {
				document.getElementById("password").focus();
				isFocus = true;
			}
			document.getElementById("passwordMsg").innerHTML = "Password doesn't match";
			return true;
		}
	}

	//name validation
	function validateName(item, isMandatory) {
		var msg = item + "Msg";
		clearMsg(msg);
		item = document.getElementById(item);
		if (isMandatory) {
			if (item.value.length < 2) {
				document.getElementById(msg).innerHTML = "Minimum Length should be 2";
				if (!isFocus) {
					item.focus();
					isFocus = true;
				}
				return true;
			}
		} else {
			if (item.value.length != 0 && item.value.length < 2) {
				document.getElementById(msg).innerHTML = "Minimum Length should be 2";
				if (!isFocus) {
					item.focus();
					isFocus = true;
				}
				return true;
			}
		}
		return false;
	}

	//mobile number validation
	function validateMobile() {
		var mobileNumber = document.getElementById("mobileNumber");
		clearMsg("mobileNumberMsg");
		if (mobileNumber.value.length == 10) {
			return false;
		} else {
			document.getElementById("mobileNumberMsg").innerHTML = "Mobile number should be of length 10";
			if (!isFocus) {
				mobileNumber.focus();
				isFocus = true;
			}
			return true;
		}
	}

	//validate email
	function validateEmail() {
		clearMsg("emailMsg");
		var email = document.getElementById("email");
		if (!validateEmailPattern(email.value)) {
			document.getElementById("emailMsg").innerHTML = "Enter Valid Email Address";
			if (!isFocus) {
				email.focus();
				isFocus = true;
			}
			return true;
		} else {
			return false;
		}
	}

	//validate owner email
	function validateOwnerEmail() {
		clearMsg("ownerEmailMsg");
		var ownerEmail = document.getElementById("ownerEmail");
		if (!validateEmailPattern(ownerEmail.value)) {
			document.getElementById("ownerEmailMsg").innetHTML = "Enter Valid Email Address";
			if (!isFocus) {
				ownerEmail.focus();
				isFocus = true;
			}
			return true;
		} else {
			return false;
		}
	}

	//validate society house number
	function validateSocietyHouseNo() {
		clearMsg("societyHouseNoMsg");
		var societyHouseNo = document.getElementById("societyHouseNo");
		if (societyHouseNo.value == null || societyHouseNo.value == "" || societyHouseNo.value == " ") {
			document.getElementById("societyHouseNoMsg").innerHTML = "This field is required";
			if (!isFocus) {
				societyHouseNo.focus();
				isFocus = true;
			}
			return true;
		} else {
			return false;
		}

	}


	//validate adhar card proof
	function validateAdharcardProof() {
		clearMsg("adharcardProofMsg");
		var adharcard = document.getElementById("adharcard");
		if (adharcard.files.length == 0) {
			document.getElementById("adharcardProofMsg").innerHTML = "Choose Adhar card proof";
			return true;
		} else {
			return false;
		}
	}

	//validate owner Proof
	function validateOwnerProof() {
		clearMsg("ownerProofMsg");
		var ownerProof = document.getElementById("ownerProof");
		if (ownerProof.files.length == 0) {
			document.getElementById("ownerProofMsg").innerHTML = "Choose proof of Owner or Tenant";
			return true;
		} else {
			return false;
		}
	}

	//validate adharcard no
	function validateAdharcard() {
		var adharcard = document.getElementById("adharcardNo");
		clearMsg("adharcardNoMsg");
		if (adharcard.value.length != 12) {
			document.getElementById("adharcardNoMsg").innerHTML = "Adhar Card number should be of 12 digit";
			if (!isFocus) {
				adharcard.focus();
				isFocus = true;
			}
			return true;
		} else {
			return false;
		}
	}

	//message clearing
	function clearMsg(msgId) {
		document.getElementById(msgId).innerHTML = "";
	}

	if (firstNameHasError || middleNameHasError
		|| lastNameHasError || passwordHasError
		|| mobileHasError || ownerEmailHasError || emailHasError || societyHouseNoHasError || adharcardHasError || ownerProofHasError || adharcardNoHasError) {
		return false;
	} else {
		document.getElementById("ownerEmail").disabled = false;
		document.getElementById("registerForm").submit();
		return true;
	}
}


//sweet alert

//approve request
function initApprove(formId) {
	swal({
		title: "Are you sure?",
		text: "You are going to approve User as valid Society Member",
		icon: "warning",
		buttons: {
			confirm: { text: "Approve It", className: "myalert" },
			cancel: "Cancel"
		},
		dangerMode: false
	})
		.then((willApprove) => {
			if (willApprove) {
				document.location = "/admin/pending-requests/approve/" + formId;
			}
			else {

			}
		});
}


//reject request
function initReject(formId) {
	swal({
		title: "Are you sure?",
		text: "You are going to reject User's request as it is not valid user",
		icon: "warning",
		buttons: ["Cancel", "Yes"],
		dangerMode: true,
	})
		.then((willReject) => {
			if (willReject) {

				swal("Enter reason for Rejection", {
					content: "input",
				})
					.then((value) => {
						if (value.length > 3)
							document.location = "/admin/pending-requests/reject/" + formId + "?reason=" + value;
						else
							swal("Operation cancelled due to reason for rejection is not given properly");
					});


			}
			else {
			}
		});
}


//toggling sidebar

const toggleSidebar = () => {
	if ($('.sidebar').is(':visible')) {
		$('.sidebar').css("display", "none");
		$('.content').css("margin-left", "0%");
		$('.sidebar').removeClass("mt-5");
		$('.content').css("display","block");
		
		
	} else {
		$('.sidebar').css("display", "block");
		$('.content').css("margin-left", "20%");
		if (window.matchMedia('(max-width: 700px)').matches) {
			$('.content').css("display","none");
			$('.sidebar').addClass("mt-5");
		}
	}
};



//calendar

$('#forMonthYear').datepicker({
    format: "mm/yyyy",
    minViewMode: 1,
    endDate: 'today',
    autoclose:true
});

$('#forMonthYearBill').datepicker({
    format: "yyyy/mm",
    minViewMode: 1,
    endDate: 'today',
    autoclose:true
});


$('#dueDate').datepicker({
	format:"dd/mm/yyyy",
	startDate:'+1d',
	orientation: "bottom",
	autoclose:true,
});

function submitForMonthYear(){
	var forMonthYear=document.getElementById("forMonthYearBill");
	const actualDate=new Date(forMonthYear.value);
	console.log(actualDate);
	var form=document.getElementById("maintenanceBill");
	form.setAttribute("action","generate-maintenance-bill-fetch");
	form.submit();
}

function checkBillData(event)
{
	event.preventDefault();
	var form=document.getElementById("maintenanceBill");
	var error=false;
	if(form.getAttribute("action")!="generate-maintenance-bill-fetch")
	{
		var dueDate=document.getElementById("dueDate").value;
		if(dueDate==""||dueDate==null)
		{
			error=true;
			document.getElementById("dueDateMsg").innerHTML="Choose Due Date";
			return false;
		}
		var parts=dueDate.split("/");
		var date=new Date(parseInt(parts[2],10),parseInt(parts[1]-1,10),parseInt(parts[0],10));
		if(date.getTime()!=date.getTime()){
			document.getElementById("dueDateMsg").innerHTML="Choose Due Date";
			error=true;
		}
		parts=document.getElementById("forMonthYearBill").value.split("/");
		console.log(parts);
		date=new Date(parseInt(parts[0],10),parseInt(parts[1],10));
		if(date.getTime()!=date.getTime())
		{
			error=true;
			document.getElementById("forMonthYearBillMsg").innerHTML="Choose valid month/year";
		}
		if(!error)
			form.submit();
	}
	
	
}
