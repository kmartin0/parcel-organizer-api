package com.km.parceltracker.features.parcel;

import com.km.parceltracker.features.user.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ParcelRepository extends CrudRepository<Parcel, Long> {
	List<Parcel> findAllByUser(User user);
}
