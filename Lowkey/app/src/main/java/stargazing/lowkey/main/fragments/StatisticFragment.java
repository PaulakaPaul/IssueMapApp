package stargazing.lowkey.main.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import org.json.JSONObject;

import java.util.List;

import stargazing.lowkey.R;
import stargazing.lowkey.api.wrapper.OnSuccessHandler;
import stargazing.lowkey.managers.StatisticsManager;
import stargazing.lowkey.models.MonthlyIssuesModel;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link StatisticFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link StatisticFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class StatisticFragment extends Fragment {
    private static final int MALE = 0;
    public static final int FEMALE = 1;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";



    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private View rootView;
    private GraphView graphHealthIndex;
    private ProgressBar progressBar;

    private OnFragmentInteractionListener mListener;

    private StatisticsManager statisticsManager = new StatisticsManager();

    public StatisticFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment StatisticFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static StatisticFragment newInstance(String param1, String param2) {
        StatisticFragment fragment = new StatisticFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView =  inflater.inflate(R.layout.fragment_statistic, container, false);

        progressBar = rootView.findViewById(R.id.progressBar);
        graphHealthIndex = rootView.findViewById(R.id.graphHealthIndex);

        switchView(true);
        populateHealthIndexGraph();

        return rootView;
    }

    private void populateHealthIndexGraph() {
        statisticsManager.getStats(new OnSuccessHandler() {
            @Override
            public void handle(JSONObject response) {

                List<MonthlyIssuesModel> monthlyIssuesModels = statisticsManager.getStatisticsModel().getMonthlyData();

                int dataPointsLength = monthlyIssuesModels.size() < 7 ? monthlyIssuesModels.size() : 7;
                DataPoint[] dataPoints = new DataPoint[dataPointsLength];

                for(int i = 0; i < dataPointsLength; i++) {
                    double x = monthlyIssuesModels.get(i).getMonthNumber();
                    double y = (double) monthlyIssuesModels.get(i).getSolvedIssues() /
                            (double) monthlyIssuesModels.get(i).getCreatedIssues();

                    dataPoints[i] = new DataPoint(x, y);
                }

                LineGraphSeries<DataPoint> series = new LineGraphSeries<>(dataPoints);
                graphHealthIndex.addSeries(series);

                switchView(false);
            }
        });
    }

    private void populateGenderPerAgeGraph() {

    }

    private void switchView(boolean isLoading) {
        graphHealthIndex.setVisibility(isLoading ? View.GONE : View.VISIBLE);
        progressBar.setVisibility(isLoading ? View.VISIBLE : View.GONE);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

}
