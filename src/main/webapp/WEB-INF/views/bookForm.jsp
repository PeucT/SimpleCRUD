<%--suppress XmlDuplicatedId --%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="highlight" uri="http://www.springframework.org/tags" %>
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
    <title>User creation form</title>
    <link rel="stylesheet"
          href="/webjars/bootstrap/3.3.7/css/bootstrap.min.css" />
    <link rel="stylesheet"
          href="/static/css/custom.css" />
</head>
<body>
<div class="container-fluid">
    <div class="container">
        <div class="row">
            <div class="col-md-8">
                <c:if test="${book.id == null}">
                    <h1><p class="text-left">Добавление новой книги</p></h1>
                </c:if>
                <c:if test="${book.id != null}">
                    <h1><p class="text-left">Редактирование книги</p></h1>
                </c:if>
            </div>
        </div>
    </div>
    <div class="container">
        <div class="row">
            <div class="col-md-8">
                <spring:form method="POST" modelAttribute="book" action="/crud/bookManager/save">
                    <table class="table table-bordered">
                        <spring:input path="id" type="number" hidden="true" />
                        <tr>
                            <td>Наименование книги</td>
                            <td>
                                <highlight:bind path="title">
                                    <div class="form-group ${status.error ? 'has-error' : ''}">
                                        <spring:input path="title" type="text" class="form-control" />
                                        <spring:errors path="title"></spring:errors>
                                    </div>
                                </highlight:bind>
                            </td>
                        </tr>
                        <tr>
                            <td>Автор</td>
                            <td>
                                <c:if test="${book.id == null || book.id == 0}">
                                    <highlight:bind path="author">
                                        <div class="form-group ${status.error ? 'has-error' : ''}">
                                            <spring:input path="author" type="text" class="form-control"/>
                                            <spring:errors path="author"></spring:errors>
                                        </div>
                                    </highlight:bind>
                                </c:if>
                                <c:if test="${book.id != null && book.id != 0}">
                                    <spring:input path="author" readonly="true" type="text" class="form-control" />
                                </c:if>

                            </td>
                        </tr>
                        <tr>
                            <td>Краткое описание</td>
                            <td>
                                <highlight:bind path="description">
                                    <div class="form-group ${status.error ? 'has-error' : ''}">
                                        <spring:textarea path="description" type="text" class="form-control" />
                                        <spring:errors path="description"></spring:errors>
                                    </div>
                                </highlight:bind>
                            </td>
                        </tr>
                        <tr>
                            <td>ISBN</td>
                            <td>
                                <highlight:bind path="isbn">
                                    <div class="form-group ${status.error ? 'has-error' : ''}">
                                        <spring:input path="isbn" type="text" class="form-control" />
                                        <spring:errors path="isbn"></spring:errors>
                                    </div>
                                </highlight:bind>
                            </td>
                        </tr>
                        <tr>
                            <td>Год печати</td>
                            <td>
                                <highlight:bind path="printYear">
                                    <div class="form-group ${status.error ? 'has-error' : ''}">
                                        <spring:input path="printYear" type="number" class="form-control" />
                                        <spring:errors path="printYear"></spring:errors>
                                    </div>
                                </highlight:bind>
                            </td>
                        </tr>
                        <c:if test="${book.id == null || book.id == 0}">
                            <tr>
                                <td>Статус прочтения</td>
                                <td>
                                    <spring:checkbox path="readAlready"/>
                                </td>
                            </tr>
                        </c:if>
                        <c:if test="${book.id > 0}">
                            <spring:checkbox path="readAlready" hidden="true"/>
                        </c:if>
                    </table>
                    <button type="submit" class="btn bg-primary form-float-left">Save changes</button>
                </spring:form>
                <form action="/crud">
                    <button type="submit" class="btn bg-primary">Cancel</button>
                </form>

            </div>
        </div>
    </div>
</div>
<script src="/webjars/jquery/3.2.1/jquery.min.js"></script>
<script src="/webjars/bootstrap/3.3.7/js/bootstrap.min.js"></script>
</body>
</html>
