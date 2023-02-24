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

/**
 *
 * @author kellychau
 */
public class CourseQueries {
    private static Connection connection;
    //private static ArrayList<CourseEntry> faculty = new ArrayList<CourseEntry>();
    private static PreparedStatement addCourse;
    private static PreparedStatement getAllCourses;
    private static PreparedStatement getAllCourseCodes;
    private static PreparedStatement getCourseSeats;
    private static PreparedStatement dropCourse;
    private static ResultSet resultSet;
    
    public static ArrayList<CourseEntry> getAllCourses(String semester){
        connection = DBConnection.getConnection();
        ArrayList<CourseEntry> course = new ArrayList<CourseEntry>();
        
        try
        {
            getAllCourses = connection.prepareStatement("SELECT * FROM APP.COURSEENTRY WHERE SEMESTER = ? ORDER BY COURSECODE");
            getAllCourses.setString(1,semester);
            resultSet = getAllCourses.executeQuery();
            
            while(resultSet.next())
            {
                course.add(new CourseEntry(semester,resultSet.getString("coursecode"),resultSet.getString("description"),resultSet.getInt("seats")));
            }
        }
        
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        return course;
    }
    
    public static void addCourse(CourseEntry course)
    {
        connection = DBConnection.getConnection();
        try
        {
            addCourse = connection.prepareStatement("INSERT INTO APP.COURSEENTRY (SEMESTER, COURSECODE, DESCRIPTION, SEATS) VALUES (?,?,?,?)");
            addCourse.setString(1,course.getSemester());
            addCourse.setString(2,course.getCourseCode());
            addCourse.setString(3, course.getCourseDescription());
            addCourse.setInt(4, course.getSeats());
            
            addCourse.executeUpdate();
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }  
    }
    
    public static ArrayList<String> getAllCourseCodes(String semester){
        connection = DBConnection.getConnection();
        ArrayList<String> courseCode = new ArrayList<String>();
        try
        {
            getAllCourseCodes = connection.prepareStatement("SELECT * FROM APP.COURSEENTRY WHERE SEMESTER = ? ORDER BY COURSECODE");
            getAllCourseCodes.setString(1,semester);
            resultSet = getAllCourseCodes.executeQuery();
            
            while(resultSet.next())
            {
                courseCode.add(resultSet.getString("coursecode"));
            }
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        return courseCode;
    }
    
    public static int getCourseSeats(String semester, String courseCode){
        connection = DBConnection.getConnection();
        
        int seats = 0;
        
        try
        {
            getCourseSeats = connection.prepareStatement("SELECT * FROM APP.COURSEENTRY WHERE SEMESTER = ? AND COURSECODE = ?");
            getCourseSeats.setString(1,semester);
            getCourseSeats.setString(2,courseCode);
            resultSet = getCourseSeats.executeQuery();
            
            while(resultSet.next())
            {
                seats = resultSet.getInt("seats");
            }
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        return seats;
    }
    
    public static void dropCourse(String semester, String courseCode){
        connection = DBConnection.getConnection();
        try{
            dropCourse = connection.prepareStatement("DELETE FROM APP.COURSEENTRY WHERE SEMESTER = ? AND COURSECODE = ?");
            dropCourse.setString(1,semester);
            dropCourse.setString(2,courseCode);
            dropCourse.executeUpdate();
            
            dropCourse = connection.prepareStatement("DELETE FROM APP.SCHEDULEENTRY WHERE SEMESTER = ? AND COURSECODE = ?");
            dropCourse.setString(1,semester);
            dropCourse.setString(2,courseCode);
            dropCourse.executeUpdate();
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
    }
    
}
