USE shop;

-- Members
INSERT INTO members (
  id, first_name, last_name, email, password,
  gender, company_name, position, tel,
  zip, address, detail_address
)
VALUES (
  10, '관리자', NULL, 'admin@shop.com', 'CHANGE_ME',
  NULL, NULL, NULL, '010-0000-0000',
  '06236', '서울 강남구 테헤란로 123', '101호'
)
ON DUPLICATE KEY UPDATE
  first_name = VALUES(first_name),
  last_name = VALUES(last_name),
  password = VALUES(password),
  tel = VALUES(tel),
  zip = VALUES(zip),
  address = VALUES(address),
  detail_address = VALUES(detail_address);

-- Brands
INSERT INTO brands (id, name, slug, logo_url)
VALUES (3, 'YONEX', 'yonex', '/uploads/brands/yonex.png')
ON DUPLICATE KEY UPDATE
  name = VALUES(name),
  slug = VALUES(slug),
  logo_url = VALUES(logo_url);

-- Nav menu
INSERT INTO nav_menu (id, name, path, parent_id, depth, sort_order, visible_yn)
VALUES (100, 'Rackets', '/products', NULL, 1, 1, 'Y')
ON DUPLICATE KEY UPDATE
  name = VALUES(name),
  path = VALUES(path),
  parent_id = VALUES(parent_id),
  depth = VALUES(depth),
  sort_order = VALUES(sort_order),
  visible_yn = VALUES(visible_yn);

-- Product
INSERT INTO products (
  id, brand_id, title, series, description, price, status,
  slug, category_id, image_url, image_path
)
VALUES (
  1203, 3, 'VCORE 100 2023', 'VCORE', '.', 289000, 'ACTIVE',
  'vcore-100-2023', 100, '/uploads/products/1203/main.jpg', 'D:/uploads/products/1203/main.jpg'
)
ON DUPLICATE KEY UPDATE
  brand_id = VALUES(brand_id),
  title = VALUES(title),
  series = VALUES(series),
  description = VALUES(description),
  price = VALUES(price),
  status = VALUES(status),
  slug = VALUES(slug),
  category_id = VALUES(category_id),
  image_url = VALUES(image_url),
  image_path = VALUES(image_path);

-- Product specs (PK=FK)
INSERT INTO product_specs (
  product_id, head_size_sq_in, unstrung_weight_g, balance_mm, length_in,
  pattern_main, pattern_cross, stiffness_ra
)
VALUES (1203, 100, 300, 320, 27.0, 16, 19, 65)
ON DUPLICATE KEY UPDATE
  head_size_sq_in = VALUES(head_size_sq_in),
  unstrung_weight_g = VALUES(unstrung_weight_g),
  balance_mm = VALUES(balance_mm),
  length_in = VALUES(length_in),
  pattern_main = VALUES(pattern_main),
  pattern_cross = VALUES(pattern_cross),
  stiffness_ra = VALUES(stiffness_ra);

-- SKU
INSERT INTO skus (id, product_id, price, sku_code, is_active, grip_size)
VALUES (2001, 1203, 289000, 'VCORE100-2023-G2', 1, 'G2')
ON DUPLICATE KEY UPDATE
  product_id = VALUES(product_id),
  price = VALUES(price),
  sku_code = VALUES(sku_code),
  is_active = VALUES(is_active),
  grip_size = VALUES(grip_size);

-- Inventory (PK=FK)
INSERT INTO inventory (sku_id, stock_qty, safety_stock_qty)
VALUES (2001, 10, 2)
ON DUPLICATE KEY UPDATE
  stock_qty = VALUES(stock_qty),
  safety_stock_qty = VALUES(safety_stock_qty);

-- Optional second SKU
-- INSERT INTO skus (id, product_id, price, sku_code, is_active, grip_size)
-- VALUES (2002, 1203, 289000, 'VCORE100-2023-G3', 1, 'G3')
-- ON DUPLICATE KEY UPDATE
--   product_id = VALUES(product_id),
--   price = VALUES(price),
--   sku_code = VALUES(sku_code),
--   is_active = VALUES(is_active),
--   grip_size = VALUES(grip_size);
--
-- INSERT INTO inventory (sku_id, stock_qty, safety_stock_qty)
-- VALUES (2002, 8, 2)
-- ON DUPLICATE KEY UPDATE
--   stock_qty = VALUES(stock_qty),
--   safety_stock_qty = VALUES(safety_stock_qty);

-- Cart (1:1 member)
INSERT INTO carts (id, member_id)
VALUES (1, 10)
ON DUPLICATE KEY UPDATE
  member_id = VALUES(member_id);

-- Cart item
INSERT INTO cart_items (id, cart_id, sku_id, quantity)
VALUES (100, 1, 2001, 2)
ON DUPLICATE KEY UPDATE
  quantity = VALUES(quantity);

-- Order
INSERT INTO orders (
  id, member_id, order_no, status, total_price, receiver_name, receiver_phone,
  zip, address1, address2, memo
)
VALUES (
  200, 10, '20260226-0001', 'PENDING', 578000, '김OO', '010-0000-0000',
  '06236', '서울 강남구 테헤란로 123', NULL, '문앞에 두세요'
)
ON DUPLICATE KEY UPDATE
  member_id = VALUES(member_id),
  order_no = VALUES(order_no),
  status = VALUES(status),
  total_price = VALUES(total_price),
  receiver_name = VALUES(receiver_name),
  receiver_phone = VALUES(receiver_phone),
  zip = VALUES(zip),
  address1 = VALUES(address1),
  address2 = VALUES(address2),
  memo = VALUES(memo);

-- Order item
INSERT INTO order_items (
  id, order_id, sku_id, product_name_snapshot, brand_name_snapshot, grip_snapshot,
  unit_price, quantity, line_total, created_at, updated_at
)
VALUES (
  300, 200, 2001, 'VCORE 100 2023', 'YONEX', 'G2',
  289000, 2, 578000, NOW(6), NOW(6)
)
ON DUPLICATE KEY UPDATE
  order_id = VALUES(order_id),
  sku_id = VALUES(sku_id),
  product_name_snapshot = VALUES(product_name_snapshot),
  brand_name_snapshot = VALUES(brand_name_snapshot),
  grip_snapshot = VALUES(grip_snapshot),
  unit_price = VALUES(unit_price),
  quantity = VALUES(quantity),
  line_total = VALUES(line_total),
  updated_at = NOW(6);