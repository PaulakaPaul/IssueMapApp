package stargazing.lowkey.models;

import java.util.List;

public class StatisticsModel {
    private float HealthIndex;
    private List<MonthlyIssuesModel> MonthlyData;
    private List<HumanStats> HumanStats;

    public StatisticsModel(float healthIndex, List<MonthlyIssuesModel> monthlyData, List<stargazing.lowkey.models.HumanStats> humanStats) {
        HealthIndex = healthIndex;
        MonthlyData = monthlyData;
        HumanStats = humanStats;
    }

    public float getHealthIndex() {
        return HealthIndex;
    }

    public void setHealthIndex(float healthIndex) {
        HealthIndex = healthIndex;
    }

    public List<MonthlyIssuesModel> getMonthlyData() {
        return MonthlyData;
    }

    public void setMonthlyData(List<MonthlyIssuesModel> monthlyData) {
        MonthlyData = monthlyData;
    }

    public List<stargazing.lowkey.models.HumanStats> getHumanStats() {
        return HumanStats;
    }

    public void setHumanStats(List<stargazing.lowkey.models.HumanStats> humanStats) {
        HumanStats = humanStats;
    }
}
