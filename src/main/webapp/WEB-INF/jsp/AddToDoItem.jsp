<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Add ToDo</title>

<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/toastr.js/latest/toastr.min.css">

<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/toastr.js/latest/toastr.min.js"></script>
</head>

<body>

<div class="container">
    <h2 class="mt-4">Add ToDo Item</h2>

    <form:form action="/save" method="post" modelAttribute="todo">

            <div class="form-group">
                <label>Title</label>
                <form:input path="title" class="form-control" required="true"/>
            </div>

            <div class="form-group">
                <label>Date and Time</label>
                <input type="datetime-local" name="date" id="date" class="form-control" required/>
            </div>

            <div class="form-group">
                <label for="priority">Priority</label>
                <select name="priority" id="priority" class="form-control">
                    <option value="Low">Low</option>
                    <option value="Medium">Medium</option>
                    <option value="High">High</option>
                </select>
            </div>

            <div class="form-group">
                <label>Status</label>
                <form:input path="status" value="Incomplete" class="form-control"/>
            </div>

            <div class="form-group">
                <label>Category</label>
                <form:input path="categoryName" class="form-control" placeholder="Enter category (e.g. Work, Personal)" required="true"/>
            </div>

            <button class="btn btn-success">Save</button>

        </form:form>


    </div>


<script type="text/javascript">
    document.addEventListener("DOMContentLoaded", function () {
        const dateInput = document.getElementById("date");

        const now = new Date();
        const tzOffset = now.getTimezoneOffset() * 60000;
        const localISOTime = (new Date(now - tzOffset)).toISOString().slice(0, 16);

        dateInput.setAttribute("min", localISOTime);

        dateInput.addEventListener("input", function () {
            if (this.value && this.value < localISOTime) {
                toastr.warning("Past date and time not allowed!");
                this.value = "";
            }
        });
    });
</script>

</body>
</html>