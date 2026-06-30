<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>ToDo List</title>

<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/toastr.js/latest/toastr.min.css">

<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/toastr.js/latest/toastr.min.js"></script>

</head>

<body>

<div class="container">

<h2 class="mt-4">ToDo List</h2>

<div class="container">
    <div class="row mb-3">
        <div class="col-md-6">
            <form action="/search" method="get" class="form-inline">
                <div class="input-group w-100">
                    <input type="text" name="keyword" class="form-control" placeholder="Search..." value="${param.keyword}">
                    <div class="input-group-append">
                        <button class="btn btn-outline-secondary" type="submit">Search</button>
                    </div>
                </div>
            </form>
        </div>
    </div>
<c:if test="${empty list}">
    <div class="alert alert-info">No tasks found matching "${keyword}"</div>
</c:if>
   <table class="table table-bordered">
        </table>
</div>
<form action="/filterByCategory" method="get" class="form-inline mb-3">
    <select name="categoryId" class="form-control mr-2">
        <option value="">All Categories</option>
        <c:forEach var="cat" items="${categories}">
            <option value="${cat.id}">${cat.name}</option>
        </c:forEach>
    </select>
    <button class="btn btn-info">Filter</button>
</form>

<table class="table table-bordered">
    <tr>
        <th>ID</th>
        <th>Title</th>
        <th>Date</th>
        <th>Priority</th> <th>Status</th>
        <th>Actions</th>
        <th>Category</th>
    </tr>

<c:forEach var="todo" items="${list}">
<tr class="${todo.dueTomorrow && todo.status eq 'Incomplete' ? 'table-warning' : ''}">
<td>${todo.id}</td>
<td>
    ${todo.title}

    <c:if test="${todo.dueTomorrow && todo.status eq 'Incomplete'}">
        <span class="badge badge-warning ml-2">Due Tomorrow</span>
    </c:if>
</td>
<td>${todo.date}</td>
<td>${todo.status}</td>


<td>
    <c:choose>
        <c:when test="${todo.priority == 'High'}">
            <span class="badge badge-danger">High</span>
        </c:when>
        <c:when test="${todo.priority == 'Medium'}">
            <span class="badge badge-primary">Medium</span>
        </c:when>
        <c:otherwise>
            <span class="badge badge-secondary">Low</span>
        </c:otherwise>
    </c:choose>
</td>

<td>
    <a href="/complete/${todo.id}" class="btn btn-success btn-sm">Complete</a>
    <a href="/edit/${todo.id}" class="btn btn-primary btn-sm">Edit</a>
    <a href="/delete/${todo.id}" class="btn btn-danger btn-sm">Delete</a>
</td>
<td>
    <c:choose>
        <c:when test="${todo.category != null}">
            ${todo.category.name}
        </c:when>
        <c:otherwise>
            -
        </c:otherwise>
    </c:choose>
</td>
</tr>
</c:forEach>


</table>

<a href="/addToDoItem" class="btn btn-primary">Add New Task</a>

</div>



<script>
document.addEventListener("DOMContentLoaded", function () {

    var msg = "${message}";

    toastr.options = {
        closeButton: true,
        progressBar: true,
        positionClass: "toast-top-right",
        timeOut: "3000"
    };

    if (msg === "Save Success") toastr.success("Task saved!");
    else if (msg === "Update Success") toastr.success("Task updated!");
    else if (msg === "Delete Success") toastr.success("Task deleted!");
    else if (msg === "Invalid Date") toastr.warning("Past date not allowed!");

    let hasUrgent = false;

    document.querySelectorAll("tr").forEach(row => {
        if (row.innerHTML.includes("Due Tomorrow") &&
            row.innerHTML.includes("Incomplete")) {
            hasUrgent = true;
        }
    });

    if (hasUrgent) {
        toastr.warning("⚠️ You have tasks due tomorrow!");
    }

});
</script>

</body>
</html>