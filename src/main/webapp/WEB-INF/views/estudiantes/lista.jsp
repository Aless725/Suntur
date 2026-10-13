<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/fragments/taglibs.jspf" %>
<c:set var="titulo" value="Estudiantes"/>
<c:set var="nav" value="estudiantes"/>
<%@ include file="/WEB-INF/views/fragments/header.jspf" %>

<div class="d-flex flex-wrap gap-2 justify-content-between align-items-center mb-3">
    <form class="d-flex gap-2" method="get" action="${ctx}/estudiantes">
        <input class="form-control" type="search" name="q" value="<c:out value='${q}'/>" placeholder="Buscar por DNI, nombre o apellido" aria-label="Buscar estudiante" style="min-width:260px">
        <button class="btn btn-primary" type="submit">Buscar</button>
        <c:if test="${not empty q}"><a class="btn btn-outline-secondary" href="${ctx}/estudiantes">Limpiar</a></c:if>
    </form>
    <c:if test="${usuarioActual.admin}"><a class="btn btn-warning" href="${ctx}/estudiantes/nuevo">+ Nuevo estudiante</a></c:if>
</div>

<div class="card">
    <div class="table-responsive">
        <table class="table table-hover align-middle">
            <thead>
            <tr><th>DNI</th><th>Apellidos y nombres</th><th>Nacimiento</th><th>Apoderado</th><th>Teléfono</th>
                <c:if test="${usuarioActual.admin}"><th class="text-end">Acciones</th></c:if></tr>
            </thead>
            <tbody>
            <c:forEach var="e" items="${estudiantes}">
                <tr>
                    <td><c:out value="${e.dni}"/></td>
                    <td><c:out value="${e.nombreCompleto}"/></td>
                    <td><c:out value="${e.fechaNacimiento}"/></td>
                    <td><c:out value="${e.apoderado}"/></td>
                    <td><c:out value="${e.telefonoApoderado}"/></td>
                    <c:if test="${usuarioActual.admin}">
                        <td class="text-end text-nowrap">
                            <a class="btn btn-sm btn-outline-primary" href="${ctx}/estudiantes/${e.id}/editar">Editar</a>
                            <form class="d-inline" method="post" action="${ctx}/estudiantes/${e.id}/eliminar" data-confirmar="¿Dar de baja a este estudiante?">
                                <button class="btn btn-sm btn-outline-secondary" type="submit">Dar de baja</button>
                            </form>
                        </td>
                    </c:if>
                </tr>
            </c:forEach>
            <c:if test="${empty estudiantes}"><tr><td colspan="6" class="tabla-vacia">No se encontraron estudiantes.</td></tr></c:if>
            </tbody>
        </table>
    </div>
</div>

<%@ include file="/WEB-INF/views/fragments/footer.jspf" %>
