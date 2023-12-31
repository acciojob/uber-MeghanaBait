package com.driver.services.impl;

import com.driver.Exception.NoCabAvaliableException;
import com.driver.model.TripBooking;
import com.driver.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.driver.model.Customer;
import com.driver.model.Driver;
import com.driver.repository.CustomerRepository;
import com.driver.repository.DriverRepository;
import com.driver.repository.TripBookingRepository;
import com.driver.model.TripStatus;

import java.util.List;
import java.util.Optional;


@Service
public class CustomerServiceImpl implements CustomerService {

	@Autowired
	CustomerRepository customerRepository2;

	@Autowired
	DriverRepository driverRepository2;

	@Autowired
	TripBookingRepository tripBookingRepository2;

	@Override
	public void register(Customer customer) {
		customerRepository2.save(customer);
		//Save the customer in database
	}

	@Override
	public void deleteCustomer(Integer customerId) {
		// Delete customer without using deleteById function
		Optional<Customer> optionalCustomer = customerRepository2.findById(customerId);
		if(optionalCustomer.isPresent()){
			Customer customer = optionalCustomer.get();
			customerRepository2.deleteById(customerId);
		}
	}

	@Override
	public TripBooking bookTrip(int customerId, String fromLocation, String toLocation, int distanceInKm) throws Exception{
		//Book the driver with lowest driverId who is free (cab available variable is Boolean.TRUE). If no driver is available, throw "No cab available!" exception
		//Avoid using SQL query
		TripBooking tripBooking = new TripBooking();

		Driver driver = null;
		List<Driver> driverList = driverRepository2.findAll();

		for (Driver driver1 : driverList){
			if(driver1.getCab().getAvailable() == Boolean.TRUE){
				if (driver == null || driver1.getDriverId() < driver.getDriverId()){
					driver = driver1;
				}
			}
		}

		if (driver == null){
			throw new NoCabAvaliableException("No cab available!");
		}

		Optional<Customer> optionalCustomer = customerRepository2.findById(customerId);
		if(optionalCustomer.isPresent()){
			Customer customer = optionalCustomer.get();

			//setting  attributes
			tripBooking.setStatus(TripStatus.CONFIRMED);
			tripBooking.setFromLocation(fromLocation);
			tripBooking.setToLocation(toLocation);
			tripBooking.setDistanceInKm(distanceInKm);

			driver.getCab().setAvailable(Boolean.FALSE);

			//assigning tripbooking FK attributes
			tripBooking.setCustomer(customer);
			tripBooking.setDriver(driver);

			//saving parent
			customer.getTripBookingList().add(tripBooking);
			customerRepository2.save(customer);

			driver.getTripBookings().add(tripBooking);
			driverRepository2.save(driver);
		}
		return tripBooking;
	}

	@Override
	public void cancelTrip(Integer tripId){
		//Cancel the trip having given trip Id and update TripBooking attributes accordingly
		Optional<TripBooking> optionalTripBooking = tripBookingRepository2.findById(tripId);

		if(optionalTripBooking.isPresent()){
			TripBooking tripBooking = optionalTripBooking.get();
			tripBooking.setStatus(TripStatus.CANCELED);
			tripBooking.setBill(0);
			tripBooking.getDriver().getCab().setAvailable(Boolean.TRUE);
			tripBookingRepository2.save(tripBooking);
		}
	}

	@Override
	public void completeTrip(Integer tripId){
		//Complete the trip having given trip Id and update TripBooking attributes accordingly
		Optional<TripBooking> optionalTripBooking = tripBookingRepository2.findById(tripId);

		if(optionalTripBooking.isPresent()){
			TripBooking tripBooking = optionalTripBooking.get();
			tripBooking.setStatus(TripStatus.COMPLETED);
			tripBooking.getDriver().getCab().setAvailable(Boolean.TRUE);
			int bill = tripBooking.getDriver().getCab().getPerKmRate()*tripBooking.getDistanceInKm();
			tripBooking.setBill(bill);
			tripBookingRepository2.save(tripBooking);
		}
	}
}
