<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/fragments/taglibs.jspf" %>
<c:set var="titulo" value="Registro de asistencia"/>
<c:set var="nav" value="asistencias"/>
<%@ include file="/WEB-INF/views/fragments/header.jspf" %>

<div class="card mb-4">
    <div class="card-body">
        <form class="row g-3 align-items-end" method="get" action="${ctx}/asistencias">
            <div class="col-md-5">
                <label class="form-label" for="seccionId">Sección</label>
                <select class="form-select" id="seccionId" name="seccionId" required>
                    <option value="">Selecciona</option>
                    <c:forEach var="s" items="${secciones}">
                        <option value="${s.id}" ${s.id == seccionId ? 'selected' : ''}><c:out value="${s.etiqueta}"/></option>
                    </c:forEach>
                </select>
            </div>
            <div class="col-md-4">
                <label class="form-label" for="fecha">Fecha</label>
                <input class="form-control" type="date" id="fecha" name="fecha" value="${fecha}" max="${hoy}" required>
            </div>
            <div class="col-md-3"><button class="btn btn-primary w-100" type="submit">Cargar</button></div>
        </form>
    </div>
</div>

<c:if test="${not empty seccionId}">
    <form method="post" action="${ctx}/asistencias/guardar">
        <input type="hidden" name="seccionId" value="${seccionId}">
        <input type="hidden" name="fecha" value="${fecha}">
        <div class="card">
            <div class="card-header d-flex justify-content-between align-items-center">
                <span>Asistencia del <c:out value="${fecha}"/></span>
                <c:if test="${not empty filas}"><button type="button" id="marcarTodosP" class="btn btn-sm btn-warning">Marcar todos Presente</button></c:if>
            </div>
            <div class="table-responsive">
                <table class="table table-hover align-middle">
                    <thead><tr><th style="width:60px">N°</th><th>Estudiante</th><th>P = Presente &nbsp; T = Tardanza &nbsp; F = Falta</th></tr></thead>
                    <tbody>
                    <c:forEach var="f" items="${filas}" varStatus="st">
                        <tr>
                            <td>${st.count}</td>
                            <td><c:out value="${f.estudiante}"/></td>
                            <td>
                                <div class="asis-opciones" role="radiogroup" aria-label="Asistencia de <c:out value='${f.estudiante}'/>">
                                    <input type="radio" id="a${f.matriculaId}P" name="asis_${f.matriculaId}" value="P" ${f.estado == 'P' ? 'checked' : ''}><label for="a${f.matriculaId}P">P</label>
                                    <input type="radio" id="a${f.matriculaId}T" name="asis_${f.matriculaId}" value="T" ${f.estado == 'T' ? 'checked' : ''}><label for="a${f.matriculaId}T">T</label>
                                    <input type="radio" id="a${f.matriculaId}F" name="asis_${f.matriculaId}" value="F" ${f.estado == 'F' ? 'checked' : ''}><label for="a${f.matriculaId}F">F</label>
                                </div>
                            </td>
                        </tr>
                    </c:forEach>
                    <c:if test="${empty filas}"><tr><td colspan="3" class="tabla-vacia">No hay alumnos matriculados en esta sección.</td></tr></c:if>
                    </tbody>
                </table>
            </div>
        </div>
        <c:if test="${not empty filas}"><button class="btn btn-primary mt-3" type="submit">Guardar asistencia</button></c:if>
    </form>
</c:if>

<%@ include file="/WEB-INF/views/fragments/footer.jspf" %>
