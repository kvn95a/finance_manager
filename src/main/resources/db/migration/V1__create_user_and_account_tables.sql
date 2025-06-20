CREATE TABLE users (
  id BIGSERIAL PRIMARY KEY, -- using BIGSERIAL to avoid issues with JPA long mapping.
  username VARCHAR(50) NOT NULL UNIQUE,
  password_hash VARCHAR(100) NOT NULL,
  email VARCHAR(100) NOT NULL UNIQUE,
  created_at TIMESTAMP DEFAULT NOW()
);

CREATE TABLE accounts (
  id BIGSERIAL PRIMARY KEY, -- using BIGSERIAL to avoid issues with JPA long mapping.
  user_id INTEGER NOT NULL REFERENCES users(id),
  name VARCHAR(100) NOT NULL,
  type VARCHAR(20) NOT NULL,
  starting_balance NUMERIC(12,2) NOT NULL DEFAULT 0,
  created_at TIMESTAMP DEFAULT NOW()
);