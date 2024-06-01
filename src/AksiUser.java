import java.util.Map;
import java.util.Scanner;

public class AksiUser extends Aksi {
    @Override
    public void tampilanAksi() {
        System.out.println("Aksi User:");
        System.out.println("1. Pesan Film");
        System.out.println("2. Lihat Saldo");
        System.out.println("3. Lihat List Film");
        System.out.println("4. Lihat Pesanan");
        System.out.println("5. Logout");
    }

    @Override
    public void keluar() {
        Akun.logout();
        System.out.println("Anda telah logout.");
    }

    @Override
    public void tutupAplikasi() {
        System.out.println("Selamat tinggal!");
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

    public void lihatSaldo() {
        System.out.printf("Saldo Anda: Rp%,.2f\n", Akun.getCurrentUser().getSaldo());
    }

    public void pesanFilm() {
        Scanner scanner = new Scanner(System.in);

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

        Film selectedFilm = null;
        while (selectedFilm == null) {
            System.out.print("Nama film yang ingin dipesan: ");
            String filmName = scanner.nextLine();

            if (filmName.isBlank()) {
                System.out.println("Nama film tidak boleh kosong.");
                continue;
            }

            selectedFilm = availableFilms.get(filmName);
            if (selectedFilm == null) {
                System.out.println("Film yang dicari tidak ditemukan.");
            }
        }

        int ticketAmount;
        while (true) {
            System.out.print("Jumlah tiket yang ingin dipesan: ");
            if (scanner.hasNextInt()) {
                ticketAmount = scanner.nextInt();
                scanner.nextLine();

                if (ticketAmount <= 0) {
                    System.out.println("Jumlah tiket harus lebih dari 0.");
                } else if (ticketAmount > selectedFilm.getStock()) {
                    System.out.println("Stok tiket tidak mencukupi.");
                } else {
                    break;
                }
            } else {
                System.out.println("Input jumlah tiket tidak valid. Masukkan angka.");
                scanner.nextLine();
            }
        }

        double totalPrice = selectedFilm.getPrice() * ticketAmount;
        if (Akun.getCurrentUser().getSaldo() < totalPrice) {
            System.out.printf("Total harga: Rp %,.2f\n", totalPrice);
            System.out.printf("Saldo tidak mencukupi, saldo yang dimiliki Rp %,.2f\n", Akun.getCurrentUser().getSaldo());
            return;
        }

        Akun.getCurrentUser().addPesanan(selectedFilm, ticketAmount);

        Akun.getCurrentUser().setSaldo(Akun.getCurrentUser().getSaldo() - totalPrice);
        selectedFilm.setStock(selectedFilm.getStock() - ticketAmount);
        System.out.printf("Harga satuan tiket: Rp %,.2f\n", selectedFilm.getPrice());
        System.out.printf("Total harga: Rp %,.2f\n", totalPrice);
        System.out.println("Pemesanan berhasil.");
    }

    public void lihatPesanan() {
        Map<String, Pesanan> pesanan = Akun.getCurrentUser().getPesanan();

        if (pesanan.isEmpty()) {
            System.out.println("Anda belum pernah melakukan pemesanan.");
            return;
        }

        System.out.println("\nDaftar Pesanan:");
        System.out.println("-----------------------------");
        for (Pesanan order : pesanan.values()) {
            System.out.printf("Film: %s - Jumlah: %d - Total Harga: Rp. %,.2f\n", order.getFilm().getName(), order.getKuantitas(), order.getFilm().getPrice() * order.getKuantitas());
        }
    }
}
