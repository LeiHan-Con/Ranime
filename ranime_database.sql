-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Waktu pembuatan: 27 Jun 2026 pada 12.02
-- Versi server: 10.4.32-MariaDB
-- Versi PHP: 8.2.12

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
-- Struktur dari tabel `anime`
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
-- Dumping data untuk tabel `anime`
--

INSERT INTO `anime` (`id`, `judul`, `genre`, `episode`, `status`, `sinopsis`, `image_path`, `video_path`, `folder_path`, `created_at`) VALUES
(1, 'Naruto Shippuden', 'Action, Adventure', 500, 'Completed', 'menceritakan petualangan Naruto Uzumaki remaja yang kembali ke Desa Konoha setelah dua setengah tahun berlatih intensif. Ia harus menyelamatkan sahabatnya, Sasuke Uchiha, dari cengkeraman balas dendam, sekaligus menghadapi ancaman global dari organisasi kriminal ninja yang sangat berbahaya, Akatsuki', 'assets/narutoshippuden/NarutoShippuden_thumbnail.jpeg', 'videos/naruto.mp4', 'assets/narutoshippuden/', '2026-06-24 11:10:56'),
(2, 'Spy x Family', 'Action, Comedy', 12, 'Completed', 'Menceritakan mata-mata bernama Loid Forger yang harus membentuk keluarga palsu untuk menjalankan misi penting. Tanpa ia ketahui, anak angkatnya adalah seorang telepat dan istrinya adalah seorang pembunuh bayaran.', 'assets/spyxfamily/SpyxFamily_thumbnail.jpeg', 'assets/spyxfamily/SpyxFamily_eps1.mp4', 'assets/spyxfamily/', '2026-06-24 11:10:56'),
(3, 'Kimetsu no Yaiba', 'Action, Fantasy', 26, 'Completed', 'Mengisahkan Tanjiro Kamado yang berjuang menjadi pembasmi iblis setelah keluarganya dibantai oleh iblis dan adiknya, Nezuko, berubah menjadi iblis.', 'assets/kimetsunoyaiba/KimetsuNoYaiba_thumbnail.jpeg', 'videos/kimetsu.mp4', 'assets/kimetsunoyaiba/', '2026-06-24 11:10:56'),
(4, 'One Piece', 'Adventure, Action', 1100, 'Ongoing', 'Menceritakan perjalanan Monkey D. Luffy dan kru Topi Jerami dalam mencari harta karun legendaris bernama One Piece untuk menjadi Raja Bajak Laut.', 'assets/onepiece/OnePiece_thumbnail.jpeg', 'videos/onepiece.mp4', 'assets/onepiece/', '2026-06-24 11:10:56'),
(5, 'Jujutsu Kaisen', 'Action, Supernatural', 24, 'Completed', 'Yuji Itadori terlibat dalam dunia kutukan setelah menelan jari Ryomen Sukuna. Ia kemudian bergabung dengan sekolah Jujutsu untuk melawan roh-roh kutukan berbahaya.', 'assets/jujutsukaisen/JujutsuKaisen_thumbnail.jpeg\r\n', 'assets/jujutsukaisen/JujutsuKaisen_eps1.mp4\r\n', 'assets/jujutsukaisen/', '2026-06-24 11:10:56'),
(6, 'Kaichou wa Maid-sama!', 'Comedy, Romance', 26, 'Completed', 'Misaki Ayuzawa adalah ketua OSIS yang tegas di sekolah yang didominasi laki-laki. Rahasianya bekerja paruh waktu di sebuah maid cafe diketahui oleh Usui Takumi, siswa paling populer di sekolah.', 'assets/kaichouwamaidsama/KaichouWaMaidSama_thumbnail.jpg', 'assets/kaichouwamaidsama/KaichouWaMaidSama_eps1.mp4', 'assets/kaichouwamaidsama/', '2026-06-24 11:10:56');

-- --------------------------------------------------------

--
-- Struktur dari tabel `users`
--

CREATE TABLE `users` (
  `id` int(11) NOT NULL,
  `username` varchar(50) NOT NULL,
  `password` varchar(255) NOT NULL,
  `role` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data untuk tabel `users`
--

INSERT INTO `users` (`id`, `username`, `password`, `role`) VALUES
(1, 'mimin', 'admin1234', 'Admin'),
(2, 'user', 'user1234', 'User'),
(3, 'Usui Takumi', 'Misa-Chan', 'User'),
(4, 'Misaki Ayuzawa', 'ahousuii', 'user'),
(5, 'kazuha', 'seika123', 'user');

-- --------------------------------------------------------

--
-- Struktur dari tabel `watched`
--

CREATE TABLE `watched` (
  `id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `anime_id` int(11) NOT NULL,
  `watched_at` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data untuk tabel `watched`
--

INSERT INTO `watched` (`id`, `user_id`, `anime_id`, `watched_at`) VALUES
(1, 3, 1, '2026-06-26 07:58:23'),
(2, 3, 2, '2026-06-26 07:59:18'),
(3, 2, 6, '2026-06-26 08:09:52'),
(4, 2, 6, '2026-06-26 08:19:39'),
(5, 2, 6, '2026-06-26 08:23:38'),
(6, 2, 6, '2026-06-26 08:29:17'),
(7, 2, 6, '2026-06-26 08:42:12'),
(8, 2, 1, '2026-06-26 08:42:17'),
(9, 2, 6, '2026-06-26 08:58:32'),
(10, 2, 6, '2026-06-26 09:16:51'),
(11, 2, 6, '2026-06-26 09:20:12'),
(12, 2, 6, '2026-06-26 09:24:05'),
(13, 2, 1, '2026-06-26 09:24:50'),
(14, 2, 6, '2026-06-26 09:25:20'),
(15, 2, 6, '2026-06-26 09:35:07'),
(16, 2, 6, '2026-06-26 09:37:28'),
(17, 2, 6, '2026-06-26 10:07:39'),
(18, 2, 6, '2026-06-26 10:08:44'),
(19, 2, 6, '2026-06-26 10:10:22'),
(20, 2, 6, '2026-06-26 10:11:39'),
(21, 2, 6, '2026-06-26 10:15:50'),
(22, 2, 6, '2026-06-26 10:18:30'),
(23, 2, 6, '2026-06-26 10:21:53'),
(24, 2, 6, '2026-06-26 11:02:07'),
(25, 2, 6, '2026-06-26 11:04:07'),
(26, 2, 1, '2026-06-26 11:05:30'),
(27, 2, 6, '2026-06-26 11:05:56'),
(28, 2, 6, '2026-06-26 11:06:17'),
(29, 2, 1, '2026-06-26 11:07:10'),
(30, 2, 6, '2026-06-26 11:07:13'),
(31, 2, 6, '2026-06-26 11:07:36'),
(32, 2, 1, '2026-06-26 11:07:40'),
(33, 2, 6, '2026-06-26 11:07:42'),
(34, 2, 6, '2026-06-26 11:07:48'),
(35, 2, 6, '2026-06-26 11:09:20'),
(36, 2, 1, '2026-06-26 11:09:27'),
(37, 2, 6, '2026-06-26 11:09:28'),
(38, 2, 6, '2026-06-26 11:10:06'),
(39, 2, 2, '2026-06-26 11:10:08'),
(40, 2, 1, '2026-06-26 11:10:10'),
(41, 2, 3, '2026-06-26 11:10:12'),
(42, 2, 4, '2026-06-26 11:10:13'),
(43, 2, 6, '2026-06-26 11:10:14'),
(44, 2, 2, '2026-06-26 11:10:16'),
(45, 2, 3, '2026-06-26 11:10:22'),
(46, 2, 4, '2026-06-26 11:10:24'),
(47, 2, 6, '2026-06-26 11:10:27'),
(48, 2, 5, '2026-06-26 11:10:29'),
(49, 2, 1, '2026-06-26 11:10:33'),
(50, 3, 2, '2026-06-26 11:12:56'),
(51, 3, 2, '2026-06-26 11:13:10'),
(52, 3, 6, '2026-06-26 11:13:29'),
(53, 3, 6, '2026-06-26 11:13:32'),
(54, 3, 4, '2026-06-26 11:13:36'),
(55, 3, 6, '2026-06-26 11:14:18'),
(56, 3, 6, '2026-06-26 15:09:06'),
(57, 3, 6, '2026-06-26 15:11:08'),
(58, 3, 6, '2026-06-26 15:33:41'),
(59, 4, 2, '2026-06-26 16:28:08'),
(60, 2, 2, '2026-06-26 16:29:20'),
(61, 2, 6, '2026-06-26 16:35:50'),
(62, 2, 6, '2026-06-26 16:37:32'),
(63, 2, 2, '2026-06-26 16:37:45'),
(64, 2, 5, '2026-06-26 17:06:52'),
(65, 3, 6, '2026-06-27 06:06:50'),
(66, 4, 6, '2026-06-27 06:20:04'),
(67, 4, 6, '2026-06-27 06:20:06'),
(68, 2, 6, '2026-06-27 06:48:47'),
(69, 2, 6, '2026-06-27 06:48:56'),
(70, 2, 6, '2026-06-27 09:50:59'),
(71, 2, 3, '2026-06-27 09:57:28'),
(72, 2, 4, '2026-06-27 09:57:32'),
(73, 2, 6, '2026-06-27 09:57:35'),
(74, 2, 2, '2026-06-27 09:57:39'),
(75, 2, 1, '2026-06-27 09:57:42'),
(76, 2, 6, '2026-06-27 09:58:17'),
(77, 2, 6, '2026-06-27 09:58:19'),
(78, 2, 2, '2026-06-27 10:00:12');

--
-- Indexes for dumped tables
--

--
-- Indeks untuk tabel `anime`
--
ALTER TABLE `anime`
  ADD PRIMARY KEY (`id`);

--
-- Indeks untuk tabel `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `username` (`username`);

--
-- Indeks untuk tabel `watched`
--
ALTER TABLE `watched`
  ADD PRIMARY KEY (`id`),
  ADD KEY `user_id` (`user_id`),
  ADD KEY `anime_id` (`anime_id`);

--
-- AUTO_INCREMENT untuk tabel yang dibuang
--

--
-- AUTO_INCREMENT untuk tabel `anime`
--
ALTER TABLE `anime`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT untuk tabel `users`
--
ALTER TABLE `users`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT untuk tabel `watched`
--
ALTER TABLE `watched`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=79;

--
-- Ketidakleluasaan untuk tabel pelimpahan (Dumped Tables)
--

--
-- Ketidakleluasaan untuk tabel `watched`
--
ALTER TABLE `watched`
  ADD CONSTRAINT `watched_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
  ADD CONSTRAINT `watched_ibfk_2` FOREIGN KEY (`anime_id`) REFERENCES `anime` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
