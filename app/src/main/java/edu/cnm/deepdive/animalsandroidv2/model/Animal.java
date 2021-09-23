package edu.cnm.deepdive.animalsandroidv2.model;

import androidx.annotation.NonNull;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.UUID;

public class Animal {

  @Expose
  private UUID id;

  @Expose
  private String title;

  @Expose
  @SerializedName("href")
  private String imageUrl;

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getImageUrl() {
    return imageUrl;
  }

  public void setImageUrl(String imageUrl) {
    this.imageUrl = imageUrl;
  }

  @NonNull
  @Override
  public String toString() {
    return title;
  }
}
