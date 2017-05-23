package module;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.Properties;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.RaspiPin;

public class Module {

	public Module() throws IOException, InterruptedException {

		final GpioController gpio = GpioFactory.getInstance();
		final GpioPinDigitalOutput relay = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_01, "RELAY", PinState.LOW);
		relay.setShutdownOptions(true, PinState.LOW);

		Properties properties = new Properties();
		properties.setProperty("ip", "127.0.0.1");
		properties.setProperty("port", "1234");
		try {
			properties.load(new FileReader(new File("res/settings.properties")));
		} catch (IOException e) {
			System.out.println("Can't read properties!");
			e.printStackTrace();
		}

		Socket socket = null;
		try {
			socket = new Socket(properties.getProperty("ip"), Integer.parseInt(properties.getProperty("port")));
			BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

			while (true) {

				String s = reader.readLine();
				System.out.println("Received: " + s);

				if (s.charAt(0) == '0')
					relay.low();
				else if (s.charAt(0) == '1')
					relay.high();
			}

		} catch (NumberFormatException | IOException e) {
			e.printStackTrace();
		}

		socket.close();
	}

	public static void main(String[] args) throws IOException, InterruptedException {
		new Module();
	}
}
