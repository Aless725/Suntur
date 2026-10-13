package com.suntur.model;

/** Usuario del sistema (tabla usuario). Roles: ADMIN y DOCENTE. */
public class Usuario {
    private int id;
    private String nombres;
    private String apellidos;
    private String correo;
    private String password;
    private String rol;
    private boolean activo;

    public Usuario() {}

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getNombres() { return nombres; }
    public void setNombres(String nombres) { this.nombres = nombres; }
    public String getApellidos() { return apellidos; }
    public void setApellidos(String apellidos) { this.apellidos = apellidos; }
    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getRol() { return rol; }
    public void setRol(String rol) { this.rol = rol; }
    public boolean isActivo() { return activo; }
    public void setActivo(boolean activo) { this.activo = activo; }

    public String getNombreCompleto() { return nombres + " " + apellidos; }
    public boolean isAdmin() { return "ADMIN".equals(rol); }
}
