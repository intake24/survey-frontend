/*
This file is part of Intake24.

© Crown copyright, 2012, 2013, 2014.

This software is licensed under the Open Government Licence 3.0:

http://www.nationalarchives.gov.uk/doc/open-government-licence/
*/

package uk.ac.ncl.openlab.intake24.client.survey;

import com.google.gwt.i18n.client.DateTimeFormat;

import java.util.Date;
import java.util.List;
import java.util.Map;

public class CompletedSurvey {
    public String startTime;
    public String endTime;
    public List<CompletedMeal> meals;
    public List<CompletedMissingFood> missingFoods;
    public List<String> log;
    public Map<String, String> customData;
    public String _up;

    @Deprecated
    public CompletedSurvey() {
    }

    public CompletedSurvey(long startTime, long endTime, List<CompletedMeal> meals, List<CompletedMissingFood> missingFoods, List<String> log, Map<String, String> customData) {
        this.startTime = DateTimeFormat.getFormat(DateTimeFormat.PredefinedFormat.ISO_8601).format(new Date(startTime));
        this.endTime = DateTimeFormat.getFormat(DateTimeFormat.PredefinedFormat.ISO_8601).format(new Date(endTime));
        this.meals = meals;
        this.missingFoods = missingFoods;
        this.log = log;
        this.customData = customData;
        this._up = "";
    }
}