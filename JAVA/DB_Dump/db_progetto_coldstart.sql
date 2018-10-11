-- phpMyAdmin SQL Dump
-- version 4.6.4
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Creato il: Gen 25, 2017 alle 18:46
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

-- --------------------------------------------------------

--
-- Struttura della tabella `componente`
--

CREATE TABLE `componente` (
  `IDComponente` int(11) NOT NULL,
  `Tipo` enum('PANNELLOSOLARE','CONTATORE','LINEAELETTH','LINEAELETTM','LINEAELETTL','CABTRASFHM','CABTRASFML') NOT NULL DEFAULT 'PANNELLOSOLARE',
  `ValoreEnergetico` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Struttura della tabella `contratto`
--

CREATE TABLE `contratto` (
  `IDContratto` int(11) NOT NULL,
  `DataInizio` date NOT NULL,
  `IDUtente` varchar(255) NOT NULL,
  `IDCompPos` int(11) NOT NULL,
  `IDCompNeg` int(11) NOT NULL,
  `Bilancio` float NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dump dei dati per la tabella `contratto`
--

-- --------------------------------------------------------

--
-- Struttura della tabella `fattura`
--

CREATE TABLE `fattura` (
  `IDFattura` int(11) NOT NULL,
  `DataEmissione` date NOT NULL,
  `Importo` float NOT NULL,
  `Dettagli` text,
  `StatoFattura` enum('EMESSA','PAGATA') NOT NULL DEFAULT 'EMESSA',
  `IDContratto` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Struttura della tabella `guasto`
--

CREATE TABLE `guasto` (
  `IDGuasto` int(11) NOT NULL,
  `StatoGuasto` enum('SEGNALATO','PRESOINCARICO','RISOLTO') NOT NULL DEFAULT 'SEGNALATO',
  `DataSegnalazione` date NOT NULL,
  `DataIncarico` date DEFAULT NULL,
  `DataRiparazione` date DEFAULT NULL,
  `Dettagli` text,
  `IDCliente` varchar(255) DEFAULT NULL,
  `IDManutentore` varchar(255) DEFAULT NULL,
  `IDComponente` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Struttura della tabella `posizione`
--

CREATE TABLE `posizione` (
  `Longitudine` varchar(255) NOT NULL,
  `Latitudine` varchar(255) NOT NULL,
  `IDComponente` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Struttura della tabella `utente`
--

CREATE TABLE `utente` (
  `IDUtente` varchar(255) NOT NULL,
  `Password` varchar(255) NOT NULL,
  `Email` varchar(255) NOT NULL,
  `NumeroTelefono` varchar(255) NOT NULL,
  `Cognome` varchar(255) NOT NULL,
  `Nome` varchar(255) NOT NULL,
  `DataNascita` date NOT NULL,
  `Indirizzo` text NOT NULL,
  `Privilegio` enum('IMP','MAN','CL') CHARACTER SET utf8 NOT NULL,
  `Loggato` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dump dei dati per la tabella `utente`
--

INSERT INTO `utente` (`IDUtente`, `Password`, `Email`, `NumeroTelefono`, `Cognome`, `Nome`, `DataNascita`, `Indirizzo`, `Privilegio`, `Loggato`) VALUES
('rossi', '1111', 'rossi.mario@gmail.com', '123456789', 'Rossi', 'Mario', '1960-10-24', 'via Valleggio, 4, 22100, Como, Italy', 'IMP', 0);

--
-- Indici per le tabelle scaricate
--

--
-- Indici per le tabelle `componente`
--
ALTER TABLE `componente`
  ADD PRIMARY KEY (`IDComponente`);

--
-- Indici per le tabelle `contratto`
--
ALTER TABLE `contratto`
  ADD PRIMARY KEY (`IDContratto`),
  ADD KEY `IDUtente` (`IDUtente`),
  ADD KEY `contratto_ibfk_2` (`IDCompPos`);

--
-- Indici per le tabelle `fattura`
--
ALTER TABLE `fattura`
  ADD PRIMARY KEY (`IDFattura`),
  ADD KEY `IDContratto` (`IDContratto`);

--
-- Indici per le tabelle `guasto`
--
ALTER TABLE `guasto`
  ADD PRIMARY KEY (`IDGuasto`),
  ADD KEY `IDCliente` (`IDCliente`),
  ADD KEY `IDManutentore` (`IDManutentore`),
  ADD KEY `IDComponente` (`IDComponente`);

--
-- Indici per le tabelle `posizione`
--
ALTER TABLE `posizione`
  ADD KEY `IDComponente` (`IDComponente`);

--
-- Indici per le tabelle `utente`
--
ALTER TABLE `utente`
  ADD PRIMARY KEY (`IDUtente`);

--
-- AUTO_INCREMENT per le tabelle scaricate
--

--
-- AUTO_INCREMENT per la tabella `componente`
--
ALTER TABLE `componente`
  MODIFY `IDComponente` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=23;
--
-- AUTO_INCREMENT per la tabella `contratto`
--
ALTER TABLE `contratto`
  MODIFY `IDContratto` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=156;
--
-- AUTO_INCREMENT per la tabella `fattura`
--
ALTER TABLE `fattura`
  MODIFY `IDFattura` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=126;
--
-- AUTO_INCREMENT per la tabella `guasto`
--
ALTER TABLE `guasto`
  MODIFY `IDGuasto` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;
--
-- Limiti per le tabelle scaricate
--

--
-- Limiti per la tabella `contratto`
--
ALTER TABLE `contratto`
  ADD CONSTRAINT `contratto_ibfk_1` FOREIGN KEY (`IDUtente`) REFERENCES `utente` (`IDUtente`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `contratto_ibfk_2` FOREIGN KEY (`IDCompPos`) REFERENCES `componente` (`IDComponente`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Limiti per la tabella `fattura`
--
ALTER TABLE `fattura`
  ADD CONSTRAINT `fattura_ibfk_1` FOREIGN KEY (`IDContratto`) REFERENCES `contratto` (`IDContratto`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Limiti per la tabella `guasto`
--
ALTER TABLE `guasto`
  ADD CONSTRAINT `guasto_ibfk_1` FOREIGN KEY (`IDCliente`) REFERENCES `utente` (`IDUtente`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `guasto_ibfk_2` FOREIGN KEY (`IDManutentore`) REFERENCES `utente` (`IDUtente`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `guasto_ibfk_3` FOREIGN KEY (`IDComponente`) REFERENCES `componente` (`IDComponente`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Limiti per la tabella `posizione`
--
ALTER TABLE `posizione`
  ADD CONSTRAINT `posizione_ibfk_1` FOREIGN KEY (`IDComponente`) REFERENCES `componente` (`IDComponente`) ON DELETE CASCADE ON UPDATE CASCADE;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
