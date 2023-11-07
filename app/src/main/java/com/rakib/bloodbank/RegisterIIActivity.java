package com.rakib.bloodbank;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class RegisterIIActivity extends AppCompatActivity {


    AutoCompleteTextView division;

    com.google.android.material.textfield.TextInputEditText District, Subdistrict, Address;
    com.google.android.material.button.MaterialButton nextToIII;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_i_i);

        initializeComponents();

        division.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, getResources().getStringArray(R.array.division)));

    }

    private void initializeComponents() {
        District = findViewById(R.id.districtRegister);
        Subdistrict = findViewById(R.id.subDistrictRegister);
        Address = findViewById(R.id.AddressRegister);
        division = findViewById(R.id.divisionDropDrown);
        nextToIII = findViewById(R.id.nextButtonII);
    }

    public void registerIII(View view) {
        String districtT,subdistrictT,addressT,divisionT;
        districtT = District.getText().toString();
        subdistrictT = Subdistrict.getText().toString();
        addressT = Address.getText().toString();
        divisionT = division.getText().toString();

        if(!districtT.isEmpty() && !subdistrictT.isEmpty() && !addressT.isEmpty() && !divisionT.isEmpty() && !divisionT.equalsIgnoreCase("Division")){
            addDataToFirebaseStorage(divisionT,districtT,subdistrictT,addressT,FirebaseAuth.getInstance().getUid());
        }

        if(divisionT.isEmpty() || divisionT.equalsIgnoreCase("division")){
            division.setError("Fill this field.");
        }
        if(districtT.isEmpty()){
            District.setError("Fill this field.");
        }
        if(subdistrictT.isEmpty()){
            Subdistrict.setError("Fill this field.");
        }
        if(addressT.isEmpty()){
            Address.setError("Fill this field.");
        }
        startActivity(new Intent(RegisterIIActivity.this,RegisterIIIActivity.class));
    }

    private void addDataToFirebaseStorage(String divisionT, String districtT, String subDistrictT, String addressT, String uid) {

        HashMap<String,Object> values = new HashMap<>();
        values.put("Division",divisionT);
        values.put("District",districtT);
        values.put("Sub District",subDistrictT);
        values.put("Address",addressT);
        values.put("Step","2");

        FirebaseDatabase.getInstance().getReference("Donors")
                .child(uid).updateChildren(values);
    }


}