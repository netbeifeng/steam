/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
