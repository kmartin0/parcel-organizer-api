package com.km.parceltracker.features.parcelstatus;

import com.km.parceltracker.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ParcelStatusServiceImpl implements IParcelStatusService {

	private final ParcelStatusRepository parcelStatusRepository;

	public ParcelStatusServiceImpl(ParcelStatusRepository parcelStatusRepository) {
		this.parcelStatusRepository = parcelStatusRepository;
	}

	@Override
	public List<ParcelStatus> getAllParcelStatuses() {
		return parcelStatusRepository.findAll();
	}

	@Override
	public ParcelStatus getParcelStatus(Long id) {
		return parcelStatusRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException(ParcelStatus.class, id));
	}

	@Override
	public ParcelStatus getParcelStatus(ParcelStatusEnum status) {
		return parcelStatusRepository.findByStatus(status)
				.orElseThrow(() -> new ResourceNotFoundException(ParcelStatus.class, status));
	}
}
