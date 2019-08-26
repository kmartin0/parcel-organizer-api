package com.km.parcelorganizer.features.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.km.parcelorganizer.util.CacheUtils;
import lombok.Data;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Null;

@Data
@Entity
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = CacheUtils.USER_CACHE_REGION)
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	@Null(groups = {Create.class})
	private Long id;

	@Column(name = "email", nullable = false, length = 45, unique = true)
	@NotBlank(groups = {Create.class, Update.class})
	@Length(max = 45, groups = {Create.class, Update.class})
	@Email
	private String email;

	@Column(name = "name", nullable = false, length = 45)
	@NotBlank(groups = {Create.class, Update.class})
	@Length(max = 45, groups = {Create.class, Update.class})
	private String name;

	@Column(name = "password", nullable = false, columnDefinition = "LONGTEXT")
	@NotBlank(groups = {Create.class, Update.class})
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private String password;

	public interface Create {
	}

	public interface Update {
	}

}
