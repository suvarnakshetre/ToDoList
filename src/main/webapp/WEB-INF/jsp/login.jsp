<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Login</title>

<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/toastr.js/latest/toastr.min.css">

<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/toastr.js/latest/toastr.min.js"></script>

</head>

<body>

<div class="container">
    <div class="row justify-content-center mt-5">
        <div class="col-md-4">

            <h3 class="text-center">Login</h3>

            <form action="/loginUser" method="post">

                <div class="form-group">
                    <label>Username</label>
                    <input type="text" name="username" class="form-control" required/>
                </div>

                <div class="form-group">
                    <label>Password</label>
                    <input type="password" name="password" class="form-control" required/>
                </div>

                <button class="btn btn-primary btn-block">Login</button>

            </form>

            <div class="text-center mt-3">
                <a href="/signup">Create Account</a>
            </div>

        </div>
    </div>
</div>

<script>
document.addEventListener("DOMContentLoaded", function () {

    var error = "${error}";

    if (error && error.length > 0) {
        toastr.error(error);
    }

});
</script>

</body>
</html>