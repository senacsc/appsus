package sc.senac.mms.appsus.view.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import sc.senac.mms.appsus.Application;
import sc.senac.mms.appsus.R;
import sc.senac.mms.appsus.view.MainActivity;

public class MedicamentosFragment extends Fragment {

    private Application application;
    private MainActivity activity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        this.activity = (MainActivity) getActivity();
        this.application = (Application) this.activity.getApplication();

        return inflater.inflate(R.layout.fragment_medicamentos, container, false);
    }

}
