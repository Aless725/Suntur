<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/fragments/taglibs.jspf" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Crear cuenta | Suntur ERP</title>
    <link rel="stylesheet" href="${ctx}/css/bootstrap.min.css">
    <link rel="stylesheet" href="${ctx}/css/suntur.css">
</head>
<body>
<div class="login-pagina">
    <div class="login-caja">
        <section class="login-lado">
            <h1>SUNTUR ERP</h1>
            <div class="linea"></div>
            <p><strong>Registro de docentes</strong></p>
            <p>Con tu cuenta podrás registrar calificaciones y asistencias de tus secciones.</p>
        </section>
        <section class="login-form">
            <h2 class="h4 mb-3">Crear cuenta</h2>
            <c:if test="${not empty error}"><div class="aviso aviso-error" role="alert"><c:out value="${error}"/></div></c:if>
            <form method="post" action="${ctx}/registro">
                <div class="row g-2">
                    <div class="col-sm-6 mb-2">
                        <label class="form-label" for="nombres">Nombres</label>
                        <input class="form-control" id="nombres" name="nombres" maxlength="80" value="<c:out value='${nombres}'/>" required>
                    </div>
                    <div class="col-sm-6 mb-2">
                        <label class="form-label" for="apellidos">Apellidos</label>
                        <input class="form-control" id="apellidos" name="apellidos" maxlength="80" value="<c:out value='${apellidos}'/>" required>
                    </div>
                </div>
                <div class="mb-2">
                    <label class="form-label" for="correo">Correo electrónico</label>
                    <input class="form-control" type="email" id="correo" name="correo" maxlength="120" value="<c:out value='${correo}'/>" required>
                </div>
                <div class="mb-2">
                    <label class="form-label" for="password">Contraseña (mínimo 8 caracteres)</label>
                    <input class="form-control" type="password" id="password" name="password" minlength="8" required>
                </div>
                <div class="mb-3">
                    <label class="form-label" for="confirmar">Confirmar contraseña</label>
                    <input class="form-control" type="password" id="confirmar" name="confirmar" minlength="8" required>
                </div>
                <button class="btn btn-primary w-100" type="submit">Crear cuenta</button>
            </form>
            <p class="mt-3 mb-0">¿Ya tienes cuenta? <a href="${ctx}/login">Inicia sesión</a></p>
        </section>
    </div>
</div>
</body>
</html>
