import java.util.Map;
import java.util.Scanner;

public class AksiAdmin extends Aksi {
    @Override
    public void tampilanAksi() {
        System.out.println("Aksi Admin:");
        System.out.println("1. Tambah Film");
        System.out.println("2. Lihat List Film");
        System.out.println("3. Logout");
        System.out.println("4. Tutup Aplikasi");
    }

    @Override
    public void keluar() {
        Akun.logout();
        System.out.println("Anda telah logout.");
    }

    @Override
    public void tutupAplikasi() {
        System.out.println("Aplikasi ditutup.");
        System.exit(0);
    }

    @Override
    public void lihatListFilm() {
        Map<String, Film> availableFilms = Film.getFilms();

        if (availableFilms.isEmpty()) {
            System.out.println("Maaf, saat ini tidak ada film yang tersedia.");
            return;
        }

        System.out.println("\nDaftar Film yang Tersedia:");
        System.out.println("-----------------------------");
        for (Film film : availableFilms.values()) {
            System.out.printf("%s - %s - Harga: Rp %,.2f - Stok: %d\n", film.getName(), film.getDescription(), film.getPrice(), film.getStock());
        }
    }

    public void tambahFilm() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Nama Film: ");
        String name = scanner.nextLine();

        // Validation
        if (name.isBlank()) {
            System.out.println("Nama film tidak boleh kosong.");
            return;
        } else if (Film.getFilms().containsKey(name)) {
            System.out.println("Film " + name + " sudah ada.");
            return;
        }

        System.out.print("Deskripsi Film: ");
        String description = scanner.nextLine();

        // Validation
        if (description.isBlank()) {
            System.out.println("Deskripsi film tidak boleh kosong.");
            return;
        }

        double price;
        while (true) {
            System.out.print("Harga Tiket: ");
            if (scanner.hasNextDouble()) {
                price = scanner.nextDouble();
                if (price > 0) {
                    break;
                } else {
                    System.out.println("Harga harus lebih dari 0.");
                }
            } else {
                System.out.println("Input harga tidak valid. Masukkan angka.");
                scanner.next();
            }
        }

        int stock;
        while (true) {
            System.out.print("Stok Tiket: ");
            if (scanner.hasNextInt()) {
                stock = scanner.nextInt();
                if (stock >= 0) {
                    break;
                } else {
                    System.out.println("Stok tidak boleh negatif.");
                }
            } else {
                System.out.println("Input stok tiket tidak valid!.");
                scanner.next();
            }
        }

        Film.addFilm(name, description, price, stock);
    }
}
