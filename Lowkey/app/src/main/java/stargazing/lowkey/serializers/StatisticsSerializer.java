package stargazing.lowkey.serializers;

import com.google.gson.Gson;

import org.json.JSONObject;

import stargazing.lowkey.models.StatisticsModel;

public class StatisticsSerializer {

    private StatisticsModel statisticsModel;

    public StatisticsSerializer(JSONObject statisticsModelSerialized) {
        Gson gson = new Gson();
        String statisticsModelStringSerialized = statisticsModelSerialized.toString();
        statisticsModel = gson.fromJson(statisticsModelStringSerialized,
                                StatisticsModel.class);
    }

    public StatisticsModel getStatisticsModel() {
        return statisticsModel;
    }
}
