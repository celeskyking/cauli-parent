package org.cauli.mock.sender;

import org.cauli.mock.response.Response;
import org.cauli.mock.entity.KeyValueStores;

/**
 * @auther sky
 */
public interface ISender {

    public Response sender(String context);

    public Response sender(KeyValueStores keyValueStores);


}
