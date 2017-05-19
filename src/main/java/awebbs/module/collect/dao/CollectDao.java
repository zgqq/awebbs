package awebbs.module.collect.dao;

import awebbs.module.user.entity.User;
import awebbs.module.collect.entity.Collect;
import awebbs.module.topic.entity.Topic;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by zgqq.
 */
@Repository
@CacheConfig(cacheNames = "collects")
public interface CollectDao extends JpaRepository<Collect, Integer> {

    @Cacheable
    Page<Collect> findByUser(User user, Pageable pageable);

    @Cacheable
    long countByUser(User user);

    @Cacheable
    long countByTopic(Topic topic);

    @Cacheable
    Collect findByUserAndTopic(User user, Topic topic);

    void deleteByUser(User user);

    void deleteByTopic(Topic topic);
}
