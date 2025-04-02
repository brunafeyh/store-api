-- Habilita a extensão para geração de UUIDs (usando pgcrypto)
CREATE EXTENSION IF NOT EXISTS "pgcrypto";

--------------------------------------------------
-- 1. Tabela de Usuários (app_user)
--------------------------------------------------
CREATE TABLE app_user (
                          id UUID DEFAULT gen_random_uuid() PRIMARY KEY,
                          name VARCHAR(100) NOT NULL,
                          email VARCHAR(100) NOT NULL UNIQUE,
                          password VARCHAR(255) NOT NULL,
                          user_type VARCHAR(20) NOT NULL
);


--------------------------------------------------
-- 2. Tabela de Endereços (um usuário pode ter vários)
--------------------------------------------------
CREATE TABLE address (
                         id UUID DEFAULT gen_random_uuid() PRIMARY KEY,
                         user_id UUID NOT NULL,
                         street VARCHAR(150) NOT NULL,
                         number VARCHAR(20),
                         complement VARCHAR(100),
                         neighborhood VARCHAR(100),
                         city VARCHAR(100) NOT NULL,
                         state VARCHAR(50) NOT NULL,
                         zip VARCHAR(20),
                         address_type VARCHAR(50),  -- Ex.: 'RESIDENCIAL', 'ENTREGA'
                         created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                         updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                         FOREIGN KEY (user_id) REFERENCES app_user(id) ON DELETE CASCADE
);

--------------------------------------------------
-- 3. Tabela de Cupons de Desconto (coupon)
--------------------------------------------------
CREATE TABLE coupon (
                        id UUID DEFAULT gen_random_uuid() PRIMARY KEY,
                        code VARCHAR(50) NOT NULL UNIQUE,
                        discount DECIMAL(5,2) NOT NULL,   -- Pode representar % ou valor fixo
                        description VARCHAR(250),
                        valid_until TIMESTAMP NOT NULL,
                        max_usage INTEGER,
                        usage_count INTEGER DEFAULT 0,
                        created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

--------------------------------------------------
-- 4. Tabela de Categorias (category)
--------------------------------------------------
CREATE TABLE category (
                          id UUID DEFAULT gen_random_uuid() PRIMARY KEY,
                          name VARCHAR(100) NOT NULL UNIQUE,
                          description VARCHAR(250),
                          created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                          updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

--------------------------------------------------
-- 5. Tabela de Marcas (brand)
--------------------------------------------------
CREATE TABLE brand (
                       id UUID DEFAULT gen_random_uuid() PRIMARY KEY,
                       name VARCHAR(100) NOT NULL UNIQUE,
                       created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

--------------------------------------------------
-- 6. Tabela de Itens (item)
--------------------------------------------------
CREATE TABLE item (
                      id UUID DEFAULT gen_random_uuid() PRIMARY KEY,
                      sku VARCHAR(50) UNIQUE,
                      name VARCHAR(100) NOT NULL,
                      description VARCHAR(250),
                      image_url TEXT,
                      price DECIMAL(10,2) NOT NULL,
                      stock INTEGER NOT NULL DEFAULT 0,
                      category_id UUID,
                      brand_id UUID,
                      created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                      updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                      FOREIGN KEY (category_id) REFERENCES category(id),
                      FOREIGN KEY (brand_id) REFERENCES brand(id)
);

--------------------------------------------------
-- 7. Tabela de Carrinho de Compras (cart)
--------------------------------------------------
CREATE TABLE cart (
                      id UUID DEFAULT gen_random_uuid() PRIMARY KEY,
                      user_id UUID NOT NULL,
                      status VARCHAR(20) NOT NULL DEFAULT 'OPEN',  -- Ex.: 'OPEN', 'CHECKED_OUT'
                      created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                      updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                      FOREIGN KEY (user_id) REFERENCES app_user(id) ON DELETE CASCADE
);

--------------------------------------------------
-- 8. Tabela de Itens do Carrinho (cart_item)
--------------------------------------------------
CREATE TABLE cart_item (
                           id UUID DEFAULT gen_random_uuid() PRIMARY KEY,
                           cart_id UUID NOT NULL,
                           item_id UUID NOT NULL,
                           quantity INTEGER NOT NULL DEFAULT 1,
                           coupon_id UUID,
                           created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                           updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                           FOREIGN KEY (cart_id) REFERENCES cart(id) ON DELETE CASCADE,
                           FOREIGN KEY (item_id) REFERENCES item(id),
                           FOREIGN KEY (coupon_id) REFERENCES coupon(id)
);

--------------------------------------------------
-- 9. Tabela de Pedidos (orders)
--------------------------------------------------
CREATE TABLE orders (
                        id UUID DEFAULT gen_random_uuid() PRIMARY KEY,
                        user_id UUID NOT NULL,
                        shipping_address_id UUID,
                        order_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                        total DECIMAL(10,2) NOT NULL,
                        status VARCHAR(50) NOT NULL,                 -- Ex.: 'PENDING', 'PAID', 'SHIPPED', 'CANCELLED'
                        payment_status VARCHAR(50),                  -- Ex.: 'UNPAID', 'PAID'
                        tracking_number VARCHAR(100),
                        created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                        updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                        FOREIGN KEY (user_id) REFERENCES app_user(id),
                        FOREIGN KEY (shipping_address_id) REFERENCES address(id)
);

--------------------------------------------------
-- 10. Tabela de Itens dos Pedidos (order_item)
--------------------------------------------------
CREATE TABLE order_item (
                            id UUID DEFAULT gen_random_uuid() PRIMARY KEY,
                            order_id UUID NOT NULL,
                            item_id UUID NOT NULL,
                            quantity INTEGER NOT NULL DEFAULT 1,
                            price DECIMAL(10,2) NOT NULL,   -- Preço unitário no momento do pedido
                            discount DECIMAL(10,2) DEFAULT 0,
                            coupon_id UUID,
                            created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                            updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                            FOREIGN KEY (order_id) REFERENCES orders(id) ON DELETE CASCADE,
                            FOREIGN KEY (item_id) REFERENCES item(id),
                            FOREIGN KEY (coupon_id) REFERENCES coupon(id)
);

--------------------------------------------------
-- 11. Tabela de Avaliações (review)
--------------------------------------------------
CREATE TABLE review (
                        id UUID DEFAULT gen_random_uuid() PRIMARY KEY,
                        user_id UUID NOT NULL,         -- Cliente que avaliou
                        item_id UUID NOT NULL,         -- Item avaliado
                        order_id UUID,                -- Pedido associado (opcional)
                        rating DECIMAL(3,2) NOT NULL,   -- Ex.: nota de 1.00 a 5.00
                        comment VARCHAR(500),         -- Comentário opcional
                        created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                        FOREIGN KEY (user_id) REFERENCES app_user(id),
                        FOREIGN KEY (item_id) REFERENCES item(id),
                        FOREIGN KEY (order_id) REFERENCES orders(id)
);
