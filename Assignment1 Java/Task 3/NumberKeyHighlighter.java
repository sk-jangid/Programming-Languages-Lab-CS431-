import java.util.Arrays;

class NumberKeyHighlighter implements Runnable{

	Calculator gui;
	NumberKeyHighlighter(Calculator gui){
		this.gui = gui;
	}


	@Override
    public void run()
    {
        int i=0;
        for(i = 0;i<11;i++){
        	gui.set_number_color(i, new java.awt.Color(224, 224, 235));
        }i=0;
        while(true)
        {
        	synchronized(gui.is_answer){
	        	if(gui.is_answer){
	        		return;
	        	}
        	}
	       	gui.set_number_color((i+10)%11, new java.awt.Color(224, 224, 235));
	       	synchronized(gui.is_second){
		       	if(i!=10 || gui.is_second){
		        	gui.set_number_color(i, new java.awt.Color(128, 255, 128));

		        	try{
		           		Thread.sleep(3000);
		        	}
			    	catch(Exception e){
		            	System.out.println(Arrays.toString(e.getStackTrace()));
		        	}
		        }

	        	i = (i+1)%11;     
	        }   	
        }
    }
}