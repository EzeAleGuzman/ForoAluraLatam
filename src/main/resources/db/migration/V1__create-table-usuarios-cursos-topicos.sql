CREATE TABLE usuario (
    id BIGINT NOT NULL AUTO_INCREMENT,
    nombre VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL,
    contrasena VARCHAR(100) NOT NULL,
    perfil VARCHAR(50),
    PRIMARY KEY (id)
);

CREATE TABLE curso (
    id BIGINT NOT NULL AUTO_INCREMENT,
    nombre VARCHAR(100) NOT NULL,
    categoria VARCHAR(50),
    PRIMARY KEY (id)
) ;


CREATE TABLE topico (
    id BIGINT NOT NULL AUTO_INCREMENT,
    titulo VARCHAR(100) NOT NULL,
    mensaje VARCHAR(255) NOT NULL,
    fechadecreacion DATE NOT NULL,
    status BOOLEAN,
    autor_id BIGINT,
    curso_id BIGINT,
    PRIMARY KEY (id),
    FOREIGN KEY (autor_id) REFERENCES usuario(id),
    FOREIGN KEY (curso_id) REFERENCES curso(id)
);

CREATE TABLE respuesta (
    id BIGINT NOT NULL AUTO_INCREMENT,
    mensaje VARCHAR(255) NOT NULL,
    id_topico BIGINT,
    autor_id BIGINT,
    fechaCreacion TIMESTAMP,
    solucion BOOLEAN,
    PRIMARY KEY (id),
    FOREIGN KEY (autor_id) REFERENCES usuario(id),
    FOREIGN KEY (id_topico) REFERENCES topico(id)
);


