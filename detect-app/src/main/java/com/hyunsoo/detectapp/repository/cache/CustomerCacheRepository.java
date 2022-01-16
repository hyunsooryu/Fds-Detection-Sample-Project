package com.hyunsoo.detectapp.repository.cache;

import com.hyunsoo.detectapp.beans.CustomerStatus;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerCacheRepository extends CrudRepository<CustomerStatus, Long> {

}
