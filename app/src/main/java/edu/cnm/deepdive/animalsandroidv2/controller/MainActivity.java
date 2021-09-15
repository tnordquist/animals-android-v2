package edu.cnm.deepdive.animalsandroidv2.controller;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.squareup.picasso.Picasso;
import edu.cnm.deepdive.animalsandroidv2.BuildConfig;
import edu.cnm.deepdive.animalsandroidv2.R;
import edu.cnm.deepdive.animalsandroidv2.databinding.ActivityMainBinding;
import edu.cnm.deepdive.animalsandroidv2.model.Animal;
import edu.cnm.deepdive.animalsandroidv2.viewmodel.MainViewModel;
import java.util.List;

public class MainActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
    setContentView(binding.getRoot());
    binding.animalSelector.setOnItemSelectedListener(new OnItemSelectedListener() {
      @Override
      public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Animal animal = (Animal) parent.getItemAtPosition(position);
          Picasso.get().load(String.format(BuildConfig.CONTENT_FORMAT, animal.getImageUrl()))
              .into(binding.image);
      }

      @Override
      public void onNothingSelected(AdapterView<?> parent) {
      }
    });
    setupViewModel(binding);
  }

  private void setupViewModel(ActivityMainBinding binding) {
    MainViewModel viewModel = new ViewModelProvider(this)
        .get(MainViewModel.class);
    getLifecycle().addObserver(viewModel);
    viewModel.getAnimals().observe(this, new Observer<List<Animal>>() {
      @Override
      public void onChanged(List<Animal> animals) {
        ArrayAdapter<Animal> adapter = new ArrayAdapter<>(MainActivity.this,
            R.layout.item_animal_spinner, animals);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        binding.animalSelector.setAdapter(adapter);
      }
    });
    viewModel.getThrowable().observe(this, new Observer<Throwable>() {
      @Override
      public void onChanged(Throwable throwable) {
        Log.e(getClass().getSimpleName(), throwable.getMessage(), throwable);
        //noinspection ConstantConditions
        Snackbar
            .make(binding.getRoot(), throwable.getMessage(),
                BaseTransientBottomBar.LENGTH_INDEFINITE)
            .show();
      }
    });
  }
}

