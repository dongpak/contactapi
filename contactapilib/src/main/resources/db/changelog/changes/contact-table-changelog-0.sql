--liquibase formatted sql

--changeset dongp:0
CREATE TABLE contact (
	id              UUID PRIMARY KEY,
	active          BOOL NOT NULL DEFAULT true,
	phone           TEXT,
	email           TEXT,
	web             TEXT,
	street          TEXT NOT NULL,
	city            TEXT NOT NULL,
	state           TEXT NOT NULL,
	zip             TEXT NOT NULL,
	country         TEXT NOT NULL,
	created_date    timestamp NOT NULL,
	created_by      TEXT NOT NULL,
	updated_date    timestamp NOT NULL,
	updated_by      TEXT NOT NULL
);

