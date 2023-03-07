--liquibase formatted sql

--changeset dongp:0
CREATE TABLE public.contact (
	active bool NULL DEFAULT true,
	id uuid NOT NULL,
	phone varchar(20) NULL,
	email varchar(128) NULL,
	web varchar(256) NULL,
	street varchar(128) NOT NULL,
	city varchar(64) NOT NULL,
	state varchar(64) NOT NULL,
	zip varchar(64) NOT NULL,
	country varchar(64) NOT NULL,
	created_date timestamp NOT NULL,
	created_by varchar(64) NOT NULL,
	updated_date timestamp NOT NULL,
	updated_by varchar(64) NOT NULL,
	CONSTRAINT contact_pkey PRIMARY KEY (id)
);

