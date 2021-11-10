package com.example.carbonfootprint;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.Serializable;

public class HomeEnergyReduceEmissions extends AppCompatActivity implements Serializable {
    Spinner spinnerRefridge, spinnerFurnaceBoiler, spinnerWindow;
    Button transportationbutton, wastebutton, homeenergypreviousbutton;
    EditText acThermostatInput, winterThermostatInput, reduceLightingInput, coldWaterInput;
    double acThermostat, winterThermostat, reduceLighting, coldWater, emissionsTotal, emissionsTotalAfterReduce, naturalGas, electricity, fuelOil, propane;
    userInfo currentUser;
    public static final String CURRENT_USER_KEY = "CurrentUserKey";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_energy_reduce_emissions);

        currentUser = (userInfo) getIntent().getSerializableExtra(HomeEnergy.CURRENT_USER_KEY);

        acThermostatInput = (EditText) findViewById(R.id.acThermostatInput);
        winterThermostatInput = (EditText) findViewById(R.id.winterThermostatInput);
        reduceLightingInput = (EditText) findViewById(R.id.reduceLightingInput);
        coldWaterInput = (EditText) findViewById(R.id.coldWaterInput);

        Intent intent = getIntent();

        naturalGas = intent.getDoubleExtra(HomeEnergy.NATURAL, 0);
        electricity = intent.getDoubleExtra(HomeEnergy.ELECTRICITY, 0);
        fuelOil = intent.getDoubleExtra(HomeEnergy.FUELOIL, 0);
        propane = intent.getDoubleExtra(HomeEnergy.PROPANE, 0);


        spinnerRefridge = findViewById(R.id.starEnergyRefrigerator);
        spinnerFurnaceBoiler = findViewById(R.id.starEnergyFurnaceBoiler);
        spinnerWindow = findViewById(R.id.starEnergyWindow);

        transportationbutton = findViewById(R.id.transportationbutton);
        wastebutton = findViewById(R.id.wasteBttn);
        homeenergypreviousbutton = findViewById(R.id.homenergypreviousbutton);

        energyStarRefridge();
        energyStarFurnaceBoiler();
        energyStarWindow();


    }


    public void HomeReducetoHomeEnergy(View view) {
        Intent intent = new Intent(HomeEnergyReduceEmissions.this, HomeEnergy.class);
        intent.putExtra(CURRENT_USER_KEY, currentUser);
        startActivity(intent);
    }

    public void HomeReducetoTransportation(View view) {
        homeEnergyCalculation();
        Intent intent = new Intent(HomeEnergyReduceEmissions.this, Transportation.class);

        intent.putExtra(CURRENT_USER_KEY, emissionsTotalAfterReduce);
        currentUser.setHomeEnergyTotal(emissionsTotalAfterReduce);
        intent.putExtra(CURRENT_USER_KEY, currentUser);
        startActivity(intent);
    }


    public void HomeReducetoWaste(View view) {
        homeEnergyCalculation();
        Intent intent = new Intent(HomeEnergyReduceEmissions.this, Waste.class);
        currentUser.setHomeEnergyTotal(emissionsTotalAfterReduce);
        intent.putExtra(CURRENT_USER_KEY, currentUser);
        startActivity(intent);
    }

    public void HomeReducetoResults(View view) {
        homeEnergyCalculation();
        Intent intent = new Intent(HomeEnergyReduceEmissions.this, ResultsActivity.class);
        currentUser.setHomeEnergyTotal(emissionsTotalAfterReduce);
        intent.putExtra(CURRENT_USER_KEY, currentUser);
        startActivity(intent);
    }



    private void energyStarRefridge() {
        ArrayAdapter<String> refridgeAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.starProducts));
        refridgeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerRefridge.setAdapter(refridgeAdapter);

    }

    private void energyStarFurnaceBoiler() {
        ArrayAdapter<String> furnaceBoilerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.starProducts));
        furnaceBoilerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerRefridge.setAdapter(furnaceBoilerAdapter);
    }

    private void energyStarWindow() {
        ArrayAdapter<String> windowAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.starProducts));
        windowAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerRefridge.setAdapter(windowAdapter);
    }

    private void showToast(String text) {
        Toast.makeText(HomeEnergyReduceEmissions.this, text, Toast.LENGTH_LONG).show();
    }

    private void homeEnergyCalculation() {

        acThermostat = Integer.valueOf(acThermostatInput.getText().toString());
        winterThermostat = Integer.valueOf(winterThermostatInput.getText().toString());
        reduceLighting = Integer.valueOf(reduceLightingInput.getText().toString());
        coldWater = Integer.valueOf(coldWaterInput.getText().toString());

        double acMoneySaved = 0;
        double acCO2Saved = 0;


        for (int i = 1; i <= acThermostat; i++) {

            if (i % 2 == 1) {
                acMoneySaved = acMoneySaved + 4;
                acCO2Saved = acCO2Saved + 20;
            }
            if (i % 2 == 0) {
                acMoneySaved = acMoneySaved + 5;
                acCO2Saved = acCO2Saved + 21;
            }
        }
        acThermostat = acCO2Saved;

        double acWinterMoneySaved = 0;
        double acWinterCO2Saved = 0;

        for (int i = 1; i <= winterThermostat; i++) {

            if (i % 2 == 1) {
                acWinterMoneySaved = acWinterMoneySaved + 7;
                acWinterCO2Saved = acWinterCO2Saved + 84;
            }
            if (i % 2 == 0) {
                acWinterMoneySaved = acWinterMoneySaved + 8;
                acWinterCO2Saved = acWinterCO2Saved + 84;
            }
        }

        winterThermostat = acWinterCO2Saved;

        double lightingMoneySaved = 0;
        double lightingCO2Saved = 0;

        for (int i = 1; i <= reduceLighting; i++) {
            lightingMoneySaved = lightingMoneySaved + 4;
            lightingCO2Saved = lightingCO2Saved + 18;
        }

        reduceLighting = lightingCO2Saved;

        double coldWaterMoneySaved = 0;
        double coldWaterCO2Saved = 0;

        for (int i = 1; i <= coldWater; i++) {
            coldWaterMoneySaved += 6;
            if (i % 2 == 1) {
                coldWaterCO2Saved += 27;
            }
            if (i % 2 == 0) {
                coldWaterCO2Saved += 28;
            }
        }
        coldWater = coldWaterCO2Saved;

        String refridge = spinnerRefridge.getSelectedItem().toString();
        String furnaceBoiler = spinnerFurnaceBoiler.getSelectedItem().toString();
        String window = spinnerWindow.getSelectedItem().toString();
        int energyStarRefridgeCO2 = 0;
        if (refridge.equals("Will Do"))
            energyStarRefridgeCO2 += 177;
        else
            energyStarRefridgeCO2 = 0;

        int energyStarFurnaceBoiler = 0;
        if (furnaceBoiler.equals("Will Do"))
            energyStarFurnaceBoiler += 728;
        else
            energyStarFurnaceBoiler = 0;

        int energyStarWindow = 0;
        if (window.equals("Will Do"))
            energyStarWindow += 2947;
        else
            energyStarWindow = 0;


        emissionsTotal = emissionsTotal + (electricity + fuelOil + propane + naturalGas);

        emissionsTotalAfterReduce = emissionsTotal - (acThermostat + coldWater + reduceLighting + winterThermostat + energyStarRefridgeCO2 + energyStarWindow + energyStarFurnaceBoiler);
        emissionsTotal = emissionsTotal / 2000;
        emissionsTotalAfterReduce = emissionsTotalAfterReduce / 2000;

    }
}


