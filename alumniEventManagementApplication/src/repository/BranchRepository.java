package repository;

import java.util.ArrayList;

import config.DBHelper;
import model.BranchModel;

public class BranchRepository extends DBHelper {

	/* Add new Branch */
	public boolean isAddNewBranch(BranchModel branchModel) {
		try {
			stmt = conn.prepareStatement("insert into alumnibranch values('0',?,?)");
			stmt.setString(1, branchModel.getBranchName());
			stmt.setString(2, branchModel.getHodName());

			int value = stmt.executeUpdate();
			return value > 0 ? true : false;

		} catch (Exception ex) {
			System.out.println("Branch Add Error " + ex);
			return false;
		}
	}

	/* Branch Information */
	public ArrayList<BranchModel> getBranchInfo() {
		ArrayList<BranchModel> al = new ArrayList<BranchModel>();
		try {
			stmt = conn.prepareStatement("select * from alumnibranch ");
			rs = stmt.executeQuery();
			while (rs.next()) {
				BranchModel branchModel = new BranchModel();
				branchModel.setId(rs.getInt(1));
				branchModel.setBranchName(rs.getString(2));
				branchModel.setHodName(rs.getString(3));
				al.add(branchModel);
			}
			return al;
		} catch (Exception ex) {
			System.out.println("get Branch Info error " + ex);
			return null;
		}
	}
	
	
	public int getBranchId(String branchName) {

		try {
			stmt = conn.prepareStatement("select branch_id from alumnibranch where branch_name=?");
			stmt.setString(1, branchName);
			rs = stmt.executeQuery();
			if(rs.next()) {
				return rs.getInt(1);
			}
			else {
				return 0;
			}
		}catch (Exception ex) {
			System.out.println("get branch id error " + ex);
			return 0;
		}

	}

	public BranchModel getIdHODInfo(String branchName) {

		try {
			BranchModel branchModel = new BranchModel();
			stmt = conn.prepareStatement("select branch_id,branch_HOD from alumnibranch where branch_name=?");
			stmt.setString(1, branchName);

			rs = stmt.executeQuery();
			while (rs.next()) {

				branchModel.setId(rs.getInt(1));
				branchModel.setHodName(rs.getString(2));
			}
			return branchModel;
		} catch (Exception ex) {
			System.out.println("getIdHODInfo error " + ex);
			return null;
		}

	}

	

}
