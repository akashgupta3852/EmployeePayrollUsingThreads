package com.bridgelabz.employeepayrollusingthread;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class EmployeePayrollServiceTest {
	private EmployeePayrollService employeePayrollService;

	@Before
	public void initialize() {
		employeePayrollService = new EmployeePayrollService();
	}

	@Test
	public void givenEmployeePayrollInDB_WhenRetrieved_ShouldMatchEmployeeCount() throws CustomException {
		List<EmployeePayrollData> employeePayrollList = employeePayrollService
				.readEmployeePayrollData(EmployeePayrollService.IOService.DB_IO);
		Assert.assertEquals(8, employeePayrollList.size());
	}

	@Test
	public void givenNewSalaryForEmployee_WhenUpdated_ShouldSyncWithDB() throws CustomException {
		employeePayrollService.updateEmployeeSalary("Terisa", 3000000.00);
		employeePayrollService.readEmployeePayrollData(EmployeePayrollService.IOService.DB_IO);
		boolean result = employeePayrollService.checkEmployeePayrollInSyncWithDB("Terisa");
		Assert.assertTrue(result);
	}

	@Test
	public void givenNewSalaryForEmployee_WhenUpdatedUsingPreparedStatement_ShouldSyncWithDB() throws CustomException {
		employeePayrollService.updateEmployeeSalaryUsingPreparedStatement("Terisa", 3000000.00);
		employeePayrollService.readEmployeePayrollData(EmployeePayrollService.IOService.DB_IO);
		boolean result = employeePayrollService.checkEmployeePayrollInSyncWithDB("Terisa");
		Assert.assertTrue(result);
	}

	@Test
	public void givenDateRange_WhenRetrieved_ShouldMatchEmployeeCount() throws CustomException {
		List<EmployeePayrollData> employeePayrollList = employeePayrollService.findEmployeeByDateRange("2019-01-01",
				"2020-10-31");
		Assert.assertEquals(1, employeePayrollList.size());
	}

	@Test
	public void givenPayrollData_WhenMaxSalaryRetrievedByGender_ShouldReturnProperValue() throws CustomException {
		double maxSalaryofFemale = employeePayrollService.findMaxSalaryByGender("F");
		Assert.assertEquals(3000000, maxSalaryofFemale, 0.00);
	}

	@Test
	public void givenPayrollData_WhenMinSalaryRetrievedByGender_ShouldReturnProperValue() throws CustomException {
		double minSalaryofFemale = employeePayrollService.findMinSalaryByGender("F");
		Assert.assertEquals(3000000, minSalaryofFemale, 0.00);
	}

	@Test
	public void givenPayrollData_WhenTotalSalaryRetrievedByGender_ShouldReturnProperValue() throws CustomException {
		double salaryofFemale = employeePayrollService.calculateTotalSalaryByGender("F");
		Assert.assertEquals(3000000, salaryofFemale, 0.00);
	}

	@Test
	public void givenPayrollData_WhenTotalCountRetrievedByGender_ShouldReturnProperValue() throws CustomException {
		int noOfMale = employeePayrollService.countByGender("M");
		Assert.assertEquals(7, noOfMale);
	}

	@Test
	public void givenPayrollData_WhenAverageSalaryRetrievedByGender_ShouldReturnProperValue() throws CustomException {
		double avgSalaryofFemale = employeePayrollService.findAvgSalaryByGender("F");
		Assert.assertEquals(3000000, avgSalaryofFemale, 0.00);
	}

	@Test
	public void givenNewEmployee_WhenAdded_ShouldBeSyncWithDB() {
		try {
			employeePayrollService.readEmployeePayrollData(EmployeePayrollService.IOService.DB_IO);
			employeePayrollService.addEmployeeToPayrollData("Sohan", "M", 2000000.00, "2020-11-04");
			boolean result = employeePayrollService.checkEmployeePayrollInSyncWithDB("Mark");
			Assert.assertTrue(result);
		} catch (CustomException e) {
		}
	}

	@Test
	public void given6Employees_whenAddedToDB_shouldMatchEmployeeEntries() throws CustomException {
		EmployeePayrollData[] arrayOfEmps = { new EmployeePayrollData(0, "Jeff Bezos", "M", 600000.0, LocalDate.now()),
				new EmployeePayrollData(0, "Bill Gates", "M", 500000.0, LocalDate.now()),
				new EmployeePayrollData(0, "Mark Zuckerberg", "M", 400000.0, LocalDate.now()),
				new EmployeePayrollData(0, "Sunder Pichai", "M", 300000.0, LocalDate.now()),
				new EmployeePayrollData(0, "Mukesh Ambani", "M", 200000.0, LocalDate.now()),
				new EmployeePayrollData(0, "Anil Ambani", "M", 100000.0, LocalDate.now()) };
		employeePayrollService.readEmployeePayrollData(EmployeePayrollService.IOService.DB_IO);
		Instant start = Instant.now();
		employeePayrollService.addEmployeesToPayroll(Arrays.asList(arrayOfEmps));
		Instant end = Instant.now();
		System.out.println("Duration without Thread; " + Duration.between(start, end));
		Assert.assertEquals(7, employeePayrollService.countEntries(EmployeePayrollService.IOService.DB_IO));
	}
}