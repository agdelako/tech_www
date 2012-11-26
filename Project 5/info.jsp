<%-- 
    Document   : info
    Created on : Nov 25, 2012, 9:43:57 PM
    Author     : AD
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <script language="javascript" type="text/javascript" src="jquery.js"></script>
        <script language="javascript" type="text/javascript" src="jquery.flot.js"></script>
        <title>ImageStuck Information</title>
    </head>
    
    <body>
        
        <div style="font-family: Arial Rounded MT Bold">
            <%@ page import="listener.SessionListener" %>
            <%@ page import="listener.RequestListener" %>
            <%@ page import="java.util.*" %>

            
            <% int count = SessionListener.SessionListener(); %>
            <% out.println("Active sessions:" + count);%>
            
            <br>

            <% int total = SessionListener.TotalSessionListener(); %>
            <% out.println("Total visits:" + total);%>
            
            <br>
            <br>

            <% RequestListener requestsNum = new RequestListener(); %>
            <% out.println("Total edits:" + requestsNum.getEditCount()+"<br>"); %>
            <% requestsNum.WriteRequestsData(); %>
            Total Image Edits in the last month: <br><br>
            <div id="placeholder" style="width:600px;height:300px;"></div> 
            
            <script type="text/javascript">
                $(function () {
                    var options = {
                        lines: { show: true},
                        points: { show: true },
                        xaxis: { tickDecimals: 0, tickSize: 1 }
                    };
                    var data = [];
                    
                    alreadyFetched = {};

                    var iteration = 0;

                    function fetchData() {
                        ++iteration;

                        function onDataReceived(series) {

                                data = [ series ];
                                $.plot($("#placeholder"), data, options);

                        }
                        $.ajax({
                            url: "data.json",
                            method: 'GET',
                            dataType: 'json',
                            success: onDataReceived
                        });

                            
                     }

                     setTimeout(fetchData, 100);

                });
            </script>
        </div>
    </body>
</html>
