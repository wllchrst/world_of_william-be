
CREATE TABLE search_histories (
    id CHAR(36) PRIMARY KEY,
    user_id CHAR(36) NOT NULL,
    search_query VARCHAR(255),

    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    deleted_at TIMESTAMP,

    CONSTRAINT fk_search_history_user
        FOREIGN KEY (user_id)
        REFERENCES users(id)
        ON DELETE CASCADE
);

CREATE TABLE search_results (
    id CHAR(36) PRIMARY KEY,
    search_history_id CHAR(36) NOT NULL,
    link VARCHAR(255) NOT NULL,
    title VARCHAR(255) NOT NULL,
    snippet VARCHAR(1000) NOT NULL,

    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    deleted_at TIMESTAMP,

    CONSTRAINT fk_search_result_history
        FOREIGN KEY (search_history_id)
        REFERENCES search_histories(id)
        ON DELETE CASCADE
);