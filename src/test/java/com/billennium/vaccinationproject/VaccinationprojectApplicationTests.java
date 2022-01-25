package com.billennium.vaccinationproject;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

class VaccinationprojectApplicationTests {

	Calculator underTest = new Calculator();

	@Test
	void itShouldAddNumbers() {
		int x = 10;
		int y = 15;

		int sum = underTest.add(10,15);

		assertThat(sum).isEqualTo(25);
	}

	class Calculator {
		int add(int x, int y) {
			return x+y;
		}
	}
}
