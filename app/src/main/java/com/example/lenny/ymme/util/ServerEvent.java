package com.example.lenny.ymme.util;

import com.example.lenny.ymme.models.YMMEElement;

/**
 * Created by lenny on 5/22/17.
 */

public class ServerEvent {
    private YMMEElement serverResponse;

    public ServerEvent(YMMEElement serverResponse) {
        this.serverResponse = serverResponse;
    }

    public YMMEElement getServerResponse() {
        return serverResponse;
    }

    public void setServerResponse(YMMEElement serverResponse) {
        this.serverResponse = serverResponse;
    }
}
