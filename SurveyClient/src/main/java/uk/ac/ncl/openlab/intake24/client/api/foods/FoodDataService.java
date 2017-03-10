package uk.ac.ncl.openlab.intake24.client.api.foods;

import com.google.gwt.core.client.GWT;
import org.fusesource.restygwt.client.MethodCallback;
import org.fusesource.restygwt.client.Options;
import org.fusesource.restygwt.client.RestService;
import uk.ac.ncl.openlab.intake24.client.api.auth.AccessDispatcher;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

@Options(dispatcher = AccessDispatcher.class)
public interface FoodDataService extends RestService {

    FoodDataService INSTANCE = GWT.create(FoodDataService.class);

    @GET
    @Path("/user/foods/{locale}/{code}")
    void getFoodData(@PathParam("locale") String localeId, @PathParam("code") String foodCode, MethodCallback<FoodData> callback);

}
