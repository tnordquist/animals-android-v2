  package edu.cnm.deepdive.animalsandroidv2.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import edu.cnm.deepdive.animalsandroidv2.BuildConfig;
import edu.cnm.deepdive.animalsandroidv2.model.Animal;
import io.reactivex.Single;
import java.util.List;
import java.util.UUID;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import okhttp3.logging.HttpLoggingInterceptor.Level;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;

  public interface WebServiceProxy {

  @GET("images")
  Single<List<Animal>> getAnimals();

  @GET("images/{id}")
  Single<Animal> getAnimal(@Path("id") UUID id);

  static WebServiceProxy getInstance() {
    return InstanceHolder.INSTANCE;
  }

  class InstanceHolder {

    private static final WebServiceProxy INSTANCE;

    static {
      Gson gson = new GsonBuilder()
          .excludeFieldsWithoutExposeAnnotation()
          .create();
      HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
      interceptor.setLevel(Level.BODY);
      OkHttpClient client = new OkHttpClient.Builder()
          .addInterceptor(interceptor)
          .build();
      Retrofit retrofit = new Retrofit.Builder()
          .baseUrl(BuildConfig.BASE_URL)
          .addConverterFactory(GsonConverterFactory.create(gson))
          .client(client)
          .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
          .build();
      INSTANCE = retrofit.create(WebServiceProxy.class);
    }

  }

}