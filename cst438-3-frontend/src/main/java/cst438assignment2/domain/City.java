package cst438assignment2.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name="city")
public class City {
	
	@Id
	private int ID;
	private String name;
	private String district;
	private int population;
	
	//transient marks extra field in an entity class 
	//that is not written to or read from the database
	@Transient
	public TimeAndTemp timeAndTemp;
	
	@ManyToOne
	@JoinColumn(name = "countrycode", referencedColumnName="code")
	private Country country;
	
	public City() { }

	public City(int iD, String name, String district, int population, Country country)
	{
		super();
		ID = iD;
		this.name = name;
		this.district = district;
		this.population = population;
		this.country = country;
	}

	public int getID()
	{
		return ID;
	}

	public void setID(int iD)
	{
		ID = iD;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getDistrict()
	{
		return district;
	}

	public void setDistrict(String district)
	{
		this.district = district;
	}

	public int getPopulation()
	{
		return population;
	}

	public void setPopulation(int population)
	{
		this.population = population;
	}

	public TimeAndTemp getTimeAndTemp()
	{
		return timeAndTemp;
	}

	public void setTimeAndTemp(TimeAndTemp timeAndTemp)
	{
		this.timeAndTemp = timeAndTemp;
	}

	public Country getCountry()
	{
		return country;
	}

	public void setCountry(Country country)
	{
		this.country = country;
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ID;
		result = prime * result + ((country == null) ? 0 : country.hashCode());
		result = prime * result + ((district == null) ? 0 : district.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + population;
		result = prime * result + ((timeAndTemp == null) ? 0 : timeAndTemp.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		City other = (City) obj;
		if (ID != other.ID)
			return false;
		if (country == null)
		{
			if (other.country != null)
				return false;
		} else if (!country.equals(other.country))
			return false;
		if (district == null)
		{
			if (other.district != null)
				return false;
		} else if (!district.equals(other.district))
			return false;
		if (name == null)
		{
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (population != other.population)
			return false;
		if (timeAndTemp == null)
		{
			if (other.timeAndTemp != null)
				return false;
		} else if (!timeAndTemp.equals(other.timeAndTemp))
			return false;
		return true;
	}

}

