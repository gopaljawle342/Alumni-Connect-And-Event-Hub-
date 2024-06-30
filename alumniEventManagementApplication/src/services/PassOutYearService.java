package services;

import java.util.ArrayList;

import model.PassOutYearModel;
import repository.PassOutYearRepository;

public class PassOutYearService {

	PassOutYearRepository passRepo=new PassOutYearRepository();
	
	public boolean isAddPassOutYear(int pass_Year) {
		return passRepo.isAddPassOutYear(pass_Year);
	}
	
	public ArrayList<PassOutYearModel> getPassYearInfo(){
		return passRepo.getPassYearInfo();
	}
	
	public int getPassOutYearId(int year) {
		return passRepo.getPassOutYearId(year);
	}
}
