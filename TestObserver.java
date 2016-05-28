package stef.testareProiectCTS;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.List;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import stef.patterns.facade.AppFacade;
import stef.patterns.factory.MesajImportant;
import stef.patterns.factory.MesajUrgent;
import stef.patterns.observer.ClientRomania;
import stef.patterns.observer.Notificare;

public class TestObserver {

	private static AppFacade app = null;
	private static ClientRomania roman;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		System.out.println("Instantiez obiectele necesare pentru toate testele din " + TestObserver.class);
		app = new AppFacade();
		roman = new ClientRomania("Stef Serban","stefserban13@stud.ase.ro","1423455423XXXX",3400);
		app.adaugaObserver(roman);
	}
	
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		System.out.println("Atribuirea valorii null obiectelor care au fost necesare testelor din " + TestObserver.class);
		app = null;
		roman = null;
	}
	
	@Test
	public void testAdaugareObservator() {
		System.out.println("Testez adaugarea de observer - valoare normala");
		List<Notificare> observatori = app.getBlackBoard().getObservatori();
		assertTrue("Nu s-a adaugat observatorul", observatori.contains(roman));
	}
	
	@Test
	public void testAdaugareObservatorNull() {
		System.out.println("Testez adaugarea de observer - valoare speciala null");
		ClientRomania roman2 = null;
		try{
			app.adaugaObserver(roman2);
			fail("Trebuia sa fie aruncata o exceptie!");
		}
		catch(IllegalArgumentException ex){
			System.out.println(ex);
		}
	}

	@Test
	public void testNotificareObserveriCuMesajNull(){
		System.out.println("Test notificare cu mesaj null");
		for(Notificare o : app.getBlackBoard().getObservatori()){
			if (o instanceof ClientRomania) {
				ClientRomania r = (ClientRomania) o;
				r.seteazaCont();
			}
			try{
				o.notifica(null);
				fail("trebuia sa fie aruncata o exceptie!");
			}
			catch(IllegalArgumentException ex){
				System.out.println(ex);
			}
		}
	}
	
	@Test
	public void testNotificareObserveriCuMesajUrgent(){
		System.out.println("Test notificare cu mesaj null");
		for(Notificare o : app.getBlackBoard().getObservatori()){
			if (o instanceof ClientRomania) {
				ClientRomania r = (ClientRomania) o;
				r.seteazaCont();
			}
			try{
				o.notifica(new MesajUrgent("Norificare urgenta!"));
			}
			catch(IllegalArgumentException ex){
				System.out.println(ex);
				fail("Nu trebuia sa fie aruncata o exceptie!");
			}
		}
	}
	
	@Test
	public void testNotificareObserveriCuMesajImportant(){
		System.out.println("Test notificare cu mesaj abstract");
		for(Notificare o : app.getBlackBoard().getObservatori()){
			if (o instanceof ClientRomania) {
				ClientRomania r = (ClientRomania) o;
				r.seteazaCont();
			}
			try{
				o.notifica(new MesajImportant("Notificare importanta!"));
			}
			catch(IllegalArgumentException ex){
				System.out.println(ex);
				fail("Nu trebuia sa fie aruncata o exceptie!");
			}
		}
	}
}
