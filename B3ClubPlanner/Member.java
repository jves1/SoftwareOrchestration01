import java.math.BigDecimal;
import java.sql.Date;

public class Member {
   private String memberID;
   private String firstName;
   private String lastName;
   private String membershipLevel;
   private String status;
   private String email;
   private BigDecimal duesPaid;
   private Date duesPaidDate;
   private Date renewalDate;

   // Constructor
   public Member(String memberID, String firstName, String lastName, String membershipLevel, String status, String email, BigDecimal duesPaid, Date duesPaidDate, Date renewalDate) {
      this.memberID = memberID;
      this.firstName = firstName;
      this.lastName = lastName;
      this.membershipLevel = membershipLevel;
      this.status = status;
      this.email = email;
      this.duesPaid = duesPaid;
      this.duesPaidDate = duesPaidDate;
      this.renewalDate = renewalDate;
   }

   // Getters and Setters
   public String getMemberID() {
      return memberID;
   }

   public void setMemberID(String memberID) {
      this.memberID = memberID;
   }

   public String getFirstName() {
      return firstName;
   }

   public void setFirstName(String firstName) {
      this.firstName = firstName;
   }

   public String getLastName() {
      return lastName;
   }

   public void setLastName(String lastName) {
      this.lastName = lastName;
   }

   public String getMembershipLevel() {
      return membershipLevel;
   }

   public void setMembershipLevel(String membershipLevel) {
      this.membershipLevel = membershipLevel;
   }

   public String getStatus() {
      return status;
   }

   public void setStatus(String status) {
      this.status = status;
   }

   public String getEmail() {
      return email;
   }

   public void setEmail(String email) {
      this.email = email;
   }

   public BigDecimal getDuesPaid() {
      return duesPaid;
   }

   public void setDuesPaid(BigDecimal duesPaid) {
      this.duesPaid = duesPaid;
   }

   public Date getDuesPaidDate() {
      return duesPaidDate;
   }

   public void setDuesPaidDate(Date duesPaidDate) {
      this.duesPaidDate = duesPaidDate;
   }

   public Date getRenewalDate() {
      return renewalDate;
   }

   public void setRenewalDate(Date renewalDate) {
      this.renewalDate = renewalDate;
   }
   
   @Override
   public String toString() {
      return "MemberID: " + memberID + ", Name: " + firstName + " " + lastName + ", Email: " + email + 
           ", Membership Level: " + membershipLevel + ", Status: " + status + 
           ", Dues Paid: " + duesPaid + ", Dues Paid Date: " + duesPaidDate + ", Renewal Date: " + renewalDate;
   }

}
