package awebbs.module.notification.dao;

import awebbs.module.user.entity.User;
import awebbs.module.notification.entity.Notification;
import awebbs.module.topic.entity.Topic;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by zgqq.
 */
@Repository
@CacheConfig(cacheNames = "notifications")
public interface NotificationDao extends JpaRepository<Notification, Integer> {

    @Cacheable
    Page<Notification> findByTargetUser(User targetUser, Pageable pageable);

    @Cacheable
    Page<Notification> findByTargetUserAndIsRead(User targetUser, boolean isRead, Pageable pageable);

    @Cacheable
    List<Notification> findByTargetUserAndIsRead(User targetUser, boolean isRead);

    @Cacheable
    long countByTargetUserAndIsRead(User targetUser, boolean isRead);

    @Modifying
    @Query("update Notification n set n.isRead = true where n.targetUser = ?1")
    void updateByIsRead(User targetUser);

    void deleteByTargetUser(User user);

    void deleteByUser(User user);

    void deleteByTopic(Topic topic);
}
