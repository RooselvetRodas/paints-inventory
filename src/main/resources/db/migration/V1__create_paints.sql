CREATE TABLE IF NOT EXISTS paints (
    id BIGSERIAL PRIMARY KEY,
    color_name VARCHAR(150) NOT NULL,        
    type VARCHAR(30) NOT NULL,               
    brand VARCHAR(100) NOT NULL,             
    code VARCHAR(50),                        
    quantity INTEGER NOT NULL DEFAULT 0 
        CHECK (quantity >= 0),               
    data TIMESTAMP WITHOUT TIME ZONE NOT NULL 
        DEFAULT NOW(),                       
    CONSTRAINT ux_paints_brand_name UNIQUE (brand, color_name)  
);

CREATE UNIQUE INDEX IF NOT EXISTS ux_paints_brand_code_not_null
ON paints(brand, code)
WHERE code IS NOT NULL;