<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/fragments/taglibs.jspf" %>
<c:set var="titulo" value="Tablero"/>
<c:set var="nav" value="dashboard"/>
<%@ include file="/WEB-INF/views/fragments/header.jspf" %>

<div class="row g-3 mb-4">
    <div class="col-6 col-lg-3">
        <div class="stat destacado"><div class="valor"><c:out value="${resumen.estudiantes}"/></div><div class="etiqueta">Estudiantes activos</div></div>
    </div>
    <div class="col-6 col-lg-3">
        <div class="stat"><div class="valor"><c:out value="${resumen.matriculas}"/></div><div class="etiqueta">Matrículas <c:out value="${anioActual}"/></div></div>
    </div>
    <c:if test="${usuarioActual.admin}">
        <div class="col-6 col-lg-3">
            <div class="stat"><div class="valor">S/ <fmt:formatNumber value="${resumen.pagosCobrados}" pattern="#,##0.00"/></div><div class="etiqueta">Cobrado en actividades</div></div>
        </div>
        <div class="col-6 col-lg-3">
            <div class="stat"><div class="valor">S/ <fmt:formatNumber value="${resumen.pagosPendientes}" pattern="#,##0.00"/></div>
                <div class="etiqueta">Por cobrar (<c:out value="${resumen.pagosPendientesCantidad}"/> pagos)</div></div>
        </div>
    </c:if>
</div>

<div class="row g-3">
    <div class="col-lg-5">
        <div class="card h-100">
            <div class="card-header">Asistencia de hoy</div>
            <div class="card-body">
                <div class="row text-center g-2">
                    <div class="col-4"><div class="stat"><div class="valor"><c:out value="${resumen.presentesHoy}"/></div><div class="etiqueta">Presentes</div></div></div>
                    <div class="col-4"><div class="stat"><div class="valor"><c:out value="${resumen.tardanzasHoy}"/></div><div class="etiqueta">Tardanzas</div></div></div>
                    <div class="col-4"><div class="stat"><div class="valor"><c:out value="${resumen.faltasHoy}"/></div><div class="etiqueta">Faltas</div></div></div>
                </div>
                <a class="btn btn-warning mt-3" href="${ctx}/asistencias">Registrar asistencia</a>
            </div>
        </div>
    </div>
    <div class="col-lg-7">
        <div class="card h-100">
            <div class="card-header">Promedio de notas por sección</div>
            <div class="table-responsive">
                <table class="table table-hover align-middle">
                    <thead><tr><th>Sección</th><th class="text-end">Alumnos</th><th class="text-end">Promedio</th></tr></thead>
                    <tbody>
                    <c:forEach var="p" items="${promedios}">
                        <tr>
                            <td><c:out value="${p.seccion}"/></td>
                            <td class="text-end"><c:out value="${p.alumnos}"/></td>
                            <td class="text-end">
                                <c:choose>
                                    <c:when test="${not empty p.promedio}"><fmt:formatNumber value="${p.promedio}" minFractionDigits="1" maxFractionDigits="1"/></c:when>
                                    <c:otherwise>&mdash;</c:otherwise>
                                </c:choose>
                            </td>
                        </tr>
                    </c:forEach>
                    <c:if test="${empty promedios}"><tr><td colspan="3" class="tabla-vacia">Aún no hay matrículas este año.</td></tr></c:if>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>

<%@ include file="/WEB-INF/views/fragments/footer.jspf" %>
