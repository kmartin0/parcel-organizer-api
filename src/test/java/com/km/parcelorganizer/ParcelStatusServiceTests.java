package com.km.parcelorganizer;

import com.km.parcelorganizer.exception.ResourceNotFoundException;
import com.km.parcelorganizer.features.parcelstatus.ParcelStatus;
import com.km.parcelorganizer.features.parcelstatus.ParcelStatusEnum;
import com.km.parcelorganizer.features.parcelstatus.ParcelStatusRepository;
import com.km.parcelorganizer.features.parcelstatus.ParcelStatusServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@ExtendWith(SpringExtension.class)
@ContextConfiguration
@SpringBootTest
class ParcelStatusServiceTests {

	@Mock
	private ParcelStatusRepository parcelStatusRepository;

	@InjectMocks
	private ParcelStatusServiceImpl parcelStatusService;

	@Test
	void testGetAllParcelStatuses_returnsRepositoryData() {
		Mockito.when(parcelStatusRepository.findAll()).thenReturn(getTestData());
		Assertions.assertEquals(getTestData(), parcelStatusService.getAllParcelStatuses());
	}

	@Test
	void testGetParcelStatusById_returnsDeliveredStatus() {
		ParcelStatus expected = getTestData().get(0);
		Mockito.when(parcelStatusRepository.findById(expected.getId())).thenReturn(Optional.of(expected));
		Assertions.assertEquals(expected, parcelStatusService.getParcelStatus(0L));
	}

	@Test
	void testGetNonExistentParcelStatusById_throwsResourceNotFoundException() {
		Assertions.assertThrows(ResourceNotFoundException.class, () -> parcelStatusService.getParcelStatus(20L));
	}

	@Test
	void testGetParcelStatusByName_returnsDeliveredStatus() {
		ParcelStatus expected = getTestData().get(0);
		Mockito.when(parcelStatusRepository.findByStatus(expected.getStatus())).thenReturn(Optional.of(expected));
		Assertions.assertEquals(expected, parcelStatusService.getParcelStatus("DELIVERED"));
	}

	@Test
	void testGetNonExistentParcelStatusByName_throwsResourceNotFoundException() {
		Assertions.assertThrows(ResourceNotFoundException.class, () -> parcelStatusService.getParcelStatus("InvalidParcelStatus"));
	}

	private List<ParcelStatus> getTestData() {
		return Arrays.asList(
				new ParcelStatus(0L, ParcelStatusEnum.DELIVERED),
				new ParcelStatus(1L, ParcelStatusEnum.ORDERED),
				new ParcelStatus(2L, ParcelStatusEnum.SENT)
		);
	}

}
