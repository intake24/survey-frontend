package uk.ac.ncl.openlab.intake24.client.api.foods;

import com.google.gwt.core.client.GWT;
import org.fusesource.restygwt.client.MethodCallback;
import org.fusesource.restygwt.client.Options;
import org.fusesource.restygwt.client.RestService;
import uk.ac.ncl.openlab.intake24.client.api.auth.AccessDispatcher;

import javax.ws.rs.*;
import java.util.List;

@Options(dispatcher = AccessDispatcher.class, serviceRootKey = "intake24-api")
public interface FoodDataService extends RestService {

    FoodDataService INSTANCE = GWT.create(FoodDataService.class);

    @GET
    @Path("/user/foods/{locale}/{code}")
    void getFoodData(@PathParam("locale") String localeId, @PathParam("code") String foodCode, MethodCallback<FoodData> callback);

    @GET
    @Path("/user/portion-size/as-served/{id}")
    void getAsServedSet(@PathParam("id") String id, MethodCallback<AsServedSet> callback);

    @POST
    @Path("/user/portion-size/as-served")
    void getAsServedSets(List<String> ids, MethodCallback<List<AsServedSet>> callback);

    @GET
    @Path("/user/portion-size/guide-image/{id}")
    void getGuideImage(@PathParam("id") String id, MethodCallback<GuideImage> callback);

    @GET
    @Path("/user/portion-size/image-maps/{id}")
    void getImageMap(@PathParam("id") String id, MethodCallback<SImageMap> callback);

    @GET
    @Path("/user/portion-size/drinkware/{id}")
    void getDrinkwareSet(@PathParam("id") String id, MethodCallback<DrinkwareSet> callback);

    @GET
    @Path("/user/portion-size/weight")
    void getWeightPortionSizeMethod(MethodCallback<PortionSizeMethod> callback);

    @POST
    @Path("/user/portion-size/image-maps")
    void getImageMaps(List<String> ids, MethodCallback<List<SImageMap>> callback);

    @GET
    @Path("/user/categories/{locale}")
    void getRootCategories(@PathParam("locale") String localeId, MethodCallback<List<CategoryHeader>> callback);

    @GET
    @Path("/user/categories/{locale}/{code}")
    void getCategoryContents(@PathParam("locale") String localeId, @PathParam("code") String categoryCode,
                             @QueryParam("alg") String algorithmId, @QueryParam("existing") List<String> existingFoods,
                             MethodCallback<LookupResult> callback);

    @GET
    @Path("/user/foods/associated/{locale}")
    void getAutomaticAssociatedFoods(@PathParam("locale") String localeId, @QueryParam("f") List<String> foodCodes, MethodCallback<AutomaticAssociatedFoods> callback);
}
