package nl.kmartin.parcelorganizer.api.features.user.password;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface PasswordTokenRepository extends CrudRepository<PasswordToken, Long> {

	Optional<PasswordToken> findByToken(String token);

}
