package com.km.parcelorganizer.features.parcel;

import com.km.parcelorganizer.exception.ResourceNotFoundException;
import com.km.parcelorganizer.features.parcelstatus.IParcelStatusService;
import com.km.parcelorganizer.features.parcelstatus.ParcelStatus;
import com.km.parcelorganizer.features.user.User;
import com.km.parcelorganizer.security.SecurityHelper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.Date;
import java.util.List;

@Service
@Validated
public class ParcelServiceImpl implements IParcelService {

	private final ParcelRepository parcelRepository;

	private final IParcelStatusService parcelStatusService;

	public ParcelServiceImpl(ParcelRepository parcelRepository, IParcelStatusService parcelStatusService) {
		this.parcelRepository = parcelRepository;
		this.parcelStatusService = parcelStatusService;
	}

	@Override
	@Validated({Parcel.Create.class, ParcelStatus.Identifier.class})
	@PreAuthorize("isAuthenticated()")
	public Parcel saveParcel(Parcel parcel) {
		// Get logged in user and bind it to the parcel.
		User user = SecurityHelper.getPrincipalUser();
		parcel.setUser(user);

		// Set the parcel error.
		ParcelStatus parcelStatus = parcelStatusService.getParcelStatus(parcel.getParcelStatus().getId());
		parcel.setParcelStatus(parcelStatus);

		// Set the last updated time.
		parcel.setLastUpdated(new Date());

		// Save and return the parcel.
		return parcelRepository.save(parcel);
	}

	@Override
	@PreAuthorize("isAuthenticated()")
	public List<Parcel> getParcels() {

//		Return the parcels owned by the authenticated user.
		return parcelRepository.findAllByUserOrderByLastUpdatedDesc(SecurityHelper.getPrincipalUser());
	}

	@Override
	@PreAuthorize("isAuthenticated()")
	public Parcel getParcel(Long id) {
		// Find the parcel or throw resource not found exception.
		Parcel parcel = parcelRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(Parcel.class, id));

		// Validate if the parcel is owned by the authenticated user.
		SecurityHelper.validatePrincipalIsUser(parcel.getUser().getId());

		// Return the parcel.
		return parcel;
	}

	@Override
	@Validated({Parcel.Update.class})
	@PreAuthorize("isAuthenticated()")
	public Parcel updateParcel(Parcel parcel) {
		// Get the parcel that needs to be updated.
		Parcel parcelToUpdate = parcelRepository.findById(parcel.getId())
				.orElseThrow(() -> new ResourceNotFoundException(Parcel.class, parcel.getId()));

		// Validate if authenticated user is the parcel owner.
		SecurityHelper.validatePrincipalIsUser(parcelToUpdate.getUser().getId());

		// Set the last updated time keep the id and user related to the parcel the same.
		parcel.setLastUpdated(new Date());
		parcel.setId(parcelToUpdate.getId());
		parcel.setUser(SecurityHelper.getPrincipalUser());

		// Set the new parcel status.
		ParcelStatus newParcelStatus = parcelStatusService.getParcelStatus(parcel.getParcelStatus().getId());
		parcel.setParcelStatus(newParcelStatus);

		// Save and return the updated parcel.
		return parcelRepository.save(parcel);
	}

	@Override
	@PreAuthorize("isAuthenticated()")
	public void deleteParcel(Long id) {
		// Get the parcel by it's id. Or throw a resource not found exception.
		Parcel parcel = parcelRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException(Parcel.class, id));

		// Validate if the authenticated user is the owner of the parcel.
		SecurityHelper.validatePrincipalIsUser(parcel.getUser().getId());

		// Delete the parcel.
		parcelRepository.delete(parcel);
	}
}
