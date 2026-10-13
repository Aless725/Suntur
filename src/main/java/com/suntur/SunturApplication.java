package com.suntur;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Punto de entrada de Suntur ERP.
 * Al estar en el paquete raíz com.suntur, Spring detecta automáticamente
 * los controladores, la fachada, los DAO y la configuración de los subpaquetes.
 */
@SpringBootApplication
public class SunturApplication {

    public static void main(String[] args) {
        SpringApplication.run(SunturApplication.class, args);
    }
}
