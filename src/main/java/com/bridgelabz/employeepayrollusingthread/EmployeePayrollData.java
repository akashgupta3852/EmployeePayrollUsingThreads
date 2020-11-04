package com.bridgelabz.employeepayrollusingthread;

import java.time.LocalDate;

public class EmployeePayrollData {
	public int id;
	public String name;
	public String gender;
	public double salary;
	public LocalDate startDate;

	public EmployeePayrollData(int id, String name, String gender, double salary, LocalDate startDate) {
		this.id = id;
		this.name = name;
		this.gender = gender;
		this.salary = salary;
		this.startDate = startDate;
	}

	@Override
	public String toString() {
		return "id= " + id + ", name= " + name + ", gender= " + gender + ",salary= " + salary + ", startDate="
				+ startDate;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null || getClass() != obj.getClass())
			return false;
		EmployeePayrollData that = (EmployeePayrollData) obj;
		return (id == that.id && Double.compare(that.salary, salary) == 0 && name.equals(that.name)
				&& gender.equals(that.gender));
	}
}