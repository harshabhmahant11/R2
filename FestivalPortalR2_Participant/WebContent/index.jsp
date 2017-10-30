<%@page import="jdk.nashorn.internal.ir.RuntimeNode.Request"%>
<%@page import="org.apache.catalina.Session"%>
<%@ include file="/include.jsp"%>
<%@page import="java.util.ResourceBundle"%>
<%@page import="java.util.Locale;"%>
<%@ page contentType="text/html; charset=UTF-8" %>
<html>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />

<head>
<title>Welcome to Festival Event Registration System</title>
<link rel="StyleSheet" href="css/struts2.css" type="text/css" />
<script language="JavaScript">
<!--
var nHist = window.history.length;
if(window.history[nHist] != window.location)
  window.history.forward();
//-->
</script>

<script type="text/javascript">
function validateForm()
{
var uname=document.forms["logForm"]["USERNAME"].value;
var password=document.forms["logForm"]["PASSWORD"].value;
if (uname==null || uname=="")
{
alert("Please provide Username");
return false;
}
if (password==null || password=="")
{
alert("Please provide Password");
return false;
}
}

function selectlanguage()
{
	

}

</script>
</head>
<style>
.error {
	color: red;
	font-size: 13px; 
	font-weight: bold;
}
</style>

<body>
<br/><br/><br/><br/><br/><br/>
<%
	HttpSession session=request.getSession(true);
	ResourceBundle res= null;
	String ln = request.getParameter("ln");
	try
	{
	res = ResourceBundle.getBundle("Login");
	if(ln.equals("hindi"))
	{
	Locale.setDefault(new Locale("hi","IN"));
	}
	else
	{
	Locale.setDefault(new Locale("en","US"));

	}
	
	}
	catch(Exception e)
	{
		e.getMessage();
	}
	session.invalidate();

%>
<form method="post" name="logForm" action="searchVisitor.htm" onsubmit="return validateForm()">
<table width="80%" align="center" border="2" bordercolor="#339999">
	<tr>
		<td align="Center">
		<div id="header">&nbsp;
		<div align="center"><%=res.getString("heading1") %></div>
		</div>
		<!-- header end -->
		<br/>
		<table>
			<tr>
				<!--content begin -->
				<td colspan="2" align="center">
				<div id="content">
				<h3><%=res.getString("heading2")%></h3>
				</div>
				</td>
			</tr>
			<tr>
				<td>Visitor Name</td>
				<td><input type="text" name="USERNAME"></td>
			</tr>
			<tr>
				<td>Password</td>
				<td><input type="password" name="PASSWORD">
				</td>
			</tr>
			<tr>
				<td colspan="2" align="right"><input type='submit' value=<%=res.getString("login") %>></input> <br />
				</td>
			</tr>
			<tr>
				<td colspan="2" align="center"><span class="error">${ERROR}</span></td>
			</tr>
			<tr>
				<td></td>
			</tr>
			<tr>
				<td></td>
			</tr>
			<tr>
				<td>
				<div id="content">New Visitor:</div>
				<div id="content"><a href="registration.jsp">Register
				here</a></div>
				</td>
			</tr>
			<tr>
			<td><a href="<jstlcore:url value="/index.jsp?ln=hindi"/>">Hindi</a></td>
			<td><a href="<jstlcore:url value="/index.jsp?ln=english"/>">English</a></td>

			
			</tr>
		</table>
		<br />
		</td>
	</tr>
</table>

</form>

</body>

</html>
