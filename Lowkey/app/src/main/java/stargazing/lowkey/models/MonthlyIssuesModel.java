package stargazing.lowkey.models;

public class MonthlyIssuesModel {
    private int MonthNumber;
    private String MonthName;
    private int SolvedIssues;
    private int CreatedIssues;

    public MonthlyIssuesModel(int monthNumber, String monthName, int solvedIssues, int createdIssues) {
        MonthNumber = monthNumber;
        MonthName = monthName;
        SolvedIssues = solvedIssues;
        CreatedIssues = createdIssues;
    }

    public int getMonthNumber() {
        return MonthNumber;
    }

    public void setMonthNumber(int monthNumber) {
        MonthNumber = monthNumber;
    }

    public String getMonthName() {
        return MonthName;
    }

    public void setMonthName(String monthName) {
        MonthName = monthName;
    }

    public int getSolvedIssues() {
        return SolvedIssues;
    }

    public void setSolvedIssues(int solvedIssues) {
        SolvedIssues = solvedIssues;
    }

    public int getCreatedIssues() {
        return CreatedIssues;
    }

    public void setCreatedIssues(int createdIssues) {
        CreatedIssues = createdIssues;
    }
}
