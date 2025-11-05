-- Add new password column
ALTER TABLE users
ADD COLUMN password VARCHAR(255) NOT NULL AFTER email;

-- Change id column to UUID
-- Step 1: Add a temporary UUID column
ALTER TABLE users
ADD COLUMN uuid CHAR(36);

-- Step 2: Fill UUID values for existing rows
UPDATE users
SET uuid = (SELECT REPLACE(UUID(), '-', ''));

-- Step 3: Drop the old primary key
ALTER TABLE users
DROP PRIMARY KEY,
DROP COLUMN id;

-- Step 4: Rename uuid column to id and make it the new primary key
ALTER TABLE users
CHANGE COLUMN uuid id CHAR(36) NOT NULL,
ADD PRIMARY KEY (id);
