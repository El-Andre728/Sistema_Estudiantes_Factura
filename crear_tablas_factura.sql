CREATE TABLE factura (
    id SERIAL PRIMARY KEY,
    numero_factura VARCHAR(20) NOT NULL UNIQUE,
    fecha DATE NOT NULL DEFAULT CURRENT_DATE,
    cliente_nombre VARCHAR(100) NOT NULL,
    cliente_nit VARCHAR(20),
    total NUMERIC(10,2) NOT NULL DEFAULT 0
);

CREATE TABLE detalle_factura (
    id SERIAL PRIMARY KEY,
    factura_id INTEGER NOT NULL REFERENCES factura(id) ON DELETE CASCADE,
    descripcion VARCHAR(200) NOT NULL,
    cantidad INTEGER NOT NULL,
    precio_unitario NUMERIC(10,2) NOT NULL,
    subtotal NUMERIC(10,2) NOT NULL
);
