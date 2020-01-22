package com.km.parcelorganizer.features.parcelstatus;

import java.util.List;

public interface IParcelStatusService {
	List<ParcelStatus> getAllParcelStatuses();

	ParcelStatus getParcelStatus(Long id);

	ParcelStatus getParcelStatus(String status);
}
