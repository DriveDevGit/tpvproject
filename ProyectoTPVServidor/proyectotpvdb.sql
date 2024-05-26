-- phpMyAdmin SQL Dump
-- version 5.1.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 26-05-2024 a las 22:49:58
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
(3, 'Licores');

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
(9, 'Pablo', 'Usuario10', '123456A', 'Camarero', '987654432', 'Calle 92', '87654322H');

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
(4, 'Fanta', 'Refresco', 'Bebidas', '1.40');

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `categoria`
--
ALTER TABLE `categoria`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `perfil`
--
ALTER TABLE `perfil`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `producto`
--
ALTER TABLE `producto`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `categoria`
--
ALTER TABLE `categoria`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT de la tabla `perfil`
--
ALTER TABLE `perfil`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- AUTO_INCREMENT de la tabla `producto`
--
ALTER TABLE `producto`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
