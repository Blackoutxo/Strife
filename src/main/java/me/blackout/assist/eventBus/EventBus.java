package me.blackout.assist.eventBus;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class EventBus {
    private record Listener(Object owner, Method method, int priority) {}                 // Store @EventHandler calls
    private final Map<Class<?>, List<Listener>> listeners = new ConcurrentHashMap<>();    // Map them


    /**
     * Add event calls
     * */
    public void subscribe(Object owner) {
        for (Method m : owner.getClass().getDeclaredMethods()) {
            EventHandler annotation = m.getAnnotation(EventHandler.class);
            if (annotation == null) continue;

            if (m.getParameterCount() != 1) {
                throw new IllegalArgumentException(m + " must have exactly one parameter");
            }

            m.setAccessible(true);
            Class<?> eventType = m.getParameterTypes()[0];

            List<Listener> list = listeners.computeIfAbsent(eventType, k -> new CopyOnWriteArrayList<>());
            list.add(new Listener(owner, m, annotation.priority()));
            list.sort(Comparator.comparingInt(Listener::priority).reversed());
        }
    }

    /**
     * Remove event calls
     * */
    public void unsubscribe(Object owner) {
        for (List<Listener> list : listeners.values()) {
            list.removeIf(listener -> listener.owner() == owner);
        }
    }

    public <T> T post(T event) {
        List<Listener> list = listeners.get(event.getClass());

        if (list == null) return event;

        for (Listener l : list) {
            try {
                l.method.invoke(l.owner, event);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            if (event instanceof Cancellable c && c.isCancelled()) break;
        }

        return event;
    }
}
