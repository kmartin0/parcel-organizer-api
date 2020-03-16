package com.km.parcelorganizer;

import com.km.parcelorganizer.exception.ResourceNotFoundException;
import com.km.parcelorganizer.features.parcel.Parcel;
import com.km.parcelorganizer.features.parcel.ParcelRepository;
import com.km.parcelorganizer.features.parcel.ParcelServiceImpl;
import com.km.parcelorganizer.features.parcelstatus.IParcelStatusService;
import com.km.parcelorganizer.features.parcelstatus.ParcelStatus;
import com.km.parcelorganizer.features.parcelstatus.ParcelStatusEnum;
import com.km.parcelorganizer.features.user.User;
import com.km.parcelorganizer.security.UserPrincipal;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration
@SpringBootTest
class ParcelServiceTests {

	@Mock
	private ParcelRepository parcelRepository;

	@Mock
	private IParcelStatusService parcelStatusService;

	@InjectMocks
	private ParcelServiceImpl parcelService;

	@Autowired
	private Validator validator;

	@Test
	void saveParcel_isSaved() {
		User authenticatedUser = setAuthenticatedUser();

		Parcel parcel = new Parcel();
		parcel.setParcelStatus(new ParcelStatus(0L, ParcelStatusEnum.SENT));
		parcel.setTitle("parcelTitle");
		parcel.setCourier("parcelCourier");
		parcel.setSender("parcelSender");

		Mockito.when(parcelStatusService.getParcelStatus(parcel.getParcelStatus().getId())).thenReturn(new ParcelStatus(0L, ParcelStatusEnum.SENT));

		parcelService.saveParcel(parcel);
		Set<ConstraintViolation<Parcel>> constraintViolations = validator.validate(parcel, Parcel.Create.class, ParcelStatus.Identifier.class);
		Assertions.assertEquals(0, constraintViolations.size());

		Mockito.verify(parcelRepository, Mockito.times(1)).save(parcel);
		Assertions.assertNotNull(parcel.getLastUpdated());
		Assertions.assertEquals(authenticatedUser, parcel.getUser());
	}

	@Test
	void getParcels_returnsTestData() {
		User authenticatedUser = setAuthenticatedUser();
		Mockito.when(parcelRepository.findAllByUser(authenticatedUser)).thenReturn(getTestParcels());
		Assertions.assertEquals(getTestParcels(), parcelService.getParcels());
	}

	@Test
	void getParcel_returnsParcel() {
		setAuthenticatedUser();
		Parcel expected = getTestParcels().get(0);
		Mockito.when(parcelRepository.findById(expected.getId())).thenReturn(Optional.of(expected));
		Assertions.assertEquals(expected, parcelService.getParcel(expected.getId()));
	}

	@Test
	void getUnownedParcel_throwsAccessDeniedException() {
		User authenticatedUser = setAuthenticatedUser();

		Parcel expected = getTestParcels().get(0);
		expected.getUser().setId(authenticatedUser.getId() + 1); // Change user of the parcel.

		Mockito.when(parcelRepository.findById(expected.getId())).thenReturn(Optional.of(expected));
		Assertions.assertThrows(AccessDeniedException.class, () -> parcelService.getParcel(expected.getId()));
	}

	@Test
	void getNonExistentParcel_throwsResourceNotFoundException() {
		setAuthenticatedUser();
		Assertions.assertThrows(ResourceNotFoundException.class, () -> parcelService.getParcel(20L));
	}

	@Test
	void updateParcel_isUpdated() {
		setAuthenticatedUser();
		Parcel parcelToUpdate = getTestParcels().get(0);
		Mockito.when(parcelRepository.findById(parcelToUpdate.getId())).thenReturn(Optional.of(parcelToUpdate));

		parcelToUpdate.setTitle("newTitle");
		parcelService.updateParcel(parcelToUpdate);

		Set<ConstraintViolation<Parcel>> constraintViolations = validator.validate(parcelToUpdate, Parcel.Update.class);
		Assertions.assertEquals(0, constraintViolations.size());
		Mockito.verify(parcelRepository, Mockito.times(1)).save(parcelToUpdate);
	}

	@Test
	void updateParcelFromAnotherUser_throwsAccessDeniedException() {
		setAuthenticatedUser();
		Parcel parcelToUpdate = getTestParcels().get(0);
		parcelToUpdate.getUser().setId(20L);
		Mockito.when(parcelRepository.findById(parcelToUpdate.getId())).thenReturn(Optional.of(parcelToUpdate));

		Assertions.assertThrows(AccessDeniedException.class, () -> parcelService.updateParcel(parcelToUpdate));
	}

	@Test
	void updateNonExistentParcel_throwsResourceNotFoundException() {
		setAuthenticatedUser();
		Parcel parcelToUpdate = getTestParcels().get(0);
		Assertions.assertThrows(ResourceNotFoundException.class, () -> parcelService.updateParcel(parcelToUpdate));
	}

	@Test
	void deleteParcel_isDeleted() {
		setAuthenticatedUser();
		Parcel parcelToDelete = getTestParcels().get(0);
		Mockito.when(parcelRepository.findById(parcelToDelete.getId())).thenReturn(Optional.of(parcelToDelete));

		parcelService.deleteParcel(parcelToDelete.getId());
		Mockito.verify(parcelRepository, Mockito.times(1)).delete(parcelToDelete);
	}

	@Test
	void deleteParcelFromAnotherUser_throwsAccessDeniedException() {
		setAuthenticatedUser();
		Parcel parcelToDelete = getTestParcels().get(0);
		parcelToDelete.getUser().setId(20L);
		Mockito.when(parcelRepository.findById(parcelToDelete.getId())).thenReturn(Optional.of(parcelToDelete));

		Assertions.assertThrows(AccessDeniedException.class, () -> parcelService.deleteParcel(parcelToDelete.getId()));
	}

	@Test
	void deleteNonExistentParcel_throwsResourceNotFoundException() {
		setAuthenticatedUser();
		Parcel parcelToDelete = getTestParcels().get(0);
		Assertions.assertThrows(ResourceNotFoundException.class, () -> parcelService.deleteParcel(parcelToDelete.getId()));
	}

	private List<Parcel> getTestParcels() {
		User user = new User();
		user.setPassword("pass");
		user.setName("Jason");
		user.setEmail("email");
		user.setId(1L);

		return Arrays.asList(
				new Parcel(0L, user, "clothes", "zalando", "postnl", null, null, new ParcelStatus(0L, ParcelStatusEnum.SENT), new Date()),
				new Parcel(1L, user, "watch", "bol.com", "postnl", null, "Pick up at parcel point", new ParcelStatus(1L, ParcelStatusEnum.ORDERED), new Date())
		);
	}

	/**
	 * Helper method to inject a principal user authentication in Spring Security Context.
	 *
	 * @return The authenticated user.
	 */
	private User setAuthenticatedUser() {
		User user = new User();
		user.setPassword("pass");
		user.setName("Jason");
		user.setEmail("email");
		user.setId(1L);

		Authentication auth = Mockito.mock(Authentication.class);

		SecurityContext securityContext = Mockito.mock(SecurityContext.class);
		Mockito.when(securityContext.getAuthentication()).thenReturn(auth);
		SecurityContextHolder.setContext(securityContext);

		UserPrincipal userPrincipal = new UserPrincipal(user);
		Mockito.when(auth.getPrincipal()).thenReturn(userPrincipal);

		return user;
	}

}
