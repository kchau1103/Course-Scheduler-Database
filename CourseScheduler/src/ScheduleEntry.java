/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.sql.Timestamp;
/**
 *
 * @author kellychau
 */
public class ScheduleEntry {
    private String semester;
    private String courseCode;
    private String studentID;
    private String status;
    private Timestamp timeStamp;
    
    public ScheduleEntry(String semester, String courseCode, String studentID, String status, Timestamp timeStamp){
        this.semester = semester;
        this.courseCode = courseCode;
        this.studentID = studentID;
        this.status = status;
        this.timeStamp = timeStamp; 
    }
    
    public String getSemester(){
        return semester;
    }
    
    public String getCourseCode(){
        return courseCode;
    }
    
    public String getStudentID(){
        return studentID;
    }
    
    public String getStatus(){
        return status;
    }
    
    public Timestamp getTimeStamp(){
        return timeStamp;
    }
}
