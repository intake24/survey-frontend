/*
This file is part of Intake24.

© Crown copyright, 2012, 2013, 2014.

This software is licensed under the Open Government Licence 3.0:

http://www.nationalarchives.gov.uk/doc/open-government-licence/
*/

package uk.ac.ncl.openlab.intake24.client.api;


import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.StatusCodeException;
import uk.ac.ncl.openlab.intake24.client.ui.LoginForm;

public class AsyncRequestAuthHandler {
	public static <T> void execute (final AsyncRequest<T> request, final AsyncCallback<T> resultHandler) {
		request.execute(new AsyncCallback<T>() {
			@Override
			public void onFailure(Throwable caught) {
				final AsyncCallback<T> callback = this; 

				try {
					throw (caught);
				} catch (StatusCodeException e) {
					if (e.getStatusCode() == 401)
					{
						/*LoginForm.showPopup(new Callback1<UserInfo>() {
							@Override
							public void call(UserInfo info) {
								request.execute(callback);
							}
						});*/
					} else {
						resultHandler.onFailure(caught);						
					}
				} catch (Throwable e) {
					resultHandler.onFailure(caught);
				}
			}

			@Override
			public void onSuccess(T result) {
				resultHandler.onSuccess(result);
			}
		});
	}
}
