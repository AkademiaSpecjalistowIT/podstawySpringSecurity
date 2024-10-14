INSERT INTO users (username, password, enabled) VALUES
('admin', '{noop}password', true);

INSERT INTO authorities (username, authority) VALUES
('admin', 'ROLE_ADMIN');