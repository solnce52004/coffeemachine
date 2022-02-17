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

CREATE TABLE "public"."resources"
(
    "id" BIGINT NOT NULL,
    "water" INTEGER NOT NULL,
    "coffee" INTEGER NOT NULL,
    "resource_uuid" CHARACTER VARYING(255) NOT NULL,
    "created_at" TIMESTAMP WITHOUT TIME ZONE DEFAULT CURRENT_TIMESTAMP NOT NULL,
    "updated_at" TIMESTAMP WITHOUT TIME ZONE DEFAULT CURRENT_TIMESTAMP NOT NULL
);

CREATE SEQUENCE "public"."resources_id_seq"
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

ALTER SEQUENCE "public"."resources_id_seq" OWNED BY "public"."resources"."id";
ALTER TABLE ONLY "public"."resources"
    ALTER COLUMN "id" SET DEFAULT NEXTVAL('public.resources_id_seq'::REGCLASS);

ALTER TABLE ONLY "public"."resources"
    ADD CONSTRAINT "resources_pkey" PRIMARY KEY ("id");

CREATE INDEX "resources_resource_uuid_index"
    ON "resources" ("resource_uuid");
