<%-- 
    Document   : login
    Created on : 06-Apr-2019, 19:02:10
    Author     : kevin
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!doctype html>
<html lang="en">
<head>
<meta charset="UTF-8">
<title>Login</title>
<link rel="stylesheet" type="text/css" href="styles/mainstyle.css" />
<link rel="stylesheet" type="text/css" href="styles/forum.css" />
<script type="text/javascript">
            function validateLogin()
            {
                var user_name = document.getElementById("user_name").value;
                var password = document.getElementById("password").value;

                if (validate_req(username, password) == true)
                    return true;
                else
                    return false;
            }

            function validate_req(username, password)
            {
                if (user_name == null || user_name == "")
                {
                    alert("Please, Enter your username");
                    return false;
                }
                if (password == null || password == "")
                {
                    alert("Please, Enter your password");
                    return false;
                }

                return true;
            }
        </script>
</head>
<body>
	<div id="container">
		<header>
			<img src="images/bn_header.png" alt="PathFinder banner">
		</header>
		<nav id="menu">
			<ul>
				<li><a href="index.html">ABOUT US</a></li>
				<li><a href="register.html">REGISTER</a></li>
				<li><a href="login.html" class="current">LOGIN</a></li>
				<li><a href="contact.html">CONTACT US</a></li>
			</ul>
		</nav>
		<main>
			<section id="form">
				<h1>Login</h1>
				<form action="LoginDB" method="post" name="form"
					onSubmit="return validateLogin();">
					<fieldset>
						<p>
							<label for="user_name" class="title">Username: </label> <input
								type="text" name="user_name" id="user_name" />
						</p>
						<p>
							<label for="password" class="title">Password: </label> <input
								type="password" name="password" id="password" />
						</p>
						<p>
							<input type="submit" name="submit" id="submit" value="Login" />
						</p>
						<%
                                if (null != request.getAttribute("error"))
                                {
                                    out.println("<br><br>");
                                    out.println(request.getAttribute("error"));
                                }
                            %>
					</fieldset>
				</form>
				<br>
			</section>
		</main>
		<footer>
			<p>PathFinder Project 2019</p>
			<p>Authors: Kevin Dunne, Jekaterina Pavlenko &amp; Christopher
				Costelloe</p>
			<p>
				<img src="images/maze_ic.png" alt="Maze icon">
			</p>
		</footer>
	</div>
</body>
</html>

