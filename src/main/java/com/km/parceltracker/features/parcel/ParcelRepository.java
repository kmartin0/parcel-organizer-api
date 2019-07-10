package com.km.parceltracker.features.parcel;

import com.km.parceltracker.features.user.User;
import com.km.parceltracker.util.CacheUtils;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.CrudRepository;

import javax.persistence.QueryHint;
import java.util.List;

public interface ParcelRepository extends CrudRepository<Parcel, Long> {

	@QueryHints(value = {@QueryHint(name = CacheUtils.QUERY_HINT_CACHEABLE_KEY, value = CacheUtils.QUERY_HINT_CACHEABLE_TRUE)})
	List<Parcel> findAllByUser(User user);

}
