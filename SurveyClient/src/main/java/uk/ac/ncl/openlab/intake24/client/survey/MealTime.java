package uk.ac.ncl.openlab.intake24.client.survey;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class MealTime {
  @JsonProperty
  public final int hours;
  @JsonProperty
  public final int minutes;

  @JsonCreator
  public MealTime(@JsonProperty("hours") int hours, @JsonProperty("minutes") int minutes) {
    this.hours = hours;
    this.minutes = minutes;
  }
}
