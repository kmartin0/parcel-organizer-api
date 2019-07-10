package com.km.parceltracker.features.parcelstatus;

import java.util.List;

public interface IParcelStatusService {
	List<ParcelStatus> getAllParcelStatuses();

	ParcelStatus getParcelStatus(Long id);

	ParcelStatus getParcelStatus(ParcelStatusEnum status);
}
