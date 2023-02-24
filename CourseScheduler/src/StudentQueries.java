/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
/**
 *
 * @author kellychau
 */
public class StudentQueries {
    private static Connection connection;
    private static ArrayList<StudentEntry> faculty = new ArrayList<StudentEntry>();
    private static PreparedStatement addStudent;
    private static PreparedStatement getAllStudents;
    private static PreparedStatement getStudent;
    private static PreparedStatement dropStudent;
    private static ResultSet resultSet;
    
    public static void addStudent(StudentEntry name)
    {
        connection = DBConnection.getConnection();
        try
        {
            addStudent = connection.prepareStatement("INSERT INTO APP.STUDENTENTRY (STUDENTID, FIRSTNAME, LASTNAME) VALUES (?,?,?)");
            addStudent.setString(1,name.getStudentID());
            addStudent.setString(2,name.getFirstName());
            addStudent.setString(3, name.getLastName());
            
            addStudent.executeUpdate();
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }  
    }
    
    public static ArrayList<StudentEntry> getAllStudents()
    {
        connection = DBConnection.getConnection();
        ArrayList<StudentEntry> student = new ArrayList<StudentEntry>();
        try
        {
            getAllStudents = connection.prepareStatement("SELECT * FROM APP.STUDENTENTRY ORDER BY LASTNAME, FIRSTNAME");
            resultSet = getAllStudents.executeQuery();
            
            while(resultSet.next())
            {
                student.add(new StudentEntry(resultSet.getString("STUDENTID"),resultSet.getString("FIRSTNAME"),resultSet.getString("LASTNAME")));
            }
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        return student;
        
    }
    
    public static StudentEntry getStudent(String studentID){
        connection = DBConnection.getConnection();
        StudentEntry student = null;
        try{
            getStudent = connection.prepareStatement("SELECT * FROM APP.STUDENTENTRY WHERE STUDENTID = ?");
            getStudent.setString(1,studentID);
            resultSet = getStudent.executeQuery();
            
            while(resultSet.next()){
                student = new StudentEntry(studentID,resultSet.getString("FIRSTNAME"),resultSet.getString("LASTNAME"));
            }
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        return student;
    }
    
    public static void dropStudent(String studentID){
        connection = DBConnection.getConnection();
        try {
            dropStudent = connection.prepareStatement("DELETE FROM APP.STUDENTENTRY WHERE STUDENTID = ?");
            dropStudent.setString(1,studentID);
            
            dropStudent.executeUpdate();
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
    }
    
}
