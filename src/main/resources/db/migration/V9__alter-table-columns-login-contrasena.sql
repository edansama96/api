ALTER TABLE usuarios
DROP INDEX contrasena;

ALTER TABLE usuarios
ADD UNIQUE (login);