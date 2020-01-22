package com.km.parcelorganizer.features.parcel;

import com.km.parcelorganizer.features.user.User;
import com.km.parcelorganizer.util.CacheUtils;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.CrudRepository;

import javax.persistence.QueryHint;
import java.util.List;

public interface ParcelRepository extends CrudRepository<Parcel, Long> {

	@Override
	@QueryHints(value = {@QueryHint(name = CacheUtils.QUERY_HINT_CACHEABLE_KEY, value = CacheUtils.QUERY_HINT_CACHEABLE_TRUE)})
	List<Parcel> findAll();

	@QueryHints(value = {@QueryHint(name = CacheUtils.QUERY_HINT_CACHEABLE_KEY, value = CacheUtils.QUERY_HINT_CACHEABLE_TRUE)})
	List<Parcel> findAllByUser(User user);

}
