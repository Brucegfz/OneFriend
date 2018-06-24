package com.zephyrs.android.onefriend;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.Button;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link HomePage.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link HomePage#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomePage extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    Button yes;
    Button no;
    Button notsure;
    Button question;
    Button report;
    Button reportbutton;
    Animation[] animations = new Animation[4];
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    Button exercisepage;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public HomePage() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NotificationFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomePage newInstance(String param1, String param2) {
        HomePage fragment = new HomePage();
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
        View view = inflater.inflate(R.layout.fragment_notification, container, false);

        AlphaAnimation animationview = new AlphaAnimation(0f, 1f);
        animationview.setDuration(500);
        view.setAnimation(animationview);
        ScaleAnimation scaleAnimation1 = new ScaleAnimation(0.5f, 1, 0.5f, 1f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        scaleAnimation1.setDuration(500);
        ScaleAnimation scaleAnimation2 = new ScaleAnimation(0.5f, 1, 0.5f, 1f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        scaleAnimation2.setDuration(500);

        reportbutton = (Button) view.findViewById(R.id.reportselect);
        yes = (Button) view.findViewById(R.id.yes);
        no = (Button) view.findViewById(R.id.no);
        notsure = (Button) view.findViewById(R.id.notsure);
        question = (Button) view.findViewById(R.id.buttonquestion);
        yes.startAnimation(scaleAnimation1);
        no.startAnimation(scaleAnimation1);
        notsure.startAnimation(scaleAnimation2);
        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent testintent = new Intent(getActivity(),StressLevel.class);
                testintent.putExtra("pagechange","bottom");
                startActivity(testintent);
//                getActivity().finish();
                getActivity().overridePendingTransition(R.anim.fade, R.anim.hold);
            }
        });

        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent testintent = new Intent(getActivity(),FactPage.class);
                testintent.putExtra("pagechange","bottom");
                startActivity(testintent);
//                getActivity().finish();
                getActivity().overridePendingTransition(R.anim.fade, R.anim.hold);
            }
        });

        notsure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent testintent = new Intent(getActivity(),StressTest.class);
                testintent.putExtra("pagechange","bottom");
                startActivity(testintent);
//                getActivity().finish();
                getActivity().overridePendingTransition(R.anim.fade, R.anim.hold);
            }
        });

        question.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent testintent = new Intent(getActivity(),FactPage.class);
                testintent.putExtra("pagechange","bottom");
                startActivity(testintent);
//                getActivity().finish();
                getActivity().overridePendingTransition(R.anim.fade, R.anim.hold);
            }
        });

        report = (Button) view.findViewById(R.id.report_page);
        report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent testintent = new Intent(getActivity(),ReportPage.class);
                testintent.putExtra("pagechange","bottom");
                startActivity(testintent);
//                getActivity().finish();
                getActivity().overridePendingTransition(R.anim.fade, R.anim.hold);
            }
        });

        reportbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent testintent = new Intent(getActivity(),ReportPage.class);
                testintent.putExtra("pagechange","bottom");
                startActivity(testintent);
//                getActivity().finish();
                getActivity().overridePendingTransition(R.anim.fade, R.anim.hold);
            }
        });
        return view;
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
