package org.cauli.mock.response;

import jodd.http.HttpResponse;

/**
 * @auther sky
 */
public class HttpResponseTransfer implements ResponseTransfer<HttpResponse> {
    @Override
    public Response transfer(HttpResponse httpResponse) {
        Response response = new Response();
        response.setBody(httpResponse.bodyText());
        return response;
    }
}
