<%@page import="java.util.ResourceBundle"%>
<%@page import="java.util.Locale;"%>
<%@ include file="/include.jsp"%>
<%@ page contentType="text/html; charset=UTF-8"%>

<html>
<head>
<title>Welcome to Festival Registration System</title>
<script language="JavaScript">
<!--
var nHist = window.history.length;
if(window.history[nHist] != window.location)
  window.history.forward();
//-->
</script>
<script type="text/javascript">
function isNumeric(value) {
	  if (value=="" || value == null || !value.toString().match(/^[-]?\d*\.?\d*$/))
	  { return false;
	  }
	  return true;
	}		
	function echeck(str) {
		   var emailPattern = /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,4}$/;   
			   return emailPattern.test(str);   							
	}
	function validateForm()
	{
	var fname=document.forms["regform"]["FIRSTNAME"].value;
	var lname=document.forms["regform"]["LASTNAME"].value;
	var uname=document.forms["regform"]["USERNAME"].value;
	var pwd=document.forms["regform"]["PASSWORD"].value;
	var cpwd=document.forms["regform"]["CPASSWORD"].value;
	var email=document.forms["regform"]["EMAIL"].value;
	var phoneno=document.forms["regform"]["PHONENO"].value;
	if (fname==null || fname=="")
	  {
	  alert("Please provide Firstname");
	  return false;
	  }
	if (lname==null || lname=="")
	  {
	  alert("Please provide Lastname");
	  return false;
	  }
	if (uname==null || uname=="")
	  {
	  alert("Please provide Username");
	  return false;
	  }
	if (pwd==null || pwd=="")
	  {
	  alert("Please provide password");
	  return false;
	  }
	if (cpwd==null || cpwd=="")
	  {
	  alert("Please confirm password");
	  return false;	
	  }
	if (email==null || email=="")
	  {
	  alert("Please provide Email");
	  return false;
	  }
	if (phoneno==null || phoneno=="")
	  {
	  alert("Please provide Phonenumber");
	  return false;
	  }
	if(pwd!=cpwd)
	{
		alert("Password and confirm paswword must be same !!");
		return false;
	}
	if (echeck(email)==false){
		alert("Invalid EmailID");
		return false;
	}
	if(isNumeric(phoneno)==false)
	{
		alert("Invalid Phonenumber");
		return false;
	}
}
	function cancelRegistration()
	{
		history.go(-1);
	}
</script>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<link rel="StyleSheet" href="css/struts2.css" type="text/css" />
<style type="text/css">
<!--
.style1 {
	font-size: 12
}
-->
</style>
<style>
.error {
	color: red;
	font-weight: bold;
	font-size: 20px;
}
</style>
</head>

<body>
<%
ResourceBundle res=ResourceBundle.getBundle("Login");



%>

	<br/><br/>
	<form action="newVistor.htm" name="regform" method="post" onsubmit="return validateForm()">
	<table width="80%" align="center"  border="2">
		<tbody><tr>
			<td>
			<div id="header">&nbsp;
			<div align="center"><%=res.getString("heading1") %></div>
			</div>
			
			<table>
				<tbody><tr>
					<td width="100%">
					<table align="right" cellpadding="2">
						<tbody><tr>
							<td width="90">
							<div id="menu" align="center"><a href="<jstlcore:url value="/index.jsp"/>">
							<%=res.getString("login_page") %> </a></div>
							</td>							
						</tr>
					</tbody></table>
					</td>
				</tr>
				<tr>
					<td width="900">
					<div id="content">
					
					<table align="center" border="0">
						
						<tbody><tr>
							<td align="center" colspan="2">
							<h3><%=res.getString("new_user_page") %></h3>
							</td>
						</tr>
						<tr><td align="center" colspan="2" style="font-style: italic;"><!--  Fields marked (<span style="font-weight: bold;color: red; font-size: 15px;">*</span>) are Mandatory--><%=res.getString("mandatory")%></td></tr>
						<tr><td></td><td></td></tr>
						<tr><td></td><td></td></tr>
						<tr>
						
							<td><span style="font-weight: bold;color: red;font-size: 15px;">*</span>
							<%=res.getString("first_name") %>:</td><td> 
							   <input type="text" name="FIRSTNAME" size="25" maxlength="40"></input></td> 
							
					   </tr>
					   <tr>			
							<td><span style="font-weight: bold;color: red;font-size: 15px;">*</span>
							<%=res.getString("last_name") %>: </td><td><input type="text" name="LASTNAME" size="25" maxlength="40"></input></td>
							
					   </tr>
					   <tr>
							<td><span style="font-weight: bold;color: red;font-size: 15px;">*</span>
							<%=res.getString("username") %>:</td><td><input type="text" name="USERNAME" size="25" maxlength="40"></input></td> 
							
					   </tr>
					   <tr>
						    <td><span style="font-weight: bold;color: red;font-size: 15px;">*</span>
						    <%=res.getString("password") %>:</td><td><input type="password" name="PASSWORD" size="27" maxlength="40"></input></td>
						   
					   </tr>
					   <tr>
						    <td><span style="font-weight: bold;color: red;font-size: 15px;">*</span>
						    <%=res.getString("confirm_password") %>:</td><td><input type="password" name="CPASSWORD" size="27" maxlength="40"></input></td>
						   
					   </tr>
					   <tr>
						   <td><span style="font-weight: bold;color: red;font-size: 15px;">*</span>
						    <%=res.getString("email") %>: </td><td> <input type="text" name="EMAIL" size="25" maxlength="50"></input></td> 
						    
					   </tr>
					   <tr>
                           <td><span style="font-weight: bold;color: red;font-size: 15px;">*</span>
                           <%=res.getString("phone_number") %>:</td><td><input type="text" name="PHONENO" size="25" maxlength="15"></input></td>
                           
					   </tr>
					   <tr>
						   <td> <%=res.getString("address") %>:</td><td> <input type="text" name="ADDRESS" size="25" maxlength="30"></input> </td>
						   
						</tr>
						<tr>	
						   <td colspan="2" align="right">										
						    <input value="<%=res.getString("register") %>" type="submit">
						    <input value="<%=res.getString("clear") %>" type="reset" ></td>
						</tr>
						<tr>
						
						</tr>
					</tbody></table>
					</div>
					</td>
					<!-- content end -->
				</tr>
			</tbody>
			</table>
			</td>			
		</tr>
		<tr><td colspan="4" align="center"><div style="font-size: 15px; color: red; font-weight: bold;">${REGISTRATIONSTATUSMESSAGE}</div></td></tr>
	</tbody></table>
	
	</form>
</body>

</html>
