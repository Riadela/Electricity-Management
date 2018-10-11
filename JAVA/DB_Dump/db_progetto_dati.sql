-- phpMyAdmin SQL Dump
-- version 4.6.4
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Creato il: Gen 25, 2017 alle 18:44
-- Versione del server: 5.7.14
-- Versione PHP: 5.6.25

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `db_progetto`
--

INSERT INTO `utente` (`IDUtente`, `Password`, `Email`, `NumeroTelefono`, `Cognome`, `Nome`, `DataNascita`, `Indirizzo`, `Privilegio`, `Loggato`) VALUES
('cliente', '1111', 'cliente@gmail.com', '74102', 'Verdi', 'Fabio', '1952-06-15', 'via casamia 12 como', 'CL', 0),
('cliente1', '1111', 'cliente1@gmail.com', '987456321', 'Bianchi', 'Mauro', '1957-12-11', 'via Castelnuovo 45, Como', 'CL', 0),
('cliente2', '1111', 'cliente2@gmail.com', '45618927', 'Brazov', 'Ajeje', '1947-12-11', 'via Castelnuovo 11, Como', 'CL', 0),
('manutentore', '1111', 'manutentore@gmail.com', '9632587', 'Papaleo', 'Rocco', '1942-10-23', 'via Rovelli 12, Como', 'MAN', 0),
('rossi', '1111', 'rossi.mario@gmail.com', '123456789', 'Rossi', 'Mario', '1960-10-24', 'via Valleggio, 4, 22100, Como, Italy', 'IMP', 0);



--
-- Dump dei dati per la tabella `componente`
--

INSERT INTO `componente` (`IDComponente`, `Tipo`, `ValoreEnergetico`) VALUES
(14, 'CONTATORE', 50),
(15, 'CONTATORE', 0),
(16, 'PANNELLOSOLARE', 0),
(17, 'CONTATORE', 124),
(18, 'LINEAELETTL', 1000),
(19, 'LINEAELETTM', 10000),
(20, 'LINEAELETTH', 100000),
(21, 'CABTRASFML', 5000),
(22, 'CABTRASFHM', 50000);

--
-- Dump dei dati per la tabella `contratto`
--

INSERT INTO `contratto` (`IDContratto`, `DataInizio`, `IDUtente`, `IDCompPos`, `IDCompNeg`, `Bilancio`) VALUES
(135, '1900-01-01', 'cliente2', 14, 0, 50),
(140, '2015-06-16', 'cliente', 15, 16, 0),
(155, '2016-03-09', 'cliente1', 17, 0, 0);

--
-- Dump dei dati per la tabella `fattura`
--

INSERT INTO `fattura` (`IDFattura`, `DataEmissione`, `Importo`, `Dettagli`, `StatoFattura`, `IDContratto`) VALUES
(100, '2016-11-08', 190, 'Fattura Ottobre 2016', 'PAGATA', 155),
(111, '2016-12-05', 158, 'Fattura Novembre 2016', 'EMESSA', 155),
(123, '2017-01-10', 178, 'Fattura Dicembre 2016', 'EMESSA', 155),
(125, '2016-12-05', 146, 'Fattura Novembre 2016', 'EMESSA', 140);

--
-- Dump dei dati per la tabella `guasto`
--

INSERT INTO `guasto` (`IDGuasto`, `StatoGuasto`, `DataSegnalazione`, `DataIncarico`, `DataRiparazione`, `Dettagli`, `IDCliente`, `IDManutentore`, `IDComponente`) VALUES
(1, 'SEGNALATO', '2017-01-23', NULL, NULL, 'Non c\'è corrente', 'cliente', NULL, 15),
(2, 'PRESOINCARICO', '2017-01-09', '2017-01-10', NULL, 'Cali di Tensione', 'cliente', 'manutentore', 15),
(3, 'RISOLTO', '2016-12-05', '2016-12-06', '2016-12-06', 'Non va la luce', 'cliente2', 'manutentore', 14);

--
-- Dump dei dati per la tabella `posizione`
--

INSERT INTO `posizione` (`Longitudine`, `Latitudine`, `IDComponente`) VALUES
('2°2\'2\'\'', '2°0\'2\'\'', 14),
('-11°12\'13\'\'', '11°15\'12\'\'', 15),
('12°8\'8\'\'', '-10°11\'6\'\'', 16),
('15°2\'3\'\'', '13°18\'2\'\'', 17),
('16°6\'33\'\'', '21°17\'1\'\'', 18),
('-17°9\'17\'\'', '17°25\'22\'\'', 19),
('18°10\'21\'\'', '1°10\'10\'\'', 20),
('19°12\'11\'\'', '14°1\'34\'\'', 21),
('-20°11\'30\'\'', '24°5\'56\'\'', 22);
--
-- Dump dei dati per la tabella `utente`
--


/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
