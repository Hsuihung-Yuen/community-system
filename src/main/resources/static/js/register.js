$(function () {
	$("form").submit(check_data);
	$("input").focus(clear_error);
});

function check_data() {
	var pwd1 = $("#password").val();
	var pwd2 = $("#confirm-password").val();
	if (pwd1 != pwd2) {
		$("#confirm-password").addClass("is-invalid");
		return false;
	}
	return true;
}

function clear_error() {
	$(this).removeClass("is-invalid");
}

// password-confirmation.js

document.addEventListener('DOMContentLoaded', function () {
	var passwordInput = document.getElementById('password');
	var confirmPasswordInput = document.getElementById('confirm-password');
	var registerButton = document.getElementById('register-button'); // 替换为你的注册按钮的ID

	function updateButtonState() {
		var password = passwordInput.value;
		var confirmPassword = confirmPasswordInput.value;

		var mismatchMsg = document.getElementById('password-mismatch-msg');
		if (password !== confirmPassword) {
			mismatchMsg.style.display = 'block';
			registerButton.disabled = true;
		} else {
			mismatchMsg.style.display = 'none';
			registerButton.disabled = false;
		}
	}

	confirmPasswordInput.addEventListener('input', updateButtonState);
	passwordInput.addEventListener('input', updateButtonState);

	// 初始化时执行一次，确保初始状态正确
	updateButtonState();
});
