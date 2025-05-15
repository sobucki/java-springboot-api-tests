# Sistema de Gerenciamento de Produtos

## Entidades Atuais

| Entidade       | Descrição                            | Atributos                                                                                |
| -------------- | ------------------------------------ | ---------------------------------------------------------------------------------------- |
| **Product**    | Produto com suas informações básicas | id (UUID), name (String), description (String), price (BigDecimal)                       |
| **Category**   | Categoria do produto                 | id (UUID), name (String)                                                                 |
| **BaseEntity** | Classe base com campos de auditoria  | createdDate (Instant), lastUpdatedDate (Instant), createdBy (String), updatedBy (String) |

## Entidades Sugeridas

### 1. Cliente (Customer)
- **id** (UUID)
- **nome** (String)
- **email** (String)
- **telefone** (String)
- **endereço** (Address - relacionamento de composição)

### 2. Endereço (Address)
- **id** (UUID)
- **rua** (String)
- **número** (String)
- **complemento** (String)
- **bairro** (String)
- **cidade** (String)
- **estado** (String)
- **cep** (String)
- **país** (String)

### 3. Pedido (Order)
- **id** (UUID)
- **cliente** (Customer - muitos pedidos para um cliente)
- **data** (Instant)
- **status** (enum OrderStatus)
- **itens** (List<OrderItem> - um pedido tem muitos itens)
- **valorTotal** (BigDecimal)

### 4. Item de Pedido (OrderItem)
- **id** (UUID)
- **pedido** (Order - muitos itens para um pedido)
- **produto** (Product - muitos itens para um produto)
- **quantidade** (Integer)
- **precoUnitario** (BigDecimal)
- **subtotal** (BigDecimal)

### 5. Estoque (Inventory)
- **id** (UUID)
- **produto** (Product - um registro de estoque para um produto)
- **quantidade** (Integer)
- **localização** (String)
- **últimaAtualização** (Instant)

### 6. Fornecedor (Supplier)
- **id** (UUID)
- **nome** (String)
- **contato** (String)
- **email** (String)
- **telefone** (String)
- **produtos** (List<Product> - muitos produtos para um fornecedor)

### 7. Avaliação (Review)
- **id** (UUID)
- **produto** (Product - muitas avaliações para um produto)
- **cliente** (Customer - muitas avaliações para um cliente)
- **classificação** (Integer)
- **comentário** (String)
- **data** (Instant)

### 8. Promoção (Promotion)
- **id** (UUID)
- **nome** (String)
- **descrição** (String)
- **descontoPercentual** (BigDecimal)
- **dataInício** (Instant)
- **dataFim** (Instant)
- **produtos** (List<Product> - muitos produtos para uma promoção)

## Diagrama de Relacionamentos

```
┌───────────┐       ┌───────────┐       ┌───────────┐
│ Customer  │──1┬──*│   Order   │──1┬──*│ OrderItem │
└─────┬─────┘       └─────┬─────┘       └─────┬─────┘
      │                   │                   │
      │                   │                   │
      │             ┌─────┴─────┐             │
      └────*┬─1─────┤  Review   │             │
            │       └─────┬─────┘             │
            │             │                   │
┌───────────┐             │             ┌─────┴─────┐
│  Address  │             └──────*┬─1───┤  Product  │
└───────────┘                    │     └─────┬─────┘
                                 │           │
                                 │           │
                         ┌───────┴─────┐     │
                         │   Category  │     │
                         └─────────────┘     │
                                             │
                         ┌─────────────┐     │
                         │  Inventory  │─1┬─1┘
                         └─────────────┘     │
                                             │
                         ┌─────────────┐     │
                         │   Supplier  │─1┬─*┘
                         └─────────────┘     │
                                             │
                         ┌─────────────┐     │
                         │  Promotion  │─*┬─*┘
                         └─────────────┘
```

## Relações Detalhadas

- **Product** tem uma **Category** (muitos para um)
- **Product** pode ter vários **OrderItem** (um para muitos)
- **Product** tem um **Inventory** (um para um)
- **Product** pode ter vários **Review** (um para muitos)
- **Product** pode estar em várias **Promotion** (muitos para muitos)
- **Product** pode ter um **Supplier** (muitos para um)
- **Customer** pode fazer vários **Order** (um para muitos)
- **Customer** pode deixar vários **Review** (um para muitos)
- **Order** contém vários **OrderItem** (um para muitos)
- **OrderItem** refere-se a um **Product** (muitos para um) 