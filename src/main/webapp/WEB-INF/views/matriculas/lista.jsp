<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/fragments/taglibs.jspf" %>
<c:set var="titulo" value="Matrículas"/>
<c:set var="nav" value="matriculas"/>
<%@ include file="/WEB-INF/views/fragments/header.jspf" %>

<div class="d-flex flex-wrap gap-2 justify-content-between align-items-center mb-3">
    <form class="d-flex gap-2 align-items-center" method="get" action="${ctx}/matriculas">
        <label class="form-label mb-0" for="anio">Año</label>
        <input class="form-control" style="width:110px" type="number" id="anio" name="anio" value="${anio}" min="2000" max="2100">
        <button class="btn btn-primary" type="submit">Ver</button>
    </form>
    <a class="btn btn-warning" href="${ctx}/matriculas/nueva">+ Nueva matrícula</a>
</div>

<div class="card">
    <div class="table-responsive">
        <table class="table table-hover align-middle">
            <thead><tr><th>N°</th><th>DNI</th><th>Estudiante</th><th>Sección</th><th>Fecha</th><th>Estado</th><th class="text-end">Acciones</th></tr></thead>
            <tbody>
            <c:forEach var="m" items="${matriculas}">
                <tr>
                    <td><c:out value="${m.id}"/></td>
                    <td><c:out value="${m.dni}"/></td>
                    <td><c:out value="${m.estudiante}"/></td>
                    <td><c:out value="${m.seccion}"/></td>
                    <td><c:out value="${m.fecha}"/></td>
                    <td><span class="etiqueta-estado e-${fn:toLowerCase(m.estado)}"><c:out value="${m.estado}"/></span></td>
                    <td class="text-end text-nowrap">
                        <a class="btn btn-sm btn-outline-primary" href="${ctx}/reportes/boleta/${m.id}">Boleta</a>
                        <c:if test="${m.estado == 'ACTIVA'}">
                            <form class="d-inline" method="post" action="${ctx}/matriculas/${m.id}/anular" data-confirmar="¿Anular esta matrícula?">
                                <input type="hidden" name="anio" value="${anio}">
                                <button class="btn btn-sm btn-outline-secondary" type="submit">Anular</button>
                            </form>
                        </c:if>
                    </td>
                </tr>
            </c:forEach>
            <c:if test="${empty matriculas}"><tr><td colspan="7" class="tabla-vacia">No hay matrículas registradas en este año.</td></tr></c:if>
            </tbody>
        </table>
    </div>
</div>

<%@ include file="/WEB-INF/views/fragments/footer.jspf" %>
