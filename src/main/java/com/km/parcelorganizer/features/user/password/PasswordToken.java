package com.km.parcelorganizer.features.user.password;

import com.km.parcelorganizer.features.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class PasswordToken {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private Long id;

	@Column(name = "token", nullable = false)
	private String token;

	@OneToOne(targetEntity = User.class)
	@JoinColumn(name = "\"user\"", nullable = false)
	private User user;

	@Column(name = "expiration", nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date expiration;
}
