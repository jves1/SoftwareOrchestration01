import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DatabaseManager {
   private String jdbcURL;
   private String jdbcUsername;
   private String jdbcPassword;
   private Connection jdbcConnection;

   public DatabaseManager(String jdbcURL, String jdbcUsername, String jdbcPassword) {
      this.jdbcURL = jdbcURL;
      this.jdbcUsername = jdbcUsername;
      this.jdbcPassword = jdbcPassword;
   }

   // Method to establish a connection to the database
   protected void connect() throws SQLException {
      if (jdbcConnection == null || jdbcConnection.isClosed()) {
         try {
            Class.forName("com.mysql.jdbc.Driver");
         } catch (ClassNotFoundException e) {
            throw new SQLException(e);
         }
         jdbcConnection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
      }
   }

   // Method to disconnect from the database
   protected void disconnect() throws SQLException {
      if (jdbcConnection != null && !jdbcConnection.isClosed()) {
         jdbcConnection.close();
      }
   }

   // Method to add a new member
   public boolean insertMember(Member member) throws SQLException {
      String sql = "INSERT INTO Members (FirstName, LastName, MembershipLevel, Status, Email, DuesPaid, DuesPaidDate, RenewalDate) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
      connect();
   
      PreparedStatement statement = jdbcConnection.prepareStatement(sql);
      statement.setString(1, member.getFirstName());
      statement.setString(2, member.getLastName());
      statement.setString(3, member.getMembershipLevel());
      statement.setString(4, member.getStatus());
      statement.setString(5, member.getEmail());
      statement.setDouble(6, member.getDuesPaid());
      statement.setString(7, member.getDuesPaidDate());
      statement.setString(8, member.getRenewalDate());
   
      boolean rowInserted = statement.executeUpdate() > 0;
      statement.close();
      disconnect();
      return rowInserted;
   }

   // Method to update an existing member
   public boolean updateMember(Member member) throws SQLException {
      String sql = "UPDATE Members SET FirstName = ?, LastName = ?, MembershipLevel = ?, Status = ?, Email = ?, DuesPaid = ?, DuesPaidDate = ?, RenewalDate = ? WHERE MemberID = ?";
      connect();
   
      PreparedStatement statement = jdbcConnection.prepareStatement(sql);
      statement.setString(1, member.getFirstName());
      statement.setString(2, member.getLastName());
      statement.setString(3, member.getMembershipLevel());
      statement.setString(4, member.getStatus());
      statement.setString(5, member.getEmail());
      statement.setDouble(6, member.getDuesPaid());
      statement.setString(7, member.getDuesPaidDate());
      statement.setString(8, member.getRenewalDate());
      statement.setInt(9, member.getMemberId());
   
      boolean rowUpdated = statement.executeUpdate() > 0;
      statement.close();
      disconnect();
      return rowUpdated;
   }

   // Method to delete a member
   public boolean deleteMember(Member member) throws SQLException {
      String sql = "DELETE FROM Members WHERE MemberID = ?";
      connect();
   
      PreparedStatement statement = jdbcConnection.prepareStatement(sql);
      statement.setInt(1, member.getMemberId());
   
      boolean rowDeleted = statement.executeUpdate() > 0;
      statement.close();
      disconnect();
      return rowDeleted;
   }

   // Method to get a list of all members
   public List<Member> listAllMembers() throws SQLException {
      List<Member> listMember = new ArrayList<>();
      String sql = "SELECT * FROM Members";
      connect();
   
      PreparedStatement statement = jdbcConnection.prepareStatement(sql);
      ResultSet resultSet = statement.executeQuery();
   
      while (resultSet.next()) {
         int id = resultSet.getInt("MemberID");
         String firstName = resultSet.getString("FirstName");
         String lastName = resultSet.getString("LastName");
         String membershipLevel = resultSet.getString("MembershipLevel");
         String status = resultSet.getString("Status");
         String email = resultSet.getString("Email");
         double duesPaid = resultSet.getDouble("DuesPaid");
         String duesPaidDate = resultSet.getString("DuesPaidDate");
         String renewalDate = resultSet.getString("RenewalDate");
      
         Member member = new Member(id, firstName, lastName, membershipLevel, status, email, duesPaid, duesPaidDate, renewalDate);
         listMember.add(member);
      }
   
      resultSet.close();
      statement.close();
      disconnect();
      return listMember;
   }
   
   
      // Method to get members by their status
   public List<Member> getMembersByStatus(String status) throws SQLException {
      List<Member> members = new ArrayList<>();
      String sql = "SELECT * FROM Members WHERE Status = ?";
   
      connect();
   
      PreparedStatement statement = jdbcConnection.prepareStatement(sql);
      statement.setString(1, status);
   
      ResultSet resultSet = statement.executeQuery();
   
      while (resultSet.next()) {
         members.add(extractMemberFromResultSet(resultSet));
      }
   
      resultSet.close();
      statement.close();
      disconnect();
   
      return members;
   }

   // Method to get members by their membership level
   public List<Member> getMembersByLevel(String level) throws SQLException {
      List<Member> members = new ArrayList<>();
      String sql = "SELECT * FROM Members WHERE MembershipLevel = ?";
   
      connect();
   
      PreparedStatement statement = jdbcConnection.prepareStatement(sql);
      statement.setString(1, level);
   
      ResultSet resultSet = statement.executeQuery();
   
      while (resultSet.next()) {
         members.add(extractMemberFromResultSet(resultSet));
      }
   
      resultSet.close();
      statement.close();
      disconnect();
   
      return members;
   }
   
   public Member getMemberById(int memberId) throws SQLException {
      String sql = "SELECT * FROM Members WHERE MemberID = ?";
      connect();
   
      PreparedStatement statement = jdbcConnection.prepareStatement(sql);
      statement.setInt(1, memberId);
      ResultSet resultSet = statement.executeQuery();
   
      Member member = null;
      if (resultSet.next()) {
         member = extractMemberFromResultSet(resultSet);
      }
   
      resultSet.close();
      statement.close();
      disconnect();
   
      return member;
   }


   // Utility method to extract member data from ResultSet
   private Member extractMemberFromResultSet(ResultSet resultSet) throws SQLException {
      // Assuming you have a constructor in your Member class that takes all these parameters
      return new Member(
         resultSet.getInt("MemberID"),
         resultSet.getString("FirstName"),
         resultSet.getString("LastName"),
         resultSet.getString("MembershipLevel"),
         resultSet.getString("Status"),
         resultSet.getString("Email"),
         resultSet.getDouble("DuesPaid"),
         resultSet.getString("DuesPaidDate"),
         resultSet.getString("RenewalDate")
         );
   }
}
