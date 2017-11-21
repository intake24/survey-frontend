package uk.ac.ncl.openlab.intake24.client.api.uxevents;

import org.fusesource.restygwt.client.MethodCallback;
import org.fusesource.restygwt.client.Options;
import org.fusesource.restygwt.client.RestService;
import uk.ac.ncl.openlab.intake24.client.api.auth.AccessDispatcher;
import uk.ac.ncl.openlab.intake24.client.api.uxevents.associatedfoods.AutomaticData;
import uk.ac.ncl.openlab.intake24.client.api.uxevents.associatedfoods.ManualConfirmedData;
import uk.ac.ncl.openlab.intake24.client.api.uxevents.associatedfoods.ManualRejectedData;

import javax.ws.rs.POST;
import javax.ws.rs.Path;

@Options(dispatcher = AccessDispatcher.class, serviceRootKey = "intake24-api")
public interface UxEventsService extends RestService {

    @POST
    @Path("/ux/event")
    void postSearchResultsReceived(UxEvent<SearchResult> event, MethodCallback<Void> callback);

    @POST
    @Path("/ux/event")
    void postSearchButtonClicked(UxEvent<SearchButtonData> event, MethodCallback<Void> callback);

    @POST
    @Path("/ux/event")
    void postCantFindButtonClicked(UxEvent<NoData> event, MethodCallback<Void> callback);

    @POST
    @Path("/ux/event")
    void postBrowseAllFoodsButtonClicked(UxEvent<NoData> event, MethodCallback<Void> callback);

    @POST
    @Path("/ux/event")
    void postSearchResultSelected(UxEvent<SearchResultSelectionData> event, MethodCallback<Void> callback);

    @POST
    @Path("/ux/event")
    void postManualAssociatedFoodConfirmed(UxEvent<ManualConfirmedData> event, MethodCallback<Void> callback);

    @POST
    @Path("/ux/event")
    void postManualAssociatedFoodRejected(UxEvent<ManualRejectedData> event, MethodCallback<Void> callback);

    @POST
    @Path("/ux/event")
    void postAutomaticAssociatedFoodsResponse(UxEvent<AutomaticData> event, MethodCallback<Void> callback);


}
