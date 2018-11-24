package stargazing.lowkey.api.views;

import stargazing.lowkey.api.wrapper.OnSuccessHandler;
import stargazing.lowkey.api.wrapper.RequestItecWrapper;

public class StatisticsView extends RequestItecWrapper{
    private static final String TAG = "Statistics";

    private static final String GET_STATS_URL = "/api/Statistics/GetStats";

    public StatisticsView() {
        super(TAG);
    }

    public void getStats(OnSuccessHandler onSuccessHandler) {
        super.get(GET_STATS_URL, null, null, onSuccessHandler);
    }
}
