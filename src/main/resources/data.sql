INSERT INTO customer (version, created_at, modified_at, first_name, last_name, email_id)
VALUES
  (1, CURRENT_TIMESTAMP, NULL, 'John', 'Doe', CONCAT('john.doe', CAST(FLOOR(RAND() * 1000) AS VARCHAR), '@example.com')),
  (1, CURRENT_TIMESTAMP, NULL, 'Jane', 'Smith', CONCAT('jane.smith', CAST(FLOOR(RAND() * 1000) AS VARCHAR), '@example.com')),
  (1, CURRENT_TIMESTAMP, NULL, 'Bob', 'Johnson', CONCAT('bob.johnson', CAST(FLOOR(RAND() * 1000) AS VARCHAR), '@example.com'))
  ;