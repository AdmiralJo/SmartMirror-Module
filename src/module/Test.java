package module;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.RaspiPin;

public class Test {

	public static void main(String[] args) throws InterruptedException {

		System.out.println("<--Pi4J--> GPIO Trigger Example ... started.");

		final GpioController gpio = GpioFactory.getInstance();

		final GpioPinDigitalOutput pin01 = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_04, "01", PinState.HIGH);
		
		while (true) {
			Thread.sleep(2000);
			pin01.low();
			System.out.println("LOW");
			Thread.sleep(2000);
			pin01.high();
			System.out.println("HIGH");
		}
	}
}