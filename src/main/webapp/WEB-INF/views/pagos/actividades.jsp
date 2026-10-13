<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/fragments/taglibs.jspf" %>
<c:set var="titulo" value="Actividades complementarias"/>
<c:set var="nav" value="actividades"/>
<%@ include file="/WEB-INF/views/fragments/header.jspf" %>

<div class="row g-3 mb-4">
    <div class="col-lg-6">
        <div class="card h-100">
            <div class="card-header">Nueva actividad</div>
            <div class="card-body">
                <form method="post" action="${ctx}/pagos/actividades/guardar">
                    <div class="mb-2">
                        <label class="form-label" for="nombre">Nombre</label>
                        <input class="form-control" id="nombre" name="nombre" maxlength="100" required>
                    </div>
                    <div class="mb-2">
                        <label class="form-label" for="descripcion">Descripción</label>
                        <input class="form-control" id="descripcion" name="descripcion" maxlength="255">
                    </div>
                    <div class="row g-2 mb-3">
                        <div class="col-6">
                            <label class="form-label" for="monto">Monto (S/)</label>
                            <input class="form-control" id="monto" name="monto" inputmode="decimal" pattern="\d+(\.\d{1,2})?" placeholder="0.00" required>
                        </div>
                        <div class="col-6">
                            <label class="form-label" for="fecha">Fecha</label>
                            <input class="form-control" type="date" id="fecha" name="fecha">
                        </div>
                    </div>
                    <button class="btn btn-primary" type="submit">Crear actividad</button>
                </form>
            </div>
        </div>
    </div>
    <div class="col-lg-6">
        <div class="card h-100">
            <div class="card-header">Generar cobros para una sección</div>
            <div class="card-body">
                <form method="post" action="${ctx}/pagos/asignar">
                    <div class="mb-2">
                        <label class="form-label" for="actividadId">Actividad</label>
                        <select class="form-select" id="actividadId" name="actividadId" required>
                            <option value="">Selecciona</option>
                            <c:forEach var="a" items="${actividades}">
                                <option value="${a.id}"><c:out value="${a.nombre}"/> (S/ <fmt:formatNumber value="${a.monto}" pattern="#,##0.00"/>)</option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="mb-3">
                        <label class="form-label" for="seccionId">Sección</label>
                        <select class="form-select" id="seccionId" name="seccionId" required>
                            <option value="">Selecciona</option>
                            <c:forEach var="s" items="${secciones}">
                                <option value="${s.id}"><c:out value="${s.etiqueta}"/></option>
                            </c:forEach>
                        </select>
                    </div>
                    <p class="small">Se crea un cobro pendiente para cada alumno matriculado en <c:out value="${anioActual}"/> que aún no lo tenga.</p>
                    <button class="btn btn-warning" type="submit">Generar cobros</button>
                </form>
            </div>
        </div>
    </div>
</div>

<div class="card">
    <div class="card-header">Actividades registradas</div>
    <div class="table-responsive">
        <table class="table table-hover align-middle">
            <thead><tr><th>Actividad</th><th>Descripción</th><th class="text-end">Monto (S/)</th><th>Fecha</th></tr></thead>
            <tbody>
            <c:forEach var="a" items="${actividades}">
                <tr>
                    <td><c:out value="${a.nombre}"/></td>
                    <td><c:out value="${a.descripcion}"/></td>
                    <td class="text-end"><fmt:formatNumber value="${a.monto}" pattern="#,##0.00"/></td>
                    <td><c:out value="${a.fecha}"/></td>
                </tr>
            </c:forEach>
            <c:if test="${empty actividades}"><tr><td colspan="4" class="tabla-vacia">Aún no hay actividades.</td></tr></c:if>
            </tbody>
        </table>
    </div>
</div>

<%@ include file="/WEB-INF/views/fragments/footer.jspf" %>
