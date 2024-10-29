-- Table Users
INSERT INTO users (id, email, password) VALUES (1, 'testuser@example.com', 'password');
INSERT INTO users (id, email, password) VALUES (2, 'otheruser@example.com', 'password');

-- Table Channels
INSERT INTO channels (id, channel_name, owner_id) VALUES (1, 'Test Channel 1', 1);
INSERT INTO channels (id, channel_name, owner_id) VALUES (2, 'Test Channel 2', 2);

-- Table Fields
INSERT INTO fields (id, field_name, data_type, channel_id) VALUES (1, 'Field1', 'String', 1);
INSERT INTO fields (id, field_name, data_type, channel_id) VALUES (2, 'Field2', 'Integer', 2);

-- Table Entries
INSERT INTO entries (id, value, field_id) VALUES (1, 'Sample Value 1', 1);
INSERT INTO entries (id, value, field_id) VALUES (2, 'Sample Value 2', 2);
