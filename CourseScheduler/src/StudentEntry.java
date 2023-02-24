/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author kellychau
 */
public class StudentEntry {
    public String studentID;
    public String firstName;
    public String lastName;
    
    public StudentEntry(String studentID, String firstName, String lastName){
        this.studentID = studentID;
        this.firstName = firstName;
        this.lastName = lastName;      
    }
    
    public String getStudentID(){
        return studentID;
    }
    
    public String getFirstName(){
        return firstName;
    }
    
    public String getLastName(){
        return lastName;
    }
}
