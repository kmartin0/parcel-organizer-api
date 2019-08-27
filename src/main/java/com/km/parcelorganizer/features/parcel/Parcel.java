package com.km.parcelorganizer.features.parcel;

import com.km.parcelorganizer.features.parcelstatus.ParcelStatus;
import com.km.parcelorganizer.features.user.User;
import com.km.parcelorganizer.util.CacheUtils;
import lombok.Data;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.util.Date;

@Data
@Entity
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = CacheUtils.PARCEL_CACHE_REGION)
public class Parcel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	@Null(groups = {Create.class})
	@NotNull(groups = {Update.class})
	private Long id;

	@ManyToOne
	@JoinColumn(name = "\"user\"", nullable = false)
	private User user;

	@Column(name = "title", nullable = false, length = 45)
	@NotBlank(groups = {Create.class, Update.class})
	@Length(max = 45, groups = {Create.class, Update.class})
	private String title;

	@Column(name = "sender", nullable = true, length = 45)
	@Length(max = 45, groups = {Create.class, Update.class})
	private String sender;

	@Column(name = "courier", nullable = true, length = 45)
	@Length(max = 45, groups = {Create.class, Update.class})
	private String courier;

	@Column(name = "tracking_url", nullable = true, columnDefinition = "TEXT")
	private String trackingUrl;

	@ManyToOne
	@JoinColumn(name = "parcel_status", nullable = false)
	@NotNull(groups = {Create.class, Update.class})
	@Valid
	private ParcelStatus parcelStatus;

	@Column(name = "last_updated", nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date lastUpdated;

	public interface Create {
	}

	public interface Update {
	}

}
