CREATE TABLE IF NOT EXISTS public.covers
(
    id INTEGER NOT NULL,
    name character varying,
    CONSTRAINT covers_pkey PRIMARY KEY (id)
)
INSERT into covers (id, name)
VALUES
    (1, 'SOFT'),
    (2, 'HARD'),
    (3, 'SPECIAL');

