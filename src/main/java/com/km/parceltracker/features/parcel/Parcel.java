package com.km.parceltracker.features.parcel;

import com.km.parceltracker.features.user.User;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Null;
import java.util.Date;

@Data
@Entity
public class Parcel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	@Null(groups = {Create.class})
	private Long id;

	@ManyToOne
	@JoinColumn(name = "user", nullable = false)
	private User user;

	@Column(name = "title", nullable = false, length = 45, unique = true)
	@NotBlank(groups = {Create.class, Update.class})
	@Length(max = 45, groups = {Create.class, Update.class})
	private String title;

	@Column(name = "sender", nullable = true, length = 45)
	@Length(max = 45, groups = {Create.class, Update.class})
	private String sender;

	@Column(name = "courier", nullable = true, length = 45)
	@Length(max = 45, groups = {Create.class, Update.class})
	private String courier;

	@Column(name = "tracking_url", nullable = true, columnDefinition = "LONGTEXT")
	private String trackingUrl;

	@Column(name = "delivery_status", nullable = false)
	@NotBlank(groups = {Create.class, Update.class})
	private String deliveryStatus;

	@Column(name = "last_updated", nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date lastUpdated;

	public interface Create {
	}

	public interface Update {
	}

}
