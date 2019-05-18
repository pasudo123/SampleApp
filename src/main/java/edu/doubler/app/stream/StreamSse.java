package edu.doubler.app.stream;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StreamSse {

    private SseEmitter emitter;
    private Long id;

    @Builder
    public StreamSse(SseEmitter emitter, Long id){
        this.emitter = emitter;
        this.id = id;
    }
}
