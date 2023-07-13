CREATE TABLE  IF NOT EXISTS cloth (   cloth_Id BIGINT PRIMARY KEY AUTO_INCREMENT,
                      date_time VARCHAR(255),
                      price FLOAT NOT NULL,
                      cloth_material VARCHAR(255),
                      cloth_size ENUM('EXTRA_EXTRA_LARGE', 'EXTRA_LARGE', 'EXTRA_SMALL', 'LARGE', 'MEDIUM', 'SMALL'),
                      cloth_style VARCHAR(255),
                      cloth_type VARCHAR(255),
                      collection_name VARCHAR(255),
                      cover_image_file_name VARCHAR(255),
                      designer_name VARCHAR(255)
) engine=InnoDB;





