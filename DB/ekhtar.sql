-- phpMyAdmin SQL Dump
-- version 4.7.7
-- https://www.phpmyadmin.net/
--
-- Hôte : localhost:3306
-- Généré le :  Dim 18 nov. 2018 à 15:46
-- Version du serveur :  10.1.31-MariaDB
-- Version de PHP :  7.0.26

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données :  `ekhtar`
--

-- --------------------------------------------------------

--
-- Structure de la table `questions`
--

CREATE TABLE `questions` (
  `qid` int(5) NOT NULL,
  `uid` int(5) NOT NULL,
  `qcreated` date NOT NULL,
  `qvalidity` date NOT NULL,
  `content` varchar(150) COLLATE utf8_unicode_ci NOT NULL,
  `ch1` varchar(150) COLLATE utf8_unicode_ci NOT NULL,
  `ch2` varchar(150) COLLATE utf8_unicode_ci NOT NULL,
  `ch3` varchar(150) COLLATE utf8_unicode_ci NOT NULL,
  `ch4` varchar(150) COLLATE utf8_unicode_ci NOT NULL,
  `ch5` varchar(150) COLLATE utf8_unicode_ci NOT NULL,
  `nombrechoix` int(11) NOT NULL,
  `categorie` varchar(50) COLLATE utf8_unicode_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Déchargement des données de la table `questions`
--

INSERT INTO `questions` (`qid`, `uid`, `qcreated`, `qvalidity`, `content`, `ch1`, `ch2`, `ch3`, `ch4`, `ch5`, `nombrechoix`, `categorie`) VALUES
(1, 1, '2017-02-11', '2018-02-11', 'Que préférez vous?', 'Pain au chcoolat', 'Croissant', 'Carré', '', '', 3, 'Fun'),
(2, 1, '2017-02-11', '2018-02-14', 'Blond or black air?', 'Blond', 'Black', 'Other', '', '', 3, 'Fun'),
(3, 3, '2017-02-17', '0000-00-00', 'Best of Liga BBVA', 'Real Marid', 'Barcelone', 'Ateltico Madrid', 'Sevile FC', 'Real Sociedad', 5, 'Sport'),
(4, 0, '2017-02-17', '0000-00-00', 'Actor of the year', 'Leo', 'Johny', '', '', '', 3, 'Cinema'),
(5, 3, '2017-02-17', '0000-00-00', 'Best Chaneb food?', 'Kaskrout chaneb', 'Lablebi', 'Chapati', '', '', 3, 'Fun'),
(9, 3, '2017-02-17', '0000-00-00', 'Android VS iOS', 'Android', 'iOS', '', '', '', 3, 'Science'),
(10, 3, '2017-02-17', '0000-00-00', 'Diesel VS Gasoil', 'Diesel', 'Gasoil', '', '', '', 3, 'Science'),
(11, 5, '2017-02-22', '0000-00-00', 'Best social media?', 'Facebook', 'Twitter', 'Instagram', 'Snapchat', '', 3, 'Fun'),
(12, 3, '2017-02-23', '0000-00-00', 'Best mobile operator?', 'Orange', 'OoredoO', 'Tunisie Telecom', '', '', 3, 'Science'),
(13, 3, '2017-02-23', '0000-00-00', 'I dance', 'Techno', 'Classic', 'Salsa', 'Fazeni', 'Hiphop', 3, 'Art'),
(14, 3, '2017-02-23', '0000-00-00', 'If I was an artist I would be a ', 'Painter', 'Designer', 'Musician', 'Writer', '', 3, 'Art'),
(15, 3, '2017-02-23', '0000-00-00', 'Which the best for a president', 'Marzouki', 'Hechmi Hamdi', 'Hama Hammemi', 'Mahdi Jomaa', '', 3, 'Politics'),
(16, 3, '2017-02-23', '0000-00-00', 'Free Marijuana?', 'YES', 'NO', '', '', '', 3, 'Politics'),
(17, 3, '2017-02-23', '0000-00-00', 'Quitting tunisia because of the gouvernement? ', 'Probably yes', 'Hell no ', '', '', '', 3, 'Politics');

-- --------------------------------------------------------

--
-- Structure de la table `users`
--

CREATE TABLE `users` (
  `id` int(5) NOT NULL,
  `username` varchar(50) NOT NULL,
  `password` varchar(50) NOT NULL,
  `email` varchar(50) NOT NULL,
  `created_at` datetime NOT NULL,
  `updated_at` datetime NOT NULL,
  `name` varchar(50) NOT NULL,
  `birthday` date NOT NULL,
  `nationality` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `users`
--

INSERT INTO `users` (`id`, `username`, `password`, `email`, `created_at`, `updated_at`, `name`, `birthday`, `nationality`) VALUES
(1, 'admin', '21232f297a57a5a743894a0e4a801fc3', 'admin@admin.com', '2017-02-16 00:37:00', '2017-02-16 00:37:00', 'Dia Loghmari', '1994-05-07', 'Tunisian');

-- --------------------------------------------------------

--
-- Structure de la table `vote`
--

CREATE TABLE `vote` (
  `idv` int(11) NOT NULL,
  `qid` int(11) NOT NULL,
  `uid` int(11) NOT NULL,
  `choix` varchar(10) COLLATE utf8_unicode_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;


--
-- Index pour les tables déchargées
--

--
-- Index pour la table `questions`
--
ALTER TABLE `questions`
  ADD PRIMARY KEY (`qid`);

--
-- Index pour la table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`);

--
-- Index pour la table `vote`
--
ALTER TABLE `vote`
  ADD PRIMARY KEY (`idv`);

--
-- AUTO_INCREMENT pour les tables déchargées
--

--
-- AUTO_INCREMENT pour la table `questions`
--
ALTER TABLE `questions`
  MODIFY `qid` int(5) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=18;

--
-- AUTO_INCREMENT pour la table `users`
--
ALTER TABLE `users`
  MODIFY `id` int(5) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- AUTO_INCREMENT pour la table `vote`
--
ALTER TABLE `vote`
  MODIFY `idv` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=21;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
