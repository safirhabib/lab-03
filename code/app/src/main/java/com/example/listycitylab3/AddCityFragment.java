package com.example.listycitylab3;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.location.GnssAntennaInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
public class AddCityFragment extends DialogFragment {
    interface AddCityDialogListener {

        void addCity(City city);
    }
    private City chosenCity;

    public AddCityFragment(City city){
        this.chosenCity = city;
    }

    public AddCityFragment(){

    }


    private AddCityDialogListener listener;

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);

        // Only notify the activity if editing an existing city
        if (chosenCity != null && getActivity() instanceof MainActivity) {
            ((MainActivity) getActivity()).refreshAdapter();
        }
    }

    @Override
    public void onAttach(@NonNull Context context){
        super.onAttach(context);
        if(context instanceof AddCityDialogListener){
            listener = (AddCityDialogListener) context;
        } else {
            throw new RuntimeException(context+ " must implement AddCityDialogLister");
        }
    }
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState){
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_add_city, null);
        EditText editCityName = view.findViewById(R.id.edit_text_city_text);
        EditText editProvinceName = view.findViewById(R.id.edit_text_province_text);
        if(chosenCity != null){
            editCityName.setText(chosenCity.getName());
            editProvinceName.setText(chosenCity.getProvince());

        }
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        return builder
                .setView(view)
                .setTitle(chosenCity != null ? "Edit City" : "Add City")
                .setNegativeButton("Cancel",null)
                .setPositiveButton(chosenCity != null ? "Edit" : "Add", (dialog,which)->{
                    String cityName = editCityName.getText().toString();
                    String provinceName = editProvinceName.getText().toString();
                    if(chosenCity != null){
                        chosenCity.setName(cityName);
                        chosenCity.setProvince(provinceName);
                        return;
                    }
                    else{
                    listener.addCity(new City(cityName, provinceName));}
                }).create();
    }
}
