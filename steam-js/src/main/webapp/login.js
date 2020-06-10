var signUpButton = document.getElementById('signUp')
var signInButton = document.getElementById('signIn')
var container = document.getElementById('container')

signUpButton.addEventListener('click', function () {
    container.classList.add('right-panel-active')
})

signInButton.addEventListener('click', function () {
    container.classList.remove('right-panel-active')
})

function pw() {
    var pwd1 = document.getElementById("password-register").value;
    var pwd2 = document.getElementById("password-register-confirm").value;
    var rform = document.getElementById("rform");
    if(pwd1 == pwd2) {
        document.getElementById("submit").disabled = false;
    }
    else {
        document.getElementById("tips").innerHTML="<text id='red'>Zweimal eingegebene Passwörter sind inkonsistent</text>";
        document.getElementById("submit").disabled = true;
    }
}

function login() {
    $.ajax({
        url: "steam_service/user/login",
        type: "POST",
        data: $('.form-login').serialize(),
        dataType: "json",
        success: function (data) {
            console.log(data.status);
            if(data.status.indexOf("suc") != -1) {
                window.location.replace("index.html?username="+ $("#username").val());
            } else {
                alert("Falsche Benutzername oder Password.");
            }
        }
    })
}

function register() {
    $.ajax({
        url: "steam_service/user/register",
        type: "POST",
        data: $('.form-register').serialize(),
        dataType: "json",
        success: function (data) {
            if(data.status.indexOf("suc") != -1){
                signInButton.click()
            } else if(data.status.indexOf("existed") != -1){
                alert("Dieser Account existiert bereits!")
            } else {
                alert("Bitte geben Sie 6-12 Ziffern oder Buchstaben ein.")
            }
        }
    })
}

// forget3
// function validate() {
//     var pwt1 = document.getElementById("pwt1").value;
//     var pwt2 = document.getElementById("pwt2").value;
//     if(pwt1 != pwt2) {
//         window.alert("Du musst zweimal das selbe Passwort eingeben.");
//         return false;
//     }
//     return true;
// }

