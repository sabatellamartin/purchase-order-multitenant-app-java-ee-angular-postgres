package com.app.distrity.util;

public class Constants {

	public static final String LOCALHOST = "127.0.0.1";
	public static final String APPLICATION_URL = "distrity.localhost";
	
	// Authentication & Authorization
	public static final String SALT = "3ntr0py";
    public static final String TOKEN_KEY = "5de03e460e9a971365d3c4b1d5bbd1b04dc17c30547fde6c5b7c1a392af11537";
    public static final String TENANT_HEADER = "X-TenantID"; // Filter header for choose tenant
    public static final String MASTER_TENANT = "master"; // Default tenant
    
    // TIPOS DE USUARIOS
    public static final String ADMINISTRADOR = "ADMINISTRADOR";
    public static final String OPERADOR = "OPERADOR";
    
    // ROLES
    public static final String PROPIETARIO = "PROPIETARIO";
    public static final String ADMINISTRACION = "ADMINISTRACION";
    public static final String VENTAS = "VENTAS";

    // TIPOS DE EMPRESAS
    public static final String DISTRIBUIDOR = "DISTRIBUIDOR";
    public static final String PROVEEDOR = "PROVEEDOR";
    public static final String CLIENTE = "CLIENTE";
    
    // TIPOS DE PERSONAS
    public static final String CONSUMIDOR = "CONSUMIDOR";
    public static final String EMPLEADO = "EMPLEADO";
    public static final String REFERENTE = "REFERENTE";
  
    // ORDENES
    public static final String ORDEN_COMPRA = "ORDEN_COMPRA";
    public static final String ORDEN_VENTA = "ORDEN_VENTA";
    
    // PERSISTANCE UNIT
 	public static final String PERSISTENCE_UNIT_NAME = "DistributionDS";
 	public static final String JTA_DATA_SOURCE = "java:jboss/datasources/DistributionDS";
 	
 	// CONEXION MongoDB
 	public static final String MONGODBHOST = "172.27.1.3";
 	public static final String MONGODBPORT = "27017";
 	public static final String MONGODBNAME = "distrity";

    // ENVIO DE MAIL
    public static final String ACCOUNT_SEND_EMAIL = "sabatellamartin@gmail.com";
    public static final String ACCOUNT_SEND_PASSWORD = "5C;aw&'FHMm[";
    public static final String SMTP_MAIL_PORT = "587";
    public static final String SMTP_MAIL_HOST = "smtp.gmail.com"; //  (Gmail)
    public static final String SMTP_MAIL_AUTH = "true"; // true (Gmail) : Without session
    public static final String SMTP_MAIL_TLS = "true";
    
    public static final String GEOSERVER_ENDPOINT = "http://172.27.1.5:8600/geoserver";  
    public static final String GEOSERVER_USER = "admin";  
    public static final String GEOSERVER_PASS = "geoserver";
    public static final String GEOSERVER_WORKSPACE = "distrity";
    public static final String GEOSERVER_SRS = "EPSG:4326"; //EPSG:3857
    public static final String OSM_NOMINATIM_ENDPOINT = "https://nominatim.openstreetmap.org";
    
}