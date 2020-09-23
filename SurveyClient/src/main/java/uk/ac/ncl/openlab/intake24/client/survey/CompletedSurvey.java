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
    public String timeZone;
    public String uxSessionId;

    public List<CompletedMeal> meals;
    public Map<String, String> customData;

    @Deprecated
    public CompletedSurvey() {
    }

    public CompletedSurvey(long startTime, long endTime, String timeZone, UUID uxSessionId, List<CompletedMeal> meals, Map<String, String> customData) {
        this.startTime = DateTimeFormat.getFormat(DateTimeFormat.PredefinedFormat.ISO_8601).format(new Date(startTime));
        this.endTime = DateTimeFormat.getFormat(DateTimeFormat.PredefinedFormat.ISO_8601).format(new Date(endTime));
        this.timeZone = timeZone;
        this.meals = meals;
        this.uxSessionId = uxSessionId.toString();
        this.customData = customData;
    }
}
