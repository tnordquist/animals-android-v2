package edu.cnm.deepdive.animalsandroidv2.controller;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import androidx.appcompat.app.AppCompatActivity;
import com.squareup.picasso.Picasso;
import edu.cnm.deepdive.animalsandroidv2.BuildConfig;
import edu.cnm.deepdive.animalsandroidv2.R;
import edu.cnm.deepdive.animalsandroidv2.model.Animal;
import edu.cnm.deepdive.animalsandroidv2.service.WebServiceProxy;
import java.io.IOException;
import java.util.List;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

  private Spinner animalSelector;
  private ArrayAdapter<Animal> adapter;
  private ImageView image;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    animalSelector = findViewById(R.id.animal_selector);
    image = findViewById(R.id.image);
    animalSelector.setOnItemSelectedListener(new OnItemSelectedListener() {
      @Override
      public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Animal animal = (Animal) parent.getItemAtPosition(position);
        if (animal.getImageUrl() != null) {
          Picasso.get().load(String.format(BuildConfig.CONTENT_FORMAT, animal.getImageUrl()))
              .into((ImageView) findViewById(R.id.image));
        }
      }

      @Override
      public void onNothingSelected(AdapterView<?> parent) {
      }
    });
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
          //noinspection ConstantConditions
          String url = animals.get(0).getImageUrl();
          adapter = new ArrayAdapter<>(MainActivity.this, R.layout.item_animal_spinner, animals);
          adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
          runOnUiThread(() -> {
            if (url != null) {
              Picasso.get().load(String.format(BuildConfig.CONTENT_FORMAT, url))
                  .into((ImageView) findViewById(R.id.image));
            }
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


