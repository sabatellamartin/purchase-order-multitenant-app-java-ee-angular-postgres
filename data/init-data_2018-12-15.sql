--
-- PostgreSQL database dump
--

-- Dumped from database version 10.6
-- Dumped by pg_dump version 10.5

-- Started on 2018-12-15 06:18:01 -03

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
-- TOC entry 4548 (class 0 OID 26106)
-- Dependencies: 216
-- Data for Name: locations; Type: TABLE DATA; Schema: gis; Owner: -
--



--
-- TOC entry 4317 (class 0 OID 24887)
-- Dependencies: 201
-- Data for Name: spatial_ref_sys; Type: TABLE DATA; Schema: gis; Owner: -
--



--
-- TOC entry 4550 (class 0 OID 26117)
-- Dependencies: 218
-- Data for Name: zones; Type: TABLE DATA; Schema: gis; Owner: -
--



--
-- TOC entry 4552 (class 0 OID 26138)
-- Dependencies: 220
-- Data for Name: tenants; Type: TABLE DATA; Schema: master; Owner: -
--

INSERT INTO master.tenants VALUES (1, '2018-12-15 06:09:10.392369', NULL, 'slave');
INSERT INTO master.tenants VALUES (2, '2018-12-15 06:09:10.392369', NULL, 'test');


--
-- TOC entry 4554 (class 0 OID 26148)
-- Dependencies: 222
-- Data for Name: usuarios; Type: TABLE DATA; Schema: master; Owner: -
--

INSERT INTO master.usuarios VALUES ('ADMINISTRADOR', 1, '2018-12-15 06:09:10.392369', NULL, NULL, 'admin@distrity.com', '17950858528ec9f8fed7b7ea91a703ad2b7b23acc6d65c1b44ffc7b77e02a2b718f9da6f86b1afdbd1de05758cd64f12e88182c1adf63de5c47095b55c2b3e8e', 'administrador', NULL, NULL);
INSERT INTO master.usuarios VALUES ('OPERADOR', 2, '2018-12-15 06:09:10.392369', NULL, NULL, 'ventas@distrity.com', '17950858528ec9f8fed7b7ea91a703ad2b7b23acc6d65c1b44ffc7b77e02a2b718f9da6f86b1afdbd1de05758cd64f12e88182c1adf63de5c47095b55c2b3e8e', 'vendedor', 'VENTAS', 1);


--
-- TOC entry 4560 (class 0 OID 26188)
-- Dependencies: 228
-- Data for Name: calificaciones; Type: TABLE DATA; Schema: slave; Owner: -
--



--
-- TOC entry 4578 (class 0 OID 26276)
-- Dependencies: 246
-- Data for Name: tipos_direccion; Type: TABLE DATA; Schema: slave; Owner: -
--



--
-- TOC entry 4564 (class 0 OID 26207)
-- Dependencies: 232
-- Data for Name: direccion; Type: TABLE DATA; Schema: slave; Owner: -
--



--
-- TOC entry 4582 (class 0 OID 26298)
-- Dependencies: 250
-- Data for Name: tipos_empresa; Type: TABLE DATA; Schema: slave; Owner: -
--



--
-- TOC entry 4568 (class 0 OID 26226)
-- Dependencies: 236
-- Data for Name: empresas; Type: TABLE DATA; Schema: slave; Owner: -
--



--
-- TOC entry 4584 (class 0 OID 26309)
-- Dependencies: 252
-- Data for Name: tipos_zona; Type: TABLE DATA; Schema: slave; Owner: -
--



--
-- TOC entry 4588 (class 0 OID 26331)
-- Dependencies: 256
-- Data for Name: zona; Type: TABLE DATA; Schema: slave; Owner: -
--



--
-- TOC entry 4570 (class 0 OID 26237)
-- Dependencies: 238
-- Data for Name: locales; Type: TABLE DATA; Schema: slave; Owner: -
--



--
-- TOC entry 4556 (class 0 OID 26166)
-- Dependencies: 224
-- Data for Name: almacenes; Type: TABLE DATA; Schema: slave; Owner: -
--



--
-- TOC entry 4586 (class 0 OID 26320)
-- Dependencies: 254
-- Data for Name: unidades; Type: TABLE DATA; Schema: slave; Owner: -
--



--
-- TOC entry 4558 (class 0 OID 26177)
-- Dependencies: 226
-- Data for Name: articulos; Type: TABLE DATA; Schema: slave; Owner: -
--



--
-- TOC entry 4572 (class 0 OID 26248)
-- Dependencies: 240
-- Data for Name: monedas; Type: TABLE DATA; Schema: slave; Owner: -
--



--
-- TOC entry 4566 (class 0 OID 26218)
-- Dependencies: 234
-- Data for Name: documentos; Type: TABLE DATA; Schema: slave; Owner: -
--



--
-- TOC entry 4562 (class 0 OID 26199)
-- Dependencies: 230
-- Data for Name: detalles; Type: TABLE DATA; Schema: slave; Owner: -
--



--
-- TOC entry 4580 (class 0 OID 26287)
-- Dependencies: 248
-- Data for Name: tipos_documento; Type: TABLE DATA; Schema: slave; Owner: -
--



--
-- TOC entry 4574 (class 0 OID 26259)
-- Dependencies: 242
-- Data for Name: personas; Type: TABLE DATA; Schema: slave; Owner: -
--



--
-- TOC entry 4575 (class 0 OID 26268)
-- Dependencies: 243
-- Data for Name: proveedores_articulos; Type: TABLE DATA; Schema: slave; Owner: -
--



--
-- TOC entry 4576 (class 0 OID 26271)
-- Dependencies: 244
-- Data for Name: referentes; Type: TABLE DATA; Schema: slave; Owner: -
--



--
-- TOC entry 4594 (class 0 OID 0)
-- Dependencies: 215
-- Name: locations_id_seq; Type: SEQUENCE SET; Schema: gis; Owner: -
--

SELECT pg_catalog.setval('gis.locations_id_seq', 1, false);


--
-- TOC entry 4595 (class 0 OID 0)
-- Dependencies: 217
-- Name: zones_id_seq; Type: SEQUENCE SET; Schema: gis; Owner: -
--

SELECT pg_catalog.setval('gis.zones_id_seq', 1, false);


--
-- TOC entry 4596 (class 0 OID 0)
-- Dependencies: 219
-- Name: tenants_id_seq; Type: SEQUENCE SET; Schema: master; Owner: -
--

SELECT pg_catalog.setval('master.tenants_id_seq', 1, false);


--
-- TOC entry 4597 (class 0 OID 0)
-- Dependencies: 221
-- Name: usuarios_id_seq; Type: SEQUENCE SET; Schema: master; Owner: -
--

SELECT pg_catalog.setval('master.usuarios_id_seq', 1, false);


--
-- TOC entry 4598 (class 0 OID 0)
-- Dependencies: 223
-- Name: almacenes_id_seq; Type: SEQUENCE SET; Schema: slave; Owner: -
--

SELECT pg_catalog.setval('slave.almacenes_id_seq', 1, false);


--
-- TOC entry 4599 (class 0 OID 0)
-- Dependencies: 225
-- Name: articulos_id_seq; Type: SEQUENCE SET; Schema: slave; Owner: -
--

SELECT pg_catalog.setval('slave.articulos_id_seq', 1, false);


--
-- TOC entry 4600 (class 0 OID 0)
-- Dependencies: 227
-- Name: calificaciones_id_seq; Type: SEQUENCE SET; Schema: slave; Owner: -
--

SELECT pg_catalog.setval('slave.calificaciones_id_seq', 1, false);


--
-- TOC entry 4601 (class 0 OID 0)
-- Dependencies: 229
-- Name: detalles_id_seq; Type: SEQUENCE SET; Schema: slave; Owner: -
--

SELECT pg_catalog.setval('slave.detalles_id_seq', 1, false);


--
-- TOC entry 4602 (class 0 OID 0)
-- Dependencies: 231
-- Name: direccion_id_seq; Type: SEQUENCE SET; Schema: slave; Owner: -
--

SELECT pg_catalog.setval('slave.direccion_id_seq', 1, false);


--
-- TOC entry 4603 (class 0 OID 0)
-- Dependencies: 233
-- Name: documentos_id_seq; Type: SEQUENCE SET; Schema: slave; Owner: -
--

SELECT pg_catalog.setval('slave.documentos_id_seq', 1, false);


--
-- TOC entry 4604 (class 0 OID 0)
-- Dependencies: 235
-- Name: empresas_id_seq; Type: SEQUENCE SET; Schema: slave; Owner: -
--

SELECT pg_catalog.setval('slave.empresas_id_seq', 1, false);


--
-- TOC entry 4605 (class 0 OID 0)
-- Dependencies: 237
-- Name: locales_id_seq; Type: SEQUENCE SET; Schema: slave; Owner: -
--

SELECT pg_catalog.setval('slave.locales_id_seq', 1, false);


--
-- TOC entry 4606 (class 0 OID 0)
-- Dependencies: 239
-- Name: monedas_id_seq; Type: SEQUENCE SET; Schema: slave; Owner: -
--

SELECT pg_catalog.setval('slave.monedas_id_seq', 1, false);


--
-- TOC entry 4607 (class 0 OID 0)
-- Dependencies: 241
-- Name: personas_id_seq; Type: SEQUENCE SET; Schema: slave; Owner: -
--

SELECT pg_catalog.setval('slave.personas_id_seq', 1, false);


--
-- TOC entry 4608 (class 0 OID 0)
-- Dependencies: 245
-- Name: tipos_direccion_id_seq; Type: SEQUENCE SET; Schema: slave; Owner: -
--

SELECT pg_catalog.setval('slave.tipos_direccion_id_seq', 1, false);


--
-- TOC entry 4609 (class 0 OID 0)
-- Dependencies: 247
-- Name: tipos_documento_id_seq; Type: SEQUENCE SET; Schema: slave; Owner: -
--

SELECT pg_catalog.setval('slave.tipos_documento_id_seq', 1, false);


--
-- TOC entry 4610 (class 0 OID 0)
-- Dependencies: 249
-- Name: tipos_empresa_id_seq; Type: SEQUENCE SET; Schema: slave; Owner: -
--

SELECT pg_catalog.setval('slave.tipos_empresa_id_seq', 1, false);


--
-- TOC entry 4611 (class 0 OID 0)
-- Dependencies: 251
-- Name: tipos_zona_id_seq; Type: SEQUENCE SET; Schema: slave; Owner: -
--

SELECT pg_catalog.setval('slave.tipos_zona_id_seq', 1, false);


--
-- TOC entry 4612 (class 0 OID 0)
-- Dependencies: 253
-- Name: unidades_id_seq; Type: SEQUENCE SET; Schema: slave; Owner: -
--

SELECT pg_catalog.setval('slave.unidades_id_seq', 1, false);


--
-- TOC entry 4613 (class 0 OID 0)
-- Dependencies: 255
-- Name: zona_id_seq; Type: SEQUENCE SET; Schema: slave; Owner: -
--

SELECT pg_catalog.setval('slave.zona_id_seq', 1, false);


-- Completed on 2018-12-15 06:18:02 -03

--
-- PostgreSQL database dump complete
--

