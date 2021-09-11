package edu.cnm.deepdive.animalsandroidv2.model;

import androidx.annotation.NonNull;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.jetbrains.annotations.NotNull;

public class Animal {

  @Expose
  private String title;

  @Expose
  @SerializedName("href")
  private String imageUrl;

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
