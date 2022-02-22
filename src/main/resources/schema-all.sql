DROP TABLE IF EXISTS employee_details_tab;

CREATE employee_details_tab   (
    id BIGINT GENERATED ALWAYS AS IDENTITY,
    name VARCHAR(100),
    email VARCHAR(100),
    address VARCHAR(150),
    telephone VARCHAR(50)
);