

public class Robot extends Thread{
	private MainSockMatching sock_matching;  
    private MatchingMachine matching_machine;
    private int robot_id; 
    Robot(MainSockMatching main_sock_matching,MatchingMachine matching_machine, int id) {
        super();
        this.robot_id = id;
        this.sock_matching = main_sock_matching;
        this.matching_machine = matching_machine;
    }


    @Override
    public void run() {
    	int c = 0;
        while (true) {
            
            int sock_to_pick = sock_matching.get_sock(robot_id);
            if (sock_to_pick == -1) {   // If no sock left in the
                System.out.println("Thread " + Integer.toString(robot_id) + " Stopped!");
                
               break;
            }else{ c++;}
            matching_machine.match_sock(sock_to_pick);
        }
        System.out.println("Thread "+Integer.toString(robot_id)+" have picked "+Integer.toString(c)+" socks.");
    }

}