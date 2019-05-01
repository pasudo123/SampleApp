package edu.doubler.app.notification;

import edu.doubler.app.config.BooleanToYNConverter;
import edu.doubler.app.config.TimeEntity;
import edu.doubler.app.user.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="notification")
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@Getter
public class Notification extends TimeEntity implements Serializable {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column(name = "is_read", nullable = false)
    @Convert(converter = BooleanToYNConverter.class)
    private boolean isRead = false;

    @Column(name = "message", nullable = false)
    private String message;

    @Column(name = "noti_target_user", nullable = false)
    private User notificationTargetUser;

    @Column(name = "noti_action_user", nullable = false)
    private User notificationActionUser;

    @Column(name = "noti_url", nullable = false)
    private String notiUrl;

    @Builder
    public Notification(boolean isRead, String message, User notificationTargetUser, User notificationActionUser, String notiUrl){
        this.isRead = isRead;
        this.message = message;
        this.notificationTargetUser = notificationTargetUser;
        this.notificationActionUser = notificationActionUser;
        this.notiUrl = notiUrl;
    }
}
