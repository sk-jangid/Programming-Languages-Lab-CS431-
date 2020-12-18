import java.util.ArrayList;

/*
 * Teacher class (Threads)
 * */
public class Teacher extends Thread {
    private EvaluationSystem evaluation_system;  // The main parent running class
    private ArrayList<UpdateBuffer> buffer; // roll num, update marks by
    private Boolean sync;    // indicates if the marks need to be updated synchronously or nor
    private String teacher;
    int priority;
    /*
     * Constructor
     * */
    Teacher(EvaluationSystem evaluation_system, String teacher_name, int priority) {
        this.evaluation_system = evaluation_system;
        teacher = teacher_name;
        setPriority(priority);
        buffer = new ArrayList<UpdateBuffer>();
        sync = false;
    }

    /*
     * While the teacher has input in it's buffer,
     * keep on updating the marks
     * */
    @Override
    public void run() {
        while (buffer.size() > 0) {
            evaluation_system.update_marks(buffer.get(0).roll_nu,buffer.get(0).marks_to_update,teacher);
            buffer.remove(0);
        }
    }

    /*
     * Add a new input to the buffer
     * input contains the details about updating the marks
     * */
    void add_to_thread_buffer(String roll_number, int marks) {
        UpdateBuffer temp = new UpdateBuffer();
        temp.roll_nu = roll_number;
        temp.marks_to_update = marks;
        temp.teacher = teacher;

        buffer.add(temp);
    }
}
