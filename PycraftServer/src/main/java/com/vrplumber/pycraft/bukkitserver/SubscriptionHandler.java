package com.vrplumber.pycraft.bukkitserver;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.Class;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;
import java.security.InvalidParameterException;
import com.vrplumber.pycraft.bukkitserver.MessageHandler;
import java.util.logging.Logger;

class SubscriptionHandler implements MessageHandler {
    /* Handle a method by dispatching to a method on a class instance */
    public String getMethod() {
        return "subscribe";
    }

    public Map<String, Object> getDescription() {
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("type", "method");
        result.put("static", Boolean.TRUE);
        result.put("name", "subscribe");
        result.put("__doc__", "public void subscribe(String EventTypeName, Boolean subscribe) -> Boolean");
        result.put("argcount", (Integer) 0);

        return result;
    }

    public void register(HandlerRegistry registry) {
    }

    public Object handle(PycraftAPI api, PycraftMessage message) {
        // TODO: should hold a lock for this whole operation as we're
        // mutating a structure
        Logger log = api.getLogger();
        String name = api.expectString(message, 0);
        Boolean enabled = api.expectBoolean(message, 1);
        if (enabled) {
            log.info(String.format("Subscribing for events %s for message id %d", name, message.messageId));
            List<PycraftMessage> handlers = api.subscriptions.get(name);
            if (handlers == null) {
                log.info(String.format("  Creating handler list for %s", name));
                handlers = new ArrayList<PycraftMessage>();
                api.subscriptions.put(name, handlers);
            }
            log.info(String.format("  Added to handler list %s", name));
            handlers.add(message);
            return (Integer) message.messageId;
        } else {
            Integer messageId = api.expectInteger(message, 2);
            log.info(String.format("Unsubscribing for events %s for message id %d", name, messageId));
            List<PycraftMessage> handlers = api.subscriptions.get(name);
            if (handlers == null) {
                log.info(String.format("  No handlers were registered for %s", name));
                return 0;
            }
            Boolean found = Boolean.FALSE;
            for (int index = handlers.size() - 1; index > -1; index--) {
                if (handlers.get(index).messageId == messageId) {
                    log.info(String.format("  Removed handler from %s", name));
                    handlers.remove(index);
                    found = true;
                }
            }
            return (Integer) (found ? messageId : 0);
        }
    }

}