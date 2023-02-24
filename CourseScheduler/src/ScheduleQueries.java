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
public class ScheduleQueries {
    private static Connection connection;
    private static ArrayList<ScheduleEntry> faculty = new ArrayList<ScheduleEntry>();
    private static PreparedStatement addScheduleEntry;
    private static PreparedStatement getScheduleByStudent;
    private static PreparedStatement getScheduledStudentCount;
    private static PreparedStatement getScheduledStudentsByCourse;
    private static PreparedStatement getWaitlistedStudentsByCourse;
    private static PreparedStatement updateScheduleEntry;
    private static PreparedStatement dropStudentScheduleByCourse;
    private static ResultSet resultSet;
    

    public static void addScheduleEntry(ScheduleEntry schedule){
        connection = DBConnection.getConnection();
        try
        {
            addScheduleEntry = connection.prepareStatement("INSERT INTO APP.SCHEDULEENTRY (SEMESTER,STUDENTID,COURSECODE,STATUS,TIMESTAMP) VALUES (?,?,?,?,?)");
            addScheduleEntry.setString(1,schedule.getSemester());
            addScheduleEntry.setString(2,schedule.getStudentID());
            addScheduleEntry.setString(3,schedule.getCourseCode());
            addScheduleEntry.setString(4,schedule.getStatus());
            addScheduleEntry.setTimestamp(5,schedule.getTimeStamp());
            
            addScheduleEntry.executeUpdate();
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }  
    }
    
    public static ArrayList<ScheduleEntry> getScheduleByStudent(String semester, String studentID){
        connection = DBConnection.getConnection();
        ArrayList<ScheduleEntry> schedule = new ArrayList<ScheduleEntry>();
        try
        {
            getScheduleByStudent = connection.prepareStatement("SELECT COURSECODE, STATUS, TIMESTAMP FROM APP.SCHEDULEENTRY WHERE SEMESTER = ? AND STUDENTID = ? ORDER BY STATUS");
            getScheduleByStudent.setString(1,semester);
            getScheduleByStudent.setString(2,studentID);
            resultSet = getScheduleByStudent.executeQuery();
            
            while(resultSet.next())
            {
                schedule.add(new ScheduleEntry(semester,resultSet.getString("courseCode"), studentID, resultSet.getString("status"),resultSet.getTimestamp("timestamp")));
            }
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        return schedule;
    }
    
    public static int getScheduledStudentCount(String currentSemester, String courseCode){
        connection = DBConnection.getConnection();
        int count = 0;
        
        try
        {
            getScheduledStudentCount = connection.prepareStatement("SELECT COUNT (STUDENTID) FROM APP.SCHEDULEENTRY WHERE SEMESTER = ? AND COURSECODE = ?");
            getScheduledStudentCount.setString(1,currentSemester);
            getScheduledStudentCount.setString(2,courseCode);
            resultSet = getScheduledStudentCount.executeQuery(); 
           
            while(resultSet.next()){
                count = resultSet.getInt(1);         
            }   
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();   
        }
        return count;       
    }
    
    public static ArrayList<ScheduleEntry> getScheduledStudentsByCourse(String semester, String courseCode){
        connection = DBConnection.getConnection();
        ArrayList<ScheduleEntry> schedule = new ArrayList<ScheduleEntry>();
        
        try{
            getScheduledStudentsByCourse = connection.prepareStatement("SELECT STUDENTID, TIMESTAMP FROM APP.SCHEDULEENTRY WHERE SEMESTER = ? AND COURSECODE = ? AND STATUS = ?");
            getScheduledStudentsByCourse.setString(1,semester);
            getScheduledStudentsByCourse.setString(2,courseCode);
            getScheduledStudentsByCourse.setString(3,"S");
            resultSet = getScheduledStudentsByCourse.executeQuery();
        
            while(resultSet.next())
            {
                    schedule.add(new ScheduleEntry(semester,courseCode, resultSet.getString(1), "S", resultSet.getTimestamp(2)));
                }
            }
            catch(SQLException sqlException)
            {
                sqlException.printStackTrace();
            }
            return schedule;
    }
    
    public static ArrayList<ScheduleEntry> getWaitlistedStudentsByCourse(String semester, String courseCode){
        connection = DBConnection.getConnection();
        ArrayList<ScheduleEntry> schedule = new ArrayList<ScheduleEntry>();
        
        try{
            getWaitlistedStudentsByCourse = connection.prepareStatement("SELECT STUDENTID, TIMESTAMP FROM APP.SCHEDULEENTRY WHERE SEMESTER = ? AND COURSECODE = ? AND STATUS = ?");
            getWaitlistedStudentsByCourse.setString(1,semester);
            getWaitlistedStudentsByCourse.setString(2,courseCode);
            getWaitlistedStudentsByCourse.setString(3,"W");
            resultSet = getWaitlistedStudentsByCourse.executeQuery(); 

            while(resultSet.next())
                {
                    schedule.add(new ScheduleEntry(semester,courseCode, resultSet.getString(1), "W",resultSet.getTimestamp(2)));
                }
            }
            catch(SQLException sqlException)
            {
                sqlException.printStackTrace();
            }
        return schedule;
    }
    
    public static void dropStudentScheduleByCourse(String semester, String studentID, String courseCode){
        connection = DBConnection.getConnection();
        try{
            dropStudentScheduleByCourse = connection.prepareStatement("DELETE FROM APP.SCHEDULEENTRY WHERE SEMESTER = ? AND STUDENTID = ? AND COURSECODE = ?");
            dropStudentScheduleByCourse.setString(1,semester);
            dropStudentScheduleByCourse.setString(2,studentID);
            dropStudentScheduleByCourse.setString(3,courseCode);
            
            dropStudentScheduleByCourse.executeUpdate();
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
    }
    
    public static void dropScheduleByCourse(String semester, String courseCode){
        connection = DBConnection.getConnection();
        try{
            dropStudentScheduleByCourse = connection.prepareStatement("DELETE FROM APP.SCHEDULEENTRY WHERE SEMESTER = ? AND COURSECODE = ?");
            dropStudentScheduleByCourse.setString(1,semester);
            dropStudentScheduleByCourse.setString(2,courseCode);
            
            dropStudentScheduleByCourse.executeUpdate();
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
    }
    
    public static void updateScheduleEntry(String semester, ScheduleEntry entry){
        connection = DBConnection.getConnection();
        try{
            updateScheduleEntry = connection.prepareStatement("UPDATE APP.SCHEDULEENTRY SET STATUS = ? WHERE SEMESTER = ? AND COURSECODE = ? AND STUDENTID = ?");
            updateScheduleEntry.setString(1,"S");
            updateScheduleEntry.setString(2,entry.getSemester());
            updateScheduleEntry.setString(3,entry.getCourseCode());
            updateScheduleEntry.setString(4,entry.getStudentID());
            updateScheduleEntry.executeUpdate(); 
        }
        catch(SQLException sqlException)
            {
             sqlException.printStackTrace();
            }
    }
}
