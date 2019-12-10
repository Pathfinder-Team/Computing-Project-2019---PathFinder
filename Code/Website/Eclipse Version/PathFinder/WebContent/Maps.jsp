<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="java.io.*,java.util.*,java.sql.*"%>
<%@ page import="javax.servlet.http.*,javax.servlet.*"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Organization Page JSP</title>
<link rel="stylesheet" type="text/css" href="styles/mainstyle.css" />
<link rel="stylesheet" type="text/css" href="styles/forum.css" />
</head>
<body>

	<%
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		Connection conn = null;
		Connection conn2 = null;
		Statement stmt = null;
		PreparedStatement prepStat = null;
		//GetImageAction g1;
		ArrayList<String> specialArray = new ArrayList<>();
		//specialArray = g1.getImageArray();
		int powerID;
		String powerUsername;
		String powerFirstName;
		String powerLastName;
		String powerPassword;
		String powerEmail;
		String powerOrgName;
		int powerStatus;
		ResultSet result;

		int idRights = 0;
		String userNameRights = "";
		String passwordRights = "";
		String emailRights = "";
		int AccountStatusRights = 0;
		int special;
		String orgNameRights = "";

		String organisation_name = "";
		String organisation_address = "";
		String organisation_email = "";
		String organisation_mobile = "";
		String organisation_building_name = "";
		String user_org_name = "";

		String org_name = "";
		String org_building = "";
		String map_name = "";
		String map_comments = "";
		Blob map_image = null;

		String URL = "jdbc:mysql://remotemysql.com:3306/4eyg55o51S?autoReconnect=true&useSSL=false";
		String USERNAME = "4eyg55o51S";
		String PASSWORD = "ADRFyeBfRn";

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			// setup the connection with the DB
			conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
			System.out.println("Connected");
		} catch (ClassNotFoundException | SQLException e) {
			System.err.println("Error 1" + e);
		}

		// create a cookie array
		Cookie cookie = null;
		Cookie[] cookies = null;
		cookies = request.getCookies();
		// were going to loop through the cookies array and if the active cookies match
		// values in the database
		// we know we are that user in the database so were going to put there
		// information into some variables
		try {
			if (cookies != null) {
				for (int i = 0; i < cookies.length; i++) {
					cookie = cookies[i];
					stmt = conn.createStatement();
					String sql5 = "select user_id,user_name,password,account_rank_account_rank_id,email,organisation_name from users";
					result = stmt.executeQuery(sql5);
					while (result.next()) {
						powerOrgName = result.getString("organisation_name");
						powerUsername = result.getString("user_name");
						powerID = result.getInt("user_id");
						powerPassword = result.getString("password");
						powerStatus = result.getInt("account_rank_account_rank_id");
						powerEmail = result.getString("email");

						if (powerUsername.equals(cookie.getValue())) {
							userNameRights = powerUsername;
							idRights = powerID;
							passwordRights = powerPassword;
							AccountStatusRights = powerStatus;
							emailRights = powerEmail;
							orgNameRights = powerOrgName;
						}
					}
				}
			}
		} catch (SQLException ex) {
			System.err.println("Error 2" + ex);
		}

		try {
			//System.out.println("get org details");
			//orgNameRights = "Limerick Institute of Technology";
			prepStat = conn.prepareStatement(
					"select organisation_name, organisation_address, organisation_email,organisation_mobile,organisation_building_name from organisation where organisation_name = ?");
			prepStat.setString(1, orgNameRights);
			result = prepStat.executeQuery();
			while (result.next()) {
				organisation_name = result.getString("organisation_name");
				organisation_address = result.getString("organisation_address");
				organisation_email = result.getString("organisation_email");
				organisation_mobile = result.getString("organisation_mobile");
				organisation_building_name = result.getString("organisation_building_name");
			}

		} catch (SQLException ex) {
			System.err.println("Error: " + ex);
		}
	%>
	<div id="container">
		<header>
			<img src="images/bn_header.png" alt="">
		</header>
		<nav id="menu">
			<ul>
				"
				<li><a href="index.html">ABOUT US</a></li>
				<li><a href="register.html">REGISTER</a></li>
				<li><a href="login.html">LOGIN</a></li>
				<li><a href="contact.html">CONTACT US</a></li>
			</ul>
		</nav>
		<main>
			<section id="form">
				<ul class="sign_login">
					<li><a href="details.html">DETAILS</a></li>
					<li><a href="orgDB" class="current">MAPS</a></li>
					<li><a href="LogOutDB">LOG OUT</a></li>
					<li><a href="ControlDB">Control</a></li>
				</ul>
				<br> <br>
				<p>Here you can upload maps:</p>
				<br>

				<p>
					Organization name:<%=organisation_name%></p>
				<p>
					Organization Address:<%=organisation_address%></p>
				<p>
					Organization Email:
					<%=organisation_email%></p>
				<p>
					Organization Contact Number:
					<%=organisation_mobile%></p>
				<p>
					Organization Building Name:
					<%=organisation_building_name%></p>
				<br>

				<form action="UploadMapDB" method="post" class="">
					<button type="submit">Upload A New Map</button>
				</form>
				<br> <select name="party">
					<option value="">SELECT</option>
					<%
						for (int i = 0; i < specialArray.size(); i++) {
							String party = (String) specialArray.get(i);
					%>
					<option value="<%=party%>">
						<%=party%>
					</option>
					<%
						}
					%>
				</select> <input type="submit" value="Select Floor"> <br> <br>
				<img src="GetImageAction" width="300" height="325" border="1">
				<br> <img src="GetImageAction?orgNameRights=<%=orgNameRights%>"
					width="125" height="125" border="1">
			<br>
			</section>
		</main>
		<footer>
			<p>PathFinder project 2019</p>
			<p>Authors: Kevin Dunne,Jekaterina Pavlenko & Christopher
				Costelloe</p>
			<p>
				<img src="images/maze_ic.png" alt="">
			</p>
		</footer>
</body>
</html>