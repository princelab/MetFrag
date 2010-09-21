/* Drop Tables */

DROP TABLE IF EXISTS NAME;
DROP TABLE IF EXISTS SUBSTANCE;
DROP TABLE IF EXISTS COMPOUND;
DROP TABLE IF EXISTS LIBRARY;




/* Create Tables */

CREATE TABLE COMPOUND
(
	COMPOUND_ID SERIAL NOT NULL,
	MOL_STRUCTURE MOLECULE NOT NULL,
	EXACT_MASS DECIMAL(8,4) NOT NULL,
	FORMULA VARCHAR NOT NULL,
	SMILES VARCHAR NOT NULL,
	INCHI VARCHAR NOT NULL,
	-- First part of the InChi key (skeleton)
	INCHI_KEY_1 VARCHAR(14) NOT NULL,
	-- Second part
	INCHI_KEY_2 VARCHAR(10) NOT NULL,
	-- The last part of the InChI key.
	INCHI_KEY_3 VARCHAR(1),
	PRIMARY KEY (COMPOUND_ID)
) WITHOUT OIDS;


ALTER SEQUENCE COMPOUND_COMPOUND_ID_SEQ INCREMENT 1 MINVALUE 0;


CREATE TABLE LIBRARY
(
	LIBRARY_ID SERIAL NOT NULL,
	LIBRARY_NAME VARCHAR,
	PRIMARY KEY (LIBRARY_ID)
) WITHOUT OIDS;


CREATE TABLE NAME
(
	NAME VARCHAR,
	SUBSTANCE_ID INTEGER NOT NULL
) WITHOUT OIDS;


CREATE TABLE SUBSTANCE
(
	SUBSTANCE_ID SERIAL NOT NULL,
	LIBRARY_ID INTEGER NOT NULL,
	COMPOUND_ID INTEGER NOT NULL,
	ACCESSION VARCHAR,
	PRIMARY KEY (SUBSTANCE_ID)
) WITHOUT OIDS;



/* Create Foreign Keys */

ALTER TABLE SUBSTANCE
	ADD FOREIGN KEY (COMPOUND_ID)
	REFERENCES COMPOUND (COMPOUND_ID)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE SUBSTANCE
	ADD FOREIGN KEY (LIBRARY_ID)
	REFERENCES LIBRARY (LIBRARY_ID)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE NAME
	ADD FOREIGN KEY (SUBSTANCE_ID)
	REFERENCES SUBSTANCE (SUBSTANCE_ID)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;



/* Comments */

COMMENT ON COLUMN COMPOUND.INCHI_KEY_1 IS 'First part of the InChi key (skeleton)';
COMMENT ON COLUMN COMPOUND.INCHI_KEY_2 IS 'Second part';
COMMENT ON COLUMN COMPOUND.INCHI_KEY_3 IS 'The last part of the InChI key.';



/* Insert standard database which are imported */
insert into library(library_name) values ('kegg');
insert into library(library_name) values ('pubchem');



