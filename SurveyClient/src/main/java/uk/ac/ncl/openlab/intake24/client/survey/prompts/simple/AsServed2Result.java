package uk.ac.ncl.openlab.intake24.client.survey.prompts.simple;

public class AsServed2Result {
    public final String imageUrl;
    public final int imageIndex;
    public final double imageWeight;
    public final double weightFactor;

    AsServed2Result(String imageUrl, int imageIndex, double imageWeight, double weightFactor) {
        this.imageUrl = imageUrl;
        this.imageIndex = imageIndex;
        this.imageWeight = imageWeight;
        this.weightFactor = weightFactor;
    }
}
