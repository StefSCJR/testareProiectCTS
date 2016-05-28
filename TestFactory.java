package stef.testareProiectCTS;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import stef.patterns.facade.AppFacade;
import stef.patterns.factory.MesajAbstract;
import stef.patterns.observer.ClientGermania;
import stef.utils.Prioritate;

public class TestFactory {
	
	private AppFacade app;

	@Before
	public void setUp() throws Exception {
		System.out.println("----------------Initializari pentru test!----------------");
		this.app = new AppFacade();
		ClientGermania german = new ClientGermania("Schweinsteiger Greg","greggs@abc.de","4564223432XXXX", 75300);
		this.app.adaugaObserver(german);
	}

	@After
	public void tearDown() throws Exception {
		System.out.println("----------------Final de test!----------------");
	}

	@Test
	public void testGenerareMesajUrgent() {
		MesajAbstract mesaj = this.app.genereazaMesaj(Prioritate.URGENT, "Test mesaj urgent!");
		assertEquals("Valorile nu sunt egale!", Prioritate.URGENT, mesaj.getPrioritate());
	}
	
	@Test
	public void testGenerareMesajInformativSiVerificarePrioritateDiferitaDeMesajUrgent() {
		MesajAbstract mesaj = this.app.genereazaMesaj(Prioritate.INFORMATIV, "Test mesaj urgent!");
		assertNotEquals("Prioritatea mesajului este egala!", Prioritate.URGENT, mesaj.getPrioritate());
	}
	
	@Test
	public void testGenerareMesajSiValidareContinut() {
		String msg = "Test mesaj urgent!";
		MesajAbstract mesaj = this.app.genereazaMesaj(Prioritate.INFORMATIV, msg);
		assertEquals("Continutul mesajului nu este egal!", msg, mesaj.getMesaj());
	}
	
	@Test
	public void testGenerareMesajCuContinutNull() {
		MesajAbstract mesaj = this.app.genereazaMesaj(Prioritate.INFORMATIV, null);
		assertNull("Continutul mesajului nu este null!", mesaj.getMesaj());
	}
	
	@Test
	public void testGenerareMesajCuCPrioritateNull() {
		try{
			MesajAbstract mesaj = this.app.genereazaMesaj(null, "Test mesaj fara prioritate");
			System.out.println(mesaj.getPrioritate());
		}
		catch(IllegalArgumentException ex){
			System.out.println(ex);
		}
	}

}
