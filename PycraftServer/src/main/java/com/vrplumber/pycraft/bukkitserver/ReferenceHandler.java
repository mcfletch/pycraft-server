package com.vrplumber.pycraft.bukkitserver;

import java.lang.Class;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;
import java.security.InvalidParameterException;
import com.vrplumber.pycraft.bukkitserver.MessageHandler;
import java.util.logging.Logger;

class ReferenceHandler implements MessageHandler {
    /* Allows client to release previously-held references */
    public String getMethod() {
        return "dereference";
    }

    public Map<String, Object> getDescription() {
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("type", "method");
        result.put("static", Boolean.TRUE);
        result.put("name", "dereference");
        result.put("__doc__", "public void dereference(Integer[] references) -> None\nRemoves references to objects on the server to allow cleanup");
        result.put("argcount", (Integer) 1);
        return result;
    }

    public void register(HandlerRegistry registry) {
    }

    public Object handle(PycraftAPI api, PycraftMessage message) {
        Integer[] references = (Integer []) api.expectType(message, 0, Integer[].class);
        if (references != null) {
            for (Integer ref: references) {
                api.releaseReference(ref);
            }
        }
        return (Boolean) true;
   }

}