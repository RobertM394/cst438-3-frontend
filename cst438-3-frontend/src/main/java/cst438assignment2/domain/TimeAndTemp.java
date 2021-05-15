package cst438assignment2.domain;

public class TimeAndTemp {
	
	public double temp;
	public long time;
	public int timezone;
	
	public TimeAndTemp() { };
	
	public TimeAndTemp(double temp, long time, int timezone)
	{
		this.temp = temp;
		this.time = time;
		this.timezone = timezone;
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(this.temp);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + (int) (time ^ (time >>> 32));
		result = prime * result + timezone;
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
		TimeAndTemp other = (TimeAndTemp) obj;
		if (Double.doubleToLongBits(temp) != Double.doubleToLongBits(other.temp))
			return false;
		if (time != other.time)
			return false;
		if (timezone != other.timezone)
			return false;
		return true;
	}
	
}


