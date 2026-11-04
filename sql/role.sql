-- Table: public.role

-- DROP TABLE IF EXISTS public.role;

CREATE TABLE IF NOT EXISTS public.role
(
    id bigint NOT NULL,
    rolename character varying,
    CONSTRAINT role_pkey PRIMARY KEY (id)
)
INSERT INTO role (id, name)
VALUES
    (1, 'ADMIN'),
    (2, 'DEFAULT'),
    (3, 'PREMIUM');