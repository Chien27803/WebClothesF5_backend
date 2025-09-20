
drop database if exists test;

create database test;

use test;

CREATE TABLE address (
                         city_id BIGINT,
                         district_id BIGINT,
                         id BIGINT NOT NULL AUTO_INCREMENT,
                         ward_id BIGINT,
                         detail VARCHAR(255),
                         PRIMARY KEY (id)
) ENGINE = InnoDB;

--
CREATE TABLE category (
                          deleted TINYINT(1),
                          id BIGINT NOT NULL AUTO_INCREMENT,
                          parent_id BIGINT,
                          name VARCHAR(255),
                          PRIMARY KEY (id)
) ENGINE = InnoDB;

--
CREATE TABLE color (
                       id BIGINT NOT NULL AUTO_INCREMENT,
                       name VARCHAR(255),
                       hex_code VARCHAR(255),
                       PRIMARY KEY (id)
) ENGINE = InnoDB;

--
CREATE TABLE detail (
                        color_id BIGINT,
                        id BIGINT NOT NULL AUTO_INCREMENT,
                        product_id BIGINT,
                        size_id BIGINT,
                        sold_count BIGINT,
                        stock BIGINT,
                        PRIMARY KEY (id)
) ENGINE = InnoDB;

CREATE TABLE discount (
                          deleted TINYINT(1),
                          percent FLOAT(53),
                          id BIGINT NOT NULL AUTO_INCREMENT,
                          PRIMARY KEY (id)
) ENGINE = InnoDB;

CREATE TABLE feedback (
                          deleted TINYINT(1),
                          rating FLOAT(23),
                          feedback_time DATETIME(6),
                          id BIGINT NOT NULL AUTO_INCREMENT,
                          product_id BIGINT,
                          user_id BIGINT,
                          description VARCHAR(255),
                          PRIMARY KEY (id)
) ENGINE = InnoDB;

CREATE TABLE image (
                       id BIGINT NOT NULL AUTO_INCREMENT,
                       product_id BIGINT,
                       src VARCHAR(255),
                       color_id BIGINT,
                       PRIMARY KEY (id)
) ENGINE = InnoDB;

CREATE TABLE order_product (
                               price FLOAT(23),
                               id BIGINT NOT NULL AUTO_INCREMENT,
                               order_id BIGINT NOT NULL,
                               product_id BIGINT NOT NULL,
                               image_id BIGINT,
                               size_id BIGINT,
                               color_id BIGINT,
                               quantity BIGINT,
                               PRIMARY KEY (id)
) ENGINE = InnoDB;

CREATE TABLE orders (
                        deleted TINYINT(1),
                        address_id BIGINT NOT NULL,
                        id BIGINT NOT NULL AUTO_INCREMENT,
                        payment_id BIGINT,
                        time DATETIME(6),
                        user_id BIGINT,
                        status VARCHAR(255),
                        total FLOAT(23),
                        PRIMARY KEY (id)
) ENGINE = InnoDB;

CREATE TABLE payment (
                         id BIGINT NOT NULL AUTO_INCREMENT,
                         user_id BIGINT,
                         code VARCHAR(255),
                         name VARCHAR(255),
                         PRIMARY KEY (id)
) ENGINE = InnoDB;

CREATE TABLE permission (
                            deleted TINYINT(1),
                            id BIGINT NOT NULL AUTO_INCREMENT,
                            description VARCHAR(255),
                            name VARCHAR(255),
                            PRIMARY KEY (id)
) ENGINE = InnoDB;

CREATE TABLE product (
                         deleted BIT,
                         price FLOAT(23),
                         category_id BIGINT,
                         created_at DATETIME(6),
                         id BIGINT NOT NULL AUTO_INCREMENT,
                         quantity BIGINT,
                         updated_at DATETIME(6),
                         description VARCHAR(255),
                         name VARCHAR(255),
                         slug VARCHAR(255),
                         PRIMARY KEY (id)
) ENGINE = InnoDB;

CREATE TABLE product_discount (
                                  discount_id BIGINT,
                                  end_time DATETIME(6),
                                  id BIGINT NOT NULL AUTO_INCREMENT,
                                  product_id BIGINT,
                                  start_time DATETIME(6),
                                  PRIMARY KEY (id)
) ENGINE = InnoDB;

CREATE TABLE product_shoppingcart (
                                      id BIGINT NOT NULL AUTO_INCREMENT,
                                      product_id BIGINT,
                                      quantity BIGINT,
                                      shoppingcart_id BIGINT,
                                      stock BIGINT,
                                      image_id BIGINT,
                                      size_id BIGINT,
                                      color_id BIGINT,
                                      PRIMARY KEY (id)
) ENGINE = InnoDB;

CREATE TABLE role (
                      deleted TINYINT(1),
                      id BIGINT NOT NULL AUTO_INCREMENT,
                      code VARCHAR(255),
                      name VARCHAR(255),
                      PRIMARY KEY (id)
) ENGINE = InnoDB;

CREATE TABLE role_permissions (
                                  permission_id BIGINT NOT NULL,
                                  role_id BIGINT NOT NULL
) ENGINE = InnoDB;

CREATE TABLE shoppingcart (
                              id BIGINT NOT NULL AUTO_INCREMENT,
                              user_id BIGINT,
                              PRIMARY KEY (id)
) ENGINE = InnoDB;

CREATE TABLE size (
                      id BIGINT NOT NULL AUTO_INCREMENT,
                      description VARCHAR(255),
                      name VARCHAR(255),
                      PRIMARY KEY (id)
) ENGINE = InnoDB;

CREATE TABLE user (
                      deleted TINYINT(1),
                      status TINYINT(1),
                      created_at DATETIME(6),
                      id BIGINT NOT NULL AUTO_INCREMENT,
                      updated_at DATETIME(6),
                      avatar VARCHAR(512),
                      email VARCHAR(255),
                      name VARCHAR(255),
                      password VARCHAR(255),
                      phone VARCHAR(255),
                      PRIMARY KEY (id)
) ENGINE = InnoDB;

CREATE TABLE user_address (
                              is_default BIT,
                              address_id BIGINT NOT NULL,
                              id BIGINT NOT NULL AUTO_INCREMENT,
                              user_id BIGINT NOT NULL,
                              PRIMARY KEY (id)
) ENGINE = InnoDB;

CREATE TABLE user_role (
                           role_id BIGINT NOT NULL,
                           user_id BIGINT NOT NULL
) ENGINE = InnoDB;

--     
ALTER TABLE
    detail
    ADD
        CONSTRAINT fk_detail_color_id FOREIGN KEY (color_id) REFERENCES color(id),
ADD
	CONSTRAINT fk_detail_product_id FOREIGN KEY (product_id) REFERENCES product(id),
ADD
	CONSTRAINT fk_detail_size_id FOREIGN KEY (size_id) REFERENCES size(id);

ALTER TABLE
    feedback
    ADD
        CONSTRAINT fk_feedback_product_id FOREIGN KEY (product_id) REFERENCES product(id),
ADD
	CONSTRAINT fk_feedback_user_id FOREIGN KEY (user_id) REFERENCES user(id);

ALTER TABLE
    image
    ADD
        CONSTRAINT fk_image_product_id FOREIGN KEY (product_id) REFERENCES product(id);

ALTER TABLE
    order_product
    ADD
        CONSTRAINT fk_order_product_order_id FOREIGN KEY (order_id) REFERENCES orders(id),
ADD
	CONSTRAINT fk_order_product_product_id FOREIGN KEY (product_id) REFERENCES product(id),
ADD
	CONSTRAINT fk_order_product_size_id FOREIGN KEY (size_id) REFERENCES size(id),
ADD
	CONSTRAINT fk_order_product_color_id FOREIGN KEY (color_id) REFERENCES color(id),
ADD
	CONSTRAINT fk_order_product_image_id FOREIGN KEY (image_id) REFERENCES image(id);

ALTER TABLE
    orders
    ADD
        CONSTRAINT fk_orders_address_id FOREIGN KEY (address_id) REFERENCES address(id),
ADD
	CONSTRAINT fk_orders_payment_id FOREIGN KEY (payment_id) REFERENCES payment(id),
ADD
	CONSTRAINT fk_orders_user_id FOREIGN KEY (user_id) REFERENCES user(id);

ALTER TABLE
    product_discount
    ADD
        CONSTRAINT fk_product_discount_discount_id FOREIGN KEY (discount_id) REFERENCES discount(id),
ADD
	CONSTRAINT fk_product_discount_product_id FOREIGN KEY (product_id) REFERENCES product(id);

ALTER TABLE
    role_permissions
    ADD
        CONSTRAINT fk_role_permissions_permission_id FOREIGN KEY (permission_id) REFERENCES permission(id),
ADD
	CONSTRAINT fk_role_permissions_role_id FOREIGN KEY (role_id) REFERENCES role(id);

ALTER TABLE
    shoppingcart
    ADD
        CONSTRAINT fk_shoppingcart_user_id FOREIGN KEY (user_id) REFERENCES user(id);

ALTER TABLE
    user_address
    ADD
        CONSTRAINT fk_user_address_address_id FOREIGN KEY (address_id) REFERENCES address(id),
ADD
	CONSTRAINT fk_user_address_user_id FOREIGN KEY (user_id) REFERENCES user(id);

ALTER TABLE
    user_role
    ADD
        CONSTRAINT fk_user_role_role_id FOREIGN KEY (role_id) REFERENCES role(id),
ADD
	CONSTRAINT fk_user_role_user_id FOREIGN KEY (user_id) REFERENCES user(id);

ALTER TABLE
    payment
    ADD
        CONSTRAINT fk_payment_user_id FOREIGN KEY (user_id) REFERENCES user(id);

ALTER TABLE
    product_shoppingcart
    ADD
        CONSTRAINT fk_product_shoppingcart_size_id FOREIGN KEY (size_id) REFERENCES size(id),
ADD
	CONSTRAINT fk_product_shoppingcart_color_id FOREIGN KEY (color_id) REFERENCES color(id),
ADD
	CONSTRAINT fk_product_shoppingcart_image_id FOREIGN KEY (image_id) REFERENCES image(id),
ADD
	CONSTRAINT fk_product_shoppingcart_cart_id FOREIGN KEY (shoppingcart_id) REFERENCES shoppingcart(id),
ADD
	CONSTRAINT fk_product_shoppingcart_product_id FOREIGN KEY (product_id) REFERENCES product(id);

-- insert
insert into
    role(deleted, name, code)
values
    (0, "admin", "ADMIN"),
    (0, "user", "USER"),
    (0, "Quản lý sản phẩm", "PRODUCT_MANAGEMENT"),
    (0, "Quản lý quyền", "ROLE_MANAGEMENT"),
    (0, "Quản lý danh mục", "CATEGORY_MANAGEMENT"),
    (0, "Quản lý tài khoản", "ACCOUNT_MANAGEMENT"),
    (0, "Quản lý đơn hàng", "ORDER_MANAGEMENT");


INSERT INTO `permission` VALUES (0, 1, 'Xóa sản phẩm', 'PRODUCT_DELETE');
INSERT INTO `permission` VALUES (0, 2, 'Thêm sản phẩm', 'PRODUCT_ADD');
INSERT INTO `permission` VALUES (0, 3, 'Sửa sản phẩm', 'PRODUCT_UPDATE');
INSERT INTO `permission` VALUES (0, 4, 'Thêm quyền', 'ROLE_ADD');
INSERT INTO `permission` VALUES (0, 5, 'Xóa quyền', 'ROLE_DELETE');
INSERT INTO `permission` VALUES (0, 6, 'Phân quyền', 'ROLE_PERMISSION');
INSERT INTO `permission` VALUES (0, 7, 'Xem quyền', 'ROLE_VIEW');
INSERT INTO `permission` VALUES (0, 8, 'Sửa quyền', 'ROLE_UPDATE');
INSERT INTO `permission` VALUES (0, 9, 'Xem sản phẩm', 'PRODUCT_VIEW');
INSERT INTO `permission` VALUES (0, 10, 'Xem danh mục', 'CATEGORY_VIEW');
INSERT INTO `permission` VALUES (0, 11, 'Thêm danh mục', 'CATEGORY_ADD');
INSERT INTO `permission` VALUES (0, 12, 'Sửa danh mục', 'CATEGORY_UPDATE');
INSERT INTO `permission` VALUES (0, 13, 'Xóa danh mục', 'CATEGORY_DELETE');
INSERT INTO `permission` VALUES (0, 15, 'Xem tài khoản', 'ACCOUNT_VIEW');
INSERT INTO `permission` VALUES (0, 16, 'Thêm tài khoản', 'ACCOUNT_ADD');
INSERT INTO `permission` VALUES (0, 17, 'Sửa tài khoản', 'ACCOUNT_UPDATE');
INSERT INTO `permission` VALUES (0, 18, 'Xóa tài khoản', 'ACCOUNT_DELETE');
INSERT INTO `permission` VALUES (0, 19, 'Xóa đơn hàng', 'ORDER_DELETE');
INSERT INTO `permission` VALUES (0, 20, 'Sửa đơn hàng', 'ORDER_UPDATE');


-- danh muc
INSERT INTO
    category (deleted, id, name, parent_id)
VALUES
    (0, 1, 'Áo', 0),
    (0, 2, 'Quần', 0),
    (0, 3, 'Dép', 0),
    (0, 4, 'Áo thun', 1),
    (0, 5, 'Áo sơ mi', 1),
    (0, 6, 'Áo polo', 1),
    (0, 7, 'Áo hoodie', 1),
    (0, 8, 'Quần âu', 2),
    (0, 9, 'Tông lào', 3),
    (0, 10, 'Quần bò', 2),
    (0, 11, 'Bánh mỳ', 3),
    (0, 12, 'Mũ', 0),
    (0, 13, 'Mũ lưỡi trai', 12),
    (0, 14, 'Mũ phớt', 12),
    (0, 15, 'Giày', 0),
    (0, 16, 'Giày thể thao', 15),
    (0, 17, 'Giày đá bóng', 15),
    (0, 18, 'Áo len cổ lọ', 1),
    (0, 19, 'Áo blazer', 1),
    (0, 20, 'Áo ba lỗ', 1),
    (0, 21, 'Áo khoác', 1),
    (0, 22, 'Áo croptop', 1),
    (0, 23, 'Áo giữ nhiệt', 1);

-- mau sac
INSERT INTO
    color (name, hex_code)
VALUES
    ('Đen', '#000000'),
    ('Trắng', '#FFFFFF'),
    ('Xám', '#808080'),
    ('Kem', '#FFFDD0'),
    ('Be', '#F5F5DC'),
    ('Nâu', '#8B4513'),
    ('Kaki', '#C3B091'),
    ('Đỏ', '#FF0000'),
    ('Hồng', '#FFC0CB'),
    ('Cam', '#FFA500'),
    ('Vàng', '#FFFF00'),
    ('Xanh lá', '#008000'),
    ('Xanh rêu', '#4B5320'),
    ('Xanh dương', '#0000FF'),
    ('Xanh navy', '#000080'),
    ('Xanh da trời', '#87CEEB'),
    ('Xanh ngọc', '#00CED1'),
    ('Tím', '#800080'),
    ('Bordeaux', '#800000');

-- kich thuoc
INSERT INTO
    size (description, name)
VALUES
    (
        'Phù hợp với người cao 1m50–1m55, nặng 40–45kg',
        'XS'
    ),
    (
        'Phù hợp với người cao 1m55–1m60, nặng 45–50kg',
        'S'
    ),
    (
        'Phù hợp với người cao 1m60–1m65, nặng 50–60kg',
        'M'
    ),
    (
        'Phù hợp với người cao 1m65–1m70, nặng 60–70kg',
        'L'
    ),
    (
        'Phù hợp với người cao 1m70–1m75, nặng 70–80kg',
        'XL'
    ),
    (
        'Phù hợp với người cao 1m75–1m80, nặng 80–90kg',
        'XXL'
    ),
    (
        'Phù hợp với người cao trên 1m80, nặng trên 90kg',
        'XXXL'
    );

-- san pham
insert into
    product(
    id,
    deleted,
    price,
    category_id,
    quantity,
    description,
    name,
    slug
)
values
    (
        21,
        0,
        140000,
        17,
        50,
        "Giá thành rẻ, thiết kế chắc chắn, phù hợp với sân phủi và người mới chơi.",
        "Giày Đá Banh Wika 3 Sọc Ct3",
        "giay-da-banh-wika-3-soc-ct3"
    ),
    (
        22,
        0,
        2100000,
        17,
        50,
        "Chất liệu da mềm mại mang lại cảm giác bóng tốt, phù hợp với cầu thủ chơi ở vị trí tiền vệ.",
        "Giày Đá Bóng Firm Ground Copa Pure 3 Elite",
        "giay-da-bong-firm-ground-copa-pure-3-elite"
    ),
    (
        23,
        0,
        1600000,
        17,
        50,
        "Thiết kế đặc biệt ở phần thân giày giúp kiểm soát bóng tốt, phù hợp với cầu thủ thiên về kỹ thuật.",
        "Giày đá Bóng Nam adidas Predator League Firm Ground",
        "giay-da-bong-nam-adidas-predator-league-firm-ground"
    ),
    (
        24,
        0,
        320000,
        17,
        50,
        "Thiết kế hiện đại, ôm chân, hỗ trợ di chuyển linh hoạt trên sân cỏ nhân tạo.",
        "Giày Đá Bóng Sân Cỏ Nhân Tạo Future Z 1.1",
        "giay-da-bong-san-co-nhan-tao-future-z-11"
    ),
    (
        25,
        0,
        230000,
        17,
        50,
        "Chất liệu da PU với bề mặt vân nổi, đế TF bám sân tốt, thiết kế phối màu trắng xanh trẻ trung.",
        "Giày đá bóng Wika Toni Kroos",
        "giay-da-bong-wika-toni-kroos"
    ),
    (
        26,
        0,
        2700000,
        16,
        50,
        "Được thiết kế với công nghệ Air-Sole độc quyền của Nike, kết hợp kiểu dáng hầm hố, cá tính, và phong cách retro độc đáo, Air Jordan đã tạo nên cơn sốt toàn cầu suốt nhiều thập kỷ.",
        "Giày thể thao Nike Air Jordan",
        "giay-the-thao-nike-air-jordan"
    ),
    (
        27,
        0,
        3500000,
        16,
        50,
        "Ultraboost là dòng giày chạy bộ cao cấp của Adidas, nổi bật với công nghệ Boost siêu nhẹ, hoàn trả năng lượng tối đa. Thiết kế ôm chân với vải dệt Primeknit linh hoạt, tạo cảm giác như đi tất.",
        "Giày thể thao Adidas Ultraboost",
        "giay-the-thao-adidas-ultraboost"
    ),
    (
        28,
        0,
        800000,
        16,
        50,
        "Biti’s Hunter là sản phẩm giày thể thao quốc dân của Việt Nam. Thiết kế trẻ trung, đệm EVA êm nhẹ, phần upper thoáng khí và kiểu dáng dễ phối đồ. Giá thành hợp lý, phù hợp với học sinh, sinh viên.",
        "Giày thể thao Biti’s Hunter",
        "giay-the-thao-bitis-hunter"
    ),
    (
        29,
        0,
        2200000,
        16,
        50,
        "MLB Bigball Chunky là dòng giày thể thao thời trang từ thương hiệu MLB (Hàn Quốc), nổi bật với thiết kế đế dày, form 'hầm hố' cá tính. Logo đội bóng Mỹ được in nổi bật tạo điểm nhấn phong cách đường phố.",
        "Giày thể thao MLB Bigball",
        "giay-the-thao-mlb-bigball"
    ),
    (
        30,
        0,
        1800000,
        16,
        50,
        "Nike Court là dòng giày tennis và lifestyle đơn giản nhưng cực kỳ thanh lịch. Đế cao su bền chắc, kiểu dáng cổ điển, dễ phối với quần jeans, kaki hay đồ thể thao.",
        "Giày thể thao Nike Court",
        "giay-the-thao-nike-court"
    );

insert into
    image(product_id, src, color_id)
values
    (
        21,
        "http://res.cloudinary.com/dxx1lgamz/image/upload/693b700b-f883-4828-b724-5aad8a5e6274_giay3soc-den",
        1
    ),
    (
        21,
        "http://res.cloudinary.com/dxx1lgamz/image/upload/a5ef8ad5-ef98-4f67-b6db-ffc7a92cbfbc_giay3soc-do",
        8
    ),
    (
        21,
        "http://res.cloudinary.com/dxx1lgamz/image/upload/18d5fe97-0ef3-4f48-8a89-c232d98a1826_giay3soc-vang",
        11
    ),
    (
        21,
        "http://res.cloudinary.com/dxx1lgamz/image/upload/7fb52a68-5918-460b-9fe4-5e62fc81e72e_giay3soc-xanh-duong",
        14
    ),
    (
        21,
        "http://res.cloudinary.com/dxx1lgamz/image/upload/aece7b05-89ee-4793-95bb-16fdcdab7772_giay3soc-xanh-la",
        12
    ),
    (
        22,
        "http://res.cloudinary.com/dxx1lgamz/image/upload/66464014-4da9-4c4d-89dc-a7fb5b277023_copa-den",
        1
    ),
    (
        22,
        "http://res.cloudinary.com/dxx1lgamz/image/upload/48ad03d4-12fe-48dd-afac-0eee8620bbc8_copa-nau",
        6
    ),
    (
        22,
        "http://res.cloudinary.com/dxx1lgamz/image/upload/2b89634a-4529-42cd-8fca-cd7580304720_copa-trang",
        2
    ),
    (
        22,
        "http://res.cloudinary.com/dxx1lgamz/image/upload/4d144e4c-1b05-4ac4-9c5c-5541ce421e23_copa-xanh-duong",
        14
    ),
    (
        23,
        "http://res.cloudinary.com/dxx1lgamz/image/upload/69f4a684-f0ec-4d99-9f5e-efa8b7c3b25e_predator-league-den",
        1
    ),
    (
        23,
        "http://res.cloudinary.com/dxx1lgamz/image/upload/79f2cc77-8031-4277-9e04-2971f9ad2d39_predator-league-do",
        8
    ),
    (
        23,
        "http://res.cloudinary.com/dxx1lgamz/image/upload/33337d6f-3952-4548-bac8-b6575af724f3_predator-league-xam",
        3
    ),
    (
        23,
        "http://res.cloudinary.com/dxx1lgamz/image/upload/35b0b6e5-dc6f-464a-8058-49242cc25349_predator-league-xanh-duong",
        14
    ),
    (
        24,
        "http://res.cloudinary.com/dxx1lgamz/image/upload/9c5f45fa-43a4-461a-8ba6-0182341177b8_puma-future-trang",
        2
    ),
    (
        24,
        "http://res.cloudinary.com/dxx1lgamz/image/upload/09e82c83-2815-4f4a-a4c4-1323c107e236_puma-future-vang",
        11
    ),
    (
        24,
        "http://res.cloudinary.com/dxx1lgamz/image/upload/f31cad1f-6dcd-41c6-9f9a-a1a8281bf4f8_puma-future-xanh-duong",
        14
    ),
    (
        25,
        "http://res.cloudinary.com/dxx1lgamz/image/upload/88503b32-8e21-4ef7-9e35-785d0b555d3b_toni-kroos-hong",
        9
    ),
    (
        25,
        "http://res.cloudinary.com/dxx1lgamz/image/upload/f54ebf40-2ab1-4d08-8752-5cf9045a3c5f_toni-kroos-vang",
        11
    ),
    (
        25,
        "http://res.cloudinary.com/dxx1lgamz/image/upload/2655d0bd-f624-4bd3-83e3-afdedeb1002e_toni-kroos-xam",
        3
    ),
    (
        25,
        "http://res.cloudinary.com/dxx1lgamz/image/upload/bc3e99b9-9c6e-4792-962c-1739203920f0_toni-kroos-xanh-duong",
        14
    ),
    (
        26,
        "http://res.cloudinary.com/dxx1lgamz/image/upload/389f2fd3-7d3b-4b44-86d6-f6a0c83186fa_nike-air-jordan-den",
        1
    ),
    (
        26,
        "http://res.cloudinary.com/dxx1lgamz/image/upload/baf87afb-165f-4c88-a8e1-1490e32f46a1_nike-air-jordan-do",
        8
    ),
    (
        26,
        "http://res.cloudinary.com/dxx1lgamz/image/upload/60ca6ad3-511f-4830-a9b1-ac669f6227a6_nike-air-jordan-trang",
        2
    ),
    (
        26,
        "http://res.cloudinary.com/dxx1lgamz/image/upload/f8b75f21-e81a-4b53-aba2-8b31cd64e1d1_nike-air-jordan-xanh-duong",
        14
    ),
    (
        27,
        "http://res.cloudinary.com/dxx1lgamz/image/upload/6f45d675-1730-4f33-99ec-0256711226cd_adidas-utraboost-den",
        1
    ),
    (
        27,
        "http://res.cloudinary.com/dxx1lgamz/image/upload/e0b9dff5-1fb7-43a0-84c1-3fc11fd5e16c_adidas-utraboost-trang",
        2
    ),
    (
        27,
        "http://res.cloudinary.com/dxx1lgamz/image/upload/7e816168-0dba-4114-8e74-c4def852295f_adidas-utraboost-xam",
        3
    ),
    (
        27,
        "http://res.cloudinary.com/dxx1lgamz/image/upload/87582fcc-ec6e-42f3-a728-6a83b3a42116_adidas-utraboost-xanh-reu",
        13
    ),
    (
        28,
        "http://res.cloudinary.com/dxx1lgamz/image/upload/6a0ce971-7007-4c0a-865a-bde7d826ee7a_biti-huner-cam",
        10
    ),
    (
        28,
        "http://res.cloudinary.com/dxx1lgamz/image/upload/40e91909-c1f6-47d5-9ebd-9d179b9561ac_biti-hunter-den",
        1
    ),
    (
        28,
        "http://res.cloudinary.com/dxx1lgamz/image/upload/22797e74-0dc8-4dc8-9ea1-0678a2061c12_biti-hunter-trang",
        2
    ),
    (
        28,
        "http://res.cloudinary.com/dxx1lgamz/image/upload/25b71846-6961-4d54-92f3-5dfddd4a40f9_biti-hunter-xam",
        3
    ),
    (
        29,
        "http://res.cloudinary.com/dxx1lgamz/image/upload/b29c940d-a36f-40de-a23b-766bf6f5fb4d_mlb-bigball-den",
        1
    ),
    (
        29,
        "http://res.cloudinary.com/dxx1lgamz/image/upload/9baac69e-ddb0-4594-87a6-a1bd2fa01bb9_mlb-bigball-tim",
        18
    ),
    (
        29,
        "http://res.cloudinary.com/dxx1lgamz/image/upload/2a0601dd-920d-4edd-af71-13c5e9a5c4f3_mlb-bigball-trang",
        2
    ),
    (
        30,
        "http://res.cloudinary.com/dxx1lgamz/image/upload/27c7c33e-42cb-494d-a37a-ef2574196d48_nike-court-den",
        1
    ),
    (
        30,
        "http://res.cloudinary.com/dxx1lgamz/image/upload/062755d6-47fc-4277-b9a9-9c75864fbd5f_nike-court-trang",
        2
    ),
    (
        30,
        "http://res.cloudinary.com/dxx1lgamz/image/upload/53c89d59-d3fc-4eb0-a568-c1406ea9dc5e_nike-court-vang",
        11
    ),
    (
        30,
        "http://res.cloudinary.com/dxx1lgamz/image/upload/9410baf0-e918-4590-8c32-38a33d0aa613_nike-court-xanh-reu",
        13
    );

-- them chi tiet san pham
insert into
    detail(size_id, color_id, product_id, stock, sold_count)
values
    (5, 1, 21, 10, 0),
    (2, 8, 21, 5, 0),
    (3, 11, 21, 3, 0),
    (7, 14, 21, 2, 0),
    (1, 12, 21, 7, 0),
    (5, 1, 22, 5, 0),
    (4, 16, 22, 6, 0),
    (7, 12, 22, 7, 0),
    (1, 14, 22, 8, 0),
    (1, 1, 23, 7, 0),
    (2, 8, 23, 8, 0),
    (3, 3, 23, 9, 0),
    (4, 14, 23, 10, 0),
    (7, 2, 24, 7, 0),
    (6, 11, 24, 3, 0),
    (5, 14, 24, 1, 0),
    (4, 9, 25, 7, 0),
    (3, 11, 25, 7, 0),
    (2, 3, 25, 7, 0),
    (1, 14, 25, 7, 0),
    (6, 1, 26, 5, 0),
    (7, 8, 26, 3, 0),
    (7, 2, 26, 11, 0),
    (7, 14, 26, 7, 0),
    (4, 1, 27, 6, 0),
    (4, 2, 27, 13, 0),
    (4, 3, 27, 15, 0),
    (3, 13, 27, 9, 0),
    (3, 10, 28, 8, 0),
    (3, 1, 28, 8, 0),
    (1, 2, 28, 14, 0),
    (2, 3, 28, 20, 0),
    (5, 1, 29, 17, 0),
    (6, 18, 29, 7, 0),
    (7, 2, 29, 10, 0),
    (1, 1, 30, 12, 0),
    (2, 2, 30, 13, 0),
    (3, 11, 30, 5, 0),
    (4, 13, 30, 4, 0);

insert into
    product(
    id,
    deleted,
    price,
    category_id,
    quantity,
    description,
    name,
    slug
)
values
    (
        1,
        0,
        320000,
        10,
        50,
        "Quần âu bò thương hiệu",
        "Quần short bò nữ",
        "quan-short-bo-nu"
    ),
    (
        2,
        0,
        200000,
        8,
        50,
        "Quần âu thời trang",
        "Quần âu nam công sở",
        "quan-au-nam-cong-so"
    ),
    (
        3,
        0,
        250000,
        8,
        50,
        "Quần âu thời trang",
        "Quần âu nữ công sở",
        "quan-au-nu-cong-so"
    ),
    (
        4,
        0,
        210000,
        8,
        50,
        "Quần âu thời trang",
        "Quần âu nỉ nam ",
        "quan-au-ni-nam"
    ),
    (
        5,
        0,
        190000,
        8,
        50,
        "Quần âu thời trang",
        "Quần âu nữ ống rộng",
        "quan-au-nu-ong-rong"
    ),
    (
        6,
        0,
        150000,
        8,
        50,
        "Quần âu thời trang",
        "Quần âu vải nữ",
        "quan-au-vai-nu"
    ),
    (
        7,
        0,
        300000,
        10,
        50,
        "Quần âu bò thương hiệu",
        "Quần bò jeans nam",
        "quan-bo-jeans-nam"
    ),
    (
        8,
        0,
        290000,
        10,
        50,
        "Quần âu bò thương hiệu",
        "Quần bò nam ống rộng",
        "quan-bo-nam-ong-rong"
    ),
    (
        9,
        0,
        400000,
        10,
        50,
        "Quần âu bò thương hiệu",
        "Quần bò nữ ống rộng",
        "quan-bo-nu-ong-rong"
    ),
    (
        10,
        0,
        430000,
        10,
        50,
        "Quần âu bò thương hiệu",
        "Quần short bò nam",
        "quan-short-bo-nam"
    );

insert into
    image(product_id, src, color_id)
values
    (
        2,
        "http://res.cloudinary.com/dxx1lgamz/image/upload/fa30c56c-c83e-460a-bd40-ed452f6c2333_au-cong-so-nam-den",
        1
    ),
    (
        2,
        "http://res.cloudinary.com/dv6fjob4v/image/upload/b0302499-d528-4e2a-b36b-d0ec8babccba_au-cong-so-nam-xam",
        3
    ),
    (
        2,
        "http://res.cloudinary.com/dv6fjob4v/image/upload/4b301df2-f036-496f-8bda-53fbecf5a832_au-cong-so-nam-xanh-navy",
        15
    ),
    (
        3,
        "http://res.cloudinary.com/dv6fjob4v/image/upload/ecbb4bd4-ee50-4d69-8c9b-5817ac4dd770_au-cs-nu-xanh-ngoc",
        17
    ),
    (
        3,
        "http://res.cloudinary.com/dv6fjob4v/image/upload/2f08c4a2-ef89-4ba6-bf3e-ac00a2cb3a6d_au-cs-nu-nau",
        6
    ),
    (
        3,
        "http://res.cloudinary.com/dv6fjob4v/image/upload/798d08ff-3e5a-4c25-8d4d-b6aada12fed5_au-cs-nu-den",
        1
    ),
    (
        4,
        "http://res.cloudinary.com/dv6fjob4v/image/upload/69e475e6-19bc-44a8-aa0e-2d2c0f5d3d5c_au-ni-nam-den",
        1
    ),
    (
        4,
        "http://res.cloudinary.com/dv6fjob4v/image/upload/ab7f73c5-73e6-43ba-bc00-b1ed10f75ce2_au-ni-nam-soc-trang",
        2
    ),
    (
        4,
        "http://res.cloudinary.com/dv6fjob4v/image/upload/842af061-58c0-45c6-ac0c-200a5ec832a8_au-ni-nam-xanh-navy",
        15
    ),
    (
        4,
        "http://res.cloudinary.com/dv6fjob4v/image/upload/c86e6bea-a107-475f-8551-9c1e0fd52009_au-ni-nam-xanh-ngoc",
        17
    ),
    (
        5,
        "http://res.cloudinary.com/dv6fjob4v/image/upload/7ab9b8f3-e7d4-4394-90fe-4ac11b7a7227_au-nu-ongrong-xanh-navy",
        15
    ),
    (
        5,
        "http://res.cloudinary.com/dv6fjob4v/image/upload/a7a6407a-b811-4087-98d5-d29f2b2b1b49_au-nu-ongrong-vang",
        11
    ),
    (
        5,
        "http://res.cloudinary.com/dv6fjob4v/image/upload/8249680e-319a-47ab-85de-29b554812b28_au-nu-ongrong-nau",
        6
    ),
    (
        5,
        "http://res.cloudinary.com/dv6fjob4v/image/upload/f40a8e94-471b-485b-823a-8097d6f11f36_au-nu-ongrong-den",
        1
    ),
    (
        6,
        "http://res.cloudinary.com/dv6fjob4v/image/upload/c69c8ce0-8f0a-4b8c-9cf6-b1ac061183c1_vai-au-nu-be",
        5
    ),
    (
        6,
        "http://res.cloudinary.com/dv6fjob4v/image/upload/4a728e8f-6366-4281-b133-70d83c705643_vai-au-nu-den",
        1
    ),
    (
        6,
        "http://res.cloudinary.com/dv6fjob4v/image/upload/f3ff0bbe-bd07-4440-9257-cb8b086eea8d_vai-au-nu-do",
        8
    ),
    (
        6,
        "http://res.cloudinary.com/dv6fjob4v/image/upload/bbc23e35-85a8-47a6-a38e-bd235ffd120a_vai-au-nu-xanh-navy",
        15
    ),
    (
        7,
        "http://res.cloudinary.com/dv6fjob4v/image/upload/0a21d408-283a-41ee-9755-aeee123a1691_bo-nam-jeans-trang",
        2
    ),
    (
        7,
        "http://res.cloudinary.com/dv6fjob4v/image/upload/1c90e873-e944-40a2-b152-2959b0d336f9_bo-nam-jeans-xam",
        3
    ),
    (
        7,
        "http://res.cloudinary.com/dv6fjob4v/image/upload/7ee50fd9-8d8e-4dde-8bb9-fba8a0f8c8ab_bo-nam-jeans-xanh-navy",
        15
    ),
    (
        7,
        "http://res.cloudinary.com/dv6fjob4v/image/upload/78736dd6-d25f-4651-b85e-bd8d5ab82aec_bo-nam-jeans-xanh-ngoc",
        17
    ),
    (
        8,
        "http://res.cloudinary.com/dv6fjob4v/image/upload/863d93db-8f39-4cae-9caa-7d1a98370f3a_bo-nam-oongrong-be",
        5
    ),
    (
        8,
        "http://res.cloudinary.com/dv6fjob4v/image/upload/d942169f-81c3-4357-af00-8eaf3551be9b_bo-nam-ongrong-xanh-ngoc",
        17
    ),
    (
        8,
        "http://res.cloudinary.com/dv6fjob4v/image/upload/83806698-6ca0-4a0a-89ff-ecbf1cc444b2_bo-nam-ongrong-xam",
        3
    ),
    (
        8,
        "http://res.cloudinary.com/dv6fjob4v/image/upload/87c1a38d-bcca-45de-9007-c2baa2bf8a2b_bo-nam-ongrong-trang",
        2
    ),
    (
        8,
        "http://res.cloudinary.com/dv6fjob4v/image/upload/51d8b804-8af4-4662-9a66-aa72532716c6_bo-nam-ongrong-kaki",
        7
    ),
    (
        8,
        "http://res.cloudinary.com/dv6fjob4v/image/upload/6df35850-2988-432f-95b0-6f028938af93_bo-nam-ongrong-den",
        1
    ),
    (
        9,
        "http://res.cloudinary.com/dv6fjob4v/image/upload/6d8e1a19-7f1d-4e0a-8370-85d69b7672cb_bo-nu-rong-den",
        1
    ),
    (
        9,
        "http://res.cloudinary.com/dv6fjob4v/image/upload/460c7648-f261-431a-ae82-ce781632dd4a_bo-nu-rong-trang",
        2
    ),
    (
        9,
        "http://res.cloudinary.com/dv6fjob4v/image/upload/553055ef-abc4-42e8-aafe-f3d0972152ec_bo-nu-rong-xam",
        3
    ),
    (
        9,
        "http://res.cloudinary.com/dv6fjob4v/image/upload/66583591-3019-4751-9509-240e54fa2070_bo-nu-rong-xanh-navy",
        15
    ),
    (
        10,
        "http://res.cloudinary.com/dv6fjob4v/image/upload/076b9c11-aa81-46ee-a0d7-418fcb03a29f_shortbo-nam-be",
        3
    ),
    (
        10,
        "http://res.cloudinary.com/dv6fjob4v/image/upload/b7f650da-c2d4-4ebc-8c3a-cd50983b1729_shortbo-nam-trang",
        2
    ),
    (
        10,
        "http://res.cloudinary.com/dv6fjob4v/image/upload/2511963f-ad1c-4ea4-978a-ee7190a02db7_shortbo-nam-xanh-ngoc",
        17
    ),
    (
        10,
        "http://res.cloudinary.com/dv6fjob4v/image/upload/32a40edb-0283-45fa-8662-13d19381fe62_shortbo-nam-xanh-navy",
        15
    ),
    (
        1,
        "http://res.cloudinary.com/dv6fjob4v/image/upload/4776433d-4760-4bb7-8ebc-7a38d051c2be_shortbo-nu-xanh-ngoc",
        17
    ),
    (
        1,
        "http://res.cloudinary.com/dv6fjob4v/image/upload/a2738f71-e5ab-4cb5-b072-7eeb5d01006d_shortbo-nu-xanh-navy",
        15
    ),
    (
        1,
        "http://res.cloudinary.com/dv6fjob4v/image/upload/18a31a8a-d024-4f9b-a87e-02417cc795bd_shortbo-nu-den",
        1
    ),
    (
        1,
        "http://res.cloudinary.com/dv6fjob4v/image/upload/431f3831-121e-46d2-a6eb-b384088facd1_shortbo-nu-xam",
        3
    );

-- them chi tiet san pham
insert into
    detail(size_id, color_id, product_id, stock, sold_count)
values
    (5, 1, 2, 10, 0),
    (2, 3, 2, 5, 0),
    (3, 15, 2, 3, 0),
    (4, 17, 3, 4, 0),
    (3, 17, 3, 4, 0),
    (5, 6, 3, 3, 0),
    (4, 1, 3, 3, 0),
    (5, 1, 3, 5, 0),
    (3, 1, 4, 5, 0),
    (4, 1, 4, 3, 0),
    (4, 2, 4, 3, 0),
    (4, 15, 4, 8, 0),
    (5, 15, 4, 3, 0),
    (4, 17, 4, 5, 0),
    (5, 17, 4, 3, 0),
    (4, 1, 5, 6, 0),
    (4, 6, 5, 1, 0),
    (5, 1, 5, 7, 0),
    (3, 11, 5, 1, 0),
    (4, 15, 5, 2, 0),
    (5, 15, 5, 1, 0),
    (4, 5, 6, 3, 0),
    (4, 1, 6, 2, 0),
    (5, 1, 6, 3, 0),
    (4, 8, 6, 1, 0),
    (3, 1, 6, 3, 0),
    (4, 15, 6, 6, 0),
    (5, 15, 6, 10, 0),
    (3, 2, 7, 1, 0),
    (4, 2, 7, 10, 0),
    (5, 2, 7, 5, 0),
    (4, 3, 7, 3, 0),
    (4, 15, 7, 5, 0),
    (5, 15, 7, 21, 0),
    (4, 17, 7, 3, 0),
    (4, 1, 8, 3, 0),
    (5, 1, 8, 10, 0),
    (4, 2, 8, 3, 0),
    (5, 2, 8, 5, 0),
    (4, 3, 8, 3, 0),
    (4, 5, 8, 2, 0),
    (5, 5, 8, 3, 0),
    (4, 7, 8, 2, 0),
    (4, 17, 8, 6, 0),
    (5, 17, 8, 1, 0),
    (3, 1, 9, 3, 0),
    (4, 1, 9, 1, 0),
    (5, 1, 9, 5, 0),
    (4, 2, 9, 3, 0),
    (4, 3, 9, 3, 0),
    (4, 15, 2, 3, 0),
    (5, 15, 2, 3, 0),
    (4, 2, 10, 3, 0),
    (4, 3, 10, 1, 0),
    (5, 3, 10, 1, 0),
    (4, 15, 10, 3, 0),
    (5, 15, 10, 3, 0),
    (4, 17, 10, 3, 0),
    (5, 17, 10, 3, 0),
    (4, 1, 1, 3, 0),
    (5, 1, 1, 5, 0),
    (4, 3, 1, 8, 0),
    (4, 15, 1, 10, 0),
    (5, 15, 1, 10, 0),
    (4, 17, 1, 10, 0);

insert into
    product(
    id,
    deleted,
    price,
    category_id,
    quantity,
    description,
    name,
    slug
)
values
    (
        11,
        0,
        90000,
        4,
        50,
        "Áo thun chính hãng của nhà F5",
        "Áo thun",
        "ao-thun"
    ),
    (
        12,
        0,
        150000,
        5,
        50,
        "Áo sơ mi chính hãng của nhà F5",
        "Áo sơ mi",
        "ao-so-mi"
    ),
    (
        13,
        0,
        100000,
        6,
        50,
        "Áo polo chính hãng của nhà F5",
        "Áo polo",
        "ao-polo"
    ),
    (
        14,
        0,
        200000,
        7,
        50,
        "Áo hoodie chính hãng của nhà F5",
        "Áo hoodie",
        "ao-hoodie"
    ),
    (
        15,
        0,
        95000,
        18,
        50,
        "Áo len cổ lọ chính hãng của nhà F5",
        "Áo len cổ lọ",
        "ao-len-co-lo"
    ),
    (
        16,
        0,
        500000,
        19,
        50,
        "Áo blazer chính hãng của nhà F5",
        "Áo blazer",
        "ao-blazer"
    ),
    (
        17,
        0,
        90000,
        20,
        50,
        "Áo ba lỗ chính hãng của nhà F5",
        "Áo ba lỗ",
        "ao-balo"
    ),
    (
        18,
        0,
        300000,
        21,
        50,
        "Áo khoác chính hãng của nhà F5",
        "Áo khoác",
        "ao-khoac"
    ),
    (
        19,
        0,
        200000,
        22,
        50,
        "Áo croptop chính hãng của nhà F5",
        "Áo croptop",
        "ao-croptop"
    ),
    (
        20,
        0,
        150000,
        23,
        50,
        "Áo giữ nhiệt chính hãng của nhà F5",
        "Áo giữ nhiệt",
        "ao-giu-nhiet"
    );

insert into
    image(product_id, src, color_id)
values
    (
        11,
        "http://res.cloudinary.com/dxx1lgamz/image/upload/91d6c08b-234d-4b55-9bc6-c931feff00e4_ao-thun-cam",
        10
    ),
    (
        11,
        "http://res.cloudinary.com/dq4guha5o/image/upload/c490d445-725a-45a2-b646-f6897b9d9292_ao-thun-den",
        1
    ),
    (
        11,
        "http://res.cloudinary.com/dq4guha5o/image/upload/173185f9-ef79-48c7-9fa7-6dc147008063_ao-thun-do",
        8
    ),
    (
        11,
        "http://res.cloudinary.com/dq4guha5o/image/upload/96612c14-c8b7-447a-bb3c-e4877ab9d3bc_ao-thun-trang",
        2
    ),
    (
        11,
        "http://res.cloudinary.com/dq4guha5o/image/upload/88ba657e-6587-4afd-9373-295fb7819c07_ao-thun-xanh-reu-removebg-preview",
        13
    );

insert into
    image(product_id, src, color_id)
values
    (
        12,
        "http://res.cloudinary.com/dq4guha5o/image/upload/4496f06a-5e82-45da-bf8f-5723a78dd889_ao-somi-trang",
        2
    ),
    (
        12,
        "http://res.cloudinary.com/dq4guha5o/image/upload/0573b789-3c14-4deb-868a-1d1b1afc9f15_ao-somi-den",
        1
    );

insert into
    image(product_id, src, color_id)
values
    (
        13,
        "http://res.cloudinary.com/dq4guha5o/image/upload/c6feb4c3-5248-4f0c-a7d3-6ce851f7fdb9_ao-polo-xanh-duong",
        14
    ),
    (
        13,
        "http://res.cloudinary.com/dq4guha5o/image/upload/f5bad87e-9b4d-46d2-8e20-115862e5ea55_ao-polo-vang",
        11
    ),
    (
        13,
        "http://res.cloudinary.com/dq4guha5o/image/upload/62181ab7-b54f-4aef-863a-62260c18d010_ao-polo-trang",
        2
    ),
    (
        13,
        "http://res.cloudinary.com/dq4guha5o/image/upload/b26baaa1-5d90-4526-8152-7875f5322327_ao-polo-den",
        1
    );

insert into
    image(product_id, src, color_id)
values
    (
        14,
        "http://res.cloudinary.com/dq4guha5o/image/upload/8dde53d7-4838-4234-aca7-2309871eb3c3_ao-hoodie-xanh-reu",
        13
    ),
    (
        14,
        "http://res.cloudinary.com/dq4guha5o/image/upload/483de2a1-4818-45e6-964e-7da722e8243c_ao-hoodie-xam",
        3
    ),
    (
        14,
        "http://res.cloudinary.com/dq4guha5o/image/upload/4c6abc68-6c02-4a78-adb0-4506b41faf55_ao-hoodie-trang",
        2
    ),
    (
        14,
        "http://res.cloudinary.com/dq4guha5o/image/upload/f30dbea2-d3cd-42ca-9384-424274718cf9_ao-hoodie-do",
        8
    ),
    (
        14,
        "http://res.cloudinary.com/dq4guha5o/image/upload/fed95f1e-8889-4483-bf50-ebad5207d801_ao-hoodie-den",
        1
    );

insert into
    image(product_id, src, color_id)
values
    (
        15,
        "http://res.cloudinary.com/dq4guha5o/image/upload/3738af00-2e84-442f-8629-7c971c36107a_ao-lencolo-xam",
        3
    ),
    (
        15,
        "http://res.cloudinary.com/dq4guha5o/image/upload/5bfb87e7-a793-4791-90ac-b73f5cbcc75b_ao-lencolo-vang",
        11
    ),
    (
        15,
        "http://res.cloudinary.com/dq4guha5o/image/upload/a81bfaa8-0158-4808-b1af-b70506373bf2_ao-lencolo-trang",
        2
    ),
    (
        15,
        "http://res.cloudinary.com/dq4guha5o/image/upload/212b53c5-cee6-4949-b280-66bf04005643_ao-lencolo-do",
        8
    ),
    (
        15,
        "http://res.cloudinary.com/dq4guha5o/image/upload/3862c6d4-9e11-4ebc-af66-c0bc3c17b688_ao-lencolo-den",
        1
    );

insert into
    image(product_id, src, color_id)
values
    (
        16,
        "http://res.cloudinary.com/dq4guha5o/image/upload/aca339a4-a994-45d9-ac63-b04f851b6979_ao-blazer-xanh-duong",
        14
    ),
    (
        16,
        "http://res.cloudinary.com/dq4guha5o/image/upload/4339dc13-e7e1-4c88-b9e2-058ba5374bb9_ao-blazer-xam",
        3
    ),
    (
        16,
        "http://res.cloudinary.com/dq4guha5o/image/upload/9f8b19e1-2a72-4f94-8130-d7387561ddeb_ao-blazer-trang",
        2
    ),
    (
        16,
        "http://res.cloudinary.com/dq4guha5o/image/upload/9a06fb20-bad9-48e7-ada9-4454043116a6_ao-blazer-den",
        1
    ),
    (
        16,
        "http://res.cloudinary.com/dq4guha5o/image/upload/e9217170-b8b6-46c3-b2a9-43aefd271201_ao-blazer-be",
        5
    );

insert into
    image(product_id, src, color_id)
values
    (
        17,
        "http://res.cloudinary.com/dq4guha5o/image/upload/7d42141c-b06a-48fd-bdb5-ab813e59fde2_ao-balo-xanh-duong",
        14
    ),
    (
        17,
        "http://res.cloudinary.com/dq4guha5o/image/upload/41384d9d-27ac-48cd-aae1-b04421aae9a5_ao-balo-xam",
        3
    ),
    (
        17,
        "http://res.cloudinary.com/dq4guha5o/image/upload/69c18200-e691-471c-ac36-8a0d6bc6d6d2_ao-balo-trang",
        2
    ),
    (
        17,
        "http://res.cloudinary.com/dq4guha5o/image/upload/2ff1b0d0-349a-4372-b65f-5a1fea740bda_ao-balo-hong",
        9
    ),
    (
        17,
        "http://res.cloudinary.com/dq4guha5o/image/upload/6d2ff540-ce64-4ed6-af2a-f488237e048a_ao-balo-do",
        8
    ),
    (
        17,
        "http://res.cloudinary.com/dq4guha5o/image/upload/ca1e1868-9bbc-4cbc-a048-38dc5894ec9c_ao-balo-den",
        1
    );

insert into
    image(product_id, src, color_id)
values
    (
        18,
        "http://res.cloudinary.com/dq4guha5o/image/upload/96342cc6-d7ab-43f4-ab08-06419bcf7cbe_ao-khoac-xanh-duong",
        14
    ),
    (
        18,
        "http://res.cloudinary.com/dq4guha5o/image/upload/20fc5884-2c57-411b-ae55-1b678763f2f6_ao-khoac-trang",
        2
    ),
    (
        18,
        "http://res.cloudinary.com/dq4guha5o/image/upload/6b4098a5-1509-4ea8-8336-cdf8f4dfe70f_ao-khoac-tim",
        18
    ),
    (
        18,
        "http://res.cloudinary.com/dq4guha5o/image/upload/6d739132-6003-4ae5-800e-9f3c154e1b9a_ao-khoac-hong",
        9
    ),
    (
        18,
        "http://res.cloudinary.com/dq4guha5o/image/upload/87e92637-5e57-4415-94ad-b50d2d944761_ao-khoac-den",
        1
    ),
    (
        18,
        "http://res.cloudinary.com/dq4guha5o/image/upload/b7d1dddf-9322-49d0-b067-c408ddcb527e_ao-khoac-be",
        5
    );

insert into
    image(product_id, src, color_id)
values
    (
        19,
        "http://res.cloudinary.com/dq4guha5o/image/upload/b1e83070-a72a-4eb5-8d37-afa4ea3e8cfd_ao-croptop-xanh-la",
        12
    ),
    (
        19,
        "http://res.cloudinary.com/dq4guha5o/image/upload/3f886a13-1a50-4089-b282-3e0b6d9b731a_ao-croptop-vang",
        11
    ),
    (
        19,
        "http://res.cloudinary.com/dq4guha5o/image/upload/d8d9e6b0-1b51-46b0-8018-057eddcb85a8_ao-croptop-trang",
        2
    ),
    (
        19,
        "http://res.cloudinary.com/dq4guha5o/image/upload/39cf148c-24b7-478e-a1b5-acae3176da49_ao-croptop-tim",
        18
    ),
    (
        19,
        "http://res.cloudinary.com/dq4guha5o/image/upload/5accdfcb-e7c9-4945-8dd8-fbf40a75c75d_ao-croptop-hong",
        9
    ),
    (
        19,
        "http://res.cloudinary.com/dq4guha5o/image/upload/f61134e9-f7a0-484b-9cf7-ca804fbea587_ao-croptop-do",
        8
    ),
    (
        19,
        "http://res.cloudinary.com/dq4guha5o/image/upload/ca85e163-c5b5-4f2e-ad01-b8099beb090e_ao-croptop-den",
        1
    );

insert into
    image(product_id, src, color_id)
values
    (
        20,
        "http://res.cloudinary.com/dq4guha5o/image/upload/67be3f7e-006c-4d3a-a2bc-c1812f89a50c_ao-giunhiet-vang",
        11
    ),
    (
        20,
        "http://res.cloudinary.com/dq4guha5o/image/upload/dc56e5bd-f924-4157-ac3e-fa77fd7dcd6b_ao-giunhiet-trang",
        2
    ),
    (
        20,
        "http://res.cloudinary.com/dq4guha5o/image/upload/2df178d2-1601-4f47-97a7-5acc36347a9e_ao-giunhiet-nau",
        6
    ),
    (
        20,
        "http://res.cloudinary.com/dq4guha5o/image/upload/2aacca8f-3aca-49a6-81c1-a8d9e8193fec_ao-giunhiet-den",
        1
    ),
    (
        20,
        "http://res.cloudinary.com/dq4guha5o/image/upload/39c585af-2753-4419-8dbf-77434d7b1f9f_ao-giunhiet-be",
        5
    );

insert into
    detail(size_id, color_id, product_id, stock, sold_count)
values
    (3, 10, 11, 10, 0),
    (3, 1, 11, 10, 0),
    (3, 8, 11, 10, 0),
    (3, 2, 11, 10, 0),
    (3, 13, 11, 10, 0);

insert into
    detail(size_id, color_id, product_id, stock, sold_count)
values
    (3, 2, 12, 25, 0),
    (3, 1, 12, 25, 0);

insert into
    detail(size_id, color_id, product_id, stock, sold_count)
values
    (3, 14, 13, 10, 0),
    (3, 11, 13, 10, 0),
    (3, 2, 13, 10, 0),
    (3, 1, 13, 20, 0);

insert into
    detail(size_id, color_id, product_id, stock, sold_count)
values
    (3, 13, 14, 10, 0),
    (3, 3, 14, 10, 0),
    (3, 2, 14, 10, 0),
    (3, 8, 14, 10, 0),
    (3, 1, 14, 10, 0);

insert into
    detail(size_id, color_id, product_id, stock, sold_count)
values
    (3, 3, 15, 10, 0),
    (3, 11, 15, 10, 0),
    (3, 2, 15, 10, 0),
    (3, 8, 15, 10, 0),
    (3, 1, 15, 10, 0);

insert into
    detail(size_id, color_id, product_id, stock, sold_count)
values
    (3, 14, 16, 10, 0),
    (3, 3, 16, 10, 0),
    (3, 2, 16, 10, 0),
    (3, 1, 16, 10, 0),
    (3, 5, 16, 10, 0);

insert into
    detail(size_id, color_id, product_id, stock, sold_count)
values
    (3, 14, 17, 10, 0),
    (3, 3, 17, 10, 0),
    (3, 2, 17, 10, 0),
    (3, 9, 17, 10, 0),
    (3, 8, 17, 5, 0),
    (3, 1, 17, 5, 0);

insert into
    detail(size_id, color_id, product_id, stock, sold_count)
values
    (3, 14, 18, 10, 0),
    (3, 2, 18, 10, 0),
    (3, 18, 18, 10, 0),
    (3, 9, 18, 10, 0),
    (3, 1, 18, 5, 0),
    (3, 5, 18, 5, 0);

insert into
    detail(size_id, color_id, product_id, stock, sold_count)
values
    (3, 12, 19, 10, 0),
    (3, 11, 19, 10, 0),
    (3, 2, 19, 10, 0),
    (3, 18, 19, 5, 0),
    (3, 9, 19, 5, 0),
    (3, 8, 19, 5, 0),
    (3, 1, 19, 5, 0);

insert into
    detail(size_id, color_id, product_id, stock, sold_count)
values
    (3, 11, 20, 10, 0),
    (3, 2, 20, 10, 0),
    (3, 6, 20, 10, 0),
    (3, 1, 20, 10, 0),
    (3, 5, 20, 10, 0);

-- insert cate mu o day
insert into
    product(
    id,
    deleted,
    price,
    category_id,
    quantity,
    description,
    name,
    slug
)
values
    (
        31,
        0,
        150000,
        14,
        50,
        "Mũ phớt thời trang hiện đại cá tính",
        "Mũ phớt nỉ nam",
        "mu-phot-ni-nam"
    ),
    (
        32,
        0,
        200000,
        14,
        50,
        "Mũ phớt thời trang hiện đại cá tính",
        "Mũ phớt nỉ nữ",
        "mu-phot-ni-nu"
    ),
    (
        33,
        0,
        50000,
        14,
        50,
        "Mũ phớt thời trang hiện đại cá tính",
        "Mũ phớt cói nam",
        "mu-phot-coi-nam"
    ),
    (
        34,
        0,
        400000,
        14,
        50,
        "Mũ phớt thời trang hiện đại cá tính",
        "Mũ phớt da bò",
        "mu-phot-da-bo"
    ),
    (
        35,
        0,
        70000,
        13,
        50,
        "Mũ lưỡi trai dành cho giới trẻ mẫu mới",
        "Mũ lưỡi trai Nike",
        "mu-luoi-trai-nike"
    ),
    (
        36,
        0,
        60000,
        13,
        50,
        "Mũ lưỡi trai dành cho giới trẻ mẫu mới",
        "Mũ lưỡi trai Teelab",
        "mu-luoi-trai-teelab"
    ),
    (
        37,
        0,
        80000,
        13,
        50,
        "Mũ lưỡi trai dành cho giới trẻ mẫu mới",
        "Mũ lưỡi trai Jogarbola",
        "mu-luoi-trai-jogarbola"
    ),
    (
        38,
        0,
        100000,
        13,
        50,
        "Mũ lưỡi trai dành cho giới trẻ mẫu mới",
        "Mũ lưỡi trai nhung",
        "mu-luoi-trai-nhung"
    ),
    (
        39,
        0,
        300000,
        13,
        50,
        "Mũ lưỡi trai dành cho giới trẻ mẫu mới",
        "Mũ lưỡi trai da",
        "mu-luoi-trai-da"
    );

insert into
    image(product_id, src, color_id)
values
    (
        31,
        "http://res.cloudinary.com/dv6fjob4v/image/upload/3304de05-ecd9-488d-a97e-b11ceafd1529_photnam-ni-xanh-navy",
        15
    ),
    (
        31,
        "http://res.cloudinary.com/dv6fjob4v/image/upload/dc5b648d-2972-4145-889e-184a0177def3_photnam-ni-xam",
        3
    ),
    (
        31,
        "http://res.cloudinary.com/dv6fjob4v/image/upload/4b27f085-02b1-40b4-a594-d68933dc3f91_photnam-ni-nau",
        6
    ),
    (
        32,
        "http://res.cloudinary.com/dv6fjob4v/image/upload/02cd1eaa-f470-43ab-a8e2-ec5cfccc8e42_photnu-ni-trang",
        2
    ),
    (
        32,
        "http://res.cloudinary.com/dv6fjob4v/image/upload/1a1ca876-3aea-463d-a2fa-aa69bd8c1c7d_photnu-ni-xam",
        3
    ),
    (
        32,
        "http://res.cloudinary.com/dv6fjob4v/image/upload/f6a7f5d7-756b-4e3e-9e1a-7f01e0aa0611_photnu-ni-nau",
        6
    ),
    (
        32,
        "http://res.cloudinary.com/dv6fjob4v/image/upload/02f219cf-e80f-466e-a43a-8bf5d3cfad2d_photnu-ni-hong",
        9
    ),
    (
        32,
        "http://res.cloudinary.com/dv6fjob4v/image/upload/735209cb-b909-4ac4-a7b8-f4070f6536e4_photnu-ni-be",
        5
    ),
    (
        33,
        "http://res.cloudinary.com/dv6fjob4v/image/upload/7f992190-0035-4ead-92a3-20dc9e8d4248_photnam-coi-xam",
        3
    ),
    (
        33,
        "http://res.cloudinary.com/dv6fjob4v/image/upload/3c0812a4-6ce4-4109-a843-eb796a4815d4_photnam-coi-nau",
        6
    ),
    (
        33,
        "http://res.cloudinary.com/dv6fjob4v/image/upload/60f0f5e9-fbb1-47a0-a87a-c5fad226a831_photnam-coi-den",
        1
    ),
    (
        33,
        "http://res.cloudinary.com/dv6fjob4v/image/upload/ded95b9f-7e3e-4498-b7e1-7a8d75af7821_photnam-coi-be",
        5
    ),
    (
        34,
        "http://res.cloudinary.com/dv6fjob4v/image/upload/2a924229-e27a-40b1-a713-0af5684e8b50_photdabo-den",
        1
    ),
    (
        34,
        "http://res.cloudinary.com/dv6fjob4v/image/upload/0c71e832-3615-44ea-8282-e49d33dd4292_photdabo-nau",
        6
    ),
    (
        35,
        "http://res.cloudinary.com/dv6fjob4v/image/upload/7a276819-89f8-438c-ae59-3710f91ffb73_luoitrai-nike-xanh-navy",
        15
    ),
    (
        35,
        "http://res.cloudinary.com/dv6fjob4v/image/upload/f6294e4a-5395-44c5-acbc-d068a8c545d8_luoitrai-nike-xam",
        3
    ),
    (
        35,
        "http://res.cloudinary.com/dv6fjob4v/image/upload/1cb36be2-bd37-40e5-bc57-63407355124d_luoitrai-nike-trang",
        2
    ),
    (
        35,
        "http://res.cloudinary.com/dv6fjob4v/image/upload/95d23330-8779-458f-a08b-376267fe9311_luoitrai-nike-den",
        1
    ),
    (
        36,
        "http://res.cloudinary.com/dv6fjob4v/image/upload/96273f14-d18c-4bf3-a49e-4bce312d5b30_luoitrai-teelab-be",
        5
    ),
    (
        36,
        "http://res.cloudinary.com/dv6fjob4v/image/upload/5b04de3d-1343-445e-94f1-99a4f9d5eac1_luoitrai-teelab-den",
        1
    ),
    (
        36,
        "http://res.cloudinary.com/dv6fjob4v/image/upload/36f017a1-171d-4946-a8b7-ca06e42160d1_luoitrai-teelab-do",
        8
    ),
    (
        37,
        "http://res.cloudinary.com/dv6fjob4v/image/upload/32c9e2b7-2451-44cf-be7e-8853de3a5387_luoiitrai-jogarbola-vn-trang",
        2
    ),
    (
        37,
        "http://res.cloudinary.com/dv6fjob4v/image/upload/043e59d5-9978-49ce-a9a0-12acc6edfae9_luoitrai-jogarbola-vn-nau",
        6
    ),
    (
        37,
        "http://res.cloudinary.com/dv6fjob4v/image/upload/9e78326f-ecc8-4666-8ce3-d14692235084_luotrai-jogarbola-vn-do",
        8
    ),
    (
        37,
        "http://res.cloudinary.com/dv6fjob4v/image/upload/5a49725f-b031-485d-ac98-d474604b7f84_luoitrai-jogarbola-vn-xanh-navy",
        15
    ),
    (
        38,
        "http://res.cloudinary.com/dv6fjob4v/image/upload/a57fdcc3-0961-4c86-95f1-4f6f5ae5c2e0_luotra-nhung-hong",
        9
    ),
    (
        38,
        "http://res.cloudinary.com/dv6fjob4v/image/upload/66ab6f8d-3a98-4c6b-ab77-9732d3fd646a_luoitrai-nhung-trang",
        2
    ),
    (
        38,
        "http://res.cloudinary.com/dv6fjob4v/image/upload/87b6bc70-f896-45dd-8a0b-bbb68c4758f2_luoitrai-nhung-tim",
        18
    ),
    (
        38,
        "http://res.cloudinary.com/dv6fjob4v/image/upload/11f800f2-1fd0-423b-b656-df0ff786ac6f_luoitrai-nhung-den",
        1
    ),
    (
        38,
        "http://res.cloudinary.com/dv6fjob4v/image/upload/7f1f7015-db81-441b-8fdf-56d2f26bde30_luoitrai-nhung-do",
        8
    ),
    (
        39,
        "http://res.cloudinary.com/dv6fjob4v/image/upload/f283bed1-c6df-49cc-9e0a-1bc0f2702d23_luoitrai-da-nau",
        6
    ),
    (
        39,
        "http://res.cloudinary.com/dv6fjob4v/image/upload/f96c30b3-4d61-492a-a178-ee1370fd1109_luoitrai-da-den",
        1
    );

insert into
    detail(size_id, color_id, product_id, stock, sold_count)
values
    (4, 15, 31, 10, 0),
    (4, 3, 31, 10, 0),
    (4, 6, 31, 10, 0),
    (4, 2, 32, 7, 0),
    (4, 3, 32, 5, 0),
    (4, 6, 32, 10, 0),
    (4, 9, 32, 11, 0),
    (4, 5, 32, 3, 0),
    (4, 1, 33, 6, 0),
    (4, 3, 33, 5, 0),
    (4, 6, 33, 7, 0),
    (4, 5, 33, 5, 0),
    (4, 1, 34, 9, 0),
    (4, 6, 34, 15, 0),
    (4, 1, 35, 10, 0),
    (4, 2, 35, 7, 0),
    (4, 3, 35, 5, 0),
    (4, 15, 35, 15, 0),
    (4, 1, 36, 15, 0),
    (4, 5, 36, 10, 0),
    (4, 8, 36, 5, 0),
    (4, 2, 37, 5, 0),
    (4, 6, 37, 5, 0),
    (4, 8, 37, 7, 0),
    (4, 15, 37, 5, 0),
    (4, 1, 38, 10, 0),
    (4, 2, 38, 10, 0),
    (4, 6, 38, 5, 0),
    (4, 8, 38, 5, 0),
    (4, 9, 38, 4, 0),
    (4, 18, 2, 3, 0),
    (4, 1, 39, 10, 0),
    (4, 6, 39, 10, 0);

INSERT INTO `discount` VALUES (0, 30, 1);
INSERT INTO `discount` VALUES (0, 66, 2);
INSERT INTO `discount` VALUES (0, 30, 3);
INSERT INTO `product_discount` VALUES (1, '2025-06-07 00:00:00.000000', 1, 2, '2025-05-14 00:00:00.000000');
INSERT INTO `product_discount` VALUES (2, '2025-06-08 00:00:00.000000', 2, 11, '2025-05-27 00:00:00.000000');
INSERT INTO `product_discount` VALUES (3, '2025-06-08 00:00:00.000000', 3, 26, '2025-05-21 00:00:00.000000');

INSERT INTO `role_permissions` VALUES (9, 1);
INSERT INTO `role_permissions` VALUES (2, 1);
INSERT INTO `role_permissions` VALUES (3, 1);
INSERT INTO `role_permissions` VALUES (1, 1);
INSERT INTO `role_permissions` VALUES (7, 1);
INSERT INTO `role_permissions` VALUES (4, 1);
INSERT INTO `role_permissions` VALUES (8, 1);
INSERT INTO `role_permissions` VALUES (5, 1);
INSERT INTO `role_permissions` VALUES (6, 1);
INSERT INTO `role_permissions` VALUES (10, 1);
INSERT INTO `role_permissions` VALUES (11, 1);
INSERT INTO `role_permissions` VALUES (12, 1);
INSERT INTO `role_permissions` VALUES (13, 1);
INSERT INTO `role_permissions` VALUES (15, 1);
INSERT INTO `role_permissions` VALUES (16, 1);
INSERT INTO `role_permissions` VALUES (17, 1);
INSERT INTO `role_permissions` VALUES (18, 1);
INSERT INTO `role_permissions` VALUES (20, 1);
INSERT INTO `role_permissions` VALUES (19, 1);
INSERT INTO `role_permissions` VALUES (9, 3);
INSERT INTO `role_permissions` VALUES (2, 3);
INSERT INTO `role_permissions` VALUES (3, 3);
INSERT INTO `role_permissions` VALUES (1, 3);
INSERT INTO `role_permissions` VALUES (7, 4);
INSERT INTO `role_permissions` VALUES (4, 4);
INSERT INTO `role_permissions` VALUES (8, 4);
INSERT INTO `role_permissions` VALUES (5, 4);
INSERT INTO `role_permissions` VALUES (6, 4);
INSERT INTO `role_permissions` VALUES (10, 5);
INSERT INTO `role_permissions` VALUES (11, 5);
INSERT INTO `role_permissions` VALUES (12, 5);
INSERT INTO `role_permissions` VALUES (13, 5);
INSERT INTO `role_permissions` VALUES (15, 6);
INSERT INTO `role_permissions` VALUES (16, 6);
INSERT INTO `role_permissions` VALUES (17, 6);
INSERT INTO `role_permissions` VALUES (18, 6);
INSERT INTO `role_permissions` VALUES (20, 7);
INSERT INTO `role_permissions` VALUES (19, 7);

insert into user(id, name, password, email, avatar, deleted, status, created_at) values(1, "Nguyễn Văn A", "$2a$10$hJSftRyD11HMkc4QDwTYkeKlgAzWa646.qyaQRyWNY1WcX5jq4Y2.", "nguyenvana@gmail.com", "https://ugc.production.linktr.ee/5e77e148-1b07-4429-8471-d0a13edc9fa3_boy.png", 0, 1, "2025-06-05 18:04:48.399110");
insert into feedback(user_id, product_id, description, rating, feedback_time, deleted) values(1, 2, "Shop giao hàng nhanh, đóng gói cẩn thận, shipper thân thiện, cho shop 5s", 5, '2025-06-07 00:00:00.000000', 0);
insert into user_role(user_id, role_id) values (1, 2);
