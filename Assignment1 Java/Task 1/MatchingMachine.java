//package task1;


import java.util.concurrent.*;
class MatchingMachine{
	private ShelfManager shelf_manager;
    private Integer white_sock;
    private Integer blue_sock;
    private Integer black_sock;
    private Integer grey_sock;

    MatchingMachine(ShelfManager shelf_manager) {
        white_sock = 0;
        black_sock = 0;
        blue_sock = 0;
        grey_sock = 0;
        this.shelf_manager = shelf_manager;
    }

    void match_sock(int sock_color){
    	if(sock_color == 0){
    		synchronized (white_sock) {
                if (white_sock == 1) {  
                    shelf_manager.manage(0);
                    
                    white_sock = 0;
                } else { 
                    white_sock = 1;
                }
            }
    	}else if (sock_color == 1){
    		synchronized (black_sock) {
                if (black_sock == 1) {  
                    shelf_manager.manage(1);
                    
                    black_sock = 0;
                } else { 
                    black_sock = 1;
                }
            }
    	}else if(sock_color == 2){
    		synchronized (blue_sock) {
                if (blue_sock == 1) { 
                    shelf_manager.manage(2);
                    blue_sock = 0;
                } else { 
                    blue_sock = 1;
                }
            }
    	}else{
    		synchronized (grey_sock) {
                if (grey_sock == 1) {  
                    shelf_manager.manage(3);
                    grey_sock = 0;
                } else { 
                    grey_sock = 1;
                }
            }
    	}
    }
}