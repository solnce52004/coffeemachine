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

--

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