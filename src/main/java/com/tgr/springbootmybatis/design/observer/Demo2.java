package com.tgr.springbootmybatis.design.observer;

import java.util.Observable;

public class Demo2 {
	
	public static void main(String[] args) {
		WeatherData2 w = new WeatherData2();
		Board12 b = new Board12(w);
		w.setWeather(9f, 8f, 7f);
	}
	
}

interface Display2{//布告需要显示时调用
	public void display();
}

class WeatherData2 extends Observable{//实现主题 我现在是主题 被别人观察
	
	private float temp;
	private float humidity;
	private float pressure;
	
	public WeatherData2() {
	}
	
	public void weatherChanged() {
		setChanged();//super
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

	public float getPressure() {
		return pressure;
	}

	public float getTemp() {
		return temp;
	}

}

class Board12 implements java.util.Observer,Display2{
	Observable observable;
	private float temp;
	private float humidity;
	
	public Board12(Observable observable) {
		this.observable = observable;
		observable.addObserver(this);
	}
	
	@Override
	public void update(Observable o, Object arg) {
		if(o instanceof WeatherData2) {//判断主题是否对应
			//默认为pull方式获取更新数据，这样适应性更强
			WeatherData2 weatherData2 = (WeatherData2)o;
			this.temp = weatherData2.getTemp();
			this.humidity = weatherData2.getHumidity();
			//更新面板
			display();
		}
	}
	
	@Override
	public void display() {
		System.out.println("Current conditions: "+temp);
	}


	
}

