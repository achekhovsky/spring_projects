FROM postgres:latest

LABEL maintainer="aCH"

#USER ${UID}:${GID}
RUN echo "host    all    all    0.0.0.0/0    md5" >> /var/lib/postgresql/data/pg_hba.conf

ENV POSTGRES_USER Alex
ENV POSTGRES_PASSWORD 123
ENV POSTGRES_DB j4jcui
