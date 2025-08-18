CREATE TABLE topicos (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  titulo VARCHAR(160) NOT NULL,
  mensaje TEXT NOT NULL,
  fecha_creacion DATETIME NOT NULL,
  status VARCHAR(20) NOT NULL,
  autor_id BIGINT NOT NULL,
  curso_id BIGINT NOT NULL,
  UNIQUE KEY uk_topico_titulo_mensaje (titulo, mensaje(255)),
  CONSTRAINT fk_topico_autor FOREIGN KEY (autor_id) REFERENCES usuarios(id),
  CONSTRAINT fk_topico_curso FOREIGN KEY (curso_id) REFERENCES cursos(id)
);
