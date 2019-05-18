package edu.doubler.app.stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@RestController
@RequestMapping("/api/sse-server")
public class StreamController {

    private final Logger logger = LoggerFactory.getLogger(StreamController.class);
    private final StreamService streamService;

    public StreamController(StreamService streamService) {
        this.streamService = streamService;
    }

    @GetMapping(value = "/stream-sse/{id}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter doStream(@PathVariable("id") Long id) {

        logger.info("==========> stream OPEN !!");

        final SseEmitter emitter = new SseEmitter();
        streamService.addEmitter(emitter, id);

        emitter.onCompletion(() -> {
            streamService.removeEmitter(id);
        });

        emitter.onTimeout(() -> {
            streamService.removeEmitter(id);
        });

        return emitter;
    }

    @GetMapping(value = "/stream-sse/close/{id}")
    public SseEmitter doNotStream(@PathVariable("id") Long id){

        logger.info("==========> stream CLOSE !!");
        streamService.removeEmitter(id);

        return null;
    }

    @Async
    @EventListener
    public void handleOnNotification(Message message){
        logger.info("handle Message :: {}", message.toString());
        List<StreamSse> deadEmitters = new ArrayList<>();
        CopyOnWriteArrayList<StreamSse> streamSseList = streamService.getEmitters();

        for(StreamSse streamSse : streamSseList){
            try{
                SseEmitter emitter = streamSse.getEmitter();
                emitter.send(message, MediaType.APPLICATION_JSON_UTF8);
            } catch(Exception e){
                deadEmitters.add(streamSse);
                logger.error("SseEmitter 센드 시 에러 :: {}", e.getMessage());
                continue;
            }
        }

        for(StreamSse dead : deadEmitters){
            streamService.removeEmitter(dead.getId());
        }
    }

}
