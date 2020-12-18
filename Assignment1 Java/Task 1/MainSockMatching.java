//package task1;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.*;

public class MainSockMatching{

	List<Integer>socks;
	int socks_count;
	int robots_count;
	List<Integer>sock_in_heap;  // Store Socks Id wich are currently present in heap;
	private List<Robot> robots;

	private MatchingMachine matching_machine;    // Sock matcher
    private ShelfManager shelf_manager;  // Shelf manager

    Random random = new Random();

	void init_machine(int r_count, int s_count) throws InterruptedException{
		this.robots_count = r_count;
		this.socks_count = s_count;
		sock_in_heap = new ArrayList<>();
		socks = new ArrayList<>();
		for(int i=0;i<this.socks_count;i++){
			this.sock_in_heap.add(i);
			this.socks.add(random.nextInt(4));
		}
		System.out.println("Socks Randomly generated.");

		
        this.shelf_manager = new ShelfManager();
        
        this.matching_machine = new MatchingMachine(shelf_manager);

        robots = new ArrayList<>();
        for (int i = 0; i < this.robots_count; i++) {
            Robot robot = new Robot(this, this.matching_machine, i);
            this.robots.add(robot);
        }

	}


	void start_machine() throws InterruptedException{

		for(int i=0;i<robots.size();i++){
			robots.get(i).start();
		}

		for(int i=0;i<robots.size();i++){
			robots.get(i).join();
		}

		shelf_manager.shelves();
	}

	int get_sock(int id){
		int sock_id = -1;
		int sock_color = -1;
		synchronized (sock_in_heap) {
            if (sock_in_heap.size() > 0) {
                sock_id = random.nextInt(sock_in_heap.size());
                sock_color = socks.get(sock_id);
               // System.out.println("Sock of color "+Integer.toString(sock_color)+" picked by robot "+Integer.toString(id));
        		sock_in_heap.remove(sock_id);
        		
            }
        }

        if(sock_id!=-1){
        	return sock_color;
        }

        return -1;

	}

	public static void main(String[] args) throws InterruptedException{
		Scanner scanner = new Scanner(System.in);
		System.out.print("Number Of Robots Working: ");
		int robots_count = scanner.nextInt();
		System.out.print("Number Of Socks in Heap: ");
		int socks_count = scanner.nextInt();
		

		MainSockMatching main_sock_matching = new MainSockMatching();
		
		main_sock_matching.init_machine(robots_count,socks_count);
		main_sock_matching.start_machine();

		
	}




}