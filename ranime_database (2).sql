-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Waktu pembuatan: 25 Jun 2026 pada 14.06
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
  `created_at` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data untuk tabel `anime`
--

INSERT INTO `anime` (`id`, `judul`, `genre`, `episode`, `status`, `sinopsis`, `image_path`, `video_path`, `created_at`) VALUES
(1, 'Naruto Shippuden', 'Action, Adventure', 500, 'Completed', 'menceritakan petualangan Naruto Uzumaki remaja yang kembali ke Desa Konoha setelah dua setengah tahun berlatih intensif. Ia harus menyelamatkan sahabatnya, Sasuke Uchiha, dari cengkeraman balas dendam, sekaligus menghadapi ancaman global dari organisasi kriminal ninja yang sangat berbahaya, Akatsuki', 'images/naruto.jpg', 'videos/naruto.mp4', '2026-06-24 11:10:56'),
(2, 'Spy x Family', 'Action, Comedy', 37, 'Ongoing', 'Menceritakan mata-mata bernama Loid Forger yang harus membentuk keluarga palsu untuk menjalankan misi penting. Tanpa ia ketahui, anak angkatnya adalah seorang telepat dan istrinya adalah seorang pembunuh bayaran.', 'images/spyxfamily.jpg', 'videos/spyxfamily.mp4', '2026-06-24 11:10:56'),
(3, 'Kimetsu no Yaiba', 'Action, Fantasy', 55, 'Ongoing', 'Mengisahkan Tanjiro Kamado yang berjuang menjadi pembasmi iblis setelah keluarganya dibantai oleh iblis dan adiknya, Nezuko, berubah menjadi iblis.', 'images/kimetsu.jpg', 'videos/kimetsu.mp4', '2026-06-24 11:10:56'),
(4, 'One Piece', 'Adventure, Action', 1100, 'Ongoing', 'Menceritakan perjalanan Monkey D. Luffy dan kru Topi Jerami dalam mencari harta karun legendaris bernama One Piece untuk menjadi Raja Bajak Laut.', 'images/onepiece.jpg', 'videos/onepiece.mp4', '2026-06-24 11:10:56'),
(5, 'Jujutsu Kaisen', 'Action, Supernatural', 47, 'Ongoing', 'Yuji Itadori terlibat dalam dunia kutukan setelah menelan jari Ryomen Sukuna. Ia kemudian bergabung dengan sekolah Jujutsu untuk melawan roh-roh kutukan berbahaya.', 'images/jujutsukaisen.jpg', 'videos/jujutsukaisen.mp4', '2026-06-24 11:10:56'),
(6, 'Kaichou wa Maid-sama!', 'Comedy, Romance', 26, 'Completed', 'Misaki Ayuzawa adalah ketua OSIS yang tegas di sekolah yang didominasi laki-laki. Rahasianya bekerja paruh waktu di sebuah maid cafe diketahui oleh Usui Takumi, siswa paling populer di sekolah.', 'images/maidsama.jpg', 'videos/maidsama.mp4', '2026-06-24 11:10:56');

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
(4, 'Misaki Ayuzawa', 'ahousuii', 'user');

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
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT untuk tabel `watched`
--
ALTER TABLE `watched`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

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
