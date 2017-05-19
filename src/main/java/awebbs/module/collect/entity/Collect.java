package awebbs.module.collect.entity;

import awebbs.module.topic.entity.Topic;
import awebbs.module.user.entity.User;
import awebbs.util.Constants;
import awebbs.common.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by zgqq.
 */
@Table(name = "awebbs_collect")
@Entity
public class Collect extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 643806797869767441L;

    @Id
    @GeneratedValue
    private int id;

    //与话题的关联关系
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "topic_id")
    private Topic topic;

    //与用户的关联关系
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "user_id")
    private User user;

    @Column(name = "in_time")
    @JsonFormat(pattern = Constants.DATETIME_FORMAT)
    private Date inTime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Topic getTopic() {
        return topic;
    }

    public void setTopic(Topic topic) {
        this.topic = topic;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getInTime() {
        return inTime;
    }

    public void setInTime(Date inTime) {
        this.inTime = inTime;
    }
}
