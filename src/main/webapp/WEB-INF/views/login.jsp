<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/fragments/taglibs.jspf" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Iniciar sesión | Suntur ERP</title>
    <link rel="stylesheet" href="${ctx}/css/bootstrap.min.css">
    <link rel="stylesheet" href="${ctx}/css/suntur.css">
</head>
<body>
<div class="login-pagina">
    <div class="login-caja">
        <section class="login-lado">
            <h1>SUNTUR ERP</h1>
            <div class="linea"></div>
            <p><strong>I.E. San José Marello N° 1220</strong></p>
            <p>Gestión académica y administrativa: matrículas, calificaciones, asistencias y pagos en un solo lugar.</p>
        </section>
        <section class="login-form">
            <h2 class="h4 mb-3">Iniciar sesión</h2>
            <c:if test="${not empty ok}"><div class="aviso aviso-ok"><c:out value="${ok}"/></div></c:if>
            <c:if test="${not empty error}"><div class="aviso aviso-error" role="alert"><c:out value="${error}"/></div></c:if>
            <form method="post" action="${ctx}/login" autocomplete="on">
                <div class="mb-3">
                    <label class="form-label" for="correo">Correo electrónico</label>
                    <input class="form-control" type="email" id="correo" name="correo" value="<c:out value='${correo}'/>" required autofocus>
                </div>
                <div class="mb-3">
                    <label class="form-label" for="password">Contraseña</label>
                    <input class="form-control" type="password" id="password" name="password" required>
                </div>
                <button class="btn btn-primary w-100" type="submit">Ingresar</button>
            </form>
            <p class="mt-3 mb-0">¿Eres docente y no tienes cuenta? <a href="${ctx}/registro">Regístrate</a></p>
        </section>
    </div>
</div>
</body>
</html>
