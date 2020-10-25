package com.gvn.brings.util;

public class DistanceChecker {

	
	public double distanceBetween(String fromLat,String fromLng,String userLat,String usrLng)
	{
		double lat1=Double.parseDouble(fromLat);
		double lat2=Double.parseDouble(userLat);
		double theta = Math.abs(Double.parseDouble(fromLng) - Double.parseDouble(usrLng));
		double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
		dist = Math.acos(dist);
		dist = rad2deg(dist);
		dist = dist * 60 * 1.1515;
		
		dist = dist * 1.609344;
		/*if(dist<2)
		{
		return 1;
		}*/
		System.out.println("distance "+Math.round(dist));
	return Math.round(dist);
		}
		private static double deg2rad(double deg) {
		return (deg * Math.PI / 180.0);
		}

		/*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
		/*::	This function converts radians to decimal degrees	 :*/
		/*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
		private static double rad2deg(double rad) {
		return (rad * 180 / Math.PI);
		}
		
		public double calcCrow(double lat1, double lon1, double lat2, double lon2) 
		    {
		      double R = 6371; // km
		      double dLat = toRad(lat2-lat1);
		      double dLon = toRad(lon2-lon1);
		      double lat11 = toRad(lat1);
		      double lat21 = toRad(lat2);

		      double a = Math.sin(dLat/2) * Math.sin(dLat/2) +
		        Math.sin(dLon/2) * Math.sin(dLon/2) * Math.cos(lat11) * Math.cos(lat21); 
		      double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a)); 
		      double d = R * c;
		      return d;
		    }

		    // Converts numeric degrees to radians
		public double toRad(double Value) 
		    {
		        return Value * Math.PI / 180;
		    }
		
		public static void main(String args[])
		{
			DistanceChecker ds=new DistanceChecker();
			System.out.println(ds.calcCrow(17.897, 78.987, 17.987, 78.0987));
			System.out.println(ds.distanceBetween("17.897", "78.987", "17.987", "78.0987"));
		}
	
}
