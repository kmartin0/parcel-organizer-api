package com.km.parcelorganizer.features.parcel;

import javax.validation.Valid;
import java.util.List;

public interface IParcelService {
	Parcel saveParcel(@Valid Parcel parcel);

	List<Parcel> getParcels();

	Parcel getParcel(Long id);

	Parcel updateParcel(@Valid Parcel parcel);

	void deleteParcel(Long id);
}
