<%@ taglib prefix="spring" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%--
  Created by IntelliJ IDEA.
  User: PeucT
  Date: 15.11.2017
  Time: 3:17
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>CRUD application</title>
  <link rel="stylesheet"
        href="/webjars/bootstrap/3.3.7/css/bootstrap.min.css" />
  <link rel="stylesheet"
        href="/static/css/custom.css" />
</head>

<body>
<div class="container-fluid">
  <div class="container">
    <div class="row">
      <div class="col-md-12">
        <h1><p class="text-center">Welcome to the CRUD appication</p></h1>
      </div>
    </div>
  </div>


  <div class="container">
    <div class="row">
      <div class="col-md-12">
        <form action="crud/addBook">
          <button type="submit" class="btn bg-primary">
            <span class="glyphicon glyphicon-plus"></span>
            Add Book
          </button>
        </form>
      </div>
    </div>
  </div>
  <div class="container">
    <div class="row">
      <div class="col-md-12">

        <spring:form method="post" modelAttribute="filter" action="crud" class="form-inline form-float-left">

          <div class="form-group">
            <label for="id">Filter by:</label>
            <spring:input path="id" type="number" class="form-control" id="id" placeholder="Id" />
          </div>
          <div class="form-group">
            <spring:input path="title" type="text" class="form-control" id="title" placeholder="Наименование" />
          </div>
          <div class="form-group">
            <spring:input path="author" type="text" class="form-control" id="author" placeholder="Автор"/>
          </div>
          <div class="form-group">
            <spring:input path="printYear" type="text" class="form-control" id="year" placeholder="Год печати"/>
          </div>

          <spring:select class="form-control" path="readAlready" items="${readFilterList}" placeholder="Статус прочтения" />

          <c:if test="${filterStatus == false}">
            <button type="submit" class="btn btn-primary">Apply filter</button>
          </c:if>
          <c:if test="${filterStatus == true}">
            <button type="submit" class="btn btn-danger">Apply filter</button>
          </c:if>
        </spring:form>
        <c:if test="${filterStatus == true}">
          <form action="/">
            <button type="submit" class="btn btn-primary">Drop filter</button>
          </form>
        </c:if>
      </div>
    </div>
  </div>

  <div class="container">
    <div class="row">
      <div class="col-md-12">
        <div class="dropdown">
          <span>Выводить по </span>
          <button class="btn btn-default dropdown-toggle" type="button" id="dropdownMenu1" data-toggle="dropdown" aria-haspopup="true" aria-expanded="true">
            ${pageSize}
            <span class="caret"></span>
          </button>
          <ul class="dropdown-menu" aria-labelledby="dropdownMenu1">
            <li><a class="dropdown-item" href="/crud?psize=5${filterURL}">5</a></li>
            <li><a class="dropdown-item" href="/crud?psize=10${filterURL}">10</a></li>
            <li><a class="dropdown-item" href="/crud?psize=15${filterURL}">15</a></li>
          </ul>
          <span> записей</span>
        </div>
      </div>
    </div>
  </div>
  <div class="container">
    <div class="row">
      <div class="col-md-12 <%--froze-table--%>">
        <table class="table table-bordered">
          <tr>
            <td>ID</td>
            <td>Наименование</td>
            <td>Автор</td>
            <td>Краткое описание</td>
            <td>ISBN</td>
            <td>Год печати</td>
            <td>Прочитано / Не прочитано <br><span class="small-text">(нажмите для изменения статуса)</span></td>
            <td></td>
            <td></td>
          </tr>
          <c:forEach var="entry" items="${data}">


            <tr>
              <td>${entry.id}</td>
              <td>${entry.title}</td>
              <td>${entry.author}</td>
              <td>${entry.description}</td>
              <td>${entry.isbn}</td>
              <td>${entry.printYear}</td>

              <td>
                <c:if test="${entry.readAlready == true}">
                    <a class="btn btn-default disabled opacity-full" href="#" title="Прочитано" role="button">
                        <span class="glyphicon glyphicon-check" />
                    </a>
                </c:if>
                <c:if test="${entry.readAlready == false}">
                    <a class="btn btn-default" href="/crud/setReadTrue/${entry.id}" title="Добавить к прочитанным" role="button">
                        <span class="glyphicon glyphicon-unchecked"></span>
                    </a>
                </c:if>
              </td>
              <td class="text-center">
                <a class="btn btn-success" href="/crud/edit/${entry.id}" role="button">Edit</a>
              </td>
              <td class="text-center">
                <a class="btn btn-danger" href="/crud/delete/${entry.id}" role="button">Delete</a>
              </td>
            </tr>

          </c:forEach>
        </table>
      </div>
    </div>
  </div>

  <%@include file="JSPF/navigation.jspf" %>
</div>
<script src="/webjars/jquery/3.2.1/jquery.min.js"></script>
<script src="/webjars/bootstrap/3.3.7/js/bootstrap.min.js"></script>
</body>
</html>
