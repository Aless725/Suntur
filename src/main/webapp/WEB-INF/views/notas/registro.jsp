<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/fragments/taglibs.jspf" %>
<c:set var="titulo" value="Registro de calificaciones"/>
<c:set var="nav" value="notas"/>
<%@ include file="/WEB-INF/views/fragments/header.jspf" %>

<div class="card mb-4">
    <div class="card-body">
        <form class="row g-3 align-items-end" method="get" action="${ctx}/notas">
            <div class="col-md-4">
                <label class="form-label" for="seccionId">Sección</label>
                <select class="form-select" id="seccionId" name="seccionId" required>
                    <option value="">Selecciona</option>
                    <c:forEach var="s" items="${secciones}">
                        <option value="${s.id}" ${s.id == seccionId ? 'selected' : ''}><c:out value="${s.etiqueta}"/></option>
                    </c:forEach>
                </select>
            </div>
            <div class="col-md-4">
                <label class="form-label" for="cursoId">Curso</label>
                <select class="form-select" id="cursoId" name="cursoId" required>
                    <option value="">Selecciona</option>
                    <c:forEach var="c" items="${cursos}">
                        <option value="${c.id}" ${c.id == cursoId ? 'selected' : ''}><c:out value="${c.nombre}"/></option>
                    </c:forEach>
                </select>
            </div>
            <div class="col-md-2">
                <label class="form-label" for="bimestre">Bimestre</label>
                <select class="form-select" id="bimestre" name="bimestre">
                    <c:forEach var="b" begin="1" end="4">
                        <option value="${b}" ${b == bimestre ? 'selected' : ''}>Bimestre ${b}</option>
                    </c:forEach>
                </select>
            </div>
            <div class="col-md-2"><button class="btn btn-primary w-100" type="submit">Cargar</button></div>
        </form>
    </div>
</div>

<c:if test="${not empty seccionId and not empty cursoId}">
    <form method="post" action="${ctx}/notas/guardar">
        <input type="hidden" name="seccionId" value="${seccionId}">
        <input type="hidden" name="cursoId" value="${cursoId}">
        <input type="hidden" name="bimestre" value="${bimestre}">
        <div class="card">
            <div class="table-responsive">
                <table class="table table-hover align-middle">
                    <thead><tr><th style="width:60px">N°</th><th>Estudiante</th><th style="width:140px">Nota (0-20)</th></tr></thead>
                    <tbody>
                    <c:forEach var="f" items="${filas}" varStatus="st">
                        <tr>
                            <td>${st.count}</td>
                            <td><c:out value="${f.estudiante}"/></td>
                            <td><input class="form-control input-nota" type="text" inputmode="numeric" maxlength="2"
                                       name="nota_${f.matriculaId}" value="<c:out value='${f.valor}'/>" aria-label="Nota de <c:out value='${f.estudiante}'/>"></td>
                        </tr>
                    </c:forEach>
                    <c:if test="${empty filas}"><tr><td colspan="3" class="tabla-vacia">No hay alumnos matriculados en esta sección.</td></tr></c:if>
                    </tbody>
                </table>
            </div>
        </div>
        <c:if test="${not empty filas}"><button class="btn btn-primary mt-3" type="submit">Guardar notas</button></c:if>
    </form>
</c:if>

<%@ include file="/WEB-INF/views/fragments/footer.jspf" %>
