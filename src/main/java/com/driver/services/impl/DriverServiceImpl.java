package com.driver.services.impl;

import com.driver.model.Cab;
import com.driver.repository.CabRepository;
import com.driver.services.DriverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.driver.model.Driver;
import com.driver.repository.DriverRepository;

import java.util.List;
import java.util.Optional;

@Service
public class DriverServiceImpl implements DriverService {

	@Autowired
	DriverRepository driverRepository3;

	@Autowired
	CabRepository cabRepository3;

	@Override
	public void register(String mobile, String password){
		//Save a driver in the database having given details and a cab with ratePerKm as 10 and availability as True by default.
		Driver driver = new Driver();
		driver.setMobile(mobile);
		driver.setPassword(password);

		Cab cab = null;
		List<Cab> cabList =cabRepository3.findAll();

		for(Cab cab1 : cabList){
			if(cab1.getPerKmRate()== 10 && cab1.getAvailable()==Boolean.TRUE){
				cab = cab1;
			}
		}

		if(cab != null){
			driver.setCab(cab);
		}
		driverRepository3.save(driver);
	}

	@Override
	public void removeDriver(int driverId){
		// Delete driver without using deleteById function
		Optional<Driver> optionalDriver = driverRepository3.findById(driverId);
		if(optionalDriver.isPresent()){
			Driver driver = optionalDriver.get();
			driverRepository3.delete(driver);
		}

	}

	@Override
	public void updateStatus(int driverId){
		//Set the status of respective car to unavailable
		Optional<Driver> optionalDriver = driverRepository3.findById(driverId);
		if (optionalDriver.isPresent()){
			Driver driver = optionalDriver.get();
			driver.getCab().setAvailable(Boolean.FALSE);
		}

	}
}
