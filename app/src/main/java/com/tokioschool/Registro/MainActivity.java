package com.tokioschool.Registro;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.google.android.material.textfield.TextInputLayout;
import com.tokioschool.Registro.databinding.ActivityMainBinding;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private TextInputLayout exposedDropdownLayout;
    private ImageView imagenCamara;
    private TextView linkTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        exposedDropdownLayout=binding.exposedDropdownLayout;
        imagenCamara=binding.imagenCamara;
        linkTextView= binding.condicionesPrivacidad;

        // aqui llamo a la función de abrir la camara al pulsar la imagen de la camara

        imagenCamara.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCamara();
            }
        });

        //aqui si el usuario escribe "@" o "!" le mostrara un texto de error

        binding.inputLayoutNameText.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int count, int after) {

                if (s.toString().contains("@") || s.toString().contains("!")) {
                    binding.inputLayoutNameText.setError(getString(R.string.error));
                } else {
                    binding.inputLayoutNameText.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        binding.inputLayoutLastName.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int count, int after) {

                if (s.toString().contains("@") || s.toString().contains("!")) {
                    binding.inputLayoutLastName.setError(getString(R.string.error));
                } else {
                    binding.inputLayoutLastName.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

         //aqui empieza el exposed Dropdown y si seleccionan menor de 18 les saldra mensaje de error

        List<String> edad = new ArrayList<>();
        edad.add("0-5");
        edad.add("6-11");
        edad.add("12-17");
        edad.add("18-99");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, edad);

        binding.exposedDropdown.setAdapter(adapter);
        binding.exposedDropdown.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem =parent.getItemAtPosition(position).toString();
                if (selectedItem.equals("0-5") ||selectedItem.equals("6-11") ||selectedItem.equals("12-17")) {
                    exposedDropdownLayout.setError(getString(R.string.error2));
                } else {
                    exposedDropdownLayout.setError(null);
                }

            }
        });

        //aqui tenemos el enlace a las politicas de privacidad

        linkTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openUrl("https://developers.google.com/ml-kit/terms");
            }
        });

    }
       //función camara
    private static final int REQUEST_IMAGE_CAPTURE=1;

    private void openCamara(){
        Intent takePictureIntent =new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) !=null) {
            startActivityForResult(takePictureIntent,REQUEST_IMAGE_CAPTURE);
        }
    }
        // función link politicas privacidad
    private void openUrl(String url){
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(browserIntent);
    }
}