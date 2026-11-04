CREATE TABLE public.users_roles
(
    user_id bigint not null references usersdatabase.id,
    role_id bigint not null references role.id,
    primary key(user_id, role_id)

)
INSERT INTO users_roles
VALUES
    (1, 2),
    (2, 3),
    (3, 2),
    (4, 3),
    (5, 2),
    (6, 3),
    (7, 2),
    (8, 3),
    (9, 2),
    (10, 3),
    (11, 2),
    (12, 3),
    (13, 2),
    (14, 3),
    (15, 3);