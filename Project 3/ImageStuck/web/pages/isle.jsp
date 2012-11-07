<%-- 
    Document   : isle
    Created on : Nov 7, 2012, 2:26:46 AM
    Author     : AD
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
    <head>
        <title>ImageStuck</title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="photos.css" rel="stylesheet" type="text/css" />
    </head>
    
    <body>
        
        <%@ page import="reader.JavaXML" %>
               
        <div id="header">
            <img src="Logo.png" alt="logo"/>
            <h1><a href="../home.html">ImageStuck</a></h1>
            <h4>View your photos on ImageStuck</h4>
            <h4 id="logout"><a href="../index.html">Log Out</a></h4>
        </div>
        
        <div id="content">
            
            <% JavaXML res = new JavaXML(); %>
            <% String[] show = JavaXML.readerXML(request.getRequestURL().toString()); %>
                <% out.println(show[0]);%>
        </div>               
        
         <div id="legend">
              <% out.println(show[1]);%>
          </div>
            <div id="edit"> 
              
                <form action="../EditImage.do" method="POST">
                
                    <p>
                        <label>Edit</label>
                    </p>
                    <p>
                        <label>Caption</label>
			<input name="caption" type="text" />
                    </p>
                    <p>
			<label>Height</label>
			<input name="height" type="number" />
                    </p>
                    <p>
			<label>Width</label>
			<input name="width"  type="number" />
                    </p>
                    <p>
			<label>Rotate</label>
			<input name="rotate"  type="number" />
                    </p>
                    <p>
                        <input class="button" style="font-family: Tahoma" type="submit" value="Edit" />
                    </p>
                
            </form>
                
        </div>     
        
        
    </body>
</html>
