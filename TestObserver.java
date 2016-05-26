package stef.testareProiectCTS;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.List;

import org.junit.Test;

import stef.patterns.facade.AppFacade;
import stef.patterns.observer.ClientRomania;
import stef.patterns.observer.Notificare;

public class TestObserver {

	
	@Test
	public void testAdaugareObservator() {
		AppFacade app = new AppFacade();
		ClientRomania roman = new ClientRomania("Stef Serban","stefserban13@stud.ase.ro","1423455423XXXX",3400);
		app.adaugaObserver(roman);
		List<Notificare> observatori = app.getBlackBoard().getObservatori();
		assertTrue("Nu s-a adaugat observatorul", observatori.contains(roman));
	}
	
	@Test
	public void testAdaugareObservatorNull() {
		AppFacade app = new AppFacade();
		ClientRomania roman = null;
		try{
			app.adaugaObserver(roman);
			fail("Trebuia sa fie aruncata o exceptie!");
		}
		catch(IllegalArgumentException ex){
			System.out.println(ex);
		}
	}

}
