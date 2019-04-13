package ricky.easybrowser.page.newtab;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import ricky.easybrowser.R;
import ricky.easybrowser.page.webpage.WebPageView;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnTabInteractionListener} interface
 * to handle interaction events.
 * Use the {@link NewTabFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NewTabFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private Button addTabButton;
    private RecyclerView siteGird;
    private FrameLayout frameLayout;

    private SiteAdapter siteAdapter;

    private OnTabInteractionListener mListener;

    public NewTabFragment() {
        // Required empty public constructor
    }

    public static NewTabFragment newInstance() {
        NewTabFragment fragment = new NewTabFragment();
        return fragment;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NewTabFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NewTabFragment newInstance(String param1, String param2) {
        NewTabFragment fragment = new NewTabFragment();
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
        View rootView = inflater.inflate(R.layout.fragment_new_tab, container, false);
        initViews(rootView);
        return rootView;
    }

    private void initViews(View rootView) {
        siteGird = rootView.findViewById(R.id.site_grid);
        siteGird.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));
        siteAdapter = new SiteAdapter(getContext(), SiteAdapter.DEFAULT_PAGE_SIZE);
        siteAdapter.appendDataList(SiteAdapter.getTestDataList());
        siteAdapter.setListener(new SiteAdapter.OnSiteItemClickListener() {
            @Override
            public void onSiteItemClick(SiteEntity siteEntity) {
                /*if (mListener != null) {
                    Uri uri = new Uri.Builder()
                            .scheme("http://")
                            .authority(siteEntity.getSiteUrl())
                            .build();
                    mListener.onTabtInteraction(uri);
                }*/
                Uri uri = new Uri.Builder()
                        .scheme("http://")
                        .authority(siteEntity.getSiteUrl())
                        .build();
                WebPageView webPageView = new WebPageView(getContext());
                frameLayout.addView(webPageView);
                webPageView.loadUrl(uri.getScheme() + uri.getHost());
            }
        });
        siteGird.setAdapter(siteAdapter);
        PagerSnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(siteGird);

        frameLayout = rootView.findViewById(R.id.tab_test_frame);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onTabtInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnTabInteractionListener) {
            mListener = (OnTabInteractionListener) context;
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
    public interface OnTabInteractionListener {
        // TODO: Update argument type and name
        void onTabtInteraction(Uri uri);
    }
}
