package com.etiya.recap.business.concretes;

import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.etiya.recap.business.abstracts.CorporateInvoicesService;
import com.etiya.recap.business.abstracts.CustomerFindeksScoreCheckService;
import com.etiya.recap.business.abstracts.IndividualInvoicesService;
import com.etiya.recap.business.abstracts.RentalService;
import com.etiya.recap.business.constants.Messages;
import com.etiya.recap.core.services.posService.PosService;
import com.etiya.recap.core.utilities.business.BusinessRules;
import com.etiya.recap.core.utilities.results.DataResult;
import com.etiya.recap.core.utilities.results.ErrorResult;
import com.etiya.recap.core.utilities.results.Result;
import com.etiya.recap.core.utilities.results.SuccessDataResult;
import com.etiya.recap.core.utilities.results.SuccessResult;
import com.etiya.recap.dataAccess.abstracts.AdditionalServicesDao;
import com.etiya.recap.dataAccess.abstracts.CarDao;
import com.etiya.recap.dataAccess.abstracts.CreditCardDao;
import com.etiya.recap.dataAccess.abstracts.RentalDao;
import com.etiya.recap.entities.concretes.AdditionalServices;
import com.etiya.recap.entities.concretes.ApplicationUser;
import com.etiya.recap.entities.concretes.Car;
import com.etiya.recap.entities.concretes.CorporateCustomer;
import com.etiya.recap.entities.concretes.CreditCard;
import com.etiya.recap.entities.concretes.IndividualCustomer;
import com.etiya.recap.entities.concretes.Rental;
import com.etiya.recap.entities.requests.create.CreateDeliverTheCar;
import com.etiya.recap.entities.requests.create.CreateInvoicesRequest;
import com.etiya.recap.entities.requests.create.CreatePosServiceRequest;
import com.etiya.recap.entities.requests.create.CreateRentalRequest;
import com.etiya.recap.entities.requests.delete.DeleteRentalRequest;
import com.etiya.recap.entities.requests.update.UpdateRentalRequest;

@Service
public class RentalManager implements RentalService {

	private RentalDao rentalDao;
	private CarDao carDao;
	private CustomerFindeksScoreCheckService customerFindeksScoreCheckService;
	private CreditCardDao creditCardDao;
	private CorporateInvoicesService corporateInvoicesService;
	private IndividualInvoicesService individualInvoicesService;
	private AdditionalServicesDao additionalServicesDao;
	private PosService posService;

	@Autowired
	public RentalManager(RentalDao rentalDao, CarDao carDao,
			CustomerFindeksScoreCheckService customerFindeksScoreCheckService,CreditCardDao creditCardDao,
			CorporateInvoicesService corporateInvoicesService,IndividualInvoicesService individualInvoicesService, 
			AdditionalServicesDao additionalServicesDao,PosService posService) {
		this.rentalDao = rentalDao;
		this.carDao = carDao;
		this.customerFindeksScoreCheckService = customerFindeksScoreCheckService;
		this.creditCardDao = creditCardDao;
		this.corporateInvoicesService = corporateInvoicesService;
		this.individualInvoicesService = individualInvoicesService;
		this.additionalServicesDao=additionalServicesDao;
		this.posService = posService;
	}

	@Override
	public DataResult<List<Rental>> getAll() {
		return new SuccessDataResult<List<Rental>>(this.rentalDao.findAll(), Messages.GetAll);
	}

	@Override
	public Result rentCorporateCustomer(CreateRentalRequest createRentalRequest) {
		Car car =this.carDao.getById(createRentalRequest.getCarId());
		
		//Araç bakımda ise kiralanamaz****************************************
		if(car.isCarIsAvailable()==false) {
			return new ErrorResult(Messages.ErrorIfCarIsNotAvailableToRent);
		}
		
		ApplicationUser applicationUser = new ApplicationUser();
		applicationUser.setUserId(createRentalRequest.getUserId());
		
		CorporateCustomer corporateCustomer = new CorporateCustomer();
		corporateCustomer.setApplicationUser(applicationUser);

		Rental rental = new Rental();
		rental.setRentDate(createRentalRequest.getRentDate());
		rental.setReturnDate(createRentalRequest.getReturnDate());
		rental.setCar(car);
		rental.setUser(applicationUser);
		rental.setReturnCity(createRentalRequest.getReturnCity());
		rental.setKilometer(createRentalRequest.getKilometer());
		
		//Hizmetleri taşıma işlemi
		List<AdditionalServices> additionalServices = new ArrayList<AdditionalServices>();
		
		for (Integer additionalServicesId: createRentalRequest.getAdditionalServicesId()) {
			additionalServices.add(this.additionalServicesDao.getById(additionalServicesId));
		}
		rental.setAdditionalServices(additionalServices);
		//**************************************************************************************
		
		CreditCard creditCard = new CreditCard();
		creditCard = new CreditCard();
		creditCard.setCardNumber(createRentalRequest.getCreditCardDto().getCardNumber());
		creditCard.setApplicationUser(applicationUser);
		creditCard.setCvc(createRentalRequest.getCreditCardDto().getCvc());
		creditCard.setExpirationDate(createRentalRequest.getCreditCardDto().getExpirationDate());
		creditCard.setNameOnTheCard(createRentalRequest.getCreditCardDto().getNameOnTheCard());
		
		//Arabanın kiralanmadan önceki ili kiralanma şehri olacak
		rental.setRentalStartingCity(car.getCity());
		
		//Arabayı tekrar teslim ettiğimizde teslim ili arabanın bulunduğu il olacak
		car.setCity(createRentalRequest.getReturnCity());
		
		//Müşterinin girdiği km bilgisini arabanın km bilgisi ile güncelleme
		car.setKilometer(createRentalRequest.getKilometer()+car.getKilometer());
		
		//Eğer müşteri kredi kartını kaydetmek istiyorsa kaydetme işlemi
		if(createRentalRequest.isSaveCreditCard()==true) {
			this.creditCardDao.save(creditCard);
		}
		//****************************************************************
		
		var result = BusinessRules.run(checkCarIsReturned(car.getId()),checkCorporateCustomerFindeksScore(corporateCustomer, car)
				,checkCreditCardNumber(creditCard));
		if (result != null) {
			return result;
		}
		
		
		
		
		
		
		long totalRentDateCount = ChronoUnit.DAYS.between(rental.getRentDate().toInstant(),rental.getReturnDate().toInstant());
		double rentPrice = (rental.getCar().getDailyPrice() * totalRentDateCount);
		
		//başlangıç şehrine teslim edilmediyse 500 tl fark alınır.
		if(rental.getReturnCity().equals(rental.getRentalStartingCity())==false)
		{
		   rentPrice+=500;
		}
		
		//Hizmet bedelleri***********************************************************
		for (AdditionalServices additionalService : rental.getAdditionalServices()) {
			if(additionalService.getAdditionalServiceName().equals("BebekKoltugu")) {
				rentPrice+=additionalService.getAdditionalServicePrice();
			}
			if(additionalService.getAdditionalServiceName().equals("Scooter")) {
				 rentPrice+=additionalService.getAdditionalServicePrice();
			}
			if(additionalService.getAdditionalServiceName().equals("Sigorta")) {
				 rentPrice+=additionalService.getAdditionalServicePrice();
			}
			if(additionalService.getAdditionalServiceName().equals("Kasko")) {
				 rentPrice+=additionalService.getAdditionalServicePrice();
			}
		}
		
		
		
	
		CreatePosServiceRequest createPosServiceRequest = new CreatePosServiceRequest();
		createPosServiceRequest.setCardNumber(creditCard.getCardNumber());
		createPosServiceRequest.setCvc(creditCard.getCvc());
		createPosServiceRequest.setExpirationDate(creditCard.getExpirationDate());
		createPosServiceRequest.setNameOnTheCard(creditCard.getNameOnTheCard());
		createPosServiceRequest.setFeePayable(rentPrice);
		
		if(this.posService.withdraw(createPosServiceRequest) == true)
		{
			this.rentalDao.save(rental);
		}
		else {
			return new ErrorResult(Messages.ErrorMoneyIsNotEnough);
		}
		
		
		
		
		if(rental.getReturnDate()!=null)
		{
	
		//Araba kiralanır kiralanmaz fatura kesme işlemi(Bunu save'den sonra yapmamızın sebebi,rental kayıt olduktan sonra IDsini get edebiliyoruz.
		Date dateNow = new java.sql.Date(new java.util.Date().getTime());
		CreateInvoicesRequest createInvoicesRequest = new CreateInvoicesRequest();
		createInvoicesRequest.setCreationDate(dateNow);
		createInvoicesRequest.setRentalId(rental.getId());
		createInvoicesRequest.setRentPrice(rentPrice);
		createInvoicesRequest.setTotalRentDateCount(totalRentDateCount);
		this.corporateInvoicesService.add(createInvoicesRequest);
		
		}
		
		return new SuccessResult(true, Messages.Add);
	}

	@Override
	public Result rentIndividualCustomer(CreateRentalRequest createRentalRequest) {
		Car car =this.carDao.getById(createRentalRequest.getCarId());
		
		//Araç bakımda ise kiralanamaz****************************************
		if(car.isCarIsAvailable()==false) {
			return new ErrorResult(Messages.ErrorIfCarIsNotAvailableToRent);
		}
		//*********************************************************************
		
		
		ApplicationUser applicationUser = new ApplicationUser();
		applicationUser.setUserId(createRentalRequest.getUserId());

		IndividualCustomer individualCustomer = new IndividualCustomer();
		individualCustomer.setApplicationUser(applicationUser);

		Rental rental = new Rental();
		rental.setRentDate(createRentalRequest.getRentDate());
		rental.setReturnDate(createRentalRequest.getReturnDate());
		rental.setCar(car);
		rental.setUser(applicationUser);
		rental.setReturnCity(createRentalRequest.getReturnCity());
		rental.setKilometer(createRentalRequest.getKilometer());
		
		//Hizmetleri taşıma işlemi
		List<AdditionalServices> additionalServices = new ArrayList<AdditionalServices>();
		for (Integer additionalServicesId: createRentalRequest.getAdditionalServicesId()) {
			additionalServices.add(this.additionalServicesDao.getById(additionalServicesId));
		}
		rental.setAdditionalServices(additionalServices);
		
		CreditCard creditCard = new CreditCard();
		creditCard = new CreditCard();
		creditCard.setCardNumber(createRentalRequest.getCreditCardDto().getCardNumber());
		creditCard.setApplicationUser(applicationUser);
		creditCard.setCvc(createRentalRequest.getCreditCardDto().getCvc());
		creditCard.setExpirationDate(createRentalRequest.getCreditCardDto().getExpirationDate());
		creditCard.setNameOnTheCard(createRentalRequest.getCreditCardDto().getNameOnTheCard());
		
		//Arabanın kiralanmadan önceki ili kiralanma şehri olacak
		rental.setRentalStartingCity(car.getCity());
		
		//Arabayı tekrar teslim ettiğimizde teslim ili arabanın bulunduğu il olacak
		car.setCity(createRentalRequest.getReturnCity());
		
		//Müşterinin girdiği km bilgisini arabanın km bilgisi ile güncelleme
		car.setKilometer(createRentalRequest.getKilometer()+car.getKilometer());
		
		//Eğer müşteri kredi kartını kaydetmek istiyorsa kaydetme işlemi
		if(createRentalRequest.isSaveCreditCard()==true) {
			this.creditCardDao.save(creditCard);
		}
		//****************************************************************
		
		
		var result = BusinessRules.run(checkCarIsReturned(car.getId()),checkIndividualCustomerFindeksScore(individualCustomer, car),
				checkCreditCardNumber(creditCard));

		if (result != null) {
			return result;
		}
		this.rentalDao.save(rental);
		
		if(rental.getReturnDate()!=null)
		{
			
		//Araba kiralanır kiralanmaz fatura kesme işlemi(Bunu save'den sonra yapmamızın sebebi,rental kayıt olduktan sonra IDsini get edebiliyoruz.)
		Date dateNow = new java.sql.Date(new java.util.Date().getTime());
		CreateInvoicesRequest createInvoicesRequest = new CreateInvoicesRequest();
		createInvoicesRequest.setCreationDate(dateNow);
		createInvoicesRequest.setRentalId(rental.getId());
		this.individualInvoicesService.add(createInvoicesRequest);
		}
				
		return new SuccessResult(true, Messages.Add);
	}

	@Override
	public DataResult<Rental> getById(int id) {
		return new SuccessDataResult<Rental>(this.rentalDao.getById(id), Messages.GetById);
	}

	@Override
	public Result delete(DeleteRentalRequest deleteRentalRequest) {
		Rental rental = new Rental();
		rental.setId(deleteRentalRequest.getId());

		this.rentalDao.delete(rental);
		return new SuccessResult(true, Messages.Delete);
	}

	@Override
	public Result updateIndividualCustomerRent(UpdateRentalRequest updateRentalRequest) {
		Car car =this.carDao.getById(updateRentalRequest.getCarId());
		
		//Araç bakımda ise kiralanamaz****************************************
		if(car.isCarIsAvailable()==false) {
			return new ErrorResult(Messages.ErrorIfCarIsNotAvailableToRent);
		}
		//*********************************************************************
		
		ApplicationUser applicationUser = new ApplicationUser();
		applicationUser.setUserId(updateRentalRequest.getUserId());
		
		IndividualCustomer individualCustomer = new IndividualCustomer();
		individualCustomer.setApplicationUser(applicationUser);

		Rental rental = new Rental();
		rental.setId(updateRentalRequest.getId());
		rental.setRentDate(updateRentalRequest.getRentDate());
		rental.setReturnDate(updateRentalRequest.getReturnDate());
		rental.setCar(car);
		rental.setUser(applicationUser);
		rental.setReturnCity(updateRentalRequest.getReturnCity());
		rental.setKilometer(updateRentalRequest.getKilometer());
		
		CreditCard creditCard = new CreditCard();
		creditCard = new CreditCard();
		creditCard.setCardNumber(updateRentalRequest.getCreditCardDto().getCardNumber());
		creditCard.setApplicationUser(applicationUser);
		creditCard.setCvc(updateRentalRequest.getCreditCardDto().getCvc());
		creditCard.setExpirationDate(updateRentalRequest.getCreditCardDto().getExpirationDate());
		creditCard.setNameOnTheCard(updateRentalRequest.getCreditCardDto().getNameOnTheCard());
		
		//Arabanın kiralanmadan önceki ili kiralanma şehri olacak
		rental.setRentalStartingCity(car.getCity());
		
		//Arabayı tekrar teslim ettiğimizde teslim ili arabanın bulunduğu il olacak
		car.setCity(updateRentalRequest.getReturnCity());
		
		//Müşterinin girdiği km bilgisini arabanın km bilgisi ile güncelleme
		car.setKilometer(updateRentalRequest.getKilometer()+car.getKilometer());
		
		//Eğer müşteri kredi kartını kaydetmek istiyorsa kaydetme işlemi
		if(updateRentalRequest.isSaveCreditCard()==true) {
			this.creditCardDao.save(creditCard);
		}
		//****************************************************************
		

		var result = BusinessRules.run(checkIndividualCustomerFindeksScore(individualCustomer, car),
				this.checkCreditCardNumber(creditCard));

		if (result != null) {
			return result;
		}
		
		this.creditCardDao.save(creditCard);
		this.rentalDao.save(rental);
		return new SuccessResult(true, Messages.Update);
	}
	
	@Override
	public Result updateCorporateCustomerRent(UpdateRentalRequest updateRentalRequest) {
		Car car =this.carDao.getById(updateRentalRequest.getCarId());
		
		//Araç bakımda ise kiralanamaz****************************************
		if(car.isCarIsAvailable()==false) {
			return new ErrorResult(Messages.ErrorIfCarIsNotAvailableToRent);
		}
		//*********************************************************************
			
		
		ApplicationUser applicationUser = new ApplicationUser();
		applicationUser.setUserId(updateRentalRequest.getUserId());
		
		CorporateCustomer corporateCustomer = new CorporateCustomer();
		corporateCustomer.setApplicationUser(applicationUser);

		Rental rental = new Rental();
		rental.setId(updateRentalRequest.getId());
		rental.setRentDate(updateRentalRequest.getRentDate());
		rental.setReturnDate(updateRentalRequest.getReturnDate());
		rental.setCar(car);
		rental.setUser(applicationUser);
		rental.setReturnCity(updateRentalRequest.getReturnCity());
		rental.setKilometer(updateRentalRequest.getKilometer());
		
		CreditCard creditCard = new CreditCard();
		creditCard = new CreditCard();
		creditCard.setCardNumber(updateRentalRequest.getCreditCardDto().getCardNumber());
		creditCard.setApplicationUser(applicationUser);
		creditCard.setCvc(updateRentalRequest.getCreditCardDto().getCvc());
		creditCard.setExpirationDate(updateRentalRequest.getCreditCardDto().getExpirationDate());
		creditCard.setNameOnTheCard(updateRentalRequest.getCreditCardDto().getNameOnTheCard());
		
		//Arabanın kiralanmadan önceki ili kiralanma şehri olacak
		rental.setRentalStartingCity(car.getCity());
		
		//Arabayı tekrar teslim ettiğimizde teslim ili arabanın bulunduğu il olacak
		car.setCity(updateRentalRequest.getReturnCity());
		
		//Müşterinin girdiği km bilgisini arabanın km bilgisi ile güncelleme
		car.setKilometer(updateRentalRequest.getKilometer()+car.getKilometer());
		
		//Eğer müşteri kredi kartını kaydetmek istiyorsa kaydetme işlemi
		if(updateRentalRequest.isSaveCreditCard()==true) {
			this.creditCardDao.save(creditCard);
		}
		//****************************************************************
		
		
		var result = BusinessRules.run(checkCorporateCustomerFindeksScore(corporateCustomer, car),
				checkCreditCardNumber(creditCard));

		if (result != null) {
			return result;
		}

		this.creditCardDao.save(creditCard);
		this.rentalDao.save(rental);
		return new SuccessResult(true, Messages.Update);
	}

	private Result checkCarIsReturned(int carId) {
		List<Rental> checkRentalReturnDate = this.rentalDao.getByCar_id(carId);
		for (Rental rental2 : checkRentalReturnDate) {
			if (rental2.getReturnDate() == null) {
				return new ErrorResult(Messages.ErrorIfCarIsNotAvailable);
			}
		}
		return new SuccessResult();
	}
	
	private Result checkIndividualCustomerFindeksScore(IndividualCustomer individualCustomer,Car car) {
		int individualCustomerFindeksScore = this.customerFindeksScoreCheckService.checkIndividualCustomerFindeksScore(individualCustomer);
		if(car.getFindeksScore()>individualCustomerFindeksScore) {
			return new ErrorResult(Messages.ErrorFindeksScore);
		}
		return new SuccessResult();
	}
	
	private Result checkCorporateCustomerFindeksScore(CorporateCustomer corporateCustomer, Car car) {
		int corporateCustomerFindeksScore = this.customerFindeksScoreCheckService.checkCorporateCustomerFindeksScore(corporateCustomer);
		if(car.getFindeksScore()>corporateCustomerFindeksScore) {
			return new ErrorResult(Messages.ErrorFindeksScore);
		}
		return new SuccessResult();
	}
	
	//Kredi Kart Numarası Kontrolü
	private Result checkCreditCardNumber(CreditCard creditCard) {
		
		String regex = "^(?:(?<visa>4[0-9]{12}(?:[0-9]{3})?)|" +
		        "(?<mastercard>5[1-5][0-9]{14})|" +
		        "(?<discover>6(?:011|5[0-9]{2})[0-9]{12})|" +
		        "(?<amex>3[47][0-9]{13})|" +
		        "(?<diners>3(?:0[0-5]|[68][0-9])?[0-9]{11})|" +
		        "(?<jcb>(?:2131|1800|35[0-9]{3})[0-9]{11}))$";
		
		Pattern pattern = Pattern.compile(regex);
		creditCard.setCardNumber(creditCard.getCardNumber().replaceAll("-", ""));
		Matcher matcher = pattern.matcher(creditCard.getCardNumber());
		if(matcher.matches()==true) {
			return new SuccessResult();
		}else {
			return new ErrorResult(Messages.ErrorIfCreditCardIsWrong);
			}
		}
	//*********************************************************

	@Override
	public Result deliverCorporateCustomerCar(CreateDeliverTheCar createDeliverTheCar) {
		
		Rental rental=this.rentalDao.getById(createDeliverTheCar.getRentalId());
		
		rental.setReturnDate(createDeliverTheCar.getReturnDate());
		
		Date dateNow = new java.sql.Date(new java.util.Date().getTime());
		CreateInvoicesRequest createInvoicesRequest = new CreateInvoicesRequest();
		createInvoicesRequest.setCreationDate(dateNow);
		createInvoicesRequest.setRentalId(rental.getId());
		this.corporateInvoicesService.add(createInvoicesRequest);
		
		return new SuccessResult(true,Messages.GetAll);
		
	}

	@Override
	public Result deliverIndividualCustomerCar(CreateDeliverTheCar createDeliverTheCar) {
		
       Rental rental=this.rentalDao.getById(createDeliverTheCar.getRentalId());
   	   rental.setReturnDate(createDeliverTheCar.getReturnDate());
		
		Date dateNow = new java.sql.Date(new java.util.Date().getTime());
		CreateInvoicesRequest createInvoicesRequest = new CreateInvoicesRequest();
		createInvoicesRequest.setCreationDate(dateNow);
		createInvoicesRequest.setRentalId(rental.getId());
		this.individualInvoicesService.add(createInvoicesRequest);
		
		return new SuccessResult(true,Messages.GetAll);
	}



}
