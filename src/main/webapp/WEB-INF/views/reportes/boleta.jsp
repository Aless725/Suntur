<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/fragments/taglibs.jspf" %>
<c:set var="titulo" value="Boleta de notas"/>
<c:set var="nav" value="reportes"/>
<%@ include file="/WEB-INF/views/fragments/header.jspf" %>

<div class="no-imprimir mb-3 d-flex gap-2">
    <button type="button" class="btn btn-primary" data-imprimir>Imprimir boleta</button>
    <a class="btn btn-outline-secondary" href="${ctx}/reportes">Volver</a>
</div>

<div class="boleta-titulo">
    <h2 class="h4 mb-1">I.E. San José Marello N° 1220 - Boleta de notas <c:out value="${boleta.matricula.anio}"/></h2>
    <div><strong>Estudiante:</strong> <c:out value="${boleta.matricula.estudiante}"/> &nbsp;|&nbsp;
         <strong>DNI:</strong> <c:out value="${boleta.matricula.dni}"/> &nbsp;|&nbsp;
         <strong>Sección:</strong> <c:out value="${boleta.matricula.seccion}"/></div>
</div>

<div class="card">
    <div class="table-responsive">
        <table class="table align-middle">
            <thead><tr><th>Curso</th><th class="text-center">Bim. 1</th><th class="text-center">Bim. 2</th><th class="text-center">Bim. 3</th><th class="text-center">Bim. 4</th><th class="text-center">Promedio</th></tr></thead>
            <tbody>
            <c:forEach var="f" items="${boleta.filas}">
                <tr>
                    <td><c:out value="${f.curso}"/></td>
                    <td class="text-center ${not empty f.b1 and f.b1 < 11 ? 'nota-baja' : ''}"><c:out value="${empty f.b1 ? '-' : f.b1}"/></td>
                    <td class="text-center ${not empty f.b2 and f.b2 < 11 ? 'nota-baja' : ''}"><c:out value="${empty f.b2 ? '-' : f.b2}"/></td>
                    <td class="text-center ${not empty f.b3 and f.b3 < 11 ? 'nota-baja' : ''}"><c:out value="${empty f.b3 ? '-' : f.b3}"/></td>
                    <td class="text-center ${not empty f.b4 and f.b4 < 11 ? 'nota-baja' : ''}"><c:out value="${empty f.b4 ? '-' : f.b4}"/></td>
                    <td class="text-center"><strong><c:choose>
                        <c:when test="${not empty f.promedio}"><fmt:formatNumber value="${f.promedio}" minFractionDigits="1" maxFractionDigits="1"/></c:when>
                        <c:otherwise>-</c:otherwise></c:choose></strong></td>
                </tr>
            </c:forEach>
            </tbody>
            <tfoot>
                <tr>
                    <td colspan="5" class="text-end"><strong>Promedio general</strong></td>
                    <td class="text-center"><strong><c:choose>
                        <c:when test="${not empty boleta.promedioGeneral}"><fmt:formatNumber value="${boleta.promedioGeneral}" minFractionDigits="1" maxFractionDigits="1"/></c:when>
                        <c:otherwise>-</c:otherwise></c:choose></strong></td>
                </tr>
            </tfoot>
        </table>
    </div>
</div>
<p class="small mt-2">Escala vigesimal (0-20). Las notas menores a 11 se resaltan en guinda.</p>

<%@ include file="/WEB-INF/views/fragments/footer.jspf" %>
