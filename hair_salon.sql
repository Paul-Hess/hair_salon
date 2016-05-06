--
-- PostgreSQL database dump
--

SET statement_timeout = 0;
SET lock_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;

--
-- Name: plpgsql; Type: EXTENSION; Schema: -; Owner: 
--

CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;


--
-- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


SET search_path = public, pg_catalog;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- Name: clients; Type: TABLE; Schema: public; Owner: home; Tablespace: 
--

CREATE TABLE clients (
    id integer NOT NULL,
    created_at timestamp without time zone NOT NULL,
    updated_at timestamp without time zone,
    client_name character varying NOT NULL,
    stylist_id integer NOT NULL
);


ALTER TABLE clients OWNER TO home;

--
-- Name: clients_id_seq; Type: SEQUENCE; Schema: public; Owner: home
--

CREATE SEQUENCE clients_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE clients_id_seq OWNER TO home;

--
-- Name: clients_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: home
--

ALTER SEQUENCE clients_id_seq OWNED BY clients.id;


--
-- Name: stylists; Type: TABLE; Schema: public; Owner: home; Tablespace: 
--

CREATE TABLE stylists (
    id integer NOT NULL,
    created_at timestamp without time zone NOT NULL,
    updated_at timestamp without time zone,
    stylist_name character varying NOT NULL,
    stylist_specialty character varying NOT NULL
);


ALTER TABLE stylists OWNER TO home;

--
-- Name: stylists_id_seq; Type: SEQUENCE; Schema: public; Owner: home
--

CREATE SEQUENCE stylists_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE stylists_id_seq OWNER TO home;

--
-- Name: stylists_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: home
--

ALTER SEQUENCE stylists_id_seq OWNED BY stylists.id;


--
-- Name: visits; Type: TABLE; Schema: public; Owner: home; Tablespace: 
--

CREATE TABLE visits (
    id integer NOT NULL,
    visit_datetime timestamp without time zone NOT NULL,
    stylist_id integer NOT NULL,
    client_id integer NOT NULL,
    style_description character varying,
    style_review character varying
);


ALTER TABLE visits OWNER TO home;

--
-- Name: visits_id_seq; Type: SEQUENCE; Schema: public; Owner: home
--

CREATE SEQUENCE visits_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE visits_id_seq OWNER TO home;

--
-- Name: visits_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: home
--

ALTER SEQUENCE visits_id_seq OWNED BY visits.id;


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: home
--

ALTER TABLE ONLY clients ALTER COLUMN id SET DEFAULT nextval('clients_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: home
--

ALTER TABLE ONLY stylists ALTER COLUMN id SET DEFAULT nextval('stylists_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: home
--

ALTER TABLE ONLY visits ALTER COLUMN id SET DEFAULT nextval('visits_id_seq'::regclass);


--
-- Data for Name: clients; Type: TABLE DATA; Schema: public; Owner: home
--

COPY clients (id, created_at, updated_at, client_name, stylist_id) FROM stdin;
\.


--
-- Name: clients_id_seq; Type: SEQUENCE SET; Schema: public; Owner: home
--

SELECT pg_catalog.setval('clients_id_seq', 1, false);


--
-- Data for Name: stylists; Type: TABLE DATA; Schema: public; Owner: home
--

COPY stylists (id, created_at, updated_at, stylist_name, stylist_specialty) FROM stdin;
\.


--
-- Name: stylists_id_seq; Type: SEQUENCE SET; Schema: public; Owner: home
--

SELECT pg_catalog.setval('stylists_id_seq', 1, false);


--
-- Data for Name: visits; Type: TABLE DATA; Schema: public; Owner: home
--

COPY visits (id, visit_datetime, stylist_id, client_id, style_description, style_review) FROM stdin;
\.


--
-- Name: visits_id_seq; Type: SEQUENCE SET; Schema: public; Owner: home
--

SELECT pg_catalog.setval('visits_id_seq', 1, false);


--
-- Name: clients_pkey; Type: CONSTRAINT; Schema: public; Owner: home; Tablespace: 
--

ALTER TABLE ONLY clients
    ADD CONSTRAINT clients_pkey PRIMARY KEY (id);


--
-- Name: stylists_pkey; Type: CONSTRAINT; Schema: public; Owner: home; Tablespace: 
--

ALTER TABLE ONLY stylists
    ADD CONSTRAINT stylists_pkey PRIMARY KEY (id);


--
-- Name: stylists_stylist_name_key; Type: CONSTRAINT; Schema: public; Owner: home; Tablespace: 
--

ALTER TABLE ONLY stylists
    ADD CONSTRAINT stylists_stylist_name_key UNIQUE (stylist_name);


--
-- Name: visits_pkey; Type: CONSTRAINT; Schema: public; Owner: home; Tablespace: 
--

ALTER TABLE ONLY visits
    ADD CONSTRAINT visits_pkey PRIMARY KEY (id);


--
-- Name: public; Type: ACL; Schema: -; Owner: home
--

REVOKE ALL ON SCHEMA public FROM PUBLIC;
REVOKE ALL ON SCHEMA public FROM home;
GRANT ALL ON SCHEMA public TO home;
GRANT ALL ON SCHEMA public TO PUBLIC;


--
-- PostgreSQL database dump complete
--

