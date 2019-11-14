package com.simens.us.myapplication.Utils.db;

import android.provider.BaseColumns;

/**
 * DB?�使?�れ?�マ?�タ?�ー??br>
 * DB?�と?�ー?�ル?�な?�を決め??br>
 */
public final class KumaDBConstants {

	private KumaDBConstants() {
	};

	/** DB??��??��?�名 */
	public static final String DB_SCHEME_NAME = "CUSTOMER_KUMA";

	// -----------------------------------------------------------------------

	/** 주 고객 Table */
	public static final String TABLE_CUSTOMER_MAIN = "MAIN_CUSTOMER_INFORMATION";
	public static final String COLUMN_ID = BaseColumns._ID;
	public static final String CUSTOMER_MAIN_NAME = "NAME";
	public static final String CUSTOMER_MAIN_RELATION = "RELATION";
	public static final String CUSTOMER_MAIN_ID = "MAIN_ID";
	public static final String CUSTOMER_MAIN_BIRTHDAY = "BIRTHDAY";
	public static final String CUSTOMER_MAIN_BIRTHDAY_TYPE = "BIRTHDAY_TYPE";
	public static final String CUSTOMER_MAIN_WEDDING = "WEDDING";
//	public static final String CUSTOMER_MAIN_CONTRACT = "CONTRACT";
	public static final String CUSTOMER_MAIN_COMPANY = "COMPANY";
	public static final String CUSTOMER_MAIN_POSITION = "POSITION";
	public static final String CUSTOMER_MAIN_HOUSE_NUMBER = "HOUSE_NUMBER";
	public static final String CUSTOMER_MAIN_COMPANY_NUMBER = "COMPANY_NUMBER";
	public static final String CUSTOMER_MAIN_PHONE_NUMBER = "PHONE_NUMBER";
	public static final String CUSTOMER_MAIN_FAX_NUMBER = "FAX_NUMBER";
	public static final String CUSTOMER_MAIN_HOUSE_POSTCODE = "HOUSE_POSTCODE";
	public static final String CUSTOMER_MAIN_HOUSE_ADDRESS = "HOUSE_ADDRESS";
	public static final String CUSTOMER_MAIN_COMPANY_POSTCODE = "COMPANY_POSTCODE";
	public static final String CUSTOMER_MAIN_COMPANY_ADDRESS = "COMPANY_ADDRESS";
	public static final String CUSTOMER_MAIN_GROUP = "CUSTOMER_GROUP";
	
	// -----------------------------------------------------------------------

	/** 주 고객 주변인 Table */
	public static final String TABLE_CUSTOMER_SUB = "SUB_CUSTOMER_INFORMATION";
	public static final String SUB_COLUMN_ID = BaseColumns._ID;
	public static final String CUSTOMER_SUB_MAIN_CUSTOMER = "MAIN_CUSTOMER";
	public static final String CUSTOMER_SUB_NAME = "NAME";
	public static final String CUSTOMER_SUB_RELATION = "RELATION";
	public static final String CUSTOMER_SUB_ID = "MAIN_ID";
	public static final String CUSTOMER_SUB_BIRTHDAY = "BIRTHDAY";
	public static final String CUSTOMER_SUB_BIRTHDAY_TYPE = "BIRTHDAY_TYPE";
	public static final String CUSTOMER_SUB_WEDDING = "WEDDING";
//	public static final String CUSTOMER_SUB_CONTRACT = "CONTRACT";
	public static final String CUSTOMER_SUB_COMPANY = "COMPANY";
	public static final String CUSTOMER_SUB_POSITION = "POSITION";
	public static final String CUSTOMER_SUB_HOUSE_NUMBER = "HOUSE_NUMBER";
	public static final String CUSTOMER_SUB_COMPANY_NUMBER = "COMPANY_NUMBER";
	public static final String CUSTOMER_SUB_PHONE_NUMBER = "PHONE_NUMBER";
	public static final String CUSTOMER_SUB_FAX_NUMBER = "FAX_NUMBER";
	public static final String CUSTOMER_SUB_HOUSE_POSTCODE = "HOUSE_POSTCODE";
	public static final String CUSTOMER_SUB_HOUSE_ADDRESS = "HOUSE_ADDRESS";
	public static final String CUSTOMER_SUB_COMPANY_POSTCODE = "COMPANY_POSTCODE";
	public static final String CUSTOMER_SUB_COMPANY_ADDRESS = "COMPANY_ADDRESS";
	
	// -----------------------------------------------------------------------

	/** 계약내용 Table */
	public static final String TABLE_CUSTOMER_CONTRACT = "CUSTOMER_CONTRACT";
	public static final String CONTRACT_COLUMN_ID = BaseColumns._ID;
	public static final String CUSTOMER_CONTRACT_MAIN_CUSTOMER = "MAIN_CUSTOMER";
	public static final String CUSTOMER_CONTRACT_NAME = "CONTRACT_NAME";
	public static final String CUSTOMER_CONTRACT_CONTRACTOR = "CONTRACT_CONTRACTOR";
	public static final String CUSTOMER_CONTRACT_INSURANT = "CONTRACT_INSURANT";
	public static final String CUSTOMER_CONTRACT_EVENT = "CONTRACT_EVENT";
	public static final String CUSTOMER_CONTRACT_DATE = "CONTRACT_DATE";
	public static final String CUSTOMER_CONTRACT_MONEY = "CONTRACT_MONEY";
	public static final String CUSTOMER_CONTRACT_SPENDING_TERM = "CONTRACT_SPENDING_TERM";
	public static final String CUSTOMER_CONTRACT_SPENDING_EXPIRY = "CONTRACT_SPENDING_EXPIRY";
	public static final String CUSTOMER_CONTRACT_EXPIRY = "CONTRACT_EXPIRY";
	
	// -----------------------------------------------------------------------

	/** 이벤트  Table */
	public static final String TABLE_CUSTOMER_EVENT = "CUSTOMER_EVENT";
	public static final String EVENT_COLUMN_ID = BaseColumns._ID;
	public static final String CUSTOMER_EVENT_MAIN_CUSTOMER = "MAIN_CUSTOMER";
	public static final String CUSTOMER_EVENT_NAME 			= "EVENT_NAME";
	public static final String CUSTOMER_EVENT_TYPE	= "EVENT_TYPE";
	public static final String CUSTOMER_EVENT_BIRTHDAY	= "EVENT_BIRTHDAY";
//	public static final String CUSTOMER_EVENT_NAME_TYPE	= "EVENT_TYPE";
	public static final String CUSTOMER_EVENT_DATE = "EVENT_DATE";
	public static final String CUSTOMER_EVENT_MEMO = "EVENT_MEMO";
	
	// -----------------------------------------------------------------------
	
	/** 접촉이력 Table */
	public static final String TABLE_CONTACT_LIST = "CONTACT_LIST";
	public static final String CONTACT_COLUMN_ID = BaseColumns._ID;
	public static final String CUSTOMER_CONTACT_MAIN_CUSTOMER = "MAIN_CUSTOMER";
	public static final String CUSTOMER_CONTACT_CONTENTS = "CONTACT_CONTENTS";
	public static final String CUSTOMER_CONTACT_DATE = "CONTACT_DATE";
	public static final String CUSTOMER_CONTACT_ACTION = "CONTACT_ACTION";
	public static final String CUSTOMER_CONTACT_CANCLE = "CONTACT_CANCLE";
	public static final String CUSTOMER_CONTACT_DELAY = "CONTACT_DELAY";
	public static final String CUSTOMER_CONTACT_DELAY_DATE = "CONTACT_DELAY_DATE";
	public static final String CUSTOMER_CONTACT_CONTRACT_COUNT = "CONTACT_CONTRACT_COUNT";
	public static final String CUSTOMER_CONTACT_CONTRACT_MONEY = "CONTACT_CONTRACT_MONEY";
	public static final String CUSTOMER_CONTACT_TOTAL = "CONTACT_TOTAL";
	public static final String CUSTOMER_CONTACT_MEMO = "CONTACT_MEMO";
	public static final String CUSTOMER_CONTACT_VOICE_MEMO = "CONTACT_VOICE_MEMO";
	
	// -----------------------------------------------------------------------

	/** 주 고객 Table */
	public static final String SQL_CREATE_TABLE_CUSTOMER_MAIN = KumaDBConstants
			.replaceString("CREATE TABLE ##### ( "
					+ "##### INTEGER PRIMARY KEY AUTOINCREMENT "
					+ ", ##### TEXT "+ ", ##### TEXT "
					+ ", ##### TEXT "+ ", ##### TEXT "
					+ ", ##### TEXT "+ ", ##### TEXT "
					+ ", ##### TEXT " 
					+ ", ##### TEXT "+ ", ##### TEXT "
					+ ", ##### TEXT "+ ", ##### TEXT "
					+ ", ##### TEXT "+ ", ##### TEXT "
					+ ", ##### TEXT "+ ", ##### TEXT "
					+ ", ##### TEXT "+ ", ##### TEXT )", TABLE_CUSTOMER_MAIN, COLUMN_ID,
					CUSTOMER_MAIN_NAME, CUSTOMER_MAIN_RELATION
					,CUSTOMER_MAIN_ID, CUSTOMER_MAIN_BIRTHDAY
					,CUSTOMER_MAIN_BIRTHDAY_TYPE, CUSTOMER_MAIN_WEDDING
					,CUSTOMER_MAIN_COMPANY
					,CUSTOMER_MAIN_POSITION, CUSTOMER_MAIN_HOUSE_NUMBER
					,CUSTOMER_MAIN_COMPANY_NUMBER, CUSTOMER_MAIN_PHONE_NUMBER
					,CUSTOMER_MAIN_FAX_NUMBER, CUSTOMER_MAIN_HOUSE_POSTCODE
					,CUSTOMER_MAIN_HOUSE_ADDRESS, CUSTOMER_MAIN_COMPANY_POSTCODE
					,CUSTOMER_MAIN_COMPANY_ADDRESS, CUSTOMER_MAIN_GROUP);
	// -----------------------------------------------------------------------

	/** 주 고객 주변인 Table */
	public static final String SQL_CREATE_TABLE_CUSTOMER_SUB = KumaDBConstants
			.replaceString("CREATE TABLE ##### ( "
					+ "##### INTEGER PRIMARY KEY AUTOINCREMENT "
					+ ", ##### TEXT "+ ", ##### TEXT "
					+ ", ##### TEXT "+ ", ##### TEXT "
					+ ", ##### TEXT "+ ", ##### TEXT "
					+ ", ##### TEXT "+ ", ##### TEXT "
					+ ", ##### TEXT "+ ", ##### TEXT "
					+ ", ##### TEXT "
					+ ", ##### TEXT "+ ", ##### TEXT "
					+ ", ##### TEXT "+ ", ##### TEXT "
					+ ", ##### TEXT " + ", ##### TEXT )", TABLE_CUSTOMER_SUB, SUB_COLUMN_ID,
					CUSTOMER_SUB_MAIN_CUSTOMER,
					CUSTOMER_SUB_NAME, CUSTOMER_SUB_RELATION
					,CUSTOMER_SUB_ID, CUSTOMER_SUB_BIRTHDAY
					,CUSTOMER_SUB_BIRTHDAY_TYPE, CUSTOMER_SUB_WEDDING
					, CUSTOMER_SUB_COMPANY
					,CUSTOMER_SUB_POSITION, CUSTOMER_SUB_HOUSE_NUMBER
					,CUSTOMER_SUB_COMPANY_NUMBER, CUSTOMER_SUB_PHONE_NUMBER
					,CUSTOMER_SUB_FAX_NUMBER, CUSTOMER_SUB_HOUSE_POSTCODE
					,CUSTOMER_SUB_HOUSE_ADDRESS, CUSTOMER_SUB_COMPANY_POSTCODE
					,CUSTOMER_SUB_COMPANY_ADDRESS);
	
	
	// -----------------------------------------------------------------------

	/** 계약 내용 Table */
	public static final String SQL_CREATE_TABLE_CUSTOMER_CONTRACT = KumaDBConstants
			.replaceString("CREATE TABLE ##### ( "
					+ "##### INTEGER PRIMARY KEY AUTOINCREMENT "
					+ ", ##### TEXT "+ ", ##### TEXT "
					+ ", ##### TEXT "+ ", ##### TEXT "
					+ ", ##### TEXT "+ ", ##### TEXT "
					+ ", ##### TEXT "+ ", ##### TEXT "
					+ ", ##### TEXT "+ ", ##### TEXT )", TABLE_CUSTOMER_CONTRACT, CONTRACT_COLUMN_ID,
					CUSTOMER_CONTRACT_MAIN_CUSTOMER, CUSTOMER_CONTRACT_NAME
					,CUSTOMER_CONTRACT_CONTRACTOR, CUSTOMER_CONTRACT_INSURANT
					,CUSTOMER_CONTRACT_DATE, CUSTOMER_CONTRACT_MONEY
					,CUSTOMER_CONTRACT_SPENDING_TERM, CUSTOMER_CONTRACT_SPENDING_EXPIRY
					, CUSTOMER_CONTRACT_EXPIRY	, CUSTOMER_CONTRACT_EVENT);
	
	// -----------------------------------------------------------------------a

	/** 이벤트 Table */
	public static final String SQL_CREATE_TABLE_CUSTOMER_EVENT = KumaDBConstants
			.replaceString("CREATE TABLE ##### ( "
					+ "##### INTEGER PRIMARY KEY AUTOINCREMENT "
					+ ", ##### TEXT "+ ", ##### TEXT "+ ", ##### TEXT "
					+ ", ##### TEXT "+ ", ##### TEXT "+ ", ##### TEXT )", TABLE_CUSTOMER_EVENT, EVENT_COLUMN_ID,
					CUSTOMER_EVENT_MAIN_CUSTOMER, CUSTOMER_EVENT_NAME, CUSTOMER_EVENT_TYPE
					,CUSTOMER_EVENT_DATE, CUSTOMER_EVENT_MEMO, CUSTOMER_EVENT_BIRTHDAY);
	
	
	// -----------------------------------------------------------------------

	/** 접촉이력  Table */
	public static final String SQL_CREATE_TABLE_CONTACT_LIST = KumaDBConstants
			.replaceString("CREATE TABLE ##### ( "
					+ "##### INTEGER PRIMARY KEY AUTOINCREMENT "
					+ ", ##### TEXT "+ ", ##### TEXT "
					+ ", ##### TEXT "+ ", ##### TEXT "
					+ ", ##### TEXT "+ ", ##### TEXT "
					+ ", ##### TEXT "+ ", ##### TEXT "
					+ ", ##### TEXT "+ ", ##### TEXT "
					+ ", ##### TEXT "+ ", ##### TEXT )", TABLE_CONTACT_LIST, CONTACT_COLUMN_ID,
					CUSTOMER_CONTACT_MAIN_CUSTOMER, CUSTOMER_CONTACT_CONTENTS
					,CUSTOMER_CONTACT_DATE, CUSTOMER_CONTACT_ACTION
					,CUSTOMER_CONTACT_CANCLE, CUSTOMER_CONTACT_DELAY
					,CUSTOMER_CONTACT_DELAY_DATE, CUSTOMER_CONTACT_CONTRACT_COUNT
					,CUSTOMER_CONTACT_CONTRACT_MONEY, CUSTOMER_CONTACT_TOTAL
					,CUSTOMER_CONTACT_MEMO, CUSTOMER_CONTACT_VOICE_MEMO
					);

	
	// -----------------------------------------------------------------------
//	/** ���̵� ���� ���� ����Ʈ �� */
//	public static final String SQL_KOLON_LOGIN_SELECTION = KumaDBConstants
//			.replaceString("Select " + "#####" + ", #####" + ", #####" +" FROM ##### ",
//					COLUMN_ID, COLUMN_STATE, COLUMN_LOGINID, TABLE_KOLON_LOGIN);
//
////	// -----------------------------------------------------------------------
////
////	/** MAIN TIME */
////	public static final String SQL_KOLON_MAIN_TIME_SELECTION = KumaDBConstants
////			.replaceString("Select " + "#####" + ", #####" + ", #####" +" FROM ##### ",
////					MAIN_COLUMN_ID, MAIN_COLUMN_TIME, MAIN_COLUMN_STATE, TABLE_KOLON_TIME_MAIN);
//	
//	// -----------------------------------------------------------------------
//
//	/** POPTV TIME  */
//	public static final String SQL_KOLON_POPTV_TIME_SELECTION = KumaDBConstants
//			.replaceString("Select " + "#####" + ", #####" + ", #####" +" FROM ##### ",
//					POPTV_COLUMN_ID, POPTV_COLUMN_TIME, POPTV_COLUMN_STATE, TABLE_KOLON_TIME_POPTV);
//	
//	// -----------------------------------------------------------------------
//
//	/** COMICS SELECTION  */
//	public static final String SQL_KOLON_COMICS_TIME_SELECTION = KumaDBConstants
//			.replaceString("Select " + "#####" + ", #####" + ", #####" +" FROM ##### ",
//					COMICS_COLUMN_ID, COMICS_COLUMN_TIME, COMICS_COLUMN_STATE, TABLE_KOLON_TIME_COMICS);
//	
//	// -----------------------------------------------------------------------
//
//	/** COMICS SELECTION  */
//	public static final String SQL_KOLON_POPUP_CF_SELECTION = KumaDBConstants
//			.replaceString("Select " + "#####" + ", #####" + ", #####" +" FROM ##### ",
//					POPUP_COLUMN_ID, POPUP_COLUMN_TIME, POPUP_COLUMN_STATE, TABLE_POPUP_CF);
//	
//	// -----------------------------------------------------------------------
//
//	/** MANAGEMENT SELECTION  */
//	public static final String SQL_KOLON_MANAGEMENT_SELECTION = KumaDBConstants
//			.replaceString("Select " + "#####" + ", #####" +" FROM ##### ",
//					MANAGEMENT_COLUMN_ID, MANAGEMENT_COLUMN_STATE, TABLE_MANAGEMENT);
//
//	// -----------------------------------------------------------------------
//	
//	/** ���̵� ���� ������Ʈ */
////	public static final String SQL_KOLON_LOGIN_UPDATE = KumaDBConstants
////			.replaceString(
////					" update ##### " + "set " + "LOGIN_STATE = ##### "
////					 + ", LOGIN_ID = ##### " + "WHERE ##### = ##### ",
////					KumaDBConstants.TABLE_KOLON_LOGIN, "'%%%%%'", "'%%%%%'",
////					KumaDBConstants.COLUMN_ID, "'%%%%%'").replace("%%%%%",
////					"#####");
////
////	// -----------------------------------------------------------------------
////	/** MAIN ���� ������Ʈ */
////	public static final String SQL_KOLON_MAIN_TIME_UPDATE = KumaDBConstants
////			.replaceString(
////					" update ##### " + "set " + "NOW_TIME = ##### "
////					 + ", TIME_STATE = ##### " + "WHERE ##### = ##### ",
////					KumaDBConstants.TABLE_KOLON_TIME_MAIN, "'%%%%%'", "'%%%%%'",
////					KumaDBConstants.MAIN_COLUMN_ID, "'%%%%%'").replace("%%%%%",
////					"#####");
//	
//	// -----------------------------------------------------------------------
//	/** POPTV ���� ������Ʈ */
//	public static final String SQL_KOLON_POPTV_TIME_UPDATE = KumaDBConstants
//			.replaceString(
//					" update ##### " + "set " + "NOW_TIME = ##### "
//					 + ", TIME_STATE = ##### " + "WHERE ##### = ##### ",
//					KumaDBConstants.TABLE_KOLON_TIME_POPTV, "'%%%%%'", "'%%%%%'",
//					KumaDBConstants.POPTV_COLUMN_ID, "'%%%%%'").replace("%%%%%",
//					"#####");
//	
//	// -----------------------------------------------------------------------
//	/** COMICS ���� ������Ʈ */
//	public static final String SQL_KOLON_COMICS_TIME_UPDATE = KumaDBConstants
//			.replaceString(
//					" update ##### " + "set " + "NOW_TIME = ##### "
//					 + ", TIME_STATE = ##### " + "WHERE ##### = ##### ",
//					KumaDBConstants.TABLE_KOLON_TIME_COMICS, "'%%%%%'", "'%%%%%'",
//					KumaDBConstants.COMICS_COLUMN_ID, "'%%%%%'").replace("%%%%%",
//					"#####");
//	
//	// -----------------------------------------------------------------------
//	/** POPUP CF ���� ������Ʈ */
//	public static final String SQL_KOLON_POPUP_CF_UPDATE = KumaDBConstants
//			.replaceString(
//					" update ##### " + "set " + "NOW_DAY = ##### "
//					 + ", CHECK_STATE = ##### " + "WHERE ##### = ##### ",
//					KumaDBConstants.TABLE_POPUP_CF, "'%%%%%'", "'%%%%%'",
//					KumaDBConstants.POPUP_COLUMN_ID, "'%%%%%'").replace("%%%%%",
//					"#####");
//	
//	// -----------------------------------------------------------------------
//	
//	/** POPUP CF ���� ������Ʈ */
//	public static final String SQL_KOLON_MANAGEMNET_UPDATE = KumaDBConstants
//			.replaceString(
//					" update ##### " + "set " + "MANAGEMENT_CHECK_STATE = ##### "
//					  + "WHERE ##### = ##### ",
//					KumaDBConstants.TABLE_MANAGEMENT, "'%%%%%'",
//					KumaDBConstants.MANAGEMENT_COLUMN_ID, "'%%%%%'").replace("%%%%%",
//					"#####");

	
	// -----------------------------------------------------------------------
	/** �?��?�え?�字??*/
	public static final String PREDEFINED_REPLACE_STRING = "#####";

	/**
	 * 対象?�字?�の?�定?�分?�パ?�メ?�タ????�置?�す??br>
	 * 
	 * @param source
	 *            対象?�字??	 * @param args
	 *            �?��?�ラ?�ー??	 * @return �?��結果?�字??	 * @version 0.1
	 * @since 0.1
	 */

	public static String replaceString(String source, String... args) {
		int argLength = args.length;

		if (argLength > 0) {
			for (int i = 0; i < argLength; i++) {
				source = source.replaceFirst(
						KumaDBConstants.PREDEFINED_REPLACE_STRING, args[i]);
			}
		}
		;
//		KumaLog.LogW("tes_1"," source : " + source);
		return source;
	}
}
