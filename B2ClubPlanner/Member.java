public class Member {
    private int memberId;
    private String firstName;
    private String lastName;
    private String membershipLevel;
    private String status;
    private String emailAddress;
    private boolean duesPaid;
    private java.sql.Date duesPaidDate;
    private java.sql.Date renewalDate;

    // Constructor
    public Member(int memberId, String firstName, String lastName, String membershipLevel, 
                  String status, String emailAddress, boolean duesPaid, 
                  java.sql.Date duesPaidDate, java.sql.Date renewalDate) {
        this.memberId = memberId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.membershipLevel = membershipLevel;
        this.status = status;
        this.emailAddress = emailAddress;
        this.duesPaid = duesPaid;
        this.duesPaidDate = duesPaidDate;
        this.renewalDate = renewalDate;
    }

    // Getters and Setters
    public int getMemberId() {
        return memberId;
    }

    public void setMemberId(int memberId) {
        this.memberId = memberId;
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

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public boolean isDuesPaid() {
        return duesPaid;
    }

    public void setDuesPaid(boolean duesPaid) {
        this.duesPaid = duesPaid;
    }

    public java.sql.Date getDuesPaidDate() {
        return duesPaidDate;
    }

    public void setDuesPaidDate(java.sql.Date duesPaidDate) {
        this.duesPaidDate = duesPaidDate;
    }

    public java.sql.Date getRenewalDate() {
        return renewalDate;
    }

    public void setRenewalDate(java.sql.Date renewalDate) {
        this.renewalDate = renewalDate;
    }

    @Override
    public String toString() {
        return "Member{" +
                "memberId=" + memberId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", membershipLevel='" + membershipLevel + '\'' +
                ", status='" + status + '\'' +
                ", emailAddress='" + emailAddress + '\'' +
                ", duesPaid=" + duesPaid +
                ", duesPaidDate=" + duesPaidDate +
                ", renewalDate=" + renewalDate +
                '}';
    }
}
