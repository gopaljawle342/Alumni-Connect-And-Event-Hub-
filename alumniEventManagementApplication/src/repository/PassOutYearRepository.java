package repository;

import model.*;

import java.util.*;

import config.DBHelper;
public class PassOutYearRepository extends DBHelper{

	public boolean isAddPassOutYear(int passYear) {
		
		try {
			stmt=conn.prepareStatement("insert into passoutyear values('0',?)");
			stmt.setInt(1, passYear);
			int value=stmt.executeUpdate();
			return value>0?true:false;
		}
		catch(Exception ex) {
			System.out.println("isAddPassOutYear error "+ex);
			return false;
		}
	}
	
	public ArrayList<PassOutYearModel> getPassYearInfo(){
		ArrayList<PassOutYearModel> al=new ArrayList<PassOutYearModel>();
		try {
			stmt=conn.prepareStatement("select *from passoutyear");
			rs=stmt.executeQuery();
			while(rs.next()) {
				PassOutYearModel passModel=new PassOutYearModel();
				passModel.setId(rs.getInt(1));
				passModel.setYear(rs.getInt(2));
				
				al.add(passModel);
			}
			return al;
		}
		catch(Exception ex) {
			System.out.println("get pass year error "+ex);
			return null;
			}
	}
	
	public int getPassOutYearId(int year) {
		try {
			stmt=conn.prepareStatement("select id from passoutyear where pass_year=?");
			stmt.setInt(1, year);
			rs=stmt.executeQuery();
			if(rs.next())
				return rs.getInt(1);
			else
				return 0;
			
		}
		catch(Exception ex) {
			System.out.println("get pass year id error "+ex);
			return -1;
		}
	}
	
}

