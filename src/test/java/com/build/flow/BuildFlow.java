package com.build.flow;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import static org.assertj.core.api.Assertions.*;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import com.build.constants.BuildConstants;
import com.build.page.BuildPage;
import com.build.util.Util;

/**
 * This class is used to write the methods and flow of application.
 * @author siva
 *
 */
public class BuildFlow{
	WebDriver driver;
	List<Double> price=new ArrayList<Double>();
	Double sum=(double) 0;
	/**
	 * This method is used to launch the application in Firefox browser
	 */
	protected void launchURL() {
		try{
		driver=new FirefoxDriver();
		driver.get(BuildConstants.APPURL);
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		Util.waitForSecifiedTime(5);
		driver.manage().window().maximize();
		}catch(Exception e){
			e.printStackTrace();
		}	
	}
	/**
	 * This method is used to search the product in application
	 */
	protected void addSudheKohlerProduct(){
		searchProduct(BuildPage.searchBox,BuildConstants.SUEDE);
		clickOnSearch(BuildPage.search);
		getCurrentPageTitle(BuildConstants.SUEDE_PAGE_TITLE);
		getAllpriceValue();
		addToCart();
	}
	/**
	 * This method is used to search the different products in application
	 * @param productName
	 * @param pageTitle
	 */
	protected void addKohlerProduct(String productName,String pageTitle){
		searchProduct(BuildPage.headerSearchBox,productName);
		clickOnSearchIcon();
		getCurrentPageTitle(pageTitle);
		if(productName.equals(BuildConstants.KOHLER)){
			selectQuantity();
			getAllpriceValue();
		}
		getAllpriceValue();
		addToCart();
	}
	/**
	 * This method is used to add the different products in application to cart and verify grand total.
	 */
	protected void addCashmereKohlerProduct(){
		addKohlerProduct(BuildConstants.CASHMERE_KOHLER,BuildConstants.SUEDE_PAGE_TITLE);
		addKohlerProduct(BuildConstants.KOHLER,BuildConstants.KOHLER_PAGE_TITLE);
		clickOnShoppingBtn();
		clickOnCheckOut();
		guestCheckOut();
		enterAddress();
		enterPaymentInfo();
		clickOnReviwOrder();
		verifyTaxAmount();
		verifyGrandAmount();
	}
	/**
	 * This method is used to identify the search box widget
	 * @param searchBox
	 * @param productName
	 */
	private void searchProduct(String searchBox,String productName){
		driver.findElement(By.id(searchBox)).sendKeys(productName);
	}
	/**
	 * This method is used to click the search box Icon widget
	 * @param searchIcon
	 */
	private void clickOnSearch(String searchIcon){
		driver.findElement(By.xpath(searchIcon)).click();
	}
	/**
	 * This method is used to click the search box Icon for header search
	 */
	private void clickOnSearchIcon(){
		driver.findElement(By.id(BuildPage.headerSearch)).click();
	}
	/**
	 * This method is used to close the application after execution
	 */
	public void closeDriver(){
		driver.close();
	}
	/**
	 * This method is used to get the page title
	 * @param expPageTitle
	 */
	private void getCurrentPageTitle(String expPageTitle){
		assertThat(driver.getTitle()).as("Current Page Title: "+driver.getTitle()).isEqualTo(expPageTitle);
	}
	/**
	 * This method is used to add the products to cart
	 */
	public void addToCart(){
		driver.findElement(By.className(BuildPage.addToCart)).click();
		isItemsAddedToCart();
	}
	/**
	 * This method is used to select the quantity of the products
	 */
	public void selectQuantity(){
		driver.findElement(By.id(BuildPage.selectQuantitiy)).clear();
		driver.findElement(By.id(BuildPage.selectQuantitiy)).sendKeys(BuildConstants.QUANTITY);
	}
	/**
	 * This method is used to verify products are added to cart or not
	 */
	public void isItemsAddedToCart(){
		assertThat(driver.findElement(By.className(BuildPage.itemdsAddedToCart)).getText()).isEqualTo(BuildConstants.SUCCESS_MSG);
	}
	/**
	 * This method is used to get the price of the products
	 * @return
	 */
	public String getPriceValue(){
		return driver.findElement(By.id(BuildPage.productPrice)).getText();
	}
	public void getAllpriceValue(){
		price.add(Util.stringToDouble(getPriceValue()));
	}
	public double totalPrice(){
		for(int i=0;i<price.size();i++){
			sum=sum+price.get(i);
		}
		return sum;
	}
	/**
	 * This method is used to click on Shopping button
	 */
	public void clickOnShoppingBtn(){
		driver.findElement(By.className(BuildPage.shoppingCart)).click();
	}
	/**
	 * This method is used to click on the check out of shopping cart
	 */
	public void clickOnCheckOut(){
		driver.findElement(By.xpath(BuildPage.checkOut)).click();
	}
	/**
	 * This Method is used to selecting the type of check out
	 */
	public void guestCheckOut(){
		driver.findElement(By.name(BuildPage.guestCheckOut)).click();
	}
	/**
	 * This Method is used to provide the Address of customer
	 */
	public void enterAddress(){
		driver.findElement(By.id(BuildPage.firstName)).sendKeys(BuildConstants.FIRST_NAME);
		driver.findElement(By.id(BuildPage.lastName)).sendKeys(BuildConstants.LAST_NAME);
		driver.findElement(By.id(BuildPage.address)).sendKeys(BuildConstants.ADDRESS);
		driver.findElement(By.id(BuildPage.zipCode)).sendKeys(BuildConstants.ZIPCODE);
		driver.findElement(By.id(BuildPage.city)).sendKeys(BuildConstants.CITY);
		driver.findElement(By.id(BuildPage.state)).sendKeys(BuildConstants.STATE);
		driver.findElement(By.id(BuildPage.country)).sendKeys(BuildConstants.COUNTRY);
		Util.waitForSecifiedTime(2);
		driver.findElement(By.id(BuildPage.phNumber)).sendKeys(BuildConstants.PH_NUMBER);
		driver.findElement(By.id(BuildPage.emailID)).sendKeys(BuildConstants.EMAIL_ID);
	}
	/**
	 * This Method is used for providing the payment information
	 */
	public void enterPaymentInfo(){
		driver.findElement(By.id(BuildPage.creditCardNumber)).sendKeys(BuildConstants.CARD_NUMBER);
		driver.findElement(By.id(BuildPage.creditCardMonth)).sendKeys(BuildConstants.MONTH);
		driver.findElement(By.id(BuildPage.creditCardYear)).sendKeys(BuildConstants.YEAR);
		driver.findElement(By.id(BuildPage.creditCardName)).sendKeys(BuildConstants.FIRST_NAME);
		driver.findElement(By.id(BuildPage.cvv)).sendKeys(BuildConstants.CVV);
	}
	/**
	 * This Method is used to click on review order button
	 */
	public void clickOnReviwOrder(){
		driver.findElement(By.xpath(BuildPage.reviwOrder)).click();
	}
	/**
	 * This Method is used to verify the  Tax amount
	 */
	public void verifyTaxAmount(){
		assertThat(driver.findElement(By.id(BuildPage.taxAmount)).getText().trim()).isEqualTo(BuildConstants.expTaxAmount);
	}
	/**
	 * This Method is used to verify the grand total
	 */
	public void verifyGrandAmount(){
		String value =driver.findElement(By.id(BuildPage.grandTotal)).getText().trim().replace("0", "").replace(",","");
		assertThat(Util.stringToDouble(value)).isEqualTo(totalPrice());
	}
	
}
