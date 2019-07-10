package com.km.parceltracker.features.parcel;

import java.util.List;

public interface IParcelService {
	Parcel saveParcel(Parcel parcel);

	List<Parcel> getParcels();

	Parcel getParcel(Long id);

	Parcel updateParcel(Parcel parcel);

	void deleteParcel(Long id);
}
