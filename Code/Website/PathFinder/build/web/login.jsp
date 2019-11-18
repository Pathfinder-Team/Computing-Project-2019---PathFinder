
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!doctype html>
<!-- Author: Jekaterina Pavlenko, Kevin Dunne, Christopher Costello
         Date: 
-->
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <title>Forum</title>
        <link rel="stylesheet" type="text/css" href="styles/mainstyle.css" />
        <link rel="stylesheet" type="text/css" href="styles/forum.css" />
        <script>
        </script>
        <script type="text/javascript">
            function validateLogin()
            {
                var username = document.getElementById("username").value;
                var password = document.getElementById("password").value;


                if (validate_req(username, password) == true)
                    return true;
                else
                    return false;
            }

            function validate_req(username, password)
            {
                if (username == null || username == "")
                {
                    alert("Please enter your username");
                    return false;
                }
                if (password == null || password == "")
                {
                    alert("Please enter your password");
                    return false;
                }

                return true;
            }

        </script>

    </head>

    <body>
        <div id="container">
            <header>

            </header>
            <nav id="menu">
                <ul>
                    <li><a href="index.html" >About Us</a></li>
                    <li><a href="register.html">Register</a></li>
                    <li><a href="login.html" >LOGIN</a></li>
                    <li><a href="forum.html">FORUM</a></li>
                    <li><a href="contact.html" >CONTACT</a></li>
                    <li><a href="ControlDB" >CONTROL</a></li>
                </ul>
            </nav>
            <main>
                <section id="form">
                    <h1>Login to Forum</h1>
                    <form action="LoginDB" method="post" name="form" onSubmit="return validateLogin();">
                        <fieldset>
                            <ul class="sign_login">
                                <li><a href="register.html">Register</a></li>
                                <li><a href="#login">Login</a></li>
                            </ul>
                            <br>
                            <br>
                            <p>
                                <label for="user_name" class="title">Username: <span>*</span></label>
                                <input type="text" name="user_name" id="user_name" />
                            </p>
                            <p>
                                <label for="password" class="title">Password: <span>*</span></label>
                                <input type="password" name="password" id="password" />
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

                </section>
            </main>
            <footer>
                <p>
                    
                </p>
            </footer>	

        </div>

    </body>
</html>

