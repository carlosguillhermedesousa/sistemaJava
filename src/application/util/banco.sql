create database sistema; 
use sistema;
CREATE TABLE produto (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    descricao TEXT,
    categoria VARCHAR(50),
    preco DOUBLE(10,2) NOT NULL,
    quantidade INT NOT NULL
);
select *from produto;

select * from produto;
select *from movimentacaoestoque;

select *from produto



select * from produto where descricao="arroz" or categoria="arroz" or nome="arroz";

ALTER TABLE produto
ADD COLUMN codBarras VARCHAR(50) NOT NULL;

);




ALTER TABLE produto
CHANGE codBrras codBarras VARCHAR(50) NULL;

update produto set codbarras="0000000000000" ;



CREATE TABLE movimentacaoEstoque (
    id INT AUTO_INCREMENT PRIMARY KEY,   
    idProd INT NOT NULL,                 -- id do produto
    dataHora DATETIME NOT NULL,              -- data e hora do processamento
    quantidade INT NOT NULL,                    -- quantidade movimentada
    tipo INT NOT NULL , -- 0 = entrada, 1 = saída
    CONSTRAINT fk_produto FOREIGN KEY (idProd) REFERENCES produto(id)
    );
    
    


select m.dataHora,p.id,p.nome,m.quantidade,
(case when m.tipo=0 then 'Entrada' 
	when m.tipo=1  then 'Saida'  
	else 'Não Informado' end) as tipo
 from produto p inner join movimentacaoestoque m
on p.id=m.idProd where p.id=3 and m.dataHora between '2026-04-01' and '2026-04-30';

DELIMITER $$

CREATE TRIGGER trg_movimentacao_estoque
AFTER INSERT ON movimentacaoEstoque
FOR EACH ROW
BEGIN
    IF NEW.tipo = 0 THEN -- Entrada: soma a quantidade
        UPDATE produto
        SET qtd = qtd + NEW.qtd
        WHERE id = NEW.idProd;
    ELSEIF NEW.tipo = 1 THEN -- Saída: subtrai a quantidade
        UPDATE produto
        SET qtd = qtd - NEW.qtd
        WHERE id = NEW.idProd;
    END IF;
END$$

DELIMITER ;

CREATE TABLE usuario (
    id INT PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(100) NOT NULL,
    usuario VARCHAR(150) UNIQUE NOT NULL,
    senha VARCHAR(255) NOT NULL);
    
    alter table usuario
ADD COLUMN tipo int;
-- 0: administrador 1: Gerente - 2: Vendedor - 3: Estoquista
    
    INSERT INTO usuario (nome, usuario, senha,tipo)
VALUES 
('Carlos Guilherme', 'gerente', 'gerente',1),
('Carlos Guilherme', 'vendedor', 'vendedor',2),
('Carlos Guilherme', 'estoquista', 'estoquista',3);

update usuario set tipo = 0 where id>0;


select * ,  
case tipo when 0 then "administrador" when 1 then "gerente" 
when 2 then "vendedor" 
when 3 then "estoquista" else "desconhecido" end as tipo  
from usuario;

alter table produto
ADD COLUMN lucro DOUBLE(10,2);

update produto set lucro=5 where id>0;

CREATE TABLE formaPgto(
    id INT AUTO_INCREMENT PRIMARY KEY,
    descricao VARCHAR(100) NOT NULL,
    tipo int NOT NULL
);

INSERT INTO formaPgto (descricao, tipo)
VALUES
('Dinheiro', 0),
    ('Visa', 2),
    ('MasterCard', 2),
    ('Elo Débito', 1),
    ('Boleto Bancário', 3),
    ('Pagamento via Pix', 4),
    ('Transferência Bancária', 5)    ;
    
select Descricao, (CASE tipo
        WHEN 0 THEN 'Dinheiro'
        WHEN 1 THEN 'Cartão Débito'
        WHEN 2 THEN 'Cartão Crédito'
        WHEN 3 THEN 'Boleto'
        WHEN 4 THEN 'Pix'
        WHEN 5 THEN 'Transferência'
        ELSE 'Outro'
    END) as tipo from formapgto;
    
    
    CREATE TABLE cliente (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome_razao VARCHAR(80) NOT NULL,
    fantasia VARCHAR(80),
    cpf_cgc VARCHAR(30),
    rg_ie VARCHAR(30),
    telefone VARCHAR(30),
    email VARCHAR(150),
    data_nasc DATE,
    sexo INT, -- 0:Masculina 1:Feminino
    tipo INT, -- 0:fisica 1:juridica
    status INT -- 0:inativo 1:
    );
    

INSERT INTO cliente (nome_razao, fantasia, cpf_cgc, rg_ie, telefone, email, data_nasc, sexo, tipo, status)
VALUES
('Pedro Santos', NULL, '333.444.555-66', 'TO3344556', '63-99999-1014', 'pedro.santos@email.com', '1982-01-25', 0, 0, 1),
('Empresa Alpha Ltda', 'Alpha', '12.345.678/0001-90', '123456789', '63-99999-0001', 'contato@alpha.com', NULL, NULL, 1, 1),
('Carlos Silva', NULL, '123.456.789-00', 'MG1234567', '63-99999-1010', 'carlos.silva@email.com', '1985-05-10', 0, 0, 1),
('Beta Comércio S/A', 'Beta', '98.765.432/0001-12', '987654321', '63-99999-0002', 'vendas@beta.com', NULL, NULL, 1, 1),
('Fernanda Lima', NULL, '666.777.888-99', 'PR6677889', '63-99999-1017', 'fernanda.lima@email.com', '1993-04-18', 1, 0, 1),
('Lucas Martins', NULL, '777.888.999-00', 'SC7788990', '63-99999-1018', 'lucas.martins@email.com', '1987-12-22', 0, 0, 1),
('Gamma Indústria Ltda', 'Gamma', '11.222.333/0001-44', '112233445', '63-99999-0003', 'gamma@industria.com', NULL, NULL, 1, 1),
('Maria Oliveira', NULL, '987.654.321-00', 'SP9876543', '63-99999-1011', 'maria.oliveira@email.com', '1990-07-20', 1, 0, 1),
('Delta Serviços Ltda', 'Delta', '22.333.444/0001-55', '223344556', '63-99999-0004', 'delta@servicos.com', NULL, NULL, 1, 1),
('João Pereira', NULL, '111.222.333-44', 'RJ1122334', '63-99999-1012', 'joao.pereira@email.com', '1978-03-15', 0, 0, 1),
('Epsilon Consultoria Ltda', 'Epsilon', '33.444.555/0001-66', '334455667', '63-99999-0005', 'epsilon@consultoria.com', NULL, NULL, 1, 1),
('Zeta Transportes Ltda', 'Zeta', '44.555.666/0001-77', '445566778', '63-99999-0006', 'zeta@transportes.com', NULL, NULL, 1, 1),
('Theta Construções Ltda', 'Theta', '55.666.777/0001-88', '556677889', '63-99999-0007', 'theta@construcoes.com', NULL, NULL, 1, 1),
('Iota Tecnologia Ltda', 'Iota', '66.777.888/0001-99', '667788990', '63-99999-0008', 'iota@tecnologia.com', NULL, NULL, 1, 1),
('Kappa Alimentos Ltda', 'Kappa', '77.888.999/0001-11', '778899001', '63-99999-0009', 'kappa@alimentos.com', NULL, NULL, 1, 1),
('Lambda Energia Ltda', 'Lambda', '88.999.000/0001-22', '889900112', '63-99999-0010', 'lambda@energia.com', NULL, NULL, 1, 1),
('Ana Souza', NULL, '222.333.444-55', 'GO2233445', '63-99999-1013', 'ana.souza@email.com', '1995-11-30', 1, 0, 1),
('Juliana Costa', NULL, '444.555.666-77', 'BA4455667', '63-99999-1015', 'juliana.costa@email.com', '1988-09-12', 1, 0, 1),
('Ricardo Almeida', NULL, '555.666.777-88', 'DF5566778', '63-99999-1016', 'ricardo.almeida@email.com', '1975-06-05', 0, 0, 1),
('Patrícia Gomes', NULL, '888.999.000-11', 'RS8899001', '63-99999-1019', 'patricia.gomes@email.com', '1991-08-08', 1, 0, 1);









SELECT 
    id, IFNULL(nome_razao, 'Sem Nome/Razão') AS nome_razao, IFNULL(fantasia, 'Sem Fantasia') AS fantasia,
    IFNULL(cpf_cgc, 'Sem CPF/CNPJ') AS cpf_cgc, IFNULL(rg_ie, 'Sem RG/IE') AS rg_ie, IFNULL(telefone, 'Sem Telefone') AS telefone,
    IFNULL(email, 'Sem Email') AS email, DATE_FORMAT(data_nasc, '%d/%m/%Y') AS data_nasc,
    CASE   WHEN sexo = 0 THEN 'Masculino' WHEN sexo = 1 THEN 'Feminino' ELSE 'Masculino'
    END AS sexo,
    CASE WHEN tipo = 0 THEN 'Pessoa Física' WHEN tipo = 1 THEN 'Pessoa Jurídica'  ELSE 'Pessoa Física'
    END AS tipo,
    CASE   WHEN status = 0 THEN 'Inativo' WHEN status = 1 THEN 'Ativo' ELSE 'Inativo' END AS status
FROM cliente ;	

update cliente set status=0 where id in (2,5,6,8,5,16,15,20) ;

CREATE TABLE `movimentacaoestoque` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `idProd` int(11) NOT NULL,
  `dataHora` datetime NOT NULL,
  `quantidade` int(11) NOT NULL,
  `tipo` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_produto` (`idProd`),
  CONSTRAINT `fk_produto` FOREIGN KEY (`idProd`) REFERENCES `produto` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
CREATE TABLE `produto` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nome` varchar(100) NOT NULL,
  `descricao` text DEFAULT NULL,
  `categoria` varchar(50) DEFAULT NULL,
  `preco` double(10,2) NOT NULL,
  `quantidade` int(11) NOT NULL,
  `codBarras` varchar(50) DEFAULT NULL,
  `lucro` double(10,2) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;


use sistema;
SELECT `produto`.`id`,
    `produto`.`nome`,
    `produto`.`descricao`,
    `produto`.`categoria`,
    `produto`.`preco`,
    `produto`.`quantidade`,
    `produto`.`codBarras`,
    `produto`.`lucro`
FROM `sistema`.`produto`;

	
INSERT INTO `sistema`.`produto`
(`id`, `nome`, `descricao`, `categoria`, `preco`, `quantidade`, `codBarras`, `lucro`)
VALUES
(1, 'Arroz Branco 5kg', 'Arroz tipo 1', 'Alimentos', 25.90, 100, '789100000001', 8.50),
(2, 'Feijão Carioca 1kg', 'Feijão selecionado', 'Alimentos', 8.90, 200, '789100000002', 3.20),
(3, 'Macarrão Espaguete 500g', 'Massa de trigo', 'Alimentos', 5.50, 150, '789100000003', 2.10),
(4, 'Óleo de Soja 900ml', 'Óleo refinado', 'Alimentos', 9.80, 120, '789100000004', 3.40),
(5, 'Açúcar Cristal 2kg', 'Açúcar refinado', 'Alimentos', 7.20, 180, '789100000005', 2.60),
(6, 'Café Torrado 500g', 'Café moído', 'Alimentos', 14.50, 90, '789100000006', 5.00),
(7, 'Leite Integral 1L', 'Leite UHT', 'Bebidas', 4.80, 300, '789100000007', 1.70),
(8, 'Suco de Laranja 1L', 'Suco natural', 'Bebidas', 6.90, 80, '789100000008', 2.50),
(9, 'Refrigerante Cola 2L', 'Bebida gaseificada', 'Bebidas', 8.50, 200, '789100000009', 3.00),
(10, 'Água Mineral 500ml', 'Água sem gás', 'Bebidas', 2.50, 500, '789100000010', 0.90),
(11, 'Sabonete Neutro', 'Sabonete em barra', 'Higiene', 2.20, 400, '789100000011', 0.80),
(12, 'Shampoo 300ml', 'Shampoo hidratante', 'Higiene', 12.90, 100, '789100000012', 4.50),
(13, 'Condicionador 300ml', 'Condicionador nutritivo', 'Higiene', 13.50, 90, '789100000013', 4.80),
(14, 'Creme Dental 90g', 'Pasta de dentes', 'Higiene', 6.20, 250, '789100000014', 2.20),
(15, 'Desodorante Spray 150ml', 'Proteção 48h', 'Higiene', 15.90, 70, '789100000015', 5.60),
(16, 'Detergente Líquido 500ml', 'Limpeza de louças', 'Limpeza', 3.50, 300, '789100000016', 1.20),
(17, 'Sabão em Pó 1kg', 'Limpeza de roupas', 'Limpeza', 12.00, 150, '789100000017', 4.20),
(18, 'Amaciante 2L', 'Perfume suave', 'Limpeza', 10.50, 100, '789100000018', 3.80),
(19, 'Esponja Multiuso', 'Esponja de cozinha', 'Limpeza', 1.80, 500, '789100000019', 0.70),
(20, 'Água Sanitária 2L', 'Desinfetante', 'Limpeza', 6.90, 120, '789100000020', 2.50),
(21, 'Notebook 15"', 'Intel i5, 8GB RAM', 'Eletrônicos', 3200.00, 20, '789100000021', 800.00),
(22, 'Smartphone 128GB', 'Tela 6.5"', 'Eletrônicos', 2200.00, 30, '789100000022', 600.00),
(23, 'Fone Bluetooth', 'Som estéreo', 'Eletrônicos', 150.00, 80, '789100000023', 50.00),
(24, 'Caixa de Som', 'Portátil', 'Eletrônicos', 280.00, 40, '789100000024', 90.00),
(25, 'Televisão 50"', 'Smart TV 4K', 'Eletrônicos', 2800.00, 15, '789100000025', 700.00),
(26, 'Camiseta Algodão', 'Tamanho M', 'Vestuário', 49.90, 100, '789100000026', 18.00),
(27, 'Calça Jeans', 'Tamanho 42', 'Vestuário', 120.00, 60, '789100000027', 40.00),
(28, 'Tênis Esportivo', 'Numeração 41', 'Vestuário', 250.00, 50, '789100000028', 80.00),
(29, 'Jaqueta Couro', 'Tamanho G', 'Vestuário', 450.00, 20, '789100000029', 150.00),
(30, 'Boné Preto', 'Ajustável', 'Vestuário', 35.00, 80, '789100000030', 12.00),
(31, 'Livro Romance', 'Capa comum', 'Livros', 45.00, 70, '789100000031', 15.00),
(32, 'Livro Técnico', 'Programação', 'Livros', 120.00, 40, '789100000032', 40.00),
(33, 'Revista Mensal', 'Edição atual', 'Livros', 15.00, 100, '789100000033', 5.00),
(34, 'Dicionário Português', 'Capa dura', 'Livros', 80.00, 30, '789100000034', 25.00),
(35, 'HQ Super-Herói', 'Edição especial', 'Livros', 35.00, 50, '789100000035', 12.00),
(36, 'Bicicleta Aro 29', 'Mountain Bike', 'Esporte', 1500.00, 10, '789100000036', 500.00),
(37, 'Bola de Futebol', 'Oficial', 'Esporte', 120.00, 40, '789100000037', 40.00),
(38, 'Raquete Tênis', 'Fibra de carbono', 'Esporte', 350.00, 15, '789100000038', 120.00),
(39, 'Capacete Ciclismo', 'Proteção leve', 'Esporte', 220.00, 25, '789100000039', 70.00),
(40, 'Luvas Boxe', 'Couro sintético', 'Esporte', 180.00, 20, '789100000040', 60.00),
(41, 'Mouse Gamer', 'RGB', 'Informática', 180.00, 50, '789100000041', 60.00),
(42, 'Teclado Mecânico', 'Switch azul', 'Informática', 350.00, 30, '789100000042', 120.00),
(43, 'Monitor 24"', 'Full HD', 'Informática', 800.00, 20, '789100000043', 250.00),
(44, 'HD Externo 1TB', 'USB 3.0', 'Informática', 400.00, 25, '789100000044', 130.00),
(45, 'Pendrive 64GB', 'USB 3.0', 'Informática', 90.00, 100, '789100000045', 30.00),
(46, 'Cadeira Escritório', 'Ergonômica', 'Móveis', 650.00, 15, '789100000046', 200.00),
(47, 'Mesa de Jantar', 'Madeira maciça', 'Móveis', 1200.00, 10, '789100000047', 400.00),
(48, 'Sofá 3 Lugares', 'Tecido cinza', 'Móveis', 2500.00, 8, '789100000048', 800.00),
(49, 'Estante Livros', 'Madeira MDF', 'Móveis', 750.00, 12, '789100000049', 250.00),
(50, 'Cama Casal', 'Box com colchão', 'Móveis', 1800.00, 6, '789100000050',1500.00);


SELECT `movimentacaoestoque`.`id`,
    `movimentacaoestoque`.`idProd`,
    `movimentacaoestoque`.`dataHora`,
    `movimentacaoestoque`.`quantidade`,
    `movimentacaoestoque`.`tipo`
FROM `sistema`.`movimentacaoestoque`;

INSERT INTO movimentacaoEstoque
(idProd, dataHora, quantidade, tipo)
VALUES

-- Produto 1
(1, NOW() - INTERVAL 5 DAY, 20, 0),
(1, NOW() - INTERVAL 4 DAY, 20, 0),
(1, NOW() - INTERVAL 3 DAY, 20, 0),
(1, NOW() - INTERVAL 2 DAY, 20, 0),
(1, NOW() - INTERVAL 1 DAY, 20, 0),

-- Produto 2
(2, NOW() - INTERVAL 5 DAY, 40, 0),
(2, NOW() - INTERVAL 4 DAY, 40, 0),
(2, NOW() - INTERVAL 3 DAY, 40, 0),
(2, NOW() - INTERVAL 2 DAY, 40, 0),
(2, NOW() - INTERVAL 1 DAY, 40, 0),

-- Produto 3
(3, NOW() - INTERVAL 5 DAY, 30, 0),
(3, NOW() - INTERVAL 4 DAY, 30, 0),
(3, NOW() - INTERVAL 3 DAY, 30, 0),
(3, NOW() - INTERVAL 2 DAY, 30, 0),
(3, NOW() - INTERVAL 1 DAY, 30, 0),

-- Produto 4
(4, NOW() - INTERVAL 5 DAY, 24, 0),
(4, NOW() - INTERVAL 4 DAY, 24, 0),
(4, NOW() - INTERVAL 3 DAY, 24, 0),
(4, NOW() - INTERVAL 2 DAY, 24, 0),
(4, NOW() - INTERVAL 1 DAY, 24, 0),

-- Produto 5
(5, NOW() - INTERVAL 5 DAY, 36, 0),
(5, NOW() - INTERVAL 4 DAY, 36, 0),
(5, NOW() - INTERVAL 3 DAY, 36, 0),
(5, NOW() - INTERVAL 2 DAY, 36, 0),
(5, NOW() - INTERVAL 1 DAY, 36, 0),

-- Produto 6
(6, NOW() - INTERVAL 5 DAY, 18, 0),
(6, NOW() - INTERVAL 4 DAY, 18, 0),
(6, NOW() - INTERVAL 3 DAY, 18, 0),
(6, NOW() - INTERVAL 2 DAY, 18, 0),
(6, NOW() - INTERVAL 1 DAY, 18, 0),

-- Produto 7
(7, NOW() - INTERVAL 5 DAY, 60, 0),
(7, NOW() - INTERVAL 4 DAY, 60, 0),
(7, NOW() - INTERVAL 3 DAY, 60, 0),
(7, NOW() - INTERVAL 2 DAY, 60, 0),
(7, NOW() - INTERVAL 1 DAY, 60, 0),

-- Produto 8
(8, NOW() - INTERVAL 5 DAY, 16, 0),
(8, NOW() - INTERVAL 4 DAY, 16, 0),
(8, NOW() - INTERVAL 3 DAY, 16, 0),
(8, NOW() - INTERVAL 2 DAY, 16, 0),
(8, NOW() - INTERVAL 1 DAY, 16, 0),

-- Produto 9
(9, NOW() - INTERVAL 5 DAY, 40, 0),
(9, NOW() - INTERVAL 4 DAY, 40, 0),
(9, NOW() - INTERVAL 3 DAY, 40, 0),
(9, NOW() - INTERVAL 2 DAY, 40, 0),
(9, NOW() - INTERVAL 1 DAY, 40, 0),

-- Produto 10
(10, NOW() - INTERVAL 5 DAY, 100, 0),
(10, NOW() - INTERVAL 4 DAY, 100, 0),
(10, NOW() - INTERVAL 3 DAY, 100, 0),
(10, NOW() - INTERVAL 2 DAY, 100, 0),
(10, NOW() - INTERVAL 1 DAY, 100, 0),

-- Produto 11
(11, NOW() - INTERVAL 5 DAY, 80, 0),
(11, NOW() - INTERVAL 4 DAY, 80, 0),
(11, NOW() - INTERVAL 3 DAY, 80, 0),
(11, NOW() - INTERVAL 2 DAY, 80, 0),
(11, NOW() - INTERVAL 1 DAY, 80, 0),

-- Produto 12
(12, NOW() - INTERVAL 5 DAY, 20, 0),
(12, NOW() - INTERVAL 4 DAY, 20, 0),
(12, NOW() - INTERVAL 3 DAY, 20, 0),
(12, NOW() - INTERVAL 2 DAY, 20, 0),
(12, NOW() - INTERVAL 1 DAY, 20, 0),

-- Produto 13
(13, NOW() - INTERVAL 5 DAY, 18, 0),
(13, NOW() - INTERVAL 4 DAY, 18, 0),
(13, NOW() - INTERVAL 3 DAY, 18, 0),
(13, NOW() - INTERVAL 2 DAY, 18, 0),
(13, NOW() - INTERVAL 1 DAY, 18, 0),

-- Produto 14
(14, NOW() - INTERVAL 5 DAY, 50, 0),
(14, NOW() - INTERVAL 4 DAY, 50, 0),
(14, NOW() - INTERVAL 3 DAY, 50, 0),
(14, NOW() - INTERVAL 2 DAY, 50, 0),
(14, NOW() - INTERVAL 1 DAY, 50, 0),

-- Produto 15
(15, NOW() - INTERVAL 5 DAY, 14, 0),
(15, NOW() - INTERVAL 4 DAY, 14, 0),
(15, NOW() - INTERVAL 3 DAY, 14, 0),
(15, NOW() - INTERVAL 2 DAY, 14, 0),
(15, NOW() - INTERVAL 1 DAY, 14, 0);

/* Continue o mesmo padrão para os produtos 16 até 50:
quantidade_total / 5 em cada movimentação,
tipo = 0 (entrada),
datas distribuídas nos últimos 5 dias */
INSERT INTO movimentacaoEstoque
(idProd, dataHora, quantidade, tipo)
VALUES
-- Produto 16
(16, NOW() - INTERVAL 5 DAY, 60, 0),
(16, NOW() - INTERVAL 4 DAY, 60, 0),
(16, NOW() - INTERVAL 3 DAY, 60, 0),
(16, NOW() - INTERVAL 2 DAY, 60, 0),
(16, NOW() - INTERVAL 1 DAY, 60, 0),

-- Produto 17
(17, NOW() - INTERVAL 5 DAY, 30, 0),
(17, NOW() - INTERVAL 4 DAY, 30, 0),
(17, NOW() - INTERVAL 3 DAY, 30, 0),
(17, NOW() - INTERVAL 2 DAY, 30, 0),
(17, NOW() - INTERVAL 1 DAY, 30, 0),

-- Produto 18
(18, NOW() - INTERVAL 5 DAY, 20, 0),
(18, NOW() - INTERVAL 4 DAY, 20, 0),
(18, NOW() - INTERVAL 3 DAY, 20, 0),
(18, NOW() - INTERVAL 2 DAY, 20, 0),
(18, NOW() - INTERVAL 1 DAY, 20, 0),

-- Produto 19
(19, NOW() - INTERVAL 5 DAY, 100, 0),
(19, NOW() - INTERVAL 4 DAY, 100, 0),
(19, NOW() - INTERVAL 3 DAY, 100, 0),
(19, NOW() - INTERVAL 2 DAY, 100, 0),
(19, NOW() - INTERVAL 1 DAY, 100, 0),

-- Produto 20
(20, NOW() - INTERVAL 5 DAY, 24, 0),
(20, NOW() - INTERVAL 4 DAY, 24, 0),
(20, NOW() - INTERVAL 3 DAY, 24, 0),
(20, NOW() - INTERVAL 2 DAY, 24, 0),
(20, NOW() - INTERVAL 1 DAY, 24, 0),

-- Produto 21
(21, NOW() - INTERVAL 5 DAY, 4, 0),
(21, NOW() - INTERVAL 4 DAY, 4, 0),
(21, NOW() - INTERVAL 3 DAY, 4, 0),
(21, NOW() - INTERVAL 2 DAY, 4, 0),
(21, NOW() - INTERVAL 1 DAY, 4, 0),

-- Produto 22
(22, NOW() - INTERVAL 5 DAY, 6, 0),
(22, NOW() - INTERVAL 4 DAY, 6, 0),
(22, NOW() - INTERVAL 3 DAY, 6, 0),
(22, NOW() - INTERVAL 2 DAY, 6, 0),
(22, NOW() - INTERVAL 1 DAY, 6, 0),

-- Produto 23
(23, NOW() - INTERVAL 5 DAY, 16, 0),
(23, NOW() - INTERVAL 4 DAY, 16, 0),
(23, NOW() - INTERVAL 3 DAY, 16, 0),
(23, NOW() - INTERVAL 2 DAY, 16, 0),
(23, NOW() - INTERVAL 1 DAY, 16, 0),

-- Produto 24
(24, NOW() - INTERVAL 5 DAY, 8, 0),
(24, NOW() - INTERVAL 4 DAY, 8, 0),
(24, NOW() - INTERVAL 3 DAY, 8, 0),
(24, NOW() - INTERVAL 2 DAY, 8, 0),
(24, NOW() - INTERVAL 1 DAY, 8, 0),

-- Produto 25
(25, NOW() - INTERVAL 5 DAY, 3, 0),
(25, NOW() - INTERVAL 4 DAY, 3, 0),
(25, NOW() - INTERVAL 3 DAY, 3, 0),
(25, NOW() - INTERVAL 2 DAY, 3, 0),
(25, NOW() - INTERVAL 1 DAY, 3, 0),

-- Produto 26
(26, NOW() - INTERVAL 5 DAY, 20, 0),
(26, NOW() - INTERVAL 4 DAY, 20, 0),
(26, NOW() - INTERVAL 3 DAY, 20, 0),
(26, NOW() - INTERVAL 2 DAY, 20, 0),
(26, NOW() - INTERVAL 1 DAY, 20, 0),

-- Produto 27
(27, NOW() - INTERVAL 5 DAY, 12, 0),
(27, NOW() - INTERVAL 4 DAY, 12, 0),
(27, NOW() - INTERVAL 3 DAY, 12, 0),
(27, NOW() - INTERVAL 2 DAY, 12, 0),
(27, NOW() - INTERVAL 1 DAY, 12, 0),

-- Produto 28
(28, NOW() - INTERVAL 5 DAY, 10, 0),
(28, NOW() - INTERVAL 4 DAY, 10, 0),
(28, NOW() - INTERVAL 3 DAY, 10, 0),
(28, NOW() - INTERVAL 2 DAY, 10, 0),
(28, NOW() - INTERVAL 1 DAY, 10, 0),

-- Produto 29
(29, NOW() - INTERVAL 5 DAY, 4, 0),
(29, NOW() - INTERVAL 4 DAY, 4, 0),
(29, NOW() - INTERVAL 3 DAY, 4, 0),
(29, NOW() - INTERVAL 2 DAY, 4, 0),
(29, NOW() - INTERVAL 1 DAY, 4, 0),

-- Produto 30
(30, NOW() - INTERVAL 5 DAY, 16, 0),
(30, NOW() - INTERVAL 4 DAY, 16, 0),
(30, NOW() - INTERVAL 3 DAY, 16, 0),
(30, NOW() - INTERVAL 2 DAY, 16, 0),
(30, NOW() - INTERVAL 1 DAY, 16, 0),

-- Produto 31
(31, NOW() - INTERVAL 5 DAY, 14, 0),
(31, NOW() - INTERVAL 4 DAY, 14, 0),
(31, NOW() - INTERVAL 3 DAY, 14, 0),
(31, NOW() - INTERVAL 2 DAY, 14, 0),
(31, NOW() - INTERVAL 1 DAY, 14, 0),

-- Produto 32
(32, NOW() - INTERVAL 5 DAY, 8, 0),
(32, NOW() - INTERVAL 4 DAY, 8, 0),
(32, NOW() - INTERVAL 3 DAY, 8, 0),
(32, NOW() - INTERVAL 2 DAY, 8, 0),
(32, NOW() - INTERVAL 1 DAY, 8, 0),

-- Produto 33
(33, NOW() - INTERVAL 5 DAY, 20, 0),
(33, NOW() - INTERVAL 4 DAY, 20, 0),
(33, NOW() - INTERVAL 3 DAY, 20, 0),
(33, NOW() - INTERVAL 2 DAY, 20, 0),
(33, NOW() - INTERVAL 1 DAY, 20, 0),

-- Produto 34
(34, NOW() - INTERVAL 5 DAY, 6, 0),
(34, NOW() - INTERVAL 4 DAY, 6, 0),
(34, NOW() - INTERVAL 3 DAY, 6, 0),
(34, NOW() - INTERVAL 2 DAY, 6, 0),
(34, NOW() - INTERVAL 1 DAY, 6, 0),

-- Produto 35
(35, NOW() - INTERVAL 5 DAY, 10, 0),
(35, NOW() - INTERVAL 4 DAY, 10, 0),
(35, NOW() - INTERVAL 3 DAY, 10, 0),
(35, NOW() - INTERVAL 2 DAY, 10, 0),
(35, NOW() - INTERVAL 1 DAY, 10, 0),

-- Produto 36
(36, NOW() - INTERVAL 5 DAY, 2, 0),
(36, NOW() - INTERVAL 4 DAY, 2, 0),
(36, NOW() - INTERVAL 3 DAY, 2, 0),
(36, NOW() - INTERVAL 2 DAY, 2, 0),
(36, NOW() - INTERVAL 1 DAY, 2, 0),

-- Produto 37
(37, NOW() - INTERVAL 5 DAY, 8, 0),
(37, NOW() - INTERVAL 4 DAY, 8, 0),
(37, NOW() - INTERVAL 3 DAY, 8, 0),
(37, NOW() - INTERVAL 2 DAY, 8, 0),
(37, NOW() - INTERVAL 1 DAY, 8, 0),

-- Produto 38
(38, NOW() - INTERVAL 5 DAY, 3, 0),
(38, NOW() - INTERVAL 4 DAY, 3, 0),
(38, NOW() - INTERVAL 3 DAY, 3, 0),
(38, NOW() - INTERVAL 2 DAY, 3, 0),
(38, NOW() - INTERVAL 1 DAY, 3, 0),

-- Produto 39
(39, NOW() - INTERVAL 5 DAY, 5, 0),
(39, NOW() - INTERVAL 4 DAY, 5, 0),
(39, NOW() - INTERVAL 3 DAY, 5, 0),
(39, NOW() - INTERVAL 2 DAY, 5, 0),
(39, NOW() - INTERVAL 1 DAY, 5, 0),

-- Produto 40
(40, NOW() - INTERVAL 5 DAY, 4, 0),
(40, NOW() - INTERVAL 4 DAY, 4, 0),
(40, NOW() - INTERVAL 3 DAY, 4, 0),
(40, NOW() - INTERVAL 2 DAY, 4, 0),
(40, NOW() - INTERVAL 1 DAY, 4, 0),

-- Produto 41
(41, NOW() - INTERVAL 5 DAY, 10, 0),
(41, NOW() - INTERVAL 4 DAY, 10, 0),
(41, NOW() - INTERVAL 3 DAY, 10, 0),
(41, NOW() - INTERVAL 2 DAY, 10, 0),
(41, NOW() - INTERVAL 1 DAY, 10, 0),

-- Produto 42
(42, NOW() - INTERVAL 5 DAY, 6, 0),
(42, NOW() - INTERVAL 4 DAY, 6, 0),
(42, NOW() - INTERVAL 3 DAY, 6, 0),
(42, NOW() - INTERVAL 2 DAY, 6, 0),
(42, NOW() - INTERVAL 1 DAY, 6, 0),

-- Produto 43
(43, NOW() - INTERVAL 5 DAY, 4, 0),
(43, NOW() - INTERVAL 4 DAY, 4, 0),
(43, NOW() - INTERVAL 3 DAY, 4, 0),
(43, NOW() - INTERVAL 2 DAY, 4, 0),
(43, NOW() - INTERVAL 1 DAY, 4, 0),

-- Produto 44
(44, NOW() - INTERVAL 5 DAY, 5, 0),
(44, NOW() - INTERVAL 4 DAY, 5, 0),
(44, NOW() - INTERVAL 3 DAY, 5, 0),
(44, NOW() - INTERVAL 2 DAY, 5, 0),
(44, NOW() - INTERVAL 1 DAY, 5, 0),

-- Produto 45
(45, NOW() - INTERVAL 5 DAY, 20, 0),
(45, NOW() - INTERVAL 4 DAY, 20, 0),
(45, NOW() - INTERVAL 3 DAY, 20, 0),
(45, NOW() - INTERVAL 2 DAY, 20, 0),
(45, NOW() - INTERVAL 1 DAY, 20, 0),

-- Produto 46
(46, NOW() - INTERVAL 5 DAY, 3, 0),
(46, NOW() - INTERVAL 4 DAY, 3, 0),
(46, NOW() - INTERVAL 3 DAY, 3, 0),
(46, NOW() - INTERVAL 2 DAY, 3, 0),
(46, NOW() - INTERVAL 1 DAY, 3, 0),

-- Produto 47
(47, NOW() - INTERVAL 5 DAY, 2, 0),
(47, NOW() - INTERVAL 4 DAY, 2, 0),
(47, NOW() - INTERVAL 3 DAY, 2, 0),
(47, NOW() - INTERVAL 2 DAY, 2, 0),
(47, NOW() - INTERVAL 1 DAY, 2, 0),

-- Produto 48
(48, NOW() - INTERVAL 5 DAY, 1, 0),
(48, NOW() - INTERVAL 4 DAY, 1, 0),
(48, NOW() - INTERVAL 3 DAY, 2, 0),
(48, NOW() - INTERVAL 2 DAY, 2, 0),
(48, NOW() - INTERVAL 1 DAY, 2, 0),

-- Produto 49
(49, NOW() - INTERVAL 5 DAY, 2, 0),
(49, NOW() - INTERVAL 4 DAY, 2, 0),
(49, NOW() - INTERVAL 3 DAY, 2, 0),
(49, NOW() - INTERVAL 2 DAY, 3, 0),
(49, NOW() - INTERVAL 1 DAY, 3, 0),

-- Produto 50
(50, NOW() - INTERVAL 5 DAY, 1, 0),
(50, NOW() - INTERVAL 4 DAY, 1, 0),
(50, NOW() - INTERVAL 3 DAY, 1, 0),
(50, NOW() - INTERVAL 2 DAY, 1, 0),
(50, NOW() - INTERVAL 1 DAY, 2, 0);











