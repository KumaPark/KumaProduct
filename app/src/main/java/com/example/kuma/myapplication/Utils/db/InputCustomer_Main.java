package com.example.kuma.myapplication.Utils.db;

import java.util.ArrayList;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class InputCustomer_Main{
	
	private KumaDBAdapter dba;
	private SQLiteDatabase sDB;
		
	public InputCustomer_Main( Context context ){
		try {
			dba = new KumaDBAdapter(context, null, true);
			sDB = dba.getmDatabase();
			dba.createTable();
		} catch (Exception e) {
			e.printStackTrace();
		}
 	}
 	
	/**
	 *  Insert
	 * */
	
	public String InsertCustomerMain( ArrayList<String> arrCustomer){
		String Main_customer_Id ="";
		try {
			String insert_sql = "INSERT INTO ##### "
					+ "(#####, #####,#####, #####,#####, #####,#####, #####" +
					",#####, #####,#####, #####,#####, #####,#####, #####, ##### ) VALUES " 
					+ "('#####', '#####', '#####', '#####','#####', '#####', '#####',  '#####'" +
					",'#####', '#####', '#####', '#####','#####', '#####', '#####', '#####' , '#####' )";
		 insert_sql = KumaDBConstants.replaceString(insert_sql,
						KumaDBConstants.TABLE_CUSTOMER_MAIN,
						
						KumaDBConstants.CUSTOMER_MAIN_NAME,
						KumaDBConstants.CUSTOMER_MAIN_RELATION,
						KumaDBConstants.CUSTOMER_MAIN_ID,
						KumaDBConstants.CUSTOMER_MAIN_BIRTHDAY,
						KumaDBConstants.CUSTOMER_MAIN_BIRTHDAY_TYPE,
						KumaDBConstants.CUSTOMER_MAIN_WEDDING,
						KumaDBConstants.CUSTOMER_MAIN_COMPANY,
						KumaDBConstants.CUSTOMER_MAIN_POSITION,
						KumaDBConstants.CUSTOMER_MAIN_HOUSE_NUMBER,
						KumaDBConstants.CUSTOMER_MAIN_COMPANY_NUMBER,
						KumaDBConstants.CUSTOMER_MAIN_PHONE_NUMBER,
						KumaDBConstants.CUSTOMER_MAIN_FAX_NUMBER,
						KumaDBConstants.CUSTOMER_MAIN_HOUSE_POSTCODE,
						KumaDBConstants.CUSTOMER_MAIN_HOUSE_ADDRESS,
						KumaDBConstants.CUSTOMER_MAIN_COMPANY_POSTCODE,
						KumaDBConstants.CUSTOMER_MAIN_COMPANY_ADDRESS,
						KumaDBConstants.CUSTOMER_MAIN_GROUP,
						arrCustomer.get(0),arrCustomer.get(1), arrCustomer.get(2),arrCustomer.get(3),
						arrCustomer.get(4), arrCustomer.get(5), arrCustomer.get(6), arrCustomer.get(7),
						arrCustomer.get(8), arrCustomer.get(9), arrCustomer.get(10), arrCustomer.get(11), 
						arrCustomer.get(12), arrCustomer.get(13), arrCustomer.get(14), arrCustomer.get(15),
						arrCustomer.get(16));
				
				try {
//					Log.w("test"," ================ insert_sql ================ \n"  + insert_sql);
					sDB.execSQL(insert_sql);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				
//				String select_str = KumaDBConstants.CUSTOMER_MAIN_NAME + "='박재성'";
		 		String bb = KumaDBConstants.CUSTOMER_MAIN_NAME + "='" + arrCustomer.get(0) + "' AND " +
		 				KumaDBConstants.CUSTOMER_MAIN_ID + "='" + arrCustomer.get(2)  + "'";
				String[][] aa = dba.selectColumn(KumaDBConstants.TABLE_CUSTOMER_MAIN, null, bb, null, null, null, null);

				if( aa.length - 1 > 0 ){
					for ( int j = 0; j < aa.length - 1; j++){
						for(int i = 0; i < aa[j].length; i++) {
							if( i == 0 ){
								Main_customer_Id = aa[j][0];
								break;
							}
						}
					}
				}
				
				InsertCustomer_Sub( Main_customer_Id , arrCustomer);
				
				
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return Main_customer_Id;
	}
	
	public void InsertCustomer_Sub(String Main_customer_Id, ArrayList<String> arrCustomer){
		 // 가족 및 주변인
		try {
			 String insert_sql = "INSERT INTO ##### "
						+ "(#####, #####,#####, #####,#####, #####,#####, ##### " +
						",#####, #####,#####, #####,#####, #####,#####, #####, #####) VALUES " 
						+ "('#####', '#####', '#####', '#####','#####', '#####', '#####', '#####', '#####'" +
						",'#####', '#####', '#####', '#####','#####', '#####', '#####',   '#####')";
					insert_sql = KumaDBConstants.replaceString(insert_sql,
							KumaDBConstants.TABLE_CUSTOMER_SUB,
							KumaDBConstants.CUSTOMER_SUB_MAIN_CUSTOMER,
							KumaDBConstants.CUSTOMER_SUB_NAME,
							KumaDBConstants.CUSTOMER_SUB_RELATION,
							KumaDBConstants.CUSTOMER_SUB_ID,
							KumaDBConstants.CUSTOMER_SUB_BIRTHDAY,
							KumaDBConstants.CUSTOMER_SUB_BIRTHDAY_TYPE,
							KumaDBConstants.CUSTOMER_SUB_WEDDING,
							KumaDBConstants.CUSTOMER_SUB_COMPANY,
							KumaDBConstants.CUSTOMER_SUB_POSITION,
							KumaDBConstants.CUSTOMER_SUB_HOUSE_NUMBER,
							KumaDBConstants.CUSTOMER_SUB_COMPANY_NUMBER,
							KumaDBConstants.CUSTOMER_SUB_PHONE_NUMBER,
							KumaDBConstants.CUSTOMER_SUB_FAX_NUMBER,
							KumaDBConstants.CUSTOMER_SUB_HOUSE_POSTCODE,
							KumaDBConstants.CUSTOMER_SUB_HOUSE_ADDRESS,
							KumaDBConstants.CUSTOMER_SUB_COMPANY_POSTCODE,
							KumaDBConstants.CUSTOMER_SUB_COMPANY_ADDRESS,
							Main_customer_Id, arrCustomer.get(0),  arrCustomer.get(1), arrCustomer.get(2),arrCustomer.get(3),
							arrCustomer.get(4), arrCustomer.get(5), arrCustomer.get(6), arrCustomer.get(7),
							arrCustomer.get(8), arrCustomer.get(9), arrCustomer.get(10), arrCustomer.get(11), 
							arrCustomer.get(12), arrCustomer.get(13), arrCustomer.get(14), arrCustomer.get(15) );
					try {
//						Log.i("test"," ================ insert_sql ================ \n"  + insert_sql);
						sDB.execSQL(insert_sql);
					} catch (Exception e1) {
						e1.printStackTrace();
					}
		} catch (Exception e) { 
			e.printStackTrace();
		}
	}
	
	public void Insert_Customer_CONTRACT(String Main_customer_Id, ArrayList<String> arrCustomer){
		try {
			 String insert_sql = "INSERT INTO ##### "
						+ "(#####, #####,#####, #####,#####, #####,#####, #####, #####, #####) VALUES " 
						+ "('#####', '#####', '#####', '#####','#####', '#####', '#####', '#####', '#####', '#####')";
					insert_sql = KumaDBConstants.replaceString(insert_sql,
							KumaDBConstants.TABLE_CUSTOMER_CONTRACT,
							KumaDBConstants.CUSTOMER_CONTRACT_MAIN_CUSTOMER,
							KumaDBConstants.CUSTOMER_CONTRACT_NAME,
							KumaDBConstants.CUSTOMER_CONTRACT_CONTRACTOR,
							KumaDBConstants.CUSTOMER_CONTRACT_INSURANT,
							KumaDBConstants.CUSTOMER_CONTRACT_EVENT,
							KumaDBConstants.CUSTOMER_CONTRACT_DATE,
							KumaDBConstants.CUSTOMER_CONTRACT_MONEY,
							KumaDBConstants.CUSTOMER_CONTRACT_SPENDING_TERM,
							KumaDBConstants.CUSTOMER_CONTRACT_SPENDING_EXPIRY,
							KumaDBConstants.CUSTOMER_CONTRACT_EXPIRY,
							Main_customer_Id, arrCustomer.get(0), arrCustomer.get(1), arrCustomer.get(2),arrCustomer.get(3),
							arrCustomer.get(4), arrCustomer.get(5), arrCustomer.get(6), arrCustomer.get(7), arrCustomer.get(8) );
					
					
					try {
//						Log.i("test"," ================ insert_sql ================ \n"  + insert_sql);
						sDB.execSQL(insert_sql);
					} catch (Exception e1) {
						e1.printStackTrace();
					}
		} catch (Exception e) { 
			e.printStackTrace();
		}
	}
	
	 public void DB_Destroy(){
		 sDB.close();
	 }
}//class
