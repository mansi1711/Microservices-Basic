<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<html>
<head>
 <script src="https://www.google.com/recaptcha/api.js" async defer></script> 
<style type="text/css">
body {
	margin: 0;
	color: #6a6f8c;
	background: #c8c8c8;
	font: 600 16px/18px 'Open Sans', sans-serif;
}

*, :after, :before {
	box-sizing: border-box
}

.clearfix:after, .clearfix:before {
	content: '';
	display: table
}

.clearfix:after {
	clear: both;
	display: block
}

a {
	color: inherit;
	text-decoration: none
}

.login-wrap {
	width: 100%;
 height:100%;	margin: auto;
	position: relative;
	background:
		url(https:// raw.githubusercontent.com/khadkamhn/day-01-login-form/master/img/bg.jpg)
		no-repeat center;
	box-shadow: 0 12px 15px 0 rgba(0, 0, 0, .24), 0 17px 50px 0
		rgba(0, 0, 0, .19);
}

.login-html {
	width: 100%;
	height: 100%;
	position: absolute;
	padding: 90px 70px 50px 70px;
	background: rgba(40, 57, 101, .9);
}

.login-html .sign-in-htm, .login-html .sign-up-htm {
	top: 0;
	left: 0;
	right: 0;
	bottom: 0;
	position: absolute;
	transform: rotateY(180deg);
	backface-visibility: hidden;
	transition: all .4s linear;
}

.login-html .sign-in, .login-html .sign-up, .login-form .group .check {
	display: none;
}

.login-html .tab, .login-form .group .label, .login-form .group .button
	{
	text-transform: uppercase;
}

.login-html .tab {
	font-size: 22px;
	margin-right: 15px;
	padding-bottom: 5px;
	margin: 0 15px 10px 0;
	display: inline-block;
	border-bottom: 2px solid transparent;
}

.login-html .sign-in:checked+.tab, .login-html .sign-up:checked+.tab {
	color: #fff;
	border-color: #1161ee;
}

.login-form {
	min-height: 345px;
	position: relative;
	perspective: 1000px;
	transform-style: preserve-3d;
}

.login-form .group {
	margin-bottom: 15px;
}

.login-form .group .label, .login-form .group .input, .login-form .group .button
	{
	width: 100%;
	color: #fff;
	display: block;
}

.login-form .group .input, .login-form .group .button {
	border: none;
	padding: 15px 20px;
	border-radius: 25px;
	background: rgba(255, 255, 255, .1);
}

.login-form .group input[data-type="password"] {
	text-security: circle;
	-webkit-text-security: circle;
}

.login-form .group .label {
	color: #aaa;
	font-size: 12px;
}

.login-form .group .button {
	background: #1161ee;
}

.login-form .group label .icon {
	width: 15px;
	height: 15px;
	border-radius: 2px;
	position: relative;
	display: inline-block;
	background: rgba(255, 255, 255, .1);
}

.login-form .group label .icon:before, .login-form .group label .icon:after
	{
	content: '';
	width: 10px;
	height: 2px;
	background: #fff;
	position: absolute;
	transition: all .2s ease-in-out 0s;
}

.login-form .group label .icon:before {
	left: 3px;
	width: 5px;
	bottom: 6px;
	transform: scale(0) rotate(0);
}

}
.foot-lnk {
	text-align: center;
}
.error {
	color: red;
}
</style>
</head>
<body>
	<div class="login-wrap">
		<div class="login-html">
			<label for="tab-1" class="tab">Sign In</label> <font color="red">${errorMessage}</font>
			<form:form method="post" modelAttribute="user">
				<div class="login-form">

					<div class="group">
						<form:label  class="label" path="username">Username</form:label> <form:input
							type="text" class="input" path="username"></form:input>
						<form:errors path="username" cssClass="error" >Enter valid username</form:errors>
					</div>
					<div class="group">
						<form:label  class="label" path="password">Password</form:label> <form:input
							type="password" class="input" data-type="password"
						 path="password"></form:input>
						<form:errors path="password" cssClass="error" ></form:errors>
					</div>
					
					<div class="group">
						<input type="submit" class="button" value="Sign In"/>

					</div>
				</div>
			</form:form>
		</div>
	</div>
</body>
</html>