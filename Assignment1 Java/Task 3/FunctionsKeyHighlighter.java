import java.util.Arrays;


class FunctionsKeyHighlighter implements Runnable{

	Integer is_show = 0;
	Calculator gui;
	FunctionsKeyHighlighter(Calculator gui){
		this.gui = gui;
	}
	@Override
    public void run()
    {
        int i=0;
        for(i = 0;i<4;i++){
        	gui.set_func_color(i, new java.awt.Color(224, 224, 235));
        }i=0;
        while(true)
        {
        	//System.out.println(gui.is_first);
        	if(gui.is_func){
        		return;
        	}
        	synchronized(gui.is_first){
	        	if(gui.is_first==true){
		            gui.set_func_color((i+3)%4, new java.awt.Color(224, 224, 235));
		            gui.set_func_color(i, new java.awt.Color(255, 51, 133));
		            i = (i+1)%4;
		            //System.out.println(i);
		           // System.out.println("ewws");
		            //long expected = System.currentTimeMillis() + 1000;
		            try{
		            	Thread.sleep(3000);
		            }
		            catch(Exception e)
		            {
		                System.out.println(Arrays.toString(e.getStackTrace()));
		            }
	        	}
       		}
        }
    }




}