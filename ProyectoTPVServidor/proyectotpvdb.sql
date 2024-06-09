-- phpMyAdmin SQL Dump
-- version 5.1.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 09-06-2024 a las 22:18:02
-- Versión del servidor: 10.4.22-MariaDB
-- Versión de PHP: 8.1.2

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `proyectotpvdb`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `categoria`
--

CREATE TABLE `categoria` (
  `id` int(11) NOT NULL,
  `categoria` varchar(32) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `categoria`
--

INSERT INTO `categoria` (`id`, `categoria`) VALUES
(1, 'Desayunos'),
(2, 'Bebidas'),
(3, 'Licores'),
(4, 'Menu');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `mesa`
--

CREATE TABLE `mesa` (
  `ID` int(11) NOT NULL,
  `Nombre` varchar(25) DEFAULT NULL,
  `Numero` int(11) DEFAULT NULL,
  `Ocupado` tinyint(1) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `mesa`
--

INSERT INTO `mesa` (`ID`, `Nombre`, `Numero`, `Ocupado`) VALUES
(1, 'Mesa', 1, 0),
(2, 'Mesa', 2, 1),
(3, 'Barra', 1, 1),
(4, 'Barra', 2, 1),
(5, 'Mesa', 3, 1),
(7, 'Barra', 3, 1),
(8, 'Mesa', 4, 1);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `pedido`
--

CREATE TABLE `pedido` (
  `ID` int(11) NOT NULL,
  `Total` decimal(10,2) DEFAULT NULL,
  `Apertura` date DEFAULT NULL,
  `Cierre` date DEFAULT NULL,
  `Mesa_ID` int(11) DEFAULT NULL,
  `Username` varchar(16) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `pedido`
--

INSERT INTO `pedido` (`ID`, `Total`, `Apertura`, `Cierre`, `Mesa_ID`, `Username`) VALUES
(3, '10.10', '2024-05-29', NULL, 3, 'Usuario2'),
(4, '2.00', '2024-05-30', '2024-05-31', 1, 'Usuario2'),
(5, '28.10', '2024-05-30', NULL, 5, 'Usuario2'),
(6, '11.80', '2024-05-30', '2024-06-02', 2, 'Usuario2'),
(7, '1.40', '2024-05-31', NULL, 7, 'Usuario2'),
(8, '4.20', '2024-05-31', '2024-05-31', 1, 'Usuario2'),
(9, '2.80', '2024-05-31', '2024-05-31', 4, 'Usuario2'),
(10, '8.70', '2024-05-31', '2024-05-31', 4, 'Usuario2'),
(11, '22.40', '2024-05-31', NULL, 2, 'Usuario2'),
(12, '20.80', '2024-06-02', NULL, 4, 'Usuario2'),
(13, '10.10', '2024-06-02', NULL, 8, 'Usuario2');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `perfil`
--

CREATE TABLE `perfil` (
  `id` int(11) NOT NULL,
  `nombre` varchar(32) DEFAULT NULL,
  `username` varchar(16) DEFAULT NULL,
  `password` varchar(32) DEFAULT NULL,
  `rol` varchar(35) DEFAULT NULL,
  `telefono` varchar(15) DEFAULT NULL,
  `direccion` varchar(100) DEFAULT NULL,
  `dni` varchar(9) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `perfil`
--

INSERT INTO `perfil` (`id`, `nombre`, `username`, `password`, `rol`, `telefono`, `direccion`, `dni`) VALUES
(1, 'Nombre1', 'Usuario1', 'Contrasena1', 'Administrador', '987654321', 'Avenida 54', '12345678B'),
(2, 'Nombre2', 'Usuario2', 'Contrasena2', 'Camarero', '123456789', 'Calle 1', '12345678A'),
(3, 'Pablo', 'Usuario3', 'Contrasena3', 'Camarero', '123456789', 'Callje�n 4', '12343565F'),
(4, 'Enrique', 'Usuario4', 'Contrasena4', 'Administrador', '987654432', 'Calle 99', '87654322D'),
(5, 'Andrea', 'Usuario5', 'Contrasena5', 'Administrador', '987654434', 'Calle 92', '87654322F'),
(6, 'Pedro', 'Usuario6', 'Contrasena6', 'Camarero', '687654434', 'Calle 553', '87654322H'),
(7, 'Adolfo', 'Usuario7', 'Contrasena7', 'Camarero', '687654432', 'Calle 653', '87654322J'),
(8, 'sadf', 'Usuario8', '123456', 'Camarero', '23423423', 'sfdsfda', '2342234d'),
(9, 'Pablo', 'Usuario10', '123456A', 'Camarero', '987654432', 'Calle 92', '87654322H'),
(10, 'Andrea', 'Cocinero', '123456A', 'Administrador', '987654434', 'Calle 92', '87654322F'),
(11, 'Andrea', 'Cocinero1', '123456A', 'Cocinero', '987654434', 'Calle 92', '87654322F');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `producto`
--

CREATE TABLE `producto` (
  `id` int(11) NOT NULL,
  `nombre` varchar(255) DEFAULT NULL,
  `descripcion` varchar(255) DEFAULT NULL,
  `categoria` varchar(255) DEFAULT NULL,
  `precio` decimal(10,2) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `producto`
--

INSERT INTO `producto` (`id`, `nombre`, `descripcion`, `categoria`, `precio`) VALUES
(1, 'Cocacola', 'Refresco', 'Bebidas', '1.40'),
(2, 'Caf�', 'Un caf�', 'Desayunos', '1.20'),
(3, 'Gintoni', 'Bebida alcoh�lica en vaso de tubo.', 'Licores', '4.50'),
(4, 'Fanta', 'Refresco', 'Bebidas', '1.40'),
(5, 'Pepsi', 'Refresco', 'Bebidas', '1.40');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `productoseleccionado`
--

CREATE TABLE `productoseleccionado` (
  `id` int(11) NOT NULL,
  `producto_id` int(11) NOT NULL,
  `nombre` varchar(255) NOT NULL,
  `pedido_id` int(11) NOT NULL,
  `unidades` int(11) NOT NULL,
  `preciototal` decimal(10,2) DEFAULT NULL,
  `ListoCocina` tinyint(1) DEFAULT NULL,
  `ListoEntrega` tinyint(1) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `productoseleccionado`
--

INSERT INTO `productoseleccionado` (`id`, `producto_id`, `nombre`, `pedido_id`, `unidades`, `preciototal`, `ListoCocina`, `ListoEntrega`) VALUES
(43, 1, 'Cocacola', 6, 1, '1.40', 0, 0),
(44, 5, 'Pepsi', 6, 1, '1.40', 0, 0),
(45, 3, 'Gintoni', 6, 2, '9.00', 0, 0),
(48, 4, 'Fanta', 4, 1, '1.40', 0, 0),
(49, 1, 'Cocacola', 4, 1, '1.40', 0, 1),
(83, 1, 'Cocacola', 8, 1, '1.40', 0, 1),
(84, 4, 'Fanta', 8, 1, '1.40', 0, 1),
(85, 5, 'Pepsi', 8, 1, '1.40', 0, 1),
(86, 1, 'Cocacola', 9, 1, '1.40', 0, 0),
(87, 4, 'Fanta', 9, 1, '1.40', 0, 0),
(91, 1, 'Cocacola', 10, 1, '1.40', 0, 0),
(92, 4, 'Fanta', 10, 1, '1.40', 1, 0),
(93, 5, 'Pepsi', 10, 1, '1.40', 0, 1),
(94, 3, 'Gintoni', 10, 1, '4.50', 0, 0),
(181, 1, 'Cocacola', 7, 1, '1.40', 0, 1),
(200, 1, 'Cocacola', 5, 1, '1.40', 1, 0),
(201, 4, 'Fanta', 5, 2, '2.80', 1, 1),
(202, 4, 'Fanta', 5, 1, '1.40', 1, 0),
(203, 3, 'Gintoni', 5, 5, '22.50', 0, 1),
(284, 4, 'Fanta', 12, 1, '1.40', 0, 1),
(285, 5, 'Pepsi', 12, 1, '1.40', 0, 0),
(286, 3, 'Gintoni', 12, 2, '9.00', 0, 0),
(287, 3, 'Gintoni', 12, 2, '9.00', 0, 0),
(307, 1, 'Cocacola', 13, 1, '1.40', 0, 0),
(308, 5, 'Pepsi', 13, 3, '4.20', 0, 1),
(309, 3, 'Gintoni', 13, 1, '4.50', 0, 1),
(310, 1, 'Cocacola', 11, 1, '1.40', 0, 0),
(311, 1, 'Cocacola', 11, 1, '1.40', 0, 1),
(312, 1, 'Cocacola', 11, 1, '1.40', 0, 1),
(313, 1, 'Cocacola', 11, 1, '1.40', 0, 0),
(314, 1, 'Cocacola', 11, 1, '1.40', 0, 1),
(315, 1, 'Cocacola', 11, 1, '1.40', 0, 1),
(316, 1, 'Cocacola', 11, 1, '1.40', 0, 0),
(317, 1, 'Cocacola', 11, 1, '1.40', 0, 0),
(318, 1, 'Cocacola', 11, 1, '1.40', 0, 0),
(319, 1, 'Cocacola', 11, 1, '1.40', 0, 0),
(320, 1, 'Cocacola', 11, 1, '1.40', 0, 0),
(321, 1, 'Cocacola', 11, 1, '1.40', 0, 0),
(322, 1, 'Cocacola', 11, 1, '1.40', 0, 0),
(323, 1, 'Cocacola', 11, 1, '1.40', 0, 0),
(324, 1, 'Cocacola', 11, 1, '1.40', 0, 0),
(325, 1, 'Cocacola', 11, 1, '1.40', 0, 0),
(326, 1, 'Cocacola', 3, 1, '1.40', 0, 0),
(327, 4, 'Fanta', 3, 1, '1.40', 0, 1),
(328, 5, 'Pepsi', 3, 2, '2.80', 0, 0),
(329, 3, 'Gintoni', 3, 1, '4.50', 0, 0);

--
-- Disparadores `productoseleccionado`
--
DELIMITER $$
CREATE TRIGGER `actualizar_total` AFTER INSERT ON `productoseleccionado` FOR EACH ROW BEGIN
   UPDATE pedido
   SET Total = (SELECT SUM(preciototal) FROM productoseleccionado WHERE pedido_id = NEW.pedido_id)
   WHERE ID = NEW.pedido_id;
END
$$
DELIMITER ;

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `categoria`
--
ALTER TABLE `categoria`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `mesa`
--
ALTER TABLE `mesa`
  ADD PRIMARY KEY (`ID`);

--
-- Indices de la tabla `pedido`
--
ALTER TABLE `pedido`
  ADD PRIMARY KEY (`ID`),
  ADD KEY `Mesa_ID` (`Mesa_ID`),
  ADD KEY `Username` (`Username`);

--
-- Indices de la tabla `perfil`
--
ALTER TABLE `perfil`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `username` (`username`);

--
-- Indices de la tabla `producto`
--
ALTER TABLE `producto`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `productoseleccionado`
--
ALTER TABLE `productoseleccionado`
  ADD PRIMARY KEY (`id`),
  ADD KEY `producto_id` (`producto_id`),
  ADD KEY `pedido_id` (`pedido_id`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `categoria`
--
ALTER TABLE `categoria`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT de la tabla `mesa`
--
ALTER TABLE `mesa`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- AUTO_INCREMENT de la tabla `pedido`
--
ALTER TABLE `pedido`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=14;

--
-- AUTO_INCREMENT de la tabla `perfil`
--
ALTER TABLE `perfil`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;

--
-- AUTO_INCREMENT de la tabla `producto`
--
ALTER TABLE `producto`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT de la tabla `productoseleccionado`
--
ALTER TABLE `productoseleccionado`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=330;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `pedido`
--
ALTER TABLE `pedido`
  ADD CONSTRAINT `pedido_ibfk_1` FOREIGN KEY (`Mesa_ID`) REFERENCES `mesa` (`ID`),
  ADD CONSTRAINT `pedido_ibfk_2` FOREIGN KEY (`Username`) REFERENCES `perfil` (`username`);

--
-- Filtros para la tabla `productoseleccionado`
--
ALTER TABLE `productoseleccionado`
  ADD CONSTRAINT `productoseleccionado_ibfk_1` FOREIGN KEY (`producto_id`) REFERENCES `producto` (`id`),
  ADD CONSTRAINT `productoseleccionado_ibfk_2` FOREIGN KEY (`pedido_id`) REFERENCES `pedido` (`ID`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
