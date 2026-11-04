CREATE TABLE public.books_covers
(
    book_id bigint not null references bookstore_bh.id,
    cover_id bigint not null references covers.id,
    primary key(book_id, cover_id)
)
INSERT into books_covers
values
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
    (15, 2),
    (16, 3),
    (17, 2),
    (18, 3),
    (19, 2),
    (20, 3),
    (21, 2),
    (22, 3),
    (23, 2),
    (24, 3),
    (25, 2),
    (26, 3),
    (27, 2),
    (28, 3),
    (29, 2),
    (30, 3),
    (31, 2);