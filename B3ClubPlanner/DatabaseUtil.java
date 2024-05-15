import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DatabaseUtil {

   private static final String URL = "jdbc:mysql://localhost:3306/ClubMembershipDB";
   private static final String USERNAME = "root"; // replace with your DB username
   private static final String PASSWORD = ""; // replace with your DB password

   // Establishes database connection
   private Connection connect() throws SQLException {
      return DriverManager.getConnection(URL, USERNAME, PASSWORD);
   }

   // Adds a new member to the database
   public boolean addMember(Member member) {
      String sql = "INSERT INTO Members (memberID, firstName, lastName, membershipLevel, status, email, duesPaid, duesPaidDate, renewalDate) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
      try (Connection conn = connect();
          PreparedStatement pstmt = conn.prepareStatement(sql)) {
         pstmt.setString(1, member.getMemberID());
         pstmt.setString(2, member.getFirstName());
         pstmt.setString(3, member.getLastName());
         pstmt.setString(4, member.getMembershipLevel());
         pstmt.setString(5, member.getStatus());
         pstmt.setString(6, member.getEmail());
         pstmt.setBigDecimal(7, member.getDuesPaid());
         pstmt.setDate(8, member.getDuesPaidDate());
         pstmt.setDate(9, member.getRenewalDate());
         pstmt.executeUpdate();
         return true;
      } catch (SQLException e) {
         e.printStackTrace();
         return false;
      }
   }

   // Retrieves a member from the database
   public Member getMember(String memberID) {
      String sql = "SELECT * FROM Members WHERE memberID = ?";
      try (Connection conn = connect();
          PreparedStatement pstmt = conn.prepareStatement(sql)) {
         pstmt.setString(1, memberID);
         ResultSet rs = pstmt.executeQuery();
         if (rs.next()) {
            return new Member(
               rs.getString("memberID"),
               rs.getString("firstName"),
               rs.getString("lastName"),
               rs.getString("membershipLevel"),
               rs.getString("status"),
               rs.getString("email"),
               rs.getBigDecimal("duesPaid"),
               rs.getDate("duesPaidDate"),
               rs.getDate("renewalDate")
               );
         }
      } catch (SQLException e) {
         e.printStackTrace();
      }
      return null;
   }

   // Updates a member's information in the database
   public boolean updateMember(Member member) {
      String sql = "UPDATE Members SET firstName = ?, lastName = ?, membershipLevel = ?, status = ?, email = ?, duesPaid = ?, duesPaidDate = ?, renewalDate = ? WHERE memberID = ?";
      try (Connection conn = connect();
          PreparedStatement pstmt = conn.prepareStatement(sql)) {
         pstmt.setString(1, member.getFirstName());
         pstmt.setString(2, member.getLastName());
         pstmt.setString(3, member.getMembershipLevel());
         pstmt.setString(4, member.getStatus());
         pstmt.setString(5, member.getEmail());
         pstmt.setBigDecimal(6, member.getDuesPaid());
         pstmt.setDate(7, member.getDuesPaidDate());
         pstmt.setDate(8, member.getRenewalDate());
         pstmt.setString(9, member.getMemberID());
         pstmt.executeUpdate();
         return true;
      } catch (SQLException e) {
         e.printStackTrace();
         return false;
      }
   }

   // Deletes a member from the database
   public boolean deleteMember(String memberID) {
      String sql = "DELETE FROM Members WHERE memberID = ?";
      try (Connection conn = connect();
          PreparedStatement pstmt = conn.prepareStatement(sql)) {
         pstmt.setString(1, memberID);
         pstmt.executeUpdate();
         return true;
      } catch (SQLException e) {
         e.printStackTrace();
         return false;
      }
   }

   // Generates a report based on the specified criteria
   public List<Member> generateReport(String reportType) {
      List<Member> members = new ArrayList<>();
      String sql = "";
      switch (reportType) {
         case "All Members":
            sql = "SELECT * FROM Members";
            break;
         case "Active Members":
            sql = "SELECT * FROM Members WHERE status = 'Active'";
            break;
         case "Inactive Members":
            sql = "SELECT * FROM Members WHERE status = 'Inactive'";
            break;
         case "Level 1":
            sql = "SELECT * FROM Members WHERE membershipLevel = 'Level1'";
            break;
         case "Level 2":
            sql = "SELECT * FROM Members WHERE membershipLevel = 'Level2'";
            break;
         case "Level 3":
            sql = "SELECT * FROM Members WHERE membershipLevel = 'Level3'";
            break;
         default:
            return members; // Empty list for invalid report types
      }
   
      try (Connection conn = connect();
          PreparedStatement pstmt = conn.prepareStatement(sql)) {
         ResultSet rs = pstmt.executeQuery();
         while (rs.next()) {
            members.add(new Member(
               rs.getString("memberID"),
               rs.getString("firstName"),
               rs.getString("lastName"),
               rs.getString("membershipLevel"),
               rs.getString("status"),
               rs.getString("email"),
               rs.getBigDecimal("duesPaid"),
               rs.getDate("duesPaidDate"),
               rs.getDate("renewalDate")
               ));
         }
      } catch (SQLException e) {
         e.printStackTrace();
      }
      return members;
   }
   
   public int getLastMemberID() {
   // Method to query the database and find the highest member ID
      String sql = "SELECT MAX(memberID) FROM Members";
      try (Connection conn = connect();
      PreparedStatement pstmt = conn.prepareStatement(sql);
      ResultSet rs = pstmt.executeQuery()) {
         if (rs.next()) {
            return rs.getInt(1); // Return the highest member ID
         }
      } catch (SQLException e) {
         e.printStackTrace();
      }
      return -1; // Return -1 if the table is empty or in case of an error
   }

}
