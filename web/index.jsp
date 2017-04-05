
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <!-- 
        IMPORTANT: download and includes bootstrap.min.css,jquery-3.1.1.min.js
        and boostrap.min.js in the project for bootstrap framework and jquery AJAX request
        -->
        <!-- Remember file must be in this order -->
        <link href="css/bootstrap.min.css" rel="stylesheet">
        <script src="js/jquery-3.1.1.min.js" type="text/javascript"></script> 
        <script  src="js/bootstrap.min.js" type="text/javascript"></script> 
        <!-- Add jquery file contains functions for sending AJAX request to API server -->
        <script type="text/javascript" src="js/index.js"></script>
        <!-- link to css file for this page -->
        <link rel="stylesheet" href="css/index.css">

        <title>Home</title>
</head>
<body>
 <!-- hidden form for user's name and username and will be used in Jquery Ajax to send request to API server -->
    <form id="profileform">
       <input type="hidden" name="Username" id="Username" value="meka">
       <input type="hidden" name="Name" id="Name" value="mew kanokwan">
    </form> 
<!-- ...................................ends profile form here ............................................... -->
<!-- .................................... Header Starts here................................................... -->
<%@include file="includes/header.jsp"%>
<!-- .................................... Header ends here...................................................... -->
 
<div class="container">
        
      
    
    
        
</div>        
</body>
</html>
