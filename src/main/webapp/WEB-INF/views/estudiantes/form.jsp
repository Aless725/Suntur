<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/fragments/taglibs.jspf" %>
<c:set var="titulo" value="${e.id == 0 ? 'Nuevo estudiante' : 'Editar estudiante'}"/>
<c:set var="nav" value="estudiantes"/>
<%@ include file="/WEB-INF/views/fragments/header.jspf" %>

<div class="card">
    <div class="card-header">Datos del estudiante</div>
    <div class="card-body">
        <form method="post" action="${ctx}/estudiantes/guardar">
            <input type="hidden" name="id" value="${e.id}">
            <div class="row g-3">
                <div class="col-md-3">
                    <label class="form-label" for="dni">DNI</label>
                    <input class="form-control" id="dni" name="dni" value="<c:out value='${e.dni}'/>" pattern="\d{8}" maxlength="8" inputmode="numeric" required>
                </div>
                <div class="col-md-4">
                    <label class="form-label" for="nombres">Nombres</label>
                    <input class="form-control" id="nombres" name="nombres" value="<c:out value='${e.nombres}'/>" maxlength="80" required>
                </div>
                <div class="col-md-5">
                    <label class="form-label" for="apellidos">Apellidos</label>
                    <input class="form-control" id="apellidos" name="apellidos" value="<c:out value='${e.apellidos}'/>" maxlength="80" required>
                </div>
                <div class="col-md-3">
                    <label class="form-label" for="fechaNacimiento">Fecha de nacimiento</label>
                    <input class="form-control" type="date" id="fechaNacimiento" name="fechaNacimiento" value="<c:out value='${e.fechaNacimiento}'/>" required>
                </div>
                <div class="col-md-9">
                    <label class="form-label" for="direccion">Dirección</label>
                    <input class="form-control" id="direccion" name="direccion" value="<c:out value='${e.direccion}'/>" maxlength="150">
                </div>
                <div class="col-md-8">
                    <label class="form-label" for="apoderado">Apoderado</label>
                    <input class="form-control" id="apoderado" name="apoderado" value="<c:out value='${e.apoderado}'/>" maxlength="120">
                </div>
                <div class="col-md-4">
                    <label class="form-label" for="telefonoApoderado">Teléfono del apoderado</label>
                    <input class="form-control" id="telefonoApoderado" name="telefonoApoderado" value="<c:out value='${e.telefonoApoderado}'/>" maxlength="15" inputmode="tel">
                </div>
            </div>
            <div class="mt-4 d-flex gap-2">
                <button class="btn btn-primary" type="submit">Guardar</button>
                <a class="btn btn-outline-secondary" href="${ctx}/estudiantes">Cancelar</a>
            </div>
        </form>
    </div>
</div>

<%@ include file="/WEB-INF/views/fragments/footer.jspf" %>
