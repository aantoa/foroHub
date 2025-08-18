CREATE TABLE respuestas (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  mensaje TEXT NOT NULL,
  fecha_creacion DATETIME NOT NULL,
  solucion TINYINT(1) NOT NULL DEFAULT 0,
  autor_id BIGINT NOT NULL,
  topico_id BIGINT NOT NULL,
  CONSTRAINT fk_resp_autor FOREIGN KEY (autor_id) REFERENCES usuarios(id),
  CONSTRAINT fk_resp_topico FOREIGN KEY (topico_id) REFERENCES topicos(id)
);