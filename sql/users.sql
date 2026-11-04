CREATE TABLE IF NOT EXISTS public.users
(
    id bigint,
    name character varying,
    surname character varying,
    email character varying,
    CONSTRAINT users_pkey PRIMARY KEY (id)
)

INSERT INTO users(id, name, surname, email)
VALUES
    (1, 'Andrey', 'Golovin', 'andrey.golov@gmail.com'),
    (2, 'Valerii', 'Relantii', 'valvar2003@gmail.com'),
    (3, 'Irina', 'Luchina', 'iraluchina@mail.ru'),
    (4, 'Yan', 'Prohor', 'yanchiksss@gmail.com'),
    (5, 'Nadezhda', 'Valeeva', 'nadyavall@gmail.com'),
    (6, 'Dmitrii', 'Karchevich', 'dm.kar94ch@mail.ru'),
    (7, 'Olga', 'Renatova', 'olgarenatova@mai.ru'),
    (8, 'Nikita', 'Tsaruch', 'niktsaruchh@gmail.com'),
    (9, 'Grisha', 'Sokol', 'grishsok@mail.ru'),
    (10, 'Roman', 'Surovich', 'rom4iksurov1ch@mail.ru'),
    (11, 'Sergey','Tersan', 'ser.tersan@gmail.com'),
    (12, 'Alesya', 'Haripova', 'alesya99haripova@mail.ru'),
    (13, 'Herry', 'Belman', 'herrybelman@gmail.com'),
    (14, 'Kostya', 'Rehan', 'kost9rehh@mail.ru'),
    (15, 'Yana', 'Novikova', 'yananovikova@gmail.com');
