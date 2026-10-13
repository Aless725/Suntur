<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/fragments/taglibs.jspf" %>
<c:set var="titulo" value="Reportes e informes"/>
<c:set var="nav" value="reportes"/>
<%@ include file="/WEB-INF/views/fragments/header.jspf" %>

<div class="d-flex flex-wrap gap-2 mb-3 no-imprimir">
    <button type="button" class="btn btn-primary" data-imprimir>Imprimir reporte</button>
    <a class="btn btn-outline-primary" href="${ctx}/reportes/matriculas.csv">Exportar matrículas (CSV)</a>
    <c:if test="${usuarioActual.admin}"><a class="btn btn-outline-primary" href="${ctx}/reportes/pagos.csv">Exportar pagos (CSV)</a></c:if>
</div>

<div class="row g-3 mb-4">
    <div class="col-6 col-lg-3"><div class="stat destacado"><div class="valor"><c:out value="${resumen.estudiantes}"/></div><div class="etiqueta">Estudiantes activos</div></div></div>
    <div class="col-6 col-lg-3"><div class="stat"><div class="valor"><c:out value="${resumen.matriculas}"/></div><div class="etiqueta">Matrículas <c:out value="${anioActual}"/></div></div></div>
    <c:if test="${usuarioActual.admin}">
        <div class="col-6 col-lg-3"><div class="stat"><div class="valor">S/ <fmt:formatNumber value="${resumen.pagosCobrados}" pattern="#,##0.00"/></div><div class="etiqueta">Cobrado</div></div></div>
        <div class="col-6 col-lg-3"><div class="stat"><div class="valor">S/ <fmt:formatNumber value="${resumen.pagosPendientes}" pattern="#,##0.00"/></div><div class="etiqueta">Por cobrar</div></div></div>
    </c:if>
</div>

<div class="card mb-4">
    <div class="card-header">Rendimiento por sección</div>
    <div class="table-responsive">
        <table class="table table-hover align-middle">
            <thead><tr><th>Sección</th><th class="text-end">Alumnos</th><th class="text-end">Promedio</th></tr></thead>
            <tbody>
            <c:forEach var="p" items="${promedios}">
                <tr>
                    <td><c:out value="${p.seccion}"/></td>
                    <td class="text-end"><c:out value="${p.alumnos}"/></td>
                    <td class="text-end"><c:choose>
                        <c:when test="${not empty p.promedio}"><fmt:formatNumber value="${p.promedio}" minFractionDigits="1" maxFractionDigits="1"/></c:when>
                        <c:otherwise>&mdash;</c:otherwise></c:choose></td>
                </tr>
            </c:forEach>
            <c:if test="${empty promedios}"><tr><td colspan="3" class="tabla-vacia">Sin datos.</td></tr></c:if>
            </tbody>
        </table>
    </div>
</div>

<div class="card mb-4">
    <div class="card-header">Boletas de notas por estudiante</div>
    <div class="table-responsive">
        <table class="table table-hover align-middle">
            <thead><tr><th>DNI</th><th>Estudiante</th><th>Sección</th><th>Estado</th><th class="text-end no-imprimir">Boleta</th></tr></thead>
            <tbody>
            <c:forEach var="m" items="${matriculas}">
                <tr>
                    <td><c:out value="${m.dni}"/></td>
                    <td><c:out value="${m.estudiante}"/></td>
                    <td><c:out value="${m.seccion}"/></td>
                    <td><span class="etiqueta-estado e-${fn:toLowerCase(m.estado)}"><c:out value="${m.estado}"/></span></td>
                    <td class="text-end no-imprimir"><a class="btn btn-sm btn-outline-primary" href="${ctx}/reportes/boleta/${m.id}">Ver boleta</a></td>
                </tr>
            </c:forEach>
            <c:if test="${empty matriculas}"><tr><td colspan="5" class="tabla-vacia">Sin matrículas.</td></tr></c:if>
            </tbody>
        </table>
    </div>
</div>

<c:if test="${usuarioActual.admin}">
    <div class="card">
        <div class="card-header">Pagos pendientes</div>
        <div class="table-responsive">
            <table class="table table-hover align-middle">
                <thead><tr><th>Estudiante</th><th>Sección</th><th>Actividad</th><th class="text-end">Monto (S/)</th></tr></thead>
                <tbody>
                <c:forEach var="p" items="${pendientes}">
                    <tr>
                        <td><c:out value="${p.estudiante}"/></td>
                        <td><c:out value="${p.seccion}"/></td>
                        <td><c:out value="${p.actividad}"/></td>
                        <td class="text-end"><fmt:formatNumber value="${p.monto}" pattern="#,##0.00"/></td>
                    </tr>
                </c:forEach>
                <c:if test="${empty pendientes}"><tr><td colspan="4" class="tabla-vacia">No hay pagos pendientes.</td></tr></c:if>
                </tbody>
            </table>
        </div>
    </div>
</c:if>

<%@ include file="/WEB-INF/views/fragments/footer.jspf" %>
