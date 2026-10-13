<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/fragments/taglibs.jspf" %>
<c:set var="titulo" value="Nueva matrícula"/>
<c:set var="nav" value="matriculas"/>
<%@ include file="/WEB-INF/views/fragments/header.jspf" %>

<div class="card" style="max-width:640px">
    <div class="card-header">Registrar matrícula</div>
    <div class="card-body">
        <form method="post" action="${ctx}/matriculas/guardar">
            <div class="mb-3">
                <label class="form-label" for="estudianteId">Estudiante</label>
                <select class="form-select" id="estudianteId" name="estudianteId" required>
                    <option value="">Selecciona un estudiante</option>
                    <c:forEach var="e" items="${estudiantes}">
                        <option value="${e.id}"><c:out value="${e.dni} - ${e.nombreCompleto}"/></option>
                    </c:forEach>
                </select>
            </div>
            <div class="mb-3">
                <label class="form-label" for="seccionId">Sección</label>
                <select class="form-select" id="seccionId" name="seccionId" required>
                    <option value="">Selecciona una sección</option>
                    <c:forEach var="s" items="${secciones}">
                        <option value="${s.id}"><c:out value="${s.etiqueta}"/></option>
                    </c:forEach>
                </select>
            </div>
            <div class="mb-4">
                <label class="form-label" for="anio">Año académico</label>
                <input class="form-control" type="number" id="anio" name="anio" value="${anioActual}" min="2000" max="2100" required>
            </div>
            <div class="d-flex gap-2">
                <button class="btn btn-primary" type="submit">Matricular</button>
                <a class="btn btn-outline-secondary" href="${ctx}/matriculas">Cancelar</a>
            </div>
        </form>
    </div>
</div>

<%@ include file="/WEB-INF/views/fragments/footer.jspf" %>
