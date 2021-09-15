package edu.cnm.deepdive.animalsandroidv2.service;

import android.content.Context;
import edu.cnm.deepdive.animalsandroidv2.model.Animal;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import java.util.List;

public class AnimalRepository {

  private final Context context;

  public AnimalRepository(Context context) {
    this.context = context;
  }

  public Single<List<Animal>> getAll() {
    return WebServiceProxy
        .getInstance()
        .getAnimals()
        .subscribeOn(Schedulers.io());
  }
}
