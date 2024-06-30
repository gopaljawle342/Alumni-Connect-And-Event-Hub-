package services;

import java.util.ArrayList;

import model.BranchModel;
import repository.BranchRepository;

public class BranchService {
	BranchRepository branchRepo=new BranchRepository();
	public boolean isAddNewBranch(BranchModel branchModel) {
		return branchRepo.isAddNewBranch(branchModel);
	}
	
	
	public ArrayList<BranchModel> getBranchInfo(){
		return branchRepo.getBranchInfo();
	}
	
	public BranchModel getIdHODInfo(String branch_name) {
		return branchRepo.getIdHODInfo(branch_name);
	}
	
	public int getBranchId(String branch_name) {
		return branchRepo.getBranchId(branch_name);
	}
}
