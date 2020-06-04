package com.tgr.springbootmybatis.design.observer;

import java.util.ArrayList;

public class Demo1 {
	
	public static void main(String[] args) {
		WeatherData weatherData = new WeatherData();
		Board1 b = new Board1(weatherData);
		weatherData.setWeather(1f, 2f, 3f);
	}
}

interface Subject{//主题接口
	public void registerObserver(Observer o);//注册观察者
	public void removeObserver(Observer o);//删除观察者
	public void notifyObservers();//主题状态改变 通知观察者
}

interface Observer{//所有观察者需要实现的接口
	public void update(float temp,float humidity,float presure);
}

interface Display{//布告需要显示时调用
	public void display();
}

class WeatherData implements Subject{//实现主题 我现在是主题 被别人观察
	
	private ArrayList<Observer> observers;
	private float temp;
	private float humidity;
	private float pressure;
	
	public WeatherData() {
		observers = new ArrayList<Observer>();
	}
	
	@Override
	public void registerObserver(Observer o) {
		observers.add(o);
	}

	@Override
	public void removeObserver(Observer o) {
		int i = observers.indexOf(o);
		if(i>=0) {
			observers.remove(i);
		}
	}

	@Override
	public void notifyObservers() {
		//刷新队列中所有已经注册的观察者
		for(int i = 0 ; i < observers.size();i++) {
			observers.get(i).update(temp, humidity, pressure);
		}
	}
	
	public void weatherChanged() {
		//气象站得知消息更新时调用,更新观察者(布告板)
		notifyObservers();
	}
	
	//主动刷新气象信息
	public void setWeather(float temperature,float humidity,float pressure) {
		this.temp = temperature;
		this.humidity = humidity;
		this.pressure = pressure;
		weatherChanged();
	}

	//单独获取气象信息3个get
	public float getHumidity() {
		return humidity;
	}

	public void setHumidity(float humidity) {
		this.humidity = humidity;
	}

	public float getPressure() {
		return pressure;
	}

}

class Board1 implements Observer,Display2{
	private float temp;
	private float humidity;
	private Subject weatherData;
	
	public Board1(Subject weatherData) {
		this.weatherData = weatherData;
		weatherData.registerObserver(this);
	}
	
	@Override
	public void display() {
		System.out.println("Current conditions: "+temp);
	}
	@Override
	public void update(float temp, float humidity, float presure) {
		this.temp = temp;
		this.humidity = humidity;
		display();
	}
}
