package com.gvn.brings.dao;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.gvn.brings.dto.OrderDeliveryDto;
import com.gvn.brings.dto.TestDto;
import com.gvn.brings.model.BrngAdminImages;
import com.gvn.brings.model.BrngAppVersion;
import com.gvn.brings.model.BrngContactDetails;
import com.gvn.brings.model.BrngDeliveryExecutiveFromWeb;
import com.gvn.brings.model.BrngGeneralLkp;
import com.gvn.brings.model.BrngLkpIsPaid;
import com.gvn.brings.model.BrngLkpPayType;
import com.gvn.brings.model.BrngPerPersonPricing;
import com.gvn.brings.model.BrngRateLkp;
import com.gvn.brings.model.BrngSubscribeUsers;
import com.gvn.brings.model.BrngTest;
import com.gvn.brings.model.BrngUsrLogin;
import com.gvn.brings.model.BrngUsrReg;
import com.gvn.brings.util.MailUtility;
import com.gvn.brings.util.PaymentUtil;
import com.gvn.brings.util.UserMessages;

@Repository
@Transactional
public class UtilityDAO extends AbstractBaseDao {

	@PersistenceContext
	private EntityManager manager;

	public HashMap<String, String> getAppVersion() {
		List<BrngAppVersion> BrngAppVersion = getManager()
				.createQuery("Select a From BrngAppVersion a order by effectiveDate desc", BrngAppVersion.class)
				.getResultList();
		
		List<BrngGeneralLkp> generalValues = getManager()
				.createQuery("Select a From BrngGeneralLkp a where a.brngKey in ('maintenance_mode','maintenance_msg','app_exit','maintenance_title','ios-social-login') order by id", BrngGeneralLkp.class)
				.getResultList();
		HashMap<String, String> versions = new HashMap<String, String>();
		versions.put("android", BrngAppVersion.get(0).getAndroidVersion());
		versions.put("ios", BrngAppVersion.get(0).getIosVersion());
		versions.put("maintenance_mode", generalValues.get(0).getValue());
		versions.put("maintenance_msg", generalValues.get(1).getValue());
		versions.put("app_exit", generalValues.get(2).getValue());
		versions.put("maintenance_title", generalValues.get(3).getValue());
		versions.put("iosSocialLogin", generalValues.get(4).getValue());
		return versions;
	}

	public List<BrngLkpPayType> getPaymentOptions() {
		List<BrngLkpPayType> brnglkppaytypes = getManager()
				.createQuery("Select a From BrngLkpPayType a ", BrngLkpPayType.class).getResultList();
		return brnglkppaytypes;
	}

	public float getRateValues(String dist, String weight, String city, String phoneNumber) {
		List<BrngRateLkp> brngTests = getManager()
				.createQuery("Select a From BrngRateLkp a order by effectiveDate desc", BrngRateLkp.class)
				.getResultList();

	
	List<BrngPerPersonPricing> listOfPricing  = getManager().createQuery("select a from BrngPerPersonPricing a where a.phoneNumber='"+phoneNumber+"'" , BrngPerPersonPricing.class).getResultList();
	if(listOfPricing !=null && listOfPricing.size() > 0 ) {
		return Float.parseFloat(String.format("%.2f",
				getPricePerPerson((float)Math.ceil(Float.parseFloat(dist.replace(",", ""))),(float)Math.ceil( Float.parseFloat(weight)), brngTests.get(0),phoneNumber)));
	}
//		String.format("%.2f",
//				getNewPrice((float)Math.ceil(Float.parseFloat(dist.replace(",", ""))),(float)Math.ceil( Float.parseFloat(weight)), brngTests.get(0)));
//		return Float.parseFloat(String.format("%.2f",
//				getNewPrice((float)Math.ceil(Float.parseFloat(dist.replace(",", ""))),(float)Math.ceil( Float.parseFloat(weight)), brngTests.get(0))));
		return Float.parseFloat(String.format("%.2f",
				getPriceByCity((float)Math.ceil(Float.parseFloat(dist.replace(",", ""))),(float)Math.ceil( Float.parseFloat(weight)), brngTests.get(0),city)));
		// return getWeight(Float.parseFloat(dist.replace(",",
		// "")),Float.parseFloat(weight),brngTests.get(0));
	}
	
	public float getRateValues(String dist, String weight) {
		List<BrngRateLkp> brngTests = getManager()
				.createQuery("Select a From BrngRateLkp a order by effectiveDate desc", BrngRateLkp.class)
				.getResultList();

	
//	List<BrngPerPersonPricing> listOfPricing  = getManager().createQuery("select a from BrngPerPersonPricing a where a.phoneNumber='"+phoneNumber+"'" , BrngPerPersonPricing.class).getResultList();
//	if(listOfPricing !=null && listOfPricing.size() > 0 ) {
		return Float.parseFloat(String.format("%.2f",
				getNewPrice((float)Math.ceil(Float.parseFloat(dist.replace(",", ""))),(float)Math.ceil( Float.parseFloat(weight)), brngTests.get(0))));
	//}
//		String.format("%.2f",
//				getNewPrice((float)Math.ceil(Float.parseFloat(dist.replace(",", ""))),(float)Math.ceil( Float.parseFloat(weight)), brngTests.get(0)));
//		return Float.parseFloat(String.format("%.2f",
//				getNewPrice((float)Math.ceil(Float.parseFloat(dist.replace(",", ""))),(float)Math.ceil( Float.parseFloat(weight)), brngTests.get(0))));
//		return Float.parseFloat(String.format("%.2f",
//				getPriceByCity((float)Math.ceil(Float.parseFloat(dist.replace(",", ""))),(float)Math.ceil( Float.parseFloat(weight)), brngTests.get(0),city)));
		// return getWeight(Float.parseFloat(dist.replace(",",
		// "")),Float.parseFloat(weight),brngTests.get(0));
	}

	public List<BrngAdminImages> getAdminImages() {
		List<BrngAdminImages> listImages = getManager()
				.createQuery("Select a From BrngAdminImages a  where a.status='A' order by effectiveStartDate desc",
						BrngAdminImages.class)
				.getResultList();

		return listImages;
	}

	public float getWeight(float dist, float weight, BrngRateLkp brngTests) {

		try {
			List<BrngGeneralLkp> brnggenerallkup1 = getManager().createQuery(
					"select a from BrngGeneralLkp a where a.brngKey in ('fivekmsflag','distance','rate') order by a.brngKey ",
					BrngGeneralLkp.class).getResultList();
			int distancetbc = Integer.parseInt(brnggenerallkup1.get(0).getValue());
			String fivekmsflag = brnggenerallkup1.get(1).getValue();
			int ratetbc = Integer.parseInt(brnggenerallkup1.get(2).getValue());
			if (fivekmsflag.equalsIgnoreCase("F")) {
				Date date = new Date();
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(date);
				int hours = calendar.get(Calendar.HOUR_OF_DAY);
				int surChargeHour = 0;
				int surchargeValue = 0;
				List<BrngGeneralLkp> brnggenerallkup = getManager().createQuery(
						"select a from BrngGeneralLkp a where a.brngKey in ('surcharge-time','surcharge-value') order by a.brngKey ",
						BrngGeneralLkp.class).getResultList();

				surChargeHour = Integer.parseInt(brnggenerallkup.get(0).getValue());
				if (surChargeHour < hours) {
					surchargeValue = Integer.parseInt(brnggenerallkup.get(1).getValue());
				}
				float price;
				if (dist <= Float.parseFloat(brngTests.getInitialKms())) {
					if (weight <= Float.parseFloat(brngTests.getInitialWeight())) {
						if (surchargeValue == 0) {
							return price = Float.parseFloat(brngTests.getInitialRate());
						} else {
							return price = surchargeValue * Float.parseFloat(brngTests.getInitialRate());
						}

					} else {
						weight = weight - Float.parseFloat(brngTests.getInitialWeight());
						if (surchargeValue == 0) {
							return price = Float.parseFloat(brngTests.getInitialRate())
									+ (weight * Float.parseFloat(brngTests.getRatePerKg()));
						} else {
							return price = surchargeValue * Float.parseFloat(brngTests.getInitialRate())
									+ (weight * Float.parseFloat(brngTests.getRatePerKg()));
						}

					}
				} else {
					if (weight <= Float.parseFloat(brngTests.getInitialWeight())) {
						dist = (dist - Float.parseFloat(brngTests.getInitialKms()));
						if (surchargeValue == 0) {
							return price = Float.parseFloat(brngTests.getInitialRate())
									+ ((float) Math.ceil(dist) * Float.parseFloat(brngTests.getRatePerKm()));
						} else {
							return price = surchargeValue * Float.parseFloat(brngTests.getInitialRate())
									+ ((float) Math.ceil(dist) * Float.parseFloat(brngTests.getRatePerKm()));
						}
					} else {
						dist = dist - Float.parseFloat(brngTests.getInitialKms());
						weight = weight - Float.parseFloat(brngTests.getInitialWeight());
						if (surchargeValue == 0) {
							return price = Float.parseFloat(brngTests.getInitialRate())
									+ (dist * Float.parseFloat(brngTests.getRatePerKm()))
									+ (weight * Float.parseFloat(brngTests.getRatePerKg()));
						} else {
							return price = surchargeValue * Float.parseFloat(brngTests.getInitialRate())
									+ (dist * Float.parseFloat(brngTests.getRatePerKm()))
									+ (weight * Float.parseFloat(brngTests.getRatePerKg()));
						}

					}
				}

			}

			else if (fivekmsflag.equalsIgnoreCase("T")) {
				Date date = new Date();
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(date);
				int hours = calendar.get(Calendar.HOUR_OF_DAY);
				int surChargeHour = 0;
				int surchargeValue = 0;
				List<BrngGeneralLkp> brnggenerallkup = getManager().createQuery(
						"select a from BrngGeneralLkp a where a.brngKey in ('surcharge-time','surcharge-value') order by a.brngKey ",
						BrngGeneralLkp.class).getResultList();

				surChargeHour = Integer.parseInt(brnggenerallkup.get(0).getValue());
				if (surChargeHour < hours) {
					surchargeValue = Integer.parseInt(brnggenerallkup.get(1).getValue());
				}
				float price;
				if (dist <= distancetbc) {
					if (weight <= Float.parseFloat(brngTests.getInitialWeight())) {
						if (surchargeValue == 0) {
							return price = ratetbc;
						} else {
							return price = surchargeValue * ratetbc;
						}

					} else {
						weight = weight - Float.parseFloat(brngTests.getInitialWeight());
						if (surchargeValue == 0) {
							return price = ratetbc + (weight * Float.parseFloat(brngTests.getRatePerKg()));
						} else {
							return price = surchargeValue * ratetbc
									+ (weight * Float.parseFloat(brngTests.getRatePerKg()));
						}

					}
				} else {
					if (weight <= Float.parseFloat(brngTests.getInitialWeight())) {
						dist = (dist - distancetbc);

						if (surchargeValue == 0) {
							if (dist < distancetbc) {
								return price = ratetbc + ratetbc;
							} else if (dist > distancetbc) {
								int calctbc = (int) dist / distancetbc;
								return price = ratetbc + ratetbc + ratetbc * calctbc;
							}

						} else {
							if (dist < distancetbc) {
								return price = surchargeValue * (ratetbc + ratetbc);
							} else if (dist > distancetbc) {
								int calctbc = (int) dist / distancetbc;
								return price = surchargeValue * (ratetbc + ratetbc + ratetbc * calctbc);
							}
						}
					} else {
						dist = (dist - distancetbc);
						weight = weight - Float.parseFloat(brngTests.getInitialWeight());
						if (surchargeValue == 0) {
							if (dist < distancetbc) {
								return price = ratetbc + ratetbc
										+ (weight * Float.parseFloat(brngTests.getRatePerKg()));
							} else if (dist > distancetbc) {
								int calctbc = (int) dist / distancetbc;
								return price = ratetbc + ratetbc + ratetbc * calctbc
										+ (weight * Float.parseFloat(brngTests.getRatePerKg()));
							}
						} else {
							if (dist < distancetbc) {
								return price = surchargeValue
										* (ratetbc + ratetbc + (weight * Float.parseFloat(brngTests.getRatePerKg())));
							} else if (dist > distancetbc) {
								int calctbc = (int) dist / distancetbc;
								return price = surchargeValue * (ratetbc + ratetbc + ratetbc * calctbc
										+ (weight * Float.parseFloat(brngTests.getRatePerKg())));
							}
						}

					}
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;

	}

	public HashMap<String, String> deilveryBoyFromWeb(HashMap<String, String> inputDetails) {
		HashMap<String, String> response = new HashMap<>();
		String message = "";
		try {
			BrngDeliveryExecutiveFromWeb brngDeliveryExecutiveFromWeb = getManager().createQuery(
					"Select a From BrngDeliveryExecutiveFromWeb a where phone='" + inputDetails.get("phone") + "'",
					BrngDeliveryExecutiveFromWeb.class).getSingleResult();
			message = UserMessages.getUserMessagesNew("ARFW");
			response.put("message", message);
			response.put("response", "1");
			// response.put("email",
			// brngusrregattr.getBrngUsrReg().getEmailId());
		} catch (NoResultException e) {
			BrngDeliveryExecutiveFromWeb brngdeliveryexecutiveFromWeb = new BrngDeliveryExecutiveFromWeb();
			brngdeliveryexecutiveFromWeb.setEffectiveDate(new Timestamp(System.currentTimeMillis()));
			brngdeliveryexecutiveFromWeb.setName(inputDetails.get("name"));
			brngdeliveryexecutiveFromWeb.setPhone(inputDetails.get("phone"));

			getManager().persist(brngdeliveryexecutiveFromWeb);
			MailUtility mailutil = new MailUtility();
			mailutil.sendMailToAdmin("hr@gvnsoftech.com",
					"Name : " + inputDetails.get("name") + " phone :" + " " + inputDetails.get("phone")); // hardcoded
																											// remoce
																											// after
			message = UserMessages.getUserMessagesNew("RFW");
			response.put("message", message);
			response.put("response", "1");
		} catch (Exception e) {
			e.printStackTrace();
			message = UserMessages.getUserMessagesNew("E");
			response.put("message", message);
			response.put("response", "-1");
		}
		return response;
	}

	public HashMap<String, String> subscribeUsers(HashMap<String, String> inputDetails) {
		HashMap<String, String> response = new HashMap<>();
		String message = "";
		try {
			BrngSubscribeUsers brngsubscribeusers = getManager()
					.createQuery("Select a From BrngSubscribeUsers a where email='" + inputDetails.get("email") + "'",
							BrngSubscribeUsers.class)
					.getSingleResult();
			message = UserMessages.getUserMessagesNew("UAS");
			response.put("message", message);
			response.put("response", "1");
			// response.put("email",
			// brngusrregattr.getBrngUsrReg().getEmailId());
		} catch (NoResultException e) {
			BrngSubscribeUsers brngsubscribeusers = new BrngSubscribeUsers();
			brngsubscribeusers.setEffectiveDate(new Timestamp(System.currentTimeMillis()));
			brngsubscribeusers.setEmail(inputDetails.get("email"));

			getManager().persist(brngsubscribeusers);
			message = UserMessages.getUserMessagesNew("USS");
			response.put("message", message);
			response.put("response", "1");
		} catch (Exception e) {
			e.printStackTrace();
			message = UserMessages.getUserMessagesNew("E");
			response.put("message", message);
			response.put("response", "-1");
		}
		return response;
	}

	public HashMap<String, String> contactDetails(HashMap<String, String> inputDetails) {
		HashMap<String, String> response = new HashMap<>();
		String message = "";
		try {

			BrngContactDetails brngcontactdetails = new BrngContactDetails();
			brngcontactdetails.setEffectiveDate(new Timestamp(System.currentTimeMillis()));
			brngcontactdetails.setEmail(inputDetails.get("email"));
			brngcontactdetails.setPhone(inputDetails.get("phone"));
			brngcontactdetails.setName(inputDetails.get("name"));

			brngcontactdetails.setDescription(inputDetails.get("description"));

			getManager().persist(brngcontactdetails);
			MailUtility mailutil = new MailUtility();
			mailutil.sendMailToAdminForContact("hr@gvnsoftech.com", "Name : " + inputDetails.get("name") + " phone :"
					+ " " + inputDetails.get("phone") + " description:" + inputDetails.get("description")); // hardcoded
																											// remoce
																											// after

			message = UserMessages.getUserMessagesNew("RFW");
			response.put("message", message);
			response.put("response", "1");
		} catch (Exception e) {
			e.printStackTrace();
			message = UserMessages.getUserMessagesNew("E");
			response.put("message", message);
			response.put("response", "-1");
		}
		return response;
	}

	public ArrayList<HashMap<Object, Object>> getOffers() {
		ArrayList<HashMap<Object, Object>> response = new ArrayList<>();
		String message = "";
		try {
			Date today = new Date();
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			String strDate = formatter.format(today);
			today = new SimpleDateFormat("yyyy-MM-dd").parse(strDate);

			System.out.println(strDate);
			System.out.println(today);
			List<Object[]> listOfOffers = getManager()
					.createQuery(
							"select a.name,b.description,date(a.startDate),date(a.endDate),a.maxDiscount,a.offerPercent from BrngOffer a,BrngLkpOfferType b where a.offerType=b.id and a.name <> 'NA' and a.name <> 'CORP' order by a.endDate desc")
					.getResultList();

			for (Object[] result : listOfOffers) {
				HashMap<Object, Object> offerDetails = new HashMap<>();
				offerDetails.put("name", (String) result[0]);
				offerDetails.put("description", (String) result[1]);
				offerDetails.put("startDate", (Date) result[2]);
				offerDetails.put("endDate", (Date) result[3]);
				if (((String) result[4]).equalsIgnoreCase("999999")) {
					offerDetails.put("maxDiscount", "ORDER AMOUNT ");
				} else {
					offerDetails.put("maxDiscount", (String) result[4]);
				}
				offerDetails.put("offerPercent", (String) result[5]);
				response.add(offerDetails);
			}

		} catch (Exception e) {
			e.printStackTrace();
			HashMap<Object, Object> offerDetails = new HashMap<>();
			offerDetails.put("message", "Its a server Issue");
			offerDetails.put("code", "500");
			response.add(offerDetails);

		}
		return response;
	}

	public int checkAuthentication(String token, int regId) {

		try {
			System.out.println("token :" + token + " regId" + regId);
			Query query = getManager().createQuery(
					"Select count(*) from BrngUsrLogin where brngUsrReg.id=" + regId + " and token='" + token + "'");
			long count = (long) query.getSingleResult();

			if (count > 0) {
				return 1;
			} else {
				return 0;
			}

		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}

	}

	public HashMap<String, String> proceedPayment(HashMap<String, String> inputDetails) {

		int paidId = 0;
		HashMap<String, String> response = new HashMap<>();
		String message = "";
		String paidStatus = "";

		try {
			PaymentUtil paymentutil = new PaymentUtil();
			inputDetails = paymentutil.populatePaymentDetail(inputDetails);
			BrngUsrReg brngusrreg = manager
					.createQuery("Select a From BrngUsrReg a where a.emailId= '" + inputDetails.get("email") + "'",
							BrngUsrReg.class)
					.getSingleResult();

			int userId = brngusrreg.getId();
			/*
			 * int ret=checkAuthentication(apiKey, userId); if(ret==0) {
			 * message=UserMessages.getUserMessagesNew("TE");
			 * response.put("response","0"); response.put("message",message);
			 * return response; }
			 */
			BrngLkpPayType brnglkppaytype = manager
					.createQuery("Select a From BrngLkpPayType a where a.type= '" + inputDetails.get("type") + "'",
							BrngLkpPayType.class)
					.getSingleResult();
			int payTypeId = brnglkppaytype.getId();
			List<BrngLkpIsPaid> brngLkpIsPaidTypes = manager
					.createQuery("Select a From BrngLkpIsPaid a", BrngLkpIsPaid.class).getResultList();
			List<OrderDeliveryDto> dtoList = new ArrayList();
			OrderDeliveryDto orderDeliveryDto = null;
			for (BrngLkpIsPaid brngLkpIsPaidType : brngLkpIsPaidTypes) {
				if (brngLkpIsPaidType.getIsPaid().equalsIgnoreCase("Y")) {
					paidId = brngLkpIsPaidType.getId();
					paidStatus = brngLkpIsPaidType.getIsPaid();
					break;
				}
			}
			BrngUsrLogin brngusrlogin = manager
					.createQuery("Select a From BrngUsrLogin a where a.brngUsrReg.id= '" + userId + "'",
							BrngUsrLogin.class)
					.getSingleResult();

			int userLoginId = brngusrlogin.getId();

			Query query = manager.createQuery(
					"UPDATE BrngOrder a SET a.brngLkpIsPaid.id =:isPaid,a.payTxId=:payTxId,a.brnglkppaytype.id=:payTypeId where a.id=:orderId and a.brngUsrLogin.id=:userLoginId");
			query.setParameter("isPaid", paidId);

			query.setParameter("orderId", Integer.parseInt(inputDetails.get("orderId")));
			query.setParameter("userLoginId", userLoginId);
			query.setParameter("payTxId", (inputDetails.get("payTxId")));
			query.setParameter("payTypeId", payTypeId);
			// query.setParameter("payTxtCharges",payuTxtCharges);
			query.executeUpdate();

			message = UserMessages.getUserMessagesNew("Success");

			inputDetails.put("response", "1");
			inputDetails.put("message", message);

		} catch (Exception e) {
			e.printStackTrace();
			message = UserMessages.getUserMessagesNew("E");
			inputDetails.put("response", "-1");
			inputDetails.put("message", message);
		}

		return response;
	}

	public float getNewPrice(float dist, float weight, BrngRateLkp brngTests) {

		try {
			List<BrngGeneralLkp> brnggenerallkup1 = getManager().createQuery(
					"select a from BrngGeneralLkp a where a.brngKey in ('0-5','6-10','11-15','16-20','21-25','26-40','41-999') ",
					BrngGeneralLkp.class).getResultList();
			float zerotofive = Float.parseFloat(brnggenerallkup1.get(0).getValue());
			float sixtoten = Float.parseFloat(brnggenerallkup1.get(1).getValue());
			float eltofiften = Float.parseFloat(brnggenerallkup1.get(2).getValue());
			float sixttotwen = Float.parseFloat(brnggenerallkup1.get(3).getValue());
			float twentototwef = Float.parseFloat(brnggenerallkup1.get(4).getValue());
			float twenstofort = Float.parseFloat(brnggenerallkup1.get(5).getValue());
			float forotoall = Float.parseFloat(brnggenerallkup1.get(6).getValue());

			Date date = new Date();
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			int hours = calendar.get(Calendar.HOUR_OF_DAY);
			int surChargeHour = 0;
			int surchargeValue = 0;
			List<BrngGeneralLkp> brnggenerallkup = getManager().createQuery(
					"select a from BrngGeneralLkp a where a.brngKey in ('surcharge-time','surcharge-value') order by a.brngKey ",
					BrngGeneralLkp.class).getResultList();

			surChargeHour = Integer.parseInt(brnggenerallkup.get(0).getValue());
			if (surChargeHour < hours) {
				surchargeValue = Integer.parseInt(brnggenerallkup.get(1).getValue());
			}
			float price;
			if (dist <= 5) {
				if (weight <= Float.parseFloat(brngTests.getInitialWeight())) {
					if (surchargeValue == 0) {
						return price = zerotofive;
					} else {
						return price = surchargeValue * zerotofive;
					}

				} else {
					weight = weight - Float.parseFloat(brngTests.getInitialWeight());
					if (surchargeValue == 0) {
						return price = zerotofive
								+ (weight * Float.parseFloat(brngTests.getRatePerKg()));
					} else {
						return price = surchargeValue * zerotofive
								+ (weight * Float.parseFloat(brngTests.getRatePerKg()));
					}

				}
			} else if (dist > 5 && dist <= 10) {
				if (weight <= Float.parseFloat(brngTests.getInitialWeight())) {

					if (surchargeValue == 0) {
						return price = zerotofive + sixtoten;
					} else {
						return price = surchargeValue * (zerotofive + sixtoten);
					}
				} else {
					dist = dist - Float.parseFloat(brngTests.getInitialKms());
					weight = weight - Float.parseFloat(brngTests.getInitialWeight());
					if (surchargeValue == 0) {
						return price = zerotofive + sixtoten +
								+ (weight * Float.parseFloat(brngTests.getRatePerKg()));
					} else {
						return price = surchargeValue * (zerotofive + sixtoten)
								+ (weight * Float.parseFloat(brngTests.getRatePerKg()));
					}

				}
			} else if (dist > 10 && dist <= 15) {
				float tempDistance = dist - 10;
				if (weight <= Float.parseFloat(brngTests.getInitialWeight())) {

					if (surchargeValue == 0) {
						return price = zerotofive + sixtoten + (tempDistance * eltofiften);
					} else {
						return price = surchargeValue * (zerotofive + sixtoten + (tempDistance * eltofiften));
					}
				} else {
					weight = weight - Float.parseFloat(brngTests.getInitialWeight());
					if (surchargeValue == 0) {
						return price = zerotofive + sixtoten + (tempDistance * eltofiften)
								+ (weight * Float.parseFloat(brngTests.getRatePerKg()));
					} else {
						return price = surchargeValue * (zerotofive + sixtoten + (tempDistance * eltofiften))
								+ (weight * Float.parseFloat(brngTests.getRatePerKg()));
					}

				}
			} else if (dist > 15 && dist <= 20) {
				float tempDistance = dist - 15;
				if (weight <= Float.parseFloat(brngTests.getInitialWeight())) {

					if (surchargeValue == 0) {
						return price = zerotofive + sixtoten + (5 * eltofiften) + (tempDistance * sixttotwen);
					} else {
						return price = surchargeValue*(
								zerotofive + sixtoten + (5 * eltofiften) + (tempDistance * sixttotwen));
					}
				} else {
					weight = weight - Float.parseFloat(brngTests.getInitialWeight());
					if (surchargeValue == 0) {
						return price = zerotofive + sixtoten + (5 * eltofiften) + (tempDistance * sixttotwen)
								+ (weight * Float.parseFloat(brngTests.getRatePerKg()));
					} else {
						return price = surchargeValue*(
								(zerotofive + sixtoten + (5 * eltofiften) + (tempDistance * sixttotwen))
										+ (weight * Float.parseFloat(brngTests.getRatePerKg())));
					}

				}
				}
				  else if (dist > 20 && dist <= 25) {
				float tempDistance = dist - 20;
				if (weight <= Float.parseFloat(brngTests.getInitialWeight())) {

					if (surchargeValue == 0) {
						return price = zerotofive + sixtoten + (5 * eltofiften) + 5 * sixttotwen
								+ (tempDistance * twentototwef);
					} else {
						return price = surchargeValue * (zerotofive + sixtoten + (5 * eltofiften) + 5 * sixttotwen
								+ (tempDistance * twentototwef));
					}
				} else {
					weight = weight - Float.parseFloat(brngTests.getInitialWeight());
					if (surchargeValue == 0) {
						return price = zerotofive + sixtoten + (5 * eltofiften) + 5 * sixttotwen
								+ (tempDistance * twentototwef) 
								+ (weight * Float.parseFloat(brngTests.getRatePerKg()));
					} else {
						return price = surchargeValue
								* ((zerotofive + sixtoten + (5 * eltofiften) + 5 * sixttotwen
										+ (tempDistance * twentototwef))
								+ (weight * Float.parseFloat(brngTests.getRatePerKg())));
					}

				}}
			else if (dist > 25 && dist <= 40) {
				float tempDistance = dist - 25;
				if (weight <= Float.parseFloat(brngTests.getInitialWeight())) {

					if (surchargeValue == 0) {
						return price = zerotofive + sixtoten + (5 * eltofiften) + + 5 * sixttotwen + 5* twentototwef
								+ (tempDistance * twenstofort);
					} else {
						return price = surchargeValue * (zerotofive + sixtoten + (5 * eltofiften) + 5 * sixttotwen + 5 * twentototwef
								+ (tempDistance * twenstofort));
					}
				} else {
					weight = weight - Float.parseFloat(brngTests.getInitialWeight());
					if (surchargeValue == 0) {
						return price = zerotofive + sixtoten + (5 * eltofiften) + 5 * sixttotwen + 5 * twentototwef
								+ (tempDistance * twenstofort) 
								+ (weight * Float.parseFloat(brngTests.getRatePerKg()));
					} else {
						return price = surchargeValue
								* ((zerotofive + sixtoten + (5 * eltofiften) + 5 * sixttotwen + 5 * twentototwef
										+ (tempDistance * twenstofort))
								+ (weight * Float.parseFloat(brngTests.getRatePerKg())));
					}

				}}
			else if(dist >40) {
				float tempDistance = dist - 40;
				if (weight <= Float.parseFloat(brngTests.getInitialWeight())) {
					
					if(surchargeValue==0)
					{
					return price =  zerotofive + sixtoten + (5*eltofiften)+ 5 * sixttotwen +5*twentototwef+15*twenstofort+(tempDistance* forotoall);
					}
					else
					{
					return price = surchargeValue*( zerotofive + sixtoten + (5*eltofiften) + 5 * sixttotwen +5*twentototwef+15*twenstofort+(tempDistance* forotoall)) ;
					}
					} else {
					weight = weight -  Float.parseFloat(brngTests.getInitialWeight());
					if(surchargeValue==0)
					{
						return price =  zerotofive + sixtoten + (5*eltofiften)+ 5 * sixttotwen +5*twentototwef+15*twenstofort +(tempDistance* forotoall)  + (weight * Float.parseFloat(brngTests.getRatePerKg()));	
					}
					else
					{
						return price = surchargeValue*
								(( zerotofive + sixtoten + (5*eltofiften)+ 5 * sixttotwen +5*twentototwef+15*twenstofort +(tempDistance* forotoall)) + (weight * Float.parseFloat(brngTests.getRatePerKg())));
					}
					
					}
					}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;

	}
	
	public float getPriceByCity(float dist, float weight, BrngRateLkp brngTests, String city) {

		try {
			List<BrngGeneralLkp> brnggenerallkup1 = getManager().createQuery(
					"select a from BrngGeneralLkp a where a.brngKey in ('0-5"+city+"','6-10"+city+"','11-15"+city+"','16-20"+city+"','21-25"+city+"','26-40"+city+"','41-999"+city+"') ",
					BrngGeneralLkp.class).getResultList();
			float zerotofive = Float.parseFloat(brnggenerallkup1.get(0).getValue());
			float sixtoten = Float.parseFloat(brnggenerallkup1.get(1).getValue());
			float eltofiften = Float.parseFloat(brnggenerallkup1.get(2).getValue());
			float sixttotwen = Float.parseFloat(brnggenerallkup1.get(3).getValue());
			float twentototwef = Float.parseFloat(brnggenerallkup1.get(4).getValue());
			float twenstofort = Float.parseFloat(brnggenerallkup1.get(5).getValue());
			float forotoall = Float.parseFloat(brnggenerallkup1.get(6).getValue());

			Date date = new Date();
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			int hours = calendar.get(Calendar.HOUR_OF_DAY);
			int surChargeHour = 0;
			int surchargeValue = 0;
			List<BrngGeneralLkp> brnggenerallkup = getManager().createQuery(
					"select a from BrngGeneralLkp a where a.brngKey in ('surcharge-time','surcharge-value') order by a.brngKey ",
					BrngGeneralLkp.class).getResultList();

			surChargeHour = Integer.parseInt(brnggenerallkup.get(0).getValue());
			if (surChargeHour < hours) {
				surchargeValue = Integer.parseInt(brnggenerallkup.get(1).getValue());
			}
			float price;
			if (dist <= 5) {
				if (weight <= Float.parseFloat(brngTests.getInitialWeight())) {
					if (surchargeValue == 0) {
						return price = zerotofive;
					} else {
						return price = surchargeValue * zerotofive;
					}

				} else {
					weight = weight - Float.parseFloat(brngTests.getInitialWeight());
					if (surchargeValue == 0) {
						return price = zerotofive
								+ (weight * Float.parseFloat(brngTests.getRatePerKg()));
					} else {
						return price = surchargeValue * zerotofive
								+ (weight * Float.parseFloat(brngTests.getRatePerKg()));
					}

				}
			} else if (dist > 5 && dist <= 10) {
				if (weight <= Float.parseFloat(brngTests.getInitialWeight())) {

					if (surchargeValue == 0) {
						return price = zerotofive + sixtoten;
					} else {
						return price = surchargeValue * (zerotofive + sixtoten);
					}
				} else {
					dist = dist - Float.parseFloat(brngTests.getInitialKms());
					weight = weight - Float.parseFloat(brngTests.getInitialWeight());
					if (surchargeValue == 0) {
						return price = zerotofive + sixtoten +
								+ (weight * Float.parseFloat(brngTests.getRatePerKg()));
					} else {
						return price = surchargeValue * (zerotofive + sixtoten)
								+ (weight * Float.parseFloat(brngTests.getRatePerKg()));
					}

				}
			} else if (dist > 10 && dist <= 15) {
				float tempDistance = dist - 10;
				if (weight <= Float.parseFloat(brngTests.getInitialWeight())) {

					if (surchargeValue == 0) {
						return price = zerotofive + sixtoten + (tempDistance * eltofiften);
					} else {
						return price = surchargeValue * (zerotofive + sixtoten + (tempDistance * eltofiften));
					}
				} else {
					weight = weight - Float.parseFloat(brngTests.getInitialWeight());
					if (surchargeValue == 0) {
						return price = zerotofive + sixtoten + (tempDistance * eltofiften)
								+ (weight * Float.parseFloat(brngTests.getRatePerKg()));
					} else {
						return price = surchargeValue * (zerotofive + sixtoten + (tempDistance * eltofiften))
								+ (weight * Float.parseFloat(brngTests.getRatePerKg()));
					}

				}
			} else if (dist > 15 && dist <= 20) {
				float tempDistance = dist - 15;
				if (weight <= Float.parseFloat(brngTests.getInitialWeight())) {

					if (surchargeValue == 0) {
						return price = zerotofive + sixtoten + (5 * eltofiften) + (tempDistance * sixttotwen);
					} else {
						return price = surchargeValue*(
								zerotofive + sixtoten + (5 * eltofiften) + (tempDistance * sixttotwen));
					}
				} else {
					weight = weight - Float.parseFloat(brngTests.getInitialWeight());
					if (surchargeValue == 0) {
						return price = zerotofive + sixtoten + (5 * eltofiften) + (tempDistance * sixttotwen)
								+ (weight * Float.parseFloat(brngTests.getRatePerKg()));
					} else {
						return price = surchargeValue*(
								(zerotofive + sixtoten + (5 * eltofiften) + (tempDistance * sixttotwen))
										+ (weight * Float.parseFloat(brngTests.getRatePerKg())));
					}

				}
				}
				  else if (dist > 20 && dist <= 25) {
				float tempDistance = dist - 20;
				if (weight <= Float.parseFloat(brngTests.getInitialWeight())) {

					if (surchargeValue == 0) {
						return price = zerotofive + sixtoten + (5 * eltofiften) + 5 * sixttotwen
								+ (tempDistance * twentototwef);
					} else {
						return price = surchargeValue * (zerotofive + sixtoten + (5 * eltofiften) + 5 * sixttotwen
								+ (tempDistance * twentototwef));
					}
				} else {
					weight = weight - Float.parseFloat(brngTests.getInitialWeight());
					if (surchargeValue == 0) {
						return price = zerotofive + sixtoten + (5 * eltofiften) + 5 * sixttotwen
								+ (tempDistance * twentototwef) 
								+ (weight * Float.parseFloat(brngTests.getRatePerKg()));
					} else {
						return price = surchargeValue
								* ((zerotofive + sixtoten + (5 * eltofiften) + 5 * sixttotwen
										+ (tempDistance * twentototwef))
								+ (weight * Float.parseFloat(brngTests.getRatePerKg())));
					}

				}}
			else if (dist > 25 && dist <= 40) {
				float tempDistance = dist - 25;
				if (weight <= Float.parseFloat(brngTests.getInitialWeight())) {

					if (surchargeValue == 0) {
						return price = zerotofive + sixtoten + (5 * eltofiften) + + 5 * sixttotwen + 5* twentototwef
								+ (tempDistance * twenstofort);
					} else {
						return price = surchargeValue * (zerotofive + sixtoten + (5 * eltofiften) + 5 * sixttotwen + 5 * twentototwef
								+ (tempDistance * twenstofort));
					}
				} else {
					weight = weight - Float.parseFloat(brngTests.getInitialWeight());
					if (surchargeValue == 0) {
						return price = zerotofive + sixtoten + (5 * eltofiften) + 5 * sixttotwen + 5 * twentototwef
								+ (tempDistance * twenstofort) 
								+ (weight * Float.parseFloat(brngTests.getRatePerKg()));
					} else {
						return price = surchargeValue
								* ((zerotofive + sixtoten + (5 * eltofiften) + 5 * sixttotwen + 5 * twentototwef
										+ (tempDistance * twenstofort))
								+ (weight * Float.parseFloat(brngTests.getRatePerKg())));
					}

				}}
			else if(dist >40) {
				float tempDistance = dist - 40;
				if (weight <= Float.parseFloat(brngTests.getInitialWeight())) {
					
					if(surchargeValue==0)
					{
					return price =  zerotofive + sixtoten + (5*eltofiften)+ 5 * sixttotwen +5*twentototwef+15*twenstofort+(tempDistance* forotoall);
					}
					else
					{
					return price = surchargeValue*( zerotofive + sixtoten + (5*eltofiften) + 5 * sixttotwen +5*twentototwef+15*twenstofort+(tempDistance* forotoall)) ;
					}
					} else {
					weight = weight -  Float.parseFloat(brngTests.getInitialWeight());
					if(surchargeValue==0)
					{
						return price =  zerotofive + sixtoten + (5*eltofiften)+ 5 * sixttotwen +5*twentototwef+15*twenstofort +(tempDistance* forotoall)  + (weight * Float.parseFloat(brngTests.getRatePerKg()));	
					}
					else
					{
						return price = surchargeValue*
								(( zerotofive + sixtoten + (5*eltofiften)+ 5 * sixttotwen +5*twentototwef+15*twenstofort +(tempDistance* forotoall)) + (weight * Float.parseFloat(brngTests.getRatePerKg())));
					}
					
					}
					}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;

	}

	
	public float getPricePerPerson(float dist, float weight, BrngRateLkp brngTests, String phoneNumber) {

		try {
			List<BrngPerPersonPricing> brngPerPersonPricing = getManager().createQuery(
					"select a from BrngPerPersonPricing a where a.kms in ('0-5','6-10','11-15','16-20','21-25','26-40','41-999') and a.phoneNumber ='"+phoneNumber+"'",
					BrngPerPersonPricing.class).getResultList();
			float zerotofive = Float.parseFloat(brngPerPersonPricing.get(0).getValue());
			float sixtoten = Float.parseFloat(brngPerPersonPricing.get(1).getValue());
			float eltofiften = Float.parseFloat(brngPerPersonPricing.get(2).getValue());
			float sixttotwen = Float.parseFloat(brngPerPersonPricing.get(3).getValue());
			float twentototwef = Float.parseFloat(brngPerPersonPricing.get(4).getValue());
			float twenstofort = Float.parseFloat(brngPerPersonPricing.get(5).getValue());
			float forotoall = Float.parseFloat(brngPerPersonPricing.get(6).getValue());

			Date date = new Date();
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			int hours = calendar.get(Calendar.HOUR_OF_DAY);
			int surChargeHour = 0;
			int surchargeValue = 0;
			List<BrngGeneralLkp> brnggenerallkup = getManager().createQuery(
					"select a from BrngGeneralLkp a where a.brngKey in ('surcharge-time','surcharge-value') order by a.brngKey ",
					BrngGeneralLkp.class).getResultList();

			surChargeHour = Integer.parseInt(brnggenerallkup.get(0).getValue());
			if (surChargeHour < hours) {
				surchargeValue = Integer.parseInt(brnggenerallkup.get(1).getValue());
			}
			float price;
			if (dist <= 5) {
				if (weight <= Float.parseFloat(brngTests.getInitialWeight())) {
					if (surchargeValue == 0) {
						return price = zerotofive;
					} else {
						return price = surchargeValue * zerotofive;
					}

				} else {
					weight = weight - Float.parseFloat(brngTests.getInitialWeight());
					if (surchargeValue == 0) {
						return price = zerotofive
								+ (weight * Float.parseFloat(brngTests.getRatePerKg()));
					} else {
						return price = surchargeValue * zerotofive
								+ (weight * Float.parseFloat(brngTests.getRatePerKg()));
					}

				}
			} else if (dist > 5 && dist <= 10) {
				if (weight <= Float.parseFloat(brngTests.getInitialWeight())) {

					if (surchargeValue == 0) {
						return price = zerotofive + sixtoten;
					} else {
						return price = surchargeValue * (zerotofive + sixtoten);
					}
				} else {
					dist = dist - Float.parseFloat(brngTests.getInitialKms());
					weight = weight - Float.parseFloat(brngTests.getInitialWeight());
					if (surchargeValue == 0) {
						return price = zerotofive + sixtoten +
								+ (weight * Float.parseFloat(brngTests.getRatePerKg()));
					} else {
						return price = surchargeValue * (zerotofive + sixtoten)
								+ (weight * Float.parseFloat(brngTests.getRatePerKg()));
					}

				}
			} else if (dist > 10 && dist <= 15) {
				float tempDistance = dist - 10;
				if (weight <= Float.parseFloat(brngTests.getInitialWeight())) {

					if (surchargeValue == 0) {
						return price = zerotofive + sixtoten + (tempDistance * eltofiften);
					} else {
						return price = surchargeValue * (zerotofive + sixtoten + (tempDistance * eltofiften));
					}
				} else {
					weight = weight - Float.parseFloat(brngTests.getInitialWeight());
					if (surchargeValue == 0) {
						return price = zerotofive + sixtoten + (tempDistance * eltofiften)
								+ (weight * Float.parseFloat(brngTests.getRatePerKg()));
					} else {
						return price = surchargeValue * (zerotofive + sixtoten + (tempDistance * eltofiften))
								+ (weight * Float.parseFloat(brngTests.getRatePerKg()));
					}

				}
			} else if (dist > 15 && dist <= 20) {
				float tempDistance = dist - 15;
				if (weight <= Float.parseFloat(brngTests.getInitialWeight())) {

					if (surchargeValue == 0) {
						return price = zerotofive + sixtoten + (5 * eltofiften) + (tempDistance * sixttotwen);
					} else {
						return price = surchargeValue*(
								zerotofive + sixtoten + (5 * eltofiften) + (tempDistance * sixttotwen));
					}
				} else {
					weight = weight - Float.parseFloat(brngTests.getInitialWeight());
					if (surchargeValue == 0) {
						return price = zerotofive + sixtoten + (5 * eltofiften) + (tempDistance * sixttotwen)
								+ (weight * Float.parseFloat(brngTests.getRatePerKg()));
					} else {
						return price = surchargeValue*(
								(zerotofive + sixtoten + (5 * eltofiften) + (tempDistance * sixttotwen))
										+ (weight * Float.parseFloat(brngTests.getRatePerKg())));
					}

				}
				}
				  else if (dist > 20 && dist <= 25) {
				float tempDistance = dist - 20;
				if (weight <= Float.parseFloat(brngTests.getInitialWeight())) {

					if (surchargeValue == 0) {
						return price = zerotofive + sixtoten + (5 * eltofiften) + 5 * sixttotwen
								+ (tempDistance * twentototwef);
					} else {
						return price = surchargeValue * (zerotofive + sixtoten + (5 * eltofiften) + 5 * sixttotwen
								+ (tempDistance * twentototwef));
					}
				} else {
					weight = weight - Float.parseFloat(brngTests.getInitialWeight());
					if (surchargeValue == 0) {
						return price = zerotofive + sixtoten + (5 * eltofiften) + 5 * sixttotwen
								+ (tempDistance * twentototwef) 
								+ (weight * Float.parseFloat(brngTests.getRatePerKg()));
					} else {
						return price = surchargeValue
								* ((zerotofive + sixtoten + (5 * eltofiften) + 5 * sixttotwen
										+ (tempDistance * twentototwef))
								+ (weight * Float.parseFloat(brngTests.getRatePerKg())));
					}

				}}
			else if (dist > 25 && dist <= 40) {
				float tempDistance = dist - 25;
				if (weight <= Float.parseFloat(brngTests.getInitialWeight())) {

					if (surchargeValue == 0) {
						return price = zerotofive + sixtoten + (5 * eltofiften) + + 5 * sixttotwen + 5* twentototwef
								+ (tempDistance * twenstofort);
					} else {
						return price = surchargeValue * (zerotofive + sixtoten + (5 * eltofiften) + 5 * sixttotwen + 5 * twentototwef
								+ (tempDistance * twenstofort));
					}
				} else {
					weight = weight - Float.parseFloat(brngTests.getInitialWeight());
					if (surchargeValue == 0) {
						return price = zerotofive + sixtoten + (5 * eltofiften) + 5 * sixttotwen + 5 * twentototwef
								+ (tempDistance * twenstofort) 
								+ (weight * Float.parseFloat(brngTests.getRatePerKg()));
					} else {
						return price = surchargeValue
								* ((zerotofive + sixtoten + (5 * eltofiften) + 5 * sixttotwen + 5 * twentototwef
										+ (tempDistance * twenstofort))
								+ (weight * Float.parseFloat(brngTests.getRatePerKg())));
					}

				}}
			else if(dist >40) {
				float tempDistance = dist - 40;
				if (weight <= Float.parseFloat(brngTests.getInitialWeight())) {
					
					if(surchargeValue==0)
					{
					return price =  zerotofive + sixtoten + (5*eltofiften)+ 5 * sixttotwen +5*twentototwef+15*twenstofort+(tempDistance* forotoall);
					}
					else
					{
					return price = surchargeValue*( zerotofive + sixtoten + (5*eltofiften) + 5 * sixttotwen +5*twentototwef+15*twenstofort+(tempDistance* forotoall)) ;
					}
					} else {
					weight = weight -  Float.parseFloat(brngTests.getInitialWeight());
					if(surchargeValue==0)
					{
						return price =  zerotofive + sixtoten + (5*eltofiften)+ 5 * sixttotwen +5*twentototwef+15*twenstofort +(tempDistance* forotoall)  + (weight * Float.parseFloat(brngTests.getRatePerKg()));	
					}
					else
					{
						return price = surchargeValue*
								(( zerotofive + sixtoten + (5*eltofiften)+ 5 * sixttotwen +5*twentototwef+15*twenstofort +(tempDistance* forotoall)) + (weight * Float.parseFloat(brngTests.getRatePerKg())));
					}
					
					}
					}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;

	}
	
	
	public HashMap<String, String> activateDeliveryBoy(String email) {
		
		try {
		Query query = manager.createQuery(
				"UPDATE BrngUsrReg a SET a.brnglkpservicemanvalidated.id =:isValidated where a.emailId=:emailId ");
		query.setParameter("isValidated", 1);

		query.setParameter("emailId", email);
		// query.setParameter("payTxtCharges",payuTxtCharges);
		query.executeUpdate();
		HashMap<String, String> versions = new HashMap<String, String>();
		versions.put("message", "Success");
		return versions;
		}
		catch(Exception e) {
			HashMap<String, String> versions = new HashMap<String, String>();
			versions.put("message", "Error");
			return versions;
			
		}
	}


}
