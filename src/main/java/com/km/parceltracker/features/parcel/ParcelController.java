package com.km.parceltracker.features.parcel;

import com.km.parceltracker.features.parcelstatus.ParcelStatus;
import com.km.parceltracker.util.Endpoints;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ParcelController {

	private final IParcelService parcelService;

	@Autowired
	public ParcelController(IParcelService parcelService) {
		this.parcelService = parcelService;
	}

	@PostMapping(path = Endpoints.SAVE_PARCEL, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.CREATED)
	public Parcel saveParcel(@Validated({Parcel.Create.class, ParcelStatus.Identifier.class}) @RequestBody Parcel parcel) {

		return parcelService.saveParcel(parcel);
	}

	@GetMapping(path = Endpoints.GET_PARCELS)
	@ResponseStatus(HttpStatus.OK)
	@PreAuthorize("isAuthenticated()")
	public List<Parcel> getParcels() {

		return parcelService.getParcels();
	}

	@GetMapping(path = Endpoints.GET_PARCEL)
	@ResponseStatus(HttpStatus.OK)
	@PreAuthorize("isAuthenticated()")
	public Parcel getParcelById(@PathVariable Long parcelId) {

		return parcelService.getParcel(parcelId);
	}

	@PutMapping(path = Endpoints.UPDATE_PARCEL)
	@ResponseStatus(HttpStatus.OK)
	@PreAuthorize("isAuthenticated()")
	public Parcel updateParcel(@Validated({Parcel.Update.class}) @RequestBody Parcel parcel) {

		return parcelService.updateParcel(parcel);
	}

	@DeleteMapping(path = Endpoints.DELETE_PARCEL)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@PreAuthorize("isAuthenticated()")
	public void deleteParcel(@PathVariable Long parcelId) {

		parcelService.deleteParcel(parcelId);
	}

}
