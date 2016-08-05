package app;

import util.Observer;

public class Mother implements Observer<Baby, String> {

	@Override
	public void update(Baby subject, String param) {
		String babyName = (String) param;
		System.out.println("Let's change that diaper on " + babyName + "!");
	}

}