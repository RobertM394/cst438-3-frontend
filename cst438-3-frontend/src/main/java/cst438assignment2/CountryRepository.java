package cst438assignment2;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cst438assignment2.domain.Country;

@Repository
public interface CountryRepository extends JpaRepository<Country, String> {
	Country findByCode(String code);

}
