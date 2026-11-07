package me.blackout.assist.events;

import com.ibm.icu.dev.tool.docs.ICUNewTaglet;
import me.blackout.assist.eventBus.Cancellable;

public class SendMessageEvent extends Cancellable {
    public static SendMessageEvent INSTANCE = new SendMessageEvent();
    public String message;

    public static SendMessageEvent get(String message) {
        INSTANCE.reset();
        INSTANCE.message = message;
        return INSTANCE;
    }

    public String getMessage() {
        return message;
    }
}
