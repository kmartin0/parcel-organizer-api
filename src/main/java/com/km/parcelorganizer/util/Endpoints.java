package com.km.parcelorganizer.util;

public class Endpoints {
	// User Endpoints
	public static final String SAVE_USER = "/users";
	public static final String GET_USER = "/users";
	public static final String UPDATE_USER = "/users";
	public static final String DELETE_USER = "/users";
	public static final String CHANGE_PASSWORD = "/users/change-password";
	public static final String FORGOT_PASSWORD = "/users/forgot-password";
	public static final String RESET_PASSWORD = "/users/reset-password";

	// Parcel Endpoints
	public static final String SAVE_PARCEL = "/parcels";
	public static final String GET_PARCELS = "/parcels";
	public static final String GET_PARCEL = "/parcels/{parcelId}";
	public static final String UPDATE_PARCEL = "/parcels";
	public static final String DELETE_PARCEL = "/parcels/{parcelId}";

	// Parcel Status Endpoints
	public static final String GET_PARCEL_STATUSES = "/parcel-statuses";
	public static final String GET_PARCEL_STATUS_BY_ID = "/parcel-statuses/id/{parcelStatusId}";
	public static final String GET_PARCEL_STATUS_BY_STATUS = "/parcel-statuses/status/{parcelStatus}";
}
