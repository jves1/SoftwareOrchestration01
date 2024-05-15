import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class DatabaseUtility {

   private static final String DATABASE_URL = "jdbc:mysql://localhost:3306/ClubMembershipDB1";
   private static final String DATABASE_USER = "root";
   private static final String DATABASE_PASSWORD = "";

   public DatabaseUtility() {
      // Load the JDBC driver
      try {
         Class.forName("com.mysql.cj.jdbc.Driver");
      } catch (ClassNotFoundException e) {
         e.printStackTrace();
      }
   }

   private Connection getConnection() throws SQLException {
      return DriverManager.getConnection(DATABASE_URL, DATABASE_USER, DATABASE_PASSWORD);
   }

   // Method to add a new member to the database
   public void addMember(String firstName, String lastName, String membershipLevel, 
                         String status, String emailAddress, boolean duesPaid, 
                         java.sql.Date duesPaidDate, java.sql.Date renewalDate) {
      String sql = "INSERT INTO members (first_name, last_name, membership_level, status, email_address, dues_paid, dues_paid_date, renewal_date) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
   
      try (Connection conn = getConnection();
          PreparedStatement pstmt = conn.prepareStatement(sql)) {
      
         pstmt.setString(1, firstName);
         pstmt.setString(2, lastName);
         pstmt.setString(3, membershipLevel);
         pstmt.setString(4, status);
         pstmt.setString(5, emailAddress);
         pstmt.setBoolean(6, duesPaid);
         pstmt.setDate(7, duesPaidDate);
         pstmt.setDate(8, renewalDate);
      
         pstmt.executeUpdate();
      } catch (SQLException e) {
         e.printStackTrace();
      }
   }

   // Method to update an existing member
   public void updateMember(int memberId, String firstName, String lastName, 
                            String membershipLevel, String status, String emailAddress, 
                            boolean duesPaid, java.sql.Date duesPaidDate, 
                            java.sql.Date renewalDate) {
      String sql = "UPDATE members SET first_name = ?, last_name = ?, membership_level = ?, status = ?, email_address = ?, dues_paid = ?, dues_paid_date = ?, renewal_date = ? WHERE member_id = ?";
   
      try (Connection conn = getConnection();
          PreparedStatement pstmt = conn.prepareStatement(sql)) {
      
         pstmt.setString(1, firstName);
         pstmt.setString(2, lastName);
         pstmt.setString(3, membershipLevel);
         pstmt.setString(4, status);
         pstmt.setString(5, emailAddress);
         pstmt.setBoolean(6, duesPaid);
         pstmt.setDate(7, duesPaidDate);
         pstmt.setDate(8, renewalDate);
         pstmt.setInt(9, memberId);
      
         pstmt.executeUpdate();
      } catch (SQLException e) {
         e.printStackTrace();
      }
   }

   // Method to delete a member
   public void deleteMember(int memberId) {
      String sql = "DELETE FROM members WHERE member_id = ?";
   
      try (Connection conn = getConnection();
          PreparedStatement pstmt = conn.prepareStatement(sql)) {
      
         pstmt.setInt(1, memberId);
      
         pstmt.executeUpdate();
      } catch (SQLException e) {
         e.printStackTrace();
      }
   }

   // Method to fetch a member by ID
   public Member getMember(int memberId) {
      String sql = "SELECT * FROM members WHERE member_id = ?";
      Member member = null;
   
      try (Connection conn = getConnection();
          PreparedStatement pstmt = conn.prepareStatement(sql)) {
      
         pstmt.setInt(1, memberId);
      
         try (ResultSet rs = pstmt.executeQuery()) {
            if (rs.next()) {
               member = new Member(rs.getInt("member_id"),
                                  rs.getString("first_name"),
                                  rs.getString("last_name"),
                                  rs.getString("membership_level"),
                                  rs.getString("status"),
                                  rs.getString("email_address"),
                                  rs.getBoolean("dues_paid"),
                                  rs.getDate("dues_paid_date"),
                                  rs.getDate("renewal_date"));
            }
         }
      } catch (SQLException e) {
         e.printStackTrace();
      }
      return member;
   }
   
   public boolean checkMemberExists(int memberId) {
      String sql = "SELECT COUNT(*) FROM members WHERE member_id = ?";
      try (Connection conn = getConnection();
      PreparedStatement pstmt = conn.prepareStatement(sql)) {
         pstmt.setInt(1, memberId);
      
         try (ResultSet rs = pstmt.executeQuery()) {
            if (rs.next()) {
               int count = rs.getInt(1);
               return count > 0;
            }
         }
      } catch (SQLException e) {
         e.printStackTrace();
      }
      return false;
   }
   public List<Member> getAllMembers() {
      List<Member> members = new ArrayList<>();
      String sql = "SELECT * FROM members"; // Adjust according to your table structure
   
      try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
      
        // Inside the getAllMembers method in DatabaseUtility
         while (rs.next()) {
            Member member = new Member(
               rs.getInt("member_id"),
               rs.getString("first_name"),
               rs.getString("last_name"),
               // Add the missing parameters as per your Member class constructor
               // For example:
               rs.getString("membership_level"),
               rs.getString("status"),
               rs.getString("email_address"),
               rs.getBoolean("dues_paid"),
               rs.getDate("dues_paid_date"),
               rs.getDate("renewal_date")
               );
            members.add(member);
         }
      } catch (SQLException e) {
         e.printStackTrace();
      }
      return members;
   }

   // Other database operations as needed...
}
