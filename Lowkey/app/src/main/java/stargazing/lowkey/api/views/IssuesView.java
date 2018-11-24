package stargazing.lowkey.api.views;

import stargazing.lowkey.api.wrapper.RequestItecWrapper;

public class IssuesView extends RequestItecWrapper {
    private static final String TAG = "Issues";

    private static final String CREATE_URL = "/api/Issue/Create";
    private static final String UPDATE_URL = "/api/Issue/Update";
    private static final String VOTE_URL = "/api/Issue/Vote";
    private static final String DELETE_ID_PICTURE = ""; //TODO: with id

    private static final String GET_ALL_URL = "/api/Issue/GetAll";
    private static final String GET_ID_URL = ""; //TODO: with id


    public IssuesView() {
        super(TAG);
    }

    public void getAll() {
        super.get(GET_ALL_URL, null, null, null);
    }
}
