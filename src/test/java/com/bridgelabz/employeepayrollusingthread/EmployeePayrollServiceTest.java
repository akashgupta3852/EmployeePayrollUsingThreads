package com.bridgelabz.employeepayrollusingthread;

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
		Assert.assertEquals(4, employeePayrollList.size());
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
		Assert.assertEquals(2, employeePayrollList.size());
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
		int noOfFemale = employeePayrollService.countByGender("F");
		Assert.assertEquals(1, noOfFemale);
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
}