--
-- PostgreSQL database dump
--

-- Dumped from database version 10.6
-- Dumped by pg_dump version 10.5

-- Started on 2018-12-15 06:11:41 -03

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET client_min_messages = warning;
SET row_security = off;

--
-- TOC entry 5 (class 2615 OID 24577)
-- Name: gis; Type: SCHEMA; Schema: -; Owner: docker
--

CREATE SCHEMA gis;


ALTER SCHEMA gis OWNER TO docker;

--
-- TOC entry 11 (class 2615 OID 26134)
-- Name: master; Type: SCHEMA; Schema: -; Owner: docker
--

CREATE SCHEMA master;


ALTER SCHEMA master OWNER TO docker;

--
-- TOC entry 9 (class 2615 OID 26135)
-- Name: slave; Type: SCHEMA; Schema: -; Owner: docker
--

CREATE SCHEMA slave;


ALTER SCHEMA slave OWNER TO docker;

--
-- TOC entry 1 (class 3079 OID 13001)
-- Name: plpgsql; Type: EXTENSION; Schema: -; Owner: 
--

CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;


--
-- TOC entry 4596 (class 0 OID 0)
-- Dependencies: 1
-- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


--
-- TOC entry 2 (class 3079 OID 24578)
-- Name: postgis; Type: EXTENSION; Schema: -; Owner: 
--

CREATE EXTENSION IF NOT EXISTS postgis WITH SCHEMA gis;


--
-- TOC entry 4597 (class 0 OID 0)
-- Dependencies: 2
-- Name: EXTENSION postgis; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION postgis IS 'PostGIS geometry, geography, and raster spatial types and functions';


SET default_tablespace = '';

SET default_with_oids = false;

--
-- TOC entry 216 (class 1259 OID 26106)
-- Name: locations; Type: TABLE; Schema: gis; Owner: docker
--

CREATE TABLE gis.locations (
    id integer NOT NULL,
    geometry gis.geometry(Point)
);


ALTER TABLE gis.locations OWNER TO docker;

--
-- TOC entry 215 (class 1259 OID 26104)
-- Name: locations_id_seq; Type: SEQUENCE; Schema: gis; Owner: docker
--

CREATE SEQUENCE gis.locations_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE gis.locations_id_seq OWNER TO docker;

--
-- TOC entry 4598 (class 0 OID 0)
-- Dependencies: 215
-- Name: locations_id_seq; Type: SEQUENCE OWNED BY; Schema: gis; Owner: docker
--

ALTER SEQUENCE gis.locations_id_seq OWNED BY gis.locations.id;


--
-- TOC entry 218 (class 1259 OID 26117)
-- Name: zones; Type: TABLE; Schema: gis; Owner: docker
--

CREATE TABLE gis.zones (
    id integer NOT NULL,
    geometry gis.geometry(Polygon)
);


ALTER TABLE gis.zones OWNER TO docker;

--
-- TOC entry 217 (class 1259 OID 26115)
-- Name: zones_id_seq; Type: SEQUENCE; Schema: gis; Owner: docker
--

CREATE SEQUENCE gis.zones_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE gis.zones_id_seq OWNER TO docker;

--
-- TOC entry 4599 (class 0 OID 0)
-- Dependencies: 217
-- Name: zones_id_seq; Type: SEQUENCE OWNED BY; Schema: gis; Owner: docker
--

ALTER SEQUENCE gis.zones_id_seq OWNED BY gis.zones.id;


--
-- TOC entry 220 (class 1259 OID 26138)
-- Name: tenants; Type: TABLE; Schema: master; Owner: docker
--

CREATE TABLE master.tenants (
    id bigint NOT NULL,
    alta timestamp without time zone NOT NULL,
    baja timestamp without time zone,
    nombre character varying(255)
);


ALTER TABLE master.tenants OWNER TO docker;

--
-- TOC entry 219 (class 1259 OID 26136)
-- Name: tenants_id_seq; Type: SEQUENCE; Schema: master; Owner: docker
--

CREATE SEQUENCE master.tenants_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE master.tenants_id_seq OWNER TO docker;

--
-- TOC entry 4600 (class 0 OID 0)
-- Dependencies: 219
-- Name: tenants_id_seq; Type: SEQUENCE OWNED BY; Schema: master; Owner: docker
--

ALTER SEQUENCE master.tenants_id_seq OWNED BY master.tenants.id;


--
-- TOC entry 222 (class 1259 OID 26148)
-- Name: usuarios; Type: TABLE; Schema: master; Owner: docker
--

CREATE TABLE master.usuarios (
    usuario_tipo character varying(31) NOT NULL,
    id bigint NOT NULL,
    alta timestamp without time zone,
    baja timestamp without time zone,
    bloqueado timestamp without time zone,
    email character varying(255),
    password character varying(255),
    username character varying(255),
    rol character varying(255),
    tenant_id bigint
);


ALTER TABLE master.usuarios OWNER TO docker;

--
-- TOC entry 221 (class 1259 OID 26146)
-- Name: usuarios_id_seq; Type: SEQUENCE; Schema: master; Owner: docker
--

CREATE SEQUENCE master.usuarios_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE master.usuarios_id_seq OWNER TO docker;

--
-- TOC entry 4601 (class 0 OID 0)
-- Dependencies: 221
-- Name: usuarios_id_seq; Type: SEQUENCE OWNED BY; Schema: master; Owner: docker
--

ALTER SEQUENCE master.usuarios_id_seq OWNED BY master.usuarios.id;


--
-- TOC entry 224 (class 1259 OID 26166)
-- Name: almacenes; Type: TABLE; Schema: slave; Owner: docker
--

CREATE TABLE slave.almacenes (
    id bigint NOT NULL,
    codigo character varying(255),
    nombre character varying(255),
    direccion_id bigint,
    local_id bigint
);


ALTER TABLE slave.almacenes OWNER TO docker;

--
-- TOC entry 223 (class 1259 OID 26164)
-- Name: almacenes_id_seq; Type: SEQUENCE; Schema: slave; Owner: docker
--

CREATE SEQUENCE slave.almacenes_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE slave.almacenes_id_seq OWNER TO docker;

--
-- TOC entry 4602 (class 0 OID 0)
-- Dependencies: 223
-- Name: almacenes_id_seq; Type: SEQUENCE OWNED BY; Schema: slave; Owner: docker
--

ALTER SEQUENCE slave.almacenes_id_seq OWNED BY slave.almacenes.id;


--
-- TOC entry 226 (class 1259 OID 26177)
-- Name: articulos; Type: TABLE; Schema: slave; Owner: docker
--

CREATE TABLE slave.articulos (
    id bigint NOT NULL,
    codigo character varying(20),
    codigo_barra character varying(32),
    descripcion character varying(255),
    fecha_alta timestamp without time zone,
    fecha_baja timestamp without time zone,
    nombre character varying(255),
    observaciones character varying(1000),
    porcentaje_descuento double precision,
    precio_base double precision,
    precio_compra double precision,
    precio_venta double precision,
    unidad_id bigint NOT NULL
);


ALTER TABLE slave.articulos OWNER TO docker;

--
-- TOC entry 225 (class 1259 OID 26175)
-- Name: articulos_id_seq; Type: SEQUENCE; Schema: slave; Owner: docker
--

CREATE SEQUENCE slave.articulos_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE slave.articulos_id_seq OWNER TO docker;

--
-- TOC entry 4603 (class 0 OID 0)
-- Dependencies: 225
-- Name: articulos_id_seq; Type: SEQUENCE OWNED BY; Schema: slave; Owner: docker
--

ALTER SEQUENCE slave.articulos_id_seq OWNED BY slave.articulos.id;


--
-- TOC entry 228 (class 1259 OID 26188)
-- Name: calificaciones; Type: TABLE; Schema: slave; Owner: docker
--

CREATE TABLE slave.calificaciones (
    id bigint NOT NULL,
    codigo character varying(255),
    descripcion character varying(255)
);


ALTER TABLE slave.calificaciones OWNER TO docker;

--
-- TOC entry 227 (class 1259 OID 26186)
-- Name: calificaciones_id_seq; Type: SEQUENCE; Schema: slave; Owner: docker
--

CREATE SEQUENCE slave.calificaciones_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE slave.calificaciones_id_seq OWNER TO docker;

--
-- TOC entry 4604 (class 0 OID 0)
-- Dependencies: 227
-- Name: calificaciones_id_seq; Type: SEQUENCE OWNED BY; Schema: slave; Owner: docker
--

ALTER SEQUENCE slave.calificaciones_id_seq OWNED BY slave.calificaciones.id;


--
-- TOC entry 230 (class 1259 OID 26199)
-- Name: detalles; Type: TABLE; Schema: slave; Owner: docker
--

CREATE TABLE slave.detalles (
    id bigint NOT NULL,
    cantidad integer,
    descuento double precision,
    precio double precision,
    articulo_id bigint NOT NULL,
    documento_id bigint NOT NULL
);


ALTER TABLE slave.detalles OWNER TO docker;

--
-- TOC entry 229 (class 1259 OID 26197)
-- Name: detalles_id_seq; Type: SEQUENCE; Schema: slave; Owner: docker
--

CREATE SEQUENCE slave.detalles_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE slave.detalles_id_seq OWNER TO docker;

--
-- TOC entry 4605 (class 0 OID 0)
-- Dependencies: 229
-- Name: detalles_id_seq; Type: SEQUENCE OWNED BY; Schema: slave; Owner: docker
--

ALTER SEQUENCE slave.detalles_id_seq OWNED BY slave.detalles.id;


--
-- TOC entry 232 (class 1259 OID 26207)
-- Name: direccion; Type: TABLE; Schema: slave; Owner: docker
--

CREATE TABLE slave.direccion (
    id bigint NOT NULL,
    calle character varying(255),
    locacion_id character varying(255),
    numero character varying(255),
    observacion character varying(255),
    tipo_direccion_id bigint
);


ALTER TABLE slave.direccion OWNER TO docker;

--
-- TOC entry 231 (class 1259 OID 26205)
-- Name: direccion_id_seq; Type: SEQUENCE; Schema: slave; Owner: docker
--

CREATE SEQUENCE slave.direccion_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE slave.direccion_id_seq OWNER TO docker;

--
-- TOC entry 4606 (class 0 OID 0)
-- Dependencies: 231
-- Name: direccion_id_seq; Type: SEQUENCE OWNED BY; Schema: slave; Owner: docker
--

ALTER SEQUENCE slave.direccion_id_seq OWNED BY slave.direccion.id;


--
-- TOC entry 234 (class 1259 OID 26218)
-- Name: documentos; Type: TABLE; Schema: slave; Owner: docker
--

CREATE TABLE slave.documentos (
    documento_tipo character varying(31) NOT NULL,
    id bigint NOT NULL,
    fecha timestamp without time zone,
    numero_documento character varying(15),
    vencimiento timestamp without time zone,
    local_id bigint,
    moneda_id bigint NOT NULL,
    usuario_id bigint NOT NULL,
    proveedor_id bigint,
    cliente_id bigint
);


ALTER TABLE slave.documentos OWNER TO docker;

--
-- TOC entry 233 (class 1259 OID 26216)
-- Name: documentos_id_seq; Type: SEQUENCE; Schema: slave; Owner: docker
--

CREATE SEQUENCE slave.documentos_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE slave.documentos_id_seq OWNER TO docker;

--
-- TOC entry 4607 (class 0 OID 0)
-- Dependencies: 233
-- Name: documentos_id_seq; Type: SEQUENCE OWNED BY; Schema: slave; Owner: docker
--

ALTER SEQUENCE slave.documentos_id_seq OWNED BY slave.documentos.id;


--
-- TOC entry 236 (class 1259 OID 26226)
-- Name: empresas; Type: TABLE; Schema: slave; Owner: docker
--

CREATE TABLE slave.empresas (
    empresa_tipo character varying(31) NOT NULL,
    id bigint NOT NULL,
    alta timestamp without time zone,
    baja timestamp without time zone,
    email character varying(255),
    nombre_comercial character varying(255),
    razon_social character varying(255),
    rubro character varying(255),
    rut character varying(255),
    telefono character varying(255),
    web character varying(255),
    calificacion_id bigint,
    direccion_id bigint,
    tipo_empresa_id bigint,
    central_id bigint,
    tenant_id bigint
);


ALTER TABLE slave.empresas OWNER TO docker;

--
-- TOC entry 235 (class 1259 OID 26224)
-- Name: empresas_id_seq; Type: SEQUENCE; Schema: slave; Owner: docker
--

CREATE SEQUENCE slave.empresas_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE slave.empresas_id_seq OWNER TO docker;

--
-- TOC entry 4608 (class 0 OID 0)
-- Dependencies: 235
-- Name: empresas_id_seq; Type: SEQUENCE OWNED BY; Schema: slave; Owner: docker
--

ALTER SEQUENCE slave.empresas_id_seq OWNED BY slave.empresas.id;


--
-- TOC entry 238 (class 1259 OID 26237)
-- Name: locales; Type: TABLE; Schema: slave; Owner: docker
--

CREATE TABLE slave.locales (
    id bigint NOT NULL,
    codigo character varying(255),
    nombre character varying(255),
    direccion_id bigint,
    empresa_id bigint,
    zona_id bigint
);


ALTER TABLE slave.locales OWNER TO docker;

--
-- TOC entry 237 (class 1259 OID 26235)
-- Name: locales_id_seq; Type: SEQUENCE; Schema: slave; Owner: docker
--

CREATE SEQUENCE slave.locales_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE slave.locales_id_seq OWNER TO docker;

--
-- TOC entry 4609 (class 0 OID 0)
-- Dependencies: 237
-- Name: locales_id_seq; Type: SEQUENCE OWNED BY; Schema: slave; Owner: docker
--

ALTER SEQUENCE slave.locales_id_seq OWNED BY slave.locales.id;


--
-- TOC entry 240 (class 1259 OID 26248)
-- Name: monedas; Type: TABLE; Schema: slave; Owner: docker
--

CREATE TABLE slave.monedas (
    id bigint NOT NULL,
    codigo character varying(255),
    codigo_dgi character varying(3),
    descripcion character varying(255),
    flag_url character varying(255),
    orden integer,
    sigla character varying(5)
);


ALTER TABLE slave.monedas OWNER TO docker;

--
-- TOC entry 239 (class 1259 OID 26246)
-- Name: monedas_id_seq; Type: SEQUENCE; Schema: slave; Owner: docker
--

CREATE SEQUENCE slave.monedas_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE slave.monedas_id_seq OWNER TO docker;

--
-- TOC entry 4610 (class 0 OID 0)
-- Dependencies: 239
-- Name: monedas_id_seq; Type: SEQUENCE OWNED BY; Schema: slave; Owner: docker
--

ALTER SEQUENCE slave.monedas_id_seq OWNED BY slave.monedas.id;


--
-- TOC entry 242 (class 1259 OID 26259)
-- Name: personas; Type: TABLE; Schema: slave; Owner: docker
--

CREATE TABLE slave.personas (
    persona_tipo character varying(31) NOT NULL,
    id bigint NOT NULL,
    direccion_email character varying(100),
    fecha_fallecimiento timestamp without time zone,
    fecha_nacimiento timestamp without time zone,
    nombre_busqueda character varying(100),
    nombre_completo character varying(100),
    numero_documento character varying(20),
    primer_apellido character varying(25),
    primer_nombre character varying(25),
    segundo_apellido character varying(25),
    segundo_nombre character varying(25),
    telefono character varying(255),
    observacion character varying(255),
    cargo character varying(255),
    tarea character varying(255),
    direccion_id bigint,
    tipo_documento_id bigint,
    local_id bigint,
    operador_id bigint,
    zona_id bigint
);


ALTER TABLE slave.personas OWNER TO docker;

--
-- TOC entry 241 (class 1259 OID 26257)
-- Name: personas_id_seq; Type: SEQUENCE; Schema: slave; Owner: docker
--

CREATE SEQUENCE slave.personas_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE slave.personas_id_seq OWNER TO docker;

--
-- TOC entry 4611 (class 0 OID 0)
-- Dependencies: 241
-- Name: personas_id_seq; Type: SEQUENCE OWNED BY; Schema: slave; Owner: docker
--

ALTER SEQUENCE slave.personas_id_seq OWNED BY slave.personas.id;


--
-- TOC entry 243 (class 1259 OID 26268)
-- Name: proveedores_articulos; Type: TABLE; Schema: slave; Owner: docker
--

CREATE TABLE slave.proveedores_articulos (
    articulo_id bigint NOT NULL,
    proveedor_id bigint NOT NULL
);


ALTER TABLE slave.proveedores_articulos OWNER TO docker;

--
-- TOC entry 244 (class 1259 OID 26271)
-- Name: referentes; Type: TABLE; Schema: slave; Owner: docker
--

CREATE TABLE slave.referentes (
    persona_id bigint NOT NULL,
    empresa_id bigint NOT NULL
);


ALTER TABLE slave.referentes OWNER TO docker;

--
-- TOC entry 246 (class 1259 OID 26276)
-- Name: tipos_direccion; Type: TABLE; Schema: slave; Owner: docker
--

CREATE TABLE slave.tipos_direccion (
    id bigint NOT NULL,
    codigo character varying(255),
    descripcion character varying(255)
);


ALTER TABLE slave.tipos_direccion OWNER TO docker;

--
-- TOC entry 245 (class 1259 OID 26274)
-- Name: tipos_direccion_id_seq; Type: SEQUENCE; Schema: slave; Owner: docker
--

CREATE SEQUENCE slave.tipos_direccion_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE slave.tipos_direccion_id_seq OWNER TO docker;

--
-- TOC entry 4612 (class 0 OID 0)
-- Dependencies: 245
-- Name: tipos_direccion_id_seq; Type: SEQUENCE OWNED BY; Schema: slave; Owner: docker
--

ALTER SEQUENCE slave.tipos_direccion_id_seq OWNED BY slave.tipos_direccion.id;


--
-- TOC entry 248 (class 1259 OID 26287)
-- Name: tipos_documento; Type: TABLE; Schema: slave; Owner: docker
--

CREATE TABLE slave.tipos_documento (
    id bigint NOT NULL,
    descripcion character varying(255),
    sigla character varying(255)
);


ALTER TABLE slave.tipos_documento OWNER TO docker;

--
-- TOC entry 247 (class 1259 OID 26285)
-- Name: tipos_documento_id_seq; Type: SEQUENCE; Schema: slave; Owner: docker
--

CREATE SEQUENCE slave.tipos_documento_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE slave.tipos_documento_id_seq OWNER TO docker;

--
-- TOC entry 4613 (class 0 OID 0)
-- Dependencies: 247
-- Name: tipos_documento_id_seq; Type: SEQUENCE OWNED BY; Schema: slave; Owner: docker
--

ALTER SEQUENCE slave.tipos_documento_id_seq OWNED BY slave.tipos_documento.id;


--
-- TOC entry 250 (class 1259 OID 26298)
-- Name: tipos_empresa; Type: TABLE; Schema: slave; Owner: docker
--

CREATE TABLE slave.tipos_empresa (
    id bigint NOT NULL,
    descripcion character varying(255),
    sigla character varying(255)
);


ALTER TABLE slave.tipos_empresa OWNER TO docker;

--
-- TOC entry 249 (class 1259 OID 26296)
-- Name: tipos_empresa_id_seq; Type: SEQUENCE; Schema: slave; Owner: docker
--

CREATE SEQUENCE slave.tipos_empresa_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE slave.tipos_empresa_id_seq OWNER TO docker;

--
-- TOC entry 4614 (class 0 OID 0)
-- Dependencies: 249
-- Name: tipos_empresa_id_seq; Type: SEQUENCE OWNED BY; Schema: slave; Owner: docker
--

ALTER SEQUENCE slave.tipos_empresa_id_seq OWNED BY slave.tipos_empresa.id;


--
-- TOC entry 252 (class 1259 OID 26309)
-- Name: tipos_zona; Type: TABLE; Schema: slave; Owner: docker
--

CREATE TABLE slave.tipos_zona (
    id bigint NOT NULL,
    codigo character varying(255),
    descripcion character varying(255)
);


ALTER TABLE slave.tipos_zona OWNER TO docker;

--
-- TOC entry 251 (class 1259 OID 26307)
-- Name: tipos_zona_id_seq; Type: SEQUENCE; Schema: slave; Owner: docker
--

CREATE SEQUENCE slave.tipos_zona_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE slave.tipos_zona_id_seq OWNER TO docker;

--
-- TOC entry 4615 (class 0 OID 0)
-- Dependencies: 251
-- Name: tipos_zona_id_seq; Type: SEQUENCE OWNED BY; Schema: slave; Owner: docker
--

ALTER SEQUENCE slave.tipos_zona_id_seq OWNED BY slave.tipos_zona.id;


--
-- TOC entry 254 (class 1259 OID 26320)
-- Name: unidades; Type: TABLE; Schema: slave; Owner: docker
--

CREATE TABLE slave.unidades (
    id bigint NOT NULL,
    codigo character varying(255),
    descripcion character varying(255)
);


ALTER TABLE slave.unidades OWNER TO docker;

--
-- TOC entry 253 (class 1259 OID 26318)
-- Name: unidades_id_seq; Type: SEQUENCE; Schema: slave; Owner: docker
--

CREATE SEQUENCE slave.unidades_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE slave.unidades_id_seq OWNER TO docker;

--
-- TOC entry 4616 (class 0 OID 0)
-- Dependencies: 253
-- Name: unidades_id_seq; Type: SEQUENCE OWNED BY; Schema: slave; Owner: docker
--

ALTER SEQUENCE slave.unidades_id_seq OWNED BY slave.unidades.id;


--
-- TOC entry 256 (class 1259 OID 26331)
-- Name: zona; Type: TABLE; Schema: slave; Owner: docker
--

CREATE TABLE slave.zona (
    id bigint NOT NULL,
    nombre character varying(255),
    observacion character varying(255),
    zona_id character varying(255),
    tipo_zona_id bigint
);


ALTER TABLE slave.zona OWNER TO docker;

--
-- TOC entry 255 (class 1259 OID 26329)
-- Name: zona_id_seq; Type: SEQUENCE; Schema: slave; Owner: docker
--

CREATE SEQUENCE slave.zona_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE slave.zona_id_seq OWNER TO docker;

--
-- TOC entry 4617 (class 0 OID 0)
-- Dependencies: 255
-- Name: zona_id_seq; Type: SEQUENCE OWNED BY; Schema: slave; Owner: docker
--

ALTER SEQUENCE slave.zona_id_seq OWNED BY slave.zona.id;


--
-- TOC entry 4319 (class 2604 OID 26109)
-- Name: locations id; Type: DEFAULT; Schema: gis; Owner: docker
--

ALTER TABLE ONLY gis.locations ALTER COLUMN id SET DEFAULT nextval('gis.locations_id_seq'::regclass);


--
-- TOC entry 4320 (class 2604 OID 26120)
-- Name: zones id; Type: DEFAULT; Schema: gis; Owner: docker
--

ALTER TABLE ONLY gis.zones ALTER COLUMN id SET DEFAULT nextval('gis.zones_id_seq'::regclass);


--
-- TOC entry 4321 (class 2604 OID 26141)
-- Name: tenants id; Type: DEFAULT; Schema: master; Owner: docker
--

ALTER TABLE ONLY master.tenants ALTER COLUMN id SET DEFAULT nextval('master.tenants_id_seq'::regclass);


--
-- TOC entry 4322 (class 2604 OID 26151)
-- Name: usuarios id; Type: DEFAULT; Schema: master; Owner: docker
--

ALTER TABLE ONLY master.usuarios ALTER COLUMN id SET DEFAULT nextval('master.usuarios_id_seq'::regclass);


--
-- TOC entry 4323 (class 2604 OID 26169)
-- Name: almacenes id; Type: DEFAULT; Schema: slave; Owner: docker
--

ALTER TABLE ONLY slave.almacenes ALTER COLUMN id SET DEFAULT nextval('slave.almacenes_id_seq'::regclass);


--
-- TOC entry 4324 (class 2604 OID 26180)
-- Name: articulos id; Type: DEFAULT; Schema: slave; Owner: docker
--

ALTER TABLE ONLY slave.articulos ALTER COLUMN id SET DEFAULT nextval('slave.articulos_id_seq'::regclass);


--
-- TOC entry 4325 (class 2604 OID 26191)
-- Name: calificaciones id; Type: DEFAULT; Schema: slave; Owner: docker
--

ALTER TABLE ONLY slave.calificaciones ALTER COLUMN id SET DEFAULT nextval('slave.calificaciones_id_seq'::regclass);


--
-- TOC entry 4326 (class 2604 OID 26202)
-- Name: detalles id; Type: DEFAULT; Schema: slave; Owner: docker
--

ALTER TABLE ONLY slave.detalles ALTER COLUMN id SET DEFAULT nextval('slave.detalles_id_seq'::regclass);


--
-- TOC entry 4327 (class 2604 OID 26210)
-- Name: direccion id; Type: DEFAULT; Schema: slave; Owner: docker
--

ALTER TABLE ONLY slave.direccion ALTER COLUMN id SET DEFAULT nextval('slave.direccion_id_seq'::regclass);


--
-- TOC entry 4328 (class 2604 OID 26221)
-- Name: documentos id; Type: DEFAULT; Schema: slave; Owner: docker
--

ALTER TABLE ONLY slave.documentos ALTER COLUMN id SET DEFAULT nextval('slave.documentos_id_seq'::regclass);


--
-- TOC entry 4329 (class 2604 OID 26229)
-- Name: empresas id; Type: DEFAULT; Schema: slave; Owner: docker
--

ALTER TABLE ONLY slave.empresas ALTER COLUMN id SET DEFAULT nextval('slave.empresas_id_seq'::regclass);


--
-- TOC entry 4330 (class 2604 OID 26240)
-- Name: locales id; Type: DEFAULT; Schema: slave; Owner: docker
--

ALTER TABLE ONLY slave.locales ALTER COLUMN id SET DEFAULT nextval('slave.locales_id_seq'::regclass);


--
-- TOC entry 4331 (class 2604 OID 26251)
-- Name: monedas id; Type: DEFAULT; Schema: slave; Owner: docker
--

ALTER TABLE ONLY slave.monedas ALTER COLUMN id SET DEFAULT nextval('slave.monedas_id_seq'::regclass);


--
-- TOC entry 4332 (class 2604 OID 26262)
-- Name: personas id; Type: DEFAULT; Schema: slave; Owner: docker
--

ALTER TABLE ONLY slave.personas ALTER COLUMN id SET DEFAULT nextval('slave.personas_id_seq'::regclass);


--
-- TOC entry 4333 (class 2604 OID 26279)
-- Name: tipos_direccion id; Type: DEFAULT; Schema: slave; Owner: docker
--

ALTER TABLE ONLY slave.tipos_direccion ALTER COLUMN id SET DEFAULT nextval('slave.tipos_direccion_id_seq'::regclass);


--
-- TOC entry 4334 (class 2604 OID 26290)
-- Name: tipos_documento id; Type: DEFAULT; Schema: slave; Owner: docker
--

ALTER TABLE ONLY slave.tipos_documento ALTER COLUMN id SET DEFAULT nextval('slave.tipos_documento_id_seq'::regclass);


--
-- TOC entry 4335 (class 2604 OID 26301)
-- Name: tipos_empresa id; Type: DEFAULT; Schema: slave; Owner: docker
--

ALTER TABLE ONLY slave.tipos_empresa ALTER COLUMN id SET DEFAULT nextval('slave.tipos_empresa_id_seq'::regclass);


--
-- TOC entry 4336 (class 2604 OID 26312)
-- Name: tipos_zona id; Type: DEFAULT; Schema: slave; Owner: docker
--

ALTER TABLE ONLY slave.tipos_zona ALTER COLUMN id SET DEFAULT nextval('slave.tipos_zona_id_seq'::regclass);


--
-- TOC entry 4337 (class 2604 OID 26323)
-- Name: unidades id; Type: DEFAULT; Schema: slave; Owner: docker
--

ALTER TABLE ONLY slave.unidades ALTER COLUMN id SET DEFAULT nextval('slave.unidades_id_seq'::regclass);


--
-- TOC entry 4338 (class 2604 OID 26334)
-- Name: zona id; Type: DEFAULT; Schema: slave; Owner: docker
--

ALTER TABLE ONLY slave.zona ALTER COLUMN id SET DEFAULT nextval('slave.zona_id_seq'::regclass);


--
-- TOC entry 4548 (class 0 OID 26106)
-- Dependencies: 216
-- Data for Name: locations; Type: TABLE DATA; Schema: gis; Owner: docker
--



--
-- TOC entry 4317 (class 0 OID 24887)
-- Dependencies: 201
-- Data for Name: spatial_ref_sys; Type: TABLE DATA; Schema: gis; Owner: docker
--



--
-- TOC entry 4550 (class 0 OID 26117)
-- Dependencies: 218
-- Data for Name: zones; Type: TABLE DATA; Schema: gis; Owner: docker
--



--
-- TOC entry 4552 (class 0 OID 26138)
-- Dependencies: 220
-- Data for Name: tenants; Type: TABLE DATA; Schema: master; Owner: docker
--

INSERT INTO master.tenants VALUES (1, '2018-12-15 06:09:10.392369', NULL, 'slave');
INSERT INTO master.tenants VALUES (2, '2018-12-15 06:09:10.392369', NULL, 'test');


--
-- TOC entry 4554 (class 0 OID 26148)
-- Dependencies: 222
-- Data for Name: usuarios; Type: TABLE DATA; Schema: master; Owner: docker
--

INSERT INTO master.usuarios VALUES ('ADMINISTRADOR', 1, '2018-12-15 06:09:10.392369', NULL, NULL, 'admin@distrity.com', '17950858528ec9f8fed7b7ea91a703ad2b7b23acc6d65c1b44ffc7b77e02a2b718f9da6f86b1afdbd1de05758cd64f12e88182c1adf63de5c47095b55c2b3e8e', 'administrador', NULL, NULL);
INSERT INTO master.usuarios VALUES ('OPERADOR', 2, '2018-12-15 06:09:10.392369', NULL, NULL, 'ventas@distrity.com', '17950858528ec9f8fed7b7ea91a703ad2b7b23acc6d65c1b44ffc7b77e02a2b718f9da6f86b1afdbd1de05758cd64f12e88182c1adf63de5c47095b55c2b3e8e', 'vendedor', 'VENTAS', 1);


--
-- TOC entry 4556 (class 0 OID 26166)
-- Dependencies: 224
-- Data for Name: almacenes; Type: TABLE DATA; Schema: slave; Owner: docker
--



--
-- TOC entry 4558 (class 0 OID 26177)
-- Dependencies: 226
-- Data for Name: articulos; Type: TABLE DATA; Schema: slave; Owner: docker
--



--
-- TOC entry 4560 (class 0 OID 26188)
-- Dependencies: 228
-- Data for Name: calificaciones; Type: TABLE DATA; Schema: slave; Owner: docker
--



--
-- TOC entry 4562 (class 0 OID 26199)
-- Dependencies: 230
-- Data for Name: detalles; Type: TABLE DATA; Schema: slave; Owner: docker
--



--
-- TOC entry 4564 (class 0 OID 26207)
-- Dependencies: 232
-- Data for Name: direccion; Type: TABLE DATA; Schema: slave; Owner: docker
--



--
-- TOC entry 4566 (class 0 OID 26218)
-- Dependencies: 234
-- Data for Name: documentos; Type: TABLE DATA; Schema: slave; Owner: docker
--



--
-- TOC entry 4568 (class 0 OID 26226)
-- Dependencies: 236
-- Data for Name: empresas; Type: TABLE DATA; Schema: slave; Owner: docker
--



--
-- TOC entry 4570 (class 0 OID 26237)
-- Dependencies: 238
-- Data for Name: locales; Type: TABLE DATA; Schema: slave; Owner: docker
--



--
-- TOC entry 4572 (class 0 OID 26248)
-- Dependencies: 240
-- Data for Name: monedas; Type: TABLE DATA; Schema: slave; Owner: docker
--



--
-- TOC entry 4574 (class 0 OID 26259)
-- Dependencies: 242
-- Data for Name: personas; Type: TABLE DATA; Schema: slave; Owner: docker
--



--
-- TOC entry 4575 (class 0 OID 26268)
-- Dependencies: 243
-- Data for Name: proveedores_articulos; Type: TABLE DATA; Schema: slave; Owner: docker
--



--
-- TOC entry 4576 (class 0 OID 26271)
-- Dependencies: 244
-- Data for Name: referentes; Type: TABLE DATA; Schema: slave; Owner: docker
--



--
-- TOC entry 4578 (class 0 OID 26276)
-- Dependencies: 246
-- Data for Name: tipos_direccion; Type: TABLE DATA; Schema: slave; Owner: docker
--



--
-- TOC entry 4580 (class 0 OID 26287)
-- Dependencies: 248
-- Data for Name: tipos_documento; Type: TABLE DATA; Schema: slave; Owner: docker
--



--
-- TOC entry 4582 (class 0 OID 26298)
-- Dependencies: 250
-- Data for Name: tipos_empresa; Type: TABLE DATA; Schema: slave; Owner: docker
--



--
-- TOC entry 4584 (class 0 OID 26309)
-- Dependencies: 252
-- Data for Name: tipos_zona; Type: TABLE DATA; Schema: slave; Owner: docker
--



--
-- TOC entry 4586 (class 0 OID 26320)
-- Dependencies: 254
-- Data for Name: unidades; Type: TABLE DATA; Schema: slave; Owner: docker
--



--
-- TOC entry 4588 (class 0 OID 26331)
-- Dependencies: 256
-- Data for Name: zona; Type: TABLE DATA; Schema: slave; Owner: docker
--



--
-- TOC entry 4618 (class 0 OID 0)
-- Dependencies: 215
-- Name: locations_id_seq; Type: SEQUENCE SET; Schema: gis; Owner: docker
--

SELECT pg_catalog.setval('gis.locations_id_seq', 1, false);


--
-- TOC entry 4619 (class 0 OID 0)
-- Dependencies: 217
-- Name: zones_id_seq; Type: SEQUENCE SET; Schema: gis; Owner: docker
--

SELECT pg_catalog.setval('gis.zones_id_seq', 1, false);


--
-- TOC entry 4620 (class 0 OID 0)
-- Dependencies: 219
-- Name: tenants_id_seq; Type: SEQUENCE SET; Schema: master; Owner: docker
--

SELECT pg_catalog.setval('master.tenants_id_seq', 1, false);


--
-- TOC entry 4621 (class 0 OID 0)
-- Dependencies: 221
-- Name: usuarios_id_seq; Type: SEQUENCE SET; Schema: master; Owner: docker
--

SELECT pg_catalog.setval('master.usuarios_id_seq', 1, false);


--
-- TOC entry 4622 (class 0 OID 0)
-- Dependencies: 223
-- Name: almacenes_id_seq; Type: SEQUENCE SET; Schema: slave; Owner: docker
--

SELECT pg_catalog.setval('slave.almacenes_id_seq', 1, false);


--
-- TOC entry 4623 (class 0 OID 0)
-- Dependencies: 225
-- Name: articulos_id_seq; Type: SEQUENCE SET; Schema: slave; Owner: docker
--

SELECT pg_catalog.setval('slave.articulos_id_seq', 1, false);


--
-- TOC entry 4624 (class 0 OID 0)
-- Dependencies: 227
-- Name: calificaciones_id_seq; Type: SEQUENCE SET; Schema: slave; Owner: docker
--

SELECT pg_catalog.setval('slave.calificaciones_id_seq', 1, false);


--
-- TOC entry 4625 (class 0 OID 0)
-- Dependencies: 229
-- Name: detalles_id_seq; Type: SEQUENCE SET; Schema: slave; Owner: docker
--

SELECT pg_catalog.setval('slave.detalles_id_seq', 1, false);


--
-- TOC entry 4626 (class 0 OID 0)
-- Dependencies: 231
-- Name: direccion_id_seq; Type: SEQUENCE SET; Schema: slave; Owner: docker
--

SELECT pg_catalog.setval('slave.direccion_id_seq', 1, false);


--
-- TOC entry 4627 (class 0 OID 0)
-- Dependencies: 233
-- Name: documentos_id_seq; Type: SEQUENCE SET; Schema: slave; Owner: docker
--

SELECT pg_catalog.setval('slave.documentos_id_seq', 1, false);


--
-- TOC entry 4628 (class 0 OID 0)
-- Dependencies: 235
-- Name: empresas_id_seq; Type: SEQUENCE SET; Schema: slave; Owner: docker
--

SELECT pg_catalog.setval('slave.empresas_id_seq', 1, false);


--
-- TOC entry 4629 (class 0 OID 0)
-- Dependencies: 237
-- Name: locales_id_seq; Type: SEQUENCE SET; Schema: slave; Owner: docker
--

SELECT pg_catalog.setval('slave.locales_id_seq', 1, false);


--
-- TOC entry 4630 (class 0 OID 0)
-- Dependencies: 239
-- Name: monedas_id_seq; Type: SEQUENCE SET; Schema: slave; Owner: docker
--

SELECT pg_catalog.setval('slave.monedas_id_seq', 1, false);


--
-- TOC entry 4631 (class 0 OID 0)
-- Dependencies: 241
-- Name: personas_id_seq; Type: SEQUENCE SET; Schema: slave; Owner: docker
--

SELECT pg_catalog.setval('slave.personas_id_seq', 1, false);


--
-- TOC entry 4632 (class 0 OID 0)
-- Dependencies: 245
-- Name: tipos_direccion_id_seq; Type: SEQUENCE SET; Schema: slave; Owner: docker
--

SELECT pg_catalog.setval('slave.tipos_direccion_id_seq', 1, false);


--
-- TOC entry 4633 (class 0 OID 0)
-- Dependencies: 247
-- Name: tipos_documento_id_seq; Type: SEQUENCE SET; Schema: slave; Owner: docker
--

SELECT pg_catalog.setval('slave.tipos_documento_id_seq', 1, false);


--
-- TOC entry 4634 (class 0 OID 0)
-- Dependencies: 249
-- Name: tipos_empresa_id_seq; Type: SEQUENCE SET; Schema: slave; Owner: docker
--

SELECT pg_catalog.setval('slave.tipos_empresa_id_seq', 1, false);


--
-- TOC entry 4635 (class 0 OID 0)
-- Dependencies: 251
-- Name: tipos_zona_id_seq; Type: SEQUENCE SET; Schema: slave; Owner: docker
--

SELECT pg_catalog.setval('slave.tipos_zona_id_seq', 1, false);


--
-- TOC entry 4636 (class 0 OID 0)
-- Dependencies: 253
-- Name: unidades_id_seq; Type: SEQUENCE SET; Schema: slave; Owner: docker
--

SELECT pg_catalog.setval('slave.unidades_id_seq', 1, false);


--
-- TOC entry 4637 (class 0 OID 0)
-- Dependencies: 255
-- Name: zona_id_seq; Type: SEQUENCE SET; Schema: slave; Owner: docker
--

SELECT pg_catalog.setval('slave.zona_id_seq', 1, false);


--
-- TOC entry 4340 (class 2606 OID 26114)
-- Name: locations locations_pkey; Type: CONSTRAINT; Schema: gis; Owner: docker
--

ALTER TABLE ONLY gis.locations
    ADD CONSTRAINT locations_pkey PRIMARY KEY (id);


--
-- TOC entry 4342 (class 2606 OID 26125)
-- Name: zones zones_pkey; Type: CONSTRAINT; Schema: gis; Owner: docker
--

ALTER TABLE ONLY gis.zones
    ADD CONSTRAINT zones_pkey PRIMARY KEY (id);


--
-- TOC entry 4344 (class 2606 OID 26143)
-- Name: tenants tenants_pkey; Type: CONSTRAINT; Schema: master; Owner: docker
--

ALTER TABLE ONLY master.tenants
    ADD CONSTRAINT tenants_pkey PRIMARY KEY (id);


--
-- TOC entry 4348 (class 2606 OID 26158)
-- Name: usuarios uk_kfsp0s1tflm1cwlj8idhqsad0; Type: CONSTRAINT; Schema: master; Owner: docker
--

ALTER TABLE ONLY master.usuarios
    ADD CONSTRAINT uk_kfsp0s1tflm1cwlj8idhqsad0 UNIQUE (email);


--
-- TOC entry 4346 (class 2606 OID 26145)
-- Name: tenants uk_qjr1gcajh6xppj3qgu7hl9203; Type: CONSTRAINT; Schema: master; Owner: docker
--

ALTER TABLE ONLY master.tenants
    ADD CONSTRAINT uk_qjr1gcajh6xppj3qgu7hl9203 UNIQUE (nombre);


--
-- TOC entry 4350 (class 2606 OID 26156)
-- Name: usuarios usuarios_pkey; Type: CONSTRAINT; Schema: master; Owner: docker
--

ALTER TABLE ONLY master.usuarios
    ADD CONSTRAINT usuarios_pkey PRIMARY KEY (id);


--
-- TOC entry 4352 (class 2606 OID 26174)
-- Name: almacenes almacenes_pkey; Type: CONSTRAINT; Schema: slave; Owner: docker
--

ALTER TABLE ONLY slave.almacenes
    ADD CONSTRAINT almacenes_pkey PRIMARY KEY (id);


--
-- TOC entry 4354 (class 2606 OID 26185)
-- Name: articulos articulos_pkey; Type: CONSTRAINT; Schema: slave; Owner: docker
--

ALTER TABLE ONLY slave.articulos
    ADD CONSTRAINT articulos_pkey PRIMARY KEY (id);


--
-- TOC entry 4356 (class 2606 OID 26196)
-- Name: calificaciones calificaciones_pkey; Type: CONSTRAINT; Schema: slave; Owner: docker
--

ALTER TABLE ONLY slave.calificaciones
    ADD CONSTRAINT calificaciones_pkey PRIMARY KEY (id);


--
-- TOC entry 4358 (class 2606 OID 26204)
-- Name: detalles detalles_pkey; Type: CONSTRAINT; Schema: slave; Owner: docker
--

ALTER TABLE ONLY slave.detalles
    ADD CONSTRAINT detalles_pkey PRIMARY KEY (id);


--
-- TOC entry 4360 (class 2606 OID 26215)
-- Name: direccion direccion_pkey; Type: CONSTRAINT; Schema: slave; Owner: docker
--

ALTER TABLE ONLY slave.direccion
    ADD CONSTRAINT direccion_pkey PRIMARY KEY (id);


--
-- TOC entry 4362 (class 2606 OID 26223)
-- Name: documentos documentos_pkey; Type: CONSTRAINT; Schema: slave; Owner: docker
--

ALTER TABLE ONLY slave.documentos
    ADD CONSTRAINT documentos_pkey PRIMARY KEY (id);


--
-- TOC entry 4364 (class 2606 OID 26234)
-- Name: empresas empresas_pkey; Type: CONSTRAINT; Schema: slave; Owner: docker
--

ALTER TABLE ONLY slave.empresas
    ADD CONSTRAINT empresas_pkey PRIMARY KEY (id);


--
-- TOC entry 4372 (class 2606 OID 26245)
-- Name: locales locales_pkey; Type: CONSTRAINT; Schema: slave; Owner: docker
--

ALTER TABLE ONLY slave.locales
    ADD CONSTRAINT locales_pkey PRIMARY KEY (id);


--
-- TOC entry 4374 (class 2606 OID 26256)
-- Name: monedas monedas_pkey; Type: CONSTRAINT; Schema: slave; Owner: docker
--

ALTER TABLE ONLY slave.monedas
    ADD CONSTRAINT monedas_pkey PRIMARY KEY (id);


--
-- TOC entry 4376 (class 2606 OID 26267)
-- Name: personas personas_pkey; Type: CONSTRAINT; Schema: slave; Owner: docker
--

ALTER TABLE ONLY slave.personas
    ADD CONSTRAINT personas_pkey PRIMARY KEY (id);


--
-- TOC entry 4378 (class 2606 OID 26284)
-- Name: tipos_direccion tipos_direccion_pkey; Type: CONSTRAINT; Schema: slave; Owner: docker
--

ALTER TABLE ONLY slave.tipos_direccion
    ADD CONSTRAINT tipos_direccion_pkey PRIMARY KEY (id);


--
-- TOC entry 4380 (class 2606 OID 26295)
-- Name: tipos_documento tipos_documento_pkey; Type: CONSTRAINT; Schema: slave; Owner: docker
--

ALTER TABLE ONLY slave.tipos_documento
    ADD CONSTRAINT tipos_documento_pkey PRIMARY KEY (id);


--
-- TOC entry 4382 (class 2606 OID 26306)
-- Name: tipos_empresa tipos_empresa_pkey; Type: CONSTRAINT; Schema: slave; Owner: docker
--

ALTER TABLE ONLY slave.tipos_empresa
    ADD CONSTRAINT tipos_empresa_pkey PRIMARY KEY (id);


--
-- TOC entry 4384 (class 2606 OID 26317)
-- Name: tipos_zona tipos_zona_pkey; Type: CONSTRAINT; Schema: slave; Owner: docker
--

ALTER TABLE ONLY slave.tipos_zona
    ADD CONSTRAINT tipos_zona_pkey PRIMARY KEY (id);


--
-- TOC entry 4366 (class 2606 OID 26345)
-- Name: empresas uk_8kg2msi6sjwji1h9lxuyk576a; Type: CONSTRAINT; Schema: slave; Owner: docker
--

ALTER TABLE ONLY slave.empresas
    ADD CONSTRAINT uk_8kg2msi6sjwji1h9lxuyk576a UNIQUE (central_id);


--
-- TOC entry 4368 (class 2606 OID 26341)
-- Name: empresas uk_hx1cartw5g1cmi8b4o3403r1b; Type: CONSTRAINT; Schema: slave; Owner: docker
--

ALTER TABLE ONLY slave.empresas
    ADD CONSTRAINT uk_hx1cartw5g1cmi8b4o3403r1b UNIQUE (nombre_comercial);


--
-- TOC entry 4370 (class 2606 OID 26343)
-- Name: empresas uk_qpbwdn6icj2ktae57joulkfpm; Type: CONSTRAINT; Schema: slave; Owner: docker
--

ALTER TABLE ONLY slave.empresas
    ADD CONSTRAINT uk_qpbwdn6icj2ktae57joulkfpm UNIQUE (razon_social);


--
-- TOC entry 4386 (class 2606 OID 26328)
-- Name: unidades unidades_pkey; Type: CONSTRAINT; Schema: slave; Owner: docker
--

ALTER TABLE ONLY slave.unidades
    ADD CONSTRAINT unidades_pkey PRIMARY KEY (id);


--
-- TOC entry 4388 (class 2606 OID 26339)
-- Name: zona zona_pkey; Type: CONSTRAINT; Schema: slave; Owner: docker
--

ALTER TABLE ONLY slave.zona
    ADD CONSTRAINT zona_pkey PRIMARY KEY (id);


--
-- TOC entry 4389 (class 2606 OID 26159)
-- Name: usuarios fkeq2qjef9kg8q9gfxessikg61x; Type: FK CONSTRAINT; Schema: master; Owner: docker
--

ALTER TABLE ONLY master.usuarios
    ADD CONSTRAINT fkeq2qjef9kg8q9gfxessikg61x FOREIGN KEY (tenant_id) REFERENCES master.tenants(id);


--
-- TOC entry 4397 (class 2606 OID 26381)
-- Name: documentos fk428ownpi0rlqi8b6fg1b2oew9; Type: FK CONSTRAINT; Schema: slave; Owner: docker
--

ALTER TABLE ONLY slave.documentos
    ADD CONSTRAINT fk428ownpi0rlqi8b6fg1b2oew9 FOREIGN KEY (moneda_id) REFERENCES slave.monedas(id);


--
-- TOC entry 4390 (class 2606 OID 26346)
-- Name: almacenes fk4x01o1nrrnvwkv14ivh9qmx4a; Type: FK CONSTRAINT; Schema: slave; Owner: docker
--

ALTER TABLE ONLY slave.almacenes
    ADD CONSTRAINT fk4x01o1nrrnvwkv14ivh9qmx4a FOREIGN KEY (direccion_id) REFERENCES slave.direccion(id);


--
-- TOC entry 4417 (class 2606 OID 26481)
-- Name: referentes fk5mvhe07j5vgc141nrfg2huci2; Type: FK CONSTRAINT; Schema: slave; Owner: docker
--

ALTER TABLE ONLY slave.referentes
    ADD CONSTRAINT fk5mvhe07j5vgc141nrfg2huci2 FOREIGN KEY (persona_id) REFERENCES slave.empresas(id);


--
-- TOC entry 4391 (class 2606 OID 26351)
-- Name: almacenes fk8o5hcppc5ssd3blnjiaccj5mc; Type: FK CONSTRAINT; Schema: slave; Owner: docker
--

ALTER TABLE ONLY slave.almacenes
    ADD CONSTRAINT fk8o5hcppc5ssd3blnjiaccj5mc FOREIGN KEY (local_id) REFERENCES slave.locales(id);


--
-- TOC entry 4412 (class 2606 OID 26456)
-- Name: personas fk93ha77oii5ako9q41b22srlrn; Type: FK CONSTRAINT; Schema: slave; Owner: docker
--

ALTER TABLE ONLY slave.personas
    ADD CONSTRAINT fk93ha77oii5ako9q41b22srlrn FOREIGN KEY (operador_id) REFERENCES master.usuarios(id);


--
-- TOC entry 4418 (class 2606 OID 26486)
-- Name: zona fk9d03hvm238aaalhvjtjdl8c71; Type: FK CONSTRAINT; Schema: slave; Owner: docker
--

ALTER TABLE ONLY slave.zona
    ADD CONSTRAINT fk9d03hvm238aaalhvjtjdl8c71 FOREIGN KEY (tipo_zona_id) REFERENCES slave.tipos_zona(id);


--
-- TOC entry 4395 (class 2606 OID 26371)
-- Name: direccion fk9kira0bqtpccvi09w32ufsbm3; Type: FK CONSTRAINT; Schema: slave; Owner: docker
--

ALTER TABLE ONLY slave.direccion
    ADD CONSTRAINT fk9kira0bqtpccvi09w32ufsbm3 FOREIGN KEY (tipo_direccion_id) REFERENCES slave.tipos_direccion(id);


--
-- TOC entry 4396 (class 2606 OID 26376)
-- Name: documentos fkbs9awa1g5yioflfd2myaioiw7; Type: FK CONSTRAINT; Schema: slave; Owner: docker
--

ALTER TABLE ONLY slave.documentos
    ADD CONSTRAINT fkbs9awa1g5yioflfd2myaioiw7 FOREIGN KEY (local_id) REFERENCES slave.locales(id);


--
-- TOC entry 4408 (class 2606 OID 26436)
-- Name: locales fkcli0aejv1f533k2tyv6nbqo31; Type: FK CONSTRAINT; Schema: slave; Owner: docker
--

ALTER TABLE ONLY slave.locales
    ADD CONSTRAINT fkcli0aejv1f533k2tyv6nbqo31 FOREIGN KEY (zona_id) REFERENCES slave.zona(id);


--
-- TOC entry 4411 (class 2606 OID 26451)
-- Name: personas fkcp7smlsg4enrblxnr9kvkv2gb; Type: FK CONSTRAINT; Schema: slave; Owner: docker
--

ALTER TABLE ONLY slave.personas
    ADD CONSTRAINT fkcp7smlsg4enrblxnr9kvkv2gb FOREIGN KEY (local_id) REFERENCES slave.locales(id);


--
-- TOC entry 4399 (class 2606 OID 26391)
-- Name: documentos fkdy1bg7hu13fn6lnwccrvcxdfb; Type: FK CONSTRAINT; Schema: slave; Owner: docker
--

ALTER TABLE ONLY slave.documentos
    ADD CONSTRAINT fkdy1bg7hu13fn6lnwccrvcxdfb FOREIGN KEY (proveedor_id) REFERENCES slave.empresas(id);


--
-- TOC entry 4393 (class 2606 OID 26361)
-- Name: detalles fkej5w2akjk2pekwi6hx6369m9c; Type: FK CONSTRAINT; Schema: slave; Owner: docker
--

ALTER TABLE ONLY slave.detalles
    ADD CONSTRAINT fkej5w2akjk2pekwi6hx6369m9c FOREIGN KEY (articulo_id) REFERENCES slave.articulos(id);


--
-- TOC entry 4402 (class 2606 OID 26406)
-- Name: empresas fkf3kr9yjq12v4553r2syhx3lns; Type: FK CONSTRAINT; Schema: slave; Owner: docker
--

ALTER TABLE ONLY slave.empresas
    ADD CONSTRAINT fkf3kr9yjq12v4553r2syhx3lns FOREIGN KEY (direccion_id) REFERENCES slave.direccion(id);


--
-- TOC entry 4416 (class 2606 OID 26476)
-- Name: referentes fkf79g3aino0fo0c6x1h3eaykqx; Type: FK CONSTRAINT; Schema: slave; Owner: docker
--

ALTER TABLE ONLY slave.referentes
    ADD CONSTRAINT fkf79g3aino0fo0c6x1h3eaykqx FOREIGN KEY (empresa_id) REFERENCES slave.personas(id);


--
-- TOC entry 4405 (class 2606 OID 26421)
-- Name: empresas fkfjaarg22005fal4j5oapgb9dg; Type: FK CONSTRAINT; Schema: slave; Owner: docker
--

ALTER TABLE ONLY slave.empresas
    ADD CONSTRAINT fkfjaarg22005fal4j5oapgb9dg FOREIGN KEY (tenant_id) REFERENCES master.tenants(id);


--
-- TOC entry 4403 (class 2606 OID 26411)
-- Name: empresas fkg3205eyhscg9ra62w9imj0dbb; Type: FK CONSTRAINT; Schema: slave; Owner: docker
--

ALTER TABLE ONLY slave.empresas
    ADD CONSTRAINT fkg3205eyhscg9ra62w9imj0dbb FOREIGN KEY (tipo_empresa_id) REFERENCES slave.tipos_empresa(id);


--
-- TOC entry 4410 (class 2606 OID 26446)
-- Name: personas fkgf3i0tauh35uxs1i3oqvst4ya; Type: FK CONSTRAINT; Schema: slave; Owner: docker
--

ALTER TABLE ONLY slave.personas
    ADD CONSTRAINT fkgf3i0tauh35uxs1i3oqvst4ya FOREIGN KEY (tipo_documento_id) REFERENCES slave.tipos_documento(id);


--
-- TOC entry 4398 (class 2606 OID 26386)
-- Name: documentos fkgkvdeyop79dc45rw3jadhvfg8; Type: FK CONSTRAINT; Schema: slave; Owner: docker
--

ALTER TABLE ONLY slave.documentos
    ADD CONSTRAINT fkgkvdeyop79dc45rw3jadhvfg8 FOREIGN KEY (usuario_id) REFERENCES master.usuarios(id);


--
-- TOC entry 4404 (class 2606 OID 26416)
-- Name: empresas fkk0goc3ue7rh4vfp699sucf05e; Type: FK CONSTRAINT; Schema: slave; Owner: docker
--

ALTER TABLE ONLY slave.empresas
    ADD CONSTRAINT fkk0goc3ue7rh4vfp699sucf05e FOREIGN KEY (central_id) REFERENCES slave.locales(id);


--
-- TOC entry 4414 (class 2606 OID 26466)
-- Name: proveedores_articulos fkkjymanlwjrye8exff1tmceua7; Type: FK CONSTRAINT; Schema: slave; Owner: docker
--

ALTER TABLE ONLY slave.proveedores_articulos
    ADD CONSTRAINT fkkjymanlwjrye8exff1tmceua7 FOREIGN KEY (proveedor_id) REFERENCES slave.articulos(id);


--
-- TOC entry 4401 (class 2606 OID 26401)
-- Name: empresas fkmci9fai3j6ay8ouf3gaaiy2o2; Type: FK CONSTRAINT; Schema: slave; Owner: docker
--

ALTER TABLE ONLY slave.empresas
    ADD CONSTRAINT fkmci9fai3j6ay8ouf3gaaiy2o2 FOREIGN KEY (calificacion_id) REFERENCES slave.calificaciones(id);


--
-- TOC entry 4413 (class 2606 OID 26461)
-- Name: personas fkmcjt06tyjstqfhv87ncy083y3; Type: FK CONSTRAINT; Schema: slave; Owner: docker
--

ALTER TABLE ONLY slave.personas
    ADD CONSTRAINT fkmcjt06tyjstqfhv87ncy083y3 FOREIGN KEY (zona_id) REFERENCES slave.zona(id);


--
-- TOC entry 4392 (class 2606 OID 26356)
-- Name: articulos fkmft3fvi4nxdtxcr93bscsu5yc; Type: FK CONSTRAINT; Schema: slave; Owner: docker
--

ALTER TABLE ONLY slave.articulos
    ADD CONSTRAINT fkmft3fvi4nxdtxcr93bscsu5yc FOREIGN KEY (unidad_id) REFERENCES slave.unidades(id);


--
-- TOC entry 4407 (class 2606 OID 26431)
-- Name: locales fkou6i45mvmvu6a8fvaf21e2e9n; Type: FK CONSTRAINT; Schema: slave; Owner: docker
--

ALTER TABLE ONLY slave.locales
    ADD CONSTRAINT fkou6i45mvmvu6a8fvaf21e2e9n FOREIGN KEY (empresa_id) REFERENCES slave.empresas(id);


--
-- TOC entry 4415 (class 2606 OID 26471)
-- Name: proveedores_articulos fkr4gn3wri19hb3xdpx8v2cv05v; Type: FK CONSTRAINT; Schema: slave; Owner: docker
--

ALTER TABLE ONLY slave.proveedores_articulos
    ADD CONSTRAINT fkr4gn3wri19hb3xdpx8v2cv05v FOREIGN KEY (articulo_id) REFERENCES slave.empresas(id);


--
-- TOC entry 4409 (class 2606 OID 26441)
-- Name: personas fkrgrbnskp73qv2aei2xe9iyys5; Type: FK CONSTRAINT; Schema: slave; Owner: docker
--

ALTER TABLE ONLY slave.personas
    ADD CONSTRAINT fkrgrbnskp73qv2aei2xe9iyys5 FOREIGN KEY (direccion_id) REFERENCES slave.direccion(id);


--
-- TOC entry 4406 (class 2606 OID 26426)
-- Name: locales fkt78v0evmg4hka0xckt82f1qc5; Type: FK CONSTRAINT; Schema: slave; Owner: docker
--

ALTER TABLE ONLY slave.locales
    ADD CONSTRAINT fkt78v0evmg4hka0xckt82f1qc5 FOREIGN KEY (direccion_id) REFERENCES slave.direccion(id);


--
-- TOC entry 4400 (class 2606 OID 26396)
-- Name: documentos fktawyy9yxwub614qnlulcrucp4; Type: FK CONSTRAINT; Schema: slave; Owner: docker
--

ALTER TABLE ONLY slave.documentos
    ADD CONSTRAINT fktawyy9yxwub614qnlulcrucp4 FOREIGN KEY (cliente_id) REFERENCES slave.empresas(id);


--
-- TOC entry 4394 (class 2606 OID 26366)
-- Name: detalles fktl4y17qy0rmvc72f166c040jy; Type: FK CONSTRAINT; Schema: slave; Owner: docker
--

ALTER TABLE ONLY slave.detalles
    ADD CONSTRAINT fktl4y17qy0rmvc72f166c040jy FOREIGN KEY (documento_id) REFERENCES slave.documentos(id);


-- Completed on 2018-12-15 06:11:42 -03

--
-- PostgreSQL database dump complete
--

