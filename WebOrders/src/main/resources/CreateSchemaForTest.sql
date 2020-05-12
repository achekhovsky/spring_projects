DROP TABLE IF EXISTS orderimage CASCADE;

DROP TABLE IF EXISTS j4jorders CASCADE;

CREATE TABLE orderimage (id integer auto_increment, img bytea, version bigint NOT NULL DEFAULT 0, CONSTRAINT orderimage_pkey PRIMARY KEY (id));

CREATE TABLE j4jorders (id integer auto_increment,createdate text NOT NULL, description character varying(255), done boolean NOT NULL DEFAULT false, name character varying(255), version bigint NOT NULL DEFAULT 0, oi_fk integer, CONSTRAINT j4jorders_pkey PRIMARY KEY (id), CONSTRAINT uk_order UNIQUE (id, name), CONSTRAINT oi_fk FOREIGN KEY (oi_fk) REFERENCES orderimage (id) ON UPDATE NO ACTION ON DELETE CASCADE);

