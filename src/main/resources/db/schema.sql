-- ============================================================
-- Suntur ERP - Esquema de base de datos (MySQL 8 / compatible con H2 modo MySQL)
-- I.E. San José Marello N° 1220
-- Cada sentencia termina con ";" al final de línea. Es idempotente (IF NOT EXISTS).
-- ============================================================

CREATE TABLE IF NOT EXISTS usuario (
    id          INT AUTO_INCREMENT PRIMARY KEY,
    nombres     VARCHAR(80)  NOT NULL,
    apellidos   VARCHAR(80)  NOT NULL,
    correo      VARCHAR(120) NOT NULL UNIQUE,
    password    VARCHAR(100) NOT NULL,
    rol         VARCHAR(20)  NOT NULL DEFAULT 'DOCENTE',
    activo      BOOLEAN      NOT NULL DEFAULT TRUE,
    creado_en   TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT ck_usuario_rol CHECK (rol IN ('ADMIN', 'DOCENTE'))
);

CREATE TABLE IF NOT EXISTS estudiante (
    id                  INT AUTO_INCREMENT PRIMARY KEY,
    dni                 CHAR(8)      NOT NULL UNIQUE,
    nombres             VARCHAR(80)  NOT NULL,
    apellidos           VARCHAR(80)  NOT NULL,
    fecha_nacimiento    DATE         NOT NULL,
    direccion           VARCHAR(150),
    apoderado           VARCHAR(120),
    telefono_apoderado  VARCHAR(15),
    estado              VARCHAR(10)  NOT NULL DEFAULT 'ACTIVO',
    CONSTRAINT ck_estudiante_estado CHECK (estado IN ('ACTIVO', 'INACTIVO'))
);

CREATE TABLE IF NOT EXISTS seccion (
    id      INT AUTO_INCREMENT PRIMARY KEY,
    nivel   VARCHAR(10) NOT NULL,
    grado   INT         NOT NULL,
    nombre  VARCHAR(5)  NOT NULL,
    CONSTRAINT uq_seccion UNIQUE (nivel, grado, nombre),
    CONSTRAINT ck_seccion_nivel CHECK (nivel IN ('Primaria', 'Secundaria')),
    CONSTRAINT ck_seccion_grado CHECK (grado BETWEEN 1 AND 6)
);

CREATE TABLE IF NOT EXISTS curso (
    id      INT AUTO_INCREMENT PRIMARY KEY,
    nombre  VARCHAR(80) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS matricula (
    id             INT AUTO_INCREMENT PRIMARY KEY,
    estudiante_id  INT         NOT NULL,
    seccion_id     INT         NOT NULL,
    anio           INT         NOT NULL,
    fecha          DATE        NOT NULL,
    estado         VARCHAR(10) NOT NULL DEFAULT 'ACTIVA',
    CONSTRAINT fk_matricula_estudiante FOREIGN KEY (estudiante_id) REFERENCES estudiante (id),
    CONSTRAINT fk_matricula_seccion    FOREIGN KEY (seccion_id)    REFERENCES seccion (id),
    CONSTRAINT ck_matricula_estado CHECK (estado IN ('ACTIVA', 'ANULADA'))
);

CREATE TABLE IF NOT EXISTS nota (
    id            INT AUTO_INCREMENT PRIMARY KEY,
    matricula_id  INT NOT NULL,
    curso_id      INT NOT NULL,
    bimestre      INT NOT NULL,
    valor         INT NOT NULL,
    CONSTRAINT fk_nota_matricula FOREIGN KEY (matricula_id) REFERENCES matricula (id),
    CONSTRAINT fk_nota_curso     FOREIGN KEY (curso_id)     REFERENCES curso (id),
    CONSTRAINT uq_nota UNIQUE (matricula_id, curso_id, bimestre),
    CONSTRAINT ck_nota_bimestre CHECK (bimestre BETWEEN 1 AND 4),
    CONSTRAINT ck_nota_valor    CHECK (valor BETWEEN 0 AND 20)
);

CREATE TABLE IF NOT EXISTS asistencia (
    id            INT AUTO_INCREMENT PRIMARY KEY,
    matricula_id  INT      NOT NULL,
    fecha         DATE     NOT NULL,
    estado        CHAR(1)  NOT NULL,
    CONSTRAINT fk_asistencia_matricula FOREIGN KEY (matricula_id) REFERENCES matricula (id),
    CONSTRAINT uq_asistencia UNIQUE (matricula_id, fecha),
    CONSTRAINT ck_asistencia_estado CHECK (estado IN ('P', 'T', 'F'))
);

CREATE TABLE IF NOT EXISTS actividad (
    id           INT AUTO_INCREMENT PRIMARY KEY,
    nombre       VARCHAR(100)  NOT NULL,
    descripcion  VARCHAR(255),
    monto        DECIMAL(10,2) NOT NULL,
    fecha        DATE,
    CONSTRAINT ck_actividad_monto CHECK (monto > 0)
);

CREATE TABLE IF NOT EXISTS pago (
    id            INT AUTO_INCREMENT PRIMARY KEY,
    matricula_id  INT           NOT NULL,
    actividad_id  INT           NOT NULL,
    monto         DECIMAL(10,2) NOT NULL,
    fecha_pago    DATE,
    estado        VARCHAR(10)   NOT NULL DEFAULT 'PENDIENTE',
    CONSTRAINT fk_pago_matricula FOREIGN KEY (matricula_id) REFERENCES matricula (id),
    CONSTRAINT fk_pago_actividad FOREIGN KEY (actividad_id) REFERENCES actividad (id),
    CONSTRAINT uq_pago UNIQUE (matricula_id, actividad_id),
    CONSTRAINT ck_pago_estado CHECK (estado IN ('PENDIENTE', 'PAGADO'))
);
