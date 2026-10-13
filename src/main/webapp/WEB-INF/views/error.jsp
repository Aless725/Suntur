<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/fragments/taglibs.jspf" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Error | Suntur ERP</title>
    <link rel="stylesheet" href="${ctx}/css/bootstrap.min.css">
    <link rel="stylesheet" href="${ctx}/css/suntur.css">
</head>
<body>
<div class="login-pagina">
    <div class="login-caja">
        <section class="login-lado">
            <h1><c:out value="${empty status ? 'Error' : status}"/></h1>
            <div class="linea"></div>
            <p>Algo no salió como esperábamos.</p>
        </section>
        <section class="login-form">
            <h2 class="h4 mb-3">No se pudo completar la acción</h2>
            <p><c:out value="${empty mensaje ? 'La página solicitada no está disponible o ocurrió un error inesperado.' : mensaje}"/></p>
            <a class="btn btn-primary" href="${ctx}/dashboard">Volver al tablero</a>
        </section>
    </div>
</div>
</body>
</html>
