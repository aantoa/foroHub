-- Perfiles
INSERT INTO perfiles (id, nombre) VALUES
  (1, 'ROLE_ADMIN'),
  (2, 'ROLE_MODERATOR'),
  (3, 'ROLE_PROFESSOR'),
  (4, 'ROLE_USER')
ON DUPLICATE KEY UPDATE nombre = VALUES(nombre);

-- Usuario administrador
INSERT INTO usuarios (id, nombre, email, contrasena)
VALUES (1, 'Administrador', 'admin@forohub.com',
        '$2a$10$8pYT/HgUccz6t0RcttRciOge3aVgq7E3Ql2RejAEXYYBk3gOSwZb2') -- admin123
ON DUPLICATE KEY UPDATE email = VALUES(email);

-- Asignar rol ADMIN al usuario administrador
INSERT INTO usuarios_perfiles (usuario_id, perfil_id)
VALUES (1, 1)
ON DUPLICATE KEY UPDATE perfil_id = VALUES(perfil_id);

-- Usuario normal
INSERT INTO usuarios (id, nombre, email, contrasena)
VALUES (2, 'Usuario Estudiante', 'user_student@forohub.com',
        '$2a$10$WjO0N5hiFeRPlg4Cv2PO7e16pG3AytbdrWixozIoA4aX1RpiCqfje') -- user123
ON DUPLICATE KEY UPDATE email = VALUES(email);

INSERT INTO usuarios_perfiles (usuario_id, perfil_id)
VALUES (2, 2)
ON DUPLICATE KEY UPDATE perfil_id = VALUES(perfil_id);

-- Cursos
INSERT INTO cursos (id, nombre, categoria) VALUES
  (1, 'Java Backend', 'PROGRAMACION'),
  (2, 'Spring Boot', 'FRAMEWORK')
ON DUPLICATE KEY UPDATE nombre = VALUES(nombre), categoria = VALUES(categoria);