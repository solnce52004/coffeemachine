\CONNECT coffeemachine

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

CREATE TABLE "public"."coffee_machines"
(
    "id" BIGINT NOT NULL,
    "state" CHARACTER VARYING(255) NOT NULL,
    "resource_id" BIGINT NOT NULL,
    "machine_uuid" CHARACTER VARYING(255) NOT NULL,
    "machine_id" CHARACTER VARYING(255) NOT NULL,
    "created_at" TIMESTAMP WITHOUT TIME ZONE DEFAULT CURRENT_TIMESTAMP NOT NULL,
    "updated_at" TIMESTAMP WITHOUT TIME ZONE DEFAULT CURRENT_TIMESTAMP NOT NULL
);

CREATE SEQUENCE "public"."coffee_machines_id_seq"
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

ALTER SEQUENCE "public"."coffee_machines_id_seq" OWNED BY "public"."coffee_machines"."id";
ALTER TABLE ONLY "public"."coffee_machines"
    ALTER COLUMN "id" SET DEFAULT NEXTVAL('public.coffee_machines_id_seq'::REGCLASS);

ALTER TABLE ONLY "public"."coffee_machines"
    ADD CONSTRAINT "coffee_machines_pkey" PRIMARY KEY ("id");

ALTER TABLE ONLY "public"."coffee_machines"
    ADD CONSTRAINT "fkafvosslg3i0qvjr0q7c1toata" FOREIGN KEY ("resource_id") REFERENCES "public"."resources" ("id");

CREATE INDEX "coffee_machines_machine_uuid_index"
    ON "coffee_machines" ("machine_uuid");