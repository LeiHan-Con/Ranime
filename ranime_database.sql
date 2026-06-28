-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jun 28, 2026 at 09:01 AM
-- Server version: 10.4.32-MariaDB
-- PHP Version: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `ranime_database`
--

-- --------------------------------------------------------

--
-- Table structure for table `anime`
--

CREATE TABLE `anime` (
  `id` int(11) NOT NULL,
  `judul` varchar(255) NOT NULL,
  `genre` varchar(100) DEFAULT NULL,
  `episode` int(11) DEFAULT NULL,
  `status` varchar(50) DEFAULT NULL,
  `sinopsis` text DEFAULT NULL,
  `image_path` varchar(255) DEFAULT NULL,
  `video_path` varchar(255) DEFAULT NULL,
  `folder_path` varchar(255) NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `anime`
--

INSERT INTO `anime` (`id`, `judul`, `genre`, `episode`, `status`, `sinopsis`, `image_path`, `video_path`, `folder_path`, `created_at`) VALUES
(1, 'Naruto Shippuden', 'Action, Adventure', 500, 'Completed', 'menceritakan petualangan Naruto Uzumaki remaja yang kembali ke Desa Konoha setelah dua setengah tahun berlatih intensif. Ia harus menyelamatkan sahabatnya, Sasuke Uchiha, dari cengkeraman balas dendam, sekaligus menghadapi ancaman global dari organisasi kriminal ninja yang sangat berbahaya, Akatsuki', 'assets/narutoshippuden/NarutoShippuden_thumbnail.jpeg', 'videos/naruto.mp4', 'assets/narutoshippuden/', '2026-06-24 11:10:56'),
(2, 'Spy x Family', 'Action, Comedy', 12, 'Completed', 'Menceritakan mata-mata bernama Loid Forger yang harus membentuk keluarga palsu untuk menjalankan misi penting. Tanpa ia ketahui, anak angkatnya adalah seorang telepat dan istrinya adalah seorang pembunuh bayaran.', 'assets/spyxfamily/SpyxFamily_thumbnail.jpeg', 'assets/spyxfamily/SpyxFamily_eps1.mp4', 'assets/spyxfamily/', '2026-06-24 11:10:56'),
(3, 'Kimetsu no Yaiba', 'Action, Fantasy', 26, 'Completed', 'Mengisahkan Tanjiro Kamado yang berjuang menjadi pembasmi iblis setelah keluarganya dibantai oleh iblis dan adiknya, Nezuko, berubah menjadi iblis.', 'assets/kimetsunoyaiba/KimetsuNoYaiba_thumbnail.jpeg', 'videos/kimetsu.mp4', 'assets/kimetsunoyaiba/', '2026-06-24 11:10:56'),
(4, 'One Piece', 'Adventure, Action', 1100, 'Ongoing', 'Menceritakan perjalanan Monkey D. Luffy dan kru Topi Jerami dalam mencari harta karun legendaris bernama One Piece untuk menjadi Raja Bajak Laut.', 'assets/onepiece/OnePiece_thumbnail.jpeg', 'videos/onepiece.mp4', 'assets/onepiece/', '2026-06-24 11:10:56'),
(5, 'Jujutsu Kaisen', 'Action, Supernatural', 24, 'Completed', 'Yuji Itadori terlibat dalam dunia kutukan setelah menelan jari Ryomen Sukuna. Ia kemudian bergabung dengan sekolah Jujutsu untuk melawan roh-roh kutukan berbahaya.', 'assets/jujutsukaisen/JujutsuKaisen_thumbnail.jpeg\r\n', 'assets/jujutsukaisen/JujutsuKaisen_eps1.mp4\r\n', 'assets/jujutsukaisen/', '2026-06-24 11:10:56'),
(6, 'Kaichou wa Maid-sama!', 'Comedy, Romance', 26, 'Completed', 'Misaki Ayuzawa adalah ketua OSIS yang tegas di sekolah yang didominasi laki-laki. Rahasianya bekerja paruh waktu di sebuah maid cafe diketahui oleh Usui Takumi, siswa paling populer di sekolah.', 'assets/kaichouwamaidsama/KaichouWaMaidSama_thumbnail.jpg', 'assets/kaichouwamaidsama/KaichouWaMaidSama_eps2.mp4', 'assets/kaichouwamaidsama/', '2026-06-24 11:10:56');

-- --------------------------------------------------------

--
-- Table structure for table `bookmarks`
--

CREATE TABLE `bookmarks` (
  `id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `anime_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `bookmarks`
--

INSERT INTO `bookmarks` (`id`, `user_id`, `anime_id`) VALUES
(23, 6, 2),
(31, 6, 6),
(32, 6, 5),
(33, 8, 6),
(34, 9, 6),
(35, 7, 6);

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `id` int(11) NOT NULL,
  `username` varchar(50) NOT NULL,
  `password` varchar(255) NOT NULL,
  `role` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`id`, `username`, `password`, `role`) VALUES
(1, 'mimin', 'admin1234', 'Admin'),
(2, 'user', 'user1234', 'User'),
(3, 'Usui Takumi', 'Misa-Chan', 'User'),
(4, 'Misaki Ayuzawa', 'ahousuii', 'user'),
(5, 'kazuha', 'seika123', 'user'),
(6, 'Aldo', '12345678', 'user'),
(7, 'Aldo1', '12345678', 'user'),
(8, 'test', '123123123', 'user'),
(9, 'Rayhan', '123123123', 'user');

-- --------------------------------------------------------

--
-- Table structure for table `watched`
--

CREATE TABLE `watched` (
  `id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `anime_id` int(11) NOT NULL,
  `episode_tonton` varchar(10) DEFAULT NULL,
  `watched_at` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `watched`
--

INSERT INTO `watched` (`id`, `user_id`, `anime_id`, `episode_tonton`, `watched_at`) VALUES
(192, 7, 6, '2', '2026-06-28 06:46:00'),
(193, 7, 6, '1', '2026-06-28 06:45:56'),
(195, 6, 6, '1', '2026-06-28 06:52:03');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `anime`
--
ALTER TABLE `anime`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `bookmarks`
--
ALTER TABLE `bookmarks`
  ADD PRIMARY KEY (`id`),
  ADD KEY `anime_id` (`anime_id`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `username` (`username`);

--
-- Indexes for table `watched`
--
ALTER TABLE `watched`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `unique_tontonan` (`user_id`,`anime_id`,`episode_tonton`),
  ADD KEY `user_id` (`user_id`),
  ADD KEY `anime_id` (`anime_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `anime`
--
ALTER TABLE `anime`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT for table `bookmarks`
--
ALTER TABLE `bookmarks`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=36;

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- AUTO_INCREMENT for table `watched`
--
ALTER TABLE `watched`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=196;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `bookmarks`
--
ALTER TABLE `bookmarks`
  ADD CONSTRAINT `bookmarks_ibfk_1` FOREIGN KEY (`anime_id`) REFERENCES `anime` (`id`) ON DELETE CASCADE;

--
-- Constraints for table `watched`
--
ALTER TABLE `watched`
  ADD CONSTRAINT `watched_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
  ADD CONSTRAINT `watched_ibfk_2` FOREIGN KEY (`anime_id`) REFERENCES `anime` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
