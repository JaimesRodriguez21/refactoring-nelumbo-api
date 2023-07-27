INSERT INTO ROL (nombre, fecharegistro, fechaactualizacion) VALUES ('ADMIN', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT INTO ROL (nombre, fecharegistro, fechaactualizacion) VALUES ('CLIENTE', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT INTO ROL (nombre, fecharegistro, fechaactualizacion) VALUES ('SOCIO', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT INTO USUARIO (nombre, email, password, rol_id, fecharegistro, fechaactualizacion) VALUES ('administrador', 'admin@gmail.com', '$2a$10$exhZUCSfmnBkp7tTZKZ0iOx07yWZqvgRV99RUco4XqVRMm5LN45PC', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
