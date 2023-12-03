public class Member {
    // Member attributes
    private int memberId;
    private String firstName;
    private String lastName;
    private String membershipLevel;
    private String status;
    private String email;
    private double duesPaid;
    private String duesPaidDate; // Stored as a String for simplicity. Consider using a Date type in a real application.
    private String renewalDate; // Same as above.

    // Constructor
    public Member(int memberId, String firstName, String lastName, String membershipLevel, String status, String email, double duesPaid, String duesPaidDate, String renewalDate) {
        this.memberId = memberId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.membershipLevel = membershipLevel;
        this.status = status;
        this.email = email;
        this.duesPaid = duesPaid;
        this.duesPaidDate = duesPaidDate;
        this.renewalDate = renewalDate;
    }

    // Getters and setters for each attribute
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public double getDuesPaid() {
        return duesPaid;
    }

    public void setDuesPaid(double duesPaid) {
        this.duesPaid = duesPaid;
    }

    public String getDuesPaidDate() {
        return duesPaidDate;
    }

    public void setDuesPaidDate(String duesPaidDate) {
        this.duesPaidDate = duesPaidDate;
    }

    public String getRenewalDate() {
        return renewalDate;
    }

    public void setRenewalDate(String renewalDate) {
        this.renewalDate = renewalDate;
    }

    // Optional: Override the toString method for easy printing of Member object details
    @Override
    public String toString() {
        return "Member{" +
                "memberId=" + memberId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", membershipLevel='" + membershipLevel + '\'' +
                ", status='" + status + '\'' +
                ", email='" + email + '\'' +
                ", duesPaid=" + duesPaid +
                ", duesPaidDate='" + duesPaidDate + '\'' +
                ", renewalDate='" + renewalDate + '\'' +
                '}';
    }
}
