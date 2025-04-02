-- Habilita a extensão para geração de UUIDs (usando pgcrypto)
CREATE EXTENSION IF NOT EXISTS "pgcrypto";

--------------------------------------------------
-- 1. Tabela de Usuários (app_user)
--------------------------------------------------
CREATE TABLE app_user (
    id UUID DEFAULT gen_random_uuid() PRIMARY KEY,
    email VARCHAR(100) NOT NULL UNIQUE,         -- Alterado para 'email'
    password VARCHAR(255) NOT NULL,
    user_type VARCHAR(20) NOT NULL                -- Valores esperados: 'CLIENT', 'EMPLOYEE', 'ADMIN'
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
    FOREIGN KEY (user_id) REFERENCES app_user(id) ON DELETE CASCADE
);

--------------------------------------------------
-- 3. Tabela de Cupons de Desconto
--------------------------------------------------
CREATE TABLE coupon (
    id UUID DEFAULT gen_random_uuid() PRIMARY KEY,
    code VARCHAR(50) NOT NULL UNIQUE,
    discount DECIMAL(5,2) NOT NULL,   -- Por exemplo, 10.00 pode representar 10% ou um valor fixo
    valid_until TIMESTAMP NOT NULL
);

--------------------------------------------------
-- 4. Tabela de Categorias (para itens)
--------------------------------------------------
CREATE TABLE category (
    id UUID DEFAULT gen_random_uuid() PRIMARY KEY,
    name VARCHAR(100) NOT NULL UNIQUE,
    description VARCHAR(250)
);

--------------------------------------------------
-- 5. Tabela de Marcas (para itens)
--------------------------------------------------
CREATE TABLE brand (
    id UUID DEFAULT gen_random_uuid() PRIMARY KEY,
    name VARCHAR(100) NOT NULL UNIQUE
);

--------------------------------------------------
-- 6. Tabela de Itens
--------------------------------------------------
CREATE TABLE item (
    id UUID DEFAULT gen_random_uuid() PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    description VARCHAR(250),
    price DECIMAL(10,2) NOT NULL,
    category_id UUID,
    brand_id UUID,
    stock INTEGER NOT NULL DEFAULT 0,
    FOREIGN KEY (category_id) REFERENCES category(id),
    FOREIGN KEY (brand_id) REFERENCES brand(id)
);

--------------------------------------------------
-- 7. Tabela de Carrinho de Compras
--------------------------------------------------
CREATE TABLE cart (
    id UUID DEFAULT gen_random_uuid() PRIMARY KEY,
    user_id UUID NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES app_user(id) ON DELETE CASCADE
);

--------------------------------------------------
-- 8. Tabela de Itens do Carrinho
--------------------------------------------------
CREATE TABLE cart_item (
    id UUID DEFAULT gen_random_uuid() PRIMARY KEY,
    cart_id UUID NOT NULL,
    item_id UUID NOT NULL,
    quantity INTEGER NOT NULL DEFAULT 1,
    coupon_id UUID,   -- Cupom opcional aplicado a esse item no carrinho
    FOREIGN KEY (cart_id) REFERENCES cart(id) ON DELETE CASCADE,
    FOREIGN KEY (item_id) REFERENCES item(id),
    FOREIGN KEY (coupon_id) REFERENCES coupon(id)
);

--------------------------------------------------
-- 9. Tabela de Pedidos (Histórico de Pedidos)
--------------------------------------------------
-- 'order' é palavra reservada, por isso usamos 'orders'
CREATE TABLE orders (
    id UUID DEFAULT gen_random_uuid() PRIMARY KEY,
    user_id UUID NOT NULL,
    order_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    total DECIMAL(10,2) NOT NULL,
    status VARCHAR(50) NOT NULL,
    FOREIGN KEY (user_id) REFERENCES app_user(id)
);

--------------------------------------------------
-- 10. Tabela de Itens dos Pedidos
--------------------------------------------------
CREATE TABLE order_item (
    id UUID DEFAULT gen_random_uuid() PRIMARY KEY,
    order_id UUID NOT NULL,
    item_id UUID NOT NULL,
    quantity INTEGER NOT NULL DEFAULT 1,
    price DECIMAL(10,2) NOT NULL,   -- Preço do item no momento do pedido
    coupon_id UUID,                -- Cupom aplicado, se houver
    FOREIGN KEY (order_id) REFERENCES orders(id) ON DELETE CASCADE,
    FOREIGN KEY (item_id) REFERENCES item(id),
    FOREIGN KEY (coupon_id) REFERENCES coupon(id)
);

--------------------------------------------------
-- 11. Tabela para armazenar tokens de autenticação (user_token)
--------------------------------------------------
CREATE TABLE user_token (
    id UUID DEFAULT gen_random_uuid() PRIMARY KEY,
    user_id UUID NOT NULL,
    token VARCHAR(255) NOT NULL,
    expires_at TIMESTAMP NOT NULL,
    FOREIGN KEY (user_id) REFERENCES app_user(id) ON DELETE CASCADE
);
