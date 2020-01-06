<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="java.io.*,java.util.*,java.sql.*"%>
<%@ page import="javax.servlet.http.*,javax.servlet.*"%>
<%@page import="path.getRankPower"%>
<%@page import="path.SQLConnection"%>

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
		ArrayList<String> specialArray = new ArrayList<>();
		ArrayList<Integer> specialArrayID = new ArrayList<>();

		ResultSet result;

		getRankPower rp = new getRankPower();

		String organisation_name = "";
		String organisation_address = "";
		String organisation_email = "";
		String organisation_mobile = "";
		String organisation_building_name = "";
		String user_org_name = "";
		int imageId = 0;
		String selected = "";


	    	
	        try
	        {
	        	SQLConnection connect = new SQLConnection();
	            Class.forName("com.mysql.cj.jdbc.Driver");
	            // setup the connection with the DB
	            conn = DriverManager.getConnection(connect.URL(), connect.USERNAME(), connect.PASSWORD());
	            
	            System.out.println("Connected");
	        } catch (ClassNotFoundException | SQLException e)
	        {
	            System.err.println("Error 1" + e);
	        }
	   

		///////////////////////////////////////////
		rp.getStatusRank(request,response,stmt,conn);
		System.out.println(" Maps.jsp: "+ rp.getUserNameRights());

		try {
			//System.out.println("get org details");
			//orgNameRights = "Limerick Institute of Technology";
			prepStat = conn.prepareStatement("select * from organisation where organisation_name = ?");
			prepStat.setString(1, rp.getOrgRights());
			result = prepStat.executeQuery();
			while (result.next()) {
				organisation_name = result.getString("organisation_name");
				organisation_address = result.getString("organisation_address");
				organisation_email = result.getString("organisation_email");
				organisation_mobile = result.getString("organisation_mobile");
				organisation_building_name = result.getString("organisation_building_name");
			}

		} catch (SQLException ex) {
			System.err.println("Error Org: " + ex);
		}
		
		try {
			prepStat = conn.prepareStatement("select map_id,map_name from maps where org_name = ?");
			prepStat.setString(1, rp.getOrgRights());
			result = prepStat.executeQuery();
		
			while (result.next()) {
				specialArray.add(result.getString("map_name"));
				specialArrayID.add(result.getInt("map_id"));
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.err.println("Error 10 ae: " + e);
		}
		
	%>
	<div id="container">
		<header>
			<img src="images/bn_header.png" alt="">
		</header>
		<nav id="menu">
			<ul>
				<li><a href="index.html">ABOUT US</a></li>
				<li><a href="register.html">REGISTER</a></li>
				<li><a href="login.html">LOGIN</a></li>
				<li><a href="contact.html">CONTACT US</a></li>
			</ul>
		</nav>
		<main>
			<section id="form">
				<ul class="sign_login">
					<li><a href="DetailsDB">DETAILS</a></li>
					<li><a href="Maps.jsp" class="current">MAPS</a></li>
					<li><a href="LogOutDB">LOG OUT</a></li>
					<li><a href="ControlDB">Control</a></li>
				</ul>
				<br> <br>
				<p>Here you can upload maps:</p>
				<br>
				
				<!-- 
				<p>
					Organization name:<//%=organisation_name%>
				</p>
				
				<p>
					Organization Address:<//%=organisation_address%>
				</p>
				<p>
					Organization Email:
					<//%=organisation_email%>
				</p>
				<p>
					Organization Contact Number:
					<//%=organisation_mobile%>
				</p>
				<p>
					Organization Building Name:
					<//%=organisation_building_name%>
				</p>
				<br>
				
				 -->

				<form action="UploadMapDB" method="post" class="">
					<button type="submit">Upload A New Map</button>
				</form>
				
				<br>
			
				<form action="Maps.jsp">
					<select name="imageName">
						<option value="ground">SELECT</option>
						<%
							for (int i = 0; i < specialArray.size(); i++) 
							{
								String imageName = (String) specialArray.get(i);
						%>
						<option value="<%=imageName%>">
							<%=imageName%>
						</option>
						<%
						//System.out.println("value: "+imageName);
						}
						
						%>
					</select> 
				<input type="Submit" value="Submit">
				<br>
				<br>
				</form>
				<%
					selected = request.getParameter("imageName");
				%>
				<img src="GetImageAction?map_name=<%=selected%>" alt="Select Image" >
				<br>
				<br>
				<p>
					Building Name:<%=organisation_building_name%>
					<%  %>
				<br>
					
				</p>
				<form action="AddPointsDB" method="post">
				<input type="hidden" id="maps_map_id" name="maps_map_id" value="<%=selected%>">
				<input type="hidden" id="organisation_name" name="organisation_name" value="<%=organisation_name%>">
				<input type="hidden" id="organisation_building_name" name="organisation_building_name" value="<%=organisation_building_name%>">
				<button type="submit" >Add Map Points</button>
				</form>
				<br>
				<form action="ViewPointsDB" method="post">
				<button type="submit" >View Points</button>
				</form>
				<br>
				<br>
				<form action="ActionJson" method="post">
				<button type="submit" >Create Points Json</button>
				</form>
				<br>
			</section>
		</main>
		</div>
            <footer>
                <p>PathFinder project 2019</p>
                <p>Authors: Kevin Dunne, Jekaterina Pavlenko & Christopher Costelloe</p>
                <p><img src="images/maze_ic.png" alt="" ></p>
            </footer>	
</body>
</html>