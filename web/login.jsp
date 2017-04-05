<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <!-- Remember file must be in this order -->
        <link href="css/bootstrap.min.css" rel="stylesheet">
        <script src="js/jquery-3.1.1.min.js" type="text/javascript"></script> 
        <script  src="js/bootstrap.min.js" type="text/javascript"></script>
        
        <link rel="stylesheet" href="css/login.css">


           <title>Sign In</title>
 </head>
 <body>
   <div class="container">
       
    <div class="row">
        <div class="col-sm-6 col-md-4 col-md-offset-4">
           <!-- <h1 class="text-center login-title">Sign in to Health Portal</h1> -->
            <div class="account-wall">
                <img class="profile-img" src="images/Logo2.png" alt="Logo2">
                <h3 class="text-center login-title"><strong>Log in</strong></h3> 

                <form class="form-signin" method="post" action="login.jsp">
                    
                    <div class="input-group">
                    <span class="input-group-addon"><i class="glyphicon glyphicon-user"></i></span>
                    <input id="email" type="text" class="form-control" name="account" placeholder="username" required autofocus>
                    </div>
			 
                    <div class="input-group">
                   <span class="input-group-addon"><i class="glyphicon glyphicon-lock"></i></span>
                    <input id="password" type="password" class="form-control" name="password" placeholder="Password">
                    </div>
                        <br>
                    <div>    
                    <button class="btn btn-primary btn-block" type="submit">Sign in</button>
                    <a href="forgetpassword.jsp" class="text-center new-account">forget password?</a><span class="clearfix"></span>
                    <a href="signup.jsp" class="text-center new-account">sign up now </a>
                    </div>
                </form>
            </div>
            
            
        </div>
    </div>
</div>

</body>
</html>
