package stef.testareProiectCTS;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.fail;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import stef.patterns.facade.AppFacade;
import stef.patterns.factory.Mesaj;
import stef.patterns.factory.MesajAbstract;
import stef.patterns.factory.MesajImportant;
import stef.patterns.factory.MesajUrgent;
import stef.patterns.observer.Client;
import stef.patterns.observer.ClientGermania;
import stef.patterns.observer.ClientRomania;

public class TestMemento {

	private static FileReader reader;
	private static BufferedReader bufferedReader;
	private static AppFacade app;
	private List<MesajAbstract> mesaje = new ArrayList<MesajAbstract>();
	private FileReader r;
	private BufferedReader br;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		System.out.println("Before class - citesc din fisier clienti test");
		app = new AppFacade();
		reader = new FileReader("testClienti.txt");
		bufferedReader = new BufferedReader(reader);
		String linie = null;
		while ((linie = bufferedReader.readLine()) != null && linie.length() != 0) {
			System.out.println(linie);
			String[] cuv = linie.split(" ");
			if (cuv[1].equalsIgnoreCase("roman")) {
				Client roman = new ClientRomania(cuv[2], cuv[3], cuv[4], Float.parseFloat(cuv[5]));
				roman.seteazaCont();
				app.adaugaObserver(roman);
			}
			if (cuv[1].equalsIgnoreCase("german")) {
				app.adaugaObserver(new ClientGermania(cuv[2], cuv[3], cuv[4], Float.parseFloat(cuv[5])));
			}
		}
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		System.out.println("After class - inchid fisierul cu clientii test");
		bufferedReader.close();
		reader.close();
	}

	@Before
	public void setUp() throws Exception {
		System.out.println("SetUp - citesc mesajele test din fisier");
		this.r = new FileReader("testMesaje.txt");
		this.br = new BufferedReader(r);
		String linie = null;
		while ((linie = br.readLine()) != null && linie.length() != 0) {
			System.out.println(linie);
			String[] cuv = linie.split(" ");
			if (cuv[1].equalsIgnoreCase("urgent")) {
				this.mesaje.add(new MesajUrgent(cuv[2]));
			}
			if (cuv[1].equalsIgnoreCase("important")) {
				this.mesaje.add(new MesajImportant(cuv[2]));
			}
			if (cuv[1].equalsIgnoreCase("informativ")) {
				this.mesaje.add(new Mesaj(cuv[2]));
			}
		}
	}

	@After
	public void tearDown() throws Exception {
		System.out.println("TearDown - inchid fisierul cu mesajele test");
		this.br.close();
		this.r.close();
	}

	@Test
	public void testSalvareSiRestaurareStareMesaj() {
		for (MesajAbstract m : this.mesaje) {
			app.salveazaStareMesaj(m);
			MesajAbstract mesajCopie = app.genereazaMesaj(m.getPrioritate(), m.getMesaj());
			m.setMesaj("ceva ceva");
			assertNotEquals("Continutul mesajelor sunt egale!", m.getMesaj(), mesajCopie.getMesaj());
			try {
				m.undo(app.restaureazaStareMesaj(m));
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
			assertEquals("Continutul mesajelor nu sunt egale!", m.getMesaj(), mesajCopie.getMesaj());
		}
	}

	@Test
	public void testSalvareStareMesajNull() {
		try {
			app.salveazaStareMesaj(null);
			fail("Trebuia sa fie aruncata o exceptie!");
		} catch (IllegalArgumentException ex) {
			System.out.println(ex);
		}
	}
	
	@Test
	public void testRestaurareStareMesajNull() {
		MesajAbstract m = this.mesaje.get(0);
		try{
			try {
				m.undo(app.restaureazaStareMesaj(m));
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		catch(IllegalArgumentException ex){
			System.out.println(ex);
		}
	}
	
	@Test
	public void testRestaurareStareMesajDupaCeNuMaiAmSalvari() {
		MesajAbstract m = this.mesaje.get(0);
		try{
			m.setMesaj("Stare initiala");
			app.salveazaStareMesaj(m);
			System.out.println("S-a salvat mesajul cu starea " + m.getMesaj());
			m.setMesaj("Stare1");
			app.salveazaStareMesaj(m);
			m.setMesaj("Stare2");
			System.out.println("S-a salvat mesajul cu starea " + m.getMesaj());
			try {
				m.undo(app.restaureazaStareMesaj(m));
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
			System.out.println("S-a restaurat mesajul la starea " + m.getMesaj());
			try {
				m.undo(app.restaureazaStareMesaj(m));
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
			System.out.println("S-a restaurat mesajul la starea " + m.getMesaj());
			try {
				m.undo(app.restaureazaStareMesaj(m));
			} catch (IllegalAccessException e) {
				System.out.println(e);
			}
			System.out.println("S-a restaurat mesajul la starea " + m.getMesaj());
		}
		catch(IllegalArgumentException ex){
			System.out.println(ex);
		}
	}
	
	@Test
	public void testSetareStareMesajNull() {
		MesajAbstract m = this.mesaje.get(0);
		try{
			m.setMesaj(null);
		}
		catch(IllegalArgumentException ex){
			System.out.println(ex);
		}
	}
	
	@Test
	public void testSetareStareMesajGol() {
		MesajAbstract m = this.mesaje.get(0);
		try{
			m.setMesaj("");
		}
		catch(IllegalArgumentException ex){
			System.out.println(ex);
		}
	}

}
