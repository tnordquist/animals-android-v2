package edu.cnm.deepdive.animalsandroidv2.controller;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import androidx.appcompat.app.AppCompatActivity;
import edu.cnm.deepdive.animalsandroidv2.R;
import edu.cnm.deepdive.animalsandroidv2.model.Animal;
import edu.cnm.deepdive.animalsandroidv2.service.WebServiceProxy;
import java.io.IOException;
import java.util.List;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

  private Spinner animalSelector;
  private ArrayAdapter<Animal> adapter;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    animalSelector = findViewById(R.id.animal_selector);
    new Retriever().start();
  }

  private class Retriever extends Thread {

    @Override
    public void run() {
      try {
        Response<List<Animal>> response = WebServiceProxy.getInstance()
            .getAnimals()
            .execute();
        if (response.isSuccessful()) {
          Log.d(getClass().getName(), response.body().toString());
          List<Animal> animals = response.body();
          runOnUiThread(() -> {
            adapter = new ArrayAdapter<>(
                MainActivity.this, R.layout.item_animal_spinner, animals);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
            animalSelector.setAdapter(adapter);
          });
        } else {
          Log.e(getClass().getName(), response.message());
        }
      } catch (IOException e) {
        Log.e(getClass().getName(), e.getMessage(), e);
      }
    }
  }
}

