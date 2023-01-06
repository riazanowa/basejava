create table public.resume
(
    full_name text,
    uuid      varchar not null
        constraint resume_pk
            primary key
);

create table public.contact
(
    id          serial
        constraint contact_pk
            primary key,
    type        text     not null,
    value       text     not null,
    resume_uuid varchar not null
        constraint contact_resume_uuid_fk
            references public.resume
            on delete cascade
);

create unique index contact_uuid_type_index
    on public.contact (resume_uuid, type);


