import javax.swing.*;
import java.awt.event.*; 
import java.util.*;

public class Calculator{
	JFrame frame;
	String input = "";
	JButton[] number_keys= new JButton[11];
	JButton[] function_keys=new JButton[4];
	JButton enter_key =  new JButton("Enter");
	JButton space_key =  new JButton("Space");
	JTextField output_screen=new JTextField(); 
	int first_number = 0,second_number = 0;
	Boolean is_func = false; 
	Boolean is_first = false, is_second =false,is_answer = false;
	char function_key;
	int highlighted_number_key = 0, highlighted_func_key=0;
	//boolean[] buttons = new boolean[15];

	String text = "";


	void create_frame(){
		frame = new JFrame("Calculator For Disabled Persons");    
		frame.setSize(800,600);    
		frame.setLayout(null);        
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	void number_key_pressed_func(int key){
		//System.out.println(key);
		if(!is_first){
			first_number = first_number*10+key;
			text = text+Integer.toString(key);
			is_first = true;
			output_screen.setText(text);
			return;
		}
		if(is_func && !is_second){
			if(function_key == '/' && key == 0){
				System.out.println("Can Not Divide by zero");
				return;
			}
			second_number = second_number*10+key;
			text = text+Integer.toString(key);
			is_second = true;
			output_screen.setText(text);
			return;
		}
		if(is_func){
			second_number = second_number*10+key;
			text = text+Integer.toString(key);
			output_screen.setText(text);
			return;
		}else{
			first_number = first_number*10+key;
			text = text+Integer.toString(key);
			output_screen.setText(text);
			return;
		}

		
		//System.out.println("Not Valid");
	}

	void function_key_pressed_func(int func_key){
		if(is_func){
			System.out.println("Not Valid Function key");
			return;
		}

		switch(func_key){
			case 0: function_key = '+';text = text + "+";break;
			case 1: function_key = '-';text = text + "-";break;
			case 2: function_key = '*';text = text + "*";break;
			case 3: function_key = '/';text = text + "/";break;
			default: System.out.println("Invalid Key");
		}is_func = true;

		//text = text + Character.toString(func_key);
		output_screen.setText(text);

		return;

	}
	synchronized void enter_key_pressed_func(){
		if(highlighted_number_key == 10){
			stop_key_pressed_func();
		}

		else{
			number_key_pressed_func(highlighted_number_key);
		}
		return;
		
	}

	synchronized void space_key_pressed_func(){
		if(this.is_first == true){
			function_key_pressed_func(highlighted_func_key);
		}
		return;
		
	}

	synchronized void stop_key_pressed_func(){
				/*System.out.println(first_number);
				System.out.println(second_number);
				System.out.println(function_key);*/
		if(is_func && is_first && is_second){
			switch(function_key){
				case '+': text = text + " = " + Integer.toString(first_number+second_number);break;
				case '-': text = text + " = " + Integer.toString(first_number-second_number);break;
				case '*': text = text + " = " + Integer.toString(first_number*second_number);break;
				case '/': text = text + " = " + Integer.toString(first_number/second_number);break;
			}

			output_screen.setText(text);
			is_answer = true;
		}

	}

	synchronized void set_func_color(int key, java.awt.Color color){
		try
        {
            //System.out.println(key);
            highlighted_func_key = key;
            function_keys[key].setBackground(color);
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
	}

	synchronized void set_number_color(int key, java.awt.Color color){
		try
        {
            if(key==9){
            	highlighted_number_key = 0;
            }else if(key == 10){
            	highlighted_number_key = 10;
            }else{
            	highlighted_number_key = (key+1);
            }
            number_keys[key].setBackground(color);
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
	}

	void init_controls(){
		for(int i=0;i<9;i++){
			number_keys[i] = new JButton(Integer.toString(i+1));
			//buttons[i] = true;
		}
		for(int i=0;i<4;i++){
			function_keys[i] = new JButton();
			//buttons[i+10] = false;
		}
		//buttons[14]= false;
		output_screen.setBounds(50,20,300,30);
		output_screen.setEnabled(false);
		//output_screen.setForeground(new java.awt.Color(224, 224, 235));
		frame.add(output_screen);

		number_keys[0].setBounds(50,100,95,30);
		frame.add(number_keys[0]);
		number_keys[0].addActionListener(new ActionListener(){  
			public void actionPerformed(ActionEvent e){  
					number_key_pressed_func(1);
       		 }  
    	});  

		number_keys[1].setBounds(150,100,95,30);
		frame.add(number_keys[1]);
		number_keys[1].addActionListener(new ActionListener(){  
			public void actionPerformed(ActionEvent e){  
					number_key_pressed_func(2);
       		 }  
    	});

		number_keys[2].setBounds(250,100,95,30);
		frame.add(number_keys[2]);
		number_keys[2].addActionListener(new ActionListener(){  
			public void actionPerformed(ActionEvent e){  
					number_key_pressed_func(3);
       		 }  
    	});

		number_keys[3].setBounds(50,140,95,30);
		frame.add(number_keys[3]);
		number_keys[3].addActionListener(new ActionListener(){  
			public void actionPerformed(ActionEvent e){  
					number_key_pressed_func(4);
       		 }  
    	});

		number_keys[4].setBounds(150,140,95,30);
		frame.add(number_keys[4]);
		number_keys[4].addActionListener(new ActionListener(){  
			public void actionPerformed(ActionEvent e){  
					number_key_pressed_func(5);
       		 }  
    	});

		number_keys[5].setBounds(250,140,95,30);
		frame.add(number_keys[5]);
		number_keys[5].addActionListener(new ActionListener(){  
			public void actionPerformed(ActionEvent e){  
					number_key_pressed_func(6);
       		 }  
    	});

		number_keys[6].setBounds(50,180,95,30);
		frame.add(number_keys[6]);
		number_keys[6].addActionListener(new ActionListener(){  
			public void actionPerformed(ActionEvent e){  
					number_key_pressed_func(7);
       		 }  
    	});

		number_keys[7].setBounds(150,180,95,30);
		frame.add(number_keys[7]);
		number_keys[7].addActionListener(new ActionListener(){  
			public void actionPerformed(ActionEvent e){  
					number_key_pressed_func(8);
       		 }  
    	});

		number_keys[8].setBounds(250,180,95,30);
		frame.add(number_keys[8]);
		number_keys[8].addActionListener(new ActionListener(){  
			public void actionPerformed(ActionEvent e){  
					number_key_pressed_func(9);
       		 }  
    	});


		number_keys[9] = new JButton("0");
		number_keys[9].setBounds(150,220,95,30);
		frame.add(number_keys[9]);
		number_keys[9].addActionListener(new ActionListener(){  
			public void actionPerformed(ActionEvent e){  
					number_key_pressed_func(0);
       		 }  
    	});

		number_keys[10] = new JButton("Stop");
    	number_keys[10].setBounds(250,220,95,30);
		frame.add(number_keys[10]);
		number_keys[10].addActionListener(new ActionListener(){  
			public void actionPerformed(ActionEvent e){  
					stop_key_pressed_func();
       		 }  
    	});

		
		function_keys[0].setBounds(30,300,95,30);
		function_keys[0].setText("Add(+)");
		frame.add(function_keys[0]);
		function_keys[0].addActionListener(new ActionListener(){  
			public void actionPerformed(ActionEvent e){  
					function_key_pressed_func('+');
       		 }  
    	});

		function_keys[1].setBounds(130,300,95,30);
		function_keys[1].setText("Subtract(-)");
		frame.add(function_keys[1]);
		function_keys[1].addActionListener(new ActionListener(){  
			public void actionPerformed(ActionEvent e){  
					function_key_pressed_func('-');
       		 }  
    	});

		function_keys[2].setBounds(230,300,95,30);
		function_keys[2].setText("Multiply(*)");
		frame.add(function_keys[2]);
		function_keys[2].addActionListener(new ActionListener(){  
			public void actionPerformed(ActionEvent e){  
					function_key_pressed_func('*');
       		 }  
    	});

		function_keys[3].setBounds(330,300,95,30);
		function_keys[3].setText("Divide(/)");
		frame.add(function_keys[3]);
		function_keys[3].addActionListener(new ActionListener(){  
			public void actionPerformed(ActionEvent e){  
					function_key_pressed_func('/');
       		 }  
    	});

		enter_key.setBounds(220,350,95,30);
		frame.add(enter_key);
		enter_key.addActionListener(new ActionListener(){  
			public void actionPerformed(ActionEvent e){  
					enter_key_pressed_func();
       		 }  
    	});

    	space_key.setBounds(120,350,95,30);
		frame.add(space_key);
		space_key.addActionListener(new ActionListener(){  
			public void actionPerformed(ActionEvent e){ 
					//System.out.println("ss"); 
					space_key_pressed_func();
       		 }  
    	});

	}
	void start(){
		create_frame();
		init_controls();	
		//frame.setVisible(true);

		Thread thread1 = new Thread(new NumberKeyHighlighter(this));
        Thread thread2 = new Thread(new FunctionsKeyHighlighter(this));
        thread1.setDaemon(true);
        thread2.setDaemon(true);
        thread1.start();
        thread2.start();

	}

	public static void main(String[] args){
		java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
            	Calculator calc = new Calculator();
            	calc.start();
            	calc.frame.setVisible(true);
                //new CalculatorGUI().setVisible(true);
            }
        });
		
		
	}

}