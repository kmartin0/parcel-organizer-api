package com.km.parceltracker.util;

public class Endpoints {
	// User Endpoints
	public static final String SAVE_USER = "/users";
	public static final String GET_USER = "/users";
	public static final String UPDATE_USER = "/users";
	public static final String DELETE_USER = "/users";

	// Parcel Endpoints
	public static final String SAVE_PARCEL = "/parcels";
	public static final String GET_PARCELS = "/parcels";
	public static final String GET_PARCEL = "/parcels/{parcelId}";
	public static final String UPDATE_PARCEL = "/parcels";
	public static final String DELETE_PARCEL = "/parcels/{parcelId}";

	// Parcel Status Endpoints
	public static final String GET_PARCEL_STATUSES = "/parcel-statuses";
	public static final String GET_PARCEL_STATUS_BY_ID = "/parcel-statuses/{parcelStatusId}";
	public static final String GET_PARCEL_STATUS_BY_STATUS = "/parcel-statuses/{parcelStatus}";
}
