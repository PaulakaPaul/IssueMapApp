package stargazing.lowkey.managers;

import org.json.JSONObject;

import stargazing.lowkey.api.views.StatisticsView;
import stargazing.lowkey.api.wrapper.OnSuccessHandler;
import stargazing.lowkey.api.wrapper.RequestWrapper;
import stargazing.lowkey.models.StatisticsModel;
import stargazing.lowkey.serializers.StatisticsSerializer;

public class StatisticsManager {

    private StatisticsView statisticsView = new StatisticsView();

    private StatisticsModel statisticsModel;

    public void getStats(final OnSuccessHandler onSuccessHandler) {
        OnSuccessHandler populateStatisticModelHandler = new OnSuccessHandler() {
            @Override
            public void handle(JSONObject response) {
                statisticsModel = getStatisticsModelFromResponse(response);

                if(statisticsModel != null && onSuccessHandler != null) {
                    onSuccessHandler.handle(response);
                } else if (onSuccessHandler != null)
                    onSuccessHandler.handle(RequestWrapper.FAIL_JSON_RESPONSE_VALUE);

            }
        };

        statisticsView.getStats(populateStatisticModelHandler);
    }

    private StatisticsModel getStatisticsModelFromResponse(JSONObject response) {
        StatisticsSerializer statisticsSerializer = new StatisticsSerializer(response);
        return statisticsSerializer.getStatisticsModel();
    }

    public StatisticsModel getStatisticsModel() {
        return statisticsModel;
    }
}
