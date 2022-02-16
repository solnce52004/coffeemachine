--
-- PostgreSQL database dump
--

-- Dumped from database version 14.2
-- Dumped by pg_dump version 14.2

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

DROP DATABASE IF EXISTS coffeemachine;
--
-- Name: coffeemachine; Type: DATABASE; Schema: -; Owner: admin
--

CREATE DATABASE coffeemachine WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE = 'C';


ALTER DATABASE coffeemachine OWNER TO admin;

\connect coffeemachine

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: action; Type: TABLE; Schema: public; Owner: admin
--

CREATE TABLE public.action (
    id bigint NOT NULL,
    name character varying(255),
    spel character varying(255)
);


ALTER TABLE public.action OWNER TO admin;

--
-- Name: deferred_events; Type: TABLE; Schema: public; Owner: admin
--

CREATE TABLE public.deferred_events (
    jpa_repository_state_id bigint NOT NULL,
    deferred_events character varying(255)
);


ALTER TABLE public.deferred_events OWNER TO admin;

--
-- Name: guard; Type: TABLE; Schema: public; Owner: admin
--

CREATE TABLE public.guard (
    id bigint NOT NULL,
    name character varying(255),
    spel character varying(255)
);


ALTER TABLE public.guard OWNER TO admin;

--
-- Name: hibernate_sequence; Type: SEQUENCE; Schema: public; Owner: admin
--

CREATE SEQUENCE public.hibernate_sequence
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.hibernate_sequence OWNER TO admin;

--
-- Name: state; Type: TABLE; Schema: public; Owner: admin
--

CREATE TABLE public.state (
    id bigint NOT NULL,
    initial_state boolean,
    kind integer,
    machine_id character varying(255),
    region character varying(255),
    state character varying(255),
    submachine_id character varying(255),
    initial_action_id bigint,
    parent_state_id bigint
);


ALTER TABLE public.state OWNER TO admin;

--
-- Name: state_entry_actions; Type: TABLE; Schema: public; Owner: admin
--

CREATE TABLE public.state_entry_actions (
    jpa_repository_state_id bigint NOT NULL,
    entry_actions_id bigint NOT NULL
);


ALTER TABLE public.state_entry_actions OWNER TO admin;

--
-- Name: state_exit_actions; Type: TABLE; Schema: public; Owner: admin
--

CREATE TABLE public.state_exit_actions (
    jpa_repository_state_id bigint NOT NULL,
    exit_actions_id bigint NOT NULL
);


ALTER TABLE public.state_exit_actions OWNER TO admin;

--
-- Name: state_machine; Type: TABLE; Schema: public; Owner: admin
--

CREATE TABLE public.state_machine (
    machine_id character varying(255) NOT NULL,
    state character varying(255),
    state_machine_context oid
);


ALTER TABLE public.state_machine OWNER TO admin;

--
-- Name: state_state_actions; Type: TABLE; Schema: public; Owner: admin
--

CREATE TABLE public.state_state_actions (
    jpa_repository_state_id bigint NOT NULL,
    state_actions_id bigint NOT NULL
);


ALTER TABLE public.state_state_actions OWNER TO admin;

--
-- Name: transition; Type: TABLE; Schema: public; Owner: admin
--

CREATE TABLE public.transition (
    id bigint NOT NULL,
    event character varying(255),
    kind integer,
    machine_id character varying(255),
    guard_id bigint,
    source_id bigint,
    target_id bigint
);


ALTER TABLE public.transition OWNER TO admin;

--
-- Name: transition_actions; Type: TABLE; Schema: public; Owner: admin
--

CREATE TABLE public.transition_actions (
    jpa_repository_transition_id bigint NOT NULL,
    actions_id bigint NOT NULL
);


ALTER TABLE public.transition_actions OWNER TO admin;

--
-- Data for Name: action; Type: TABLE DATA; Schema: public; Owner: admin
--

COPY public.action (id, name, spel) FROM stdin;
\.


--
-- Data for Name: deferred_events; Type: TABLE DATA; Schema: public; Owner: admin
--

COPY public.deferred_events (jpa_repository_state_id, deferred_events) FROM stdin;
\.


--
-- Data for Name: guard; Type: TABLE DATA; Schema: public; Owner: admin
--

COPY public.guard (id, name, spel) FROM stdin;
\.


--
-- Data for Name: state; Type: TABLE DATA; Schema: public; Owner: admin
--

COPY public.state (id, initial_state, kind, machine_id, region, state, submachine_id, initial_action_id, parent_state_id) FROM stdin;
\.


--
-- Data for Name: state_entry_actions; Type: TABLE DATA; Schema: public; Owner: admin
--

COPY public.state_entry_actions (jpa_repository_state_id, entry_actions_id) FROM stdin;
\.


--
-- Data for Name: state_exit_actions; Type: TABLE DATA; Schema: public; Owner: admin
--

COPY public.state_exit_actions (jpa_repository_state_id, exit_actions_id) FROM stdin;
\.


--
-- Data for Name: state_machine; Type: TABLE DATA; Schema: public; Owner: admin
--

COPY public.state_machine (machine_id, state, state_machine_context) FROM stdin;
\.


--
-- Data for Name: state_state_actions; Type: TABLE DATA; Schema: public; Owner: admin
--

COPY public.state_state_actions (jpa_repository_state_id, state_actions_id) FROM stdin;
\.


--
-- Data for Name: transition; Type: TABLE DATA; Schema: public; Owner: admin
--

COPY public.transition (id, event, kind, machine_id, guard_id, source_id, target_id) FROM stdin;
\.


--
-- Data for Name: transition_actions; Type: TABLE DATA; Schema: public; Owner: admin
--

COPY public.transition_actions (jpa_repository_transition_id, actions_id) FROM stdin;
\.


--
-- Name: hibernate_sequence; Type: SEQUENCE SET; Schema: public; Owner: admin
--

SELECT pg_catalog.setval('public.hibernate_sequence', 1, false);


--
-- Name: action action_pkey; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.action
    ADD CONSTRAINT action_pkey PRIMARY KEY (id);


--
-- Name: guard guard_pkey; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.guard
    ADD CONSTRAINT guard_pkey PRIMARY KEY (id);


--
-- Name: state_entry_actions state_entry_actions_pkey; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.state_entry_actions
    ADD CONSTRAINT state_entry_actions_pkey PRIMARY KEY (jpa_repository_state_id, entry_actions_id);


--
-- Name: state_exit_actions state_exit_actions_pkey; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.state_exit_actions
    ADD CONSTRAINT state_exit_actions_pkey PRIMARY KEY (jpa_repository_state_id, exit_actions_id);


--
-- Name: state_machine state_machine_pkey; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.state_machine
    ADD CONSTRAINT state_machine_pkey PRIMARY KEY (machine_id);


--
-- Name: state state_pkey; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.state
    ADD CONSTRAINT state_pkey PRIMARY KEY (id);


--
-- Name: state_state_actions state_state_actions_pkey; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.state_state_actions
    ADD CONSTRAINT state_state_actions_pkey PRIMARY KEY (jpa_repository_state_id, state_actions_id);


--
-- Name: transition_actions transition_actions_pkey; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.transition_actions
    ADD CONSTRAINT transition_actions_pkey PRIMARY KEY (jpa_repository_transition_id, actions_id);


--
-- Name: transition transition_pkey; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.transition
    ADD CONSTRAINT transition_pkey PRIMARY KEY (id);


--
-- Name: deferred_events fk_state_deferred_events; Type: FK CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.deferred_events
    ADD CONSTRAINT fk_state_deferred_events FOREIGN KEY (jpa_repository_state_id) REFERENCES public.state(id);


--
-- Name: state_entry_actions fk_state_entry_actions_a; Type: FK CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.state_entry_actions
    ADD CONSTRAINT fk_state_entry_actions_a FOREIGN KEY (entry_actions_id) REFERENCES public.action(id);


--
-- Name: state_entry_actions fk_state_entry_actions_s; Type: FK CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.state_entry_actions
    ADD CONSTRAINT fk_state_entry_actions_s FOREIGN KEY (jpa_repository_state_id) REFERENCES public.state(id);


--
-- Name: state_exit_actions fk_state_exit_actions_a; Type: FK CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.state_exit_actions
    ADD CONSTRAINT fk_state_exit_actions_a FOREIGN KEY (exit_actions_id) REFERENCES public.action(id);


--
-- Name: state_exit_actions fk_state_exit_actions_s; Type: FK CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.state_exit_actions
    ADD CONSTRAINT fk_state_exit_actions_s FOREIGN KEY (jpa_repository_state_id) REFERENCES public.state(id);


--
-- Name: state fk_state_initial_action; Type: FK CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.state
    ADD CONSTRAINT fk_state_initial_action FOREIGN KEY (initial_action_id) REFERENCES public.action(id);


--
-- Name: state fk_state_parent_state; Type: FK CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.state
    ADD CONSTRAINT fk_state_parent_state FOREIGN KEY (parent_state_id) REFERENCES public.state(id);


--
-- Name: state_state_actions fk_state_state_actions_a; Type: FK CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.state_state_actions
    ADD CONSTRAINT fk_state_state_actions_a FOREIGN KEY (state_actions_id) REFERENCES public.action(id);


--
-- Name: state_state_actions fk_state_state_actions_s; Type: FK CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.state_state_actions
    ADD CONSTRAINT fk_state_state_actions_s FOREIGN KEY (jpa_repository_state_id) REFERENCES public.state(id);


--
-- Name: transition_actions fk_transition_actions_a; Type: FK CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.transition_actions
    ADD CONSTRAINT fk_transition_actions_a FOREIGN KEY (actions_id) REFERENCES public.action(id);


--
-- Name: transition_actions fk_transition_actions_t; Type: FK CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.transition_actions
    ADD CONSTRAINT fk_transition_actions_t FOREIGN KEY (jpa_repository_transition_id) REFERENCES public.transition(id);


--
-- Name: transition fk_transition_guard; Type: FK CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.transition
    ADD CONSTRAINT fk_transition_guard FOREIGN KEY (guard_id) REFERENCES public.guard(id);


--
-- Name: transition fk_transition_source; Type: FK CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.transition
    ADD CONSTRAINT fk_transition_source FOREIGN KEY (source_id) REFERENCES public.state(id);


--
-- Name: transition fk_transition_target; Type: FK CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.transition
    ADD CONSTRAINT fk_transition_target FOREIGN KEY (target_id) REFERENCES public.state(id);


--
-- PostgreSQL database dump complete
--

