package com.km.parceltracker.features.parcelstatus;

import com.km.parceltracker.util.Endpoints;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ParcelStatusController {

	private final IParcelStatusService ParcelStatusService;

	public ParcelStatusController(IParcelStatusService ParcelStatusService) {
		this.ParcelStatusService = ParcelStatusService;
	}

	@GetMapping(path = Endpoints.GET_PARCEL_STATUSES)
	@ResponseStatus(HttpStatus.OK)
	public List<ParcelStatus> getParcelStatuses() {
		return ParcelStatusService.getAllParcelStatuses();
	}

	@GetMapping(path = Endpoints.GET_PARCEL_STATUS_BY_ID)
	@ResponseStatus(HttpStatus.OK)
	public ParcelStatus getParcelStatusById(@PathVariable Long parcelStatusId) {
		return ParcelStatusService.getParcelStatus(parcelStatusId);
	}

	@GetMapping(path = Endpoints.GET_PARCEL_STATUS_BY_STATUS)
	@ResponseStatus(HttpStatus.OK)
	public ParcelStatus getParcelStatusByStatus(@PathVariable ParcelStatusEnum parcelStatus) {
		return ParcelStatusService.getParcelStatus(parcelStatus);
	}
}
