<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/fragments/taglibs.jspf" %>
<c:set var="titulo" value="Pagos de actividades"/>
<c:set var="nav" value="pagos"/>
<%@ include file="/WEB-INF/views/fragments/header.jspf" %>

<div class="d-flex flex-wrap gap-2 justify-content-between align-items-center mb-3">
    <div class="btn-group" role="group" aria-label="Filtrar por estado">
        <a class="btn ${empty estado ? 'btn-primary' : 'btn-outline-primary'}" href="${ctx}/pagos">Todos</a>
        <a class="btn ${estado == 'PENDIENTE' ? 'btn-primary' : 'btn-outline-primary'}" href="${ctx}/pagos?estado=PENDIENTE">Pendientes</a>
        <a class="btn ${estado == 'PAGADO' ? 'btn-primary' : 'btn-outline-primary'}" href="${ctx}/pagos?estado=PAGADO">Pagados</a>
    </div>
    <a class="btn btn-warning" href="${ctx}/pagos/actividades">Actividades y cobros</a>
</div>

<div class="card">
    <div class="table-responsive">
        <table class="table table-hover align-middle">
            <thead><tr><th>Estudiante</th><th>Sección</th><th>Actividad</th><th class="text-end">Monto (S/)</th><th>Estado</th><th>Fecha de pago</th><th class="text-end">Acción</th></tr></thead>
            <tbody>
            <c:forEach var="p" items="${pagos}">
                <tr>
                    <td><c:out value="${p.estudiante}"/></td>
                    <td><c:out value="${p.seccion}"/></td>
                    <td><c:out value="${p.actividad}"/></td>
                    <td class="text-end"><fmt:formatNumber value="${p.monto}" pattern="#,##0.00"/></td>
                    <td><span class="etiqueta-estado e-${fn:toLowerCase(p.estado)}"><c:out value="${p.estado}"/></span></td>
                    <td><c:out value="${p.fechaPago}"/></td>
                    <td class="text-end">
                        <c:if test="${p.estado == 'PENDIENTE'}">
                            <form class="d-inline" method="post" action="${ctx}/pagos/${p.id}/pagar" data-confirmar="¿Registrar este pago como cobrado?">
                                <input type="hidden" name="estado" value="<c:out value='${estado}'/>">
                                <button class="btn btn-sm btn-primary" type="submit">Registrar pago</button>
                            </form>
                        </c:if>
                    </td>
                </tr>
            </c:forEach>
            <c:if test="${empty pagos}"><tr><td colspan="7" class="tabla-vacia">No hay pagos para mostrar.</td></tr></c:if>
            </tbody>
        </table>
    </div>
</div>

<%@ include file="/WEB-INF/views/fragments/footer.jspf" %>
