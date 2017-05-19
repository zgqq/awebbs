package awebbs.module.reply.dao;

import awebbs.module.user.entity.User;
import awebbs.module.reply.entity.Reply;
import awebbs.module.topic.entity.Topic;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by zgqq.
 */
@Repository
@CacheConfig(cacheNames = "replies")
public interface ReplyDao extends JpaRepository<Reply, Integer> {

    List<Reply> findByTopicOrderByUpDownDescDownAscInTimeAsc(Topic topic);

    void deleteByTopic(Topic topic);

    void deleteByUser(User user);

    @Cacheable
    Page<Reply> findByUser(User user, Pageable pageable);
}
