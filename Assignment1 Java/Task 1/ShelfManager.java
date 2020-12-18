//package task1;
/*
 * Shelf Manager recieves a sock pair and puts it in the right shelf
 * */
import java.util.concurrent.*;
public class ShelfManager {
    // Count of socks represents the different shelves
    private Integer white_pair_count;
    private Integer black_pair_count;
    private Integer blue_pair_count;
    private Integer grey_pair_count;

    /*
     * Constructor
     * */
    ShelfManager() {
        white_pair_count = 0;
        black_pair_count = 0;
        blue_pair_count = 0;
        grey_pair_count = 0;
    }

    /*
     * Recieves a pair of sock of some color
     * put it in the correct shelf
     * */
    void manage(int sock_color) {
        if (sock_color == 0) {
            synchronized (white_pair_count) {
                white_pair_count += 1;    // 2 socks
            }
        } else if (sock_color == 1) {
            synchronized (black_pair_count) {
                black_pair_count += 1;    // 2 socks
            }
        } else if (sock_color == 2) {
            synchronized (blue_pair_count) {
                blue_pair_count += 1;    // 2 socks
            }
        } else if (sock_color == 3) {
            synchronized (grey_pair_count) {
                grey_pair_count += 1;    // 2 socks
            }
        }
    }

    // Print the collected socks finally when the program ends.
    void shelves() {
        System.out.println(String.format("White Socks at the shelf: %d, Pair: %d\n",white_pair_count*2, white_pair_count));
        System.out.println(String.format("Black Socks at the shelf: %d, Pair: %d\n",black_pair_count*2, black_pair_count));
        System.out.println(String.format("Blue  Socks at the shelf: %d, Pair: %d\n",blue_pair_count*2,   blue_pair_count));
        System.out.println(String.format("Grey  Socks at the shelf: %d, Pair: %d\n",grey_pair_count*2,   grey_pair_count));


    }
}
