DROP TABLE IF EXISTS customer;

-- Crear la tabla customer
CREATE TABLE customer
(
  id         INT AUTO_INCREMENT PRIMARY KEY,
  version    INT NOT NULL,
  created_at TIMESTAMP NOT NULL,
  modified_at TIMESTAMP,
  first_name  VARCHAR(255) NOT NULL,
  last_name   VARCHAR(255) NOT NULL,
  email_id   VARCHAR(255) NOT NULL
);
