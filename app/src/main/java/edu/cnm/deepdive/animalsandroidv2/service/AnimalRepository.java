package edu.cnm.deepdive.animalsandroidv2.service;

import android.content.Context;
import edu.cnm.deepdive.animalsandroidv2.model.Animal;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import java.util.List;
import java.util.UUID;

public class AnimalRepository {

  private final Context context;
  private final WebServiceProxy serviceProxy;

  public AnimalRepository(Context context) {
    this.context = context;
    serviceProxy = WebServiceProxy.getInstance();
  }

  public Single<Animal> getAnimal(UUID id) {
    return serviceProxy.
        getAnimal(id)
        .subscribeOn(Schedulers.io());
  }

  public Single<List<Animal>> getAll() {
    return serviceProxy
        .getAnimals()
        .subscribeOn(Schedulers.io());
  }


}
