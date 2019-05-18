package edu.doubler.app.stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
@EnableScheduling
public class StreamService implements ApplicationEventPublisherAware {

    private final Logger logger = LoggerFactory.getLogger(StreamService.class);
    private final CopyOnWriteArrayList<StreamSse> streamSseList = new CopyOnWriteArrayList<>();
    private ApplicationEventPublisher publisher = null;

    public StreamService() {}

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.publisher = applicationEventPublisher;
    }

    public void addEmitter(final SseEmitter emitter, final Long id){
        /** open 시 추가 **/
        StreamSse streamSse = StreamSse.builder()
                .id(id)
                .emitter(emitter)
                .build();

        streamSseList.add(streamSse);
    }

    public void removeEmitter(final Long id){
        /** close 시 삭제 **/

        int removeIndex = 0;

        for(int i = 0; i < streamSseList.size(); i++){
            if(id != streamSseList.get(i).getId()){
                continue;
            }

            removeIndex = i;
        }

        streamSseList.remove(removeIndex);
    }

    public CopyOnWriteArrayList<StreamSse> getEmitters(){
        return streamSseList;
    }

    @Scheduled(initialDelay = 2000L, fixedRate = 4000L)
    public void doNotify(){
        logger.info("스케쥴링 :: {}", getNow());

        for(StreamSse streamSse : streamSseList){
            logger.info("메시지 수행");
            Long id = streamSse.getId();
            Message message = new Message(this, (id + " >> " + getNow()));
            publisher.publishEvent(message);
        }
    }

    private String getNow(){
        DateTimeFormatter formatter
                = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        return now.format(formatter);
    }
}
