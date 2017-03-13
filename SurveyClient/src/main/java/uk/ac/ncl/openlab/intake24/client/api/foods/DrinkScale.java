/*
This file is part of Intake24.

© Crown copyright, 2012, 2013, 2014.

This software is licensed under the Open Government Licence 3.0:

http://www.nationalarchives.gov.uk/doc/open-government-licence/
*/

package uk.ac.ncl.openlab.intake24.client.api.foods;


import java.util.List;

public class DrinkScale {
    public String baseImageUrl;
    public String overlayImageUrl;
    public int objectId;
    public int width;
    public int height;
    public int emptyLevel;
    public int fullLevel;
    public List<VolumeSample> volumeSamples;

    @Deprecated
    public DrinkScale() {
    }

    // choice_id: Int, baseImage: String, overlayImage: String, width: Int, height: Int, emptyLevel: Int, fullLevel: Int
    public DrinkScale(int choice_id, String baseImage, String overlayImage, int width, int height, int emptyLevel, int fullLevel, List<VolumeSample> volumeSamples) {
        this.objectId = choice_id;
        this.baseImageUrl = baseImage;
        this.overlayImageUrl = overlayImage;
        this.width = width;
        this.height = height;
        this.emptyLevel = emptyLevel;
        this.fullLevel = fullLevel;
        this.volumeSamples = volumeSamples;
    }

    private double interp(double fill, double sf0, double sv0, double sf1, double sv1) {
        double a = (fill - sf0) / (sf1 - sf0);
        return sv0 + (sv1 - sv0) * a;
    }

    public double calcVolume(double fillLevel) {
        if (fillLevel < 0.0) return 0.0;

        int i;

        for (i = 0; i < volumeSamples.size(); i++)
            if (volumeSamples.get(i).fl >= fillLevel)
                break;

        if (i == 0)
            return interp(fillLevel, 0.0, 0.0, volumeSamples.get(0).fl, volumeSamples.get(0).v);

        if (i == volumeSamples.size())
            return volumeSamples.get(i - 1).v;

        return interp(fillLevel, volumeSamples.get(i - 1).fl, volumeSamples.get(i - 1).v, volumeSamples.get(i).fl, volumeSamples.get(i).v);
    }
}