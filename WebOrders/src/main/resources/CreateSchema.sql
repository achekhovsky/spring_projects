DROP TABLE IF EXISTS orderimage CASCADE;

DROP TABLE IF EXISTS j4jorders CASCADE;

DROP SEQUENCE IF EXISTS order_id_seq;

DROP SEQUENCE IF EXISTS orderimg_id_seq;

CREATE SEQUENCE order_id_seq INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 9223372036854775807 CACHE 1;

ALTER SEQUENCE order_id_seq OWNER TO "Alex";

CREATE SEQUENCE orderimg_id_seq INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 9223372036854775807 CACHE 1;

ALTER SEQUENCE orderimg_id_seq OWNER TO "Alex";

CREATE TABLE orderimage (id integer NOT NULL DEFAULT nextval('orderimg_id_seq'::regclass), img bytea, version bigint NOT NULL DEFAULT 0, CONSTRAINT orderimage_pkey PRIMARY KEY (id));

CREATE TABLE j4jorders (id integer NOT NULL DEFAULT nextval('order_id_seq'::regclass), createdate text NOT NULL, description character varying(255), done boolean NOT NULL DEFAULT false, name character varying(255), version bigint NOT NULL DEFAULT 0, oi_fk integer, CONSTRAINT j4jorders_pkey PRIMARY KEY (id), CONSTRAINT uk_order UNIQUE (id, name), CONSTRAINT oi_fk FOREIGN KEY (oi_fk) REFERENCES orderimage (id) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE CASCADE);

ALTER TABLE j4jorders OWNER to "Alex";

ALTER TABLE orderimage OWNER to "Alex";