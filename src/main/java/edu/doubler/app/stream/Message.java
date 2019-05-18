package edu.doubler.app.stream;


import lombok.Getter;
import lombok.ToString;
import org.springframework.context.ApplicationEvent;

@Getter
@ToString
public class Message extends ApplicationEvent {

    private final String text;

    public Message(Object source, String text) {
        super(source);
        this.text = text;
    }
}
