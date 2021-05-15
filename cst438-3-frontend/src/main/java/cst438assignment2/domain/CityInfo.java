package cst438assignment2.domain;

public class CityInfo {

	public City city;
	public Country country;
	public TimeAndTemp timeAndTemp;
	
	public CityInfo(City city, Country country, TimeAndTemp timeAndTemp)
	{
		this.city = city;
		this.country = country;
		this.timeAndTemp = timeAndTemp;
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((city == null) ? 0 : city.hashCode());
		result = prime * result + ((country == null) ? 0 : country.hashCode());
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
		CityInfo other = (CityInfo) obj;
		if (city == null)
		{
			if (other.city != null)
				return false;
		} else if (!city.equals(other.city))
			return false;
		if (country == null)
		{
			if (other.country != null)
				return false;
		} else if (!country.equals(other.country))
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
