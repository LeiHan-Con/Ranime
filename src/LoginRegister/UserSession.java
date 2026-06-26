/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package LoginRegister;

/**
 *
 * @author Rivaldo
 */
public class UserSession {
    // Variabel static agar nilainya bisa dipanggil langsung tanpa bikin objek baru
    private static int id;
    private static String username;
    private static String role;

    // Getter dan Setter
    public static int getId() {
        return id;
    }

    public static void setId(int id) {
        UserSession.id = id;
    }

    public static String getUsername() {
        return username;
    }

    public static void setUsername(String username) {
        UserSession.username = username;
    }

    public static String getRole() {
        return role;
    }

    public static void setRole(String role) {
        UserSession.role = role;
    }

    // Method untuk membersihkan session saat user Logout
    public static void clear() {
        id = 0;
        username = null;
        role = null;
    }
}
