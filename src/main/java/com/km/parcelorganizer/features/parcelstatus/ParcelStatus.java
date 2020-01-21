package com.km.parcelorganizer.features.parcelstatus;

import com.km.parcelorganizer.util.CacheUtils;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Data
@Entity
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = CacheUtils.PARCEL_STATUS_CACHE_REGION)
@AllArgsConstructor
@NoArgsConstructor
public class ParcelStatus {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	@NotNull(groups = {Identifier.class})
	private Long id;

	@Basic
	@Column(name = "status", nullable = false, length = 45)
	@Enumerated(EnumType.STRING)
	private ParcelStatusEnum status;

	public interface Identifier {

	}
}
