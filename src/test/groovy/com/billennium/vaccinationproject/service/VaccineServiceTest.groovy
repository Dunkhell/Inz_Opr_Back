package com.billennium.vaccinationproject.service

import com.billennium.vaccinationproject.VaccinationprojectApplication
import com.billennium.vaccinationproject.dto.VaccineDto
import com.billennium.vaccinationproject.entity.VaccineEntity
import com.billennium.vaccinationproject.repository.VaccineRepository
import com.billennium.vaccinationproject.service.VaccineService
import com.billennium.vaccinationproject.utilities.Manufacturer
import com.billennium.vaccinationproject.utilities.VaccineDose;
import org.spockframework.spring.SpringBean
import org.spockframework.spring.SpringSpy
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.test.context.ContextConfiguration
import spock.lang.Specification;

@SpringBootTest(classes = [VaccinationprojectApplication.class,VaccineService.class])
class VaccineServiceTest extends Specification {

    @SpringSpy
    VaccineService vaccineService;

    def "Should add new Vaccine with id 1 to DB"() {
        setup:
        def manufacturer = Manufacturer.ASTRAZENECA
        def newVaccine = new VaccineDto()
        newVaccine.setManufacturer(manufacturer)
        vaccineService.addVaccine(newVaccine)

        when:
        def result = vaccineService.findVaccineById(1)

        then:
        result.getManufacturer() == Manufacturer.ASTRAZENECA
    }

    def "Should update Vaccine with id 1 from DB"(){
        setup:
        def updateVaccine = new VaccineDto()
        updateVaccine.setManufacturer(Manufacturer.JOHNSON_JOHNSON)

        and:
        vaccineService.updateVaccineById(1,updateVaccine)

        when:
        def result = vaccineService.findVaccineById(1)

        then:
        result.getManufacturer() == Manufacturer.JOHNSON_JOHNSON
    }

    def "Should delete Vaccine with id 1 from DB"() {
        setup:
        def old_vaccine = vaccineService.findVaccineById(1)

        vaccineService.deleteVaccineById(1)

        when:
        def result = vaccineService.getAllVaccines(null,0,10,"id").getBody()

        then:
        !result.contains(old_vaccine)
    }
}

