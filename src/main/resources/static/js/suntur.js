// Suntur ERP - comportamiento mínimo del frontend (sin dependencias adicionales a Bootstrap)
(function () {
    'use strict';

    // Menú lateral en pantallas pequeñas
    var boton = document.getElementById('btnMenu');
    var menu = document.getElementById('menuLateral');
    if (boton && menu) {
        boton.addEventListener('click', function () { menu.classList.toggle('abierto'); });
    }

    // Confirmación en acciones destructivas: <form data-confirmar="¿Seguro?">
    document.querySelectorAll('form[data-confirmar]').forEach(function (f) {
        f.addEventListener('submit', function (e) {
            if (!window.confirm(f.getAttribute('data-confirmar'))) { e.preventDefault(); }
        });
    });

    // Validación de notas en vivo: solo enteros de 0 a 20
    document.querySelectorAll('input.input-nota').forEach(function (inp) {
        inp.addEventListener('input', function () {
            var v = inp.value.replace(/[^0-9]/g, '');
            if (v !== '' && parseInt(v, 10) > 20) { v = '20'; }
            inp.value = v;
        });
    });

    // Marcar a todos como Presente en el registro de asistencia
    var todos = document.getElementById('marcarTodosP');
    if (todos) {
        todos.addEventListener('click', function () {
            document.querySelectorAll('input[type=radio][value=P]').forEach(function (r) { r.checked = true; });
        });
    }

    // Botones de imprimir: <button data-imprimir>
    document.querySelectorAll('[data-imprimir]').forEach(function (b) {
        b.addEventListener('click', function () { window.print(); });
    });
})();
