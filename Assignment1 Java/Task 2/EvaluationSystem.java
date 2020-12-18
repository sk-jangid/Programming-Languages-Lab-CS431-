import java.io.FileWriter;
import java.util.concurrent.Semaphore;
import java.io.*;
import java.util.*;

class EvaluationSystem{
	private final static Scanner scanner = new Scanner(System.in);

	// Store Roll Number to index in the data Array
	private Map<String,Integer> map_to_data = new HashMap<String,Integer>();
	private ArrayList<Data> data =  new ArrayList<>();
    private ArrayList<UpdateBuffer> buffer =  new ArrayList<>();
    private List<Semaphore> sem_locks = new ArrayList<>();
    Boolean sync =false;
	void update_record_buffer(){
		String teacher_name;
		String roll_nu;
        int marks_to_update;
        UpdateBuffer temp  = new UpdateBuffer();
        // Get Teacher name
		while(true){
			System.out.println("Type corresponding Integer Teacher Name( 'CC(0)', 'TA1(1)' or 'TA2(2)'): ");
			int teacher = scanner.nextInt();
			if(teacher ==0){
                temp.teacher = "CC";break;
            }else if(teacher==1){
                temp.teacher = "TA1";break;
            }else if(teacher==2){
                temp.teacher = "TA2";break;
            }
			
			System.out.println("Try Again");
		}
        // Get Roll Number
		System.out.println("Roll Number to update record.");
		roll_nu = scanner.next();
        temp.roll_nu = roll_nu;

        // Marks to update
        System.out.println("Choose Marks updating type.");
        System.out.println("1 - To Increase Marks");
        System.out.println("2 - To decrease Marks.");
		int choice  = scanner.nextInt();
        if(choice == 1){
            System.out.println("Marks to increase:");
            marks_to_update = scanner.nextInt();
        }else{
            System.out.println("Marks to decrease:");
            marks_to_update = -1*scanner.nextInt();
        }
        temp.marks_to_update = marks_to_update;

        buffer.add(temp);


	}


    void get_updated_file(){
        System.out.println(data.size());
        System.out.println("With or Without Sync");
        System.out.println("Type 0- With Sync or 1 to without sync");
        int  choice; 
        while(true){
            choice = scanner.nextInt();
            if(choice ==0 || choice ==1){
                if(choice == 0){
                    sync = true;
                }else{
                    sync =false;
                }
                break;
            }
            System.out.println("Try Again!");
            System.out.println("Type 0 - With Sync or 1 to without sync");
        }

        Teacher teacher_cc = new Teacher(this, "CC",Thread.MAX_PRIORITY);
        Teacher teacher_ta1 = new Teacher(this, "TA1",Thread.NORM_PRIORITY);
        Teacher teacher_ta2 = new Teacher(this, "TA2",Thread.NORM_PRIORITY);


        for (int i=0;i<buffer.size();i++) {
            UpdateBuffer temp = buffer.get(i);
            if (temp.teacher=="TA1") {
                teacher_ta1.add_to_thread_buffer(temp.roll_nu, temp.marks_to_update);
            } else if (temp.teacher=="TA2") {
                teacher_ta2.add_to_thread_buffer(temp.roll_nu, temp.marks_to_update);
            } else if (temp.teacher=="CC") {
                teacher_cc.add_to_thread_buffer(temp.roll_nu, temp.marks_to_update);
            }
        }
        // Clear the global input buffer.
        buffer.clear();

        // Start the threads
        teacher_ta1.start();
        teacher_ta2.start();
        teacher_cc.start();

        // Wait for the threads to complete
        try {
            teacher_cc.join();
            teacher_ta2.join();
            teacher_ta1.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        write_to_files();
        //System.out.println("ss");
    }
    
    void update_marks(String roll_nu, int marks_to_update, String updating_teacher){
            int index;
            if (map_to_data.get(roll_nu) != null) {
                index = map_to_data.get(roll_nu);
            }else{
                return;
            }

            if(sync){
                Boolean succ = sem_locks.get(index).tryAcquire();
                if(succ){
                    update_marks_in_record(index,marks_to_update,updating_teacher);
                    sem_locks.get(index).release();
                }else{
                    update_marks(roll_nu,marks_to_update,updating_teacher);
                }
            }
            else{
                update_marks_in_record(index,marks_to_update,updating_teacher);
            }
    }

    void update_marks_in_record(int index, int marks_to_update, String updating_teacher){
        Data temp_data = data.get(index);
       // System.out.println("s1");\
       /* System.out.println(temp_data.teacher);
        System.out.println(updating_teacher);*/
        if (temp_data.teacher.compareTo("CC")==0 && updating_teacher.compareTo("CC")==0){
            temp_data.marks = temp_data.marks + marks_to_update ;
            temp_data.teacher = updating_teacher;
            data.set(index, temp_data);
            System.out.println("s1");
        }else if(temp_data.teacher.compareTo("CC")!=0){
            temp_data.marks = temp_data.marks + marks_to_update ;
            temp_data.teacher = updating_teacher;
            data.set(index, temp_data);
        }

        return;
    }
    ArrayList<String>  sort(ArrayList<String> keys){
        Data data1, data2;
        String temp;
        for(int i = 0;i<keys.size();i++){
            for(int j = 0;j<keys.size()-i-1;j++){
                data1 = data.get(map_to_data.get(keys.get(j)));
                data2 = data.get(map_to_data.get(keys.get(j+1)));
                /*System.out.println(data1.name+","+data2.name);
                System.out.println(data1.name.compareTo(data2.name));*/
                if(data1.name.compareTo(data2.name) > 0){

                    temp = keys.get(j);
                    keys.set(j,keys.get(j+1));
                    keys.set(j+1, temp);
                }
            }
        }
        return keys;


    }
    private void write_to_files() {
        /* Write back to the original file. */
       // System.out.println("SKj");
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter("res/stud_info.txt"));
        } 
        catch (IOException e) {
            e.printStackTrace();
        }
        if(writer==null){
            return;
        }
        
        for (String roll_nu : map_to_data.keySet())  
        { 
            Data temp_data = data.get(map_to_data.get(roll_nu));
            
            try {
                writer.append(roll_nu);

                writer.append(',');
                writer.append(temp_data.name);

                writer.append(',');
                writer.append(temp_data.mail_id);

                writer.append(',');
                writer.append(Integer.toString(temp_data.marks));
                //System.out.println(temp_data.marks);
                writer.append(',');
                writer.append(temp_data.teacher);

                writer.append('\n');
            } 
            catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

            /* Write to a roll number sorted file. */
            /* Write to a Name sorted file. */
        try {
            writer  = new BufferedWriter(new FileWriter("res/Sorted_Roll.txt"));
            //writer1 = new BufferedWriter(new FileWriter("res/Sorted_Name.txt"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        ArrayList<String> sorted_keys = new ArrayList<>(map_to_data.keySet());
        
        String key;
       /* for(int i=0;i<sorted_keys.size();i++){
            System.out.println(sorted_keys.get(i));
        }*/Collections.sort(sorted_keys);
        for (int i = 0; i<sorted_keys.size();i++) {
            key = sorted_keys.get(i);
           // System.out.println(key);
            try {
                writer.append(key);
                Data temp_data = data.get(map_to_data.get(key));

                writer.append(',');
                writer.append(temp_data.name);

                writer.append(',');
                writer.append(temp_data.mail_id);

                writer.append(',');
                writer.append(Integer.toString(temp_data.marks));
                //System.out.println(temp_data.marks);
                writer.append(',');
                writer.append(temp_data.teacher);

                writer.append('\n');
                
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            writer.flush();
            //writer1.flush();
            writer.close();
           // writer1.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


         try {
            writer = new BufferedWriter(new FileWriter("res/Sorted_Name.txt"));
        } 
        catch (IOException e) {
            e.printStackTrace();
        }
        ArrayList<String> keys = new ArrayList<>(map_to_data.keySet());
        /*for(int i=0;i<keys.size();i++){
            System.out.println(keys.get(i));
        }*/
        //Collections.sort(keys);
        keys=sort(keys);
       /* for(int i=0;i<keys.size();i++){
            System.out.println(keys.get(i));
        }*/
        //String key;
        for (int i = 0; i<keys.size();i++) {
            key = keys.get(i);
            try {
                writer.append(key);
                Data temp_data = data.get(map_to_data.get(key));
                //writer1.append(key);
                

                writer.append(',');
                writer.append(temp_data.name);
                //System.out.println(temp_data.name);
                writer.append(',');
                writer.append(temp_data.mail_id);

                writer.append(',');
                writer.append(Integer.toString(temp_data.marks));

                writer.append(',');
                writer.append(temp_data.teacher);

                writer.append('\n');
                
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            writer.flush();
           // writer1.flush();
            writer.close();
           // writer1.close();
        } catch (IOException e) {
            e.printStackTrace();
        }





    }

	void read_data() throws IOException, FileNotFoundException{
		BufferedReader br = new BufferedReader(new FileReader("res/stud_info.txt"));
        //String line = file_reader.nextLine();
        String[] data_line ;
      	String line;
        while ((line = br.readLine()) != null) {
        		//line = file_reader.nextLine();
                if(line.compareTo("")==0){
                    continue;
                }
        		data_line = line.split(",");
        		Data temp_data = new Data(data_line[1],data_line[2],Integer.parseInt(data_line[3]), data_line[4]);
        		this.data.add(temp_data);
        		map_to_data.put(data_line[0],data.size()-1);
                Semaphore sem_lock = new Semaphore(1);
                sem_locks.add(sem_lock);
                //System.out.println("skj");
      		}
      		
            System.out.println("Successfully read");
           /* for(int i=0;i<data.size();i++){
                System.out.println(data.get(i).name+" "+data.get(i).mail_id);
            }*/
    	/*} 

        catch (IOException e) {
      		System.out.println("Can not Open File Properly.");
    	}*/
	}

	public static void main(String[] args) throws IOException {
        EvaluationSystem evaluation_system = new EvaluationSystem();
        

        // Read the current data from the file.
        evaluation_system.read_data();
        int option;
        while (true) {
            int choice;
            System.out.println("Type following for working-\n");
            System.out.println("0- Exit the system");
            System.out.println("1- Update Marks Record");
            System.out.println("2- Get the Updated Marks file");
            option = scanner.nextInt();
            switch (option) {
                case 0:
                    return;
                case 1:
                    evaluation_system.update_record_buffer();
                    break;
                case 2:
                    evaluation_system.get_updated_file();
                    break;
                default:
                    System.out.println("Try again with valid option.");
                    break;
            }
        }
    }


}