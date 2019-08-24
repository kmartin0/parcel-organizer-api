package com.km.parceltracker.features.user;

import com.km.parceltracker.util.CacheUtils;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.CrudRepository;

import javax.persistence.QueryHint;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {

	@Override
	@QueryHints(value = {@QueryHint(name = CacheUtils.QUERY_HINT_CACHEABLE_KEY, value = CacheUtils.QUERY_HINT_CACHEABLE_TRUE)})
	List<User> findAll();

	@QueryHints(value = {@QueryHint(name = CacheUtils.QUERY_HINT_CACHEABLE_KEY, value = CacheUtils.QUERY_HINT_CACHEABLE_TRUE)})
	Optional<User> findByEmail(String email);

}
